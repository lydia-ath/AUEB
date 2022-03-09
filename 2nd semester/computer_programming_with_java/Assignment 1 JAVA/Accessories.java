public class Accessories extends Product{
	public double discount;
	
	// Constructor
	public Accessories(String name, int year, String constructor, double firstprice,double Discount){
		super(name, year, constructor, firstprice);
		discount = Discount;
	}
	
	// Setters
	public void setDiscount(double Discount){
		discount = Discount;
	}
	
	// Getters
	public double getPrice(){    // Returns the price with discount
		return firstprice - firstprice*discount;
	}
	public double getDiscount(){
		return discount;
	}
	
	// toString
	public String toString(){
		return (super.toString()+"\nDiscount: "+100*getDiscount()+"%"+"\nFinal Price: "+getPrice()+" euros");
	}
}
