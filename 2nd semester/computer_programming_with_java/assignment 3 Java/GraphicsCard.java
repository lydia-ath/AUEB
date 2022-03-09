public class GraphicsCard extends Accessories{
	private String chipset;
	private int memory; 
	private static final String TYPE_nVidia = "nVidia";
	private static final String TYPE_AMD = "AMD";
	
	// Constructor
	public GraphicsCard(String name, int year, String constructor, double price, double Discount, String TheChipset, int TheMemory){
		super(name, year, constructor, price, Discount);
		chipset = TheChipset;
		memory = TheMemory;
	}
	
	// To check whether its chipset's type is nVIdia or AMD
	public boolean isnVidia(){
		if (chipset.equalsIgnoreCase(TYPE_nVidia))
			return true;
		else 
			return false;
	}
	public boolean isAMD(){
		if (chipset.equalsIgnoreCase(TYPE_AMD))
			return true;
		else 
			return false;
	}
	
	//Setters
	public void setChipset(String TheChipset){
		if (TheChipset.equalsIgnoreCase(TYPE_AMD) || TheChipset.equalsIgnoreCase(TYPE_nVidia))
			chipset = TheChipset;
	}
	public void setGraphicsMemory(int TheMemory){
		if (TheMemory==2 || TheMemory==4 || TheMemory==6)
			memory = TheMemory;
	}

	// Getters
	public String getChipset(){
		if (isnVidia())
			return TYPE_nVidia;
		else if (isAMD())
			return TYPE_AMD;
		else 
			return null;
	}
	public int getGraphicsMemory(){
		return memory;
	}
	
	//toString
	public String toString(){
		return (super.toString()+"\nChipset's Type: "+getChipset()+"\nMemory: "+getGraphicsMemory()+" GB");
	}
}
