package retailSystem;

import guiTabs.MainGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import order.OrderDB;
import order.StockDBControl;
import person.Person;
import person.Staff;

/**
 * Launches a GUI for entering a username and password, with password validation taking place within
 * the Staff class.
 * 
 * @author Conor Clarke, modified by Roland
 */
@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener {

	private PersonDB personDB;
	private StockDBControl stockDBControl;
	private ArrayList<Person> staffList;
	private boolean login;
	private JButton btnNewButton;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private MainGUI gui;
	private OrderDB orderDB;

	/**
	 * Launches the window for the login screen
	 */
	public Login(PersonDB personDB, StockDBControl stockDBControl, OrderDB orderDB) {
		this.stockDBControl = stockDBControl;
		this.staffList = personDB.getStaffList();
		this.personDB = personDB;
		this.orderDB = orderDB;
		runLogin();
		login = false;
	}

	public void clearFields() {
		usernameField.setText("");
		passwordField.setText("");
	}

	public void runLogin() {
		// Setup code for the JFrame and Panel
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(459, 339);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Setup code for the username text field
		usernameField = new JTextField();
		usernameField.setBounds(162, 84, 86, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);

		// Label for the username
		JLabel userName = new JLabel("Username");
		userName.setBounds(162, 59, 86, 14);
		contentPane.add(userName);

		// Setup code for the password field
		passwordField = new JPasswordField();
		passwordField.setBounds(162, 140, 86, 20);
		contentPane.add(passwordField);

		// Label for the password
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(162, 115, 86, 14);
		contentPane.add(passwordLabel);

		// Login button and event handler
		btnNewButton = new JButton("Login");
		contentPane.getRootPane().setDefaultButton(btnNewButton);
		btnNewButton.addActionListener(this);

		// Add the login button to the panel
		btnNewButton.setBounds(162, 205, 89, 23);
		contentPane.add(btnNewButton);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewButton) {
			String usernameText = usernameField.getText();
			Staff user = null;
			for (Person person : staffList) {
				if (person.getName().equals(usernameText)) {
					user = (Staff) person;
					break;
				}
			}
			if (user != null) {
				char[] validate = passwordField.getPassword();
				login = ((Staff) user).passwordValidation(validate);
				if (login) {
					if (((Staff) user).getAccessLevel() == 2) {
						this.dispose();
						JOptionPane.showMessageDialog(null, "Login successful!");
						gui = new MainGUI((Staff) user, personDB, stockDBControl, this, orderDB);
						gui.getSupplierTab().enableButtons(true);
						gui.getCustomerTab().enableButtons(true);
						gui.getStaffTab().enableButtons(true);
						gui.getProductTab().enableButtons(true);
						RetailSystemDriver.setPriviledged(true);
						gui.setVisible(true);
						this.setVisible(false);
					}
					else {
						this.dispose();
						JOptionPane.showMessageDialog(null, "Login successful!");
						gui = new MainGUI(personDB, stockDBControl, this);
						gui.getSupplierTab().enableButtons(false);
						gui.getCustomerTab().enableButtons(false);
						gui.getStaffTab().enableButtons(false);
						gui.getProductTab().enableButtons(false);
						RetailSystemDriver.setPriviledged(false);
						this.setVisible(false);
					}

					setVisible(false);
					gui.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "Wrong Password!");
			}
			else
				JOptionPane.showMessageDialog(null, "Invalid username!");
		}
	}

	public void submit() {
		btnNewButton.doClick();
	}
}
