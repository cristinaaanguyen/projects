

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
 * Servlet implementation class Confirmation
 */
@WebServlet("/Confirmation")
public class Confirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Confirmation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		// TODO Auto-generated method stub
		String firstname = request.getParameter("firstname");
		System.out.println("printing email");
		//System.out.println(username);
		String lastname = request.getParameter("lastname");
		String expiration = request.getParameter("expiration");
		

		//String loginUser = "mytestuser";
        //String loginPasswd = "mypassword";

        //String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        response.setContentType("application/json"); // Response mime type
        
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        //System.out.println("Before try loop");
        try {
        	System.out.println("here");
          //  Class.forName("com.mysql.jdbc.Driver").newInstance();
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
           
    		JsonArray jsonArray = new JsonArray();

            
    			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    			LocalDate localDate = LocalDate.now();
    			System.out.println(dtf.format(localDate)); //2016/11/16
    			String date = dtf.format(localDate);
    			
    			
    			for (Entry<String, Integer> entry : user.getCart().entrySet()){
    				System.out.println("Inside for loop for iterating through cart items");
    				for (int i = 0; i < entry.getValue(); i++) {
        				System.out.println("Inside for loop for iterating through quantity per item");

    					String insertQuery = "INSERT INTO sales (customerId, movieId, saleDate) VALUES("+ Integer.parseInt(user.getID()) +", '" + 
    				entry.getKey()  +"', '"+ date +"')"; 
    					int j = 0; int k = 0;
    					
        				System.out.println("printing insert query");
        				System.out.println(insertQuery);
    					Statement ps = dbcon.createStatement();
    					j = ps.executeUpdate(insertQuery, Statement.RETURN_GENERATED_KEYS);
    					
        				System.out.println("after excecuting insert query");

    					ResultSet key = ps.getGeneratedKeys();
    					int generatedKey =  0;
    					if (key.next()) {
    						generatedKey = key.getInt(1);
        					System.out.println(generatedKey);
        					//System.out.println(rs.getLong(1));

    					    //generatedKey = rs.getLong(1);
    					    
    					    
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
