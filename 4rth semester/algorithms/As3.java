import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class As3 {
    public static void main(String[] args) throws IOException{
        String from  = args[1];
        String to = args[2];
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String line;
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        HashMap<String, Integer> pairs = new HashMap<>();
        HashMap<Integer, String> pairs_inv = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
        int count = 0;
        while((line = br.readLine()) != null && !line.isEmpty()){
            String[] values = line.split("\t");
            if(!map.containsKey(values[0])){
                map.put(values[0], new ArrayList<>());
                names.add(values[0]);
                pairs.put(values[0], count);
                pairs_inv.put(count, values[0]);
                count++;
            }
            if(!map.containsKey(values[1])) {
                map.put(values[1], new ArrayList<>());
                names.add(values[1]);
                pairs.put(values[1], count);
                pairs_inv.put(count, values[1]);
                count++;
            }
            map.get(values[0]).add(values[1]);
            map.get(values[1]).add(values[0]);
        }
        boolean[] marked = new boolean[names.size()];
        int[] edgeTo = new int[names.size()];
        int[] distTo = new int[names.size()];
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < names.size(); i++) {
            distTo[i] = Integer.MAX_VALUE;
        }
        distTo[pairs.get(from)] = 0;
        marked[pairs.get(from)] = true;
        q.add(pairs.get(from));
        while (!q.isEmpty()) {
            int v = q.remove();
            for (String w : map.get(pairs_inv.get(v))) {
                if (!marked[pairs.get(w)]) {
                    edgeTo[pairs.get(w)] = v;
                    distTo[pairs.get(w)] = distTo[v] + 1;
                    marked[pairs.get(w)] = true;
                    q.add(pairs.get(w));
                }
            }
        }
        if (!names.contains(from)) {
            System.out.println(from + " is not in the database.");
        }
        else {
            if(names.contains(to)) {
                if(marked[pairs.get(to)]) {
                    Stack<Integer> s_path = new Stack<Integer>();
                    int x;
                    for (x = pairs.get(to); distTo[x] != 0; x = edgeTo[x]) {
                        s_path.push(x);
                    }
                    s_path.push(x);
                    ArrayList<String> f_path = new ArrayList<>();
                    for (Integer i : s_path) {
                        f_path.add(pairs_inv.get(i));
                    }
                    Collections.reverse(f_path);
                    System.out.print("The path to philosopher " + to + " is: ");
                    IntStream.range(0, f_path.size()).forEach(i -> System.out.print(f_path.get(i) + (i != (f_path.size() - 1) ? " -> " : "\n")));
                }
                else{
                    System.out.println("Not Connected");
                }
            }
            else{
                System.out.println(to + " is not in the database.");
            }
        }
    }
}
