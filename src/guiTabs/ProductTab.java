package guiTabs;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import order.StockDBControl;
import order.StockItem;
import person.Person;
import person.Supplier;
import retailSystem.PersonDB;
import retailSystem.Product;

public class ProductTab extends JPanel implements ActionListener, ItemListener, ComponentListener {

	private MainGUI gui;
	private JTabbedPane tabbedPane;
	private SupplierTab supplierTab;
	private boolean editMode;
	private boolean valid;
	private int submitButtonMode;
	private boolean emptiedList;
	private PersonDB personDB;
	private StockDBControl stockDBControl;
	private Vector<String> comboboxItems, supplierComboboxItems;
	private Product product;
	private StockItem stockItem;
	private Supplier supplier;
	private String name, category, description, errorMessage;
	private double supplierPrice, retailPrice, profitMargin;

	protected JButton newSupplierProductButton, newProductButton, editProductButton,
			deleteProductButton, submitButton, cancelButton, cancelEditButton;
	private JTextField nameField, categoryField, supplierField, supplierPriceField,
			retailPriceField, profitMarginField;
	private JTextArea descriptionField;
	private JLabel idLabel, idNumberLabel, nameLabel, categoryLabel, descriptionLabel,
			supplierLabel, supplierPriceLabel, retailPriceLabel, profitMarginLabel, comboboxLabel;
	private JScrollPane scrollPane;
	private JComboBox<String> comboBox, supplierComboBox;
	private DefaultComboBoxModel<String> comboboxModel, supplierComboBoxModel;

	private JPanel mainPanel, outerPanel;
	private int xSize;
	private int ySize;
	private int xPosition;
	private int yPosition;

	// private final boolean PRIVILEDGED_ACCESS =
	// RetailSystemDriver.isPriviledged();

	/**
	 * Product tab constructor
	 * 
	 * @param stockDBControl
	 *            Stock database
	 * @param personDB
	 *            Person database
	 * @param tabbedPane
	 *            The other tabs in the window
	 * @param supplierTab
	 *            Supplier Tab
	 * @param gui
	 *            The main containing GUI
	 */
	public ProductTab(StockDBControl stockDBControl, PersonDB personDB, JTabbedPane tabbedPane,
			SupplierTab supplierTab, MainGUI gui) {

		// Initialise the data passed into the constructor
		this.gui = gui;
		this.tabbedPane = tabbedPane;
		this.supplierTab = supplierTab;
		this.stockDBControl = stockDBControl;
		this.personDB = personDB;

		xSize = 530;
		ySize = 350;
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		outerPanel = new JPanel();
		outerPanel.setLayout(null);
		outerPanel.add(mainPanel);
		outerPanel.setBorder(BorderFactory.createLineBorder(new Color(176, 168, 168), 8, true));
		this.setBorder(BorderFactory.createLineBorder(new Color(176, 168, 168), 4));
		add(outerPanel, new GridBagConstraints());
		addComponentListener(this);

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

		addComponentListener(this);
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

		// Add listeners and set initial visibility
		idNumberLabel.setBounds(203, 7, 265, 23);
		nameField.setBounds(200, 30, 265, 23);
		nameField.setColumns(10);
		categoryField.setBounds(200, 55, 265, 23);
		categoryField.setColumns(10);
		supplierField.setBounds(200, 135, 265, 23);
		supplierPriceField.setBounds(200, 160, 265, 23);
		retailPriceField.setBounds(200, 185, 265, 23);
		profitMarginField.setBounds(200, 210, 265, 23);

		// Positioning
		idLabel.setBounds(59, 10, 93, 20);
		nameLabel.setBounds(59, 33, 93, 20);
		categoryLabel.setBounds(59, 58, 93, 20);
		descriptionLabel.setBounds(59, 83, 93, 20);
		supplierLabel.setBounds(59, 136, 93, 20);
		supplierPriceLabel.setBounds(59, 161, 93, 20);
		retailPriceLabel.setBounds(59, 186, 93, 20);
		profitMarginLabel.setBounds(59, 211, 93, 20);

		scrollPane = new JScrollPane(descriptionField);
		scrollPane.setBounds(200, 80, 265, 53);

		submitButton.setBounds(200, 250, 106, 23);
		newProductButton.setBounds(64, 320, 130, 23);
		cancelButton.setBounds(64, 320, 130, 23);
		cancelEditButton.setBounds(199, 320, 130, 23);
		editProductButton.setBounds(199, 320, 130, 23);
		deleteProductButton.setBounds(335, 320, 130, 23);

		comboboxLabel.setBounds(65, 285, 120, 20);
		comboBox.setBounds(200, 285, 265, 23);
		comboBox.addItemListener(this);

		// Finish setting up the GUI and display the elements
		// comboboxLabel.setBounds(15, 285, 120, 20);
		supplierComboBox.setBounds(200, 135, 265, 23);
		supplierComboBox.addItemListener(this);
		setLayout(null);
		setVisible(true);
		setTextField(0, stockDBControl.getStockList());
		retailPriceField.setEditable(false);
		supplierField.setEditable(false);
		setFieldEditable(false);
		addAllElements();
	}

	/**
	 * Places all elements on the panel
	 */
	public void addAllElements() {
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

		// add(mainPanel);

		// this.add(mainPanel, new GridBagConstraints());
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
		nameField.setEditable(editable);
		categoryField.setEditable(editable);
		supplierField.setVisible(!editable);
		supplierComboBox.setVisible(editable);
		supplierPriceField.setEditable(editable);
		profitMarginField.setEditable(editable);
		descriptionField.setEditable(editable);
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
				gui.setStringBuilder("\n-----------Quantity on stock : " + stockItem.getQuantity()
						+ "---------------------------------------------------------");
				gui.setStringBuilder("\nProduct ID : " + product.getProductID());
				gui.setStringBuilder("\nProduct Name : " + product.getProductName());
				gui.setStringBuilder("\nProduct Category : " + product.getProductCategory());
				gui.setStringBuilder("\nProduct Description : " + product.getProductDescription());
				gui.setStringBuilder("\nProduct Supplier : " + product.getSupplier().getName());
				gui.setStringBuilder("\nSupplier Price : " + product.getSupplierPrice());
				gui.setStringBuilder("\nRetail Price : " + product.getRetailPrice());
				gui.setStringBuilder("\nProfit Margin : " + product.getProfitMargin());
			}
		}
	}

	/**
	 * Updates the items in the combo box. This is called when something is removed or added to the
	 * list
	 * 
	 * @param list
	 *            The updated list the combo box refers to
	 */
	public void setSelectedProduct(StockItem stockItem) {
		addItemsToCombobox(stockDBControl.getStockList());
		comboBox.setSelectedItem(stockItem.getProduct().getProductName());
		setTextField(comboBox.getSelectedIndex(), stockDBControl.getStockList());
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
		for (StockItem stockItem : list) {
			item = stockItem.getProduct().getProductName();
			comboboxItems.add(item);
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
		for (Person person : list) {
			item = person.getName();
			supplierComboboxItems.add(item);
		}
		supplierComboBox.setSelectedIndex(personDB.getSupplierList().size());
		revalidate();
		repaint();
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
		supplierField.setText(product.getSupplier().getName());
		descriptionField.setText(product.getProductDescription());
		descriptionField.setCaretPosition(0);
		supplierPriceField.setText("" + product.getSupplierPrice());
		retailPriceField.setText("" + product.getRetailPrice());
		profitMarginField.setText("" + product.getProfitMargin());
		addItemsToSupplierCombobox(personDB.getSupplierList());
		addItemsToCombobox(list);
		comboBox.setSelectedIndex(index);
	}

	/**
	 * Deals with fields to enter product details
	 */
	public void productDetailsForm() {
		// Initialise variables
		name = null;
		category = null;
		description = null;
		supplierPrice = -1;
		retailPrice = 0;
		profitMargin = -1;
		errorMessage = "";

		// Enter a product name
		if (!nameField.getText().equals("")) {
			name = nameField.getText();
		}
		else {
			errorMessage = errorMessage + "Product name field cannot be empty!\n";
		}
		// Enter a product Category
		if (!categoryField.getText().equals("")) {
			category = (categoryField.getText());
		}
		else {
			errorMessage = errorMessage + "Category field cannot be empty!\n";
		}
		// Product description
		if (!descriptionField.getText().equals("")) {
			description = (descriptionField.getText());
		}
		else {
			errorMessage = errorMessage + "Description number field cannot be empty!\n";
		}
		// Supplier price
		if (!supplierPriceField.getText().equals(""))
			try {
				supplierPrice = Double.parseDouble(supplierPriceField.getText());
			}
			catch (NumberFormatException e) {
				errorMessage = errorMessage
						+ "\nInvalid supplier price value!!\nOnly numbers are allowed!";
				e.printStackTrace();
			}
		else {
			errorMessage = errorMessage + "Supplier price field cannot be empty!\n";
		}
		// Profit margin
		if (!profitMarginField.getText().equals(""))
			try {
				profitMargin = Double.parseDouble(profitMarginField.getText());
			}
			catch (NumberFormatException e) {
				errorMessage = errorMessage
						+ "\nInvalid profit margin value!!\nOnly numbers are allowed!";
				e.printStackTrace();
			}
		else {
			errorMessage = errorMessage + "Profit margin field cannot be empty!\n";
		}
		// Executed if something has been altered or added
		if (name != null && category != null && description != null && supplierPrice != -1
				&& profitMargin != -1) {

			// Validate the supplier field
			if (supplier != null) {
				valid = true;
			}
			else {
				JOptionPane.showMessageDialog(null,
						"Supplier field cannot be empty!\nPlease select supplier from the list!");
			}

			// Specific handlers for editing product
			if (editMode) {

				if (valid) {
					stockDBControl.changeProductDetails(Integer.parseInt(idNumberLabel.getText()),
							name, category, description, supplierPrice, profitMargin, supplier);
					setTextField(comboBox.getSelectedIndex(), stockDBControl.getStockList());
				}
			}
			// If not editing an existing product
			else if (valid) {
				stockDBControl.addNewProductToStockList(new Product(name, description, category,
						supplierPrice, profitMargin, supplier), 10);
				setTextField(stockDBControl.getStockList().size() - 1, stockDBControl
						.getStockList());
				gui.getSupplierTab().addItemsToProductCombobox();
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
		else
			JOptionPane.showMessageDialog(null, "" + errorMessage);

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
				JOptionPane.YES_NO_OPTION);// displaying
											// JOptionPane.YES_NO_OPTION
											// Confirmdialog
											// box
		if (answer == JOptionPane.YES_OPTION) {
			stockDBControl.removeProductFromStockList(stockItem);
		}
		if (list.size() > 0) {
			setTextField(0, list);
		}
		else {
			setTextField(list.size() - 1, list);
			clearTextFields(list);
			deleteProductButton.setEnabled(false);
			editProductButton.setEnabled(false);
			submitButton.setVisible(false);
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
				setTextField(comboBox.getSelectedIndex(), stockDBControl.getStockList());
			}
			if (event.getItemSelectable().equals(supplierComboBox)) {
				if (supplierComboBox.getItemAt((supplierComboBox.getSelectedIndex())).equals(
						"<html><font color='red'>Add New Supplier</font></html>")) {
					tabbedPane.setSelectedComponent(supplierTab);
					supplierTab.getNewSupplierButton().doClick();
				}
				else
					supplier = personDB.getSupplierByName(supplierComboBox
							.getItemAt(supplierComboBox.getSelectedIndex()));
			}
			revalidate();
			repaint();
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
					tabbedPane.setSelectedComponent(gui.getSupplierTab());
					gui.getSupplierTab().addItemsToProductCombobox();
				}
			}
			// Use an existing supplier
			else if (submitButtonMode == 2) {
				editMode = true;
				productDetailsForm();
			}
			else if (submitButtonMode == 1) {
				editMode = false;
				productDetailsForm();
			}
			comboBox.setEnabled(true);
		}

		// If all fields valid
		if (valid) {
			setFieldEditable(false);
			valid = false;
		}

		// New button clicked
		if (e.getSource() == newProductButton) {
			addItemsToSupplierCombobox(personDB.getSupplierList());
			if (submitButtonMode != 3)
				supplierComboBox.setSelectedItem(product.getSupplier().getName());
			else
				supplierComboBox.setSelectedItem(supplier.getName());
			if (personDB.getSupplierList().size() <= 0)
				JOptionPane
						.showMessageDialog(null,
								"No supplier in suppliers list!\nYou must first add a supplier to the list!");
			comboBox.setEnabled(false);
			supplier = null;
			supplierComboBox.setSelectedItem(supplier);
			clearTextFields(stockDBControl.getStockList());
			submitButton.setVisible(true);
			newProductButton.setVisible(false);
			deleteProductButton.setEnabled(false);
			editProductButton.setEnabled(false);
			cancelButton.setVisible(true);
			submitButtonMode = 1;
			if (emptiedList) {
				idNumberLabel.setText("" + Product.getUniqueId());
			}
			setFieldEditable(true);
		}
		// Edit button clicked
		if (e.getSource() == editProductButton) {
			addItemsToSupplierCombobox(personDB.getSupplierList());
			supplierComboBox.setSelectedItem(product.getSupplier().getName());
			if (personDB.getSupplierList().size() <= 0)
				JOptionPane
						.showMessageDialog(null,
								"No supplier in suppliers list!\nYou must first add a supplier to the list!");
			comboBox.setEnabled(false);
			setFieldEditable(true);
			submitButtonMode = 2;
			editProductButton.setVisible(false);
			submitButton.setVisible(true);
			cancelEditButton.setVisible(true);
			newProductButton.setEnabled(false);
			deleteProductButton.setEnabled(false);
		}
		// Cancel clicked
		if (e.getSource() == cancelButton) {

			// Make the fields non editable again
			supplier = product.getSupplier();
			supplierComboBox.setSelectedItem(product.getSupplier().getName());
			setFieldEditable(false);

			// Hide buttons
			submitButton.setVisible(false);
			newProductButton.setVisible(true);
			cancelButton.setVisible(false);

			// Enable the delete and edit buttons as long as the list isn't empty
			if (!emptiedList) {
				deleteProductButton.setEnabled(true);
				editProductButton.setEnabled(true);
			}
			comboBox.setEnabled(true);

			// Change the information displayed in the text fields to the last non null item
			setTextField(stockDBControl.getStockList().size() - 1, stockDBControl.getStockList());

			// Clear the text fields if the database is empty
			if (!(stockDBControl.getStockList().size() > 0))
				clearTextFields(stockDBControl.getStockList());
		}
		// Cancel clicked in edit mode
		if (e.getSource() == cancelEditButton) {
			setFieldEditable(false);
			submitButton.setVisible(false);
			cancelEditButton.setVisible(false);
			deleteProductButton.setEnabled(true);
			newProductButton.setEnabled(true);
			editProductButton.setEnabled(true);
			editProductButton.setVisible(true);
			setTextField(comboBox.getSelectedIndex(), stockDBControl.getStockList());
			comboBox.setEnabled(true);
			setFieldEditable(false);
			if (!(stockDBControl.getStockList().size() > 0))
				clearTextFields(stockDBControl.getStockList());
		}
		// Delete product clicked
		if (e.getSource() == deleteProductButton) {
			deleteProduct(stockItem, stockDBControl.getStockList());
		}

		// New button clicked
		if (e.getSource() == newSupplierProductButton) {
			newProductButton.doClick();
			submitButtonMode = 3;
		}

		revalidate();
		repaint();
	}

	/**
	 * @return the newSupplierProductButton
	 */
	public JButton getNewSupplierProductButton(Person person) {
		supplier = (Supplier) person;
		supplierComboBox.setSelectedItem(person.getName());
		System.out.println(person.getName());
		// supplierComboBox.setEnabled(false);
		revalidate();
		repaint();
		return newSupplierProductButton;
	}

	/**
	 * For when the window gets resized
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		supplierComboBox.setSelectedItem("Good Buy");
		xPosition = ((((int) mainPanel.getParent().getSize().getWidth()) - xSize) / 2);
		yPosition = ((((int) mainPanel.getParent().getSize().getHeight()) - ySize) / 2);
		mainPanel.setBounds(xPosition, yPosition, xSize, ySize);

		xPosition = ((((int) outerPanel.getParent().getSize().getWidth()) - xSize) / 2);
		yPosition = ((((int) outerPanel.getParent().getSize().getHeight()) - ySize) / 2);
		outerPanel.setBounds(xPosition - 50, yPosition - 40, xSize + 100, ySize + 80);

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

}
