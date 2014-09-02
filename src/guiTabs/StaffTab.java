package guiTabs;

//test
//another test comment

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

	public void setTextField(int index, ArrayList<Person> list) {

		super.setTextField(index, list);

		accessLevelField.setText("" + ((Staff) person).getAccessLevel());
		passwordField1.setText(((Staff) person).getPassword());
		passwordField2.setText(((Staff) person).getPassword());
		addItemsToCombobox(personDB.getStaffList());
		comboBox.setSelectedIndex(index);
		revalidate();
		repaint();
	}

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

		super(personDB);
		enablePasswordEdit = false;
		passwordLabel1 = new JLabel("Password");
		passwordLabel2 = new JLabel("Password Again");
		accessLevelLabel = new JLabel("Access Level");
		comboboxLabel.setText("Staff");

		passwordField1 = new JTextField();
		passwordField2 = new JTextField();
		accessLevelField = new JTextField();

		passwordField1.setVisible(false);
		passwordField2.setVisible(false);
		passwordLabel1.setVisible(false);
		passwordLabel2.setVisible(false);

		accessLevelField.setBounds(200, 160, 265, 23);
		accessLevelField.setColumns(10);
		passwordField2.setBounds(200, 185, 265, 23);
		passwordField2.setColumns(10);
		passwordField1.setBounds(200, 210, 265, 23);
		passwordField1.setColumns(10);

		accessLevelLabel.setBounds(59, 160, 94, 20);
		passwordLabel1.setBounds(59, 185, 93, 20);
		passwordLabel2.setBounds(59, 210, 130, 20);

		setTextField(0, personDB.getStaffList());
		setFieldEditable(false);
		addAllElements();

	}

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

	public void setFieldEditable(boolean editable) {
		super.setFieldEditable(editable);
		accessLevelField.setEditable(editable);
	}

	public void personDetailsForm() {
		super.personDetailsForm();
		int aLevel = 0;
		accessLevel = null;
		password = null;
		password2 = null;

		if (!accessLevelField.getText().equals(""))
			accessLevel = accessLevelField.getText();
		else {
			errorMessage = errorMessage + "Access level field cannot be empty!\n";
		}
		if (!passwordField1.getText().equals("") && !passwordField2.getText().equals("")) {
			password = (passwordField1.getText());
			password2 = (passwordField2.getText());
		}
		else {
			errorMessage = errorMessage + "Password fields cannot be empty!\n";
		}
		if (name != null && address != null && email != null && contactNumber != null
				&& accessLevel != null && password != null && password2 != null) {
			if (!password.equals(password2)) {
				JOptionPane.showMessageDialog(null, "Passwords do not match!!!");
			}
			else {
				try {
					aLevel = Integer.parseInt(accessLevel);
				}
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Invalid input! Only numbers are allowed!");
					e.printStackTrace();
				}

				if (RetailSystemDriver.validateAccessLevel(accessLevel)) {

					valid = true;
				}
				else {
					JOptionPane.showMessageDialog(null, "Only access level 1 or 2 are valid!");
					errorMessage = errorMessage + "Only access level 1 or 2 are valid!\n";
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
				if (valid) {
					deletePersonButton.setEnabled(true);

					newPersonButton.setEnabled(true);
					newPersonButton.setVisible(true);

					editPersonButton.setEnabled(true);
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

	@Override
	public void actionPerformed(ActionEvent e) {

		super.actionPerformed(e);

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

		if (valid) {
			// super.actionPerformed(e);
			togglePasswordField();
			setFieldEditable(false);
			valid = false;
		}
		if (e.getSource() == newPersonButton) {
			clearTextFields(personDB.getStaffList());
			super.actionPerformed(e);
			setFieldEditable(true);
			togglePasswordField();

		}
		if (e.getSource() == editPersonButton) {
			// super.actionPerformed(e);
			togglePasswordField();
			setFieldEditable(true);
		}
		if (e.getSource() == cancelButton) {

			setFieldEditable(false);
			togglePasswordField();
			setTextField(personDB.getStaffList().size() - 1, personDB.getStaffList());
			if (!(personDB.getStaffList().size() > 0))
				clearTextFields(personDB.getStaffList());
		}
		if (e.getSource() == cancelEditButton) {
			setTextField(comboBox.getSelectedIndex(), personDB.getStaffList());
			setFieldEditable(false);
			togglePasswordField();
			if (!(personDB.getStaffList().size() > 0))
				clearTextFields(personDB.getStaffList());
		}

		if (e.getSource() == deletePersonButton) {
			deletePerson(person, personDB.getStaffList());
		}
		revalidate();
		repaint();

	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			setTextField(comboBox.getSelectedIndex(), personDB.getStaffList());
			revalidate();
			repaint();
		}
	}

}
