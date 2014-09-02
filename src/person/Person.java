package person;

/**
 * @author Roland Katona
 *
 */
public class Person {
	
	private static int uniqueId = 1;
	
	private int id;
	private String name;
	private String email;
	private String contactNumber;
	private String address;
	private String imageString;
	

	/**
	 * Default constructor
	 */
	public Person(){
		name = "";
		email = "";
		address = "";
		contactNumber = "0";
		imageString = null;
	}
	
	/**
	 * @param name
	 * @param email
	 * @param contactNumber
	 * @param address
	 */
	public Person(String name, String email, String contactNumber, String address) {
		
		this.name = name;
		this.email = email;
		this.contactNumber = contactNumber;
		this.address = address;
		id = uniqueId++;
		imageString = null;
	}
	
	public static int getUniqueId(){
		return uniqueId;
	}
	
	/**
	 * 
	 */
	public void displayDetails(){		
		System.out.println("\nID : "+this.getId());
		System.out.println("Name : "+name);
		System.out.println("Email : "+email);
		System.out.println("Phone : "+contactNumber);
		System.out.println("Address : "+address);		
	}

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	public String getImageString() {
		return imageString;
	}

	public void setImageString(String imageString) {
		this.imageString = imageString;
	}

}
