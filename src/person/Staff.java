package person;


/**
 * @author Roland Katona
 *
 */
public class Staff extends Person{
	
	private String password;
	private int accessLevel;
	private boolean login;
	
	/**
	 * Default constructor
	 */
	public Staff() {
		super();
		password = "";
		accessLevel=0;
	}
		
	/**
	 * @param name
	 * @param email
	 * @param contactNumber
	 * @param address
	 * @param password
	 * @param accesslevel
	 */
	public Staff(String name, String email, String contactNumber, String address, String password, int accesslevel) {
		super(name, email, contactNumber, address);
		this.password = password;
		this.accessLevel = accesslevel;
	}
	
	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	/**
	 * @return accessLevel
	 */
	public int getAccessLevel() {
		return accessLevel;
	}
	
	
	/**
	 * @param accessLevel
	 */
	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}
	
	/**
	 * Validates a char array to check if it matches the password. 
	 * If it does, it returns the boolean 'login' as true. 
	 * 'login' is initially set to false in the constructor.
	 * @return login 
	 * @param charArr The password required for the staff 
	 * member to log in 
	 */
	public boolean passwordValidation(char [] charArr){
		login = false;
		String testStr = String.valueOf(charArr);
		if(this.password.equals(testStr))
			login = true;
		return login;
	}
	
}
