package order;

import java.util.ArrayList;

import person.Supplier;
import retailSystem.Product;

/**
 * @author Roland Katona
 *
 */
public class StockDBControl {
	private ArrayList<StockItem> stockList;

	/**
	 * Roland Katona
	 */
	public StockDBControl() {
		stockList = new ArrayList<StockItem>();

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
	 * @return productAndQuantityInStock
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
	 * Deleted an item from the stock list
	 * 
	 * @param stockItem
	 *            The item to remove
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
