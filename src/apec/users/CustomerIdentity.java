package apec.users;

/*
 * DO NOT PLACE ANY METHODS INSIDE THIS CLASS!!!!!!
 * @author apec
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import apec.DatabaseConnection;

@SuppressWarnings("serial")
public abstract class CustomerIdentity extends UserIdentity{
	private int customerID;
	private String email;
	private String first_name;
	private String last_name;
	private String phone_number;
	
	public CustomerIdentity() {
		super();
	}
	
	protected void setCustomerIdentity(UserIdentity user) {
		setUser(user);
	}
	
	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	@Override
	/*
	 * This login method will gather all the customers information to store in the customer bean
	 * @return an integer that means absolutely nothing
	 */
	public int login() {
        Connection con = DatabaseConnection.openDBConnection();
        try {
            ResultSet rs;
            String queryString = "Select * from user_identity u,customer c where u.username=? and u.password = ? and u.username = c.username";
            PreparedStatement preparedStmt = con.prepareStatement(queryString);
            preparedStmt.clearParameters();
            preparedStmt.setString(1, this.getUsername());
            preparedStmt.setString(2, this.getPassword());
            rs = preparedStmt.executeQuery();
            rs.next();
            this.first_name = rs.getString("first_name");
            this.last_name = rs.getString("last_name");
            this.phone_number = rs.getString("phone_number");
            this.customerID = rs.getInt("customerID");
            this.email = rs.getString("email");
        } catch (Exception E) {
            E.printStackTrace();
        }
        DatabaseConnection.closeDBConnection();
		return 0;
	}
}
