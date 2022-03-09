//athor: athanasiou lydia p3170003

import acm.program.*;
import java.util.Arrays.*;

public class Morse extends Program {
	public void run() {
		String input = readLine("Give the string you want to replace with morse symbals: ");
		String fin = "";
		
		for (int i=0;i<input.length();i++) {
			char key = input.charAt(i); 
			String str_key = String.valueOf(key);
			int index = -1;
			for (int j=0;(j<letters.length) && (index == -1);j++){
				if (letters[j].equalsIgnoreCase(str_key)){
					index = j;
				}
			}

			fin = fin + symbols[index];
		}
		println(fin);
	}

	public static String[] letters = {"A","B","C","D","E","F","G","H","I","G","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"," "};
	public static String[] symbols = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--..","  "};
}
