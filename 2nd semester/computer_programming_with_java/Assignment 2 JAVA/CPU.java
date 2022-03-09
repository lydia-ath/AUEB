public class CPU extends Accessories{
	private double speed;
	private int numberOfCores;
	private boolean graphics;
	
	// Constructor
	public CPU(String name, int year, String constructor, double price, double Discount, double TheSpeed, int cores, boolean TheGraphics){
		super(name, year, constructor, price, Discount);
		speed = TheSpeed;
		numberOfCores = cores;
		graphics = TheGraphics;
	}
	
	// Setters
	public void setSpeed(double TheSpeed){
		if (TheSpeed==3 || TheSpeed==3.6 || TheSpeed==4)
			speed = TheSpeed;
	}
	public void setNumberOfCores(int cores){
		if (cores==6 || cores==8 || cores==16)
			numberOfCores = cores;
	}
	public void setGraphics(boolean Graphics){
		graphics= Graphics;
	}
	
	// Getters
	public double getSpeed(){
		return speed;
	}
	public int getNumberOfCores(){
		return numberOfCores;
	}
	public boolean hasGraphics(){
		return graphics;
	}
	// Returns if it has graphics as a String
	public String getGraphics(){
		if (hasGraphics()==true)
			return "YES";
		else 
			return "NO";
	}
	
	// toString
	public String toString(){
		return (super.toString()+"\nSpeed: "+getSpeed()+" GHz\nNumber of Cores: "+getNumberOfCores()+"\nGraphics: "+getGraphics());
	}
}

