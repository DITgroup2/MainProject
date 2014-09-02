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
import javax.swing.JTextField;
import javax.swing.ToolTipManager;

import order.StockItem;
import person.Person;
import person.Supplier;
import retailSystem.PersonDB;
import retailSystem.Product;

public class SupplierTab extends PersonTab implements MouseListener {

	private JButton newSupplierButton;
	private JTabbedPane tabbedPane;
	private ProductTab productTab;
	private MainGUI gui;
	private String contactName, vatNumber;
	private JTextField vatNumberField, contactNameField;
	private JLabel vatNumberLabel, contactNameLabel, productsLabel;
	private JComboBox<String> productsComboBox;
	private DefaultComboBoxModel<String> productsComboboxModel;
	private Vector<String> productsComboboxItems;
	private boolean automaticItemSelection;

	public void setTextField(int index, ArrayList<Person> list) {

		super.setTextField(index, list);

		vatNumberField.setText(((Supplier) person).getVatNumber());
		contactNameField.setText(((Supplier) person).getContactName());
		addItemsToCombobox(personDB.getSupplierList());
		automaticItemSelection = true;
		comboBox.setSelectedIndex(index);
		addItemsToProductCombobox();
		revalidate();
		repaint();
	}

	public void clearTextFields(ArrayList<Person> list) {

		super.clearTextFields(list);

		vatNumberField.setText("");
		contactNameField.setText("");
		revalidate();
		repaint();
	}

	/**
	 * Create the panel.
	 */
	public SupplierTab(PersonDB personDB, JTabbedPane tabbedPane, MainGUI gui) {

		super(personDB);

		this.tabbedPane = tabbedPane;
		this.gui = gui;
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

		setTextField(0, personDB.getSupplierList());
		setFieldEditable(false);
		addAllElements();
	}

	public JButton getNewSupplierButton() {
		return newSupplierButton;
	}

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

	public void addItemsToProductCombobox() {
		productsComboboxItems.clear();
		String item = "<html><font color='red'>Add New Product</font></html>";
		productsComboboxItems.add(item);
		for (StockItem stockItem : gui.getStockDBControl().getStockList()) {
			Product product = stockItem.getProduct();
			if (product.getSupplier() == person) {
				item = "ID:\t" + product.getProductID() + " \t " + "" + product.getProductName()
						+ "";
				productsComboboxItems.add(item);
			}
		}

		productsComboBox.setSelectedIndex(productsComboBox.getItemCount() - 1);
		automaticItemSelection = false;
		revalidate();
		repaint();
	}

	public void personDetailsForm() {
		super.personDetailsForm();

		vatNumber = null;
		contactName = null;

		if (!contactNameField.getText().equals(""))
			contactName = contactNameField.getText();
		else {
			errorMessage = errorMessage + "Contact name field cannot be empty!\n";
		}
		if (!vatNumberField.getText().equals(""))
			vatNumber = vatNumberField.getText();
		else {
			errorMessage = errorMessage + "VAT number field cannot be empty!\n";
		}

		if (name != null && address != null && email != null && contactNumber != null
				&& vatNumber != null && contactName != null) {

			if (editMode) {
				personDB.changePersonDetails(person, name, email, contactNumber, address, 0, null,
						vatNumber, contactName);
				setTextField(comboBox.getSelectedIndex(), personDB.getSupplierList());
				valid = true;
			}
			else {

				valid = true;
				personDB.createNewPerson(person, name, email, contactNumber, address, 0, null,
						contactName, vatNumber);
				setTextField(personDB.getSupplierList().size() - 1, personDB.getSupplierList());
			}

			deletePersonButton.setEnabled(true);

			newPersonButton.setEnabled(true);
			newPersonButton.setVisible(true);

			editPersonButton.setEnabled(true);
			editPersonButton.setVisible(true);

			submitButton.setVisible(false);

			cancelButton.setVisible(false);
			cancelEditButton.setVisible(false);
		}

		else {
			JOptionPane.showMessageDialog(null, "" + errorMessage);
		}
		revalidate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		super.actionPerformed(e);

		if (e.getSource() == submitButton) {
			if (submitButtonMode == 3) {
				editMode = false;
				personDetailsForm();
				if (valid) {
					tabbedPane.setSelectedComponent(gui.getProductTab());
					gui.getProductTab().addItemsToSupplierCombobox(personDB.getSupplierList());
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
			comboBox.setEnabled(true);
		}

		if (valid) {
			setFieldEditable(false);
			valid = false;
		}
		if (e.getSource() == newPersonButton) {
			clearTextFields(personDB.getSupplierList());
			super.actionPerformed(e);
			setFieldEditable(true);
		}
		if (e.getSource() == editPersonButton) {
			setFieldEditable(true);
		}
		if (e.getSource() == cancelButton) {
			setFieldEditable(false);
			setTextField(personDB.getSupplierList().size() - 1, personDB.getSupplierList());
			if (!(personDB.getSupplierList().size() > 0))
				clearTextFields(personDB.getSupplierList());
		}
		if (e.getSource() == cancelEditButton) {
			setTextField(comboBox.getSelectedIndex(), personDB.getSupplierList());
			setFieldEditable(false);
			if (!(personDB.getSupplierList().size() > 0))
				clearTextFields(personDB.getSupplierList());
		}

		if (e.getSource() == deletePersonButton) {
			deletePerson(person, personDB.getSupplierList());
		}

		if (e.getSource() == newSupplierButton) {
			newPersonButton.doClick();
			submitButtonMode = 3;
		}

		revalidate();
		repaint();
	}

	public void setFieldEditable(boolean editable) {
		super.setFieldEditable(editable);
		vatNumberField.setEditable(editable);
		contactNameField.setEditable(editable);
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			if (event.getItemSelectable().equals(comboBox)) {
				automaticItemSelection = true;
				setTextField(comboBox.getSelectedIndex(), personDB.getSupplierList());
			}
			if (event.getItemSelectable().equals(productsComboBox)) {
				if (productsComboBox.getItemAt((productsComboBox.getSelectedIndex())).equals(
						"<html><font color='red'>Add New Product</font></html>")) {
					// unknown nullpointer exception occurs here someimes!!!!!!!!
					tabbedPane.setSelectedComponent(gui.getProductTab());
					gui.getProductTab().getNewSupplierProductButton(person).doClick();
				}
				else if (gui.getProductTab() != null) {

					String[] values = ((String) productsComboBox.getSelectedItem()).split("\\t");
					if (values != null && !automaticItemSelection) {
						tabbedPane.setSelectedComponent(gui.getProductTab());
						gui.getProductTab().setSelectedProduct(
								gui.getStockDBControl().getStockItem(
										Integer.parseInt(values[1].trim())));
					}
				}

				/*
				 * supplier = personDB.getSupplierByName(supplierComboBox
				 * .getItemAt(supplierComboBox.getSelectedIndex()));
				 */
			}

		}
		revalidate();
		repaint();

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		String[] values = ((String) productsComboBox.getSelectedItem()).split("\\t");
		if (values != null && !automaticItemSelection) {
			tabbedPane.setSelectedComponent(gui.getProductTab());
			gui.getProductTab().setSelectedProduct(
					gui.getStockDBControl().getStockItem(Integer.parseInt(values[1].trim())));

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}