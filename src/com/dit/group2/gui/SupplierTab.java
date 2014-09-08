package com.dit.group2.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;

import com.dit.group2.order.Order;
import com.dit.group2.person.Person;
import com.dit.group2.person.Supplier;
import com.dit.group2.retailSystem.RetailSystemDriver;
import com.dit.group2.stock.Product;
import com.dit.group2.stock.StockItem;

@SuppressWarnings("serial")
public class SupplierTab extends PersonTab {

	private JButton newSupplierButton;
	private String contactName, vatNumber;
	private JTextField vatNumberField, contactNameField;
	private JLabel vatNumberLabel, contactNameLabel, productsLabel;
	private JComboBox<String> productsComboBox;
	private DefaultComboBoxModel<String> productsComboboxModel;
	private Vector<String> productsComboboxItems;
	private boolean automaticItemSelection;

	/**
	 * Set the imformation to appear in the text fields
	 * 
	 * @param index
	 *            The selected index
	 * @param list
	 *            The list of suppliers
	 */
	public void setTextField(int index, ArrayList<Person> list) {

		super.setTextField(index, list);

		vatNumberField.setText(((Supplier) person).getVatNumber());
		contactNameField.setText(((Supplier) person).getContactName());
		addItemsToCombobox(driver.getPersonDB().getSupplierList());
		automaticItemSelection = true;
		comboBox.setSelectedIndex(index);
		addItemsToProductCombobox();
		

		// Reset the view
		revalidate();
		repaint();
	}

	public int getIndex(ArrayList<Person> list) {
		int index = super.getIndex(list);
		return index;
	}

	/**
	 * Clear all data in the text fields
	 * 
	 * @param list
	 *            The list of suppliers
	 */
	public void clearTextFields(ArrayList<Person> list) {

		super.clearTextFields(list);

		vatNumberField.setText("");
		contactNameField.setText("");
		revalidate();
		repaint();
	}

	/**
	 * Create the panel.
	 * 
	 * @param driver
	 *            TODO
	 */
	public SupplierTab(RetailSystemDriver driver) {
		// Initialise elements
		super(driver);

		automaticItemSelection = true;
		newSupplierButton = new JButton();
		newSupplierButton.addActionListener(this);
		contactNameLabel = new JLabel("Contact Name");
		vatNumberLabel = new JLabel("Vat Number");
		comboboxLabel.setText("Supplier");
		productsLabel = new JLabel("Products");

		productsComboboxItems = new Vector<String>();
		productsComboboxModel = new DefaultComboBoxModel<String>(productsComboboxItems);
		productsComboBox = new JComboBox<String>(productsComboboxModel);

		titleLabel.setText("<html><div style=\"text-align: center;\"> Supplier Form </html>");
		productsLabel.setBounds(59, 210, 93, 14);
		productsComboBox.setBounds(200, 210, 265, 23);
		productsComboBox.addItemListener(this);
		productsComboBox.addMouseListener(this);
		productsComboBox
				.setToolTipText("<html>Click on the text to see details of the currently selected product<br/>or click the down arrow to select another product.</html>");
		ToolTipManager.sharedInstance().setInitialDelay(0);
		mainPanel.add(productsComboBox);
		mainPanel.add(productsLabel);

		vatNumberField = new JTextField();
		contactNameField = new JTextField();

		// Set the boundaries for each element
		contactNameField.setBounds(200, 160, 265, 23);
		contactNameField.setColumns(10);
		vatNumberField.setBounds(200, 185, 265, 23);
		vatNumberField.setColumns(10);
		contactNameLabel.setBounds(59, 160, 93, 14);
		vatNumberLabel.setBounds(59, 185, 94, 14);
		
		// Set tool tips for clickable buttons
		newPersonButton.setToolTipText("Click to add a new supplier.");
		editPersonButton.setToolTipText("Click to edit the current supplier.");
		deletePersonButton.setToolTipText("Click to delete the current supplier.");
		cancelEditButton.setToolTipText("Click to cancel editing the current supplier.");
		cancelButton.setToolTipText("Click to exit the new supplier screen.");
		submitButton.setToolTipText("Submit changes/new supplier.");
		comboBox.setToolTipText("Click to view the details of another supplier.");


		// Finish setting up the tab
		setTextField(0, driver.getPersonDB().getSupplierList());
		setFieldEditable(false);
		addAllElements();
	}

	/**
	 * @return A new supplier button
	 */
	public JButton getNewSupplierButton() {
		return newSupplierButton;
	}

	/**
	 * Add every element to the panel
	 */
	public void addAllElements() {

		super.addAllElements();

		mainPanel.add(vatNumberField);
		mainPanel.add(contactNameField);
		mainPanel.add(vatNumberLabel);
		mainPanel.add(contactNameLabel);
		/*
		 * add(vatNumberField); add(contactNameField); add(vatNumberLabel); add(contactNameLabel);
		 */
	}

	/**
	 * Open fields to enter person details
	 */
	public void addItemsToProductCombobox() {
		productsComboboxItems.clear();
		String item = "<html><font color='red'>Add New Product</font></html>";
		productsComboboxItems.add(item);
		if(driver.getPersonDB().getSupplierList().size()>0){
			for (StockItem stockItem : driver.getStockDB().getStockList()) {
				Product product = stockItem.getProduct();
				if (product.getSupplier() == person) {
					item = "PID:\t" + product.getProductID() + " \t " + "" + product.getProductName()
							+ "";
					productsComboboxItems.add(item);
				}
			}
			productsComboBox.setSelectedIndex(productsComboBox.getItemCount() - 1);
		}
		else{
			productsComboBox.removeAllItems();
			productsComboBox.setSelectedItem(null);
		}
		automaticItemSelection = false;
		revalidate();
		repaint();
	}


	public void personDetailsForm() {
		super.personDetailsForm();

		vatNumber = null;
		contactName = null;

		// Contact name
		if (!contactNameField.getText().equals("")) {
			contactName = contactNameField.getText();
			contactNameLabel.setForeground(Color.black);
		}
		else {
			errorMessage = errorMessage + "Contact name field cannot be empty!\n";
			contactNameLabel.setForeground(Color.red);
		}
		// Vat number
		if (!vatNumberField.getText().equals("")) {
			vatNumber = vatNumberField.getText();
			vatNumberLabel.setForeground(Color.black);
		}
		else {
			errorMessage = errorMessage + "VAT number field cannot be empty!\n";
			vatNumberLabel.setForeground(Color.red);
		}

		// Execute if anything has been entered into any field
		if (name != null && address != null && email != null && contactNumber != null
				&& vatNumber != null && contactName != null) {

			// Specific handler for edit mode
			if (editMode) {
				driver.getPersonDB().changePersonDetails(person, name, email, contactNumber,
						address, 0, null, vatNumber, contactName);
				setTextField(getIndex(driver.getPersonDB().getSupplierList()), driver.getPersonDB()
						.getSupplierList());
				valid = true;
			}
			// Handler for new supplier mode
			else {

				valid = true;
				driver.getPersonDB().createNewPerson(person, name, email, contactNumber, address,
						0, null, contactName, vatNumber);
				setTextField(driver.getPersonDB().getSupplierList().size() - 1, driver
						.getPersonDB().getSupplierList());
			}

			// Enable buttons
			deletePersonButton.setEnabled(true);
			editPersonButton.setEnabled(true);
			newPersonButton.setEnabled(true);

			// Set the visibility of buttons
			newPersonButton.setVisible(true);
			editPersonButton.setVisible(true);
			submitButton.setVisible(false);
			cancelEditButton.setVisible(false);
		}

		// Print an error message if errors exist
		else {
			JOptionPane.showMessageDialog(null, "" + errorMessage);
		}
		revalidate();
		repaint();
	}

	/**
	 * Action listeners
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// Get action listeners for the super class
		super.actionPerformed(e);

		// Submit clicked
		if (e.getSource() == submitButton) {
			if (submitButtonMode == 3) {
				editMode = false;
				personDetailsForm();
				if (valid) {
					driver.getGui().getTabbedPane().setSelectedComponent(
							driver.getGui().getProductTab());
					driver.getGui().getProductTab().addItemsToSupplierCombobox(
							driver.getPersonDB().getSupplierList());
				}
			}
			// Creating a new supplier
			else if (submitButtonMode == 2) {
				editMode = true;

				personDetailsForm();
			}
			// Using an existing supplier
			else if (submitButtonMode == 1) {
				editMode = false;
				personDetailsForm();
			}
			automaticItemSelection = true;
			comboBox.setEnabled(true);
		}

		if (valid) {
			productsComboBox.setEnabled(true);
			setFieldEditable(false);
			driver.getGui().getSupplyOrderTab().fillUpSupplierComboBox();
			driver.getGui().getSupplyOrderTab().getSupplierComboBox().setSelectedIndex(driver.getPersonDB().getSupplierList().size());
			
			driver.getGui().getSupplyOrderTab().fillUpProductComboBox();
			driver.getGui().getSupplyOrderTab().refreshTab(0);
			valid = false;
		}
		// New supplier clicked
		if (e.getSource() == newPersonButton) {
			clearTextFields(driver.getPersonDB().getSupplierList());
			super.actionPerformed(e);
			productsComboBox.setSelectedItem(null);
			productsComboBox.setEnabled(false);
			setFieldEditable(true);
			automaticItemSelection = true;
		}
		// Edit clicked
		if (e.getSource() == editPersonButton) {
			setFieldEditable(true);
			productsComboBox.setEnabled(false);
		}
		// Cancel Clicked
		if (e.getSource() == cancelButton) {
			setFieldEditable(false);
			vatNumberLabel.setForeground(Color.black);
			contactNameLabel.setForeground(Color.black);
			productsComboBox.setEnabled(true);
			setTextField(driver.getPersonDB().getSupplierList().size() - 1, driver.getPersonDB()
					.getSupplierList());
			if (!(driver.getPersonDB().getSupplierList().size() > 0))
				clearTextFields(driver.getPersonDB().getSupplierList());
		}
		// Cancel clicked within edit mode
		if (e.getSource() == cancelEditButton) {
			vatNumberLabel.setForeground(Color.black);
			contactNameLabel.setForeground(Color.black);
			setTextField(getIndex(driver.getPersonDB().getSupplierList()), driver.getPersonDB()
					.getSupplierList());
			setFieldEditable(false);
			productsComboBox.setEnabled(true);
			if (!(driver.getPersonDB().getSupplierList().size() > 0))
				clearTextFields(driver.getPersonDB().getSupplierList());
		}

		// Delete supplier clicked
		if (e.getSource() == deletePersonButton) {
			deletePerson(person, driver.getPersonDB().getSupplierList());
			addItemsToProductCombobox();
		}

		// New supplier selected
		if (e.getSource() == newSupplierButton) {
			submitButtonMode = 3;
			newPersonButton.doClick();
		}

		automaticItemSelection = false;
		revalidate();
		repaint();
	}

	/**
	 * Toggle editing of fields
	 * 
	 * @param editable
	 *            Editable status of the fields
	 */
	public void setFieldEditable(boolean editable) {
		super.setFieldEditable(editable);
		vatNumberField.setEditable(editable);
		contactNameField.setEditable(editable);
	}

	/**
	 * Event listener for the combo box
	 */
	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			// JOptionPane.showMessageDialog(null, driver.getGui());
			if (event.getItemSelectable().equals(comboBox)) {
				automaticItemSelection = true;
				setTextField(getIndex(driver.getPersonDB().getSupplierList()), driver.getPersonDB()
						.getSupplierList());
			}
			if (event.getItemSelectable().equals(productsComboBox)) {

				if (productsComboBox.getItemAt((productsComboBox.getSelectedIndex())).equals(
						"<html><font color='red'>Add New Product</font></html>")) {

					String[] values = ((String) comboBox.getSelectedItem()).split("\\t");
					Supplier supplier = (Supplier) driver.getPersonDB().getSupplierById(
							Integer.parseInt(values[1].trim()));
					if (!automaticItemSelection && (supplier.getProducts().size() > 0)) {

						driver.getGui().getTabbedPane().setSelectedComponent(
								driver.getGui().getProductTab());
						if (values != null)
							driver.getGui().getProductTab().getNewSupplierProductButton(supplier)
									.doClick();

					}
				}
				else if (driver.getGui().getProductTab() != null) {
					String[] values = ((String) productsComboBox.getSelectedItem()).split("\\t");
					if (values != null && !automaticItemSelection) {
						driver.getGui().getTabbedPane().setSelectedComponent(
								driver.getGui().getProductTab());
						driver.getGui().getProductTab().setSelectedProduct(
								driver.getStockDB()
										.getStockItem(Integer.parseInt(values[1].trim())));
					}
				}
			}

		}
		revalidate();
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		String[] values = ((String) productsComboBox.getSelectedItem()).split("\\t");
		if (productsComboBox.getItemAt((productsComboBox.getSelectedIndex())).equals(
				"<html><font color='red'>Add New Product</font></html>")) {
			if (values != null && !automaticItemSelection) {
				driver.getGui().getTabbedPane().setSelectedComponent(
						driver.getGui().getProductTab());
				driver.getGui().getProductTab().getNewSupplierProductButton(person).doClick();
			}
		}
		else if (productsComboBox.isEnabled()) {
			driver.getGui().getTabbedPane().setSelectedComponent(driver.getGui().getProductTab());
			driver.getGui().getProductTab().setSelectedProduct(
					driver.getStockDB().getStockItem(Integer.parseInt(values[1].trim())));
		}
	}
}