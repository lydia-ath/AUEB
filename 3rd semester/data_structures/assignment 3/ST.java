
import java.io.*;

public class ST {
	private class TreeNode{
		WordFreq item;
		TreeNode l, r, p;
		int number = 0;
		
		// Constructor
		TreeNode(WordFreq wordfreq){
			if (wordfreq == null)
				throw new IllegalArgumentException();
			item = wordfreq;
			l = null;
			r = null;
			number = 0;
		}
		
		// for number
		public int getNumber() {
			return number;
		}
		public void setNumber(int num) {
			this.number = num;
		}
		public void increaseBy(int num) {
			number += num;
		}
		public void decreaseBy(int num) {
			if (num>number) {
				number = 0;
				return;
			}
			number -= num;
		}
	}// end of class TreeNode
	
	
	private class List{
		private class ListNode{
			protected String element; 
			protected ListNode next; 
			
			// Constructors
			ListNode( String element ){
				this(element, null);
			}
			ListNode( String element, ListNode node ){
				this.element = element;
				this.next = node;
			} 
			String getElement(){
				return this.element;
			}
			ListNode getNext(){
				return this.next;
			} 
		} // end of class ListNode
		
		private class EmptyListException extends RuntimeException{
			public EmptyListException(){
				this( "List" ); 
			} 
			public EmptyListException( String name ){
				super( name + " is empty" ); 
			} 
		} // end is class EmptyListException
		
		private ListNode first;
		
		// Constructor
		public List(){
			this.first = null;
		}
		public void insertAtFront( String element ){
			ListNode node = new ListNode(element); 
			if (this.isEmpty()){
				this.first = node; 
			}
			else{
				node.next = this.first;
				this.first = node;
			}
		} 
		
		//returns true if str exists in the list
		public boolean contains(String str) {
			if (isEmpty()) return false;
			return indexOf(str)!=-1;
		}
		// returns the position of str in list, -1 if not found
		public int indexOf(String str) {
			if (str==null) return -1;
			ListNode current = first;
			int index = 0;
			while(true) {
				if (current==null) break;
				if (str.equalsIgnoreCase(current.element)) {
					return index;
				}
				current = current.next;
				index++;
			}
			return -1;
		}
		// to remove an element in a specific position of the list
		public void remove(int index) {
			// if it's empty
			if (isEmpty()) return;
			ListNode temp = first;
			// if the head is removed
			if (index==0) {
				first = temp.next;
				return;
			}
			for (int i=0; i<index-1; i++) {
				if (temp==null) break;
				temp = temp.next;
			}
			if (temp == null || temp.next == null)
				return;
			ListNode next = temp.next.next;
			temp.next = next;
		}
		public boolean isEmpty(){
			if (this.first==null) 
				return true;
			return false;
		}
		public void print(){
			if ( isEmpty() ){
				System.out.printf("List is Empty");
				return;
			}
			System.out.printf( "The list is Empty");
			ListNode current = first;
			while ( current != null ){
				System.out.printf( "%s ", current.element );
				current = current.next;
			}
			System.out.println( "\n" );
		} 
	} // end class List

	
	private TreeNode head; // root of tree
	private List stopwords = new List(); // list of stopwords
	private int size;
	
	// Default Constructor
	public ST() {
		head = null;
		size = 0;
	}
	
	public WordFreq search(String w) {
		TreeNode h = head;
		int result = 0;
		while (h != null) {
			result = w.compareToIgnoreCase(h.item.key());
			// if the word is found
			if (result == 0) {
				WordFreq found = h.item;
				if (found.getFrequency() > getMeanFrequency()) {
					remove(found.key());
					size++;
					head = insertT(head, found);
				}
				return found;
			}
			h = result < 0 ? h.l : h.r;
		}
		// if not found or list is empty
		return null;
	}
	
	private TreeNode insertT(TreeNode h, WordFreq x) {
		if (h == null) return new TreeNode(x);
		if (x.key().compareToIgnoreCase(h.item.key())<0) {
			h.l = insertT(h.l, x); 
			h = rotR(h); 
		} 
		else {
			h.r = insertT(h.r, x);
			h = rotL(h); 
		}
		h.increaseBy(1);
		return h; 
	} 
	
	public void insert(WordFreq item) {
		if (item == null) throw new IllegalArgumentException();
        TreeNode n = head;
        TreeNode p = null;
        int result = 0;
        while (n != null) {
            // Compare item with the item in the current subtree
            result = item.key().compareToIgnoreCase(n.item.key());
            if (result == 0) { // if item already exists in the tree
            	update(item.key());
            	return;
            }
            // Go left or right based on comparison result
            // Keep a reference in the last non
            // node encountered
            p = n;
            n = result < 0 ? n.l : n.r;
        }
        // Create and connect a new node
        TreeNode node = new TreeNode(item);
        node.p = p;
        // The new node must be a left child of p
        if (result < 0) {
            p.l = node;
            p.increaseBy(1);
        }
        // The new node must be a right child of p
        else if (result > 0) {
            p.r = node;
            p.increaseBy(1);
        }
        // The tree is empty; head must be set
        else {
            head = node;
        }
        size++;
	}
	
	public void update(String w) {
		WordFreq word = search(w);
		// if word exists update the frequency by one
		if (word!=null) {
			word.setFrequency(word.getFrequency()+1);
		}
		else { // if word doesn't exists insert it 
			insert(new WordFreq(w,1));
		}
			
	}
	
	public void remove(String w) {
		head = removeR(head, w);
		size--;		
		head.decreaseBy(1);

	}
	private TreeNode removeR(TreeNode node, String key) {
		if (key==null || node==null)
			return null;
		String w = node.item.key();
		if (key.compareToIgnoreCase(w)<0)
			node.l = removeR(node.l, key);
		else if (w.compareToIgnoreCase(key)<0)
			node.r = removeR(node.r, key);
		else
			node = joinLR(node.l, node.r);
		return node;
	}
	
	private TreeNode joinLR(TreeNode l, TreeNode r) {
		if (r==null)
			return l;
		r = partR(r, 0);
		r.l = l;
		return r;
	}
	private TreeNode partR(TreeNode h, int k) {
		int t = (h.l==null) ? 0 : h.l.item.getFrequency();
		if (t>k) {
			h.l = partR(h.l, k);
			h = rotR(h);
		}
		if (t<k) {
			h.r = partR(h.r, k-t-1);
			h = rotL(h);
		}
		return h;
	}
	private TreeNode rotR(TreeNode h) {
		TreeNode x = h.l;
		h.l = x.r;
		x.r = h;
		h.setNumber(NumRot(h));
		return x;
	}
	private TreeNode rotL(TreeNode h) {
		TreeNode x = h.r;
		h.r = x.l;
		x.l = h;
		h.setNumber(NumRot(h));
		return x;
	}
	private int NumRot(TreeNode node) {
		if (node==null) return 0;
		return (1 + NumRot(node.l) + NumRot(node.r));
	}

	public void load(String filename) {
		System.out.println("in load");
		File f; BufferedReader reader; String line;
		try{
			f = new File(filename);
			System.out.println(f.getAbsolutePath());
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
		try {
			line = reader.readLine();
			while (line!=null) {
				for (String word : line.split(" ")) {
					// if it's in the stopwords list continue
					if (stopwords.contains(word))
						continue;
					
					// Remove all special characters except '
					word = word.replaceAll("[^A-Za-z0-9']*", "");
					// If it's a valid word
					if (java.util.regex.Pattern.matches("[A-Za-z]\\w+[']\\w+|[\\sA-Za-z\\s]+|['][A-Za-z]\\w+[']\\w+|[A-Za-z]\\w+[']\\w+[']|['][A-Za-z]\\w+[']\\w+[']", word)){
						// if it's in form 'ab'c remove the first '
						if (java.util.regex.Pattern.matches("['][A-Za-z]\\w+[']\\w+", word)) {
							word = word.substring(1, word.length());
						}
						// if it's in form ab'c' remove the last '
						else if (java.util.regex.Pattern.matches("[A-Za-z]\\w+[']\\w+[']", word)) {
							word = word.substring(0, word.length()-1);
						}
						// if it's in form 'ab'c' remove the first and the last '
						else if (java.util.regex.Pattern.matches("['][A-Za-z]\\w+[']\\w+[']", word)) {
							word = word.substring(1, word.length()-1);
						}
						// insert the word into the ST
						insert(new WordFreq(word, 1));
					}
				}
				line = reader.readLine();
			}
		}
		catch(IOException e){
			System.err.println("Error reading line.");
			return;
		}
		return;
	}
	
	// returns the number of the words read 
	public int getTotalWords() {
		return findAll(head);
	}
	private int findAll(TreeNode h) {
		if (h==null) return 0;
		return (h.item.getFrequency()+findAll(h.l)+findAll(h.r));
	}
	
	// returns the number of the distinct words read
	public int getDistinctWords() {
		return size;
	}
	
	// returns the frequency of the word w
	public int getFrequency(String w) {
		WordFreq a = search(w);
		return (a==null) ? 0 : a.getFrequency();
	}

	// returns the maximum frequency
	public WordFreq getMaximumFrequency() {
		WordFreq max = new WordFreq();
		max = findMaxFreq(head, max);
		return max;
	}
	private WordFreq findMaxFreq(TreeNode node, WordFreq max) {
		if (node==null) return max;
		findMaxFreq(node.l, max);
		if (max.getFrequency()<node.item.getFrequency()) 
			max = node.item;
		return findMaxFreq(node.r, max);
	}
	
	// finds the average frequency of all the distinct words
	public double getMeanFrequency() {
		return (double)getTotalWords()/getDistinctWords();
	}
	
	public void addStopWord(String w) {
		stopwords.insertAtFront(w);
	}
	public void removeStopWord(String w) {
		int index = stopwords.indexOf(w);
		System.out.println(index);
		stopwords.remove(index);
	}
	
	// to print the tree alphabetically
	public void printÔreeAlphabetically(PrintStream stream) {
		inorder(stream); 
	}
	private void inorder(PrintStream stream) {
	    inorder(head, stream);
	}
	private void inorder(TreeNode node, PrintStream stream) {
		if (node==null) return;
	    inorder(node.l, stream);
	    stream.println(node.item.toString());
	    inorder(node.r, stream);
	}
	
	// to print the tree by frequency
	public void printÔreeByFrequency(PrintStream stream) {
		//create new ST 
		ST byfreq = new ST();
		TreeNode n = head;
		ST c = this;
		// with inorderFreq copy this to byfreq using different compare system
		byfreq.inorderFreq(c);
		// print byfreq
		byfreq.inorder(byfreq.head, stream);
	}
	
	// to insert in tree by frequency and alphabetical order
	private void inorderFreq(ST c) {
		inorderFreq(c.head);
	}
	private void inorderFreq(TreeNode node) {
		if (node==null) return;
		inorderFreq(node.r);
		insertByFreq(node.item);
		inorderFreq(node.l);
	}
	// Insert item by frequency and alphabetical order
	private void insertByFreq(WordFreq item) {
		if (item == null) throw new IllegalArgumentException();
        TreeNode n = head;
        TreeNode p = null;
        int result = 0;
        while (n != null) {
            // Compare item with the item in the current subtree
        	result = compareTo(item,n.item);
            if (result==0) { // if item already exists in the tree
	            	update(item.key());
	            	return;
            }
            // Go left or right based on comparison result
            // Keep a reference in the last non
            // node encountered
            p = n;
            n = result<0 ? n.l : n.r;
        }
        // Create and connect a new node
        TreeNode node = new TreeNode(item);
        node.p = p;
        // The new node must be a left child of p
        if (result<0) {
            p.l = node;
            p.increaseBy(1);
        }
        // The new node must be a right child of p
        else if (result>0) {
            p.r = node;
            p.increaseBy(1);
        }
        // The tree is empty; head must be set
        else {
            head = node;
        }
        size++;
	}
	// made for printTreeByFrequency method 
	private int compareTo(WordFreq one, WordFreq two) {
		if (one.getFrequency()==two.getFrequency())
			return -two.key().compareToIgnoreCase(one.key());
		return two.getFrequency()>one.getFrequency() ? 1 : -1;
	}
}
