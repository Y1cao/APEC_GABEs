package apec.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import apec.DatabaseConnection;

/**
 *	All the database calls for the Admins
 * @author APEC
 */

@SuppressWarnings("serial")
public class Admin extends UserIdentity{
	
	public void setAdmin(UserIdentity user) {
		this.setUser(user);
	}
	
	/*
	 * View Users Functionality
	 * Place the customers in an array list so we can close the connection here
	 * If the user doesnt have the proper access rights return null
	 */
	public ArrayList<CustomerIdentity> getCustomers() {
		if(isAdmin != 1)
			return null;
        Connection con = DatabaseConnection.openDBConnection();
        ArrayList<CustomerIdentity> customers = new ArrayList<CustomerIdentity>();
        try {;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user_identity u,customer c WHERE u.username=c.username AND isAdmin=0");
            while(rs.next()) {
            	customers.add(new CustomerIdentity(
            			rs.getString("username"),
            			rs.getString("password"),
            			rs.getInt("customerID"),
            			rs.getString("first_name"),
            			rs.getString("last_name"),
            			rs.getString("phone_number"),
            			rs.getString("email")));
            }
            rs.close();
        }catch(Exception ex) {
        	ex.printStackTrace();
        }
        DatabaseConnection.closeDBConnection();
		return customers;
	}
	
	/*
	 * Add Customer Functionality
	 */
	public boolean addCustomer(CustomerIdentity customer) {
		System.out.println("CID = " + customer.getCustomerID());
		System.out.println("username = " + customer.getUsername());
		Connection con = DatabaseConnection.openDBConnection();
		PreparedStatement stmt;
		boolean updated = true;
		try {
			
			String user_insert = "INSERT INTO user_identity (username, password, isAdmin) VALUES (?, ?, ?)";
			stmt = con.prepareStatement(user_insert);
			stmt.setString(1, customer.getUsername());
			stmt.setString(2, customer.getPassword());
			stmt.setInt(3, customer.getIsAdmin());
			if(stmt.executeUpdate() == 0) {
				updated = false;
			}
			
			String customer_insert = "INSERT INTO customer (username, customerID, email, first_name, last_name, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(customer_insert);
			stmt.setString(1, customer.getUsername());
			stmt.setInt(2, customer.getCustomerID());
			stmt.setString(3, customer.getEmail());
			stmt.setString(4, customer.getFirst_name());
			stmt.setString(5, customer.getLast_name());
			stmt.setString(6, customer.getPhone_number());
			if(updated && stmt.executeUpdate() == 0) {
				updated = false;
				Statement ende = con.createStatement();
				ende.executeUpdate("DELETE FROM user_identity WHERE username='"+customer.getUsername()+"'");
				ende.close();
			}
			stmt.close();
		}catch(Exception ex) {
			ex.printStackTrace();
			updated = false;
		}
		DatabaseConnection.closeDBConnection();
		return updated;
	}
}
