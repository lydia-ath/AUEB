import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class InfoSale extends JFrame {
	private MySale a;
	private JLabel label;
	private DefaultListModel listModel;
	private JTextArea resultArea;
	private MyCatalogs catalogs;
	
	//Constructor
	public InfoSale(MySale a, MyCatalogs catalogs) {
		this.a = a;
		this.catalogs = catalogs;
		setTitle("Sales' Details");
		drawFrame();
		setVisible(true); 
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

