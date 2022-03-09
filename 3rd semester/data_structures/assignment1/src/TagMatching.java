import java.io.*;
import java.util.*;
import java.text.*;
import java.util.regex.Pattern; // an alternative  way to find and read the tags in a HTML text
import java.util.regex.Matcher;

public class TagMatching{
	public static void main(String[] args){
		File f = null;				 
		BufferedReader reader = null; 
		String line;
		StringStackImpl stack = new StringStackImpl(); //creation of the stack that we will use in the program
	
		try{
			f = new File(args[0]);
		}										//an exception in case the file we want to load 
		catch (NullPointerException e){			//doesn't exists 
			System.err.println ("File not found.");
		}
		try{
			reader = new BufferedReader(new FileReader(f));
		}
		catch (FileNotFoundException e ){			 	//an exception in case the file we want to load 
			System.err.println("Error opening file!");	// can't be opened for a reason
		}
		try	{
			line = reader.readLine(); //read line by line the file of html path
			
			while(line!=null){ 
				String patternString = "<[a-zA-Z0-9]+?(?=>)";   /*this String  is used as a regular expression later to get the substrings of the line in form "<...", where "..." 	
																  can be small/capital letters or/and numbers  */
				String patternString2 = "</[a-zA-Z0-9]+?(?=>)";	 // same as patternString but in form "</...", where "..." can be small/capital letters or/and numbers 
				Pattern pattern = Pattern.compile(patternString);  // construction of a pattern given the regular expression patternString using the "compile" method
				Pattern pattern2 = Pattern.compile(patternString2); //construction of a pattern given the regular expression patternString using the "compile" method
				Matcher matcher = pattern.matcher(line);			 //construction of a matcher that will match the line against the pattern
				Matcher matcher2 = pattern2.matcher(line);			 // construction of a matcher that will match the line against the pattern2
				while(matcher.find()){
					stack.push(line.trim().substring(matcher.start()+1,matcher.end()));		//if during the while loop the system finds a match(first type form "<...") 
				}																			//we push it into the stack, removing the "<"
				while(matcher2.find()){														//if during the while loop the system finds a match(second type form "</...")
					if(line.trim().substring(matcher2.start()+2,matcher2.end()).equalsIgnoreCase(stack.peek())) //we check if this match is the same with the lead of the stack 
					stack.pop();															// if it is then we remove it from the stack
				}	
				line = reader.readLine();
			}
				
		}catch(IOException e){
			System.err.println("Error reading line .");
		}
		
		if(stack.size()==0)
			System.out.println("The tags are well matched");		//if the size of the stack is 0 that means all tags are matching 
		else														//we print the proper message to the user 
			System.out.println("The tags don't match");
	}
}