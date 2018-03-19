import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
 * Servlet implementation class Login
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		System.out.println("printing email");
		System.out.println(username);
		String password = request.getParameter("password");
        PrintWriter out = response.getWriter();

        //String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        //System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
        // Verify CAPTCHA.
        /*
        boolean valid = VerifyUtils.verify(gRecaptchaResponse);
        if (!valid) {
            //errorString = "Captcha invalid!";
            out.println("<HTML>" +
                        "<HEAD><TITLE>" +
                        "MovieDB: Error" +
                        "</TITLE></HEAD>\n<BODY>" +
                        "<P>Recaptcha WRONG!!!! </P></BODY></HTML>");
            return;
        } */

	//	String loginUser = "mytestuser";
     //   String loginPasswd = "mypassword";

     //   String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        response.setContentType("application/json"); // Response mime type
        
        // Output stream to STDOUT
        //PrintWriter out = response.getWriter();
        //System.out.println("Before try loop");
        try {
        	System.out.println("here");
       //     Class.forName("com.mysql.jdbc.Driver").newInstance();
        //    Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
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
            
            String query = "SELECT * from customers where customers.email = " + "'"+ username+ "'" + " AND customers.password = " + "'"+password+ "'";
            // Perform the query
            ResultSet rs = statement.executeQuery(query);
            
            if (rs.next()) {
            		System.out.println("");
            		request.getSession().setAttribute("user", new User(username, rs.getString("id")));
            		JsonObject jsonObject = new JsonObject();
	            jsonObject.addProperty("status", "success");
	            jsonObject.addProperty("message", "success");
	            System.out.println(jsonObject.toString());
    				out.write(jsonObject.toString());
	           
            }
            
            else {
            		//login fails
	            	//request.getSession().setAttribute("user", new User(username));
	    			JsonObject responseJsonObject = new JsonObject();
	    			responseJsonObject.addProperty("status", "fail");
	    			responseJsonObject.addProperty("message", "email " + username + " doesn't exist");
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