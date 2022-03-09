import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class InfoOrder extends JFrame implements ActionListener {
	private MyOrder a;
	private JButton but1;
	private JLabel label;
	private DefaultListModel listModel;
	private JTextArea resultArea;
	private MyCatalogs catalogs;
	MySale sale;
	
	//Constructor
	public InfoOrder(MyOrder a,MyCatalogs catalogs) {
		this.a = a;
		this.catalogs = catalogs;
		setTitle("Orders' Details");
		drawFrame();
		but1.addActionListener(this); // Buy
		setVisible(true); 
	}
	
	// For the button 'Done.Buy'
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == but1){        // BUY DONE
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
			sale = new MySale(MySale.code, a.getProduct(), name, phone, a.currentDay(), a.getFinalPrice());
			catalogs.addSold(sale);
			mainApp.full2.addElement("("+sale.getCode()+")   "+sale.getProductName()); 
			MySale.codeIncrease();
			a.setDone(true);
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
		but1 = new JButton("Done. Buy.");
		paneButton.add(but1);
		resultArea =  new JTextArea(a.toString(),10,20);
		resultArea.setFont(new Font("Serif", Font.ITALIC, 18)); 
		resultArea.setForeground(Color.BLUE);
		resultArea.setEditable(false);
		paneButton.add(resultArea);
		cp.add(paneButton, BorderLayout.LINE_START);
		cp.add(label, BorderLayout.LINE_END);
		pack();
	}
	
}

