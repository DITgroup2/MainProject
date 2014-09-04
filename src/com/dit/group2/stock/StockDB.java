package com.dit.group2.stock;

import java.util.ArrayList;
import java.util.Random;

import com.dit.group2.person.Customer;
import com.dit.group2.person.Supplier;


/**
 * @author Roland Katona
 *
 */
public class StockDB {
	private Random random;
	private ArrayList<StockItem> stockList;

	
	public StockDB(){
		stockList = new ArrayList<StockItem>();
		random = new Random();
	}
	
	public Product getRandomProduct(){
		int randomProduct = random.nextInt((stockList.size() - 1) + 1);
		return (stockList.get(randomProduct)).getProduct();		
	}
	
	public void changeProductDetails(int productId, String productName,String category,String description,double supplierPrice,double profitMargin, Supplier supplier){
		Product product = getStockItem(productId).getProduct();
		product.setSupplier(supplier);
		product.setProductName(productName);
		product.setProductCategory(category);
		product.setProductDescription(description);
		product.setSupplierPrice(supplierPrice);
		product.setProfitMargin(profitMargin); //automatically sets retail price
	}
	
	
	
	/**
	 * @return stockList
	 */
	public ArrayList<StockItem> getStockList(){
		return stockList;
	}
	
	/**
	 * @param product
	 * @param quantity
	 */
	public void addNewProductToStockList(Product product, int quantity){
		stockList.add(new StockItem(product,quantity));
	}
	
	
	/**
	 * @param productId
	 * @return productAndQuantityInStock
	 */
	public StockItem getStockItem(int productId){
		for(StockItem stockItem : stockList){
			if(stockItem.getProduct().getProductID()==productId){
				return stockItem;
			}
		}
		System.out.println("Product ID not found!");
		return null;
	}
	
	/**
	 * @param object
	 */
	public void removeProductFromStockList(StockItem stockItem){
		if(stockItem!=null){
			stockList.remove(stockItem);
			System.out.println("Product has been removed from stock");
		}
		else{
			System.out.println("Product could not be removed from stock!");
		}	
	}
}
