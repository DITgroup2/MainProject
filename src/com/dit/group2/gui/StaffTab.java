package com.dit.group2.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.dit.group2.person.Person;
import com.dit.group2.person.Staff;
import com.dit.group2.retailSystem.RetailSystemDriver;

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
		passwordField1.setText(((Staff) person).getPassword());
		passwordField2.setText(((Staff) person).getPassword());
		addItemsToCombobox(driver.getPersonDB().getStaffList());
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
	 * 
	 * @param driver
	 */
	public StaffTab(RetailSystemDriver driver) {
		// Field and label setup
		super(driver);
		titleLabel.setText("Staff Form");
		enablePasswordEdit = false;
		passwordLabel1 = new JLabel("Password");
		passwordLabel2 = new JLabel("Password Again");
		accessLevelLabel = new JLabel("Access Level");
		comboboxLabel.setText("Staff");

		passwordField1 = new JTextField();
		passwordField2 = new JTextField();
		accessLevelField = new JTextField();

		// Initial visibility
		passwordField1.setVisible(false);
		passwordField2.setVisible(false);
		passwordLabel1.setVisible(false);
		passwordLabel2.setVisible(false);

		// Dimensions for elements
		accessLevelField.setBounds(200, 160, 265, 23);
		accessLevelField.setColumns(10);
		passwordField2.setBounds(200, 185, 265, 23);
		passwordField2.setColumns(10);
		passwordField1.setBounds(200, 210, 265, 23);
		passwordField1.setColumns(10);

		titleLabel.setText("<html><div style=\"text-align: center;\"> Staff Form </html>");
		accessLevelLabel.setBounds(59, 160, 94, 20);
		passwordLabel1.setBounds(59, 185, 93, 20);
		passwordLabel2.setBounds(59, 210, 130, 20);

		// Set tool tips for clickable buttons
		newPersonButton.setToolTipText("Click to add a staff member.");
		editPersonButton.setToolTipText("Click to edit the details of the current staff member.");
		deletePersonButton.setToolTipText("Click to delete the current staff member.");
		cancelEditButton.setToolTipText("Click to cancel editing the current staff member.");
		cancelButton.setToolTipText("Click to exit the new staff member screen.");
		submitButton.setToolTipText("Submit changes/new staff member.");

		// Set tool tips for combobox items
		comboBox.setToolTipText("Click to view the details of another staff member.");

		// Other tool tips...
		accessLevelField
				.setToolTipText("Access levels: 1) Employee, 2) Manager (Privileged access)");

		// Finish adding elements to the panel
		setTextField(0, driver.getPersonDB().getStaffList());
		setFieldEditable(false);
		addAllElements();

	}

	/**
	 * Add every element to the panel
	 */
	public void addAllElements() {
		super.addAllElements();

		mainPanel.add(accessLevelLabel);
		mainPanel.add(passwordLabel1);
		mainPanel.add(passwordLabel2);
		mainPanel.add(accessLevelField);
		mainPanel.add(passwordField1);
		mainPanel.add(passwordField2);
		/*
		 * add(accessLevelLabel); add(passwordLabel1); add(passwordLabel2);
		 * 
		 * add(accessLevelField); add(passwordField1); add(passwordField2);
		 */
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
			if (!password.equals(password2)) {
				JOptionPane.showMessageDialog(null, "Passwords do not match!!!");
				passwordLabel1.setForeground(Color.red);
				passwordLabel2.setForeground(Color.red);
			}
			else {
				try {
					aLevel = Integer.parseInt(accessLevel);
				}
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Invalid input! Only numbers are allowed!");
					e.printStackTrace();
				}

				// Validate the access level
				if (RetailSystemDriver.validateAccessLevel(accessLevel)) {

					valid = true;
				}
				else {
					JOptionPane.showMessageDialog(null, "Only access level 1 or 2 are valid!");
					errorMessage = errorMessage + "Only access level 1 or 2 are valid!\n";
					accessLevelLabel.setForeground(Color.red);
				}
				// Edit mode selected
				if (editMode) {
					System.out.println(valid);
					if (valid) {
						driver.getPersonDB().changePersonDetails(person, name, email,
								contactNumber, address, aLevel, password, null, null);
						System.out.println("Done");
						setTextField(getIndex(driver.getPersonDB().getStaffList()), driver
								.getPersonDB().getStaffList());
					}
				}
				else if (valid) {
					driver.getPersonDB().createNewPerson(person, name, email, contactNumber,
							address, aLevel, password, null, null);
					setTextField(driver.getPersonDB().getStaffList().size() - 1, driver
							.getPersonDB().getStaffList());

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
		}
		else {
			JOptionPane.showMessageDialog(null, "" + errorMessage);
		}
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
			comboBox.setEnabled(true);
		}

		// Valid fields
		if (valid) {
			// super.actionPerformed(e);
			togglePasswordField();
			setFieldEditable(false);
			valid = false;
		}
		// New button clicked
		if (e.getSource() == newPersonButton) {
			clearTextFields(driver.getPersonDB().getStaffList());
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
			setTextField(driver.getPersonDB().getStaffList().size() - 1, driver.getPersonDB()
					.getStaffList());
			if (!(driver.getPersonDB().getStaffList().size() > 0))
				clearTextFields(driver.getPersonDB().getStaffList());
		}
		// Cancel button clicked in edit mode
		if (e.getSource() == cancelEditButton) {
			setTextField(getIndex(driver.getPersonDB().getStaffList()), driver.getPersonDB()
					.getStaffList());
			setFieldEditable(false);
			togglePasswordField();
			if (!(driver.getPersonDB().getStaffList().size() > 0))
				clearTextFields(driver.getPersonDB().getStaffList());
		}

		// Delete button clicked
		if (e.getSource() == deletePersonButton) {
			deletePerson(person, driver.getPersonDB().getStaffList());
		}
		revalidate();
		repaint();

	}

	public int getIndex(ArrayList<Person> list) {
		int index = super.getIndex(list);
		return index;
	}

	/**
	 * Listener for the combo box
	 */
	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			setTextField(getIndex(driver.getPersonDB().getStaffList()), driver.getPersonDB()
					.getStaffList());
			revalidate();
			repaint();
		}
	}

}
