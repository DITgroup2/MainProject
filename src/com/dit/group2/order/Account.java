package com.dit.group2.order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Class for calculating transactions in and out of the business.
 * 
 * @author Conor
 */
public class Account {
	private ArrayList<Order> supplyOrderList;
	private ArrayList<Order> purchaseOrderList;
	public double totalSales;
	public double totalPurchases;
	private double firstquarter2014;
	private double secondquarter2014;
	private double thirdquarter2014;
	private double fourthquarter2014;

	private double firstquarter2013;
	private double secondquarter2013;
	private double thirdquarter2013;
	private double fourthquarter2013;

	private double firstquarter2012;
	private double secondquarter2012;
	private double thirdquarter2012;
	private double fourthquarter2012;

	/**
	 * Accounting constructor
	 */
	public Account(ArrayList<Order> purchaseOrderList, ArrayList<Order> supplyOrderList) {
		this.totalPurchases = 0;
		this.totalSales = 0;
		this.purchaseOrderList = purchaseOrderList;
		this.supplyOrderList = supplyOrderList;
	}

	/**
	 * @param e
	 */
	// public void add(SupplyOrder e) {
	// supplyOrderList.add(e);
	// }

	/**
	 * @param e
	 */
	// public void add(PurchaseOrder e) {
	// purchaseOrderList.add(e);
	// }

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the totalSales
	 */
	public double getTotalSales() {
		return totalSales;
	}

	/**
	 * Calculate the amount of the total sales
	 */
	public void setTotalSales() {
		for (Order order : supplyOrderList) {
			totalSales += order.getGrandTotalOfOrder();
		}
	}

	/**
	 * @return the totalPurchases
	 */
	public double getTotalPurchases() {
		return totalPurchases;
	}

	/**
	 * Calculate the amount of the total purchases
	 */
	public void setTotalPurchases() {
		for (Order order : purchaseOrderList) {
			totalPurchases += order.getGrandTotalOfOrder();
		}
	}

	public ArrayList<Order> autoCreatePurchaseOrders() {
		ArrayList<Order> purchases = new ArrayList<Order>();
		return purchases;
	}

	public ArrayList<Order> autoCreateSupplyOrders() {
		ArrayList<Order> supplies = new ArrayList<Order>();

		return supplies;
	}

	public void dateCheckchecker(Date date, Double monthlytotal) {

		Calendar cal = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yyyy/mm/dd");
		format.format(date);
		cal = format.getCalendar();

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		double total = 0.0;
		if (year == 2014 && ((month >= 0) && (month < 3))) {
			System.out.println("1st quarter " + year);
			total += monthlytotal;
			setFirstquarter2014(total);
		}
		if (year == 2014 && ((month >= 3) && (month <= 5))) {
			System.out.println("2nd Quarter " + year);
			total += monthlytotal;
			setSecondquarter2014(total);
		}
		if (year == 2014 && ((month >= 6) && (month <= 8))) {
			System.out.println("3rd Quarter " + year);
			total += monthlytotal;
			setThirdquarter2014(total);
		}
		if (year == 2014 && ((month >= 9) && (month <= 11))) {
			System.out.println("4th Quarter " + year);
			total += monthlytotal;
			setFourthquarter2014(total);
		}
		if (year == 2013 && ((month >= 0) && (month < 3))) {
			System.out.println("1st quarter " + year);
			total += monthlytotal;
			setFirstquarter2013(total);
		}
		if (year == 2013 && ((month >= 3) && (month <= 5))) {
			System.out.println("2nd Quarter " + year);
			total += monthlytotal;
			setSecondquarter2013(total);
		}
		if (year == 2013 && ((month >= 6) && (month <= 8))) {
			System.out.println("3rd Quarter " + year);
			total += monthlytotal;
			setThirdquarter2013(total);
		}
		if (year == 2013 && ((month >= 9) && (month <= 11))) {
			System.out.println("4th Quarter " + year);
			total += monthlytotal;
			setFourthquarter2013(total);
		}
		if (year == 2012 && ((month >= 0) && (month < 3))) {
			System.out.println("1st quarter " + year);
			total += monthlytotal;
			setFirstquarter2012(total);
		}
		if (year == 2012 && ((month >= 3) && (month <= 5))) {
			System.out.println("2nd Quarter " + year);
			total += monthlytotal;
			setSecondquarter2012(total);
		}
		if (year == 2012 && ((month >= 6) && (month <= 8))) {
			System.out.println("3rd Quarter " + year);
			total += monthlytotal;
			setThirdquarter2012(total);
		}
		if (year == 2012 && ((month >= 9) && (month <= 11))) {
			System.out.println("4th Quarter of " + year);
			total += monthlytotal;
			setFourthquarter2012(total);
		}
	}

	public ArrayList<Order> getSupplyOrderList() {
		return supplyOrderList;
	}

	public void setSupplyOrderList(ArrayList<Order> supplyOrderList) {
		this.supplyOrderList = supplyOrderList;
	}

	public ArrayList<Order> getPurchaseOrderList() {
		return purchaseOrderList;
	}

	public void setPurchaseOrderList(ArrayList<Order> purchaseOrderList) {
		this.purchaseOrderList = purchaseOrderList;
	}

	public double getFirstquarter2014() {
		return firstquarter2014;
	}

	public void setFirstquarter2014(double firstquarter2014) {
		this.firstquarter2014 = firstquarter2014;
	}

	public double getSecondquarter2014() {
		return secondquarter2014;
	}

	public void setSecondquarter2014(double secondquarter2014) {
		this.secondquarter2014 = secondquarter2014;
	}

	public double getThirdquarter2014() {
		return thirdquarter2014;
	}

	public void setThirdquarter2014(double thirdquarter2014) {
		this.thirdquarter2014 = thirdquarter2014;
	}

	public double getFourthquarter2014() {
		return fourthquarter2014;
	}

	public void setFourthquarter2014(double fourthquarter2014) {
		this.fourthquarter2014 = fourthquarter2014;
	}

	public double getFirstquarter2013() {
		return firstquarter2013;
	}

	public void setFirstquarter2013(double firstquarter2013) {
		this.firstquarter2013 = firstquarter2013;
	}

	public double getSecondquarter2013() {
		return secondquarter2013;
	}

	public void setSecondquarter2013(double secondquarter2013) {
		this.secondquarter2013 = secondquarter2013;
	}

	public double getThirdquarter2013() {
		return thirdquarter2013;
	}

	public void setThirdquarter2013(double thirdquarter2013) {
		this.thirdquarter2013 = thirdquarter2013;
	}

	public double getFourthquarter2013() {
		return fourthquarter2013;
	}

	public void setFourthquarter2013(double fourthquarter2013) {
		this.fourthquarter2013 = fourthquarter2013;
	}

	public double getFirstquarter2012() {
		return firstquarter2012;
	}

	public void setFirstquarter2012(double firstquarter2012) {
		this.firstquarter2012 = firstquarter2012;
	}

	public double getSecondquarter2012() {
		return secondquarter2012;
	}

	public void setSecondquarter2012(double secondquarter2012) {
		this.secondquarter2012 = secondquarter2012;
	}

	public double getThirdquarter2012() {
		return thirdquarter2012;
	}

	public void setThirdquarter2012(double thirdquarter2012) {
		this.thirdquarter2012 = thirdquarter2012;
	}

	public double getFourthquarter2012() {
		return fourthquarter2012;
	}

	public void setFourthquarter2012(double fourthquarter2012) {
		this.fourthquarter2012 = fourthquarter2012;
	}

	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}

	public void setTotalPurchases(double totalPurchases) {
		this.totalPurchases = totalPurchases;
	}

}
