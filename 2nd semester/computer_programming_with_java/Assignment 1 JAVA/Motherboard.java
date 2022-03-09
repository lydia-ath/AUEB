public class Motherboard extends Accessories{
	private String cpuType;
	private int numberOfSATA; 
	private int capacity; 
	private static final String TYPE_INTEL = "Intel";
	private static final String TYPE_AMD = "AMD";
	
	// Constructor
	public Motherboard (String name, int year, String constructor, double firstprice, double discount, int capacity, String TheCpuType, int TheNumberOfSATA){
		super(name, year, constructor, firstprice, discount);
		this.capacity = capacity;
		cpuType = TheCpuType;
		numberOfSATA = TheNumberOfSATA;
		
	}
	
	// To check whether its type is Intel or AMD
	public boolean isIntel(){
		if(cpuType.equalsIgnoreCase(TYPE_INTEL))
			return true;
		else 
			return false;
	}
	public boolean isAMD(){
		if(cpuType.equalsIgnoreCase(TYPE_AMD))
			return true;
		else 
			return false;
	}
	
	// Setters
	public void setType(String TheCpuType){
		if (TheCpuType.equalsIgnoreCase(TYPE_AMD) || TheCpuType.equalsIgnoreCase(TYPE_INTEL))
			cpuType = TheCpuType;
	}	
	public void setNumberOfSATA(int numberOfSATA){
		if (numberOfSATA==4 || numberOfSATA==6 || numberOfSATA==8)
			this.numberOfSATA = numberOfSATA;
	}
	public void setMemory(int memory){
		if (memory==32 || memory==64 || memory==128)
			capacity = memory;
	}	
	
	// Getters
	public String getCPUType(){
		if (isIntel())
			return TYPE_INTEL;
		else if (isAMD())
			return TYPE_AMD;
		else 
			return null;
	}
	public int getNumberOfSATA(){
		return numberOfSATA;
	}
	public int getMemory(){
		return capacity;
	}
	
	/* Getter of Price is inheritated by Accessories*/	
	
	// toString
	public String toString(){
		return (super.toString()+"\nCapacity: "+getMemory()+" GB\nCPU Type: "+getCPUType()+"\nNumber of SATA: "+getNumberOfSATA());
	}
}
