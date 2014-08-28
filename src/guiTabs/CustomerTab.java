package guiTabs;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import person.Person;
import retailSystem.PersonDB;

/**
 * Tab containing customer operations
 */
@SuppressWarnings("serial")
public class CustomerTab extends PersonTab {
	/**
	 * Customer tab constructor
	 * 
	 * @param personDB
	 *            The database for people
	 */
	public CustomerTab(PersonDB personDB) {
		super(personDB);

		comboboxLabel.setText("Customer List");
		setTextField(0, personDB.getCustomerList());
		setFieldEditable(false);
		addAllElements();
	}

	/**
	 * Display the selected details in the test field
	 * 
	 * @param index
	 *            The selected index
	 * @param list
	 *            The list of customers
	 */
	public void setTextField(int index, ArrayList<Person> list) {

		super.setTextField(index, list);

		addItemsToCombobox(personDB.getCustomerList());
		comboBox.setSelectedIndex(index);
		revalidate();
		repaint();
	}

	/**
	 * Activate a for to edit or add customer details
	 */
	public void personDetailsForm() {
		super.personDetailsForm();
		// If fields not empty
		if (name != null && address != null && email != null && contactNumber != null) {

			// Edit mode selected
			if (editMode) {
				personDB.changePersonDetails(person, name, email, contactNumber, address, 0, null,
						null, null);
				setTextField(comboBox.getSelectedIndex(), personDB.getCustomerList());
				valid = true;
			}
			// Adding a new customer
			else {
				valid = true; // What does valid do?
				personDB.createNewPerson(person, name, email, contactNumber, address, 0, null,
						null, null);
				setTextField(personDB.getCustomerList().size() - 1, personDB.getCustomerList());
			}
			// Set enabled status of buttons
			deletePersonButton.setEnabled(true);
			newPersonButton.setEnabled(true);
			editPersonButton.setEnabled(true);

			// Set visibility of buttons
			newPersonButton.setVisible(true);
			editPersonButton.setVisible(true);
			submitButton.setVisible(false);
			cancelButton.setVisible(false);
			cancelEditButton.setVisible(false);
		}
		// Print an error message
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

		// Inherit action listeners from the super class
		super.actionPerformed(e);

		// Submit button clicked
		if (e.getSource() == submitButton) {
			if (submitButtonMode == 2) {
				editMode = true;
				personDetailsForm();
			}
			else if (submitButtonMode == 1) {
				editMode = false;
				personDetailsForm();
			}
		}
		// If all details entered are valid
		if (valid) {
			setFieldEditable(false);
			valid = false;
		}
		// New button clicked
		if (e.getSource() == newPersonButton) {
			clearTextFields(personDB.getCustomerList());
			setFieldEditable(true);
		}
		// Edit button clicked
		if (e.getSource() == editPersonButton) {
			setFieldEditable(true);
		}
		// Cancel button clicked
		if (e.getSource() == cancelButton) {
			setFieldEditable(false);
			setTextField(personDB.getCustomerList().size() - 1, personDB.getCustomerList());
			if (!(personDB.getCustomerList().size() > 0))
				clearTextFields(personDB.getCustomerList());
		}
		// Cancel button clicekd in edit mode
		if (e.getSource() == cancelEditButton) {
			setTextField(comboBox.getSelectedIndex(), personDB.getCustomerList());
			setFieldEditable(false);
			if (!(personDB.getCustomerList().size() > 0))
				clearTextFields(personDB.getCustomerList());
		}
		// Delete button clicked
		if (e.getSource() == deletePersonButton) {
			deletePerson(person, personDB.getCustomerList());
		}
		// Reset the viev
		revalidate();
		repaint();
	}

	// Event listener for the combo box
	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			setTextField(comboBox.getSelectedIndex(), personDB.getCustomerList());
			revalidate();
			repaint();
		}
	}
}
