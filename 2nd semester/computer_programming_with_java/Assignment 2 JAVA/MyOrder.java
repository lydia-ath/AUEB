import java.time.*;
import java.time.format.*;

public class MyOrder {
	
	public static int code=1;
	public int prcode = code;
	public final static int plusdays = 3; //how many days till the arrival day
	private Product item; 
	private String name;
	private String phone;
	private double finalprice; 
	private boolean done; // true=completed, false=waiting
	MyCatalogs catalogs = new MyCatalogs();


	// Calculate current day as a String in dd/MM/yyyy form
	LocalDate localDate = LocalDate.now(); 
	DateTimeFormatter curday = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 	
	private String today = curday.format(localDate); 
	
	LocalDate a = localDate.plusDays(plusdays);
	DateTimeFormatter b = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public String arrday = b.format(a);
	
	// Constructor
	public MyOrder(int code, Product item, String name, String phone, String today, String arrday, double finalprice, boolean done){
		this.item = item;
		this.code = code;
		this.name = name;
		this.phone = phone;
		this.today = today;
		this.arrday = arrday;
		this.finalprice = finalprice;
		this.done = done;
	}

	// Increases the code of order from 1 to n so that each code is a unique serial number 
	public static void codeIncrease(){
		code++;
	}
	
	// Getters
	public boolean Situation(){
		return done;
	}
	public int getCode(){
		for (MyOrder order: catalogs.fororder){
			if (order.getCode()==this.code)
				codeIncrease();
		}
		return prcode;
	}
	public String currentDay(){ 
		return today;
	}
	public String getName(){
		return name;
	}
	public String getPhone(){
		return phone;
	}
	public double getFinalPrice(){
		return finalprice;
	}
	public String getProductName(){
		return item.getProName();
	}
	public Product getProduct(){
		return item;
	}
	// Returns the arrival day
	public String arrivalDay(){
		return arrday;
	}
	// Returns the situation of the order placed as a String
	public String situation(){
		if (Situation()==true)
			return "Completed";
		else 
			return "Waiting";
	}
	
	//Setters
	public void setDone(boolean Done){
			done = Done;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setPhone(String Phone){
		this.phone = phone;
	}
	public void setFinalPrice(double finalprice){
		this.finalprice = finalprice;
	}
	public void setCode(int code){
		this.code = code;
	}
	// toString 
	public String toString(){
		return "\n\nOrder's Code: "+getCode()+item.toString()+"\nFinal Price: "+getFinalPrice()+" euros\n\nCustomer's Name: "+getName()+ "\nCustomer's Phone: "+getPhone()+"\nDate of Order: "+currentDay()+"\nEstimated Date of Arrival: "+arrivalDay()+"\nOrder's Situation: "+situation();
	}
}

