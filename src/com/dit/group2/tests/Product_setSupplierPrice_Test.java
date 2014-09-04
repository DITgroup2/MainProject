package com.dit.group2.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.dit.group2.stock.Product;


public class Product_setSupplierPrice_Test {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetSupplierPrice() {

		Product product = new Product(null, null, null, 0, 0, null);

		double supplierPrice = 100;

		// test if constructor sets the supplierPrice correctly
		assertEquals(supplierPrice, product.getSupplierPrice(), 0);
		// test if constructor sets the retailPrice correctly
		assertEquals(supplierPrice * (1 + product.getProfitMargin()), product
				.getRetailPrice(), 0);

		supplierPrice = 150;
		product.setSupplierPrice(supplierPrice);
		// test if setSupplierPrice method sets the supplierPrice correctly
		assertEquals(supplierPrice, product.getSupplierPrice(), 0);
		// test if setSupplierPrice method sets the retailPrice correctly
		assertEquals(supplierPrice * (1 + product.getProfitMargin()), product
				.getRetailPrice(), 0);

		double profitMargin = 0.3;
		product.setProfitMargin(profitMargin);
		// test if setProfitMargin method sets the profitMargin correctly
		assertEquals(profitMargin, product.getProfitMargin(), 0);
		// test if setProfitMargin method sets the retailPrice correctly
		assertEquals(supplierPrice * (1 + product.getProfitMargin()), product
				.getRetailPrice(), 0);

		// fail("Not yet implemented"); // TODO
	}

}
