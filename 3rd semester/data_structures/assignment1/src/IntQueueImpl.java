import java.io.PrintStream;
import java.util.NoSuchElementException; 


public class IntQueueImpl implements IntQueue {
	private Node head,tail;
	private int counter = 0; //we will use it in order to compute the size of the queue
	private class Node{
		int item;
		Node next;
		Node(int item){
			this.item = item;
			next = null;
		}
	}
	
	public IntQueueImpl(){
		head = null;
		tail = null;
	}
	/**
	 * @return true if the queue is empty
	 */
	public boolean isEmpty(){	//true if we have no elements in the queue
		return (head==null);
	}
	/**
	 * insert an integer to the queue
	 */
	public void put(int item){
		Node t = tail;
		tail = new Node(item);
		if (isEmpty()) 
			head = tail;
		else 
			t.next = tail;
		counter++;
	}
	/**
 	 * remove and return the oldest item of the queue
 	 * @return oldest item of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	
	public int get() throws NoSuchElementException{
		if (this.isEmpty())
			throw new NoSuchElementException();
		int v = head.item;								// remove and return the oldest item of the queue
		Node t = head.next;
		head = t;
		counter--;
		return v;
	}
	/**
	 * return without removing the oldest item of the queue
 	 * @return oldest item of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	
	public int peek() throws NoSuchElementException{
		if (this.isEmpty())								//this method returns the oldest item of the queue 
			throw new NoSuchElementException();			// without removing it
		return head.item;
	}

	
	/**
	 * print the elements of the queue, starting from the oldest 
         * item, to the print stream given as argument. For example, to 
         * print the elements to the
	 * standard output, pass System.out as parameter. E.g.,
	 * printQueue(System.out);
	 */
	public void printQueue(PrintStream stream){
		if (isEmpty())
			System.out.println("Queue is empty");
		else{
			Node current = head;
			while (current!=null){
				System.out.printf("%s\n",current.item);
				current = current.next;
			}
		}
	}
	// In order to keep the complexity of the algorithm low we have put a 
	//counter to count the nodes of the queue and return the plethora of items
	public int size(){
		return counter;
	}
}

