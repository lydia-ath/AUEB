/*This class contains the catalog of all the available products, the catalog of the ordered products and the catalog of the sold products */
import java.util.*;
public class MyCatalogs {
	private Product item;
	private MyOrder ordered;
	private MySale sold;
	public static ArrayList<Availables> availables = new ArrayList<Availables>();
	public ArrayList<MyOrder> fororder = new ArrayList<MyOrder>();
	public ArrayList<MySale> forsale = new ArrayList<MySale>();
	private static final double AccessoriesDiscount = 0.2;
	private static final double PeripheralsDiscount = 0.25;
	
	// Constructor
	public MyCatalogs(){
		ArrayList<Availables> availables;
		ArrayList<MyOrder> fororder;
		ArrayList<MySale> forsale;
		//addAll();
	}
	
	// Fills the "availables" list with the products the store sells if the seller doesn't want to use a file to add the products.
	public void addAll(){
		// Motherboards
		availables.add(new Availables(new Motherboard("MB1", 2017, "Brand1", 95, AccessoriesDiscount, 64, "AMD", 4),10));
		availables.add(new Availables(new Motherboard("MB2", 2017, "Brand1", 90, AccessoriesDiscount, 32, "AMD", 4), 5));
		availables.add(new Availables(new Motherboard("MB3", 2017, "Brand1", 100, AccessoriesDiscount, 128, "AMD", 4), 4));
		availables.add(new Availables(new Motherboard("MB4", 2017, "Brand1", 100, AccessoriesDiscount, 64, "AMD", 6), 3));
		availables.add(new Availables(new Motherboard("MB5", 2017, "Brand1", 95, AccessoriesDiscount, 32, "AMD", 6), 6));
		availables.add(new Availables(new Motherboard("MB6", 2017, "Brand1", 105, AccessoriesDiscount, 128, "AMD", 6), 12));
		availables.add(new Availables(new Motherboard("MB7", 2017, "Brand1", 105, AccessoriesDiscount, 64, "AMD", 8), 11));
		availables.add(new Availables(new Motherboard("MB8", 2017, "Brand1", 100, AccessoriesDiscount, 32, "AMD", 8), 3));
		availables.add(new Availables(new Motherboard("MB9", 2017, "Brand1", 110, AccessoriesDiscount, 128, "AMD", 8), 2));
		availables.add(new Availables(new Motherboard("MB10", 2017, "Brand1", 95, AccessoriesDiscount, 64, "Intel", 4), 5));
		availables.add(new Availables(new Motherboard("MB11", 2017, "Brand1", 90, AccessoriesDiscount, 32, "Intel", 4), 6));
		availables.add(new Availables(new Motherboard("MB12", 2017, "Brand1", 100, AccessoriesDiscount, 128, "Intel", 4), 12));
		availables.add(new Availables(new Motherboard("MB13", 2017, "Brand1", 100, AccessoriesDiscount, 64, "Intel", 6), 4));
		availables.add(new Availables(new Motherboard("MB14", 2017, "Brand1", 95, AccessoriesDiscount, 32, "Intel", 6), 14));
		availables.add(new Availables(new Motherboard("MB15", 2017, "Brand1", 105, AccessoriesDiscount, 128, "Intel", 6), 1));
		availables.add(new Availables(new Motherboard("MB16", 2017, "Brand1", 105, AccessoriesDiscount, 64, "Intel", 8), 5));
		availables.add(new Availables(new Motherboard("MB17", 2017, "Brand1", 100, AccessoriesDiscount, 32, "Intel", 8), 4));
		availables.add(new Availables(new Motherboard("MB18", 2017, "Brand1", 110, AccessoriesDiscount, 128, "Intel", 8), 4));
		// CPUs
		availables.add(new Availables(new CPU("CPU1", 2017, "Brand2", 190, AccessoriesDiscount, 3, 6, true), 7));
		availables.add(new Availables(new CPU("CPU2", 2017, "Brand2", 195, AccessoriesDiscount, 3, 8, true), 9));
		availables.add(new Availables(new CPU("CPU3", 2017, "Brand2", 200, AccessoriesDiscount, 3, 16, true), 7));
		availables.add(new Availables(new CPU("CPU4", 2017, "Brand2", 195, AccessoriesDiscount, 3.6, 6, true), 3));
		availables.add(new Availables(new CPU("CPU5", 2017, "Brand2", 200, AccessoriesDiscount, 3.6, 8, true), 5));
		availables.add(new Availables(new CPU("CPU6", 2017, "Brand2", 205, AccessoriesDiscount, 3.6, 16, true), 11));
		availables.add(new Availables(new CPU("CPU7", 2017, "Brand2", 200, AccessoriesDiscount, 4, 6, true), 3));
		availables.add(new Availables(new CPU("CPU8", 2017, "Brand2", 205, AccessoriesDiscount, 4, 8, true), 2));
		availables.add(new Availables(new CPU("CPU9", 2017, "Brand2", 210, AccessoriesDiscount, 4, 16, true), 8));
		availables.add(new Availables(new CPU("CPU10", 2017, "Brand2", 190, AccessoriesDiscount, 3, 6, false), 6));
		availables.add(new Availables(new CPU("CPU11", 2017, "Brand2", 195, AccessoriesDiscount, 3, 8, false), 3));
		availables.add(new Availables(new CPU("CPU12", 2017, "Brand2", 200, AccessoriesDiscount, 3, 16, false), 4));
		availables.add(new Availables(new CPU("CPU13", 2017, "Brand2", 195, AccessoriesDiscount, 3.6, 6, false), 23));
		availables.add(new Availables(new CPU("CPU14", 2017, "Brand2", 200, AccessoriesDiscount, 3.6, 8, false), 5));
		availables.add(new Availables(new CPU("CPU15", 2017, "Brand2", 205, AccessoriesDiscount, 3.6, 16, false), 8));
		availables.add(new Availables(new CPU("CPU16", 2017, "Brand2", 200, AccessoriesDiscount, 4, 6, false), 3));
		availables.add(new Availables(new CPU("CPU17", 2017, "Brand2", 205, AccessoriesDiscount, 4, 8, false), 8));
		availables.add(new Availables(new CPU("CPU18", 2017, "Brand2", 210, AccessoriesDiscount, 4, 16, false), 9));
		// RAMs   
		availables.add(new Availables(new RAM("RAM1", 2017, "Brand1", 50, AccessoriesDiscount, "DDR2", 4, 1600), 7));
		availables.add(new Availables(new RAM("RAM2", 2017, "Brand1", 55, AccessoriesDiscount, "DDR2", 8, 1600), 9));
		availables.add(new Availables(new RAM("RAM3", 2017, "Brand1", 60, AccessoriesDiscount, "DDR2", 16, 1600), 4));
		availables.add(new Availables(new RAM("RAM4", 2017, "Brand1", 55, AccessoriesDiscount, "DDR2", 4, 2666), 10));
		availables.add(new Availables(new RAM("RAM5", 2017, "Brand1", 60, AccessoriesDiscount, "DDR2", 8, 2666), 6));
		availables.add(new Availables(new RAM("RAM6", 2017, "Brand1", 65, AccessoriesDiscount, "DDR2", 16, 2666), 7));
		availables.add(new Availables(new RAM("RAM7", 2017, "Brand1", 60, AccessoriesDiscount, "DDR2", 4, 4600), 3));
		availables.add(new Availables(new RAM("RAM8", 2017, "Brand1", 65, AccessoriesDiscount, "DDR2", 8, 4600), 7));
		availables.add(new Availables(new RAM("RAM9", 2017, "Brand1", 70, AccessoriesDiscount, "DDR2", 16, 4600), 12));
		availables.add(new Availables(new RAM("RAM10", 2017, "Brand1", 50, AccessoriesDiscount, "DDR3", 4, 1600), 13));
		availables.add(new Availables(new RAM("RAM11", 2017, "Brand1", 55, AccessoriesDiscount, "DDR3", 8, 1600), 7));
		availables.add(new Availables(new RAM("RAM12", 2017, "Brand1", 60, AccessoriesDiscount, "DDR3", 16, 1600), 9));
		availables.add(new Availables(new RAM("RAM13", 2017, "Brand1", 55, AccessoriesDiscount, "DDR3", 4, 2666), 15));
		availables.add(new Availables(new RAM("RAM14", 2017, "Brand1", 60, AccessoriesDiscount, "DDR3", 8, 2666), 11));
		availables.add(new Availables(new RAM("RAM15", 2017, "Brand1", 65, AccessoriesDiscount, "DDR3", 16, 2666), 12));
		availables.add(new Availables(new RAM("RAM16", 2017, "Brand1", 60, AccessoriesDiscount, "DDR3", 4, 4600), 6));
		availables.add(new Availables(new RAM("RAM17", 2017, "Brand1", 65, AccessoriesDiscount, "DDR3", 8, 4600), 4));
		availables.add(new Availables(new RAM("RAM18", 2017, "Brand1", 70, AccessoriesDiscount, "DDR3", 16, 4600), 7));
		availables.add(new Availables(new RAM("RAM19", 2017, "Brand1", 50, AccessoriesDiscount, "DDR4", 4, 1600), 2));
		availables.add(new Availables(new RAM("RAM20", 2017, "Brand1", 55, AccessoriesDiscount, "DDR4", 8, 1600), 5));
		availables.add(new Availables(new RAM("RAM21", 2017, "Brand1", 60, AccessoriesDiscount, "DDR4", 16, 1600), 8));
		availables.add(new Availables(new RAM("RAM22", 2017, "Brand1", 55, AccessoriesDiscount, "DDR4", 4, 2666), 9));
		availables.add(new Availables(new RAM("RAM23", 2017, "Brand1", 60, AccessoriesDiscount, "DDR4", 8, 2666), 10));
		availables.add(new Availables(new RAM("RAM24", 2017, "Brand1", 65, AccessoriesDiscount, "DDR4", 16, 2666), 7));
		availables.add(new Availables(new RAM("RAM25", 2017, "Brand1", 60, AccessoriesDiscount, "DDR4", 4, 4600), 5));
		availables.add(new Availables(new RAM("RAM26", 2017, "Brand1", 65, AccessoriesDiscount, "DDR4", 8, 4600), 4));
		availables.add(new Availables(new RAM("RAM27", 2017, "Brand1", 70, AccessoriesDiscount, "DDR4", 16, 4600), 3));
		//Graphics Cards
		availables.add(new Availables(new GraphicsCard("GRC1", 2017, "Brand2", 200, AccessoriesDiscount, "nVidia", 2), 7));
		availables.add(new Availables(new GraphicsCard("GRC2", 2017, "Brand2", 200, AccessoriesDiscount, "nVidia", 4), 6));
		availables.add(new Availables(new GraphicsCard("GRC3", 2017, "Brand2", 200, AccessoriesDiscount, "nVidia", 6), 10));
		availables.add(new Availables(new GraphicsCard("GRC4", 2017, "Brand2", 200, AccessoriesDiscount, "AMD", 2), 2));
		availables.add(new Availables(new GraphicsCard("GRC5", 2017, "Brand2", 200, AccessoriesDiscount, "AMD", 4), 7));
		availables.add(new Availables(new GraphicsCard("GRC6", 2017, "Brand2", 200, AccessoriesDiscount, "AMD", 6), 1));
		//Hard Drives
		availables.add(new Availables(new HardDrive("HDR1", 2017, "Brand1", 50, AccessoriesDiscount, "HDD", 1.8, 256), 9));
		availables.add(new Availables(new HardDrive("HDR2", 2017, "Brand1", 55, AccessoriesDiscount, "HDD", 2.5, 256), 2));
		availables.add(new Availables(new HardDrive("HDR3", 2017, "Brand1", 60, AccessoriesDiscount, "HDD", 3.5, 256), 3));
		availables.add(new Availables(new HardDrive("HDR4", 2017, "Brand1", 55, AccessoriesDiscount, "HDD", 1.8, 500), 6));
		availables.add(new Availables(new HardDrive("HDR5", 2017, "Brand1", 60, AccessoriesDiscount, "HDD", 2.5, 500), 10));
		availables.add(new Availables(new HardDrive("HDR6", 2017, "Brand1", 65, AccessoriesDiscount, "HDD", 3.5, 500), 3));
		availables.add(new Availables(new HardDrive("HDR7", 2017, "Brand1", 60, AccessoriesDiscount, "HDD", 1.8, 750), 1));
		availables.add(new Availables(new HardDrive("HDR8", 2017, "Brand1", 65, AccessoriesDiscount, "HDD", 2.5, 750), 8));
		availables.add(new Availables(new HardDrive("HDR9", 2017, "Brand1", 70, AccessoriesDiscount, "HDD", 3.5, 750), 5));
		availables.add(new Availables(new HardDrive("HDR10", 2017, "Brand1", 65, AccessoriesDiscount, "HDD", 1.8, 1000), 12));
		availables.add(new Availables(new HardDrive("HDR11", 2017, "Brand1", 70, AccessoriesDiscount, "HDD", 2.5, 1000), 4));
		availables.add(new Availables(new HardDrive("HDR12", 2017, "Brand1", 75, AccessoriesDiscount, "HDD", 3.5, 1000), 6));
		availables.add(new Availables(new HardDrive("HDR13", 2017, "Brand1", 50, AccessoriesDiscount, "SSD", 1.8, 256), 7));
		availables.add(new Availables(new HardDrive("HDR14", 2017, "Brand1", 55, AccessoriesDiscount, "SSD", 2.5, 256), 6));
		availables.add(new Availables(new HardDrive("HDR15", 2017, "Brand1", 60, AccessoriesDiscount, "SSD", 3.5, 256), 4));
		availables.add(new Availables(new HardDrive("HDR16", 2017, "Brand1", 55, AccessoriesDiscount, "SSD", 1.8, 500), 3));
		availables.add(new Availables(new HardDrive("HDR17", 2017, "Brand1", 60, AccessoriesDiscount, "SSD", 2.5, 500), 8));
		availables.add(new Availables(new HardDrive("HDR18", 2017, "Brand1", 65, AccessoriesDiscount, "SSD", 3.5, 500), 3));
		availables.add(new Availables(new HardDrive("HDR19", 2017, "Brand1", 60, AccessoriesDiscount, "SSD", 1.8, 750), 5));
		availables.add(new Availables(new HardDrive("HDR20", 2017, "Brand1", 65, AccessoriesDiscount, "SSD", 2.5, 750), 7));
		availables.add(new Availables(new HardDrive("HDR21", 2017, "Brand1", 70, AccessoriesDiscount, "SSD", 3.5, 750), 10));
		availables.add(new Availables(new HardDrive("HDR22", 2017, "Brand1", 65, AccessoriesDiscount, "SSD", 1.8, 1000), 1));
		availables.add(new Availables(new HardDrive("HDR23", 2017, "Brand1", 70, AccessoriesDiscount, "SSD", 2.5, 1000), 9));
		availables.add(new Availables(new HardDrive("HDR24", 2017, "Brand1", 75, AccessoriesDiscount, "SSD", 3.5, 1000), 3));
		// Keyboards
		availables.add(new Availables(new Keyboard("KB1", 2018, "Brand3", 8, PeripheralsDiscount, "Wired"), 7));
		availables.add(new Availables(new Keyboard("KB2", 2018, "Brand3", 10, PeripheralsDiscount, "Wireless"), 4));
		//Mouses
		availables.add(new Availables(new Mouse("M1", 2018, "Brand3", 5, PeripheralsDiscount, "Wired", "Optical"), 8));
		availables.add(new Availables(new Mouse("M2", 2018, "Brand3", 6, PeripheralsDiscount, "Wired", "Laser"), 6));
		availables.add(new Availables(new Mouse("M3", 2018, "Brand3", 6, PeripheralsDiscount, "Wireless", "Optical"), 9));
		availables.add(new Availables(new Mouse("M4", 2018, "Brand3", 7, PeripheralsDiscount, "Wireless", "Laser"), 1));
		//Printers
		availables.add(new Availables(new Printer("PR1", 2018, "Brand4", 40, PeripheralsDiscount, "Laser", "Black and White"), 3));
		availables.add(new Availables(new Printer("PR2", 2018, "Brand4", 50, PeripheralsDiscount, "Laser", "Colored"), 10));
		availables.add(new Availables(new Printer("PR3", 2018, "Brand4", 55, PeripheralsDiscount, "Inkjet", "Black and White"), 8));
		availables.add(new Availables(new Printer("PR4", 2018, "Brand4", 60, PeripheralsDiscount, "Inkjet", "Colored"), 1));
		//Screens
		availables.add(new Availables(new Screen("SC1", 2018, "Brand5", 100, PeripheralsDiscount, "LCD", 15.5, "1024x624", "DisplayPort"), 4));
		availables.add(new Availables(new Screen("SC2", 2018, "Brand5", 120, PeripheralsDiscount, "LCD", 15.2, "1024x624", "HDMI"), 8));
		availables.add(new Availables(new Screen("SC3", 2018, "Brand5", 130, PeripheralsDiscount, "LCD", 15.3, "1024x624", "DVI"), 2));
		availables.add(new Availables(new Screen("SC4", 2018, "Brand5", 100, PeripheralsDiscount, "LED", 15.5, "1024x624", "DisplayPort"), 4));
		availables.add(new Availables(new Screen("SC5", 2018, "Brand5", 120, PeripheralsDiscount, "LED", 15.2, "1024x624", "HDMI"), 7));
		availables.add(new Availables(new Screen("SC6", 2018, "Brand5", 130, PeripheralsDiscount, "LED", 15.3, "1024x624", "DVI"), 1));
		
		
	}
	// These methods print all products of each category
	public void showMotherboards(){
		for(Availables item: this.availables){
			if (item.getProduct() instanceof Motherboard){
				System.out.println(item.toString());
			}
		}
	}
	public void showCPUs(){
		for(Availables item: this.availables){
			if (item.getProduct() instanceof CPU){
				System.out.println(item.toString());
			}
		}
	}
	public void showRAMs(){
		for(Availables item: this.availables){
			if (item.getProduct() instanceof RAM){
				System.out.println(item.toString());
			}
		}
	}
	public void showGraphicsCards(){
		for(Availables item: this.availables){
			if (item.getProduct() instanceof GraphicsCard){
				System.out.println(item.toString());
			}
		}
	}
	public void showHardDrives(){
		for(Availables item: this.availables){
			if (item.getProduct() instanceof HardDrive){
				System.out.println(item.toString());
			}
		}
	}
	public void showPrinters(){
		for(Availables item: this.availables){
			if (item.getProduct() instanceof Printer){
				System.out.println(item.toString());
			}
		}
	}
	public void showKeyboards(){
		for(Availables item: this.availables){
			if (item.getProduct() instanceof Keyboard){
				System.out.println(item.toString());
			}
		}
	}
	public void showMouses(){
		for(Availables item: this.availables){
			if (item.getProduct() instanceof Mouse){
				System.out.println(item.toString());
			}
		}
	}
	public void showScreens(){
		for(Availables item: this.availables){
			if (item.getProduct() instanceof Screen){
				System.out.println(item.toString());
			}
		}
	}
	
	// Prints all purchased products
	public void showSold(){
		if (forsale.isEmpty())
			System.out.println("You haven't purchased anything yet");
		else{
			System.out.println("\nALL PURCHASES");
			for(MySale item: forsale){
				System.out.println(item.toString());
			}
		}
	}
	// Prints all ordered products and 
	public void showOrdered(){
		if (fororder.isEmpty())
			System.out.println("You haven't ordered anything yet");
		else{
			System.out.println("\nALL ORDERS");
			for(MyOrder item: fororder){
				System.out.println("\nItem: "+item.getProductName()+"\nCode: "+item.getCode());
				if (item.Situation()==true)
					System.out.println("(completed)");
				else 
					System.out.println("(waiting)");
			}
			Scanner in = new Scanner(System.in);
			Scanner inn = new Scanner(System.in);
			System.out.println("\nDo you want to overview an order?\nPlease type 'yes' or 'no'");
			System.out.print(">");
			boolean right = true;
			while (right){ // for the yes/no answer
				boolean correct = true;
				String ans = in.nextLine();
				//while (correct){ // for the code
				if (ans.equalsIgnoreCase("yes")){
					right = false;
					correct = false;
					int count = 0;
					System.out.println("Please type the code of the order you want to overview.");
					System.out.print(">");
					boolean forcode = true;
					while (forcode == true){ // for the code 
						int an = in.nextInt();
						for (MyOrder item: fororder){
							if (item.getCode()==an){
								forcode = false;
								count++;
								System.out.println(item.toString());
								if (item.Situation()==false){
									System.out.println("Did you get the order?\nPlease type only 'yes' or 'no'");
									System.out.print(">");
									String a = inn.nextLine();
									boolean yep = true;
									while (yep){
										if (a.equalsIgnoreCase("yes")){
											item.setDone(true);
											addSold(new MySale(MySale.code, item.getProduct(), item.getName(), item.getPhone(), item.currentDay(), item.getFinalPrice()));
											MySale.codeIncrease();
											yep = false;
										}
										else if (a.equalsIgnoreCase("no")){
											yep=false;
											break;
										}
									}
								}
								else 
									break;
							}
						}
						//right = false;
						if (count==0){
							System.out.println("There isn't any product with this code.\nPlease try again.");
							System.out.print(">");
							forcode = true;
						}
					}
				}
				else if (ans.equalsIgnoreCase("no")){
					right = false;
					correct = false;
					break;
				}
				else {
					System.out.println("Please type only 'yes' or 'no'.");
					System.out.print(">");
					right = true;
					correct = true;
				}				
			}
		}
	}
	//Adds a sold product in the "forsale" list
	public void addSold(MySale sold){ 
		forsale.add(sold);
	}
	//Adds an ordered product in the "fororder" list
	public void addOrdered(MyOrder ordered){
		fororder.add(ordered);
	}
	public void addAvailables(Availables available){
		availables.add(available);
	}
	// Returns the product
	public Product getProduct(){
		return item;
	}
}