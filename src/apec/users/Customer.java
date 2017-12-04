package apec.users;

/*
 * used to handle all the method calls for the customer
 * @author apec
 */
@SuppressWarnings("serial")
public class Customer extends CustomerIdentity{
	public Customer() {
		super();
	}
	
	public void setCustomer(UserIdentity user) {
		setCustomerIdentity(user);
	}
	
}
