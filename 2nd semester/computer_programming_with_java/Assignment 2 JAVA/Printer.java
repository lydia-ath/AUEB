public class Printer extends Peripherals{
	private String technology;
	private String printingType;
	private static final String PRINTINGTYPE_COLORED = "Colored";
	private static final String PRINTINGTYPE_BLACK_WHITE= "Black and White";
	private static final String TECHNOLOGY_LASER = "Laser";
	private static final String TECHNOLOGY_INKJET = "Inkjet";

	// Constructor
	public Printer (String name, int year, String constructor, double price, double Discount, String TheTechnology, String ThePrintingType){
		super(name, year, constructor, price, Discount);
		technology = TheTechnology;
		printingType = ThePrintingType;
	}
	
	// To check whether it works with laser or with inkjet technology
	public boolean isLaser(){
		if (technology.equalsIgnoreCase(TECHNOLOGY_LASER))
			return true;
		else 
			return false;
	}	
	public boolean isInkjet(){
		if (technology.equalsIgnoreCase(TECHNOLOGY_INKJET))
			return true;
		else 
			return false;
	}	

	// To check whether it prints in color or in black and white
	public boolean isColored(){
		if (printingType.equalsIgnoreCase(PRINTINGTYPE_COLORED))
			return true;
		else 
			return false;
	}
	public boolean isBlackAndWhite(){
		if (printingType.equalsIgnoreCase(PRINTINGTYPE_BLACK_WHITE))
			return true;
		else 
			return false;
	}	
	
	//Setters
	public void setTechnology(String TheTechnology){
		if (TheTechnology.equalsIgnoreCase(TECHNOLOGY_INKJET) || TheTechnology.equalsIgnoreCase(TECHNOLOGY_LASER))
			technology = TheTechnology;
	}
	public void setPrintingType(String ThePrintingType){
		if (ThePrintingType.equalsIgnoreCase(PRINTINGTYPE_BLACK_WHITE) || ThePrintingType.equalsIgnoreCase(PRINTINGTYPE_COLORED))
			printingType = ThePrintingType;
	}
	
	// Getters
	public String getTechnology(){
		if (isLaser())
			return TECHNOLOGY_LASER;
		else if (isInkjet())
			return TECHNOLOGY_INKJET;
		else 
			return null;
	}
	public String getPrintingType(){
		if (isColored())
			return PRINTINGTYPE_COLORED;
		else if (isBlackAndWhite())
			return PRINTINGTYPE_BLACK_WHITE;
		else 
			return null;
	}
	
	// toString
	public String toString(){
		return (super.toString()+"\nTechnology: "+getTechnology()+"\nPrinting Type: "+getPrintingType());
	}
}