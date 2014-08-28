package guiTabs;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import person.Person;
import person.Supplier;
import retailSystem.PersonDB;

public class SupplierTab extends PersonTab {

	private JButton newSupplierButton;
	private JTabbedPane tabbedPane;
	private ProductTab productTab;
	private MainGUI gui;
	private String contactName, vatNumber;
	private JTextField vatNumberField, contactNameField;
	private JLabel vatNumberLabel, contactNameLabel;

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
		addItemsToCombobox(personDB.getSupplierList());
		comboBox.setSelectedIndex(index);

		// Reset the view
		revalidate();
		repaint();
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
	 * Supplier tab constructor
	 */
	public SupplierTab(PersonDB personDB, JTabbedPane tabbedPane, MainGUI gui) {

		// Initialise elements
		super(personDB);
		this.tabbedPane = tabbedPane;
		this.gui = gui;
		newSupplierButton = new JButton();
		newSupplierButton.addActionListener(this);
		contactNameLabel = new JLabel("Contact Name");
		vatNumberLabel = new JLabel("Vat Number");
		comboboxLabel.setText("Supplier List");
		vatNumberField = new JTextField();
		contactNameField = new JTextField();

		// Set the boundaries for each element
		contactNameField.setBounds(200, 160, 265, 20);
		contactNameField.setColumns(10);
		vatNumberField.setBounds(200, 185, 265, 20);
		vatNumberField.setColumns(10);
		contactNameLabel.setBounds(59, 160, 93, 14);
		vatNumberLabel.setBounds(59, 185, 94, 14);

		// Finish setting up the tab
		setTextField(0, personDB.getSupplierList());
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
		add(vatNumberField);
		add(contactNameField);
		add(vatNumberLabel);
		add(contactNameLabel);
	}

	/**
	 * Open fields to enter person details
	 */
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
				personDB.changePersonDetails(person, name, email, contactNumber, address, 0, null,
						vatNumber, contactName);
				setTextField(comboBox.getSelectedIndex(), personDB.getSupplierList());
				valid = true;
			}

			// Handler for new supplier mode
			else {
				valid = true;
				personDB.createNewPerson(person, name, email, contactNumber, address, 0, null,
						contactName, vatNumber);
				setTextField(personDB.getSupplierList().size() - 1, personDB.getSupplierList());
			}
			// Enable buttons
			deletePersonButton.setEnabled(true);
			newPersonButton.setEnabled(true);
			editPersonButton.setEnabled(true);

			// Set the visibility of buttons
			newPersonButton.setVisible(true);
			editPersonButton.setVisible(true);
			submitButton.setVisible(false);
			cancelButton.setVisible(false);
			cancelEditButton.setVisible(false);
		}

		// Print an error message if errors exist
		else {
			JOptionPane.showMessageDialog(null, "" + errorMessage);
		}

		// Reset the view
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
					tabbedPane.setSelectedComponent(gui.getProductTab());
					gui.getProductTab().addItemsToSupplierCombobox(personDB.getSupplierList());
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
		}

		// New supplier clicked
		if (e.getSource() == newPersonButton) {
			clearTextFields(personDB.getSupplierList());
			super.actionPerformed(e);
			setFieldEditable(true);
		}

		// Edit clicked
		if (e.getSource() == editPersonButton) {
			setFieldEditable(true);
		}

		// Cancel Clicked
		if (e.getSource() == cancelButton) {
			setFieldEditable(false);
			setTextField(personDB.getSupplierList().size() - 1, personDB.getSupplierList());
			if (!(personDB.getSupplierList().size() > 0))
				clearTextFields(personDB.getSupplierList());
		}

		// Cancel clicked within edit mode
		if (e.getSource() == cancelEditButton) {
			setTextField(comboBox.getSelectedIndex(), personDB.getSupplierList());
			setFieldEditable(false);
			if (!(personDB.getSupplierList().size() > 0))
				clearTextFields(personDB.getSupplierList());
		}

		// Delete supplier clicked
		if (e.getSource() == deletePersonButton) {
			deletePerson(person, personDB.getSupplierList());
		}

		// New supplier selected
		if (e.getSource() == newSupplierButton) {
			newPersonButton.doClick();
			submitButtonMode = 3;
		}

		// If valid details have been entered, finish and set the fields to non editable
		if (valid) {
			setFieldEditable(false);
			valid = false;
		}

		// Refresh the view
		revalidate();
		repaint();
	}

	/**
	 * Toggle ediing of fields
	 * 
	 * @param editable
	 *            Editiable status of the fields
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
			setTextField(comboBox.getSelectedIndex(), personDB.getSupplierList());
			revalidate();
			repaint();
		}

	}
}
