import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.io.FileWriter.*;
public class Info extends JFrame implements ActionListener {
	private Availables a;
	private JButton but1;
	private JButton but2;
	private JLabel label;
	private JTextArea resultArea;
	private MyCatalogs catalogs;
	MyOrder order;
	MySale sale;
	
	//Constructor
	public Info(Availables a, MyCatalogs catalogs) {
		this.a = a;
		this.catalogs = catalogs;
		setTitle("Product's Details");
		drawFrame();
		if (a.getAvailability()>0)
			but1.addActionListener(this); // Buy
		else 
			but2.addActionListener(this); // Order
		setVisible(true); 
	}
	
	// For the buttons 'Buy' and 'Order'
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == but1){        // BUY
			String name = (String)JOptionPane.showInputDialog(this,"Please type your name");
			String phone = (String)JOptionPane.showInputDialog(this,"Please type your phone number");
			while (true){
				if(isNumber(phone)==true){
					break;
				}
				else{
					phone = (String)JOptionPane.showInputDialog(this,"Please type your phone number");
					isNumber(phone);
				}	
			}
			sale = new MySale(MySale.code, a.getProduct(), name, phone, a.currentDay(), a.getProductPrice());
			catalogs.addSold(sale);
			mainApp.full2.addElement("("+MySale.code+")   "+sale.getProductName()); 
			MySale.codeIncrease();
			a.decreaseAvailability();
		}
		else if (e.getSource() == but2) {  // ORDER
			String name = (String)JOptionPane.showInputDialog(this,"Please type your name");
			String phone = (String)JOptionPane.showInputDialog(this,"Please type your phone number");
			while (true){
				if(isNumber(phone)==true){
					break;
				}
				else{
					phone = (String)JOptionPane.showInputDialog(this,"Please type your phone number");
					isNumber(phone);
				}	
			}
			order = new MyOrder(MyOrder.code, a.getProduct(), name, phone, a.currentDay(), a.arrivalDay(), a.getProductPrice(), false);
			catalogs.addOrdered(order);
			mainApp.full1.addElement("("+MyOrder.code+")   "+order.getProductName());
			MyOrder.codeIncrease();
			a.decreaseAvailability();
		}
	}//actionPerformed
	
	// To check if the phone number is valid
	public static boolean isNumber(String string){
		int count = 0;
		for (int i=0; i<string.length();i++){
			if (Character.isDigit(string.charAt(i))==false)
				count++;
			else if (string.length()!=10){
				JOptionPane.showMessageDialog(null, "Please type a 10-digit number.", "Error", JOptionPane.PLAIN_MESSAGE);
				return false;
			}
		}
		if (count==0) 
			return true;
		else{ 
			JOptionPane.showMessageDialog(null, "Please type a 10-digit number.", "Error", JOptionPane.PLAIN_MESSAGE); 
			return false;
		}
		//setVisible(true);
	}
	
	// To make the frame
	public void drawFrame(){
		setBounds(300, 300, 350, 150);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		label = new JLabel();
		label.setIcon(new ImageIcon(a.getProduct().getImagePath()));
		Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
		JPanel paneButton = new JPanel();
		paneButton.setLayout(new FlowLayout());
		if (a.getAvailability()>0) {
			but1 = new JButton("Buy");
			paneButton.add(but1);
		}
		else {
			but2 = new JButton("Order");
			paneButton.add(but2);
		}
		resultArea =  new JTextArea(a.toString(),10,20);
		resultArea.setFont(new Font("Serif", Font.ITALIC, 18)); //kathorizw th grammatoseira 
		resultArea.setForeground(Color.BLUE);
		resultArea.setEditable(false);
		paneButton.add(resultArea);
		cp.add(paneButton, BorderLayout.LINE_START);
		cp.add(label, BorderLayout.LINE_END);
		pack();
	}
	
}






