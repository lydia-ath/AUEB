/* Konstantina Souvatzidaki, 3170149
 * Lydia Athanasiou, 3170003 */

import java.util.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class Peer {
    String shared_directory,ip; //shared directory of peer and ip address of the system
    int port; //port of peer
    ArrayList<String> file_names; //list of file names in this peer
    int token_id; //peer's login session token id
    boolean loggedin;
    List<String> all_files; //list of all file names in the p2p system
    ServerSocket requests; //socket to accept requests from tracker and other peers
    int tracker_port; //port of the tracker 

    /** Constructor */
    public Peer(String ip, int port, String shared_directory, int tracker_port) {
        this.ip = ip;
        this.port = port;
        this.shared_directory = shared_directory;
        this.tracker_port = tracker_port;
        loggedin = false;
        token_id = 0;
        init();
        connect();
        if(loggedin) start();
    }//Peer


    /** Initialize files of peer */
    public void init() {
        file_names = new ArrayList<String>();
        File dir = new File(shared_directory);
        File[] files = dir.listFiles();
        for (File f : files) {
            file_names.add(f.getName());
        }                                                 
    }//init


    /** Connect to the system: Register and Login */
    public void connect() {
        System.out.println("Welcome to peer. Register or Login?\n1) Register\n2) Login\n0) Exit");
        Scanner in = new Scanner(System.in);
        String user_input; int option = -1;
        while(option!= 1 && option!=2 ) {
            user_input = in.nextLine();
            try {
                option = Integer.parseInt(user_input);
            }catch(NumberFormatException e) { continue; }
           if(option == 0) break;
        }
        if(option == 1) register(); //REGISTER
        else if(option==2) login_and_inform(); //LOGIN
    }//connect

    
    /** Start 2 threads - one to receive requests and one to send requests */
    public void start() {
        //ACCEPT REQUESTS
        Thread receive = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket;
                try {
                    requests = new ServerSocket(port,20);
                    while(true) {
                        socket = requests.accept();

                        //new thread for each request
                        new Thread(new Runnable(){
                            Socket socket;
                            String request_type; 
                            
                            public Runnable init(Socket socket) { //initialize thread
                                this.socket = socket;
                                return this;
                            }

                            @Override 
                            public void run() {
                                ObjectInputStream input; ObjectOutputStream output;
                                try{
                                    //initialize input and output streams to accept messages from peer and reply
                                    output = new ObjectOutputStream(socket.getOutputStream());
                                    input = new ObjectInputStream(socket.getInputStream());
                                    //get message type (CHECKACTIVE, .. )
                                    String type = (String)input.readObject();
                                    System.out.println("Received a new request from a peer or Tracker : "+type);

                                    //CHECKACTIVE
                                    if(type.equals("CHECKACTIVE")) {
                                        checkactive(output);
                                    }
                                    //DOWNLOAD REQUEST
                                    else if(type.equals("FILE")) {
                                        reply_simpleDownload(input,output);
                                    }  
                                    //UPDATE LIST 
                                    else if(type.equals("UPDATE")) {
                                        output.writeObject("OK"); output.flush();
                                        list();
                                    }
                                }catch(IOException | ClassNotFoundException e) {
                                    e.printStackTrace();
                        }}}.init(socket)).start();
                    }
                } catch (IOException e) {
                    System.out.println("Stopped receiving requests");
                }
            }
    
        });
        receive.start();
        
        //SEND REQUESTS
        Thread send = new Thread(new Runnable() {
            @Override
            public void run() {
                list();
                while(true) {
                    System.out.println("Choose: \n1) Select file\n0) Logout");
                    Scanner in = new Scanner(System.in);
                    String user_input; int option = -1;

                    while(option!= 1 && option!=0) {
                        user_input = in.nextLine();
                        try {
                            option = Integer.parseInt(user_input);
                        }catch(NumberFormatException e) { continue; }
                    }

                    if(option == 0) {
                        logout();
                        try {
                            requests.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    } 
                    else {
                        for(int i=1; i <= all_files.size(); i++) {
                            System.out.println(i+") "+all_files.get(i-1));
                        }
                        System.out.println("Choose a file. Press any other key to exit. . . ");
                        while(true) {
                            user_input = in.nextLine();
                            try {
                                option = Integer.parseInt(user_input);
                            }catch(NumberFormatException e) { System.out.println("Not a number"); continue; }
                            break;
                        }
                        if(option <= 0 || option > all_files.size()) {
                            continue;
                        }else {
                            if(!file_names.contains(all_files.get(option-1))) {
                                /*ip,port,username,count_downloads, count_failures */
                                HashMap<Integer,ArrayList<String>> details_reply = details(all_files.get(option-1));
                                if(details_reply != null) {
                                    simpleDownload(details_reply,all_files.get(option-1));
                                }
                            } else System.out.println("File already exists in peer");
                        }
                    }
                }   
        }});
        send.start();
    }//start



    /** PEER FUNCTIONS  */

    /** Register to the system */
    public void register() {
        Scanner in = new Scanner(System.in);
        String user_input,reply; 
        Socket tracker; //socket to connect to tracker
        ObjectInputStream input; ObjectOutputStream output; //input and output streams
        
        try {
            tracker = new Socket(ip,tracker_port);
            output = new ObjectOutputStream(tracker.getOutputStream());
            input = new ObjectInputStream(tracker.getInputStream());
            output.writeObject("REGISTER"); output.flush(); //send type 

            System.out.println("Registering new peer\nInsert username: ");
            while(true) {
                user_input = in.nextLine();
                output.writeObject(user_input); output.flush();  //send username and receive reply
                reply = (String)input.readObject();
                if(reply.equals("DECLINED")) {
                    System.out.println("Username already exists. Insert a new username:");
                }else if(reply.equals("ACCEPTED")) {
                    System.out.println("Username accepted!");
                    break;
                }
            }
            System.out.println("Insert password: ");
            user_input = in.nextLine();
            output.writeObject(user_input); output.flush();
            System.out.println("Register was successful!\n");
            login_and_inform();
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }//register


    /** Login to the system */
    public void login_and_inform() {
        Scanner in = new Scanner(System.in);
        String user_input,reply; 
        Socket tracker; //socket to connect to tracker
        ObjectInputStream input; ObjectOutputStream output; //input and output streams

        try {
            tracker = new Socket(ip,tracker_port);
            output = new ObjectOutputStream(tracker.getOutputStream());
            input = new ObjectInputStream(tracker.getInputStream());
            output.writeObject("LOGIN"); output.flush(); //send type

            System.out.println("Login\nInsert username: ");
            while(true) {  
                user_input = in.nextLine();
                //send username and receive reply
                output.writeObject(user_input); output.flush();
                reply = (String)input.readObject();
                if(reply.equals("DECLINED")) {
                    System.out.println("Username doesn't exist. Try again:");
                }else if(reply.equals("ACCEPTED")) {
                    System.out.println("Username accepted!");
                    break;
                }
            }
            System.out.println("Insert password: ");
            while(true) {
                user_input = in.nextLine();
                output.writeObject(user_input); output.flush();
                //read reply (if password is correct)
                reply = (String)input.readObject();
                if(reply.equals("DECLINED")) {
                    System.out.println("Wrong Password! Try again:");
                }else {
                    token_id = Integer.parseInt(reply);
                    loggedin = true;
                    System.out.println("Password correct! Session token of peer: "+token_id+"\n");
                    break;
                }
            }
            //INFORM 
            output.writeObject(ip); output.flush();
            output.writeObject(String.valueOf(port)); output.flush();
            output.writeObject(file_names); output.flush();
            reply = (String)input.readObject();
            if(reply.equals("OK")) System.out.println("Informed tracker about ip,port and files on this peer!");

        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }//login


    /** Logout from the system */
    public void logout() {
        Socket tracker; //socket to connect to tracker
        ObjectInputStream input; ObjectOutputStream output; //input and output streams
        String reply;
        try {
            tracker = new Socket(ip,tracker_port);
            output = new ObjectOutputStream(tracker.getOutputStream());
            input = new ObjectInputStream(tracker.getInputStream());
            output.writeObject("LOGOUT"); output.flush(); //send type 
            output.writeObject(token_id); output.flush();  //send token_id 
            reply = (String)input.readObject();
            if(reply.equals("OK")) {
                System.out.println("Logout successful!\n");
            }
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }//logout


    /** Request list of all files in p2p system **/
    public void list() {
        System.out.println("Requesting a list of all files in the P2P system. . .");
        Socket tracker; //socket to connect to tracker
        ObjectInputStream input; ObjectOutputStream output; //input and output streams
        try {
            tracker = new Socket(ip,tracker_port);
            output = new ObjectOutputStream(tracker.getOutputStream());
            input = new ObjectInputStream(tracker.getInputStream());
            output.writeObject("LIST"); output.flush(); //send type
            all_files = (List<String>)input.readObject();
            System.out.println("Seccessfully received a list of all files!\n");
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }//list


    /** Request details of specific file */
    public HashMap<Integer,ArrayList<String>> details(String filename) {
        System.out.println("Requesting details for file "+filename+". . .");
        Socket tracker; //socket to connect to tracker
        ObjectInputStream input; ObjectOutputStream output; //input and output streams
        String reply;
        HashMap<Integer, ArrayList<String>> details_reply = null;
        try {
            tracker = new Socket(ip,tracker_port);
            //initialize input and output streams to accept messages from peer and reply
            output = new ObjectOutputStream(tracker.getOutputStream());
            input = new ObjectInputStream(tracker.getInputStream());

            output.writeObject("DETAILS"); output.flush(); //send type
            output.writeObject(filename); output.flush(); //send type
            reply = (String)input.readObject();
            if(reply.equals("FILE DOESN'T EXIST")) {
                System.out.println("File doesn't exist in p2p system.\n");
            } else if(reply.equals("FILE EXISTS")) { 
                System.out.println("File exists! Receiving details. . .");
                details_reply = (HashMap<Integer, ArrayList<String>>)input.readObject();
                System.out.println("Received details. Peers that have the file:");
                System.out.println(details_reply+"\n");
                output.writeObject("OK"); output.flush();
            }
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
        return details_reply;
    }//details


    /** Reply to simpleDownload requests */
    public void reply_simpleDownload(ObjectInputStream input, ObjectOutputStream output) {
        try {
            String requested_file = (String)input.readObject();
            System.out.println("Requested file: "+requested_file);
            if(file_names.contains(requested_file)) {
                System.out.println("File found.");
                output.writeObject("FILE EXISTS"); output.flush();
                File f = new File(shared_directory+"\\"+requested_file);
                FileInputStream stream = new FileInputStream(f);
                byte[] file_bytes = new byte[(int)f.length()];
                stream.read(file_bytes);
                System.out.println("Succesfully read file bytes. Sending....");
                System.out.println("Sleeping for a while, to simulate download time. . . ");
                TimeUnit.SECONDS.sleep(10);
                output.writeObject(file_bytes); output.flush();
                System.out.println("Sent! \n");
            }else {
                System.out.println("File not found.\n");
                output.writeObject("FILE DOESN'T EXIST"); output.flush();
            }
        }catch(IOException | ClassNotFoundException |InterruptedException e) {
            e.printStackTrace();
        }
    }


    /** Checkactive */
    public void checkactive(ObjectOutputStream output) {
        try {
            output.writeObject("OK"); output.flush();
            System.out.println("Successfully replied to CHECKACTIVE request!\n");
        }catch(IOException e) {
            e.printStackTrace();
        }
    }


    /** Send simpleDownload request */
    public void simpleDownload(HashMap<Integer,ArrayList<String>> details_reply ,String file) {
        long start,end,seconds;
        int min_peer_token = 0;
        double temp,min_seconds=Double.MAX_VALUE;
        Socket peer; boolean flag = false;
        String reply;
        //check if all peers are active
        TreeMap<Double, Integer> sorted = new TreeMap<Double, Integer>();
        for(int token : details_reply.keySet()) {
            if(token != this.token_id) {
                flag = true;
                start = System.currentTimeMillis();
                ArrayList<String> temp2 = details_reply.get(token);
                try {
                    peer = new Socket(temp2.get(0),Integer.parseInt(temp2.get(1)));
                    ObjectOutputStream output2 = new ObjectOutputStream(peer.getOutputStream());
                    ObjectInputStream input2 = new ObjectInputStream(peer.getInputStream());
                    output2.writeObject("CHECKACTIVE"); output2.flush();
                    reply = (String)input2.readObject(); 
                    if(reply.equals("OK")) { }
                } catch(ConnectException e) {
                    System.out.println("Peer with token "+token+" is not active!");
                    flag = false;
                } catch(IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(flag) {
                    end = System.currentTimeMillis();
                    seconds = (end-start);
                    temp = Math.pow(0.9,Integer.parseInt(temp2.get(3)))*Math.pow(1.2,Integer.parseInt(temp2.get(4)));
                    temp*=seconds;
                    if(sorted.containsKey(temp)) temp+=0.1;
                    sorted.put(temp,token);
                    System.out.println("Peer with token "+token+" is active and responded in "+seconds+". The total factor is "+temp);
                }
            } 
        }

        boolean done =  false;
        String username;
        while(!sorted.isEmpty()) {
            //find peer with the minimum response time
            min_peer_token = sorted.pollFirstEntry().getValue();
            ArrayList<String> selected_peer = details_reply.get(min_peer_token);
            username = selected_peer.get(2);
            System.out.println("\nSelected peer with the minimum response time: "+username);

            try {
                Socket download = new Socket(selected_peer.get(0),Integer.parseInt(selected_peer.get(1)));
                ObjectOutputStream output3 = new ObjectOutputStream(download.getOutputStream());
                ObjectInputStream input3 = new ObjectInputStream(download.getInputStream());
                output3.writeObject("FILE"); output3.flush();
                output3.writeObject(file); output3.flush();
                reply = (String)input3.readObject(); 
                if(reply.equals("FILE EXISTS")) {
                    System.out.println("File exists in peer "+username);
                    byte[] file_bytes = (byte[])input3.readObject();
                    File f = new File(shared_directory+"\\"+file);
                    FileOutputStream stream = new FileOutputStream(f);
                    stream.write(file_bytes);
                    done = true;
                    file_names.add(file);
                    System.out.println("File successfully downloaded from peer "+username);
                    send_notify(file,username,true);
                    break;
                }else {
                    System.out.println("File doesn't exist in peer "+username);
                }
            }catch(IOException | ClassNotFoundException e) {
                System.out.println("File transfer with peer "+username+" failed! \n");
                send_notify(file,username,false);
            }
        }
        if(!done) System.out.println("File wasn't found in any peer. \n");
    }


    /** Notify Tracker */
    public void send_notify(String file_sent,String peer_username,boolean sent) {
        try {
            Socket tracker = new Socket(ip,tracker_port);
            //initialize input and output streams to accept messages from peer and reply
            ObjectOutputStream output = new ObjectOutputStream(tracker.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(tracker.getInputStream());
            output.writeObject("NOTIFY"); output.flush(); //send type 

            System.out.println("Notifying tracker about file transfer!");
            output.writeObject(sent); output.flush();
            output.writeObject(file_sent); output.flush();
            output.writeObject(token_id); output.flush();
            output.writeObject(peer_username); output.flush();

            String reply = (String)input.readObject();
            if(reply.equals("OK")) {
                System.out.println("Notification sent succesfully!\n");
            }
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    
    /** MAIN APP */
    public static void main(String[] args) {
        String path = "";
        int port = 0;
        try {
            path = args[0];
            port = Integer.parseInt(args[1]);
        }catch(ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Run: java Peer path_name port");
            return;
        }
        //start a new peer
        new Peer("192.168.2.2",port,path,6000);
    }//main

}