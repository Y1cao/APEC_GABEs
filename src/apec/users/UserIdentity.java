package apec.users;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import apec.DatabaseConnection;

/*
 * DO NOT PLACE ANY METHODS INSIDE THIS CLASS!!!!!!
 * @author apec
 */
@SuppressWarnings("serial")
public class UserIdentity implements Serializable{
	private String username;
	private String password;
	private int isAdmin;
	
	public UserIdentity() {
		
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
	
	protected void setUser(UserIdentity user) {
		this.username = user.username;
		this.password = user.password;
		this.isAdmin = user.isAdmin;
	}
	
    public int login() {
        Connection con = DatabaseConnection.openDBConnection();
        try {
            ResultSet rs;
            String queryString = "Select * from user_identity u where u.username=? and u.password = ?";
            PreparedStatement preparedStmt = con.prepareStatement(queryString);
            preparedStmt.clearParameters();
            preparedStmt.setString(1, this.getUsername());
            preparedStmt.setString(2, this.getPassword());
            rs = preparedStmt.executeQuery();
            if(rs.next()) {
            	this.isAdmin = rs.getInt("isAdmin");
            }
        } catch (Exception E) {
            E.printStackTrace();
            this.isAdmin = -1;
        }
        DatabaseConnection.closeDBConnection();
        return this.isAdmin;
    }
}
