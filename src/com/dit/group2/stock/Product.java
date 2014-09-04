package com.dit.group2.stock;

import com.dit.group2.person.Supplier;

/**
 * @author Roland Katona
 *
 */
public class Product {
	
	private int productID;            
	private String productName;               
	private String productDescription;
	private String productCategory;           
	private double retailPrice;
	private double supplierPrice;
	private double profitMargin;
	private Supplier supplier;
	private static int uniqueId = 1;
	
	
	public static int getUniqueId() {
		return uniqueId;
	}
	
	public Product(){
		
	}

	/**
	 * @param productName
	 * @param productDescription
	 * @param productCategory
	 * @param supplierPrice
	 * @param supplier
	 */
	public Product(String productName,String productDescription, String productCategory,double supplierPrice,double profitMargin, Supplier supplier) {
		productID = uniqueId++;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productCategory = productCategory;
		this.supplierPrice = supplierPrice;
		this.profitMargin = profitMargin;
		this.retailPrice = supplierPrice+(supplierPrice*profitMargin);
		this.supplier = supplier;
	}
	
	/**
	 * @return productID
	 */
	public int getProductID() {
		return productID;
	}

	/**
	 * @return productName
	 */
	public String getProductName() {
		return productName;
	}


	/**
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}


	/**
	 * @return productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}


	/**
	 * @param productDescription
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}


	/**
	 * @return productCategory
	 */
	public String getProductCategory() {
		return productCategory;
	}


	/**
	 * @param productCategory
	 */
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}


	/**
	 * @return retailPrice
	 */
	public double getRetailPrice() {
		return retailPrice;
	}


	/**
	 * @param retailPrice
	 */
	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}


	/**
	 * @return supplierPrice
	 */
	public double getSupplierPrice() {
		return supplierPrice;
	}


	/**
	 * @param supplierPrice
	 */
	public void setSupplierPrice(double supplierPrice) {
		this.supplierPrice = supplierPrice;
		
		// upgrade retail price according to change in supplier price
		retailPrice = supplierPrice*(1+profitMargin); 
	}


	/**
	 * @return profitMargin
	 */
	public double getProfitMargin() {
		return profitMargin;
	}


	/**
	 * @param profitMargin
	 */
	public void setProfitMargin(double profitMargin) {
		this.profitMargin = profitMargin;
		// upgrade retail price according to change in profit margin
		retailPrice = supplierPrice*(1+profitMargin); 
	}


	/**
	 * @return supplier
	 */
	public Supplier getSupplier() {
		return supplier;
	}


	/**
	 * @param supplier
	 */
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
}
