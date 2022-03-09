// the super class that has as direct subclasses the Accessories and the Peripherals
abstract class Product{
	private String name;		
	private int year;			
	private String constructor;	
	protected double firstprice;	// model's price without any discount
	private String imagepath;
	
	// Constructor
	public Product(String name,int year,String constructor,double firstprice){
		this.name = name; 				
		this.year = year; 				
		this.constructor = constructor; 
		this.firstprice = firstprice; 			
	}
	
	// Setters
	public void setName(String name){
		this.name = name;
	}
	public void setConstructor(String constructor){
		this.constructor = constructor;
	}
	public void setYear(int year){
		this.year = year;
	}
	public void setFirstPrice(double firstprice){
		this.firstprice = firstprice;
	}
	public void setImagePath(String path){
		imagepath = path;
	}
    
	// Getters
	public String getProName(){
		return name;
	}
	public String getConstructor(){
		return constructor;
	}
	public int getYear(){     
		return year;
	}
	public double getFirstPrice(){
		return firstprice;
	}
	public String getImagePath(){
		return imagepath;
	}
	abstract public double getPrice();
	
	// toString
	public String toString(){
		return ("\nName: "+getProName()+"\nYear: "+getYear()+"\nConstructor: "+getConstructor()+"\nFirst Price: "+getFirstPrice()+" euros");
	}
}

