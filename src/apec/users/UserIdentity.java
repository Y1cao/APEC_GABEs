package apec.users;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import apec.DatabaseConnection;

@SuppressWarnings("serial")
public class UserIdentity implements Serializable{
	private String username;
	private String password;
	private int isAdmin;
	
	public UserIdentity() {
		
	}
	
    public int login() {
        Connection con = DatabaseConnection.openDBConnection();
        try {
            ResultSet rs;
            Statement stmt;
            String queryString = "Select * from user_identity where username='" + this.getUsername()
                                + "' and password = '" + this.getPassword() + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(queryString);
            if(rs.next()) {
            	this.isAdmin = rs.getInt("isAdmin");
            }
            return this.isAdmin;
        } catch (Exception E) {
            E.printStackTrace();
            return -1;
        }
    }
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	public boolean isAdmin() {
		return getIsAdmin() == 1;
	}
}
