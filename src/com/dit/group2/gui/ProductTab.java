package com.dit.group2.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.dit.group2.person.Person;
import com.dit.group2.person.Supplier;
import com.dit.group2.retailSystem.RetailSystemDriver;
import com.dit.group2.stock.Product;
import com.dit.group2.stock.StockItem;

@SuppressWarnings("serial")
public class ProductTab extends GuiLayout implements ActionListener, ItemListener {

	private RetailSystemDriver driver;

	private boolean editMode;
	private boolean valid;
	private int submitButtonMode;
	private boolean emptiedList;

	private Vector<String> comboboxItems, supplierComboboxItems;
	private Product product;
	private StockItem stockItem;
	private Supplier supplier;
	private String name, category, description, errorMessage;
	private double supplierPrice, retailPrice, profitMargin;

	protected JButton newSupplierProductButton, newProductButton, editProductButton,
			deleteProductButton, submitButton, cancelButton, cancelEditButton, orderButton;
	private JTextField nameField, categoryField, supplierField, supplierPriceField,
			retailPriceField, profitMarginField;
	private JTextArea descriptionField;
	private JLabel idLabel, idNumberLabel, nameLabel, categoryLabel, descriptionLabel,
			supplierLabel, supplierPriceLabel, retailPriceLabel, profitMarginLabel, comboboxLabel;
	private JScrollPane scrollPane;
	private JComboBox<String> comboBox, supplierComboBox;
	private DefaultComboBoxModel<String> comboboxModel, supplierComboBoxModel;

	// private final boolean PRIVILEDGED_ACCESS =
	// RetailSystemDriver.isPriviledged();

	/**
	 * Product tab constructor
	 * 
	 * @param driver
	 */
	public ProductTab(RetailSystemDriver driver) {
		// Initialise the data passed into the constructor
		super();
		this.driver = driver;
		titleLabel.setText("Product Form");
		valid = false;
		emptiedList = false;
		submitButtonMode = 0;

		// Set up combo boxes
		comboboxItems = new Vector<String>();
		supplierComboboxItems = new Vector<String>();
		comboboxModel = new DefaultComboBoxModel<String>(comboboxItems);
		supplierComboBoxModel = new DefaultComboBoxModel<String>(supplierComboboxItems);
		comboBox = new JComboBox<String>(comboboxModel);
		supplierComboBox = new JComboBox<String>(supplierComboBoxModel);

		// Set up buttons
		orderButton = new JButton("Order Product");
		newProductButton = new JButton("Add New");
		editProductButton = new JButton("Edit");
		deleteProductButton = new JButton("Delete");
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");
		cancelEditButton = new JButton("Cancel");
		newSupplierProductButton = new JButton();

		// Set up fields
		nameField = new JTextField();
		categoryField = new JTextField();
		supplierField = new JTextField();
		supplierPriceField = new JTextField();
		retailPriceField = new JTextField();
		profitMarginField = new JTextField();
		descriptionField = new JTextArea(5, 25);
		descriptionField.setLineWrap(true);
		descriptionField.setWrapStyleWord(true);

		// Set up labels
		idLabel = new JLabel("Product ID");
		idNumberLabel = new JLabel("0");
		nameLabel = new JLabel("Product Name");
		categoryLabel = new JLabel("Category");
		descriptionLabel = new JLabel("Description");
		supplierLabel = new JLabel("Supplier");
		supplierPriceLabel = new JLabel("Supplier Price");
		retailPriceLabel = new JLabel("Retail Price");
		profitMarginLabel = new JLabel("Profit Margin");
		comboboxLabel = new JLabel("Product");

		// Add listeners and set initial visibility
		addComponentListener(this);
		orderButton.addActionListener(this);
		newSupplierProductButton.addActionListener(this);
		newProductButton.addActionListener(this);
		editProductButton.addActionListener(this);
		deleteProductButton.addActionListener(this);
		submitButton.addActionListener(this);
		submitButton.setVisible(false);
		cancelButton.addActionListener(this);
		cancelButton.setVisible(false);
		cancelEditButton.addActionListener(this);
		cancelEditButton.setVisible(false);

		// Positioning
		idNumberLabel.setBounds(203, 7, 265, 23);
		nameField.setBounds(200, 30, 265, 23);
		nameField.setColumns(10);
		categoryField.setBounds(200, 55, 265, 23);
		categoryField.setColumns(10);
		supplierField.setBounds(200, 175, 265, 23);
		supplierPriceField.setBounds(200, 200, 265, 23);
		retailPriceField.setBounds(200, 225, 265, 23);
		profitMarginField.setBounds(200, 250, 265, 23);
		idLabel.setBounds(59, 10, 93, 20);
		nameLabel.setBounds(59, 33, 120, 20);
		categoryLabel.setBounds(59, 58, 93, 20);
		descriptionLabel.setBounds(59, 83, 93, 20);
		supplierLabel.setBounds(59, 173, 93, 20);
		supplierPriceLabel.setBounds(59, 198, 120, 20);
		retailPriceLabel.setBounds(59, 223, 93, 20);
		profitMarginLabel.setBounds(59, 248, 120, 20);

		scrollPane = new JScrollPane(descriptionField);
		scrollPane.setBounds(200, 80, 265, 93);

		orderButton.setBounds(335, 285, 130, 23);
		submitButton.setBounds(200, 285, 130, 23);
		newProductButton.setBounds(64, 320, 130, 23);
		cancelButton.setBounds(64, 320, 130, 23);
		cancelEditButton.setBounds(199, 320, 130, 23);
		editProductButton.setBounds(199, 320, 130, 23);
		deleteProductButton.setBounds(335, 320, 130, 23);

		// comboboxLabel.setBounds(200, 30, 120, 20);
		comboBox.setBounds(200, 30, 265, 23);
		comboBox.addItemListener(this);

		newProductButton.setToolTipText("Click to add a product.");
		editProductButton.setToolTipText("Click to edit the details of the current product.");
		deleteProductButton.setToolTipText("Click to delete the current product.");
		cancelEditButton.setToolTipText("Click to cancel editing the current product.");
		cancelButton.setToolTipText("Click to exit the new product screen.");
		submitButton.setToolTipText("Submit changes/new product.");
		comboBox.setToolTipText("Click to view the details of another product.");
		retailPriceField
				.setToolTipText("Retail Price = Supply Price + (Supply Price)*(Profit Margin)");

		// Finish setting up the GUI and display the elements
		// comboboxLabel.setBounds(15, 285, 120, 20);
		supplierComboBox.setBounds(200, 175, 265, 23);
		supplierComboBox.addItemListener(this);
		setLayout(null);
		setVisible(true);
		setTextField(0, driver.getStockDB().getStockList());
		retailPriceField.setEditable(false);
		supplierField.setEditable(false);
		setFieldEditable(false);
		addAllElements();
	}

	/**
	 * Places all elements on the panel
	 */
	public void addAllElements() {
		mainPanel.add(orderButton);
		mainPanel.add(scrollPane);
		mainPanel.add(categoryField);
		mainPanel.add(profitMarginField);
		mainPanel.add(supplierPriceField);
		mainPanel.add(retailPriceField);
		mainPanel.add(supplierField);
		mainPanel.add(nameField);
		mainPanel.add(newProductButton);
		mainPanel.add(deleteProductButton);
		mainPanel.add(submitButton);
		mainPanel.add(editProductButton);
		mainPanel.add(cancelButton);
		mainPanel.add(cancelEditButton);
		mainPanel.add(comboBox);
		mainPanel.add(comboboxLabel);
		mainPanel.add(supplierComboBox);
		mainPanel.add(nameField);
		mainPanel.add(supplierLabel);
		mainPanel.add(retailPriceLabel);
		mainPanel.add(scrollPane);
		mainPanel.add(descriptionLabel);
		mainPanel.add(idLabel);
		mainPanel.add(idNumberLabel);
		mainPanel.add(nameLabel);
		mainPanel.add(supplierPriceLabel);
		mainPanel.add(profitMarginLabel);
		mainPanel.add(categoryLabel);
		add(titleLabel);
	}

	/**
	 * Toggles various buttons on or off
	 * 
	 * @param enabled
	 *            Whether buttons are enabled or not
	 */
	public void enableButtons(boolean enabled) {
		newProductButton.setEnabled(enabled);
		editProductButton.setEnabled(enabled);
		deleteProductButton.setEnabled(enabled);
	}

	/**
	 * Toggles whether fields can be edited or not
	 * 
	 * @param editable
	 *            Whether fields can be edited or not
	 */
	public void setFieldEditable(boolean editable) {

		nameField.setVisible(editable);
		comboBox.setVisible(!editable);
		categoryField.setEditable(editable);
		supplierField.setVisible(!editable);
		supplierComboBox.setVisible(editable);
		supplierPriceField.setEditable(editable);
		profitMarginField.setEditable(editable);
		descriptionField.setEditable(editable);
		revalidate();
		repaint();
	}

	/**
	 * Clears the data in the fields
	 * 
	 * @param list
	 *            The list to clear
	 */
	public void clearTextFields(ArrayList<StockItem> list) {
		if (list.size() > 0) {
			idNumberLabel.setText("" + Product.getUniqueId());
		}
		else {
			idNumberLabel.setText("");
			emptiedList = true;
		}
		nameField.setText("");
		categoryField.setText("");
		supplierField.setText("");
		supplierPriceField.setText("");
		retailPriceField.setText("");
		profitMarginField.setText("");
		descriptionField.setText("");
	}

	/**
	 * Builds a string for the product description
	 * 
	 * @param list
	 *            The list to refer to
	 */
	public void buildProductDetailsString(ArrayList<StockItem> list) {
		if (list.size() > 0) {
			for (StockItem stockItem : list) {
				product = stockItem.getProduct();
				driver.getGui().setStringBuilder(
						"\n-----------Quantity on stock : " + stockItem.getQuantity()
								+ "---------------------------------------------------------");
				driver.getGui().setStringBuilder("\nProduct ID : " + product.getProductID());
				driver.getGui().setStringBuilder("\nProduct Name : " + product.getProductName());
				driver.getGui().setStringBuilder(
						"\nProduct Category : " + product.getProductCategory());
				driver.getGui().setStringBuilder(
						"\nProduct Description : " + product.getProductDescription());
				driver.getGui().setStringBuilder(
						"\nProduct Supplier : " + product.getSupplier().getName());
				driver.getGui()
						.setStringBuilder("\nSupplier Price : " + product.getSupplierPrice());
				driver.getGui().setStringBuilder("\nRetail Price : " + product.getRetailPrice());
				driver.getGui().setStringBuilder("\nProfit Margin : " + product.getProfitMargin());
			}
		}
	}

	/**
	 * Updates the items in the combo box. This is called when something is removed or added to the
	 * list
	 * 
	 * @param stockItem
	 *            The updated list the combo box refers to
	 */
	public void setSelectedProduct(StockItem stockItem) {
		addItemsToCombobox(driver.getStockDB().getStockList());
		comboBox.setSelectedItem(stockItem.getProduct().getProductName());
		setTextField(comboBox.getSelectedIndex(), driver.getStockDB().getStockList());
		revalidate();
		repaint();
	}

	/**
	 * Updates the items in the combo box. This is called when something is removed or added to the
	 * list
	 * 
	 * @param list
	 *            The updated list the combo box refers to
	 */
	public void addItemsToCombobox(ArrayList<StockItem> list) {
		comboboxItems.clear();
		String item = "";
		if (driver.getStockDB().getStockList().size() > 0) {
			for (StockItem stockItem : list) {
				item = stockItem.getProduct().getProductName();
				comboboxItems.add(item);
			}
		}
		else {
			comboBox.removeAllItems();
			comboBox.setSelectedItem(null);

		}
		revalidate();
		repaint();
	}

	/**
	 * Add items to the combo box. This is called when something is removed or added to the list
	 * 
	 * @param list
	 *            The updated list the combo box refers to
	 */
	public void addItemsToSupplierCombobox(ArrayList<Person> list) {
		supplierComboboxItems.clear();
		String item = "<html><font color='red'>Add New Supplier</font></html>";
		supplierComboboxItems.add(item);
		if (driver.getPersonDB().getSupplierList().size() > 0) {
			for (Person person : list) {
				item = "\t" + person.getId() + " \t - \t " + person.getName();
				supplierComboboxItems.add(item);
			}
			supplierComboBox.setSelectedIndex(driver.getPersonDB().getSupplierList().size());
		}
		else {
			supplierComboBox.setSelectedItem(null);
		}
		revalidate();
		repaint();
	}

	public int getIndex(ArrayList<Person> list) {
		String[] values = null;
		if (supplierComboBox.getSelectedItem() != null) {
			values = ((String) supplierComboBox.getSelectedItem()).split("\\t");
			int index = 0;
			for (Person person : list) {
				if (person.getId() == Integer.parseInt(values[1].trim()))
					break;
				index++;
			}

			return index;
		}
		return -1;
	}

	/**
	 * Set the information displayed in the text field.
	 * 
	 * @param index
	 *            The index to refer to
	 * @param list
	 *            The list from which the information is taken
	 */
	public void setTextField(int index, ArrayList<StockItem> list) {
		if (list.size() > 0) {
			stockItem = list.get(index);
			product = stockItem.getProduct();
			idNumberLabel.setText("" + product.getProductID());
			emptiedList = false;
		}
		String item = "";

		nameField.setText(product.getProductName());
		categoryField.setText(product.getProductCategory());
		idNumberLabel.setText("" + product.getProductID());
		supplierField.setText(product.getSupplier().getId() + "  -  "
				+ product.getSupplier().getName());
		descriptionField.setText(product.getProductDescription());
		descriptionField.setCaretPosition(0);
		supplierPriceField.setText("" + product.getSupplierPrice());
		retailPriceField.setText("" + product.getRetailPrice());
		profitMarginField.setText("" + product.getProfitMargin());
		addItemsToSupplierCombobox(driver.getPersonDB().getSupplierList());
		addItemsToCombobox(list);
		comboBox.setSelectedIndex(index);
	}

	/**
	 * Deals with fields to enter product details
	 */
	public void productDetailsForm() {
		name = null;
		supplier = null;
		category = null;
		description = null;
		supplierPrice = -1;
		retailPrice = 0;
		profitMargin = -1;
		errorMessage = "";
		supplierLabel.setForeground(Color.red);

		// Enter a product name
		if (!nameField.getText().equals("")) {
			name = nameField.getText();
			nameLabel.setForeground(Color.black);
		}
		else {
			errorMessage = errorMessage + "Product name field cannot be empty!\n";
			nameLabel.setForeground(Color.red);
		}
		// Enter a product Category
		if (!categoryField.getText().equals("")) {
			category = (categoryField.getText());
			categoryLabel.setForeground(Color.black);
		}
		else {
			errorMessage = errorMessage + "Category field cannot be empty!\n";
			categoryLabel.setForeground(Color.red);
		}
		// Product description
		if (!descriptionField.getText().equals("")) {
			description = (descriptionField.getText());
			descriptionLabel.setForeground(Color.black);
		}
		else {
			errorMessage = errorMessage + "Description number field cannot be empty!\n";
			descriptionLabel.setForeground(Color.red);
		}
		// Supplier price
		if (!supplierPriceField.getText().equals(""))
			try {
				supplierPrice = Double.parseDouble(supplierPriceField.getText());
				supplierPriceLabel.setForeground(Color.black);
			}
			catch (NumberFormatException e) {
				errorMessage = errorMessage
						+ "\nInvalid supplier price value!!\nOnly numbers are allowed!";
				supplierPriceLabel.setForeground(Color.red);
				e.printStackTrace();
			}
		else {
			errorMessage = errorMessage + "Supplier price field cannot be empty!\n";
			supplierPriceLabel.setForeground(Color.red);
		}
		// Profit margin
		if (!profitMarginField.getText().equals(""))
			try {
				profitMargin = Double.parseDouble(profitMarginField.getText());
				profitMarginLabel.setForeground(Color.black);
			}
			catch (NumberFormatException e) {
				errorMessage = errorMessage
						+ "\nInvalid profit margin value!!\nOnly numbers are allowed!";
				profitMarginLabel.setForeground(Color.red);
				e.printStackTrace();
			}
		else {
			errorMessage = errorMessage + "Profit margin field cannot be empty!\n";
			profitMarginLabel.setForeground(Color.red);
		}
		// Executed if something has been altered or added
		if (name != null && category != null && description != null && supplierPrice != -1
				&& profitMargin != -1) {
			if (getIndex(driver.getPersonDB().getSupplierList()) > -1)
				supplier = (Supplier) driver.getPersonDB().getSupplierList().get(
						getIndex(driver.getPersonDB().getSupplierList()));

			// Validate the supplier field
			if (supplier != null) {
				valid = true;
				supplierLabel.setForeground(Color.black);
			}
			else {
				JOptionPane.showMessageDialog(null,
						"Supplier field cannot be empty!\nPlease select supplier from the list!");
				supplierLabel.setForeground(Color.red);
			}

			// Specific handlers for editing product
			if (editMode) {

				if (valid) {
					driver.getStockDB().changeProductDetails(
							Integer.parseInt(idNumberLabel.getText()), name, category, description,
							supplierPrice, profitMargin, supplier);
					setTextField(comboBox.getSelectedIndex(), driver.getStockDB().getStockList());
				}
			}
			// If not editing an existing product
			else if (valid) {
				driver.getStockDB().addNewProductToStockList(
						new Product(name, description, category, supplierPrice, profitMargin,
								supplier), 0);
				setTextField(driver.getStockDB().getStockList().size() - 1, driver.getStockDB()
						.getStockList());
				driver.getGui().getSupplierTab().addItemsToProductCombobox();
				driver.getGui().getStockControlTab().refreshStockControlTab();
				driver.getGui().getCustomerOrderTab().fillUpProductComboBox();
			}
			// Set the appropriate visibility and enabling for the buttons
			if (valid) {
				deleteProductButton.setEnabled(true);
				newProductButton.setEnabled(true);
				newProductButton.setVisible(true);
				editProductButton.setEnabled(true);
				editProductButton.setVisible(true);
				submitButton.setVisible(false);
				cancelButton.setVisible(false);
				cancelEditButton.setVisible(false);
			}
		}
		// Print an error message
		else {
			JOptionPane.showMessageDialog(null, "" + errorMessage);
		}

		revalidate();
		repaint();
	}

	/**
	 * Removes a product from the list of items in stock
	 * 
	 * @param stockItem
	 *            The item to be removed
	 * @param list
	 *            The list from which an item is to be removed
	 */
	public void deleteProduct(StockItem stockItem, ArrayList<StockItem> list) {

		// Get the value returned from the JOption Pane (tells which button has been pressed)
		// and handle it.
		int answer = JOptionPane.showConfirmDialog(null, "Do you really want to delete "
				+ stockItem.getProduct().getProductName() + "?", " CONFIRMATION ",
				JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			driver.getStockDB().removeProductFromStockList(stockItem);
		}
		if (list.size() > 0) {
			setTextField(0, list);
		}
		else {
			comboBox.setSelectedItem(null);
			clearTextFields(driver.getStockDB().getStockList());
			deleteProductButton.setEnabled(false);
			editProductButton.setEnabled(false);
			orderButton.setEnabled(false);
		}
		revalidate();
		repaint();
	}

	/**
	 * Event handler for combo boxes
	 */
	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			if (event.getItemSelectable().equals(comboBox)) {
				setTextField(comboBox.getSelectedIndex(), driver.getStockDB().getStockList());
			}
			if (event.getItemSelectable().equals(supplierComboBox)) {
				String[] values = ((String) supplierComboBox.getSelectedItem()).split("\\t");
				if (supplierComboBox.getItemAt((supplierComboBox.getSelectedIndex())).equals(
						"<html><font color='red'>Add New Supplier</font></html>")) {
					driver.getGui().getTabbedPane().setSelectedComponent(
							driver.getGui().getSupplierTab());
					driver.getGui().getSupplierTab().getNewSupplierButton().doClick();
				}
				else
					supplier = driver.getPersonDB().getSupplierById(
							Integer.parseInt(values[1].trim()));
			}

		}
	}

	/**
	 * Action listeners
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Submit clicked
		if (e.getSource() == submitButton) {
			// Add a new supplier
			if (submitButtonMode == 3) {
				editMode = false;
				productDetailsForm();
				if (valid) {
					driver.getGui().getTabbedPane().setSelectedComponent(
							driver.getGui().getSupplierTab());

				}
			}
			// Use an existing supplier
			else if (submitButtonMode == 2) {
				editMode = true;
				productDetailsForm();
			}
			// Create a new supplier
			else if (submitButtonMode == 1) {
				editMode = false;
				productDetailsForm();
			}
			comboBox.setEnabled(true);

			// If all fields valid
			if (valid) {
				driver.getGui().getSupplierTab().addItemsToProductCombobox();
				driver.getGui().getSupplyOrderTab().fillUpSupplierComboBox();
				driver.getGui().getSupplyOrderTab().getSupplierComboBox().setSelectedIndex(
						driver.getPersonDB().getSupplierList().size());
				driver.getGui().getSupplyOrderTab().fillUpProductComboBox();
				driver.getGui().getSupplyOrderTab().refreshTab(0);
				driver.getGui().getAccountingTab().refreshAccountingTab();
				submitButtonMode = 1;
				setFieldEditable(false);
				orderButton.setEnabled(true);
				valid = false;
			}

		}
		// New button clicked
		if (e.getSource() == newProductButton) {
			addItemsToSupplierCombobox(driver.getPersonDB().getSupplierList());
			if (submitButtonMode == 3) {

				supplierComboBox.setEnabled(false);
				supplierComboBox.setSelectedItem("\t" + supplier.getId() + " \t - \t "
						+ supplier.getName());

			}
			else {
				supplier = null;
				supplierComboBox.setSelectedItem(supplier);
				supplierComboBox.setEnabled(true);
				submitButtonMode = 1;
			}
			if (driver.getPersonDB().getSupplierList().size() <= 0)
				JOptionPane
						.showMessageDialog(null,
								"No supplier in suppliers list!\nYou must first add a supplier to the list!");
			//
			clearTextFields(driver.getStockDB().getStockList());
			submitButton.setVisible(true);
			newProductButton.setVisible(false);
			deleteProductButton.setEnabled(false);
			editProductButton.setEnabled(false);
			cancelButton.setVisible(true);
			orderButton.setEnabled(false);

			if (emptiedList) {
				idNumberLabel.setText("" + Product.getUniqueId());
			}
			setFieldEditable(true);
		}
		// Edit button clicked
		if (e.getSource() == editProductButton) {
			addItemsToSupplierCombobox(driver.getPersonDB().getSupplierList());
			supplierComboBox.setEnabled(true);
			supplierComboBox.setSelectedItem("\t" + product.getSupplier().getId() + " \t - \t "
					+ product.getSupplier().getName());
			if (driver.getPersonDB().getSupplierList().size() <= 0)
				JOptionPane
						.showMessageDialog(null,
								"No supplier in suppliers list!\nYou must first add a supplier to the list!");
			setFieldEditable(true);
			submitButtonMode = 2;
			editProductButton.setVisible(false);
			submitButton.setVisible(true);
			cancelEditButton.setVisible(true);
			orderButton.setEnabled(false);
			newProductButton.setEnabled(false);
			deleteProductButton.setEnabled(false);
		}
		// Cancel clicked
		if (e.getSource() == cancelButton) {
			// Make the fields non editable again
			supplier = product.getSupplier();
			supplierComboBox.setSelectedItem("\t" + product.getSupplier().getId() + " \t - \t "
					+ product.getSupplier().getName());
			setFieldEditable(false);

			// Hide buttons
			submitButton.setVisible(false);
			newProductButton.setVisible(true);
			cancelButton.setVisible(false);

			// Enable the delete and edit buttons as long as the list isn't empty
			if (!emptiedList) {
				deleteProductButton.setEnabled(true);
				editProductButton.setEnabled(true);
				orderButton.setEnabled(true);
			}
			comboBox.setEnabled(true);

			// Change the information displayed in the text fields to the last non null item
			setTextField(driver.getStockDB().getStockList().size() - 1, driver.getStockDB()
					.getStockList());

			// Clear the text fields if the database is empty
			if (!(driver.getStockDB().getStockList().size() > 0))
				clearTextFields(driver.getStockDB().getStockList());
			submitButtonMode = 1;
			nameLabel.setForeground(Color.black);
			categoryLabel.setForeground(Color.black);
			descriptionLabel.setForeground(Color.black);
			supplierLabel.setForeground(Color.black);
			supplierPriceLabel.setForeground(Color.black);
			profitMarginLabel.setForeground(Color.black);
		}
		// Cancel clicked in edit mode
		if (e.getSource() == cancelEditButton) {
			setFieldEditable(false);
			orderButton.setEnabled(true);
			submitButton.setVisible(false);
			cancelEditButton.setVisible(false);
			deleteProductButton.setEnabled(true);
			newProductButton.setEnabled(true);
			editProductButton.setEnabled(true);
			editProductButton.setVisible(true);
			setTextField(comboBox.getSelectedIndex(), driver.getStockDB().getStockList());
			if (!(driver.getStockDB().getStockList().size() > 0))
				clearTextFields(driver.getStockDB().getStockList());
			nameLabel.setForeground(Color.black);
			categoryLabel.setForeground(Color.black);
			descriptionLabel.setForeground(Color.black);
			supplierLabel.setForeground(Color.black);
			supplierPriceLabel.setForeground(Color.black);
			profitMarginLabel.setForeground(Color.black);
		}
		// Cancel clicked in edit mode
		if (e.getSource() == deleteProductButton) {
			deleteProduct(stockItem, driver.getStockDB().getStockList());
			driver.getGui().getStockControlTab().refreshStockControlTab();
		}

		// New button clicked
		if (e.getSource() == newSupplierProductButton) {
			// supplierComboBox.setSelectedItem("\t"+
			// supplier.getId()+" \t - \t "+supplier.getName());
			submitButtonMode = 3;
			newProductButton.doClick();

		}

		// New button clicked
		if (e.getSource() == orderButton) {
			// supplierComboBox.setSelectedItem("\t"+
			// supplier.getId()+" \t - \t "+supplier.getName());
			/*
			 * submitButtonMode = 3; newProductButton.doClick();
			 */
			driver.getGui().getTabbedPane().setSelectedComponent(
					driver.getGui().getSupplyOrderTabbedPane());
			driver.getGui().getSupplyOrderTabbedPane().setSelectedComponent(
					driver.getGui().getSupplyOrderTab());
			// driver.getGui().getSupplyOrderTab().getSupplierComboBox().setSelectedIndex(supplier);

		}

		revalidate();
		repaint();
	}

	/**
	 * @return the newSupplierProductButton
	 */
	public JButton getNewSupplierProductButton(Person person) {
		supplier = (Supplier) person;
		revalidate();
		repaint();
		return newSupplierProductButton;
	}

}
