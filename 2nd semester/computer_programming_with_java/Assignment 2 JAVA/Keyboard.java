public class Keyboard extends Peripherals{
	private String connection;
	private static final String CONNECTION_WIRED = "Wired";
	private static final String CONNECTION_WIRELESS = "Wireless";

	// Constructor
	public Keyboard (String name, int year, String constructor, double price, double Discount, String TheConnection){
		super(name, year, constructor, price, Discount);
		connection = TheConnection;
	}
	
	//To check whether it's wired or wireless connected
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
	
	//Setters
	public void setConnection(String TheConnection){
		if (TheConnection.equalsIgnoreCase(CONNECTION_WIRED) || TheConnection.equalsIgnoreCase(CONNECTION_WIRELESS))
			connection = TheConnection;
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
	
	// toString
	public String toString(){
		return (super.toString()+"\nConnection: "+getConnection());
	}
}