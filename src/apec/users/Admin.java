package apec.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        try {
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
			
			String customer_insert = "INSERT INTO customer (username, customerID, email, first_name, last_name, phone_number) VALUES (?, customer_id_seq.nextval, ?, ?, ?, ?)";
			stmt = con.prepareStatement(customer_insert);
			stmt.setString(1, customer.getUsername());
			stmt.setString(2, customer.getEmail());
			stmt.setString(3, customer.getFirst_name());
			stmt.setString(4, customer.getLast_name());
			stmt.setString(5, customer.getPhone_number());
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
	
	/*
	 * This method queries the database and gets all the item information and its selling price and commission earned
	 * @return returns the resultset containing the data
	 */
	public ResultSet getSalesSummaryReport() {
		Connection con = DatabaseConnection.openDBConnection();
		PreparedStatement stmt;
		ResultSet output = null;
		try {
			String query = "Select i.category, i.ItemID, i.item_name, Current_Winning_Bid_Func(i.ItemID) as Final_selling_price, Current_Winning_Bid_Func(I.ItemID)*.05 as commission\n" + 
					"from Item i\n" + 
					"where i.ENDDATE < SYSDATE\n" + 
					"Group by i.category, i.ItemID, i.item_name, Current_Winning_Bid_Func(i.ItemID), Current_Winning_Bid_Func(I.ItemID)*.05\n" + 
					"order by category, i.itemid";
			stmt = con.prepareStatement(query);
			output = stmt.executeQuery();
			
		}catch(SQLException e) {
			e.printStackTrace();
			return output;
		}
		
		return output;
	}
	
	/*
	 * This method queries the database and adds up all the final selling prices and commission earned for each item in each category
	 * @return returns the result set containing the data
	 */
	public ResultSet getSalesSummaryTotals() {
		Connection con = DatabaseConnection.openDBConnection();
		PreparedStatement stmt;
		ResultSet output = null;
		try {
			String query = "Select category, Sum(Current_winning_bid_func(i.itemID)) as final_selling_total, Sum(Current_winning_bid_func(i.itemID)*.05) as Commision_total\n" + 
					"from item i\n" + 
					"group by category\n" + 
					"order by category;";
			stmt = con.prepareStatement(query);
			output = stmt.executeQuery();
			
		}catch(SQLException e) {
			e.printStackTrace();
			return output;
		}
		
		return output;
	}
}

/*
 * customer id can use customer_id_seq.nextval
 * 
 * isAdmin value can just be set to 0, no way to create an admin unless manually done through db.
 * 
 * GetCustomers: is it necessary to have the user_identity table? customer table only shows customers. having the isAdmin=0 seems redundant.
 * 
 * getCustomers: Query should be a prepared one as well, possible that customer data could have sql injections
 */
