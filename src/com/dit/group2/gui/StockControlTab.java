package com.dit.group2.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ToolTipManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.dit.group2.retailSystem.RetailSystemDriver;
import com.dit.group2.stock.Product;
import com.dit.group2.stock.StockItem;

@SuppressWarnings("serial")
public class StockControlTab extends GuiLayout implements ListSelectionListener, MouseMotionListener {

	private final RetailSystemDriver driver;

	protected JLabel productIdLabel;
	protected JLabel productNameLabel;
	protected JLabel productCategoryLabel;
	protected JLabel quantityLabel;

	protected DefaultListModel<String> listModel = new DefaultListModel<String>();
	protected JList<String> stockList = new JList<String>(listModel);
	
	
	protected JScrollPane stockListSroll = new JScrollPane(stockList);

	private TabListCellRenderer renderer = new TabListCellRenderer(true);
	
	// source for this class:
	// http://www.grandt.com/sbe/files/uts2/Chapter10html/Chapter10.htm

	/**
	 * Aligns text in the text pane
	 * 
	 * @param text1
	 * @param text2
	 * @param text3
	 * @param text4
	 * @return
	 */
	private String textAlignment(String text1, String text2, String text3, String text4) {
		String s = text1;
		s += "\t" + text2 + "\t" + text3 + "\t" + text4;
		return s;
	}

	public StockControlTab(RetailSystemDriver driver) {
		// Initialisation
		super();
		this.driver = driver;

		ToolTipManager.sharedInstance().registerComponent(stockList);
		titleLabel.setText("Stock List");
		add(outerPanel, new GridBagConstraints());
		addComponentListener(this);

		productIdLabel = new JLabel("ID");
		productNameLabel = new JLabel("Product Name");
		productCategoryLabel = new JLabel("Category");
		quantityLabel = new JLabel("Quantity");

		// Set dimensions
		productIdLabel.setBounds(18, 7, 40, 23);
		productNameLabel.setBounds(77, 7, 340, 23);
		quantityLabel.setBounds(280, 7, 100, 23);
		productCategoryLabel.setBounds(390, 7, 150, 23);

		// Change the font
		productIdLabel.setFont(new Font("Arial", Font.BOLD, 20));
		productNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
		quantityLabel.setFont(new Font("Arial", Font.BOLD, 20));
		productCategoryLabel.setFont(new Font("Arial", Font.BOLD, 20));

		// Finish setup and start the GUI
		stockList.addListSelectionListener(this);
		stockListSroll.setBounds(10, 30, 510, 310);
		mainPanel.add(productIdLabel);
		mainPanel.add(stockListSroll);
		mainPanel.add(productNameLabel);
		mainPanel.add(productCategoryLabel);
		mainPanel.add(quantityLabel);
		add(titleLabel);
		renderer.setTabs(new int[] { 70, 300, 400 });
		stockList.setCellRenderer(renderer);
		stockList.addMouseListener(this);
		setLayout(null);
		setVisible(true);
		refreshStockControlTab();
		
		// Attach a mouse motion adapter to let us know the mouse is over an item and to show the tip.
		stockList.addMouseMotionListener(this);
	}
	

    public void mouseMoved( MouseEvent e) {
        int index = stockList.locationToIndex(e.getPoint());
        if (index > -1) {
        	stockList.setToolTipText(null);
        	String[] values = ((String)listModel.getElementAt(index)).split("\\t");
        	Product product = driver.getStockDB().getStockItem(Integer.parseInt(values[0].trim())).getProduct();
        	String text = "<html>PRODUCT DETAILS:<br/> Supplier: "+product.getSupplier().getName()
        			+"<br/>Supplier Price: "+product.getSupplierPrice()+"<br/> Retail Price: "+product.getRetailPrice()
        			+"<br/><br/>CLICK TO EDIT PRODUCT DETAILS</html>";
        	
            stockList.setToolTipText(text);
        }
    }
    

	public void setStockList(ArrayList<StockItem> list) {
		listModel.clear();
		for (StockItem stockItem : list) {
			Product product = stockItem.getProduct();
			listModel.addElement(textAlignment("   " + product.getProductID(), product
					.getProductName(), "" + stockItem.getQuantity(), product.getProductCategory()));
			// stockList.setSelectedIndex(orderListIndex);
		}
	}

	/**
	 * Refresh stock list
	 */
	public void refreshStockControlTab() {
		setStockList(driver.getStockDB().getStockList());
	}

	/**
	 * List selection event
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == stockList) {
			// check if order list is empty
			if (stockList.getSelectedIndex() > -1) {
				// separate the text in the selected list item
				String[] values = stockList.getSelectedValue().split("\\t");
				driver.getGui().getTabbedPane().setSelectedComponent(driver.getGui().getProductTab());
				
				driver.getGui().getProductTab().setSelectedProduct(
						driver.getStockDB().getStockItem(Integer.parseInt(values[0].trim())));
				
				
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {}
}
