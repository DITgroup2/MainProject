package com.dit.group2.order;

import java.util.ArrayList;

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

	/**
	 * Accounting constructor
	 */
	public Account(ArrayList<Order> purchaseOrderList,
			ArrayList<Order> supplyOrderList) {
		this.totalPurchases = 0;
		this.totalSales = 0;
		this.purchaseOrderList = purchaseOrderList;
		this.supplyOrderList = supplyOrderList;
	}

	/**
	 * @param e
	 */
	//public void add(SupplyOrder e) {
	//	supplyOrderList.add(e);
	//}
	

	
	/**
	 * @param e
	 */
	//public void add(PurchaseOrder e) {
	//	purchaseOrderList.add(e);
	//}

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

}
