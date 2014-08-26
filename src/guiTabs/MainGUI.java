package guiTabs;

import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import order.StockDBControl;
import retailSystem.Login;
import retailSystem.PersonDB;

public class MainGUI extends JFrame implements ActionListener {

	private JTabbedPane tabbedPane;
	private PurchaseOrderTab purchaseOrderTab;
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
	private StockDBControl stockDBControl;
	private Login login;
	private JFileChooser fileChooser;
	private File file;
	private BufferedWriter writer;
	private StringBuilder stringBuilder;

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

	private JPanel contentPane;

	public MainGUI(PersonDB personDB, StockDBControl stockDBControl, Login login) {
		Toolkit tk = Toolkit.getDefaultToolkit();

		this.login = login;
		this.stockDBControl = stockDBControl;
		this.setVisible(true);

		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		this.setSize(xSize, ySize);

		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

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
		tabbedPane.setBackground(SystemColor.window);
		tabbedPane.setBounds(10, 10, xSize, ySize);
		contentPane.add(tabbedPane);

		// Set up the tabs, passing in the relevant data structures
		supplierTab = new SupplierTab(personDB, tabbedPane, this);
		staffTab = new StaffTab(personDB);
		customerTab = new CustomerTab(personDB);
		productTab = new ProductTab(stockDBControl, personDB, tabbedPane,
				supplierTab, this);
		stockControlTab = new StockControlTab(stockDBControl);
		customerOrderTab = new CustomerOrderTab(stockDBControl);
		// purchaseOrderTab = new PurchaseOrderTab(stockDBControl, personDB);

		// Add the tabs to the pane
		tabbedPane.addTab("Customer", null, customerTab, null);
		tabbedPane.addTab("Supplier", null, supplierTab, null);
		tabbedPane.addTab("Staff", null, staffTab, null);
		tabbedPane.addTab("Product", null, productTab, null);
		// tabbedPane.addTab("Purchase", null, purchaseOrderTab, null);
		tabbedPane.addTab("Customer Order", null, customerOrderTab, null);
		tabbedPane.addTab("Stock Control", null, stockControlTab, null);
	}

	// Action events for the different menu items
	// Note: It's bad practice to do action listeners in this way, even though
	// it
	// shortens the code.
	@Override
	public void actionPerformed(ActionEvent event) {

		// Exit option
		if (event.getActionCommand().equals("Exit")) {

			int answer = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to exit the program?",
					" CONFIRMATION ", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			System.out.println("Terminate Program!");
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
			String message = "DIT FCP-2014 GROUP 2 MEMBERS\n\n"
					+ "Conor Clarke\n" + "Peter Farrel\n" + "John Fleming\n"
					+ "Szabolcs Hutvagner\n" + "John O`Keeffe\n"
					+ "Roland Katona";
			JOptionPane.showMessageDialog(null, message);
		}

		// About option
		if (event.getActionCommand().equals("Open")) {
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
			}
		}

		// Save option (need to edit)
		if (event.getActionCommand().equals("Save")) {
			if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				String str = "ERFERERERE";
				/*
				 * try(FileWriter fw = new
				 * FileWriter(fileChooser.getSelectedFile()+".txt")) {
				 * fw.write(sb.toString()); } catch (IOException e) { //
				 * Auto-generated catch block e.printStackTrace(); } file =
				 * fileChooser.getSelectedFile();
				 */
				stringBuilder = new StringBuilder();
				productTab.buildProductDetailsString(stockDBControl
						.getStockList());

				try {
					writer = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(fileChooser.getSelectedFile()
									+ ".txt"), "utf-8"));
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
}