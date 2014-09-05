package com.dit.group2.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.dit.group2.graphs.BarChartDemo1;
import com.dit.group2.graphs.ChartDataHolder;
import com.dit.group2.graphs.PredictionChart;
import com.dit.group2.order.Account;
import com.dit.group2.order.Order;
import com.dit.group2.order.OrderDB;
import com.dit.group2.order.Prediction01;
import com.dit.group2.retailSystem.RetailSystemDriver;
import com.dit.group2.stock.StockDB;
import com.dit.group2.stock.StockItem;

public class AccountingTab extends JPanel implements ActionListener, ItemListener,
		ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected boolean emptiedList;
	private RetailSystemDriver driver;
	Account account = new Account();

	private StockDB stockDBControl;

	private TabListCellRenderer renderer = new TabListCellRenderer(true);
	protected ArrayList<Double> itemsPrice = new ArrayList<Double>();

	protected DefaultListModel<String> listModel = new DefaultListModel<String>();
	protected JList<String> stockList = new JList<String>(listModel);
	protected JScrollPane stockListSroll = new JScrollPane(stockList);

	private JButton viewBarChartButton;
	private JButton predictionChart;

	protected JTextArea productField;
	protected JTextArea idField;
	protected JTextArea incomeField;
	protected JTextArea expenditureField;

	protected JLabel accountingIncomeOrderProductLabel;
	protected JLabel productValueLabel;
	protected JLabel quantityLabel;

	protected JButton pieChartButton;
	JInternalFrame frame = new JInternalFrame("Pie Chartum!", false, true, false);
	JInternalFrame frame1 = new JInternalFrame("Bar Chart", false, true, false);

	public OrderDB orderDB;
	Double suppliertotal = 0.0;
	Double total;
	Double sales;
	ArrayList<ChartDataHolder> arr = new ArrayList<ChartDataHolder>();

	/**
	 * Create the panel.
	 */
	public AccountingTab(RetailSystemDriver driver) {

		this.stockDBControl = stockDBControl;
		this.orderDB = orderDB;
		this.driver = driver;

		accountingIncomeOrderProductLabel = new JLabel("Product Name");
		productValueLabel = new JLabel("Value");
		quantityLabel = new JLabel("Quantity");

		idField = new JTextArea();
		productField = new JTextArea();
		incomeField = new JTextArea();
		expenditureField = new JTextArea();

		pieChartButton = new JButton();
		viewBarChartButton = new JButton("button!");
		predictionChart = new JButton("button222!");

		productField.setEditable(false);
		idField.setEditable(false);
		incomeField.setEditable(false);
		expenditureField.setEditable(false);

		idField.setBounds(9, 50, 93, 300);

		productValueLabel.setBounds(100, 10, 93, 14);
		productField.setBounds(100, 50, 200, 300);

		quantityLabel.setBounds(309, 10, 93, 14);
		incomeField.setBounds(309, 50, 100, 300);
		expenditureField.setBounds(419, 50, 100, 300);

		// productField.setColumns(15);

		// setProductTextField(0,stockDBControl.getStockList());
		// public void setOrderTextField(0, ord ){
		calculateBarChartValues();

		viewBarChartButton.setBounds(400, 400, 100, 20);
		viewBarChartButton.addActionListener(this);

		predictionChart.setBounds(500, 400, 100, 20);
		predictionChart.addActionListener(this);

		// add(frame1);
		// add(frame);
		add(accountingIncomeOrderProductLabel);
		add(productValueLabel);
		add(quantityLabel);

		// add(idField);
		add(incomeField);

		add(productField);
		add(expenditureField);
		add(viewBarChartButton);
		add(predictionChart);

		stockList.setCellRenderer(renderer);

		setLayout(null);
		setVisible(true);
		// productField.append("\n " + "Sales" );
		refreshAccountingTab();
		showCustomerOrders();

		System.out.println("---------------" + itemsPrice.get(0).intValue());

	}

	public JButton getViewBarChartButton() {
		return viewBarChartButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == viewBarChartButton) {

			BarChartDemo1 demo = new BarChartDemo1("Bar Chart Demo", account.getFirstquarter2014(),
					account.getSecondquarter2014(), account.getThirdquarter2014(), account
							.getFourthquarter2014(), account.getFirstquarter2013(), account
							.getSecondquarter2013(), account.getThirdquarter2013(), account
							.getFourthquarter2013(), account.getFirstquarter2012(), account
							.getSecondquarter2012(), account.getThirdquarter2012(), account
							.getFourthquarter2012());
			demo.pack();
			// RefineryUtilities.centerFrameOnScreen(demo);
			demo.setDefaultCloseOperation(0);
			demo.setVisible(true);

		}
		if (e.getSource() == predictionChart) {

			PredictionChart predictionChart = new PredictionChart();
			// predictionChart.buildPredictionChart(stockDBControl.getStockList().get(0).getProduct(),orderDB.getCustomerOrderList());

			predictionChart.buildPredictionChart(stockDBControl, orderDB);
			Prediction01 pred = new Prediction01(stockDBControl.getStockList().get(0).getProduct(),
					orderDB.getCustomerOrderList());
			// System.out.println("prediciton douubles: " + pred.getPredictionDoubles()[10]);
			for (int i = 0; i < 40; i++) {
				System.out.println("prediciton doubles: " + i + "  " + pred.getPredictionInts()[i]);

			}

			// orderDB.getCustomerOrderList().get(0);
		}

	}

	public void setTextField(ArrayList<StockItem> list) {
		System.out.println("test!!");
		listModel.clear();
		// stockList.setSelectedIndex(orderListIndex);

		Double total = 0.0;
		for (StockItem stockItem : list) {
			int quantity = stockItem.getQuantity();
			Double price = stockItem.getProduct().getRetailPrice();
			Double supplierprice = stockItem.getProduct().getSupplierPrice();
			System.out.println(price);
			total += stockItem.getQuantity() * price;
			suppliertotal += stockItem.getQuantity() * supplierprice;

			System.out.println("new total" + total);

			// expenditureField.append("\n" + quantity);
		}
		productField.append("\n Stock");

		expenditureField.append("\n" + suppliertotal);

		// productField.append("\n retail price ");
		System.out.println("total" + total);
		// incomeField.append("\n\n" + total);
		if (list.size() > 0) {
			emptiedList = false;
		}
	}

	public void refreshAccountingTab() {
		setTextField(stockDBControl.getStockList());
	}

	public void setIncomeTotal() {
		Account account;
		// double total = account;
	}

	public void showCustomerOrders() {
		Double total = 0.0;
		Double suppliertotal = 0.0;
		Order order = null;
		for (int i = 0; i < orderDB.getCustomerOrderList().size(); i++) {
			order = (orderDB.getCustomerOrderList().get(i));

			total += order.getGrandTotalOfOrder();
			suppliertotal += order.getGrandTotalOfOrder() / 1.2;
			order.setTotalExpenditure(total + suppliertotal);
		}
		productField.append("\n Stock Sold ");

		productField.append("\n grand total of orders ");
		incomeField.append("\n\n\n" + total);
		expenditureField.append("\n" + suppliertotal);
		expenditureField.append("\n\n\n\n\n" + order.getTotalExpenditure());

	}

	public ArrayList<ChartDataHolder> calculateBarChartValues() {
		for (int i = 0; i < orderDB.getCustomerOrderList().size(); i++) {
			Order order = (orderDB.getCustomerOrderList().get(i));
			order.getDate();
			ChartDataHolder chartDataHolder = new ChartDataHolder(order.getGrandTotalOfOrder());
			arr.add(chartDataHolder);

			itemsPrice.add(order.getGrandTotalOfOrder());

			System.out.println("date: " + order.getDate() + "total sales: "
					+ order.getGrandTotalOfOrder());

			account.dateCheckchecker(order.getDate(), order.getGrandTotalOfOrder());

			System.out.println("........." + itemsPrice.toString());
			// System.out.println("year!! " + year);
		}
		// System.out.println("sales..." + arr.);
		return arr;

	}

	public void setStockDBControl(StockDB stockDBControl) {
		this.stockDBControl = stockDBControl;
	}

	public void showOrderDetails(Order order) {
		expenditureField.append("\n" + order.getGrandTotalOfOrder());

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}

}
