

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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

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
		String cardNum = request.getParameter("card_number");
		

	//	String loginUser = "mytestuser";
     //   String loginPasswd = "mypassword";

      //  String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        response.setContentType("application/json"); // Response mime type
        
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        //System.out.println("Before try loop");
        try {
        	System.out.println("here");
        //    Class.forName("com.mysql.jdbc.Driver").newInstance();
          //  Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            //System.out.println("here");
	           Context initCtx = new InitialContext();
	            if (initCtx == null)
	                out.println("initCtx is NULL");

	            Context envCtx = (Context) initCtx.lookup("java:comp/env");
	            if (envCtx == null)
	                out.println("envCtx is NULL");

	            // Look up our data source
	            DataSource ds = (DataSource) envCtx.lookup("jdbc/MovieDB");

	            // the following commented lines are direct connections without pooling
	            //Class.forName("org.gjt.mm.mysql.Driver");
	            //Class.forName("com.mysql.jdbc.Driver").newInstance();
	            //Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

	            if (ds == null)
	                out.println("ds is null.");

	            Connection dbcon = ds.getConnection();
	            if (dbcon == null)
	                out.println("dbcon is null.");
            // Declare our statement
        	
            Statement statement = dbcon.createStatement();
            User user = (User) request.getSession().getAttribute("user");
            String query = "SELECT * from creditcards c where c.firstname = " + "'"+ firstname + "'" + 
            " AND c.lastname = " + "'" + lastname + "'" + "and c.expiration = '" + expiration + "'" + "And c.id = '" + cardNum + "'";
            // Perform the query
    		JsonArray jsonArray = new JsonArray();

            ResultSet rs = statement.executeQuery(query);
            
            if (rs.next()) {
            		System.out.println("");
            		//String insertQuery = "INSERT INTO creditcards VALUES('"+ lastname +"', '"+ firstname  +"', '"+ expiration +"')";
            		
            		//request.getSession().setAttribute("user", new User(username));
            		JsonObject jsonObject = new JsonObject();
	            jsonObject.addProperty("status", "success");
	            jsonObject.addProperty("message", "success");
	            System.out.println(jsonObject.toString());
	            
	            jsonArray.add(jsonObject);
    				out.write(jsonArray.toString());

            }
    			            
            else {
            		//login fails
	            	//request.getSession().setAttribute("user", new User(username));
	    			JsonObject responseJsonObject = new JsonObject();
	    			responseJsonObject.addProperty("status", "fail");
	    			responseJsonObject.addProperty("message", "creditcard info doesn't exist");
	    			jsonArray.add(responseJsonObject);
	    			//responseJsonObject.addProperty("message", "incorrect password");
	    			out.write(jsonArray.toString());
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
