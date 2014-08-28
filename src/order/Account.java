package order;

import java.util.ArrayList;

/**
 * Class for calculating transactions in and out of the business.
 * 
 * @author Conor
 */
public class Account {
	private ArrayList<SupplyOrder> supplyOrderList;
	private ArrayList<PurchaseOrder> purchaseOrderList;
	public double totalSales;
	public double totalPurchases;

	/**
	 * Accounting constructor
	 */
	public Account(ArrayList<PurchaseOrder> purchaseOrderList,
			ArrayList<SupplyOrder> supplyOrderList) {
		this.totalPurchases = 0;
		this.totalSales = 0;
		this.purchaseOrderList = purchaseOrderList;
		this.supplyOrderList = supplyOrderList;
		getTotalPurchases();
	}

	/**
	 * @param e
	 */
	public void add(SupplyOrder e) {
		supplyOrderList.add(e);
	}

	/**
	 * @param e
	 */
	public void add(PurchaseOrder e) {
		purchaseOrderList.add(e);
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
		for (SupplyOrder i : supplyOrderList) {
			totalSales += i.getTotalPrice();
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
		for (PurchaseOrder i : purchaseOrderList) {
			totalPurchases += i.getTotalPrice();
		}
	}

	public ArrayList<PurchaseOrder> autoCreatePurchaseOrders() {
		ArrayList<PurchaseOrder> purchases = new ArrayList<PurchaseOrder>();
		return purchases;
	}

	public ArrayList<SupplyOrder> autoCreateSupplyOrders() {
		ArrayList<SupplyOrder> supplies = new ArrayList<SupplyOrder>();

		return supplies;
	}

}
