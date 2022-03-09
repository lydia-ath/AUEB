public class Mouse extends Peripherals{
	private String technology;
	private String connection;
	private static final String CONNECTION_WIRED = "Wired";
	private static final String CONNECTION_WIRELESS = "Wireless";
	private static final String TECHNOLOGY_LASER = "Laser";
	private static final String TECHNOLOGY_OPTICAL = "Optical";
	
	// Constructor
	public Mouse (String name, int year, String constructor, double price, double Discount, String TheConnection, String TheTechnology){
		super(name, year, constructor, price, Discount);
		connection = TheConnection;
		technology = TheTechnology;
	}
	
	// To check whether it has a wired or a wireless connection 
	public boolean isWired(){
		if (connection.equalsIgnoreCase(CONNECTION_WIRED))
			return true;
		else 
			return false;
	}	
	public boolean isWireless(){
		if (connection.equalsIgnoreCase(CONNECTION_WIRELESS))
			return true;
		else 
			return false;
	}
	
	// To check whether it works with laser or optical technology
	public boolean isLaser(){
		if (technology.equalsIgnoreCase(TECHNOLOGY_LASER))
			return true;
		else 
			return false;
	}	
	public boolean isOptical(){
		if (technology.equalsIgnoreCase(TECHNOLOGY_OPTICAL))
			return true;
		else 
			return false;
	}
	
	//Setters
	public void setConnection(String TheConnection){
		if (TheConnection.equalsIgnoreCase(CONNECTION_WIRED) || TheConnection.equalsIgnoreCase(CONNECTION_WIRELESS))
			connection = TheConnection;
	}
	public void setTechnology(String TheTechnology){
		if (TheTechnology.equalsIgnoreCase(TECHNOLOGY_LASER) || TheTechnology.equalsIgnoreCase(TECHNOLOGY_OPTICAL))
			technology = TheTechnology;
	}
	
	// Getters
	public String getConnection(){
		if (isWired())
			return CONNECTION_WIRED;
		else if (isWireless())
			return CONNECTION_WIRELESS;
		else 
			return null;
	}
	public String getTechnology(){
		if (isLaser())
			return TECHNOLOGY_LASER;
		else if (isOptical())
			return TECHNOLOGY_OPTICAL;
		else 
			return null;
	}
	
	// toString
	public String toString(){
		return (super.toString()+"\nConnection: "+getConnection()+"\nTechnology: "+getTechnology());
	}
}