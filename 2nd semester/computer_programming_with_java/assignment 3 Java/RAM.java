public class RAM extends Accessories{
	private int frequency;
	private int capacity; 
	public enum Type{
		DDR2, DDR3, DDR4;
	}
	private Type type;
	
	// Constructor
	public RAM(String name, int year, String constructor, double price, double Discount, String TheType, int capacity, int TheFrequency){
		super(name, year, constructor, price, Discount);
		type = Type.valueOf(TheType);
		this.capacity = capacity;
		frequency = TheFrequency;
	}
	
	// Setters
	public void setRAMType(String TheType){
		type = Type.valueOf(TheType);
	}
	public void setRAMMemory(int capacity){
		if (capacity==4 || capacity==8 || capacity==16)
			this.capacity = capacity;
	}
	public void setFrequency(int TheFrequency){
		if (TheFrequency==1600 || TheFrequency==2666 || TheFrequency==4600)
			frequency = TheFrequency;
	}
	
	// Getters
	public Type getRAMType(){
		return type;
	}
	
	public int getRAMMemory(){
		return capacity;
	}
	public int getFrequency(){
		return frequency;
	}
	
	// toString
	public String toString(){
		return (super.toString()+"\nRAM Type: "+getRAMType()+"\nCapacity: "+getRAMMemory()+" GB\nFrequency: "+getFrequency()+" MHz");
	}
}


