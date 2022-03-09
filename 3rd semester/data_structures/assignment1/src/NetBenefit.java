import java.io.*;
import java.util.*;
import java.text.*;

public class NetBenefit{
	public static void main(String[] args){
		IntQueueImpl queue = new IntQueueImpl(); //construction of the queue that we will store number of shares and prices at first, and when we read "sell" only the prices
		IntQueueImpl queue2 = new IntQueueImpl();   //construction of the queue2 that we will store the number of shares when we read "sell"
		File f = null;
		BufferedReader reader = null;
		String line;
		int sum = 0;		// the total number of shares bought 
		int profit = 0;		// the profit of each sale, and the final one
		int sell=0;			// the number of shares sold
		int sel=0;			// copy of sell, so that the value will be stored
		int y=0;			// a variable used to correctly put prices and numbers of shares in the right queue
		
		try{
			f = new File(args[0]);	
		}
		catch (NullPointerException e){					//an exception in case the file we want to load 
			System.err.println ("File not found.");		//doesn't exists 
		}
		try{
			reader = new BufferedReader(new FileReader(f));
		}
		catch (FileNotFoundException e ){				//an exception in case the file we want to load 
			System.err.println("Error opening file!");  //has problems at the opening
		}
		try {
			line = reader.readLine();	//read line by line the file of txt path
			while(line!=null){
				if(line.toUpperCase().startsWith("BUY")){ 							//if the line starts with the word "BUY" regardless of each letter case
					String s = line.substring(line.indexOf(" "),line.indexOf(" p"));//get the number of shares bought and put it in the queue
					queue.put(Integer.parseInt(s.trim()));
					String v = line.substring(line.indexOf("e ")+1);				// get the price of the shares and put it in the the queue
					queue.put(Integer.parseInt(v.trim()));
				}
				else if(line.toUpperCase().startsWith("SELL")){	// if the line starts with the word "SELL" regardless of each letter case
					for (int i=y;i<=queue.size();i++){	// go through the queue and 
						if (i%2==0){ 					// if the index of a Node is even then that means that it represents a number of shares bought so
							queue2.put(queue.peek());	// put it in queue2 
							sum += queue.get();			// and add it to the total number of shares bought
						}
						else{							// else if the index of a Node is odd then that means that it represents a price so
							queue.put(queue.get());		// put it in queue 
						}
					}
					queue.put(queue.get());
					String se = line.substring(line.indexOf(" "),line.indexOf(" p")); // get the number of shares sold
					String v = line.substring(line.indexOf("e ")+1); 				  // get the price of the shares 
					sell = Integer.parseInt(se.trim());
					sel = sell;								// store the value of sell, because sell is about to change
					if (sell > sum){		// in case the number of shares bought is less than the number of shares that is expected to be sold
						System.out.println("The amount of shares bought is less than the amount of shares that is expected to be sold");
						break;
					}
					int a = 0;								// a = the price that the non-sold shares were bought
					for (int i=0;i<queue2.size();i++){		// get each Node of the queue2 and see 
						if (queue2.peek()<=sell){			// if it is smaller or equal than the amount sold, that means that all shares is sold 
							queue2.put(queue2.peek());		// so put the amount of it back to the queue2 and 
							queue.put(queue.get());			// store its price to queue and 
							sell -= queue2.get(); 			// remove the amount from the total amount sold
							if (queue2.peek()>sell){		// if it's bigger than the amount sold, then 
								queue2.put(sell);			// put it in queue2 and
								a = queue.peek();			// store its price to variable a (price of the remain)
							}
						}
					}
					queue2.get();
					queue.put(queue.get());
					
					while(!queue.isEmpty()){		// calculate the profit or damage
						profit += queue2.get()*(Integer.parseInt(v.trim()) - queue.get());
						
					}    
					sum -= sel; 		//sum = sum - sel, now sum becomes the remain (the amount not sold)
					queue2.put(sum);	//for the next sale, put the remain in queue2 and its price in queue
					queue.put(a);
					y = queue.size();
				}
				line = reader.readLine();
			}
		}
		catch(IOException e){
			System.err.println("Error reading line .");
		}
		if (profit>=0){								// If profit is bigger or equal to 0, that means that we have profit, else damage 
			System.out.println("Profit : "+profit);
		}
		else
			System.out.println("Damage : "+profit);
	}
}
