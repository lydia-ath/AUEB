import java.io.*;
import java.util.*;

public class OrderWL{
	public static ArrayList<MyOrder> Order = new ArrayList<MyOrder>();
	
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
			writer.write("ORDER_LIST"+"\n"+"{"+"\n");
		}
		catch (IOException e){
			System.out.println("Can't write to file");
		}
		
		for (MyOrder order: Order){
			try {
				writer.write("\t"+"ORDER"+"\n"+"\t"+"{"
				+"\n"+"\t"+"\t"+"NUMBER "+order.getCode()
				+"\n"+"\t"+"\t"+"ITEM_TYPE "+order.getProduct().getClass().getName()
				+"\n"+"\t"+"\t"+"MODEL "+order.getProductName()
				+"\n"+"\t"+"\t"+"NAME \""+order.getName()+"\""
				+"\n"+"\t"+"\t"+"PHONE \""+order.getPhone()+"\""
				+"\n"+"\t"+"\t"+"ORDER_DATE \""+order.currentDay()+"\'"
				+"\n"+"\t"+"\t"+"DELIVERY_DATE \""+order.arrivalDay()+"\'"
				+"\n"+"\t"+"\t"+"PRICE "+order.getFinalPrice()
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


 	public void add(MyOrder b) {
		Order.add(b);
	}

}