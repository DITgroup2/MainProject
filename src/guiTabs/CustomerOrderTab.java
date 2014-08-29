package guiTabs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import order.Order;
import order.OrderDB;
import order.StockDBControl;
import order.StockItem;
import person.Customer;
import person.Person;
import person.Staff;
import retailSystem.PersonDB;
import retailSystem.Product;

public class CustomerOrderTab extends JPanel implements ActionListener, ItemListener,
		ListSelectionListener {

	private StockDBControl stockDBControl;
	private PersonDB personDB;
	private OrderDB orderDB;
	private Staff currentlyLoggedInStaff;
	private StockControlTab stockControlTab;
	private NumberFormat formatter = new DecimalFormat("#0.00");

	protected Vector<String> comboBoxItems = new Vector<String>();
	protected ArrayList<Integer> itemsQuantity = new ArrayList<Integer>();
	protected ArrayList<Double> itemsPrice = new ArrayList<Double>();
	protected DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>(
			comboBoxItems);
	protected JComboBox<String> productComboBox = new JComboBox<String>(comboBoxModel);

	protected Vector<String> customerComboBoxItems = new Vector<String>();
	protected DefaultComboBoxModel<String> customerComboBoxModel = new DefaultComboBoxModel<String>(
			customerComboBoxItems);
	protected JComboBox<String> customerComboBox = new JComboBox<String>(customerComboBoxModel);

	protected DefaultListModel<String> listModel = new DefaultListModel<String>();
	protected JList<String> orderingList = new JList<String>(listModel);
	protected JScrollPane orderingListSroll = new JScrollPane(orderingList);

	protected ArrayList<Product> currentOrderList = new ArrayList<Product>();
	protected ArrayList<Integer> currentOrderListQuantity = new ArrayList<Integer>();
	protected ArrayList<Integer> currentOrderComboIndex = new ArrayList<Integer>();

	protected JLabel productComboBoxLabel, quantityLabel, availableQuantityLabel,
			availableQuantityField, priceLabel, priceField, grandTotalLabel, grandTotalField,
			orderListProductLabel, orderListQtyLabel, orderListPriceLabel,
			orderListTotalPriceLabel, customerComboBoxLabel, customerIDLabel, customerIDField;
	protected JTextField quantityTextField;
	protected JButton addButton, removeButton, processButton;
	protected double grandTotal = 0;

	private TabListCellRenderer renderer = new TabListCellRenderer();

	// source for this class:
	// http://www.grandt.com/sbe/files/uts2/Chapter10html/Chapter10.htm

	public CustomerOrderTab(Staff currentlyLoggedInStaff, PersonDB personDB,
			StockDBControl stockDBControl, OrderDB orderDB, StockControlTab stockControlTab) {
		this.stockControlTab = stockControlTab;
		this.currentlyLoggedInStaff = currentlyLoggedInStaff;
		this.orderDB = orderDB;
		this.personDB = personDB;
		this.stockDBControl = stockDBControl;

		setUpCustomerComboBox();
		setUpProductComboBox();

		setUpCustomerNameField();
		setUpQuantityTextField();
		setUpAvailableQuantity();
		setUpPriceFields();
		setAddButton();
		setRemoveButton();
		setProcessButton();
		setUpSubtotal();
		orderingList();
		refreshTab(0);

		setLayout(null);
		setVisible(true);
	}

	// add customer combo box
	private void setUpCustomerComboBox() {
		customerComboBoxLabel = new JLabel("Customers");
		customerComboBoxLabel.setBounds(10, 10, 100, 20);
		add(customerComboBoxLabel);
		customerComboBox.setBounds(10, 30, 200, 20);
		add(customerComboBox);
		fillUpCustomerComboBox();
		customerComboBox.addItemListener(this);
	}

	// add product combo box
	private void setUpProductComboBox() {
		productComboBoxLabel = new JLabel("Products");
		productComboBoxLabel.setBounds(10, 70, 100, 20);
		productComboBox.setBounds(10, 90, 200, 20);
		add(productComboBoxLabel);
		add(productComboBox);
		fillUpProductComboBox();
		productComboBox.addItemListener(this);
	}

	// add customer name field
	private void setUpCustomerNameField() {
		customerIDLabel = new JLabel("ID");
		customerIDLabel.setBounds(220, 10, 50, 20);
		customerIDField = new JLabel();
		customerIDField.setBounds(220, 30, 70, 20);
		add(customerIDLabel);
		add(customerIDField);
	}

	// add product quantity field
	private void setUpQuantityTextField() {
		quantityLabel = new JLabel("pc(s)");
		quantityLabel.setBounds(220, 70, 50, 20);
		quantityTextField = new JTextField("1");
		quantityTextField.setBounds(220, 90, 50, 20);
		add(quantityLabel);
		add(quantityTextField);
	}

	// add product available quantity
	private void setUpAvailableQuantity() {
		availableQuantityLabel = new JLabel("AvQ");
		availableQuantityLabel.setBounds(300, 70, 40, 20);
		availableQuantityField = new JLabel(Integer.toString(itemsQuantity.get(0)));
		availableQuantityField.setBounds(300, 90, 40, 20);
		add(availableQuantityLabel);
		add(availableQuantityField);
	}

	// add product price fields
	private void setUpPriceFields() {
		priceLabel = new JLabel("Price");
		priceLabel.setBounds(350, 70, 40, 20);
		priceField = new JLabel(formatter.format(itemsPrice.get(0)));
		priceField.setBounds(350, 90, 50, 20);
		add(priceLabel);
		add(priceField);
	}

	// add Grand Total price fields
	private void setUpSubtotal() {
		grandTotalLabel = new JLabel("Grand Total:");
		grandTotalLabel.setBounds(280, 300, 70, 20);
		grandTotalField = new JLabel("0.00");
		grandTotalField.setBounds(335, 300, 70, 20);

		grandTotalField.setHorizontalAlignment(JLabel.RIGHT);
		add(grandTotalLabel);
		add(grandTotalField);
	}

	// add add button
	private void setAddButton() {
		addButton = new JButton("Add");
		addButton.setBounds(220, 120, 60, 20);
		addButton.addActionListener(this);
		add(addButton);
	}

	// add remove button
	private void setRemoveButton() {
		removeButton = new JButton("Remove");
		removeButton.setBounds(150, 300, 100, 20);
		removeButton.addActionListener(this);
		add(removeButton);
		removeButton.setEnabled(false);
	}

	// add process button
	private void setProcessButton() {
		processButton = new JButton("Process");
		processButton.setBounds(290, 325, 100, 20);
		processButton.addActionListener(this);
		add(processButton);
		processButton.setEnabled(false);
	}

	// add buying list
	private void orderingList() {
		orderListProductLabel = new JLabel("Product name");
		orderListProductLabel.setBounds(10, 150, 80, 20);
		orderListQtyLabel = new JLabel("Qty");
		orderListQtyLabel.setBounds(225, 150, 30, 20);
		orderListPriceLabel = new JLabel("Price");
		orderListPriceLabel.setBounds(290, 150, 50, 20);
		orderListTotalPriceLabel = new JLabel("Total");
		orderListTotalPriceLabel.setBounds(370, 150, 50, 20);
		add(orderListProductLabel);
		add(orderListQtyLabel);
		add(orderListPriceLabel);
		add(orderListTotalPriceLabel);

		orderingList.addListSelectionListener(this);
		orderingListSroll.setBounds(10, 170, 420, 120);

		add(orderingListSroll);

		renderer.setTabs(new int[] { 230, 315, 395 });
		orderingList.setCellRenderer(renderer);
	}

	// fill up customer combo Box
	public void fillUpCustomerComboBox() {
		ArrayList<Person> customers = personDB.getCustomerList();
		customerComboBoxItems.clear();
		// customerComboBoxItems.add(">> ADD NEW CUSTOMER <<");
		for (Person customer : customers) {
			customerComboBoxItems.add(customer.getName());
		}
		customerComboBox.setSelectedIndex(customers.size() - 1);
	}

	// fill up product combo Box with price and quantity
	private void fillUpProductComboBox() {
		ArrayList<StockItem> list = stockDBControl.getStockList();
		comboBoxItems.clear();
		itemsQuantity.clear();
		itemsPrice.clear();
		for (StockItem stockItem : list) {
			comboBoxItems.add(stockItem.getProduct().getProductName());
			itemsPrice.add(stockItem.getProduct().getRetailPrice());
			itemsQuantity.add(stockItem.getQuantity());
		}
		productComboBox.setSelectedIndex(list.size() - 1);
	}

	// refresh Tab
	private void refreshTab(int index) {
		priceField.setText(formatter.format(itemsPrice.get(index)));
		quantityTextField.setText("1");
		availableQuantityField.setText(Integer.toString(itemsQuantity.get(index)));
		grandTotalField.setText(formatter.format(grandTotal));
		productComboBox.setSelectedIndex(index);
		customerIDField.setText(Integer.toString(personDB.getCustomerList().get(
				(customerComboBox.getSelectedIndex())).getId()));
	}

	private String textAlignment(String text1, String text2, String text3, String text4) {
		String s = text1;
		s += "\t" + text2 + "\t" + text3 + "\t" + text4;
		return s;
	}

	// combo box selection
	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			refreshTab(productComboBox.getSelectedIndex());
		}

	}

	// button actions
	@Override
	public void actionPerformed(ActionEvent e) {

		// ADD BUTTON
		if (e.getSource() == addButton) {

			// add product and quantity to the current order list
			int index = productComboBox.getSelectedIndex();

			Product selectedProduct = stockDBControl.getStockList().get(index).getProduct();

			int availableQuantity = itemsQuantity.get(index);

			// accepting only integer into quantityTextField
			try {
				int orderingQuantity = (Integer.parseInt(quantityTextField.getText()));

				if (orderingQuantity > 0) {

					// check if there is enough product available to buy
					if (orderingQuantity <= availableQuantity) {
						processButton.setEnabled(true);
						// set available quantity
						itemsQuantity.set(index, availableQuantity - orderingQuantity);

						// refresh subtotal price
						grandTotal += (selectedProduct.getRetailPrice() * orderingQuantity);

						// check if the selected product is already in the currentOrderList
						int orderListIndex = currentOrderList.indexOf(selectedProduct);
						if (orderListIndex >= 0) {
							orderingQuantity += currentOrderListQuantity.get(orderListIndex);
							currentOrderListQuantity.set(orderListIndex, orderingQuantity);
							listModel.setElementAt(textAlignment(comboBoxItems.get(index), Integer
									.toString(orderingQuantity), priceField.getText(), formatter
									.format(orderingQuantity * selectedProduct.getRetailPrice())),
									orderListIndex);
							orderingList.setSelectedIndex(orderListIndex);
						}
						// selected product is NOT on the currntOrderList
						else {
							currentOrderList.add(selectedProduct);
							currentOrderListQuantity.add(orderingQuantity);
							listModel.addElement(textAlignment(comboBoxItems.get(index),
									quantityTextField.getText(), priceField.getText(), formatter
											.format(orderingQuantity
													* selectedProduct.getRetailPrice())));
							orderingList.setSelectedIndex(listModel.getSize() - 1);
						}

						removeButton.setEnabled(true);
						refreshTab(index);
					}
					else
						JOptionPane.showMessageDialog(null, "Not enough product in stock!");
				}
				else
					JOptionPane.showMessageDialog(null,
							"Product quantity must by positive whole number");

			}
			catch (NumberFormatException error) {
				JOptionPane.showMessageDialog(null,
						"Invalid entry, only positive whole numbers please!");
				// error.printStackTrace();
			}
		}

		// REMOVE BUTTON
		if (e.getSource() == removeButton) {
			int index = orderingList.getSelectedIndex();
			int i = 0; // index in combo list or stock list

			for (StockItem stockItem : stockDBControl.getStockList()) {
				if (stockItem.getProduct().getProductID() == currentOrderList.get(index)
						.getProductID()) {
					break;
				}
				i++;
			}

			grandTotal -= (currentOrderList.get(index).getRetailPrice() * currentOrderListQuantity
					.get(index));
			itemsQuantity.set(i, itemsQuantity.get(i) + currentOrderListQuantity.get(index));
			currentOrderList.remove(index);
			currentOrderListQuantity.remove(index);
			listModel.remove(index);

			if (listModel.isEmpty()) {
				removeButton.setEnabled(false);
				processButton.setEnabled(false);
			}
			if (index != 0)
				orderingList.setSelectedIndex(index - 1);
			else
				orderingList.setSelectedIndex(0);

			refreshTab(i);
		}

		// PROCESS BUTTON
		if (e.getSource() == processButton) {

			// make new stock list, fill the elements from the chosen product and quantities
			StockItem orderStockItem = new StockItem();
			ArrayList<StockItem> orderStockItemList = new ArrayList<StockItem>();

			for (int i = 0; i < currentOrderList.size(); i++) {
				orderStockItem.setProduct(currentOrderList.get(i));
				orderStockItem.setQuantity(currentOrderListQuantity.get(i));
				orderStockItemList.add(orderStockItem);
			}
			Customer customer = (Customer) personDB.getCustomerList().get(
					customerComboBox.getSelectedIndex());
			// Staff responsibleStaffForOrder = (Staff)personDB.getStaffList().get(0);

			// make a new order and put it to the orderDB
			Order order = new Order(currentlyLoggedInStaff, customer, orderStockItemList,
					grandTotal);
			orderDB.getCustomerOrderList().add(order);

			// reduce the products' quantity in stockDBControl
			int quantity;
			for (int index = 0; index < currentOrderList.size(); index++) {
				quantity = stockDBControl.getStockItem(currentOrderList.get(index).getProductID())
						.getQuantity();
				quantity -= currentOrderListQuantity.get(index);
				stockDBControl.getStockItem(currentOrderList.get(index).getProductID())
						.setQuantity(quantity);
			}

			JOptionPane.showMessageDialog(null, "Order created by "
					+ currentlyLoggedInStaff.getName() + "\n  for " + customer.getName()
					+ "\n Grand Total price: " + grandTotal);

			// clear the tab and refresh
			listModel.clear();
			currentOrderList.clear();
			currentOrderListQuantity.clear();
			grandTotal = 0;
			removeButton.setEnabled(false);
			processButton.setEnabled(false);
			fillUpCustomerComboBox();
			fillUpProductComboBox();
			refreshTab(0);
			stockControlTab.refreshStockControlTab();

			// TESTING - reading back from the orderDB
			// System.out.println(orderDB.getCustomerOrderList().get(0).getOrderEntryList().get(0).getProduct().getProductName());
			// System.out.println(orderDB.getCustomerOrderList().get(0).getOrderEntryList().size());
		}
	}

	// list selection event
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == orderingList) {
			removeButton.setEnabled(true);
		}
	}
}
