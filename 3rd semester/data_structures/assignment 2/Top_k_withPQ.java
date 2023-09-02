
import java.io.*;
import java.util.Comparator;
public class Top_k_withPQ {
	
	public static void main(String[] args) {
		int k = 0; 
		int id, likes;
		File f = null;
		BufferedReader reader = null;
		String line, title;
		PQ queue;
		
		/* To check if the user has given both variable and the file path
		   If not, the program terminates */
		try {
			if (args.length!=2)
				throw new ArrayIndexOutOfBoundsException();
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.err.println("Variable k or file not given");
			return;	//to terminate the program
		}
		
		/* To check if the user has given String k that can be converted to a number
		   If not, the program terminates */
		try {
			k = Integer.parseInt(args[0]);
		}
		catch (NumberFormatException e) {
			System.err.println("Invalid variable k.");
			return;
		}
		
		/* To check if the file is found
		   If not, the program terminates */
		try{
			f = new File(args[1]);	
		}
		catch (NullPointerException e){					
			System.err.println ("File not found.");	
			return;
		}
		
		/* To check if the file opens without throwing an exception
		   If not, the program terminates */
		try{
			reader = new BufferedReader(new FileReader(f));
		}
		catch (FileNotFoundException e ){				
			System.err.println("Error opening file!");  
			return;
		}
		queue = new PQ();
		Comparator cmp = queue.new DefaultComparator();
		queue = new PQ(k, cmp);
		/* To read the file, line per line
		 * It catches the three characteristics of a Song(one per line)
		 * The Song is then added in a linked list, which is later used to sort the songs using the Merge Sort algorithm*/
		try {
			line = reader.readLine();
			while (line!=null) {
				id = Integer.parseInt(line.substring(0, line.indexOf(" ")));
				title = line.substring(line.indexOf(" ")+1, line.lastIndexOf(" "));
				likes = Integer.parseInt(line.substring(line.lastIndexOf(" ")+1));

				// If the song is valid do the followings, else print what went wrong
				if (id>=1 && id<=9999 && title.length()<80) {
					Song a = new Song(id, title, likes);
					if (a.getID()==0) {
						System.out.println("The id of the song "+title+" is invalid");
					}
					else {
						/* If size of queue equals k, then remove the element with the least priority 
						   from the heap, and then insert the new song */
						if (queue.size() == k) {
							if (cmp.compare(a, queue.findLast())>0) { 
								queue.getMin();
								queue.insert(a);
							}
						}
						else
							queue.insert(a);
					}
				}
				else if(title.length()>=80)
					System.out.println("The title's length of the song "+title+" is bigger than it should");					
				
				line = reader.readLine();
			}
		}
		catch(IOException e){
			System.err.println("Error reading line.");
			return;
		}
		catch(NumberFormatException j) {
			System.err.println("Error reading line.");
			return;
		}
		
		/* If the songs are not enough to make a list with the top k songs print a relevant message
		 * else print the top k songs from the priority queue*/
		if (k>queue.size())
			System.out.println("\nThe number of songs in the file is smaller than the number of songs expected to be sorted");
		else if (k!=0){
			System.out.println("\nThe top "+k+" " + ((k>1) ? "songs are: " : "song is: "));
			for (int i=0; i<k; i++) {
				System.out.println(queue.getmax().getTitle());
			}
		}
	}
}
