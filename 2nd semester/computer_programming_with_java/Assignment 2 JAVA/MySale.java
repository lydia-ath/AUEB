import java.time.*;
import java.time.format.*;

public class MySale {
	
	public static int code = 1;
	private Product item;
	private String name;
	private String phone;
	private int prcode = code;
	private double finalprice;
	MyCatalogs catalogs = new MyCatalogs();
	
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
		for (MySale sale: catalogs.forsale){
			if (sale.getCode()==this.code)
				codeIncrease();
		}
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
	public double getFinalPrice(){
		return finalprice;
	}
	public String getProductName(){
		return item.getProName();
	}
	// Setters 
	public void setName(String name){
		this.name = name;
	}
	public void setPhone(String Phone){
		this.phone = phone;
	}
	public void setFinalPrice(double finalprice){
		this.finalprice = finalprice;
	}
	
	
	// toString
	public String toString(){
		return "\n\nSale's Code: "+getCode()+item.toString()+"\nFinal Price: "+getFinalPrice()+" euros\n\nCustomer's Name: "+getName()+"\nCustomer's Phone: "+getPhone()+"\nDate of Sale: "+currentDay();
	}
}