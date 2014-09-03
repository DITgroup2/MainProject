package guiTabs;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import order.OrderDB;
import order.StockDBControl;
import person.Staff;
import retailSystem.Login;
import retailSystem.PersonDB;

public class MainGUI extends JFrame implements ActionListener {

	private JTabbedPane tabbedPane;
	private CustomerOrderHistoryTab customerOrderHistorytab;
	private SupplyOrderHistoryTab supplyOrderHistorytab;
	private SupplyOrderTab supplyOrderTab;
	private CustomerOrderTab customerOrderTab;
	private StockControlTab stockControlTab;
	private StaffTab staffTab;
	private ProductTab productTab;
	private SupplierTab supplierTab;
	private CustomerTab customerTab;
	private JMenuBar menuBar;
	private JMenu fileMenu, userMenu, helpMenu;
	private JMenuItem logoutMenuItem, exitMenuItem, openMenuItem, saveMenuItem, aboutMenuItem,
			howToMenuItem;
	private StockDBControl stockDBControl;
	private OrderDB orderDB;
	private Login login;
	private JFileChooser fileChooser;
	private File file;
	private BufferedWriter writer;
	private StringBuilder stringBuilder;
	private AccountingTab accountingTab;
	private JPanel pane;
	private ButtonGroup group;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public StringBuilder getStringBuilder() {
		return stringBuilder;
	}

	public void setStringBuilder(String string) {
		stringBuilder.append(string);
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public SupplierTab getSupplierTab() {
		return supplierTab;
	}

	public ProductTab getProductTab() {
		return productTab;
	}

	public CustomerTab getCustomerTab() {
		return customerTab;
	}

	public StaffTab getStaffTab() {
		return staffTab;
	}

	public StockControlTab getStockControlTab() {
		return stockControlTab;
	}

	public CustomerOrderHistoryTab getCustomerOrderHistorytab() {
		return customerOrderHistorytab;
	}

	public SupplyOrderHistoryTab getSupplyOrderHistorytab() {
		return supplyOrderHistorytab;
	}

	public AccountingTab getAccountingTab() {
		return accountingTab;
	}

	/**
	 * @return the stockDBControl
	 */
	public StockDBControl getStockDBControl() {
		return stockDBControl;
	}

	private JPanel contentPane;
	private JMenuItem settingsMenuItem;

	/**
	 * Main GUI constructor
	 * 
	 * @param personDB
	 *            Person database
	 * @param stockDBControl
	 *            Stock control database
	 */
	public MainGUI(Staff currentlyLoggedInStaff, PersonDB personDB, StockDBControl stockDBControl,
			Login login, OrderDB orderDB) {
		Toolkit tk = Toolkit.getDefaultToolkit();

		this.orderDB = orderDB;
		this.login = login;
		this.stockDBControl = stockDBControl;
		this.setVisible(true);
		group = new ButtonGroup();

		int xSize = 850;
		int ySize = 580;
		int xPosition = ((((int) tk.getScreenSize().getWidth()) - xSize) / 2);
		int yPosition = ((((int) tk.getScreenSize().getHeight()) - ySize) / 2);

		setSize(900, 650);

		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		int menuTabSize = 16;
		int menuItemsSize = 16;
		// Create menu items
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File", true);
		fileMenu.setFont(new Font("Serif", Font.BOLD, menuTabSize));
		userMenu = new JMenu("User", true);
		userMenu.setFont(new Font("Serif", Font.BOLD, menuTabSize));
		helpMenu = new JMenu("Help", true);
		helpMenu.setFont(new Font("Serif", Font.BOLD, menuTabSize));

		logoutMenuItem = new JMenuItem("Logout");
		exitMenuItem = new JMenuItem("Exit");
		openMenuItem = new JMenuItem("Open");
		saveMenuItem = new JMenuItem("Save");
		settingsMenuItem = new JMenuItem("Preferences");
		aboutMenuItem = new JMenuItem("About Group2");
		howToMenuItem = new JMenuItem("User Guide");

		logoutMenuItem.setFont(new Font("Serif", Font.PLAIN, menuItemsSize));
		exitMenuItem.setFont(new Font("Serif", Font.PLAIN, menuItemsSize));
		openMenuItem.setFont(new Font("Serif", Font.PLAIN, menuItemsSize));
		saveMenuItem.setFont(new Font("Serif", Font.PLAIN, menuItemsSize));
		settingsMenuItem.setFont(new Font("Serif", Font.PLAIN, menuItemsSize));
		aboutMenuItem.setFont(new Font("Serif", Font.PLAIN, menuItemsSize));
		howToMenuItem.setFont(new Font("Serif", Font.PLAIN, menuItemsSize));

		// Add menu items to their relevant menu
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(exitMenuItem);
		userMenu.add(settingsMenuItem);
		userMenu.add(logoutMenuItem);
		helpMenu.add(howToMenuItem);
		helpMenu.add(aboutMenuItem);

		// Add the menus to the menu bar

		menuBar.add(fileMenu);
		menuBar.add(userMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);

		// Append listeners for the menu items
		logoutMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);
		settingsMenuItem.addActionListener(this);
		openMenuItem.addActionListener(this);
		saveMenuItem.addActionListener(this);
		aboutMenuItem.addActionListener(this);
		howToMenuItem.addActionListener(this);

		// Set up a tabbed pane system
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(SystemColor.window);
		tabbedPane.setBounds(xPosition, yPosition, xSize, ySize);
		contentPane.add(tabbedPane);

		// Set up the tabs, passing in the relevant data structures
		supplierTab = new SupplierTab(personDB, tabbedPane, this);
		staffTab = new StaffTab(personDB);
		stockControlTab = new StockControlTab(stockDBControl);
		customerOrderTab = new CustomerOrderTab(currentlyLoggedInStaff, personDB, stockDBControl,
				orderDB, this);
		customerTab = new CustomerTab(personDB, customerOrderTab);
		productTab = new ProductTab(stockDBControl, personDB, tabbedPane, supplierTab, this);
		supplyOrderTab = new SupplyOrderTab(currentlyLoggedInStaff, personDB, stockDBControl,
				orderDB, this);
		customerOrderHistorytab = new CustomerOrderHistoryTab(orderDB);
		supplyOrderHistorytab = new SupplyOrderHistoryTab(orderDB);
		accountingTab = new AccountingTab(stockDBControl, orderDB);

		// Add the tabs to the pane
		tabbedPane.addTab("Customer", new ImageIcon("Images/CustomerIcon.jpg"), customerTab,
				"Perform operations on customers.");
		tabbedPane.addTab("Supplier", new ImageIcon("Images/SupplierIcon.jpg"), supplierTab,
				"Perform operations on suppliers.");
		tabbedPane.addTab("Staff", new ImageIcon("Images/StaffIcon.jpg"), staffTab,
				"Perform operations on staff.");
		tabbedPane.addTab("Product", new ImageIcon("Images/ProductIcon.jpg"), productTab,
				"Perform operations on products.");
		tabbedPane.addTab("Customer Order", new ImageIcon("Images/CustomerOrderIcon.jpg"),
				customerOrderTab, "Perform operations on customer orders.");
		tabbedPane.addTab("Stock Control", new ImageIcon("Images/StockControlIcon.jpg"),
				stockControlTab, "Show stock list.");
		tabbedPane.addTab("Customer Order History", null, customerOrderHistorytab,
				"Show customer order history.");
		tabbedPane.addTab("Supply Order History", null, supplyOrderHistorytab,
				"Show supply order history.");
		tabbedPane.addTab("Accounts", null, accountingTab, "Show accounts and history.");
	}

	@Override
	/**
	 * Action listeners
	 */
	public void actionPerformed(ActionEvent event) {

		// Exit option
		if (event.getActionCommand().equals("Exit")) {

			int answer = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to exit the program?", " CONFIRMATION ",
					JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}

		// Logout option
		if (event.getActionCommand().equals("Logout")) {
			this.setVisible(false);
			login.clearFields();
			login.setVisible(true);

			supplierTab.cancelButton.doClick();
			staffTab.cancelButton.doClick();
			customerTab.cancelButton.doClick();
			productTab.cancelButton.doClick();

			supplierTab.cancelEditButton.doClick();
			staffTab.cancelEditButton.doClick();
			customerTab.cancelEditButton.doClick();
			productTab.cancelEditButton.doClick();

			tabbedPane.setSelectedComponent(customerTab);
		}

		// About option
		if (event.getActionCommand().equals("About Group2")) {
			String message = "DIT FCP-2014 GROUP 2 MEMBERS\n\n" + "Conor Clarke\n"
					+ "Peter Farrel\n" + "John Fleming\n" + "Szabolcs Hutvagner\n"
					+ "John O`Keeffe\n" + "Roland Katona";
			JOptionPane.showMessageDialog(null, message);
		}
		// User guide option
		if (event.getActionCommand().equals("User Guide")) {
			try {
				// Runtime.getRuntime().exec("notepad userGuide.txt");
				openWebpage("UserGuide/UserGuide.html");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		// About option
		if (event.getActionCommand().equals("Open")) {
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
			}
		}

		// Save option
		if (event.getActionCommand().equals("Save")) {
			if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				/*
				 * try(FileWriter fw = new FileWriter(fileChooser.getSelectedFile()+".txt")) {
				 * fw.write(sb.toString()); } catch (IOException e) { // Auto-generated catch block
				 * e.printStackTrace(); } file = fileChooser.getSelectedFile();
				 */
				stringBuilder = new StringBuilder();
				productTab.buildProductDetailsString(stockDBControl.getStockList());

				try {
					writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
							fileChooser.getSelectedFile() + ".txt"), "utf-8"));
					writer.write("Something " + stringBuilder);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				finally {
					try {
						writer.close();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		// Preferences option
		if (event.getActionCommand().equals("Preferences")) {
			JFrame f = new JFrame();
			f.setBounds(100, 100, 600, 420);
			pane = new JPanel();
			pane.setBorder(new EmptyBorder(5, 5, 5, 5));
			f.setContentPane(pane);
			contentPane.setLayout(null);

			JRadioButton rb1, rb2, rb3;

			rb1 = new JRadioButton("Aluminium");
			rb1.setActionCommand("Aluminium");
			rb1.setBounds(23, 96, 109, 23);
			group.add(rb1);

			rb2 = new JRadioButton("HiFi");
			rb2.setActionCommand("HiFi");
			rb2.setBounds(23, 122, 109, 23);
			group.add(rb2);

			rb3 = new JRadioButton("Aero", true);
			rb3.setActionCommand("Aero");
			rb3.setBounds(23, 148, 109, 23);
			group.add(rb3);

			pane.add(rb1);
			pane.add(rb2);
			pane.add(rb3);

			JLabel lblTheme = new JLabel("Theme");
			lblTheme.setBounds(27, 75, 46, 14);
			pane.add(lblTheme);
			JButton b = new JButton("Select");
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					group.getSelection().getActionCommand();
				}
			});
			f.add(b);
			f.setVisible(true);
		}
		if (event.getActionCommand().equals("Aluminium")) {
			com.jtattoo.plaf.aluminium.AluminiumLookAndFeel.setTheme("Default",
					"INSERT YOUR LICENSE KEY HERE", "my company");
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
			}
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (event.getActionCommand().equals("HiFi")) {
			com.jtattoo.plaf.hifi.HiFiLookAndFeel.setTheme("Default",
					"INSERT YOUR LICENSE KEY HERE", "my company");
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
			}
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (event.getActionCommand().equals("Aero")) {
			com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme("Gold", "INSERT YOUR LICENSE KEY HERE",
					"my company");
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
			}
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Open a web page given by the String
	 * 
	 * @param urlString
	 */
	public void openWebpage(String urlString) {
		try {
			File htmlFile = new File(urlString);
			Desktop.getDesktop().browse(htmlFile.toURI());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}