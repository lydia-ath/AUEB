/* Konstantina Souvatzidaki, 3170149
 * Lydia Athanasiou, 3170003 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Tracker {

    ServerSocket socket;
    String ip;
    int port;
    ConcurrentHashMap<String,ArrayList<String>> registered_peers; //key: username, value: (password, count_downloads, count_failures)
    ConcurrentHashMap<Integer,ArrayList<String>> loggedin_peers; //key: token_id, value: 	(ip_address, port,	user_name)
    List<String> file_names; //list of all file names
    ConcurrentHashMap<String,ArrayList<Integer>> files_peers; //key: file name, value: list of token_ids with this file

    //constructor
    public Tracker(String ip, int port) {
        this.ip = ip;
        this.port = port;
        registered_peers = new ConcurrentHashMap<String,ArrayList<String>>();
        loggedin_peers = new ConcurrentHashMap<Integer,ArrayList<String>>();
        file_names = Collections.synchronizedList(new ArrayList<String>());
        files_peers = new ConcurrentHashMap<String,ArrayList<Integer>>();

        accept_requests();
    }

    //ServerSocket accepting login, logout and register requests from Peers
    public void accept_requests() {
        try {
            socket = new ServerSocket(port,100);
            System.out.println("Started accepting requests from peers. . .");
            Socket peer; 
            while(true) {
                peer = socket.accept();
                System.out.println();

                //new thread for each request
                new Thread(new Runnable(){
                    Socket peer;
                    String request_type; 

                    //initialize thread
                    public Runnable init(Socket peer) {
                        this.peer = peer;
                        return this;
                    }

                    @Override 
                    public void run() {
                        ObjectInputStream input; ObjectOutputStream output;
                        try{
                            //initialize input and output streams to accept messages from peer and reply
                            output = new ObjectOutputStream(peer.getOutputStream());
                            input = new ObjectInputStream(peer.getInputStream());
                            //get message type (REGISTER, LOGIN, .. )
                            String type = (String)input.readObject(); 
                            System.out.println("Received a new request from a peer : "+type);

                            //REGISTER
                            if(type.equals("REGISTER")) {
                                register(input,output);
                            }
                            //LOGIN
                            else if(type.equals("LOGIN")) {
                                login_and_inform(input,output);
                            }
                            //LOGOUT
                            else if(type.equals("LOGOUT")) {
                                logout(input,output);
                            }
                            //REPLY LIST
                            else if(type.equals("LIST")) {
                                reply_list(output);
                            }
                            //DETAILS
                            else if(type.equals("DETAILS")) {
                                reply_details(input,output);   
                            //NOTIFY
                            }else if(type.equals("NOTIFY")) {
                                reply_notify(input,output);
                            }
                        }catch(IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }.init(peer)).start();
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        
    }


    /** Reply to register requests */
    public void register(ObjectInputStream input, ObjectOutputStream output) {
        String username,password;
        try {
            while(true) {
                username = (String)input.readObject();
                if(registered_peers.containsKey(username)) {
                    System.out.println("Username Already Exists. Request new.");
                    output.writeObject("DECLINED"); output.flush();
                }else {
                    System.out.println("Username Accepted");
                    output.writeObject("ACCEPTED"); output.flush();
                    break;
                }
            }
            password = (String)input.readObject(); //read the password
            registered_peers.put(username, new ArrayList<String>(Arrays.asList(password,"0","0"))); //register new peer
            System.out.println("Successfully registered peer with Username = "+username+" and Password = "+password);
        }catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    

    /** Reply to login requests */
    public void login_and_inform(ObjectInputStream input, ObjectOutputStream output) {
        String username,password,reply;
        int token = 0;
        try {
            while(true) {
                username = (String)input.readObject();
                if(registered_peers.containsKey(username)) {
                    output.writeObject("ACCEPTED"); output.flush();
                    break;
                }else {
                    System.out.println("Username doesn't exist. Request new.");
                    output.writeObject("DECLINED"); output.flush();
                }
            }
            while(true) {
                password = (String)input.readObject();
                if(password.equals(registered_peers.get(username).get(0))) {
                    token = new Random().nextInt();
                    while(loggedin_peers.containsKey(token)) token = new Random().nextInt();
                    System.out.println("Password accepted! Login Successful. Token for new peer = "+token);
                    output.writeObject(String.valueOf(token)); output.flush();
                    break;
                }else{
                    System.out.println("Wrong Password. Request new.");
                    output.writeObject("DECLINED"); output.flush();
                }
            }
    
            String ip = (String)input.readObject();
            String port = (String)input.readObject();
            ArrayList<String> peer_files = (ArrayList<String>)input.readObject();
            System.out.println("Received Peer information: ip = "+ip+", port = "+port+" Files:");
            for(String s: peer_files) System.out.println(s);
            //add info to hashmaps
            loggedin_peers.put(token,new ArrayList<String>(Arrays.asList(ip,port,username)));
            for(String s: peer_files){
                if(!file_names.contains(s)) file_names.add(s);
                if(!files_peers.containsKey(s)) files_peers.put(s,new ArrayList<Integer>(Arrays.asList(token)));
                else files_peers.get(s).add(token);
            }
            //confirm
            output.writeObject("OK"); output.flush();


            //inform all peers for the new files
            System.out.println("All logged-in peers currently: ");
            System.out.println(loggedin_peers);
            for(ArrayList<String> peer_details : loggedin_peers.values()) {
                if(!peer_details.get(1).equals(port)) {
                    Socket oldpeer = new Socket(peer_details.get(0),Integer.parseInt(peer_details.get(1)));
                    ObjectOutputStream output2 = new ObjectOutputStream(oldpeer.getOutputStream());
                    ObjectInputStream input2 = new ObjectInputStream(oldpeer.getInputStream());
                    output2.writeObject("UPDATE"); output2.flush();
                    reply = (String)input2.readObject();
                    if(reply.equals("OK")) System.out.println("Peer "+peer_details.get(2)+" updated.");
                }
            }
        }catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /** Reply to logout requests */
    public void logout(ObjectInputStream input, ObjectOutputStream output) {
        try {
            int temp_token = (Integer)input.readObject();
            System.out.println("Logging out from peer with token id = "+temp_token);
            loggedin_peers.remove(temp_token);
            for(ArrayList<Integer>  tokens : files_peers.values()) {
                if(tokens.contains(temp_token)) tokens.remove((Integer)temp_token);
            }
            output.writeObject("OK"); output.flush();
        }catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    

    /** Reply to list requests */
    public void reply_list(ObjectOutputStream output) {
        try {
            output.writeObject(file_names); output.flush();
            System.out.println("List of all files sent successfully!");
        }catch(IOException e) {
            e.printStackTrace();
        }   
    }


    /** Reply to details requests */
    public void reply_details(ObjectInputStream input, ObjectOutputStream output) {
        try {
            String reply;
            String filename = (String)input.readObject();
            System.out.println("Requested details for file "+filename);
            //find peers with requested file
            if(!files_peers.containsKey(filename)){
                System.out.println("File doesn't exist");
                output.writeObject("FILE DOESN'T EXIST"); output.flush();
            }else {
                System.out.println("File exists");
                output.writeObject("FILE EXISTS"); output.flush();
                ArrayList<Integer> peers_tokens = new ArrayList<Integer>(files_peers.get(filename));
                HashMap<Integer, ArrayList<String>> details_reply = new HashMap<Integer, ArrayList<String>>();
                for(int k: peers_tokens) {
                    //ip,port,username,count_downloads, count_failures
                    ArrayList<String> details = loggedin_peers.get(k);
                    ArrayList<String> temp = registered_peers.get(details.get(2));
                    details.add(temp.get(1));
                    details.add(temp.get(2));
                    
                    //checkactive
                    try {
                        Socket newpeer = new Socket(details.get(0),Integer.parseInt(details.get(1)));
                        ObjectOutputStream output2 = new ObjectOutputStream(newpeer.getOutputStream());
                        ObjectInputStream input2 = new ObjectInputStream(newpeer.getInputStream());
                        output2.writeObject("CHECKACTIVE"); output2.flush();
                        reply = (String)input2.readObject(); 
                        if(reply.equals("OK")) {
                            System.out.println("peer is active");
                            //add peer to details reply
                            details_reply.put(k,details);
                        }
                    }catch(ConnectException e) {
                        System.out.println("peer is not active");
                        //update data structures
                        loggedin_peers.remove(k);
                        for(ArrayList<Integer>  tokens : files_peers.values()) {
                            if(tokens.contains(k)) tokens.remove((Integer)k);
                        }
                    }
                } 
                //send active peer details
                output.writeObject(details_reply); output.flush();
                reply = (String)input.readObject();
                if(reply.equals("OK")) System.out.println("Details sent succesfully");
            }
        }catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }        
    }


    /** Reply to notify requests */
    public void reply_notify(ObjectInputStream input, ObjectOutputStream output) {
        try {
            boolean sent = (boolean)input.readObject();
            String file_name = (String)input.readObject();
            int peer_token = (int)input.readObject();
            String peer_username = (String)input.readObject();

            if(sent) {
                System.out.println("Peer with token "+peer_token+" has successfully received file "+file_name+" from peer with username "+peer_username);
                files_peers.get(file_name).add(peer_token);
                int count_downloads = Integer.parseInt(registered_peers.get(peer_username).get(1));
                count_downloads++;
                registered_peers.get(peer_username).set(1,String.valueOf(count_downloads));
            }else {
                System.out.println("Peer with token "+peer_token+" tried to receive file "+file_name+" from peer with username "+peer_username +" but failed");
                int count_failures = Integer.parseInt(registered_peers.get(peer_username).get(2));
                count_failures++;
                registered_peers.get(peer_username).set(2,String.valueOf(count_failures));
            }
            output.writeObject("OK"); output.flush();
        }catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }   
    }

    public static void main(String[] args) {
        new Tracker("192.168.2.2",6000);
    } 
}