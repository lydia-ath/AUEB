
import java.io.*;
public class Top_k {
	
	// this field is created to be able to make instances of List in main
	static Top_k top_k = new Top_k();

	/* Linked List */
	public class List {
		
		// Node of the list, each one has one Song element
		public class ListNode{
			protected Song element;
			protected ListNode next;
			
			// Constructors
			ListNode(){
				this(null, null);
			}
			ListNode(Song element){
				this(element, null);
			}
			ListNode(Song element, ListNode node){
				this.element = element;
				this.next = node;
			}
			
			// Getters
			Song getElement(){
				return this.element;
			}
			ListNode getNext(){
				return this.next;
			}
		}//end of ListNode class
		
		// Exception in case the list is empty
		private class EmptyListException extends RuntimeException{
			public EmptyListException(){
				this("Empty");
			}
			public EmptyListException( String name ){
				super(name + " is empty");
			} 
		}//end of EmptyListException class

		private ListNode first; 
		private ListNode last; 
		private String name;
		
		// Constructors
		public List(){
			this("list");
		}
		public List( String listName){
			this.name = listName;
			this.setFirst(this.setLast(null));
		}
		
		// Getters
		public ListNode getFirst() {
			return first;
		}
		public ListNode getLast() {
			return last;
		}
		//Setters
		public void setFirst(ListNode first) {
			this.first = first;
		}
		public ListNode setLast(ListNode last) {
			this.last = last;
			return last;
		}

		//Inserts element at back of the list
		public void insertAtBack(Song element){
			ListNode node = new ListNode(element);
			if (this.isEmpty()){
				this.setFirst(this.setLast(node));
			}
			else {
				this.getLast().next = node;
				this.setLast(node);
			}
		} 
		//Removes the first element of the list and returns it
		public Song removeFromFront() throws EmptyListException{
			if (this.isEmpty()){
				throw new EmptyListException(this.name);
			}
			Song deleted = this.getFirst().element;
			if (this.getFirst()==this.getLast()){
				this.setFirst(this.setLast(null));
			}
			else {
				this.setFirst(this.getFirst().next);
			}
			return deleted;
		}
		
		//Returns true if the list is empty
		public boolean isEmpty(){
			if (this.getFirst()==null)
				return true;
			return false;
		} 
		//Returns the size of the list
		public int size() {
			ListNode temp = this.getFirst();
			int counter = 0;
			while(temp!=null) {
				counter++;
				temp = temp.getNext();
			}
			return counter;
		}
		
		//Prints the elements of the list (the titles of the Songs)
		public void print(){
			if ( isEmpty() ){
				System.out.printf( "Empty %s\n", name );
				return;
			}
			ListNode current = getFirst();
			while (current != null){
				System.out.println(current.getElement().getTitle());
				current = current.getNext();
			}
			System.out.println( "\n" );
			return;
		} 
	}
	
	public static void main(String[] args) {
		int k = 0; 
		int id, likes;
		File f = null;
		BufferedReader reader = null;
		String line, title;
		List list = top_k.new List();
		
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
		
		/* To read the file, line per line
		 * It catches the three characteristics of a Song(one per line)
		 * The Song is then added in a linked list, which is later used to sort the songs using the Merge Sort algorithm*/
		try {
			line = reader.readLine();
			while (line!=null) {
				id = Integer.parseInt(line.substring(0, line.indexOf(" ")));
				title = line.substring(line.indexOf(" ")+1, line.lastIndexOf(" "));
				likes = Integer.parseInt(line.substring(line.lastIndexOf(" ")+1));
				
				// If the song if valid insert it in List, else print what went wrong
				if (id>=1 && id<=9999 && title.length()<80) {
					Song a = new  Song(id, title, likes);
					if (a.getID()==0) {
						System.out.println("The id of the song "+title+" is invalid");
					}
					else
						list.insertAtBack(a);
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
		 * else sort the list with Merge Sort Algorithm and print the top k songs*/
		if (k>list.size())
			System.out.println("\nThe number of songs in the file is smaller than the number of songs expected to be sorted");
		else if (k!=0) {
			list = mergesort(list);
			System.out.println("\nThe top "+k+" " + ((k>1) ? "songs are: " : "song is: "));
			for (int i=0; i<k; i++) {
				System.out.println(list.removeFromFront().getTitle());
			}
		}
	} // end of main
	
	
	// Merge Sort Algorithm achieved through two functions (mergesort + merge)
	
	/* It returns the list sorted calling the merge function 
	 * It divides the list into two lists, and with recursion each list is divided until the 
	 * there's only 1 element in each of the sublists.
	 * Then the merge function is called to connect the two sorted halves of the list*/
	public static List mergesort(List list) {
		// if list is empty or has only one element, then it is considered as sorted
		if (list.getFirst()==null || list.getFirst()==list.getLast())
			return list;
		List right = top_k.new List();
		List left = top_k.new List();
		int size = list.size();
		for (int i=0; i<size;i++) {
			if (i<list.size()/2+1)
				left.insertAtBack(list.removeFromFront());
			else
				right.insertAtBack(list.removeFromFront());
		}
		left = mergesort(left);
		right = mergesort(right);
		return merge(right, left);
	}
	/* It gets two lists as parameters, and connects them to return a sorted list, 
	 * based on the comparison of the elements(Song type) using compareTo function*/
	public static List merge(List right, List left){
		List result = top_k.new List();
		while (!left.isEmpty() && !right.isEmpty()) {
			if (left.getFirst().getElement().compareTo(right.getFirst().getElement())>0)
				result.insertAtBack(left.removeFromFront());
			else
				result.insertAtBack(right.removeFromFront());
		}
		// only one while will run
		while (!left.isEmpty())
			result.insertAtBack(left.removeFromFront());
		while (!right.isEmpty())
			result.insertAtBack(right.removeFromFront());
		return result;
	}
} // end of class Top_k
