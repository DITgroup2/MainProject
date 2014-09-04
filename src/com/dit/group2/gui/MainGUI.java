package com.dit.group2.gui;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import com.dit.group2.person.Staff;
import com.dit.group2.retailSystem.RetailSystemDriver;

@SuppressWarnings("serial")
public class MainGUI extends JFrame implements ActionListener {

	private RetailSystemDriver driver;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JTabbedPane customerOrderTabbedPane;
	private JTabbedPane supplyOrderTabbedPane;
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
	private JMenu fileMenu;
	private JMenu userMenu;
	private JMenu helpMenu;
	private JMenuItem logoutMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem openMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem aboutMenuItem;
	private JMenuItem howToMenuItem;

	private JFileChooser fileChooser;
	private File file;
	private BufferedWriter writer;
	private StringBuilder stringBuilder;

	public MainGUI() {
	}

	/**
	 * Main GUI constructor
	 * 
	 * @param currentlyLoggedInStaff
	 * @param driver
	 */
	public MainGUI(Staff currentlyLoggedInStaff, RetailSystemDriver driver) {
		super();

		this.driver = driver;

		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = 850;
		int ySize = 580;
		int xPosition = ((((int) tk.getScreenSize().getWidth()) - xSize) / 2);
		int yPosition = ((((int) tk.getScreenSize().getHeight()) - ySize) / 2);
		setSize(950, 700);

		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Create menu items
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File", true);
		userMenu = new JMenu("User", true);
		helpMenu = new JMenu("Help", true);
		logoutMenuItem = new JMenuItem("Logout");
		exitMenuItem = new JMenuItem("Exit");
		openMenuItem = new JMenuItem("Open");
		saveMenuItem = new JMenuItem("Save");
		aboutMenuItem = new JMenuItem("About Group2");
		howToMenuItem = new JMenuItem("User Guide");

		// Add menu items to their relevant menu
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		userMenu.add(logoutMenuItem);
		userMenu.add(exitMenuItem);
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
		openMenuItem.addActionListener(this);
		saveMenuItem.addActionListener(this);
		aboutMenuItem.addActionListener(this);
		howToMenuItem.addActionListener(this);

		// Set up a tabbed pane system
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(xPosition, yPosition, xSize, ySize);
		contentPane.add(tabbedPane);

		customerOrderTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		customerOrderTabbedPane.setBounds(xPosition, yPosition, xSize - 50, ySize - 40);

		supplyOrderTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		supplyOrderTabbedPane.setBounds(xPosition, yPosition, xSize - 50, ySize - 40);

		// Set up the tabs, passing in the relevant data structures
		supplierTab = new SupplierTab(driver);
		staffTab = new StaffTab(driver);
		stockControlTab = new StockControlTab(driver);
		customerOrderTab = new CustomerOrderTab(currentlyLoggedInStaff, driver);
		customerTab = new CustomerTab(driver);
		productTab = new ProductTab(driver);
		customerOrderHistorytab = new CustomerOrderHistoryTab(driver);
		supplyOrderHistorytab = new SupplyOrderHistoryTab(driver);
		supplyOrderTab = new SupplyOrderTab(currentlyLoggedInStaff, driver);

		// Add the tabs to the pane
		tabbedPane.addTab("Customer", new ImageIcon("Images/CustomerIcon.jpg"), customerTab,
				"Select this tab to perform operations on customers.");
		tabbedPane.addTab("Supplier", new ImageIcon("Images/SupplierIcon.jpg"), supplierTab,
				"Select this tab to perform operations on suppliers.");
		tabbedPane.addTab("Staff", new ImageIcon("Images/StaffIcon.jpg"), staffTab,
				"Select this tab to perform operations on staff.");
		tabbedPane.addTab("Product", new ImageIcon("Images/ProductIcon.jpg"), productTab,
				"Select this tab to perform operations on products.");

		tabbedPane.addTab("Customer Order", new ImageIcon("Images/CustomerOrderIcon.jpg"),
				customerOrderTab, "Select this tab to perform operations on customer orders.");
		tabbedPane.addTab("Stock Control", new ImageIcon("Images/StockControlIcon.jpg"),
				stockControlTab, "Select this tab to show stock list.");
		supplyOrderTabbedPane.addTab("Supply Order", null, supplyOrderTab, null);
		supplyOrderTabbedPane.addTab("Supply Order History", null, supplyOrderHistorytab, null);
		customerOrderTabbedPane.addTab("Customer Order", null, customerOrderTab,
				"Select this tab to show customer order.");
		customerOrderTabbedPane.addTab("Customer Order History", null, customerOrderHistorytab,
				"Select this tab to show customer order history.");
		tabbedPane
				.addTab("Customer Order", new ImageIcon("Images/CustomerOrderIcon.jpg"),
						customerOrderTabbedPane,
						"Select this tab to view details on orders for customers.");
		tabbedPane.addTab("Supply Order", new ImageIcon("Images/SupplyOrderIcon.jpg"),
				supplyOrderTabbedPane, "Select this tab to view details on orders from suppliers.");

		// Add tool tips to menu items
		fileMenu.setToolTipText("<html>This is the file menu.<br/> Click to open previous files, or to save current progress.</html>");
		userMenu.setToolTipText("<html>This is the user menu.<br/> Click to logout, or to exit the program.</html>");
		helpMenu.setToolTipText("<html>This is the help menu.<br/> Click to access the user guide.</html>");

		setVisible(true);
	}

	/** Action events for the different menu items */
	@Override
	public void actionPerformed(ActionEvent event) {

		// Exit option
		if (event.getActionCommand().equals("Exit")) {

			int answer = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to exit the program?", " CONFIRMATION ",
					JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			System.out.println("Terminate Program!");
		}

		// Logout option
		if (event.getActionCommand().equals("Logout")) {
			setVisible(false);
			driver.getLogin().clearFields();
			driver.getLogin().setVisible(true);

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
				String str = "ERFERERERE";
				/*
				 * try(FileWriter fw = new FileWriter(fileChooser.getSelectedFile()+".txt")) {
				 * fw.write(sb.toString()); } catch (IOException e) { // Auto-generated catch block
				 * e.printStackTrace(); } file = fileChooser.getSelectedFile();
				 */
				stringBuilder = new StringBuilder();
				productTab.buildProductDetailsString(driver.getStockDB().getStockList());

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
	}

	public static void openWebpage(String urlString) {
		try {
			File htmlFile = new File(urlString);
			Desktop.getDesktop().browse(htmlFile.toURI());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	public CustomerOrderTab getCustomerOrderTab() {
		return customerOrderTab;
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

	public JTabbedPane getCustomerOrderTabbedPane() {
		return customerOrderTabbedPane;
	}
}
