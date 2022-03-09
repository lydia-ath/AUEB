//athor: athanasiou lydia p3170003

import acm.program.*;

public class Ticket extends Program {
	public void run() {


		public static final double ticket = 1.4;

		double coin = 0;
		double sum = 0;
		double change = sum - ticket;
		println("The ticket's price is " + ticket + " euros.");
		println("The machine accepts only 0.1, 0.2, 0.5, 1, 2 coins and 5 bank notes.\n\nIf you want to stop inserting money please enter 0\n\r");
		
		while (true) {
			coin = readDouble("Coin inserted: ");

			if (coin==0.1 || coin==0.2 || coin==0.5 || coin==1 || 
			    coin==2 || coin==5 || coin==0) {
				sum = sum + coin;
				if (coin==0) break;
			}

			else 
				println("Invalid value. Please try again.");
		}
		
		if (sum==ticket) {  
			println("Ticket ready!");
		}else if (sum<ticket) {  
			println("please insert more money in order to buy your ticket!");
			change(sum);
		}else {
			println("Ticket ok!");	 
				change(sum);
		}
	}
	
	public void change (double sum) {
		while (sum>ticket+5) {
			println("return 5 euros!");
			sum = sum - 5;
		}
		while (sum<=5+ticket && sum>=2+ticket) {
			println("return 2 euros!");
			sum = sum - 2;
		}
		while ((sum<2+ticket && sum>=1+ticket) || (sum<ticket && sum>=ticket-1)) {
			println("return 1 euros!");
			sum = sum - 1;
		}
		while ((sum<1+ticket && sum>=ticket+0.5) || (sum<ticket && sum>=ticket-0.5)) {
			println("return 0.5 cents!");
			sum = sum - 0.5;
		}
		while ((sum<ticket+0.5 && sum>=ticket+0.2) || (sum<ticket && sum>=ticket-0.2)) {
			println("return 0.2 cents!");
			sum = sum - 0.2;
		}
		while ((sum<ticket+0.2 && sum>=ticket+0.1) || (sum<ticket && sum>=ticket-0.1)) {
			println("return 0.1 cent!");
			sum = sum - 0.1;
		}
	}
}
