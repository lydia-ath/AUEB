import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class As1 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        ArrayList<Integer> list = new ArrayList<>();
        String line;
        while((line = br.readLine()) != null && (!line.isEmpty())){
            String[] values = line.split(" ");
            IntStream.range(0, values.length).forEach(i -> list.add(Integer.parseInt(values[i])));
        }
        Integer arr[] = new Integer[list.size()];
        arr = list.toArray(arr);
        int find = Integer.parseInt(args[1]);
        int freq = bs(arr, find);
        System.out.println("Integer: " + find + " was found: " + ((freq == -1) ? 0 : freq) + " times");
    }

    private static int bs(Integer[] arr, int find) {
        int size = arr.length;
        int i, j;
        i = begin(arr, 0, size-1, find, size);
        if(i == -1) return i;
        j = end(arr, i, size-1, find, size);
        return j - i + 1;
    }

    private static int begin(Integer[] arr, int low, int high, int find, int size) {
        if(high >= low){
            int mid = (low + high)/2;
            if((mid == 0 || find > arr[mid-1]) && arr[mid] == find){
                return mid;
            }
            else if(find > arr[mid]){
                return begin(arr, (mid+1), high, find, size);
            }
            else{
                return begin(arr, low, (mid-1), find, size);
            }
        }
        return -1;
    }

    private static int end(Integer[] arr, int low, int high, int find, int size) {
        if(high >= low){
            int mid = (low + high)/2;
            if((mid == (size-1) || find < arr[mid+1]) && arr[mid] == find){
                return mid;
            }
            else if(find < arr[mid]){
                return end(arr, low, (mid-1), find, size);
            }
            else{
                return end(arr, (mid+1), high, find, size);
            }
        }
        return -1;
    }

}
