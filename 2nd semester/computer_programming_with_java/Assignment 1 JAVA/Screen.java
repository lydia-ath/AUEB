public class Screen extends Peripherals{
	private String type;
	private double dimension;
	private String resolution;
	private static final String TYPE_LCD = "LCD";
	private static final String TYPE_LED = "LED";
	public enum Port{
		DisplayPort, HDMI, DVI;
	}
	private Port port;

	// Constructor
	public Screen (String name, int year, String constructor, double firstprice, double Discount, String TheType, double TheDimension, String TheResolution, String ThePort){
		super(name, year, constructor, firstprice, Discount);
		type = TheType;
		dimension = TheDimension;
		resolution = TheResolution;
		port = Port.valueOf(ThePort);
	}
	
	// Checks whether the type is LCD 
	public boolean isLCD(){
		if (type.equalsIgnoreCase(TYPE_LCD))
			return true;
		else 
			return false;
	}	
	public boolean isLED(){
		if (type.equalsIgnoreCase(TYPE_LED))
			return true;
		else 
			return false;
	}	
	
	//Setters
	public void setPort(Port ThePort){
		port = ThePort;
	}
	public void setType(String TheType){
		if (TheType.equalsIgnoreCase(TYPE_LCD) || TheType.equalsIgnoreCase(TYPE_LED))
			type = TheType;
	}
	public void setDimension(double TheDimension){
		dimension = TheDimension;	
	}
	public void setResolution(String TheResolution){
		resolution = TheResolution;
	}
	
	//Getters
	public double getDimension(){
		return dimension;
	}
	public String getResolution(){
		return resolution;
	}
	public String getType(){
		if (isLCD())
			return TYPE_LCD;
		else if (isLED())
			return TYPE_LED;
		else 
			return null;
	}
	public Port getPort(){
		return port;
	}
	
	// toString
	public String toString(){
		return (super.toString()+"\nScreen Type: "+getType()+"\nDimension: "+getDimension()+"''\nResolution: "+getResolution()+"\nPort: "+getPort());
	}
}