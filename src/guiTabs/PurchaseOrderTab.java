package guiTabs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;

public class PurchaseOrderTab extends JPanel implements ActionListener, ItemListener {

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
	/*
	 * private StockDBControl stockControl; private PersonDB personDB; private Vector<String>
	 * productComboboxItems,orderComboboxItems; private Product product; private Object[] object;
	 * protected JButton
	 * newOrderButton,editOrderButton,deleteOrderButton,submitButton,cancelButton,cancelEditButton;
	 * private JTextArea orderListArea; private JTextField productQuantityFiled; private JLabel
	 * productLabel,productQuantityLabel, orderItemLabel; private JComboBox<String>
	 * productComboBox,orderCombobox; private DefaultComboBoxModel<String>
	 * productComboboxModel,orderComboboxModel; private JScrollPane scrollPane;
	 * 
	 * public PurchaseOrderTab(StockDBControl StockDBControl,PersonDB personDB){ this.stockDBControl
	 * = stockControl; this.personDB = personDB;
	 * 
	 * productComboboxItems = new Vector<String>(); orderComboboxItems = new Vector<String>();
	 * productComboboxModel = new DefaultComboBoxModel<String>(productComboboxItems);
	 * orderComboboxModel = new DefaultComboBoxModel<String>(orderComboboxItems); productComboBox =
	 * new JComboBox<String>(productComboboxModel); orderCombobox = new
	 * JComboBox<String>(orderComboboxModel);
	 * 
	 * newOrderButton = new JButton("Add New"); editOrderButton = new JButton("Edit");
	 * deleteOrderButton = new JButton("Delete"); submitButton = new JButton("Submit"); cancelButton
	 * = new JButton("Cancel"); cancelEditButton = new JButton("Cancel");
	 * 
	 * productQuantityFiled = new JTextField(); orderListArea = new JTextArea(50,40);
	 * orderListArea.setLineWrap(true); orderListArea.setWrapStyleWord(true);
	 * 
	 * 
	 * productLabel = new JLabel("Choose Product"); productQuantityLabel = new
	 * JLabel("Set Quantity"); orderItemLabel = new JLabel("Current Order");
	 * 
	 * 
	 * newOrderButton.addActionListener(this); editOrderButton.addActionListener(this);
	 * deleteOrderButton.addActionListener(this); submitButton.addActionListener(this);
	 * submitButton.setVisible(false); cancelButton.addActionListener(this);
	 * cancelButton.setVisible(false); cancelEditButton.addActionListener(this);
	 * cancelEditButton.setVisible(false);
	 * 
	 * 
	 * submitButton.setBounds(200, 250, 106, 23); newOrderButton.setBounds(64, 320, 130, 23);
	 * cancelButton.setBounds(64, 320, 130, 23); cancelEditButton.setBounds(199, 320, 130, 23);
	 * editOrderButton.setBounds(199, 320, 130, 23); deleteOrderButton.setBounds(335, 320, 130, 23);
	 * 
	 * scrollPane = new JScrollPane(orderListArea); scrollPane.setBounds(150, 8, 315, 220);
	 * 
	 * productLabel.setBounds(15, 285, 120, 20); productComboBox.setBounds(110, 285, 240, 20);
	 * productComboBox.addItemListener(this); productQuantityLabel.setBounds(360,285,120,20);
	 * productQuantityFiled.setBounds(435,285,60,20);
	 * 
	 * setLayout(null); setVisible(true); // setTextField(0,stockControl.getStockList()); //
	 * retailPriceField.setEditable(false); // supplierField.setEditable(false); //
	 * setFieldEditable(false); addAllElements();
	 * 
	 * }
	 * 
	 * public void addAllElements(){
	 * 
	 * add(newOrderButton); add(deleteOrderButton); add(submitButton); add(editOrderButton);
	 * add(cancelButton); add(cancelEditButton); add(productComboBox); add(productLabel);
	 * add(productQuantityLabel); add(productQuantityFiled);
	 * 
	 * add(scrollPane);
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * @Override public void itemStateChanged(ItemEvent e) { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void actionPerformed(ActionEvent e) { // TODO Auto-generated method stub
	 * 
	 * }
	 */

}
