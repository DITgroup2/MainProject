package guiTabs;

//test
//another test comment

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import person.Person;
import person.Staff;
import retailSystem.PersonDB;
import retailSystem.RetailSystemDriver;

@SuppressWarnings("serial")
public class StaffTab extends PersonTab {

	private boolean enablePasswordEdit;
	private String password, password2, accessLevel;
	private JTextField passwordField1, passwordField2, accessLevelField;
	private JLabel passwordLabel1, passwordLabel2, accessLevelLabel;

	/**
	 * Set up the text fields
	 * 
	 * @param index
	 *            The selected index (default value)
	 * @param list
	 *            The list of staff
	 */
	public void setTextField(int index, ArrayList<Person> list) {

		super.setTextField(index, list);
		accessLevelField.setText("" + ((Staff) person).getAccessLevel());
		// passwordField1.setText(((Staff)person).getPassword());
		// passwordField2.setText(((Staff)person).getPassword());
		addItemsToCombobox(personDB.getStaffList());
		comboBox.setSelectedIndex(index);
		revalidate();
		repaint();
	}

	/**
	 * Clear everything in the fields
	 * 
	 * @param list
	 *            The list of staff
	 */
	public void clearTextFields(ArrayList<Person> list) {
		super.clearTextFields(list);

		accessLevelField.setText("");
		passwordField1.setText("");
		passwordField2.setText("");
		revalidate();
		repaint();
	}

	/**
	 * Create the panel.
	 */
	public StaffTab(PersonDB personDB) {

		// Field and label setup
		super(personDB);
		enablePasswordEdit = false;
		passwordLabel1 = new JLabel("Password");
		passwordLabel2 = new JLabel("Password Again");
		accessLevelLabel = new JLabel("Access Level");
		comboboxLabel.setText("Staff List");
		passwordField1 = new JTextField();
		passwordField2 = new JTextField();
		accessLevelField = new JTextField();

		// Initial visibility
		passwordField1.setVisible(false);
		passwordField2.setVisible(false);
		passwordLabel1.setVisible(false);
		passwordLabel2.setVisible(false);

		// Dimensions for elements
		accessLevelField.setBounds(200, 160, 265, 20);
		accessLevelField.setColumns(10);
		passwordField2.setBounds(200, 185, 265, 20);
		passwordField2.setColumns(10);
		passwordField1.setBounds(200, 210, 265, 20);
		passwordField1.setColumns(10);
		accessLevelLabel.setBounds(59, 160, 94, 14);
		passwordLabel1.setBounds(59, 185, 93, 14);
		passwordLabel2.setBounds(59, 210, 93, 14);

		// Finish adding elements to the panel
		setTextField(0, personDB.getStaffList());
		setFieldEditable(false);
		addAllElements();

	}

	/**
	 * Add every element to the panel
	 */
	public void addAllElements() {
		super.addAllElements();

		add(accessLevelLabel);
		add(passwordLabel1);
		add(passwordLabel2);

		add(accessLevelField);
		add(passwordField1);
		add(passwordField2);
	}

	/**
	 * Toggle wether you can edit fields or not
	 * 
	 * @param editable
	 *            The editable status of the fields
	 */
	public void setFieldEditable(boolean editable) {
		super.setFieldEditable(editable);
		accessLevelField.setEditable(editable);
	}

	/**
	 * Open a form for editing or creating details
	 */
	public void personDetailsForm() {
		super.personDetailsForm();
		int aLevel = 0;
		accessLevel = null;
		password = null;
		password2 = null;

		// Access level
		if (!accessLevelField.getText().equals("")) {
			accessLevel = accessLevelField.getText();
			accessLevelLabel.setForeground(Color.black);
		}
		else {
			errorMessage = errorMessage + "Access level field cannot be empty!\n";
			accessLevelLabel.setForeground(Color.red);
		}

		// Password field
		if (!passwordField1.getText().equals("") && !passwordField2.getText().equals("")) {
			password = (passwordField1.getText());
			password2 = (passwordField2.getText());
			passwordLabel1.setForeground(Color.black);
			passwordLabel2.setForeground(Color.black);
		}
		else {
			errorMessage = errorMessage + "Password fields cannot be empty!\n";
			passwordLabel1.setForeground(Color.red);
			passwordLabel2.setForeground(Color.red);
		}

		// Execute if fields are not empty
		if (name != null && address != null && email != null && contactNumber != null
				&& accessLevel != null && password != null && password2 != null) {

			// Check that passwords match
			if (password.equals(password2)) {
				passwordLabel2.setForeground(Color.black);
				try {
					aLevel = Integer.parseInt(accessLevel);
					accessLevelLabel.setForeground(Color.black);
				}
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Invalid input! Only numbers are allowed!");
					accessLevelLabel.setForeground(Color.red);
					e.printStackTrace();
				}

				if (RetailSystemDriver.validateAccessLevel(accessLevel)) {
					accessLevelLabel.setForeground(Color.black);
					valid = true;
				}
				else {
					JOptionPane.showMessageDialog(null, "Only access level 1 or 2 are valid!");
					errorMessage = errorMessage + "Only access level 1 or 2 are valid!\n";
					accessLevelLabel.setForeground(Color.red);
				}
				if (editMode) {
					System.out.println(valid);
					if (valid) {
						personDB.changePersonDetails(person, name, email, contactNumber, address,
								aLevel, password, null, null);
						System.out.println("Done");
						setTextField(comboBox.getSelectedIndex(), personDB.getStaffList());
					}
				}
				else {
					if (valid) {
						personDB.createNewPerson(person, name, email, contactNumber, address,
								aLevel, password, null, null);
						setTextField(personDB.getStaffList().size() - 1, personDB.getStaffList());
					}
				}
				// If all fields have valid inputs
				if (valid) {
					// Enable buttons
					deletePersonButton.setEnabled(true);
					editPersonButton.setEnabled(true);
					newPersonButton.setEnabled(true);

					// Set visibility for buttons
					newPersonButton.setVisible(true);
					editPersonButton.setVisible(true);
					submitButton.setVisible(false);
					cancelButton.setVisible(false);
					cancelEditButton.setVisible(false);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Passwords do not match!!!");
				passwordLabel2.setForeground(Color.red);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "" + errorMessage);
		}
		// Reste the view
		revalidate();
		repaint();

	}

	/**
	 * Toggles the visiblilty of the password fields
	 */
	public void togglePasswordField() {
		if (!enablePasswordEdit) {
			passwordField1.setVisible(true);
			passwordField2.setVisible(true);
			passwordLabel1.setVisible(true);
			passwordLabel2.setVisible(true);
		}
		else {
			passwordField1.setVisible(false);
			passwordField2.setVisible(false);
			passwordLabel1.setVisible(false);
			passwordLabel2.setVisible(false);
		}
		enablePasswordEdit = !enablePasswordEdit;
	}

	/**
	 * Action listeners
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// Use action listeners from the superclass
		super.actionPerformed(e);

		// Valid fields
		if (valid) {
			togglePasswordField();
			setFieldEditable(false);
			valid = false;
		}

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

		// New button clicked
		if (e.getSource() == newPersonButton) {
			clearTextFields(personDB.getStaffList());
			super.actionPerformed(e);
			setFieldEditable(true);
			togglePasswordField();

		}

		// Edit button clicked
		if (e.getSource() == editPersonButton) {
			// super.actionPerformed(e);
			togglePasswordField();
			setFieldEditable(true);
		}

		// Cancel button clicked
		if (e.getSource() == cancelButton) {

			setFieldEditable(false);
			togglePasswordField();
			setTextField(personDB.getStaffList().size() - 1, personDB.getStaffList());
			if (!(personDB.getStaffList().size() > 0))
				clearTextFields(personDB.getStaffList());
		}

		// Cancel button clicked in edit mode
		if (e.getSource() == cancelEditButton) {
			setTextField(comboBox.getSelectedIndex(), personDB.getStaffList());
			setFieldEditable(false);
			togglePasswordField();
			if (!(personDB.getStaffList().size() > 0))
				clearTextFields(personDB.getStaffList());
		}

		// Delete button clicked
		if (e.getSource() == deletePersonButton) {
			deletePerson(person, personDB.getStaffList());
		}

		// Reset the view
		revalidate();
		repaint();

	}

	/**
	 * Listener for the combo box
	 */
	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			setTextField(comboBox.getSelectedIndex(), personDB.getStaffList());
			revalidate();
			repaint();
		}
	}
}
