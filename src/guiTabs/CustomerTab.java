package guiTabs;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.ToolTipManager;

import order.Order;
import order.StockItem;
import person.Person;
import retailSystem.PersonDB;

/**
 * Tab containing customer operations
 */
@SuppressWarnings("serial")
public class CustomerTab extends PersonTab implements MouseListener {

	private CustomerOrderTab customerOrderTab;
	private JButton newCustomerButton;
	private JComboBox<String> orderComboBox;
	private DefaultComboBoxModel<String> orderComboboxModel;
	private Vector<String> orderComboboxItems;
	private JLabel orderLabel;
	private boolean automaticItemSelection;

	/**
	 * Customer tab constructor
	 * 
	 * @param personDB
	 *            The database for people
	 */
	public CustomerTab(PersonDB personDB, CustomerOrderTab customerOrderTab) {
		super(personDB);

		automaticItemSelection = true;
		newCustomerButton = new JButton();
		newCustomerButton.addActionListener(this);
		this.customerOrderTab = customerOrderTab;
		comboboxLabel.setText("Customer");
		orderComboboxItems = new Vector<String>();
		orderComboboxModel = new DefaultComboBoxModel<String>(orderComboboxItems);
		orderComboBox = new JComboBox<String>(orderComboboxModel);
		orderLabel = new JLabel("Orders");

		orderLabel.setBounds(59, 160, 94, 14);
		orderComboBox.setBounds(200, 160, 265, 23);
		orderComboBox.addItemListener(this);
		orderComboBox.addMouseListener(this);
		orderComboBox
				.setToolTipText("<html>Click on the text to see details of the currently selected order<br/>or click the down arrow to select another order.</html>");
		ToolTipManager.sharedInstance().setInitialDelay(0);
		mainPanel.add(orderComboBox);
		mainPanel.add(orderLabel);

		setTextField(0, personDB.getCustomerList());
		setFieldEditable(false);
		addAllElements();
	}

	/**
	 * Automatically add items to the combo box
	 */
	public void addItemsToOrderCombobox(boolean automaticItemSelection) {
		orderComboboxItems.clear();
		String item = "";

		for (Order order : customerOrderTab.getOrderDB().getCustomerOrderList()) {
			if (order.getPerson() == person) {
				item = "ID:\t" + order.getId() + " \t " + "Date: " + order.getDate() + "";
				orderComboboxItems.add(item);
			}
		}
		this.automaticItemSelection = automaticItemSelection;
		orderComboBox.setSelectedIndex(orderComboBox.getItemCount() - 1);

		revalidate();
		repaint();
	}

	public JButton getNewCustomerButton() {
		return newCustomerButton;
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
		// automaticItemSelection = true;
		comboBox.setSelectedIndex(index);
		addItemsToOrderCombobox(true);
		automaticItemSelection = false;
		revalidate();
		repaint();
	}

	/**
	 * Activate a form to edit or add customer details
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
				valid = true;
				personDB.createNewPerson(person, name, email, contactNumber, address, 0, null,
						null, null);
				setTextField(personDB.getCustomerList().size() - 1, personDB.getCustomerList());
			}

			// Set enabled status of buttons
			deletePersonButton.setEnabled(true);
			newPersonButton.setEnabled(true);
			editPersonButton.setEnabled(true);

			// Set visibility of buttons
			editPersonButton.setVisible(true);
			newPersonButton.setVisible(true);
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
			if (submitButtonMode == 3) {
				editMode = false;
				personDetailsForm();

				// If all details entered are valid
				if (valid) {
					personDB.getCustomerList();
					((JTabbedPane) customerOrderTab.getParent())
							.setSelectedComponent(customerOrderTab);
					customerOrderTab.fillUpCustomerComboBox();
					customerOrderTab.getCustomerComboBox().setSelectedIndex(
							personDB.getCustomerList().size());
				}
			}
			else if (submitButtonMode == 2) {
				editMode = true;
				personDetailsForm();
			}
			else if (submitButtonMode == 1) {
				editMode = false;
				personDetailsForm();
			}
			orderComboBox.setEnabled(true);
			comboBox.setEnabled(true);
			// customerOrderTab.fillUpCustomerComboBox();
		}

		if (valid) {
			setFieldEditable(false);
			valid = false;
		}

		// New person button clicked
		if (e.getSource() == newPersonButton) {
			clearTextFields(personDB.getCustomerList());
			orderComboBox.setEnabled(false);
			setFieldEditable(true);
		}

		// Edit button clicked
		if (e.getSource() == editPersonButton) {
			orderComboBox.setEnabled(false);
			setFieldEditable(true);
		}

		// Cancel button clicked
		if (e.getSource() == cancelButton) {
			setFieldEditable(false);
			orderComboBox.setEnabled(true);
			setTextField(personDB.getCustomerList().size() - 1, personDB.getCustomerList());
			if (!(personDB.getCustomerList().size() > 0))
				clearTextFields(personDB.getCustomerList());
		}

		// Cancel button clicekd in edit mod
		if (e.getSource() == cancelEditButton) {
			setTextField(comboBox.getSelectedIndex(), personDB.getCustomerList());
			orderComboBox.setEnabled(true);
			setFieldEditable(false);
			if (!(personDB.getCustomerList().size() > 0))
				clearTextFields(personDB.getCustomerList());
		}

		// Delete button clicked
		if (e.getSource() == deletePersonButton) {
			deletePerson(person, personDB.getCustomerList());
			customerOrderTab.fillUpCustomerComboBox();
			validate();
		}

		// New person button clicked
		if (e.getSource() == newCustomerButton) {
			newPersonButton.doClick();
			submitButtonMode = 3;
		}
		// Reset the viev
		revalidate();
		repaint();

	}

	/**
	 * Display the order details
	 * 
	 * @param order
	 */
	private void showOrderDetails(Order order) {
		String orderDetailsMessage = "ORDER DATE : " + order.getDate();

		orderDetailsMessage += "\nItems in this order: ";
		for (StockItem stockItem : order.getOrderEntryList()) {
			orderDetailsMessage += "\n     " + stockItem.getQuantity() + " \t x \t "
					+ stockItem.getProduct().getProductName() + " \t\t\t    Subtotal: \t\u20ac"
					+ (stockItem.getProduct().getRetailPrice() * stockItem.getQuantity());
		}

		orderDetailsMessage += "\n\nCUSTOMER ID: " + order.getPerson().getId()
				+ "\nPersonal Details: ";
		orderDetailsMessage += "\n     Name: \t" + order.getPerson().getName();
		orderDetailsMessage += "\n     Phone: \t" + order.getPerson().getContactNumber();
		orderDetailsMessage += "\n     Address: \t" + order.getPerson().getAddress();

		orderDetailsMessage += "\n\nThe total order value is \t\u20ac"
				+ order.getGrandTotalOfOrder() + "\n";

		JOptionPane.showMessageDialog(null, orderDetailsMessage, "ORDER ID: " + (order.getId())
				+ "    STAFF ID: " + order.getCurrentlyLoggedInStaff().getId(),
				JOptionPane.PLAIN_MESSAGE);
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {

			if (event.getItemSelectable().equals(comboBox)) {
				automaticItemSelection = true;
				setTextField(comboBox.getSelectedIndex(), personDB.getCustomerList());

			}
			if (event.getItemSelectable().equals(orderComboBox)) {

				String[] values = ((String) orderComboBox.getSelectedItem()).split("\\t");

				if (values != null && !automaticItemSelection) {
					showOrderDetails(customerOrderTab.getOrderDB().getOrderById(
							Integer.parseInt(values[1].trim()),
							customerOrderTab.getOrderDB().getCustomerOrderList()));
					automaticItemSelection = true;
				}
			}
			revalidate();
			repaint();
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		String[] values = ((String) orderComboBox.getSelectedItem()).split("\\t");
		if (values != null && !automaticItemSelection) {
			showOrderDetails(customerOrderTab.getOrderDB().getOrderById(
					Integer.parseInt(values[1].trim()),
					customerOrderTab.getOrderDB().getCustomerOrderList()));
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
