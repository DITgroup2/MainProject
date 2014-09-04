package com.dit.group2.order;

import java.util.ArrayList;

public class OrderDB {

	private ArrayList<Order> customerOrderList;
	private ArrayList<Order> supplyOrderList;
	
	public ArrayList<Order> getSupplyOrderList(){
		return supplyOrderList;
	}
	
	public ArrayList<Order> getCustomerOrderList(){
		return customerOrderList;
	}
	
	public OrderDB(){
		customerOrderList = new ArrayList<Order>();
		supplyOrderList = new ArrayList<Order>();
	}

	public Order getOrderById(int id,ArrayList<Order> list){
		for(int i = 0; i < list.size(); i++){
			if( list.get(i).getId() == id ){
				return list.get(i);
			}
		}
		return null;
	}
}
