import java.util.HashMap;
import java.util.Map;

/*
 * This User class only has the username field in this example.
 * 
 * However, in the real project, this User class can contain many more things,
 * for example, the user's shopping cart items.
 * 
 */
public class User {
	
	private final String UserEmail;
	private final String Id;
	
	private Map<String, Integer> Cart;
	
	public User(String email, String id) {
		this.UserEmail = email;
		this.Cart = new HashMap<String, Integer>();
		this.Id = id;
	}
	
	public String getUsername() {
		return this.UserEmail;
	}
	
	public String getID() {
		return this.Id;
	}
	
	public Map<String, Integer> getCart(){
		return this.Cart;
	}
	
	public int addToCart(String MovieID) {
		if (Cart.containsKey(MovieID))
			Cart.put(MovieID, Cart.get(MovieID)+ 1);
		else
			Cart.put(MovieID, 1);
		return Cart.get(MovieID); 
	}
	
	public void removeFromCart(String MovieID) {
		if(Cart.containsKey(MovieID) && Cart.get(MovieID) > 0 )
			Cart.put(MovieID, Cart.get(MovieID) - 1);
		if(Cart.get(MovieID) <= 0)
			Cart.remove(MovieID);
	}
	
	public int changeQuantity(String MovieId, int quantity) {
		Cart.put(MovieId, quantity);
		if (Cart.get(MovieId) <= 0)
			Cart.remove(MovieId);
		return quantity;
	}

}