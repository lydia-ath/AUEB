//athor: athanasiou lydia p3170003
import acm.program.*;

public class DNA extends Program {
	public void run() {
		String longDNA = readLine("Give the long DNA : ");
		String shortDNA = readLine("Give the short DNA : ");
		println("The position that the match is made is "+findFirstMatchingPosition(shortDNA,longDNA));
	}
	
	
	public int findFirstMatchingPosition(String shortDNA,String longDNA) {
		int index = -1;
		for (int i=0;i<=longDNA.length()-shortDNA.length();i++){
			String check = reversed(longDNA).substring(i,i+shortDNA.length());
			if (check.equalsIgnoreCase(shortDNA)){
				index = i;
			}
		}
		return index;
	}
	
	
	public String reversed(String longDNA){
		String reverseDNA = "";
		for (int i=0;i<longDNA.length();i++){
			char longbase = longDNA.charAt(i);
			if (longbase=='A'){
				reverseDNA = reverseDNA + "T";
			}
			if (longbase=='T'){
				reverseDNA = reverseDNA + "A";
			}
			if (longbase=='C'){
				reverseDNA = reverseDNA + "G";
			}
			if (longbase=='G'){
				reverseDNA = reverseDNA + "C";
			}
		}
		return reverseDNA;
	}
}

