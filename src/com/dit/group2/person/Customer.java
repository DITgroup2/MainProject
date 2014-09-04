package com.dit.group2.person;

import java.util.ArrayList;

import com.dit.group2.order.Order;



/**
 * @author Roland Katona
 *
 */
public class Customer extends Person {

	private ArrayList<Order> orders;
	
	/**
	 * Default constructor
	 */
	public Customer() {
		super();
		orders = new ArrayList<Order>();
	}

	/**
	 * @param name
	 * @param email
	 * @param contactNumber
	 * @param address
	 */
	public Customer(String name, String email, String contactNumber, String address) {
		super(name, email, contactNumber, address);
		orders = new ArrayList<Order>();
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	
	
}
