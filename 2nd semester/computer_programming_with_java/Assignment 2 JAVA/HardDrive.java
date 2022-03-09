public class HardDrive extends Accessories{
	private String type;
	private double size;
	private int capacity; 
	private static final String TYPE_HDD = "HDD";
	private static final String TYPE_SSD = "SSD";

	// Constructor
	public HardDrive(String name, int year, String constructor, double price, double Discount, String TheType, double TheSize, int TheMemory){
		super(name, year, constructor, price, Discount);
		type = TheType;
		size = TheSize;
		capacity = TheMemory;
	}
	
	// To check whether its type is HDD or SSD
	public boolean isHDD(){
		if (type.equalsIgnoreCase(TYPE_HDD))
			return true;
		else 
			return false;
		
	}	public boolean isSSD(){
		if (type.equalsIgnoreCase(TYPE_SSD))
			return true;
		else 
			return false;
	}
	
	// Setters
	public void setDriveType(String TheType){
		if (TheType.equalsIgnoreCase(TYPE_HDD) || TheType.equalsIgnoreCase(TYPE_SSD))
			type = TheType;
	}
	public void setSize(double TheSize){
		if (TheSize==1.8 || TheSize==2.5 || TheSize==3.5)
			size = TheSize;
	}
	public void setDriveMemory(int TheMemory){
		if (TheMemory==256 || TheMemory==500 || TheMemory==750 || TheMemory==1000)
			capacity = TheMemory;
	}
	
	// Getters
	public String getDriveType(){
		if (isHDD())
			return TYPE_HDD;
		else if (isSSD())
			return TYPE_SSD;
		else 
			return null;
	}
	public double getSize(){
		return size;
	}
	public int getMemory(){
		return capacity;
	}
	
	//toString
	public String toString(){
		return (super.toString()+"\nType: "+getDriveType()+"\nSize: "+getSize()+" inches"+"\nCapacity: "+getMemory()+" GB");
	}
}
