package com.dit.group2.gui;

import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
	private boolean emptiedList;
	private JScrollPane scrollPane;
	private JLabel idLabel;
	private JLabel customerLabel;
	private JLabel dateLabel;
	private JLabel staffLabel;
	private JTextArea textArea;
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JList<String> orderList = new JList<String>(listModel); 
	private JScrollPane listSroll = new JScrollPane(orderList);
	private TabListCellRenderer renderer = new TabListCellRenderer(true);
	//source for this class:
	//http://www.grandt.com/sbe/files/uts2/Chapter10html/Chapter10.htm
	
	public SupplyOrderHistoryTab(RetailSystemDriver driver){
		super();
		
		this.driver = driver;
		
		titleLabel.setText("Supply Order History");
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);

		idLabel = new JLabel("Order ID");
		dateLabel  = new JLabel("Date");
		customerLabel  = new JLabel("Customer ID");
		staffLabel  = new JLabel("Staff ID");
		
		idLabel.setBounds(18,7,150,23);
		dateLabel.setBounds(160,7,100,23);
		customerLabel.setBounds(285,7,150,23);
		staffLabel.setBounds(435,7,150,23);
		
		idLabel.setFont(new Font("Arial", Font.BOLD, 20));
		customerLabel.setFont(new Font("Arial", Font.BOLD, 20));
		staffLabel.setFont(new Font("Arial", Font.BOLD, 20));
		dateLabel.setFont(new Font("Arial", Font.BOLD, 20));
		
		orderList.addListSelectionListener(this);
		listSroll.setBounds(10, 30, 510, 310);					
		mainPanel.add(idLabel);
		mainPanel.add(listSroll);
		mainPanel.add(customerLabel);
		mainPanel.add(dateLabel);
		mainPanel.add(staffLabel);
		add(titleLabel);
		renderer.setTabs(new int[] {105, 325, 455});
		orderList.setCellRenderer(renderer);
		
		setLayout(null);
		setVisible(true);	
		
		showSupplyOrders();
		setOrderList();
		
	}
	
	public void setOrderList(){
		int index = 0;
		listModel.clear();
		for(Order order : driver.getOrderDB().getSupplyOrderList()){
			listModel.addElement(textAlignment("         "+order.getId(),""+order.getDate(),""+order.getPerson().getId(),""+order.getCurrentlyLoggedInStaff().getId()));						
			//stockList.setSelectedIndex(orderListIndex);	
			index++;
		}
	}
	
	private String textAlignment(String text1, String text2, String text3, String text4){
		String s = text1;
		s += "\t" + text2 + "\t" + text3 + "\t" + text4;		
		return s;
	}
	
	private void showOrderDetails(Order order){
		String orderDetailsMessage="ORDER DATE : "+order.getDate();
		
		orderDetailsMessage+="\nItems in this order: ";
		for(StockItem stockItem : order.getOrderEntryList()){
			orderDetailsMessage+="\n     "+stockItem.getQuantity()+" \t x \t "+stockItem.getProduct().getProductName()+" \t\t\t    Subtotal: \t\u20ac"+(stockItem.getProduct().getRetailPrice()*stockItem.getQuantity());
		}
		
		orderDetailsMessage+="\n\nSUPPLIER ID: "+order.getPerson().getId()+"\nBusiness Details: ";
		orderDetailsMessage+="\n     Company: \t"+order.getPerson().getName();
		orderDetailsMessage+="\n     VAT Number: \t"+((Supplier)order.getPerson()).getVatNumber();
		orderDetailsMessage+="\n     Phone: \t"+order.getPerson().getContactNumber();
		orderDetailsMessage+="\n     Address: \t"+order.getPerson().getAddress();
		orderDetailsMessage+="\n     Contact Name: \t"+((Supplier)order.getPerson()).getContactName();
		orderDetailsMessage+="\n\nThe total order value is \t\u20ac"+order.getGrandTotalOfOrder()+"\n";
		
		Object[] options = {"OK",
        "Set Processed"};
		int n = JOptionPane.showOptionDialog(null,
		orderDetailsMessage,
		 "ORDER ID: "+ (order.getId())+"    STAFF ID: "+order.getCurrentlyLoggedInStaff().getId()+" STATUS : "+order.isStatus(),
		JOptionPane.YES_NO_OPTION,
		JOptionPane.PLAIN_MESSAGE,
		null,
		options,
		options[0]);
		System.out.println(n);
		if(n==1){
			order.setStatus(true);
		}
	}
	
	private void showSupplyOrders(){
		for(int i=0;i<driver.getOrderDB().getSupplyOrderList().size();i++){
			Order order = (driver.getOrderDB().getSupplyOrderList().get(i));
			textArea.append("Order "+order.getId()+" was created by "+order.getCurrentlyLoggedInStaff().getName()+"\nSupplier ID : "+((Supplier)order.getPerson()).getId()+"\nItems in this order :");
			for(int j=0;j<order.getOrderEntryList().size();j++){
				StockItem stockItem = order.getOrderEntryList().get(j);
				textArea.append("\n  "+stockItem.getQuantity()+"  "+stockItem.getProduct().getProductName()+"  "+(stockItem.getProduct().getRetailPrice()*stockItem.getQuantity()));
			}
			textArea.append("\nThe total order value is "+order.getGrandTotalOfOrder()+"\n\n\n");
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == orderList){
			// check if order list is empty
			if(orderList.getSelectedIndex()>-1){
				// separate the text in the selected list item 
				String[] values = orderList.getSelectedValue().split("\\t");			
				showOrderDetails(driver.getOrderDB().getOrderById(Integer.parseInt(values[0].trim()), driver.getOrderDB().getSupplyOrderList()));
			}			
		}
		
	}	
}
