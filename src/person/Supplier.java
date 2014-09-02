package person;

import java.util.ArrayList;



import retailSystem.Product;


/**
 * @author Roland Katona
 *
 */
public class Supplier extends Person {
	private  String contactName;
	private  String vatNumber;
	private ArrayList<Product> products;
	
	/**
	 * Default constructor
	 */
	public Supplier() {
		super();
		vatNumber = "";
		contactName = "";
		products = new ArrayList<Product>();
	}

	/**
	 * @param name
	 * @param email
	 * @param contactNumber
	 * @param address
	 * @param contactName
	 * @param vatNumber
	 */
	public Supplier(String name, String email, String contactNumber,
			String address,String contactName, String vatNumber) {
		super(name, email, contactNumber, address);
		this.vatNumber = vatNumber;
		this.contactName = contactName;
		products = new ArrayList<Product>();
	}
	
	

	/**
	 * @return vatNumber
	 */
	public String getVatNumber() {
		return vatNumber;
	}
	

	/**
	 * @param vatNumber
	 */
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	/**
	 * @return contactName
	 */
	public String getContactName() {
		return contactName;
	}
	

	/**
	 * @param contactName
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the products
	 */
	public ArrayList<Product> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	
	
}
