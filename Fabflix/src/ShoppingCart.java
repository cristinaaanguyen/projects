

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class ShoppingCart
 */

@WebServlet("/ShoppingCart")
public class ShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		User user = (User) request.getSession().getAttribute("user");
		String loginUser = "mytestuser";
        String loginPasswd = "mypassword";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        response.setContentType("application/json");
        
        PrintWriter out = response.getWriter();
        
        user.addToCart("tt0254440");
        user.addToCart("tt0256400");
        
        try {
        	System.out.println("here");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            //System.out.println("here");
            // Declare our statement
            JsonArray jsonArray = new JsonArray();
            for (Entry<String, Integer> entry : user.getCart().entrySet()){
             
            	JsonObject jsonObject = new JsonObject();
            	Statement statement = dbcon.createStatement();
            	String query = "SELECT title from movies where movies.id = " + "'"+ entry.getKey() + "'";
            
            	ResultSet rs = statement.executeQuery(query);
            

            	rs.next();
            
            	String movieTitle = rs.getString("title");
            	
            	jsonObject.addProperty("title", movieTitle);
            	jsonObject.addProperty("quantity", entry.getValue());
        		jsonArray.add(jsonObject);
        		rs.close();
        		statement.close();

            	}
            System.out.println(jsonArray.toString());
            out.write(jsonArray.toString());
	        dbcon.close();
            
        	} catch (SQLException ex) {
        		while (ex != null) {
        			System.out.println("SQL Exception:  " + ex.getMessage());
        			ex = ex.getNextException();
        		} // end while
        	}// end catch SQLException

        	catch (java.lang.Exception ex) {
        		out.println("<HTML>" + "<HEAD><TITLE>" + "MovieDB: Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>SQL error in doPost: " + ex.getMessage() + "</P></BODY></HTML>");
        		return;
        		}
        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

} 
