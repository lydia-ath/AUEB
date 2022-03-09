// This class connects each product with its availability 
import java.time.*;
import java.time.format.*;

public class Availables{
	private Product item;
	private int availability;
	
	// Calculate current day and day of arrival as a String 
	LocalDate localDate = LocalDate.now(); 
	DateTimeFormatter today = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 	
	private String salesday = today.format(localDate);
	
	// Constructor
	public Availables(Product item, int availability){
		this.item = item;
		this.availability = availability;
	}
	
	// Once a product gets bought or ordered, this method decreases the amount left in stock
	public void decreaseAvailability(){
		if (availability>0)
			availability--;
	}
	// Getters
	public Product getProduct(){
		return item;
	}
	public int getAvailability(){
		return availability;
	}
	public String getProductName(){
		return item.getProName();
	}
	public double getProductPrice(){
		return item.getPrice();
	}
	public String currentDay(){ 
		return salesday;
	}
	
	//Returns the estimated date of the product's arrival 
	public String arrivalDay(){
		LocalDate a = localDate.plusDays(MyOrder.plusdays);
		DateTimeFormatter b = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String arday = b.format(a);
		return arday;
	}
	// toString
	public String toString(){
		return item.toString()+ "\nAvailability: "+getAvailability();
	}
}