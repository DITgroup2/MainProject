package com.dit.group2.gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.dit.group2.graphs.AllProductsChart;
import com.dit.group2.graphs.BarChartSales;
import com.dit.group2.graphs.ChartDataHolder;
import com.dit.group2.graphs.PredictionChart;
import com.dit.group2.order.Account;
import com.dit.group2.order.Order;
import com.dit.group2.retailSystem.RetailSystemDriver;
import com.dit.group2.stock.StockItem;

public class AccountingTab extends GuiLayout implements ActionListener, ItemListener,
		ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RetailSystemDriver driver;
	protected boolean emptiedList;

	Account account = new Account();

	private int expenses = 10000;
	private int rent = 10000;
	private int wages = 100000;

	private TabListCellRenderer renderer = new TabListCellRenderer(true);
	protected ArrayList<Double> itemsPrice = new ArrayList<Double>();

	protected DefaultListModel<String> listModel = new DefaultListModel<String>();
	protected JList<String> stockList = new JList<String>(listModel);
	protected JScrollPane stockListSroll = new JScrollPane(stockList);

	private JButton viewBarChartButton, predictionChart, allCharts;

	protected Vector<String> comboBoxItems = new Vector<String>();
	protected ArrayList<Integer> itemsQuantity = new ArrayList<Integer>();
	protected DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>(
			comboBoxItems);
	protected JComboBox<String> productComboBox = new JComboBox<String>(comboBoxModel);

	protected JTextArea productField;
	protected JTextArea idField;
	protected JTextArea expensesField;
	protected JTextArea incomeField;

	protected JLabel productValueLabel, expensesQuantityLabel, productComboBoxLabel,
			accountingIncomeOrderProductLabel, incomeQuantityLabel, chartsHeaderLabel;

	int suppliertotal = 0;
	Double total;
	Double sales;
	ArrayList<ChartDataHolder> arr = new ArrayList<ChartDataHolder>();

	/**
	 * Create the panel.
	 */
	public AccountingTab(RetailSystemDriver driver) {

		this.driver = driver;

		titleLabel.setText("Accounting");
		productValueLabel = new JLabel("Profit & Loss Account");
		expensesQuantityLabel = new JLabel("Expenses");
		incomeQuantityLabel = new JLabel("Income");
		chartsHeaderLabel = new JLabel("Charts");

		idField = new JTextArea();
		productField = new JTextArea();
		expensesField = new JTextArea();
		incomeField = new JTextArea();

		viewBarChartButton = new JButton("Sales Charts");
		predictionChart = new JButton("Product Monthly Sales");
		allCharts = new JButton("All Products Sales");

		productField.setEditable(false);
		idField.setEditable(false);
		expensesField.setEditable(false);
		incomeField.setEditable(false);

		idField.setBounds(9, 50, 93, 300);
		productValueLabel.setBounds(65, 10, 200, 20);
		productField.setBounds(65, 40, 200, 215);
		expensesQuantityLabel.setBounds(285, 20, 93, 23);
		incomeQuantityLabel.setBounds(405, 20, 93, 23);
		expensesField.setBounds(280, 40, 100, 215);
		incomeField.setBounds(385, 40, 100, 215);

		calculateBarChartValues();

		chartsHeaderLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		productValueLabel.setFont(new Font("Serif", Font.PLAIN, 20));

		chartsHeaderLabel.setBounds(65, 250, 150, 30);

		viewBarChartButton.setBounds(320, 285, 170, 30);
		viewBarChartButton.addActionListener(this);

		allCharts.setBounds(320, 320, 170, 30);
		allCharts.addActionListener(this);

		predictionChart.setBounds(65, 320, 225, 30);
		predictionChart.addActionListener(this);

		mainPanel.add(productValueLabel);
		mainPanel.add(expensesQuantityLabel);
		mainPanel.add(incomeQuantityLabel);
		mainPanel.add(chartsHeaderLabel);
		mainPanel.add(expensesField);

		mainPanel.add(productField);
		mainPanel.add(incomeField);
		mainPanel.add(viewBarChartButton);
		mainPanel.add(predictionChart);
		mainPanel.add(allCharts);

		initializeExpenses();
		stockList.setCellRenderer(renderer);

		setLayout(null);
		setVisible(true);
		refreshAccountingTab();
	}

	public JButton getViewBarChartButton() {
		return viewBarChartButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == viewBarChartButton) {
			BarChartSales demo = new BarChartSales("Sales History", account.getFirstquarter2014(),
					account.getSecondquarter2014(), account.getThirdquarter2014(), account
							.getFourthquarter2014(), account.getFirstquarter2013(), account
							.getSecondquarter2013(), account.getThirdquarter2013(), account
							.getFourthquarter2013(), account.getFirstquarter2012(), account
							.getSecondquarter2012(), account.getThirdquarter2012(), account
							.getFourthquarter2012());
		}

		if (e.getSource() == predictionChart) {
			PredictionChart predictionChart = new PredictionChart();
			predictionChart.buildPredictionChart(driver);
		}

		if (e.getSource() == allCharts) {
			AllProductsChart allProductsChart = new AllProductsChart();
			allProductsChart.buildPredictionChart(driver);
		}
	}

	public void refreshAccountingTab() {
		clearAccountsFields();
		fillUpProductComboBox();
		showCustomerOrders();

		setUpProductComboBox();
	}

	private void clearAccountsFields() {
		productField.setText("");
		expensesField.setText("");
		incomeField.setText("");
	}

	private void fillUpProductComboBox() {

		ArrayList<StockItem> list = driver.getStockDB().getStockList();
		comboBoxItems.clear();
		itemsQuantity.clear();
		itemsPrice.clear();
		for (StockItem stockItem : list) {
			comboBoxItems.add(stockItem.getProduct().getProductName());
			itemsPrice.add(stockItem.getProduct().getRetailPrice());
			itemsQuantity.add(stockItem.getQuantity());
		}
		productComboBox.setSelectedIndex(list.size() - 1);
	}

	private void setUpProductComboBox() {

		productComboBox.setBounds(65, 290, 225, 23);
		mainPanel.add(productComboBox);
		fillUpProductComboBox();
		productComboBox.addItemListener(this);
	}

	public void showCustomerOrders() {

		int total = 0;
		int suppliertotal = 0;
		Order order = null;
		for (int i = 0; i < driver.getOrderDB().getCustomerOrderList().size(); i++) {
			order = (driver.getOrderDB().getCustomerOrderList().get(i));

			total += (int) order.getGrandTotalOfOrder();
			suppliertotal += (int) order.getGrandTotalOfOrder() / 1.2;
		}
		int netProfit = (total - (suppliertotal + account.getExpenses() + account.getRent() + account
				.getWages()));

		productField.append("\n Sales");
		productField.append("\n Cost Of Sales ");
		productField.append("\n\n Expenses ");
		productField.append("\n Rent ");
		productField.append("\n Wages ");
		productField.append("\n\n Net Profit ");

		expensesField.setForeground(Color.RED);
		expensesField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		incomeField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		incomeField.append("\n" + total);
		expensesField.append("\n\n" + suppliertotal);
		expensesField.append("\n\n" + account.getExpenses());
		expensesField.append("\n" + account.getRent());
		expensesField.append("\n" + account.getWages());
		incomeField.append("\n\n\n\n\n\n\n" + netProfit);
	}

	public ArrayList<ChartDataHolder> calculateBarChartValues() {

		for (int i = 0; i < driver.getOrderDB().getCustomerOrderList().size(); i++) {
			Order order = (driver.getOrderDB().getCustomerOrderList().get(i));
			order.getDate();
			ChartDataHolder chartDataHolder = new ChartDataHolder(order.getGrandTotalOfOrder());
			arr.add(chartDataHolder);
			itemsPrice.add(order.getGrandTotalOfOrder());
			account.dateCheckchecker(order.getDate(), order.getGrandTotalOfOrder());
		}
		return arr;
	}

	public JComboBox getProductCombobox() {
		return productComboBox;
	}

	public void showOrderDetails(Order order) {
		incomeField.append("\n" + order.getGrandTotalOfOrder());
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

	}

	@Override
	public void itemStateChanged(ItemEvent e) {

	}

	public void initializeExpenses() {

		account.setExpenses(expenses);
		account.setRent(rent);
		account.setWages(wages);
	}
}
