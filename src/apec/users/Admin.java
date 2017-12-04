package apec.users;

import java.sql.Connection;
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
            			rs.getString("phone_number")));
            }
            rs.close();
        }catch(Exception ex) {
        	ex.printStackTrace();
        }
        DatabaseConnection.closeDBConnection();
		return customers;
	}
}
