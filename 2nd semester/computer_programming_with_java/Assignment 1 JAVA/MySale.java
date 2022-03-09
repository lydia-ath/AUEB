import java.time.*;
import java.time.format.*;
public class MySale {
	public static int code = 1;
	private Product item;
	private static String name;
	private static String phone;
	private int prcode = code;
	private double finalprice;
	
	// Calculate current day as a String in dd/MM/yyyy form 
	LocalDate localDate = LocalDate.now(); 
	DateTimeFormatter today = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 	
	private String salesday = today.format(localDate);
	
	// Constructor
	public MySale(int code, Product item, String name, String phone, String salesday, double finalprice){
		this.code = code;
		this.item = item;
		this.name = name;
		this.phone = phone;
		this.salesday = salesday;
		this.finalprice = finalprice;
	}
	
	// Increases the code of order from 1 to n so that each code is a unique serial number 
	public static void codeIncrease(){
		code++;
	}
	
	// Getters
	public int getCode(){
		return prcode;
	}
	public Product getProduct(){
		return item;
	}
	public double getPrice(){
		return item.getPrice();
	}
	public String currentDay(){ 
		return salesday;
	}
	public String getName(){
		return name;
	}
	public String getPhone(){
		return phone;
	}

	// Setters 
	public void setName(String name){
		this.name = name;
	}
	public void setPhone(String Phone){
		this.phone = phone;
	}
	
	// toString
	public String toString(){
		return "\nSale's Code: "+getCode()+item.toString()+"\nCustomer's Name: "+getName()+"\nCustomer's Phone: "+getPhone()+"\nDate of Sale: "+currentDay();
	}
}