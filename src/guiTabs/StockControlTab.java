package guiTabs;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import order.StockDBControl;
import order.StockItem;
import retailSystem.Product;

public class StockControlTab extends JPanel implements ListSelectionListener, ComponentListener {

	private StockDBControl stockDBControl;
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

	private JPanel mainPanel, outerPanel;
	private int xSize;
	private int ySize;
	private int xPosition;
	private int yPosition;

	private String textAlignment(String text1, String text2, String text3, String text4) {
		String s = text1;
		s += "\t" + text2 + "\t" + text3 + "\t" + text4;
		return s;
	}

	public StockControlTab(StockDBControl stockDBControl) {
		this.stockDBControl = stockDBControl;

		xSize = 530;
		ySize = 350;
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		outerPanel = new JPanel();
		outerPanel.setLayout(null);
		outerPanel.add(mainPanel);
		outerPanel.setBorder(BorderFactory.createLineBorder(new Color(176, 168, 168), 8, true));
		this.setBorder(BorderFactory.createLineBorder(new Color(176, 168, 168), 4));
		add(outerPanel, new GridBagConstraints());
		addComponentListener(this);

		productIdLabel = new JLabel("ID");
		productNameLabel = new JLabel("Product Name");
		productCategoryLabel = new JLabel("Category");
		quantityLabel = new JLabel("Quantity");

		productIdLabel.setBounds(18, 7, 40, 23);
		productNameLabel.setBounds(77, 7, 340, 23);
		quantityLabel.setBounds(280, 7, 100, 23);
		productCategoryLabel.setBounds(390, 7, 150, 23);

		productIdLabel.setFont(new Font("Arial", Font.BOLD, 20));
		productNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
		quantityLabel.setFont(new Font("Arial", Font.BOLD, 20));
		productCategoryLabel.setFont(new Font("Arial", Font.BOLD, 20));

		stockList.addListSelectionListener(this);
		stockListSroll.setBounds(10, 30, 510, 310);
		mainPanel.add(productIdLabel);
		mainPanel.add(stockListSroll);
		mainPanel.add(productNameLabel);
		mainPanel.add(productCategoryLabel);
		mainPanel.add(quantityLabel);

		renderer.setTabs(new int[] { 70, 300, 400 });
		stockList.setCellRenderer(renderer);
		setLayout(null);
		setVisible(true);
		refreshStockControlTab();
	}

	public void setStockList(ArrayList<StockItem> list) {
		int index = 0;
		listModel.clear();
		for (StockItem stockItem : list) {
			Product product = stockItem.getProduct();
			listModel.addElement(textAlignment("   " + product.getProductID(), product
					.getProductName(), "" + stockItem.getQuantity(), product.getProductCategory()));
			// stockList.setSelectedIndex(orderListIndex);
			index++;
		}
	}

	public void refreshStockControlTab() {
		setStockList(stockDBControl.getStockList());
	}

	public void setStockDBControl(StockDBControl stockDBControl) {
		this.stockDBControl = stockDBControl;
	}

	// list selection event
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == stockList) {
			// check if order list is empty
			if (stockList.getSelectedIndex() > -1) {
				// separate the text in the selected list item
				String[] values = stockList.getSelectedValue().split("\\t");
				// JOptionPane.showMessageDialog(null, values[1]);
			}
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {

		xPosition = ((((int) mainPanel.getParent().getSize().getWidth()) - xSize) / 2);
		yPosition = ((((int) mainPanel.getParent().getSize().getHeight()) - ySize) / 2);
		mainPanel.setBounds(xPosition, yPosition, xSize, ySize);

		xPosition = ((((int) outerPanel.getParent().getSize().getWidth()) - xSize) / 2);
		yPosition = ((((int) outerPanel.getParent().getSize().getHeight()) - ySize) / 2);
		outerPanel.setBounds(xPosition - 50, yPosition - 40, xSize + 100, ySize + 80);

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
}
