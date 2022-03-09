import java.io.*;
import java.util.*;

public class SaleWL{
	public static ArrayList<MySale> Sale = new ArrayList<MySale>();
	
	public void createFile  (String data) {

		File f = null;
		BufferedWriter writer = null;

		try	{
			f = new File(data);
		}
		catch (NullPointerException e) {
			System.out.println ("Can't create file");
		}

		try	{
			writer = new BufferedWriter(new FileWriter(f));
			writer.write("SALE_LIST"+"\n"+"{"+"\n");
		}
		catch (IOException e){
			System.out.println("Can't write to file");
		}
		
		for (MySale sale: Sale){
			try {
				writer.write("\t"+"SALE"+"\n"+"\t"+"{"
				+"\n"+"\t"+"NUMBER "+sale.getCode()
				+"\n"+"\t"+"ITEM_TYPE "+sale.getProduct().getClass().getName()
				+"\n"+"\t"+"MODEL "+sale.getProductName() 
				+"\n"+"\t"+"NAME \""+sale.getName()+"\""
				+"\n"+"\t"+"PHONE \""+sale.getPhone()+"\""
				+"\n"+"\t"+"SALE_DATE \""+sale.currentDay()+"\'"
				+"\n"+"\t"+"PRICE "+sale.getFinalPrice()
				+"\n"+"\t"+"}"+"\n");
			}
			catch (Exception e){
				System.err.println("Write Error");
			}
		}
		try {
			writer.write("}");
			writer.close();
	
		}
		catch (IOException e) {
			System.err.println("Error closing file.");
		}
		
	}
	public void printFile (String data) {
		File f = null;
		BufferedReader reader = null;
		String line;

		try	{
			f = new File(data);
		}
		catch (NullPointerException e) {
			System.out.println ("Can't open file");
		}

		try {
            reader = new BufferedReader(new FileReader(f));
        }
        catch (IOException e) {
        	System.out.println("Can't read from file");
        }


        try {
			line = reader.readLine();
			while(line!=null){
				System.out.println(line);
				line=reader.readLine();
			}
            reader.close();
        }
        catch (IOException e) {
			System.err.println("Read error!");
		}

	}


 	public void add(MySale b) {
		Sale.add(b);

	}

}