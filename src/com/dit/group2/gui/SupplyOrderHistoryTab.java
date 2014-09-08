package com.dit.group2.gui;

import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.dit.group2.order.Order;
import com.dit.group2.person.Customer;
import com.dit.group2.person.Supplier;
import com.dit.group2.retailSystem.RetailSystemDriver;
import com.dit.group2.stock.Product;
import com.dit.group2.stock.StockItem;


@SuppressWarnings("serial")
public class SupplyOrderHistoryTab extends GuiLayout implements ListSelectionListener{
	
	private RetailSystemDriver driver;
	private JScrollPane scrollPane;

	private JLabel idLabel, supplierLabel, dateLabel, staffLabel, tickLabel;
	private JTextArea textArea;

	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JList<String> orderList = new JList<String>(listModel);
	private JScrollPane listSroll = new JScrollPane(orderList);
	private JScrollBar scrollBar;

	private TabListCellRenderer renderer = new TabListCellRenderer(true);

	// source for this class:
	// http://www.grandt.com/sbe/files/uts2/Chapter10html/Chapter10.htm

	public SupplyOrderHistoryTab(RetailSystemDriver driver) {
		super();

		this.driver = driver;

		titleLabel.setText("Supply Order History");
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
		idLabel = new JLabel("ID");
		dateLabel = new JLabel("Date");
		supplierLabel = new JLabel("Supplier");
		staffLabel = new JLabel("Staff");
		tickLabel = new JLabel("Received");

		idLabel.setBounds(17, 7, 150, 23);
		dateLabel.setBounds(100, 7, 100, 23);
		supplierLabel.setBounds(235, 7, 150, 23);
		staffLabel.setBounds(344, 7, 150, 23);
		tickLabel.setBounds(410, 7, 150, 23);

		idLabel.setFont(new Font("Arial", Font.BOLD, 18));
		supplierLabel.setFont(new Font("Arial", Font.BOLD, 18));
		staffLabel.setFont(new Font("Arial", Font.BOLD, 18));
		dateLabel.setFont(new Font("Arial", Font.BOLD, 18));
		tickLabel.setFont(new Font("Arial", Font.BOLD, 18));

		orderList.addListSelectionListener(this);
		listSroll.setBounds(10, 30, 510, 310);
		mainPanel.add(tickLabel);
		mainPanel.add(idLabel);
		mainPanel.add(listSroll);
		mainPanel.add(supplierLabel);
		mainPanel.add(dateLabel);
		mainPanel.add(staffLabel);
		add(titleLabel);
		renderer.setTabs(new int[] { 55, 230, 337, 435 });
		orderList.setCellRenderer(renderer);

		setLayout(null);
		setVisible(true);

		showSupplyOrders();
		setOrderList();

	}

	/**
	 * Set the order list
	 */
	public void setOrderList() {
		listModel.clear();
		for (Order order : driver.getOrderDB().getSupplyOrderList()) {
			if(order.isStatus())
				listModel.addElement(textAlignment(""+order.getId(), "" + order.getDateString(), ""
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
			status = "Received";
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

		Object[] options = { "OK", "Set to Delivered" };
		int n = JOptionPane.showOptionDialog(null, orderDetailsMessage, "ORDER ID: "
				+ (order.getId()) + "    STAFF ID: " + order.getCurrentlyLoggedInStaff().getId()
				, JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (n == 1) {
			order.setStatus(true);
			updateStock(order);
			setOrderList();
		}

	}
	
	public void updateStock(Order order){
		for (StockItem stockItem : order.getOrderEntryList()) {
			driver.getStockDB().getStockItem(stockItem.getProduct().getProductID()).setQuantity((driver.getStockDB().getStockItem(stockItem.getProduct().getProductID()).getQuantity())+stockItem.getQuantity());
		}
		driver.getGui().getStockControlTab().refreshStockControlTab();
	}

	/**
	 * Display the customer orders
	 */
	public void showSupplyOrders() {
		textArea.setText("");
		for (int i = 0; i < driver.getOrderDB().getSupplyOrderList().size(); i++) {
			Order order = (driver.getOrderDB().getSupplyOrderList().get(i));
			textArea.append("Order " + order.getId() + " was created by "
					+ order.getCurrentlyLoggedInStaff().getName() + "\nCustomer ID : "
					+ ((Supplier) order.getPerson()).getId() + "\nItems in this order :");
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
						driver.getOrderDB().getSupplyOrderList()));
			}
		}

	}
}
