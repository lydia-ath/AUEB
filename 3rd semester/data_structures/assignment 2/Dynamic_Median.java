
import java.io.*;
public class Dynamic_Median {

	public static void main(String[] args) {
		int lineCounter = 0; //to count the lines
		int id, likes;
		File f = null;
		BufferedReader reader = null;
		String line, title;
		PQ bigger = new PQ();
		// The two queues 
		bigger = new PQ(1, bigger.new DefaultComparator2());
		PQ smaller = new PQ(1, bigger.new DefaultComparator());
		// The median 
		Song median = null;
		
		/* To check if the user has given the file path
		   If not, the program terminates */
		try {
			if (args.length!=1)
				throw new ArrayIndexOutOfBoundsException();
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.err.println("File not given");
			return;	//to terminate the program
		}
		
		/* To check if the file is found
		   If not, the program terminates */
		try{
			f = new File(args[0]);	
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
		/* To read the file, line per line
		 * It catches the three characteristics of a Song(one per line)
		 * The Song is then added in a linked list, which is later used to sort the songs using the Merge Sort algorithm*/
		try {
			line = reader.readLine();
			while (line!=null) {
				lineCounter++;
				id = Integer.parseInt(line.substring(0, line.indexOf(" ")));
				title = line.substring(line.indexOf(" ")+1, line.lastIndexOf(" "));
				likes = Integer.parseInt(line.substring(line.lastIndexOf(" ")+1));
				// If the song is valid, do the followings, else print what went wrong
				if (id>=1 && id<=9999 && title.length()<80) {
					Song a = new Song(id, title, likes);
					if (a.getID()==0) {
						System.out.println("The id of the song "+title+" is invalid");
					}
					else {
						// Add the valid song to the right priority queue
						if (smaller.isEmpty() || a.compareTo(smaller.max())<0)
							smaller.insert(a);
						else
							bigger.insert(a);
						
						/* The size difference of the two queues should be no more than 1 
						 * so rebalance them if necessary*/
						if (smaller.size() == bigger.size() + 2)
							bigger.insert(smaller.getmax());
						else if (bigger.size() == smaller.size() + 2)
							smaller.insert(bigger.getmax());
					}
				}
				else if(title.length()>=80)
					System.out.println("The title's length of the song "+title+" is bigger than it should");
	
				// Every 5 lines, median is calculated and printed
				if (lineCounter%5==0){
					// Calculate median
					if (bigger.size() == smaller.size())
						median = (bigger.max().compareTo(smaller.max())>0) ? bigger.max() : smaller.max();
					else
						median = bigger.size()>smaller.size() ? bigger.max() : smaller.max();
					// Print median
					if (median!=null)
						System.out.println("\nMedian = "+median.getLikes()+" likes, achieved by Song: "+median.getTitle());
				}
				line = reader.readLine();
			}
		}
		catch(IOException e){
			System.err.println("Error reading line "+lineCounter);
			return;
		}
		catch(NumberFormatException j) {
			System.err.println("Error reading line.");
			return;
		}
	}
}
