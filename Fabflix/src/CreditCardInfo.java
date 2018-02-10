

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class CreditCardInfo
 */
@WebServlet("/CreditCardInfo")
public class CreditCardInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreditCardInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String firstname = request.getParameter("firstname");
		System.out.println("printing email");
		//System.out.println(username);
		String lastname = request.getParameter("lastname");
		String expiration = request.getParameter("expiration");
		

		String loginUser = "mytestuser";
        String loginPasswd = "mypassword";

        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        response.setContentType("application/json"); // Response mime type
        
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        //System.out.println("Before try loop");
        try {
        	System.out.println("here");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            //System.out.println("here");
            // Declare our statement
            Statement statement = dbcon.createStatement();
            User user = (User) request.getSession().getAttribute("user");
            String query = "SELECT * from creditcards c where c.firstname = " + "'"+ firstname + "'" + 
            " AND c.lastname = " + "'" + lastname + "'" + "and c.expiration = '" + expiration + "'";
            // Perform the query
            ResultSet rs = statement.executeQuery(query);
            
            if (rs.next()) {
            		System.out.println("");
            		JsonArray jsonArray = new JsonArray();
            		//String insertQuery = "INSERT INTO creditcards VALUES('"+ lastname +"', '"+ firstname  +"', '"+ expiration +"')";
            		
            		//request.getSession().setAttribute("user", new User(username));
            		JsonObject jsonObject = new JsonObject();
	            jsonObject.addProperty("status", "success");
	            jsonObject.addProperty("message", "success");
	            System.out.println(jsonObject.toString());
	            
	            jsonArray.add(jsonObject);
    			
    			
    			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    			LocalDate localDate = LocalDate.now();
    			System.out.println(dtf.format(localDate)); //2016/11/16
    			String date = dtf.format(localDate);
    			
    			
    			for (Entry<String, Integer> entry : user.getCart().entrySet()){
    				for (int i = 0; i < entry.getValue(); i++) {
    					String insertQuery = "INSERT INTO sales VALUES('"+ user.getID() +"', '" + 
    				entry.getKey()  +"', '"+ date +"')";
    					java.sql.PreparedStatement ps = dbcon.prepareStatement(insertQuery,
    					        Statement.RETURN_GENERATED_KEYS);
    					 
    					ps.execute();
    					 
    					ResultSet key = ps.getGeneratedKeys();
    					int generatedKey = 0;
    					if (key.next()) {
    					    generatedKey = rs.getInt(1);
    					}
    					 
    					System.out.println("Inserted record's ID: " + generatedKey);
    					
    					JsonObject Sale = new JsonObject();
    					
    					//ResultSet insert = statement.executeQuery(insertQuery);
    					
    					Sale.addProperty("SaleID", generatedKey);
    					Sale.addProperty("CustomerID", user.getID() );
    					Sale.addProperty("MovieID", entry.getKey());
    					Sale.addProperty("date", date);
    					jsonArray.add(Sale);
    				}
    			}
    			out.write(jsonArray.toString());
            }
            
            else {
            		//login fails
	            	//request.getSession().setAttribute("user", new User(username));
	    			JsonObject responseJsonObject = new JsonObject();
	    			responseJsonObject.addProperty("status", "fail");
	    			responseJsonObject.addProperty("message", "creditcard info doesn't exist");
	    			//responseJsonObject.addProperty("message", "incorrect password");
	    			out.write(responseJsonObject.toString());
            }
            
            rs.close();
            statement.close();
            dbcon.close();
        } catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception:  " + ex.getMessage());
                ex = ex.getNextException();
            } // end while
        } // end catch SQLException

        catch (java.lang.Exception ex) {
            out.println("<HTML>" + "<HEAD><TITLE>" + "MovieDB: Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>SQL error in doPost: " + ex.getMessage() + "</P></BODY></HTML>");
            return;
        }
        out.close();
		//doPost(request,response);
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
