package apec.users;

/**
 *	All the database calls for the Admins
 * @author APEC
 */

@SuppressWarnings("serial")
public class Admin extends UserIdentity{
	
	public void setAdmin(UserIdentity user) {
		this.setUser(user);
	}
}
