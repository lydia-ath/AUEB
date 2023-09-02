import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class As4BF {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String line;
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        ArrayList<String> solution = new ArrayList<>();
        while((line = br.readLine()) != null && !line.isEmpty()){
            String[] values = line.split("\t");
            if(!map.containsKey(values[0])){
                map.put(values[0], new ArrayList<>());
                names.add(values[0]);
            }
            if(!map.containsKey(values[1])){
                map.put(values[1], new ArrayList<>());
                names.add(values[1]);
            }
            map.get(values[0]).add(values[1]);
            map.get(values[1]).add(values[0]);
        }
        for (int k = 0; k < names.size(); k++) {
            ArrayList<String> temp = new ArrayList<>();
            ArrayList<String> pass = new ArrayList<>();
            for (int i = 0; i < map.size(); i++) {
                if (!temp.contains(names.get(i))) {
                    temp.add(names.get(i));
                    pass.add(names.get(i));
                }
                if (map.get(names.get(i)).size() == k) {
                    ArrayList<String> connections = map.get(names.get(i));
                    for (int j = 0; j < connections.size(); j++) {
                        if (!temp.contains(connections.get(j))) {
                            temp.add(connections.get(j));
                        }
                    }
                }
            }
            if (temp.size() == names.size()) {
                if (pass.size() < min) {
                    min = temp.size();
                    solution = pass;
                }
            }
        }
        System.out.println("The minimum amount of users needed is: " + solution.size());
    }
}
