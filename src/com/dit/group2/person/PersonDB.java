package com.dit.group2.person;

import java.util.ArrayList;
import java.util.Random;


public class PersonDB {

	private ArrayList<Person> customerList;
	private ArrayList<Person> staffList;
	private ArrayList<Person> supplierList;
	private Person person;
	private Random random;
	
	public PersonDB(){
		customerList = new ArrayList<Person>();
		staffList = new ArrayList<Person>();
		supplierList = new ArrayList<Person>();
		random = new Random();	
	}
	
	public ArrayList<Person> getCustomerList() {
		return customerList;
	}

	public ArrayList<Person> getStaffList() {
		return staffList;
	}

	public ArrayList<Person> getSupplierList() {
		return supplierList;
	}
	
	public Supplier getSupplierById(int id){		
		for(Person person : supplierList){
			if(person.getId()==id){
				return (Supplier)person;
			}
		}
		return null;
	}
	
	public Customer getCustomererById(int id){		
		for(Person person : customerList){
			if(person.getId()==id){
				return (Customer)person;
			}
		}
		return null;
	}
	
	public Supplier getSupplierByName(String supplierName){
		for(Person person : supplierList){
			if(person.getName().equalsIgnoreCase(supplierName)){
				return (Supplier)person;
			}
		}
		return null;
	}

	public Supplier getRandomSupplier(){
		int randomSupplier = random.nextInt((supplierList.size() - 1) + 1);
		return (Supplier)supplierList.get(randomSupplier);		
	}
	
	public Staff getRandomStaff(){
		int randomStaff = random.nextInt((staffList.size() - 1) + 1);
		return (Staff)staffList.get(randomStaff);		
	}
	
	public Customer getRandomCustomer(){
		int randomCustomer = random.nextInt((customerList.size() - 1) + 1);
		return (Customer)customerList.get(randomCustomer);		
	}
	
	
	public void automaticallyCreateCustomer(){
		person = new Customer("Roland","roland@msn.com","0871252345","12 Main Street");
		customerList.add(person);
		person = new Customer("Tom","tom@msn.com","0871345455","33 Main Street");
		customerList.add(person);
		person = new Customer("Bob","bob@msn.com","0857134552","1 Main Street");
		customerList.add(person);
	}
	
	public void automaticallyCreateStaff(){
		person = new Staff("Jim","jim@msn.com","0870227421","123 Main Street","pass",1);
		staffList.add(person);
		person = new Staff("0","john@msn.com","0867134552","38 Main Street","0",2);
		staffList.add(person);
	}
	
	public void automaticallyCreateSupplier(){
		person = new Supplier("Poor Buy","pb@msn.com","0850227421","111 Main Street","Pat","x0123");
		supplierList.add(person);
		person = new Supplier("Good Buy","gb@msn.com","0875123451","200 Main Street","Kate","x1234");
		supplierList.add(person);
		person = new Supplier("Better Buy","bb@msn.com","0877134552","400 Main Street","Sam","x2345");
		supplierList.add(person);
		person = new Supplier("Best Buy","bbb@msn.com","0877134552","600 Main Street","Fred","x3456");
		supplierList.add(person);
	}
	
	
	public void createNewPerson(Person person, String name, String email, String contactNumber, String address, int accessLevel, String password, String vatNumber, String contactName ){
		if (person instanceof Customer){
			customerList.add( new Customer(name, email, contactNumber, address));
		}
		else if(person instanceof Staff){
			staffList.add(new Staff(name, email, contactNumber, address, password, accessLevel));
		}
		else{
			supplierList.add(new Supplier(name, email, contactNumber, address, contactName, vatNumber));
		}
	}

	public void changePersonDetails(Person person, String name, String email, String contactNumber, String address, int accessLevel, String password, String vatNumber, String contactName ){
		this.person = person;
		this.person.setName(name);
		this.person.setEmail(email);
		this.person.setAddress(address);
		this.person.setContactNumber(contactNumber);
		if((accessLevel==0 && password==null)&&(vatNumber!=null && contactName!=null)){
			((Supplier)this.person).setVatNumber(vatNumber);
			((Supplier)this.person).setContactName(contactName);
		}
		else if((vatNumber==null && contactName==null)&&(accessLevel!=0 && password!=null)){
			((Staff)this.person).setPassword(password);
			((Staff)this.person).setAccessLevel(accessLevel);
		}	
	}
	
	public void deletePersonFromList(Person person){
		if(person instanceof Customer){
			customerList.remove(person);
		}
		else if(person instanceof Staff){
			staffList.remove(person);
		}
		else if(person instanceof Supplier){
			supplierList.remove(person);
		}
	}
}