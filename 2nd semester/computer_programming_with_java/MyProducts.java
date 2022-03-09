// This class reads the Available products from a file, and checks for any errors in the file
import java.io.*;
import java.util.*;

public class MyProducts {
	public ArrayList <Availables> StoreProducts = new ArrayList<Availables>();
	private static final double AccessoriesDiscount = 0.2;
	private static final double PeripheralsDiscount = 0.25;
	
	public void loadFile(String data) { 
		int counter = 0; 
		File f = null;
        BufferedReader reader = null;
        Product product = null; 
        String line; 
		
		String prclass = "";
		String prname = "";
		String prchipset = "";
		String prconstructor = "";
		int prcores = 0;
		int prfrequency = 0;
		String prgraphics = "";
		String printingtype = "";
		String printtechnology = "";
		int prpieces = 0;
		String prport = "";
		double prprice = 0;
		String prresolution= "";
		int prsata = 0;
		double prspeed = 0;
		int pryear= 0;
		int hdrivememory = 0;
		double hdrivesize = 0;
		String hdrivetype = "";
		int momemory = 0;
		String mothtype = "";
		String mouseconnection = "";
		String moutechnology = "";
		String screentype = "";
		double dimension = 0;
		int graphmemory = 0;
		int rammemory = 0;
		String ramtype = "";
		String keyconnection = "";
		boolean type= false;
		boolean model= false;
		boolean price= false;
		boolean graphics= false;
		int count =0;
		
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
		
		try{
			line = reader.readLine();
			while (line != null){
				if (line.equalsIgnoreCase("ITEM_LIST")){
					line = reader.readLine();
					//System.out.println("1"); ////////////////////////////////////1
					while (!line.trim().equals("{"))
						line = reader.readLine();
					if (line.trim().equals("{")){
						line = reader.readLine();
						//System.out.println("2"); ////////////////////////////////////2
						while (!line.trim().equals("}")){
							if (line.trim().equalsIgnoreCase("ITEM")){
								count++;						
								type= false;
								model= false;
								price= false;
								graphics= false;
								line = reader.readLine();
								while (!line.trim().equals("{"))
									line = reader.readLine();
								if (line.trim().equals("{")){
									line = reader.readLine();
									while (!line.trim().equals("}")){
										if (line.trim().toUpperCase().startsWith("ITEM_TYPE")){ //class
											if (count==1)
												prclass = line.substring(12).trim();
											else
												prclass = line.substring(18).trim();
											type = true;
										}
										else if(line.trim().toUpperCase().startsWith("MODEL ")){
											if (count==1)
												prname = line.substring(7).trim();
											else 
												prname = line.substring(11).trim();
											model = true;
										}
										else if(line.trim().toUpperCase().startsWith("MODEL_YEAR"))
											pryear = Integer.parseInt(line.substring(13).trim());
										else if(line.trim().toUpperCase().startsWith("CONSTRUCTOR"))
											prconstructor = line.substring(14).trim();
										else if(line.trim().toUpperCase().startsWith("PRICE")){
											prprice = Double.parseDouble(line.substring(11).trim());
											price = true;
										}
										// TYPES
										else if(line.trim().toUpperCase().startsWith("MOTHERBOARD_TYPE"))
											mothtype = line.substring(19).trim();//
										else if(line.trim().toUpperCase().startsWith("RAM_TYPE"))
											ramtype = line.substring(11).trim();
										else if(line.trim().toUpperCase().startsWith("HARDDRIVE_TYPE"))
											hdrivetype = line.substring(17).trim();
										else if(line.trim().toUpperCase().startsWith("SCREEN_TYPE"))
											screentype = line.substring(14).trim();
										else if(line.trim().toUpperCase().startsWith("PRINTING_TYPE"))
											printingtype = line.substring(16).trim();
										//SIZES
										else if(line.trim().toUpperCase().startsWith("SCREEN_SIZE")) //11+3
											dimension = Double.parseDouble(line.substring(14).trim());
										else if(line.trim().toUpperCase().startsWith("HARDDRIVE_SIZE")) 
											hdrivesize = Double.parseDouble(line.substring(17).trim());
										// TECHNOLOGIES
										else if(line.trim().toUpperCase().startsWith("MOUSE_TECHNOLOGY"))
											moutechnology = line.substring(19).trim(); //16
										else if(line.trim().toUpperCase().startsWith("PRINTER_TECHNOLOGY"))
											printtechnology = line.substring(21).trim(); //18
										// MEMORIES
										else if(line.trim().toUpperCase().startsWith("MOTHERBOARD_MEMORY"))
											momemory = Integer.parseInt(line.substring(21).trim());
										else if(line.trim().toUpperCase().startsWith("RAM_MEMORY"))
											rammemory = Integer.parseInt(line.substring(13).trim());
										else if(line.trim().toUpperCase().startsWith("GRAPHICSCARD_MEMORY")) //
											graphmemory = Integer.parseInt(line.substring(22).trim());
										else if(line.trim().toUpperCase().startsWith("HARDDRIVE_MEMORY"))
											hdrivememory = Integer.parseInt(line.substring(18).trim());
										else if(line.trim().toUpperCase().startsWith("CHIPSET"))
											prchipset = line.substring(10).trim();
										else if(line.trim().toUpperCase().startsWith("FREQUENCY"))
											prfrequency = Integer.parseInt(line.substring(11, 16).trim());
										else if(line.trim().toUpperCase().startsWith("SPEED"))
											prspeed = Double.parseDouble(line.substring(7, 9).trim());
										else if(line.trim().toUpperCase().startsWith("CORES"))
											prcores = Integer.parseInt(line.substring(7).trim());
										else if(line.trim().toUpperCase().startsWith("GRAPHICS"))
											prgraphics = line.substring(11).trim();
										else if(line.trim().toUpperCase().startsWith("NUMBEROFSATA"))
											prsata = Integer.parseInt(line.substring(14).trim());
										else if(line.trim().toUpperCase().startsWith("RESOLUTION"))
											prresolution = line.substring(13).trim();
										else if(line.trim().toUpperCase().startsWith("INTERFACES"))
											prport = line.substring(13).trim();
										// CONNECTIONS
										else if(line.trim().toUpperCase().startsWith("KEYBOARD_CONNECTION"))
											keyconnection = line.substring(22).trim();
										else if(line.trim().toUpperCase().startsWith("MOUSE_CONNECTION"))
											mouseconnection = line.substring(19).trim();
										else if(line.trim().toUpperCase().startsWith("PIECES"))
											prpieces = Integer.parseInt(line.substring(8).trim());
										else if(line.isEmpty())
											line = reader.readLine();
										line = reader.readLine();
									}
									if (line.trim().equals("}")){
										if (type==true){
											if (model==true){
												if (price==true){
													if (prclass.equalsIgnoreCase("Motherboard")){
														product = new Motherboard(prname, pryear, prconstructor, prprice, AccessoriesDiscount, momemory, mothtype, prsata);
														StoreProducts.add(new Availables(product, prpieces));
													}
													else if (prclass.equalsIgnoreCase("CPU")){
														if (prgraphics.equalsIgnoreCase("YES"))
															graphics = true;
														product = new CPU(prname, pryear, prconstructor, prprice, AccessoriesDiscount, prspeed, prcores, graphics);
														StoreProducts.add(new Availables(product, prpieces));
													}
													else if (prclass.equalsIgnoreCase("RAM")){
														product = new RAM(prname, pryear, prconstructor, prprice, AccessoriesDiscount, ramtype, rammemory, prfrequency);
														StoreProducts.add(new Availables(product, prpieces));
													}
													else if (prclass.equalsIgnoreCase("GraphicsCard")){
														product = new GraphicsCard(prname, pryear, prconstructor, prprice, AccessoriesDiscount, prchipset, graphmemory);
														StoreProducts.add(new Availables(product, prpieces));
													}
													else if (prclass.equalsIgnoreCase("HardDrive")){
														product = new HardDrive(prname, pryear, prconstructor, prprice, AccessoriesDiscount,hdrivetype, hdrivesize, hdrivememory);
														StoreProducts.add(new Availables(product, prpieces));
													}
													else if (prclass.equalsIgnoreCase("Screen")){
														product = new Screen(prname, pryear, prconstructor, prprice, PeripheralsDiscount, screentype, dimension, prresolution, prport);
														StoreProducts.add(new Availables(product, prpieces));
													}
													else if (prclass.equalsIgnoreCase("Keyboard")){
														product = new Keyboard(prname, pryear, prconstructor, prprice, PeripheralsDiscount, keyconnection);
														StoreProducts.add(new Availables(product, prpieces));
													}
													else if (prclass.equalsIgnoreCase("Mouse")){
														product = new Mouse(prname, pryear, prconstructor, prprice, PeripheralsDiscount, mouseconnection, moutechnology);
														StoreProducts.add(new Availables(product, prpieces));
													}
													else if (prclass.equalsIgnoreCase("Printer")){
														product = new Printer(prname, pryear, prconstructor, prprice, PeripheralsDiscount, printtechnology, printingtype);
														StoreProducts.add(new Availables(product, prpieces));
													}
													else 
														System.out.println("Store does not provide products included in category "+prclass);
												}
												else 
													System.out.println("Missing product's type.");
											}
											else 
												System.out.println("Missing product's name");
										}
										else 
											System.out.println("Missing product's type.");
									}
									line = reader.readLine();
								}
							}
						}
					}
				}
				else 
					line = reader.readLine();
				line = reader.readLine();
			}
		}
		catch  (IOException e) {
			System.out.println("Error reading line " + counter + ".");
		}
		
		// If it can't be closed
		try {
			reader.close(); 
		} 
		catch (IOException e) {
				System.err.println("Error closing file.");
		}
	}
	// Returns the Available in position 'i' of the ArrayList
	public Availables get(int i){
		return StoreProducts.get(i);
	}
	// Returns the number of the Availables in the ArrayList
	public int size(){
		return StoreProducts.size(); 
	}
}