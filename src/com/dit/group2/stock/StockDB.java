package com.dit.group2.stock;

import java.util.ArrayList;
import java.util.Random;

import com.dit.group2.person.Supplier;

/**
 * @author Roland Katona
 *
 */
public class StockDB {
	private Random random;
	private ArrayList<StockItem> stockList;

	public StockDB() {
		stockList = new ArrayList<StockItem>();
		random = new Random();
	}

	public Product getRandomProduct() {
		int randomProduct = random.nextInt((stockList.size() - 1) + 1);
		return (stockList.get(randomProduct)).getProduct();
	}

	public void changeProductDetails(int productId, String productName, String category,
			String description, double supplierPrice, double profitMargin, Supplier supplier) {
		Product product = getStockItem(productId).getProduct();
		product.setSupplier(supplier);
		product.setProductName(productName);
		product.setProductCategory(category);
		product.setProductDescription(description);
		product.setSupplierPrice(supplierPrice);
		product.setProfitMargin(profitMargin); // automatically sets retail price
	}

	/**
	 * @return stockList
	 */
	public ArrayList<StockItem> getStockList() {
		return stockList;
	}

	/**
	 * @param product
	 * @param quantity
	 */
	public void addNewProductToStockList(Product product, int quantity) {
		stockList.add(new StockItem(product, quantity));
	}

	/**
	 * @param productId
	 * @return stockItem
	 */
	public StockItem getStockItem(int productId) {
		for (StockItem stockItem : stockList) {
			if (stockItem.getProduct().getProductID() == productId) {
				return stockItem;
			}
		}
		System.out.println("Product ID not found!");
		return null;
	}

	/**
	 * @param productName
	 * @return stockItem
	 */
	public StockItem getProductByName(String productName) {
		for (StockItem stockItem : stockList) {
			if (stockItem.getProduct().getProductName().equals(productName)) {
				return stockItem;
			}
		}
		return null;
	}

	/**
	 * @param stockItem
	 */
	public void removeProductFromStockList(StockItem stockItem) {
		if (stockItem != null) {
			stockList.remove(stockItem);
			System.out.println("Product has been removed from stock");
		}
		else {
			System.out.println("Product could not be removed from stock!");
		}
	}
}
