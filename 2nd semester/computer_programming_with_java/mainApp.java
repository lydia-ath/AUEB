/*  KONSTADINIDIS THOMAS, 2o, 3160074
	KOUZOUMPASI THEMELINA, 1o, 3170076
	VERROIOU MARIA - ADELAIS, 1o, 3170017 
*/
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;


public class mainApp extends JFrame implements ActionListener, MouseListener {

	JFrame frame;
	JMenu menu;
	JMenuItem j1,j2,j3,j4,j5,j6,j7;
	JMenuBar menubar;
	JPanel availables_panel;
	JPanel sales_panel;
	JPanel orders_panel;
	JTabbedPane tabs;
	JPanel tabPanel;
	static JList list,list2;
	static JList list1;
	MyProducts serv1;
	Order serv2;
	Sale serv3;
	MyOrder ord;
	MySale sal;
	MyCatalogs catalogs = new MyCatalogs();
	int forlist = 0;
	Availables avail;  
	boolean bool;
	static DefaultListModel full1,full2;
	DefaultListModel sales;

	public static void main(String[] args) {
        new mainApp();
    }
    public mainApp () {

   		/** Create Main Frame (frame) */
   		frame = new JFrame("Our Store"); 
    	frame.setSize(1200,600);
    	frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/** Create menubar with 3 menu */
		JMenu menu = new JMenu("File");     // the 3 main options in the menubar
		JMenu menu1 = new JMenu("Sort Sales"); 
		JMenu menu2 = new JMenu("Sort Orders"); 

		/** Create 3 item for menu */
		j1 = new JMenuItem("Load Availables"); // Adding the option "Load Availables" in option 'File'
		j1.addActionListener(this);
		menu.add(j1);
	
		j2 = new JMenuItem("Load Orders"); // Adding the option "Load Orders"  in option 'File'
		j2.addActionListener(this);
		menu.add(j2);
		
		j3 = new JMenuItem("Load Sales"); // Adding the option "Load Sales"  in option 'File'
		j3.addActionListener(this);
		menu.add(j3);
		
		j4 = new JMenuItem("by Category"); // Adding the option "by Category"  in option 'Sort Sales'
		j4.addActionListener(this);
		menu1.add(j4);
		j6 = new JMenuItem("by Category"); // Adding the option "by Category"  in option 'Sort Orders'
		j6.addActionListener(this);
		menu2.add(j6);
		
		j5 = new JMenuItem("by Product Name"); // Adding the option "by Product Name"  in option 'Sort Sales'
		j5.addActionListener(this);
		menu1.add(j5);
		j7 = new JMenuItem("by Product Name"); // Adding the option "by Product Name"  in option 'Sort Orders'
		j7.addActionListener(this);
		menu2.add(j7);

		/** Add menu,menu1,menu2 to menubar */
		menubar = new JMenuBar();
		menubar.add(menu);
		menubar.add(menu2);
		menubar.add(menu1);

		/** Add menubar to frame (PAGE_START on MainFrame) */
		frame.add(menubar,BorderLayout.PAGE_START);

		/** Create tabPanel (TabbedPane) with 3 tabs (availables_panel, orders_panel, sales_panel) */ 
		availables_panel = new JPanel();
		sales_panel = new JPanel();
		orders_panel = new JPanel();
		tabs = new JTabbedPane();
		tabs.addTab("Availables", availables_panel);
		tabs.addTab("Orders", orders_panel);
		tabs.addTab("Sales", sales_panel);
		tabPanel = new JPanel(); //panel tou tab 
    	tabPanel.setLayout(new GridLayout(2,1));
    	tabPanel.setPreferredSize(new Dimension(400,400));
    	tabPanel.add(tabs);
		
		/** To add the categories 'Accessories' and 'Peripherals' in the availables_panel **/
		DefaultListModel listModel = new DefaultListModel();                          
		listModel.addElement("Accessories");
		listModel.addElement("Peripherals");
		list = new JList(listModel);
		//list.setSelectedIndex(0);
		list.addMouseListener(this);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(350, 200));
		availables_panel.add(listScroller);
	
		/** To add all orders in the order_panel**/
		DefaultListModel orders = new DefaultListModel();
		list1 = new JList (orders);
		list1.addMouseListener(this);
		JScrollPane listScroller1 = new JScrollPane(list1);
		listScroller1.setPreferredSize(new Dimension(350, 200));
		orders_panel.add(listScroller1);
		bool = false;
		
		/** To add all sales in the sales_panel**/
		sales = new DefaultListModel();
		list2 = new JList (sales);
		list2.addMouseListener(this);
		JScrollPane listScroller2 = new JScrollPane(list2);
		listScroller2.setPreferredSize(new Dimension(350, 200));
		sales_panel.add(listScroller2);
		
		/** Add tabPanel to frame  (LINE_START on MainFrame)*/
		frame.add (tabPanel,BorderLayout.LINE_START);

		/** Create WindowListener for Main Frame (frame) on windowClosing */
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) { 
			}
		});
     	frame.setVisible(true);
   	} // constructor mainApp
	
    // When user selects something from the three panels
	public void mouseClicked(MouseEvent e){
		// To show all the categories of Accessories or Peripherals
		if (forlist == 0){              
			if (e.getClickCount()==2){
				int i = list.getSelectedIndex();
				if (i == 0){ // if option 'Accessories' is clicked 
					DefaultListModel subAccessories = new DefaultListModel();
					list.setModel(subAccessories);
					subAccessories.addElement("Motherboards");
					subAccessories.addElement("CPUs");
					subAccessories.addElement("RAMs");
					subAccessories.addElement("Graphics Cards");
					subAccessories.addElement("Hard Drives");
					forlist = 1;
					list.addMouseListener(this);
				}
				else if (i == 1){ // if option 'Peripherals' is clicked 
					DefaultListModel subPeripherals = new DefaultListModel();
					list.setModel(subPeripherals);
					subPeripherals.addElement("Screens");
					subPeripherals.addElement("Keyboards");
					subPeripherals.addElement("Mouses");
					subPeripherals.addElement("Printers");
					forlist = 2;
					list.addMouseListener(this);
				}
			}
		}
		// To show all products of the selected category(Accessories)
		else if (forlist == 1){
			if (e.getClickCount()==2){
				int i = list.getSelectedIndex();
				if (i == 0){
					DefaultListModel motherboards = new DefaultListModel();
					list.setModel(motherboards);
					for (Availables a : MyCatalogs.availables) 
						if (a.getProduct() instanceof Motherboard)
							motherboards.addElement(a.getProduct().getProName()+"   (Price :"+a.getProductPrice()+" euros)");
					forlist = 3;
					list.addMouseListener(this);
				}
				else if (i == 1){
					DefaultListModel cpus = new DefaultListModel();
					list.setModel(cpus);
					for (Availables a : MyCatalogs.availables)   
						if (a.getProduct() instanceof CPU)
							cpus.addElement(a.getProduct().getProName()+"   (Price :"+a.getProductPrice()+" euros)");
					forlist = 3;
					list.addMouseListener(this);
				}
				else if (i == 2){
					DefaultListModel rams = new DefaultListModel();
					list.setModel(rams);
					for (Availables a : MyCatalogs.availables)   
						if (a.getProduct() instanceof RAM)
							rams.addElement(a.getProduct().getProName()+"   (Price :"+a.getProductPrice()+" euros)");
					forlist = 3;
					list.addMouseListener(this);
				}
				else if (i == 3){
					DefaultListModel graphics = new DefaultListModel();
					list.setModel(graphics);
					for (Availables a : MyCatalogs.availables)   
						if (a.getProduct() instanceof GraphicsCard)
							graphics.addElement(a.getProduct().getProName()+"   (Price :"+a.getProductPrice()+" euros)");
					forlist = 3;
					list.addMouseListener(this);
				}
				else if (i == 4){
					DefaultListModel harddrives = new DefaultListModel();
					list.setModel(harddrives);
					for (Availables a : MyCatalogs.availables) 
						if (a.getProduct() instanceof HardDrive)
							harddrives.addElement(a.getProduct().getProName()+"   (Price :"+a.getProductPrice()+" euros)");
					forlist = 3;
					list.addMouseListener(this);
				}
			}
		}
		// To show all products of the selected category(Peripherals)
		else if (forlist == 2){
			if (e.getClickCount()==2){
				int i = list.getSelectedIndex();
				if (i == 0){ 
					DefaultListModel screens = new DefaultListModel();
					list.setModel(screens);
					for (Availables a : MyCatalogs.availables) 
						if (a.getProduct() instanceof Screen)
							screens.addElement(a.getProduct().getProName()+"   (Price :"+a.getProductPrice()+" euros)");
					forlist = 3;
					list.addMouseListener(this);
				}
				else if (i == 1){
					DefaultListModel keyboards = new DefaultListModel();
					list.setModel(keyboards);
					for (Availables a : MyCatalogs.availables)   
						if (a.getProduct() instanceof Keyboard)
							keyboards.addElement(a.getProduct().getProName()+"   (Price :"+a.getProductPrice()+" euros)");
					forlist = 3;
					list.addMouseListener(this);
				}
				else if (i == 2){
					DefaultListModel mouses = new DefaultListModel();
					list.setModel(mouses);
					for (Availables a : MyCatalogs.availables)   
						if (a.getProduct() instanceof Mouse)
							mouses.addElement(a.getProduct().getProName()+"   (Price :"+a.getProductPrice()+" euros)");
					forlist = 3;
					list.addMouseListener(this);
				}
				else if (i == 3){
					DefaultListModel printers = new DefaultListModel();
					list.setModel(printers);
					for (Availables a : MyCatalogs.availables)   
						if (a.getProduct() instanceof Printer)
							printers.addElement(a.getProduct().getProName()+"   (Price :"+a.getProductPrice()+" euros)");
					forlist = 3;
					list.addMouseListener(this);
				}
			}
		}
		// To open a new window with the selected product's details
		else if (forlist==3 && tabs.getSelectedIndex()==0){
			if (e.getClickCount()==2) {
				try {
					String b = list.getSelectedValue().toString();
					int words = 1;
					String c = "";
					for (int i=0;i<b.length();i++){
						if (b.charAt(i) == ' ')
							words--;
						if (words==0)
							c = b.substring(0,i);
					}
					for (Availables a : MyCatalogs.availables){
						if (c.equalsIgnoreCase(a.getProductName())) {
							avail = a;
							break;
						}
					}
					new Info(avail,catalogs);
				}
				catch (NullPointerException d) {}
			}
		}
		// In orders_panel to show orders' details
		if (tabs.getSelectedIndex() == 1){
			if (e.getClickCount()==2) {
				try {
					String b = list1.getSelectedValue().toString();
					int words = 1;
					int tanta = 100000;
					String c = "";
					for (int i=0;i<b.length();i++){
						if (b.charAt(i) == ' ')
							words--;
						if (words==0) {
							c = b.substring(0,i);
							c = c.replace("(", "");
							c = c.replace(")", "");
							tanta = Integer.parseInt(c);
						}
					}
					for (MyOrder a : catalogs.fororder){ 
						if (tanta == a.getCode()) {
							ord = a;
							break;
						}
					}
					new InfoOrder(ord,catalogs);
				}
				catch (NullPointerException d) {}
			}
		}
		// In sales_panel to show sales' details
		if (tabs.getSelectedIndex() == 2){
			if (e.getClickCount()==2) {
				try {
					String b = list2.getSelectedValue().toString();
					int words = 1;
					int tanta = 100000;
					String c = "";
					for (int i=0;i<b.length();i++){
						if (b.charAt(i) == ' ')
							words--;
						if (words==0) {
							c = b.substring(0,i);
							c = c.replace("(", "");
							c = c.replace(")", "");
							tanta = Integer.parseInt(c);
						}
					}
					for (MySale a : catalogs.forsale){
						if (tanta == a.getCode()) {
							sal = a;
							break;
						}
					}
					new InfoSale(sal,catalogs);
				}
				catch (NullPointerException d) {}
			}
		}
	}

	// When user presses 'File' or 'Sort Orders' or 'Sort Sales'
	public void actionPerformed(ActionEvent e) {
		File selectedFile;
		JFileChooser fc = new JFileChooser();
		int returnValue;
		serv1 = new MyProducts();
		serv2 = new Order();
		serv3 = new Sale();

		/** 	Event if user click on j1 (Menu Item "Load Availables") */
		if (e.getSource() == j1) {                             // LOAD AVAILABLES
			returnValue = fc.showOpenDialog(null);
			/** Open File Chooser */
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				/** User choose file */
				selectedFile = fc.getSelectedFile();
				if (selectedFile.getName().equals("PARARTIMA1.txt")) {
					serv1.loadFile(selectedFile.getName());
					for (Availables it : serv1.StoreProducts){
						catalogs.addAvailables(it);
						for (int i=1;i<MyCatalogs.availables.size()+1;i++)
							it.getProduct().setImagePath("Images/"+i+".jpg");
					} 
				}
			}
		}
		/** 	Event if user click on j2 (Menu Item "Load Orders") */
		else if (e.getSource() == j2) {                             // LOAD ORDERS	
			returnValue = fc.showOpenDialog(null);
			/** Open File Chooser */
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				/** User choose file */
				selectedFile = fc.getSelectedFile();
				if (selectedFile.getName().equals("order.txt")) {
					serv2.loadFile(selectedFile.getName());
					full1 = new DefaultListModel();
					list1.setModel(full1);
					for (MyOrder it : serv2.Orders){
						catalogs.addOrdered(it);
						full1.addElement("("+it.getCode()+")   "+it.getProductName());
					}
				}
			}
		}
		/** 	Event if user click on j3 (Menu Item "Load SALES") */
		else if (e.getSource() == j3) {                             // LOAD SALES
			returnValue = fc.showOpenDialog(null);
			/** Open File Chooser */
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				/** User choose file */
				selectedFile = fc.getSelectedFile();
				if (selectedFile.getName().equals("sale.txt")) {
					serv3.loadFile(selectedFile.getName());
					full2 = new DefaultListModel();
					list2.setModel(full2);
					for (MySale it : serv3.Sales){
						catalogs.addSold(it);
						full2.addElement("("+it.getCode()+")   "+it.getProductName());
					}
				}
			}
		}
		/*Sort Sales by Category*/
		if (e.getSource() == j4) {
			String category = (String)JOptionPane.showInputDialog(this,"Please type the sold products' category you want to see");
			DefaultListModel salescategory = new DefaultListModel();
			bool = false;
			for (MySale a : catalogs.forsale) {
				if (category.equalsIgnoreCase(a.getProduct().getClass().getName())) {
					salescategory.addElement("("+a.getCode()+")   "+a.getProductName());
					bool = true;
				}
			}
			if (bool==true)
				list2.setModel(salescategory);
		}
		/* Sort Orders by Category */
		if (e.getSource() == j6) {
			String category1 = (String)JOptionPane.showInputDialog(this,"Please type the ordered products' category you want to see");
			DefaultListModel ordersname = new DefaultListModel();
			bool = false;
			for (MyOrder a : catalogs.fororder) {
				if (category1.equalsIgnoreCase(a.getProduct().getClass().getName())) {
					ordersname.addElement("("+a.getCode()+")   "+a.getProductName());
					bool = true;
				}
			}
			if (bool==true)
				list1.setModel(ordersname);
		}
		/* Sort Sales by Product Name*/
		if (e.getSource() == j5) {
			String name = (String)JOptionPane.showInputDialog(this,"Please type the sold products' name you want to see");
			DefaultListModel salesname = new DefaultListModel();
			bool = false;
			for (MySale a : catalogs.forsale) {
				if (name.equalsIgnoreCase(a.getProductName())) {
					salesname.addElement("("+a.getCode()+")   "+a.getProductName());
					bool = true;
				}
			}
			if (bool==true)
				list2.setModel(salesname);
		}
		/* Sort Orders by Product Name*/
		if (e.getSource() == j7) {
			String name1 = (String)JOptionPane.showInputDialog(this,"Please type the ordered products' name you want to see");
			DefaultListModel ordersname = new DefaultListModel();
			bool = false;
			for (MyOrder a : catalogs.fororder) {
				if (name1.equalsIgnoreCase(a.getProductName())) {
					ordersname.addElement("("+a.getCode()+")   "+a.getProductName());
					bool = true;
				}
			}
			if (bool==true)
				list1.setModel(ordersname);
		}
	} //actionPerformed
	public void mouseExited(MouseEvent event){}
	public void mouseEntered(MouseEvent event){}
	public void mouseReleased(MouseEvent event){}
	public void mousePressed(MouseEvent event){}
}
	