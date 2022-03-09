import java.io.*;
import java.util.*;

public class Order{
	
	public ArrayList <MyOrder> Orders = new ArrayList<MyOrder>();

	public void loadFile(String data) { 
		int counter = 0; 

		MyCatalogs catalogs = new MyCatalogs();
        File f = null; 
        BufferedReader reader = null; 
        MyOrder myorder;
		Product product = null;
        String line;
		String prclass = "";
		String prOrDay = "";
		String prarrday = "";
		String prname = "";
		String cname = "";
		String cphone = "";
		double prprice = 0;
		int procode = 0;
		boolean included;
		boolean found;
	
		// If the file does not exist or could not be found
        try {
            f = new File(data); 
        } 
		catch (NullPointerException e) {
            System.err.println("File not found.");
        }

		// If the file cannot be opened
        try {
            reader = new BufferedReader(new FileReader(f));
        } 
		catch (FileNotFoundException e) {
            System.err.println("Error opening file!");
        }
		
		try { 
			line = reader.readLine() ;
			while(line != null){
				if (line.trim().equalsIgnoreCase("ORDER_LIST")){
					line = reader.readLine();
					while (!line.trim().equals("{"))
						line = reader.readLine();
					if (line.trim().equals("{")){
						line = reader.readLine();
						while (!line.trim().equals("}")){
							if (line.trim().equalsIgnoreCase("ORDER")){
								line = reader.readLine();
								included = false;
								found= false;
								myorder= null;
								
								while (!line.trim().equals("{"))
									line = reader.readLine();
							
								if (line.trim().equals("{")){
									line = reader.readLine();
									
									while (!line.trim().equals("}")){
										if (line.trim().toUpperCase().startsWith("NUMBER"))
											procode = Integer.parseInt(line.substring(9).trim()); 
										
										else if (line.trim().toUpperCase().startsWith("ITEM_TYPE"))
											prclass = line.substring(12).trim();
										
										else if (line.trim().toUpperCase().startsWith("MODEL")){
											for (Availables item: catalogs.availables){
												// To check if product is provided by the store
												if (item.getProductName().equalsIgnoreCase(line.substring(8).trim())){
													product = item.getProduct();
													item.decreaseAvailability();
													found = true;
												}
											}
											prname = line.substring(8).trim();
										}
										else if (line.trim().toUpperCase().startsWith("NAME"))
											cname = line.substring(6).trim();
										
										else if (line.trim().toUpperCase().startsWith("PHONE"))
											cphone = line.substring(7).trim();
										
										else if (line.trim().toUpperCase().startsWith("ORDER_DATE"))
											prOrDay = line.substring(12).trim();
										
										else if (line.trim().toUpperCase().startsWith("DELIVERY_DATE"))
											prarrday = line.substring(15).trim();
										
										else if (line.trim().toUpperCase().startsWith("PRICE")){
											prprice = Double.parseDouble(line.substring(7).trim());
										}
										else if (line.isEmpty())
											line = reader.readLine();
										line = reader.readLine();
									} 
									if (line.trim().startsWith("}")){
										//it gets added in the List if the product has a valid name and belongs to the right category
										if (found==true){
											// To check if the product's category the client gives is the right one
											if (product.getClass().getName().equalsIgnoreCase(prclass))
												included = true;
											if (included==true){
												cname = cname.replaceAll("\"",""); // to remove all the " 
												cphone = cphone.replaceAll("\"","");
												prOrDay = prOrDay.replaceAll("\"","");
												prarrday = prarrday.replaceAll("\"","");
												myorder = new MyOrder(procode, product, cname, cphone, prOrDay, prarrday, prprice, false);
												myorder.setCode(procode);
												myorder.setFinalPrice(prprice);
												Orders.add(myorder);
											}
											else 
												System.out.println("Product "+prname+" is not included in category "+prclass);
										}
										else
											System.out.println("Product "+prname+" is not provided in store");
									}
									line = reader.readLine();
								} // if equals { (OF ORDER)
							} // if equals Order
							else if (line.isEmpty())
								line = reader.readLine();
						} // while not }
					} // if equals { (of ORDER_LIST)	
				} //if equals Order list
				line = reader.readLine();
			} // while line not null
		} //try 
					
		catch (IOException e) {
			System.out.println("Error reading line " + counter + ".");
		}
		
		// If it can't be opened.
		try {
			reader.close();
		} 
		catch (IOException e) {
				System.err.println("Error closing file.");
		}
	}
	//Returns the MyOrder item in position 'i' of the ArrayList
	public MyOrder get(int i){
		return Orders.get(i); 
	}
	// Returns the number of the MyOrder items in the list
	public int size(){
		return Orders.size(); 
	}
}
