package com.dit.group2.order;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.dit.group2.person.Person;
import com.dit.group2.person.Staff;
import com.dit.group2.stock.StockItem;



/**
 * @author Roland Katona
 */
public class Order {
	// 2014.08.26 R&S
		private static int uniqueId = 1;
		private int id;
		private Person person;
		private Staff currentlyLoggedInStaff;   //identify which staff was logged in and made the order
		private ArrayList<StockItem> orderItemsList;
		private double grandTotal;
		private Date date;
		private boolean status;
		long rangebegin = Timestamp.valueOf("2013-02-08 00:00:00").getTime();
		long rangeend = Timestamp.valueOf("2014-08-08 00:58:00").getTime();
		long diff = rangeend - rangebegin + 1;
		Timestamp rand;
		

		public static int getUniqueId(){
			return uniqueId;
		}
		

		public Order(Staff currentlyLoggedInStaff, Person person, ArrayList<StockItem> orderItemsList, double grandTotal) {
			this.grandTotal = grandTotal;
			this.id = uniqueId++;
			this.person = person;
			this.currentlyLoggedInStaff = currentlyLoggedInStaff;
			this.orderItemsList = orderItemsList;
			this.status = true;
			rand = new Timestamp(rangebegin + (long)(Math.random() * diff));
			date = rand;
		}
		
		public Order(Staff currentlyLoggedInStaff, Person person, ArrayList<StockItem> orderItemsList, double grandTotal, Date date) {
			this.grandTotal = grandTotal;
			this.id = uniqueId++;
			this.person = person;
			this.currentlyLoggedInStaff = currentlyLoggedInStaff;
			this.orderItemsList = orderItemsList;
			this.status = false;
			rand = new Timestamp(date.getTime());
			this.date = rand;
		}
		
		public Date getDate(){
			return date;
		}
		
		public Staff getCurrentlyLoggedInStaff() {
			return currentlyLoggedInStaff;
		}
		
		
		public double getGrandTotalOfOrder(){
			return grandTotal;
		}
		
		public int getId() {
			return id;
		}

		public Person getPerson() {
			return person;
		}

		public void setPerson(Person person) {
			this.person = person;
		}

		public ArrayList<StockItem> getOrderEntryList() {
			return orderItemsList;
		}

		public void setOrderEntryList(ArrayList<StockItem> orderEntryList) {
			this.orderItemsList = orderEntryList;
		}

		public boolean isStatus() {
			return status;
		}

		public void setStatus(boolean status) {
			this.status = status;
		}	
}
