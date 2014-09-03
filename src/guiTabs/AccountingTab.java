package guiTabs;

import graphs.BarChart;
import graphs.ChartDataHolder;
import graphs.MyComponent;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

import order.Account;
import order.Order;
import order.OrderDB;
import order.StockDBControl;
import order.StockItem;

public class AccountingTab extends JPanel implements ActionListener, ItemListener,
		ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected boolean emptiedList;

	private StockDBControl stockDBControl;

	private TabListCellRenderer renderer = new TabListCellRenderer(true);
	protected ArrayList<Double> itemsPrice = new ArrayList<Double>();

	protected DefaultListModel<String> listModel = new DefaultListModel<String>();
	protected JList<String> stockList = new JList<String>(listModel);
	protected JScrollPane stockListSroll = new JScrollPane(stockList);

	protected JTextArea productField;
	protected JTextArea idField;
	protected JTextArea incomeField;
	protected JTextArea expenditureField;

	protected JLabel accountingIncomeOrderProductLabel;
	protected JLabel productValueLabel;
	protected JLabel quantityLabel;
	private boolean barVisible = false, pieVisible = false;

	protected JButton pieChartButton;
	JInternalFrame pieChart = new JInternalFrame("Pie Chartum!", false, true, false);
	JInternalFrame barChart = new JInternalFrame("Bar Chart", false, true, false);
	BarChart chart = new BarChart();

	public OrderDB orderDB;
	Double suppliertotal = 0.0;
	Double total;
	Double sales;
	ArrayList<ChartDataHolder> arr = new ArrayList<ChartDataHolder>();
	private JLabel lblExpenses;

	/**
	 * Create the panel.
	 */
	public AccountingTab(StockDBControl stockDBControl, OrderDB orderDB) {

		this.stockDBControl = stockDBControl;
		this.orderDB = orderDB;

		accountingIncomeOrderProductLabel = new JLabel("Product Name");
		productValueLabel = new JLabel("Description");
		quantityLabel = new JLabel("Income");

		idField = new JTextArea();
		productField = new JTextArea();
		incomeField = new JTextArea();
		expenditureField = new JTextArea();

		pieChartButton = new JButton();

		productField.setEditable(false);
		idField.setEditable(false);
		incomeField.setEditable(false);
		expenditureField.setEditable(false);
		pieChart.setLocation(385, 177);

		pieChart.setVisible(false);
		barChart.setLocation(192, 192);
		barChart.setVisible(false);

		idField.setBounds(9, 50, 93, 300);

		productValueLabel.setBounds(101, 25, 93, 14);
		productField.setBounds(100, 50, 200, 300);

		quantityLabel.setBounds(419, 25, 93, 14);
		incomeField.setBounds(309, 50, 100, 300);
		expenditureField.setBounds(419, 50, 100, 300);

		// productField.setColumns(15);

		String[] labels = { "Mon", "Tue", "Wed", "Thu", "Fri" };

		// setProductTextField(0,stockDBControl.getStockList());
		// public void setOrderTextField(0, ord ){
		calculateBarChartValues();

		chart.addBar(Color.red, itemsPrice.get(0).intValue());
		chart.addBar(Color.green, itemsPrice.get(1).intValue());
		chart.addBar(Color.blue, itemsPrice.get(2).intValue());
		chart.addBar(Color.black, itemsPrice.get(3).intValue());

		barChart.getContentPane().add(chart);
		barChart.setSize(300, 300);

		pieChart.getContentPane().add(new MyComponent());
		pieChart.setSize(300, 300);

		add(barChart);
		add(pieChart);
		add(accountingIncomeOrderProductLabel);
		add(productValueLabel);
		add(quantityLabel);

		// add(idField);
		add(incomeField);

		add(productField);
		add(expenditureField);
		stockList.setCellRenderer(renderer);

		setLayout(null);

		// Button to toggle the bar chart on or off
		JButton btnBarChart = new JButton("Bar chart");
		btnBarChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				barVisible = !barVisible;
				barChart.setVisible(barVisible);
			}
		});
		btnBarChart.setBounds(529, 51, 89, 23);
		add(btnBarChart);

		// Button to toggle the pie chart on or off
		JButton btnPieChart = new JButton("Pie Chart");
		btnPieChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pieVisible = !pieVisible;
				pieChart.setVisible(barVisible);
			}
		});
		btnPieChart.setBounds(529, 85, 89, 23);
		add(btnPieChart);

		lblExpenses = new JLabel("Expenses");
		lblExpenses.setBounds(309, 25, 46, 14);
		add(lblExpenses);
		setVisible(true);
		// productField.append("\n " + "Sales" );
		refreshAccountingTab();
		showCustomerOrders();

		System.out.println("---------------" + itemsPrice.get(0).intValue());

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
			order.setTotalexpenditure(total + suppliertotal);
		}
		productField.append("\n Stock Sold ");

		productField.append("\n grand total of orders ");
		incomeField.append("\n\n\n" + total);
		expenditureField.append("\n" + suppliertotal);
		expenditureField.append("\n\n\n\n\n" + order.getTotalexpenditure());

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
			Calendar cal = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat("yyyy/mm/dd");
			format.format(order.getDate());
			cal = format.getCalendar();

			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			if (year == 2014 || (year >= 2013 && month >= 5)) {
				System.out.println("after 2013/6");
			}

			System.out.println("........." + itemsPrice.toString());
			// System.out.println("year!! " + year);
		}
		// System.out.println("sales..." + arr.);
		return arr;

	}

	public void setStockDBControl(StockDBControl stockDBControl) {
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
