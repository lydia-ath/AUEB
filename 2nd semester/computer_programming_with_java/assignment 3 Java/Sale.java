import java.io.*;
import java.util.*;

public class Sale{
	
	public ArrayList <MySale> Sales = new ArrayList<MySale>();

	public void loadFile(String data) { 
		int counter = 0; 

		MyCatalogs catalogs = new MyCatalogs();
        File f = null; 
        BufferedReader reader = null; 
        MySale mysale;
		Product product = null;
        String line;
		String prclass = "";
		String prSlDay = "";
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
				if (line.trim().equalsIgnoreCase("SALES_LIST")){
					line = reader.readLine();
					while (!line.trim().equals("{"))
						line = reader.readLine();
					if (line.trim().equals("{")){
						line = reader.readLine();
						while (!line.trim().equals("}")){
							if (line.trim().equalsIgnoreCase("SALE")){
								cname = "";
								cphone = "";
								line = reader.readLine();
								included = false;
								found= false;
								mysale= null;
								
								while (!line.trim().equals("{"))
									line = reader.readLine();
							
								if (line.trim().equals("{")){
									line = reader.readLine();
									
									while (!line.trim().equals("}")){
										if (line.trim().toUpperCase().startsWith("NUMBER"))
											procode = Integer.parseInt(line.trim().substring(7).trim()); 
										
										else if (line.trim().toUpperCase().startsWith("ITEM_TYPE"))
											prclass = line.trim().substring(10).trim();
										
										else if (line.trim().toUpperCase().startsWith("MODEL")){
											for (Availables item: catalogs.availables){
												// To check if product is provided by the store
												if (item.getProductName().equalsIgnoreCase(line.trim().substring(6).trim())){
													product = item.getProduct();
													item.decreaseAvailability();
													found = true;
												}
											}
											prname = line.trim().substring(6).trim();
										}
										else if (line.trim().toUpperCase().startsWith("NAME"))
											cname = line.trim().substring(4).trim();
										
										else if (line.trim().toUpperCase().startsWith("PHONE"))
											cphone = line.trim().substring(5).trim();
										
										else if (line.trim().toUpperCase().startsWith("SALE_DATE"))
											prSlDay = line.trim().substring(10).trim();
										
										else if (line.trim().toUpperCase().startsWith("PRICE")){
											prprice = Double.parseDouble(line.trim().substring(5).trim());
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
												prSlDay = prSlDay.replaceAll("\"","");
												mysale = new MySale(procode, product, cname, cphone, prSlDay, prprice);
												mysale.setFinalPrice(prprice);
												Sales.add(mysale);
											}
											else 
												System.out.println("Product "+prname+" is not included in category "+prclass);
										}
										else
											System.out.println("Product "+prname+" is not provided in store");
									}
										line = reader.readLine();
								} // if equals { (OF Sale)
							} // if equals Sale
							else if (line.isEmpty())
								line = reader.readLine();
						} // while not }
					} // if equals { (of SALE_LIST)	
					
				} //if equals Sale list
				line = reader.readLine();
			} // while line not null
		} //try 
					
		catch (IOException e) {
			System.out.println("Error reading line " + counter + ".");
		}
		
		try {
			reader.close(); //COULDN'T CLOSE IT.
		} 
		catch (IOException e) {
				System.err.println("Error closing file.");
		}
	}
	public MySale get(int i){
		return Sales.get(i); // RETURN WHICH ITEM  IS THIS.
	}
	public int size(){
		return Sales.size(); //RETURNS THE NUMBER OF ITEMS.
	}
}
