package guiTabs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import order.StockDBControl;
import order.StockItem;
import person.Person;
import retailSystem.Product;

public class StockControlTab extends JPanel implements ActionListener, ItemListener {

	protected boolean emptiedList;
	private StockDBControl stockDBControl;
	private ArrayList<Object> products;
	protected Vector<String> comboboxItems;
	protected Product product;
	protected String name;
	protected JTextArea productField;
	protected JTextArea quantityField;
	protected JTextArea idField;
	protected JTextArea descriptionField;
	protected JLabel productLabel;
	protected JLabel productIdLabel;
	protected JLabel quantityLabel;
	protected JButton restockButton;

	public StockControlTab(StockDBControl stockDBControl) {

		// Initialisation
		this.setStockDBControl(stockDBControl);
		productLabel = new JLabel("Product Name");
		quantityLabel = new JLabel("Quantity");
		productIdLabel = new JLabel("Product ID");
		idField = new JTextArea();
		productField = new JTextArea();
		productField.setEditable(false);
		quantityField = new JTextArea();
		quantityField.setEditable(false);
		idField.setEditable(false);
		restockButton = new JButton("Restock");

		// Set dimensions of buttons
		restockButton.addActionListener(this);
		restockButton.setBounds(209, 320, 100, 23);
		productIdLabel.setBounds(9, 10, 93, 14);
		idField.setBounds(9, 50, 93, 300);
		productLabel.setBounds(100, 10, 93, 14);
		productField.setBounds(100, 50, 200, 300);
		quantityLabel.setBounds(309, 10, 93, 14);
		quantityField.setBounds(309, 50, 100, 300);

		// Finish setup and start the GUI
		addAllElements();
		setLayout(null);
		setVisible(true);
		setProductTextField(0, stockDBControl.getStockList());
		setQuantityTextField(0, stockDBControl.getStockList());
	}

	/**
	 * Add every element to the pans
	 */
	public void addAllElements() {
		add(idField);
		add(productIdLabel);
		add(productLabel);
		add(productField);
		add(quantityLabel);
		add(quantityField);
		// add(restockButton);
	}

	/**
	 * Set the text to appear in the product field
	 * 
	 * @param index
	 *            The selected index
	 * @param list
	 *            The list of items and the quantity in stock
	 */
	public void setProductTextField(int index, ArrayList<StockItem> list) {

		for (StockItem stockItem : list) {
			Product p = stockItem.getProduct();
			String prod = p.getProductName();
			idField.append("\n" + p.getProductID());
			productField.append("\n " + prod);
		}
		// Unless there are no items in the list, set the status of the lsit to non empty
		if (list.size() > 0) {
			emptiedList = false;
		}
	}

	/**
	 * Set the text to appear in the quantity field
	 * 
	 * @param index
	 *            The selected index
	 * @param list
	 *            The list of items and the quantity in stock
	 */
	public void setQuantityTextField(int index, ArrayList<StockItem> list) {

		for (StockItem stockItem : list) {
			int quantity = stockItem.getQuantity();
			quantityField.append("\n" + quantity);
		}

		if (list.size() > 0) {
			emptiedList = false;
		}
	}

	/**
	 * Restock items
	 * 
	 * @param person
	 * @param list
	 *            The list of items
	 */
	public void Restock(Person person, ArrayList<Person> list) {
		boolean valid = false;
		String value = JOptionPane.showInputDialog(null, "Enter how many :", 0);
		do {
			try {
				int quantity = Integer.parseInt(value);
				valid = true;
			}
			catch (NumberFormatException error) {
				value = JOptionPane.showInputDialog(null, "Wrong input!!Enter how many :", 0);
				error.printStackTrace();
			}
		}
		while (!valid);
		System.out.println(value);
	}

	/**
	 * Action listeners
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == restockButton) {
			Restock(null, null);
		}
	}

	/**
	 * @return Stock database controls
	 */
	public StockDBControl getstockDBControl() {
		return stockDBControl;
	}

	/**
	 * Sets the stock database controls
	 * 
	 * @param stockDBControl
	 */
	public void setStockDBControl(StockDBControl stockDBControl) {
		this.stockDBControl = stockDBControl;
	}

	/**
	 * Event listener for the combo box
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
	}
}
