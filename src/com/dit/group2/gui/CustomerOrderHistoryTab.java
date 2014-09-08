package com.dit.group2.gui;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.w3c.dom.ls.LSInput;

import com.dit.group2.order.Order;
import com.dit.group2.person.Customer;
import com.dit.group2.retailSystem.RetailSystemDriver;
import com.dit.group2.stock.StockItem;

@SuppressWarnings("serial")
public class CustomerOrderHistoryTab extends GuiLayout implements ListSelectionListener {

	private RetailSystemDriver driver;
	private JScrollPane scrollPane;

	private JLabel idLabel, customerLabel, dateLabel, staffLabel, tickLabel;
	private JTextArea textArea;

	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JList<String> orderList = new JList<String>(listModel);
	private JScrollPane listSroll = new JScrollPane(orderList);
	private JScrollBar scrollBar;

	private TabListCellRenderer renderer = new TabListCellRenderer(true);

	// source for this class:
	// http://www.grandt.com/sbe/files/uts2/Chapter10html/Chapter10.htm

	public CustomerOrderHistoryTab(RetailSystemDriver driver) {
		super();

		this.driver = driver;

		titleLabel.setText("Customer Order History");
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
		idLabel = new JLabel("ID");
		dateLabel = new JLabel("Date");
		customerLabel = new JLabel("Customer");
		staffLabel = new JLabel("Staff");
		tickLabel = new JLabel("Dispatched");

		idLabel.setBounds(12, 7, 150, 23);
		dateLabel.setBounds(100, 7, 100, 23);
		customerLabel.setBounds(223, 7, 150, 23);
		staffLabel.setBounds(344, 7, 150, 23);
		tickLabel.setBounds(410, 7, 150, 23);

		idLabel.setFont(new Font("Arial", Font.BOLD, 18));
		customerLabel.setFont(new Font("Arial", Font.BOLD, 18));
		staffLabel.setFont(new Font("Arial", Font.BOLD, 18));
		dateLabel.setFont(new Font("Arial", Font.BOLD, 18));
		tickLabel.setFont(new Font("Arial", Font.BOLD, 18));

		orderList.addListSelectionListener(this);
		listSroll.setBounds(10, 30, 510, 310);
		mainPanel.add(tickLabel);
		mainPanel.add(idLabel);
		mainPanel.add(listSroll);
		mainPanel.add(customerLabel);
		mainPanel.add(dateLabel);
		mainPanel.add(staffLabel);
		add(titleLabel);
		renderer.setTabs(new int[] { 55, 230, 337, 435 });
		orderList.setCellRenderer(renderer);

		setLayout(null);
		setVisible(true);

		showCustomerOrders();
		setOrderList();

	}

	/**
	 * Set the order list
	 */
	public void setOrderList() {
		listModel.clear();
		for (Order order : driver.getOrderDB().getCustomerOrderList()) {
			if(order.isStatus())
				listModel.addElement(textAlignment(" " + order.getId(), "" + order.getDateString(), ""
								+order.getPerson().getName(), ""
								+order.getCurrentlyLoggedInStaff().getName(), "\u2713"));
			else
				listModel.addElement(textAlignment(" " + order.getId(), "" + order.getDateString(), ""
						+ order.getPerson().getId(), ""
						+ order.getCurrentlyLoggedInStaff().getId(), ""));
			
		}
		scrollBar = listSroll.getVerticalScrollBar();
		scrollBar.setValue(listSroll.getVerticalScrollBar().getMaximum());
	}

	/**
	 * Align text
	 * 
	 * @param text1
	 * @param text2
	 * @param text3
	 * @param text4
	 * @return
	 */
	private String textAlignment(String text1, String text2, String text3, String text4, String text5) {
		String s = text1;
		s += "\t" + text2 + "\t" + text3 + "\t" + text4+ "\t" + text5;
		return s;
	}

	/**
	 * Display the details of the order
	 * 
	 * @param order
	 */
	private void showOrderDetails(Order order) {
		String status = "";
		if(order.isStatus())
			status = "Dispatched";
		else
			status = "Pending";
		String orderDetailsMessage = "ORDER DATE : " + order.getDateString()+ "\nORDER STATUS : " + status;
		orderDetailsMessage += "\nItems in this order: ";
		for (StockItem stockItem : order.getOrderEntryList()) {
			orderDetailsMessage += "\n     " + stockItem.getQuantity() + " \t x \t "
					+ stockItem.getProduct().getProductName() + " \t\t\t    Subtotal: \t\u20ac"
					+ (stockItem.getProduct().getRetailPrice() * stockItem.getQuantity());
		}

		orderDetailsMessage += "\n\nCUSTOMER ID: " + order.getPerson().getId()+ "\nPersonal Details: ";
		orderDetailsMessage += "\n     Name: \t" + order.getPerson().getName();
		orderDetailsMessage += "\n     Phone: \t" + order.getPerson().getContactNumber();
		orderDetailsMessage += "\n     Address: \t" + order.getPerson().getAddress();
		orderDetailsMessage += "\n\nThe total order value is \t\u20ac"
				+ order.getGrandTotalOfOrder() + "\n";

		Object[] options = { "OK", "Dispatch" };
		int n = JOptionPane.showOptionDialog(null, orderDetailsMessage, "ORDER ID: "
				+ (order.getId()) + "    STAFF ID: " + order.getCurrentlyLoggedInStaff().getId()
				, JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (n == 1) {
			order.setStatus(true);
			setOrderList();
		}

	}

	/**
	 * Display the customer orders
	 */
	public void showCustomerOrders() {
		textArea.setText("");
		for (int i = 0; i < driver.getOrderDB().getCustomerOrderList().size(); i++) {
			Order order = (driver.getOrderDB().getCustomerOrderList().get(i));
			textArea.append("Order " + order.getId() + " was created by "
					+ order.getCurrentlyLoggedInStaff().getName() + "\nCustomer ID : "
					+ ((Customer) order.getPerson()).getId() + "\nItems in this order :");
			for (int j = 0; j < order.getOrderEntryList().size(); j++) {
				StockItem stockItem = order.getOrderEntryList().get(j);
				textArea.append("\n  " + stockItem.getQuantity() + "  "
						+ stockItem.getProduct().getProductName() + "  "
						+ (stockItem.getProduct().getRetailPrice() * stockItem.getQuantity()));
			}
			textArea.append("\nThe total order value is " + order.getGrandTotalOfOrder() + "\n\n\n");
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == orderList) {
			// check if order list is empty
			if (orderList.getSelectedIndex() > -1) {
				// separate the text in the selected list item
				String[] values = orderList.getSelectedValue().split("\\t");
				// JOptionPane.showMessageDialog(null, values[1]);

				showOrderDetails(driver.getOrderDB().getOrderById(
						Integer.parseInt(values[0].trim()),
						driver.getOrderDB().getCustomerOrderList()));
			}
		}

	}
}