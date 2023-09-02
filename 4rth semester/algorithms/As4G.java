import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class As4G {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String line;
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
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
        names.sort(Comparator.comparing(i -> map.get(i).size()).reversed());
        for (int k = 0; k < names.size(); k++) {
            solution.add(names.get(k));
            for (int i = 0; i < names.size(); i++) {
                if (names.get(k).equalsIgnoreCase(names.get(i))) continue;
                ArrayList<String> temp = map.get(names.get(i));
                for (int j = 0; j < temp.size(); j++) {
                    if (temp.contains(names.get(k))) temp.remove(names.get(k));
                }
                map.put(names.get(k), temp);
            }
            int sum = 0;
            for (int i = 0; i < names.size(); i++) {
                if (map.get(names.get(i)).size() == 0) sum++;
            }
            if (sum == names.size()) break;
        }
        System.out.println("The minimum amount of users needed is: " + solution.size());
    }
}
