import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringStackImpl implements StringStack {
	private Node head;
	private int counter; //in order to compute the size of the stack
	private class Node{
		String item;
		Node next;
		Node(String item, Node next){
			this.item=item; this.next=next;
		}
	}
	
	public StringStackImpl(){ //constructor 
		head = null;
	}
	
     /**
	 * @return true if the stack is empty
	 */
	public boolean isEmpty(){
		return (head == null);		//a stack is empty when the head=null(=tail)
	}								//true if we have no elements in the stack
	/**
	 * insert a String item to the stack
	 */
	public void push(String item){  //when we want to add an item to the stack
		head= new Node(item, head); //we call this method
		counter++;
	}

	/**
	 * remove and return the item on the top of the stack
	 * @return the item on the top of the stack
	 * @throws a NoSuchElementException if the stack is empty
	 */
	public String pop() throws NoSuchElementException{
		if(this.isEmpty()){
			throw new NoSuchElementException();	
		}												//when we want to remove an element from the stack we 
		String v = head.item;							//call this method
		Node t= head.next;
		head=t;
		counter--;
		return v;
	}
       /**
	 * return without removing the item on the top of the stack
	 * @return the item on the top of the stack
	 * @throws a NoSuchElementException if the stack is empty
	 */
	public String peek() throws NoSuchElementException{
		if(this.isEmpty()){							
			throw new NoSuchElementException();			//this method is being used in order to check the top item of the stack
		}
		String v= head.item;
		return v;
	}

	/**
	 * print the elements of the stack, starting from the item
         * on the top,
	 * to the stream given as argument. For example, 
	 * to print to the standard output you need to pass System.out as
	 * an argument. E.g., 
	 * printStack(System.out); 
	 */
	public void printStack(PrintStream stream){
		if(this.isEmpty()){
			System.out.println("The stack is empty");
		}
	Node current=head;
	while ( current != null ){
		System.out.printf( "%s ", current.item );
			current = current.next;
	}
	System.out.println( "\n" );
	}
	/**
     * return the size of the stack, 0 if it is empty
	 * @return the number of items currently in the stack
	 */
	public int size(){
		return counter;	//we use the counter in order the keep the complexity of
						//the algorithm low  (O(1))
	}

}
