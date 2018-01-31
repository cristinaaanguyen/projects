/*
 * This User class only has the username field in this example.
 * 
 * However, in the real project, this User class can contain many more things,
 * for example, the user's shopping cart items.
 * 
 */
public class User {
	
	private final String UserEmail;
	
	public User(String email) {
		this.UserEmail = email;
	}
	
	public String getUsername() {
		return this.UserEmail;
	}

}