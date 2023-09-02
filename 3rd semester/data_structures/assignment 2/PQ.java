
import java.util.Comparator;
public class PQ {
	
	// Comparators
	public final class DefaultComparator implements Comparator{
		@Override
		public int compare(Object a, Object b) {
			return ((Comparable)a).compareTo((Comparable)b);
		}
	}
	// To define the priority to be the opposite of the DefaultComparator (used only in Part D)
	public final class DefaultComparator2 implements Comparator{
		@Override
		public int compare(Object a, Object b) {
			return -((Comparable)a).compareTo((Comparable)b);
		}
	}
	
	private Song[] heap;
	
	//The number of Songs in the heap
    private int size;
    		    
    // The Comparator that defines the priority of the queue
	private Comparator cmp;
	
	// Array used to be able to find a song in heap using its id in O(logn)
	private Song[] id_array = new Song[9999];

	// Constructors
    public PQ() {
        this.cmp = new DefaultComparator();
    }
    public PQ(int capacity, Comparator cmp) {
        if (capacity < 1) throw new IllegalArgumentException();
        this.cmp = cmp;
        this.heap = new Song[capacity+1];
        this.size = 0;
    }
    
    
    /* Inserts a song in this heap. O(logn)
     * throws IllegalStateException if heap capacity is exceeded.*/
    public void insert(Song song) {
        // Ensure Song is not null
        if (song == null) throw new IllegalArgumentException();
        if (size >= 0.75*heap.length-1) resize();
        // Check available space
        if (size == heap.length-1) throw new IllegalStateException();
        // Place Song at the next available position
        heap[++size] = song;
        // Let the newly added Song swim
        swim(size);
        id_array[song.getID()] = song;
    }
    // Doubles the size of the heap O(n)
	private void resize() {
		Song[] newHeap = new Song[heap.length * 2];
		// copy elements that are logically in the Queue
		for (int i = 0; i < heap.length; i++)
			newHeap[i] = heap[i];
		heap = newHeap;
	}
	
	
	/* Removes song that exists in position index in heap O(logn)
	 * throws new IllegalStateException if heap is empty. */
	public void removeAt(int index) {
		if (size == 0) throw new IllegalStateException();
		heap[index] = heap[size];
		size--;
		int parent = index/2;
		// In case there was only one element in the queue do nothing else
		if (size!=0) {
			if (cmp.compare(heap[parent], heap[index])<0)
				swim(index);
			else
				sink(index);
		}
	}
	/* Removes and returns song from heap based on its id O(logn)
	 * throws new IllegalStateException if heap is empty.*/
	public Song remove(int id) {
		if (isEmpty()) throw new IllegalStateException();
		// The song to be removed
		Song removed = id_array[id];
		// Find index of song to be removed in heap
		int indexOfRemoved = binarySearch(heap, 1, size, removed);
		if (indexOfRemoved!=-1)
			removeAt(indexOfRemoved);
		else
			System.out.println("Song with id "+id+" not found.");
		return removed;
	}
	// A variant of the Binary Search Algorithm to find target in heap and return its index O(logn)
	private int binarySearch(Song[] arr, int left, int right, Song target) {
		if (right>=left) {
			int mid = left+(right-1)/2;
			if (cmp.compare(arr[mid], target)==0)
				return mid;
			if (mid>left && cmp.compare(arr[mid-1],target)==0)
				return mid-1;
			if (mid<right & cmp.compare(arr[mid+1],target)==0)
				return mid+1;
			if (cmp.compare(arr[mid], target)<0)
				return binarySearch(arr, left, mid-2, target);
			return binarySearch(arr, mid+2, right, target);
		}
		return -1;
	}

	/* Removes and returns the song at the root of this heap,
	 * the element with the highest priority O(logn)
	 * throws new IllegalStateException if heap is empty.*/
    public Song getmax() {
        // Ensure not empty
        if (size == 0) throw new IllegalStateException();
        // Keep a reference to the root Song
	    Song removed = heap[1];
        // Replace root Song with the one at rightmost leaf
        if (size > 1) heap[1] = heap[size];
        // Dispose the rightmost leaf
        heap[size--] = null;
        // Sink the new root element
        sink(1);
        // Return the Song removed
        return removed;
    }
    /* Removes the song with the least priority O(n)
     * throws new IllegalStateException if heap is empty.*/
    public void getMin() {
		if (isEmpty()) throw new IllegalStateException();
		Song minimum = findLast();
		for (int i=size; i>=1; i--) {
			if (heap[i]==minimum) {
				removeAt(i);
				break;
			}
		}
	}

    
	/* Returns the Song with the highest priority O(1)
	 * throws new IllegalStateException if heap is empty.*/
	public Song max() {
		if (isEmpty()) throw new IllegalStateException();
		return heap[1];
	}
	 
	/* Returns Song with the least priority O(n)*/
	 public Song findLast() {
		 // Copy of the heap
		 Song[] copiedHeap = new Song[heap.length];
		 for (int i=1; i<=size; i++) 
			 copiedHeap[i] = heap[i];
		 // Sort the copy of the heap
		 for (int i=1; i<size; i++) 
			 if (copiedHeap[i+1].compareTo(copiedHeap[i])>0) 
				 swap(copiedHeap, i, i+1);
		 /* The element with the least priority will be 
		  * the last element of the copied array, so return it*/
		 return copiedHeap[size]; 
		 }
	 
	 
	 // Shift up O(logn)
	 private void swim(int i) {
		 while (i > 1) {  
			 int p = i/2;  
			 int result = cmp.compare(heap[i], heap[p]);  
			 if (result <= 0) return;    
			 swap(heap, i, p);                 
			 i = p;
		 }
	 }
	 // Shift down O(logn)
	 private void sink(int i) {
		 int left = 2*i;
		 int right = left+1;
		 int max = left;
		 // If 2*i >= size, node i is a leaf
		 while (left <= size) {
			 // Determine the largest children of node i
			 if (right <= size) {
				 max = cmp.compare(heap[left], heap[right]) < 0 ? right : left;
			 }
			 // If the heap condition holds, stop. Else swap and go on.
			 if (cmp.compare(heap[i], heap[max]) >= 0) return;
		     swap(heap, i, max);
	         i = max;
	         left = 2*i;
	         right = left+1;
	         max = left;
		 }
	 }
	 // Interchanges two array elements.
	 private void swap(Song[] arr, int i, int j) {
		 Song tmp = arr[i];
		 arr[i] = arr[j];
		 arr[j] = tmp;
	 }
	 
	 // Return the size of the priority queue
	 public int size() {
		 return size;
	 }
	 // Return true if heap if empty
	 public boolean isEmpty(){
		 return size == 0;
	 }
	 
	 // Print the elements of the queue
	 public void print() {
		 if (size==0) {
			 System.out.println("Queue is empty");
			 return;
		 }
		 for (int i=1; i<=size; i++){
			 System.out.print(heap[i].getTitle()+"\t");
	     }
		 System.out.println();
	 }
}
