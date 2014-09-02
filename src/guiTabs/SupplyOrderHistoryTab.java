package guiTabs;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import order.Order;
import order.OrderDB;
import order.StockItem;
import person.Customer;
import person.Supplier;
import retailSystem.Product;

public class SupplyOrderHistoryTab extends JPanel implements ComponentListener, ListSelectionListener{

	
	protected boolean emptiedList;
	private JScrollPane scrollPane;
	
	protected JLabel idLabel;
	protected JLabel customerLabel;
	protected JLabel dateLabel;
	protected JLabel staffLabel;
	
	private OrderDB orderDB;
	protected Vector<String> comboboxItems;
	protected Product product;
	protected String name;
	
	protected DefaultListModel<String> listModel = new DefaultListModel<String>();
	protected JList<String> orderList = new JList<String>(listModel); 
	protected JScrollPane listSroll = new JScrollPane(orderList);
	
	private TabListCellRenderer renderer = new TabListCellRenderer(true);
	//source for this class:
	//http://www.grandt.com/sbe/files/uts2/Chapter10html/Chapter10.htm
	
	protected JTextArea textArea;
	
	private JPanel mainPanel,outerPanel;
	private int xSize;
	private int ySize;
	private int xPosition;
	private int yPosition;
	
	public SupplyOrderHistoryTab(OrderDB orderDB){
		this.orderDB = orderDB;
		
		
		xSize= 530;
		ySize = 350;	
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		outerPanel = new JPanel();
		outerPanel.setLayout(null);
		outerPanel.add(mainPanel);
		outerPanel.setBorder(BorderFactory.createLineBorder(new Color(176, 168, 168),8,true));
		this.setBorder(BorderFactory.createLineBorder(new Color(176, 168, 168),4));
		add(outerPanel, new GridBagConstraints());
		addComponentListener(this);
		
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);

		
		//mainPanel.add(scrollPane);
		//scrollPane.setBounds(10,30,510,310);
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
		
		renderer.setTabs(new int[] {105, 325, 455});
		orderList.setCellRenderer(renderer);
		
		setLayout(null);
		setVisible(true);	
		
		showCustomerOrders();
		setOrderList();
		
	}
	
	
	public void setOrderList(){
		
		int index = 0;
		listModel.clear();
		for(Order order : orderDB.getSupplyOrderList()){
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
		
		JOptionPane.showMessageDialog(null, orderDetailsMessage,  "ORDER ID: "+ (order.getId())+"    STAFF ID: "+order.getCurrentlyLoggedInStaff().getId()  , JOptionPane.PLAIN_MESSAGE);
	}
	
	
	public void showCustomerOrders(){
		textArea.setText("");
		for(int i=0;i<orderDB.getCustomerOrderList().size();i++){
			Order order = (orderDB.getCustomerOrderList().get(i));
			textArea.append("Order "+order.getId()+" was created by "+order.getCurrentlyLoggedInStaff().getName()+"\nCustomer ID : "+((Customer)order.getPerson()).getId()+"\nItems in this order :");
			for(int j=0;j<order.getOrderEntryList().size();j++){
				StockItem stockItem = order.getOrderEntryList().get(j);
				textArea.append("\n  "+stockItem.getQuantity()+"  "+stockItem.getProduct().getProductName()+"  "+(stockItem.getProduct().getRetailPrice()*stockItem.getQuantity()));
			}
			textArea.append("\nThe total order value is "+order.getGrandTotalOfOrder()+"\n\n\n");
		}
	}
	
	private void showSupplyOrders(){
		for(int i=0;i<orderDB.getSupplyOrderList().size();i++){
			Order order = (orderDB.getSupplyOrderList().get(i));
			textArea.append("Order "+order.getId()+" was created by "+order.getCurrentlyLoggedInStaff().getName()+"\nCustomer ID : "+((Customer)order.getPerson()).getId()+"\nItems in this order :");
			for(int j=0;j<order.getOrderEntryList().size();j++){
				StockItem stockItem = order.getOrderEntryList().get(j);
				textArea.append("\n  "+stockItem.getQuantity()+"  "+stockItem.getProduct().getProductName()+"  "+(stockItem.getProduct().getRetailPrice()*stockItem.getQuantity()));
			}
			textArea.append("\nThe total order value is "+order.getGrandTotalOfOrder()+"\n\n\n");
		}
	}
	
	public void componentResized(ComponentEvent e) {
		
		xPosition = ((((int) mainPanel.getParent().getSize().getWidth())-xSize)/2);
		yPosition = ((((int) mainPanel.getParent().getSize().getHeight())-ySize)/2);
		mainPanel.setBounds(xPosition, yPosition, xSize, ySize);
		
		xPosition = ((((int) outerPanel.getParent().getSize().getWidth())-xSize)/2);
		yPosition = ((((int) outerPanel.getParent().getSize().getHeight())-ySize)/2);
		outerPanel.setBounds(xPosition-50, yPosition-40, xSize+100, ySize+80);
		
	}
	
	
	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == orderList){
			// check if order list is empty
			if(orderList.getSelectedIndex()>-1){
				// separate the text in the selected list item 
				String[] values = orderList.getSelectedValue().split("\\t");
				//JOptionPane.showMessageDialog(null, values[1]);
				
				showOrderDetails(orderDB.getOrderById(Integer.parseInt(values[0].trim()), orderDB.getSupplyOrderList()));
			}			
		}
		
	}
	


	
	
}
