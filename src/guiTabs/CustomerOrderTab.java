package guiTabs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import order.StockDBControl;
import order.StockItem;
import retailSystem.Product;

public class CustomerOrderTab extends JPanel implements ActionListener, ItemListener {

	private JScrollPane scrollpane;
	private StockDBControl stockDBControl;
	protected Vector<String> comboBoxItems = new Vector<String>();
	protected ArrayList<Integer> ItemsQuantity = new ArrayList<Integer>();
	protected ArrayList<Double> ItemsPrice = new ArrayList<Double>();
	protected DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>(
			comboBoxItems);
	protected JComboBox<String> productComboBox = new JComboBox<String>(comboBoxModel);

	protected Vector<String> orderingListItems = new Vector<String>();
	protected DefaultListModel<String> listModel = new DefaultListModel<String>();
	protected JList<String> orderingList = new JList<String>(listModel);

	// If you use a StockItem object you only need one list here. It has methods
	// for setting the product and the quantity.
	protected ArrayList<Product> currentOrderList = new ArrayList<Product>();
	protected ArrayList<Integer> currentOrderListQuantity = new ArrayList<Integer>();

	protected JLabel productComboBoxLabel, quantityLabel, availableQuantityLabel,
			availableQuantityField, priceLabel, priceField, subtotalLabel, subtotalField;
	protected JTextField quantityTextField;
	protected JButton addButton;
	protected double subTotal = 0;

	public CustomerOrderTab(StockDBControl StockDBControl) {

		this.stockDBControl = StockDBControl;
		scrollpane = new JScrollPane(orderingList);

		setUpProductComboBox();
		setUpQuantityTextField();
		setUpAvailableQuantity();
		setUpPriceFields();
		setAddButton();
		setUpSubtotal();
		orderingList();

		setLayout(null);
		setVisible(true);
	}

	/**
	 * Add a drop down list of products
	 */
	private void setUpProductComboBox() {
		productComboBoxLabel = new JLabel("Products");
		productComboBoxLabel.setBounds(60, 10, 100, 20);
		productComboBox.setBounds(60, 30, 200, 20);
		add(productComboBoxLabel);
		add(productComboBox);
		refreshComboBox(stockDBControl.getStockList());
		productComboBox.addItemListener(this);
	}

	/**
	 * Add field to select number of products
	 */
	private void setUpQuantityTextField() {
		quantityLabel = new JLabel("pc(s)");
		quantityLabel.setBounds(270, 10, 50, 20);
		quantityTextField = new JTextField("1");
		quantityTextField.setBounds(270, 30, 30, 20);
		add(quantityLabel);
		add(quantityTextField);
	}

	/**
	 * Initialise fields to set the quantity of product available
	 */
	private void setUpAvailableQuantity() {
		availableQuantityLabel = new JLabel("Available");
		availableQuantityLabel.setBounds(330, 10, 70, 20);

		availableQuantityField = new JLabel(Integer.toString(ItemsQuantity.get(0)));
		availableQuantityField.setBounds(340, 30, 40, 20);
		add(availableQuantityLabel);
		add(availableQuantityField);
	}

	/**
	 * Add product price fields
	 */
	private void setUpPriceFields() {
		priceLabel = new JLabel("Price");
		priceLabel.setBounds(400, 10, 40, 20);
		priceField = new JLabel(Double.toString(ItemsPrice.get(0)));
		priceField.setBounds(400, 30, 40, 20);
		add(priceLabel);
		add(priceField);
	}

	/**
	 * Add Sub total price fields
	 */
	private void setUpSubtotal() {
		subtotalLabel = new JLabel("Total:");
		subtotalLabel.setBounds(290, 300, 50, 20);
		subtotalField = new JLabel("0");
		subtotalField.setBounds(350, 300, 80, 20);
		add(subtotalLabel);
		add(subtotalField);
	}

	/**
	 * Set up an add button
	 */
	private void setAddButton() {
		addButton = new JButton("Add");
		addButton.setBounds(60, 60, 60, 20);
		addButton.addActionListener(this);
		add(addButton);
	}

	/**
	 * Add scrollable text field for the ordering list
	 */
	private void orderingList() {
		scrollpane.setBounds(60, 100, 410, 180);
		add(scrollpane);
	}

	/**
	 * Refreshes combo Box for price and quantity
	 * 
	 * @param oList
	 *            The order list
	 */
	private void refreshComboBox(ArrayList<StockItem> oList) {
		// ha még egyszer le akarjuk futtatni, akkor itt ki kell kapcsolni a
		// combo listener-jént
		// és a mûvelet után visszakapcsolni!!! Hogy hogyan, nem tudom...
		comboBoxItems.clear();
		ItemsQuantity.clear();
		ItemsPrice.clear();
		for (StockItem stockItem : oList) {
			comboBoxItems.add(stockItem.getProduct().getProductName());
			ItemsQuantity.add(stockItem.getQuantity());
			ItemsPrice.add(stockItem.getProduct().getRetailPrice());
		}
		productComboBox.setSelectedIndex(0);
	}

	/**
	 * Refreshes the Tab
	 * 
	 * @param index
	 */
	private void refreshTab(int index) {
		availableQuantityField.setText(Integer.toString(ItemsQuantity.get(index)));
		priceField.setText(Double.toString(ItemsPrice.get(index)));
		quantityTextField.setText("1");
	}

	/**
	 * Event listener for the combo box. The combo box selects the produc to add to the order.
	 */
	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			refreshTab(productComboBox.getSelectedIndex());

		}
	}

	/**
	 * Action listeners
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// If the add button has been clicked
		if (e.getSource() == addButton) {

			// Add product and quantity to the current order list
			Product tempProduct = stockDBControl.getStockList().get(
					productComboBox.getSelectedIndex()).getProduct();
			int tempQuantity = (Integer.parseInt(quantityTextField.getText()));
			currentOrderList.add(tempProduct);
			currentOrderListQuantity.add(tempQuantity);

			// Display the above in a text field
			orderingListItems.add(comboBoxItems.get(productComboBox.getSelectedIndex()));
			listModel.addElement(comboBoxItems.get(productComboBox.getSelectedIndex())
					+ "\t\t   "
					+ quantityTextField.getText()
					+ " \t\t  "
					+ ((Double.parseDouble(priceField.getText()) * (Double
							.parseDouble(quantityTextField.getText())))));

			// Calculate the running total for the order price
			subTotal += (tempProduct.getRetailPrice() * tempQuantity);
			subtotalField.setText(Double.toString(subTotal));
		}
	}
}
