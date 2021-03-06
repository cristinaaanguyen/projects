

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class Browse
 */
@WebServlet("/Browse")
public class Browse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Browse() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    public String numOfResults(String param) {
    	try {
    		int result = Integer.parseInt(param);
    		switch (result) {
    		case 25: return "25";
    		case 50: return "50";
    		case 100: return "100";
    		default: return "10";
    		}
    	}catch (Exception e){
    		return "10";
    	}
    }
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String search = request.getParameter("type");
		String results = request.getParameter("results");
		String page = request.getParameter("page");
		
		results = numOfResults(results);
		if (page == null) page = "1";
		
		String loginUser = "mytestuser";
        String loginPasswd = "mypassword";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        response.setContentType("application/json"); // Response mime type
        
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        //System.out.println("Before try loop");
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            //System.out.println("here");
            // Declare our statement
            Statement statement = dbcon.createStatement();
            String query;
            JsonArray jsonArray = new JsonArray();
            ResultSet rs;
            if (search.equals("genre")) {
            query = "Select name from genres order by name";
            rs = statement.executeQuery(query);
           // JsonArray jsonArray = new JsonArray();
            
            // Iterate through each row of rs
            while (rs.next()) {
                String genre_name = rs.getString("name");
                //String star_name = rs.getString("name");
                //String star_dob = rs.getString("birthYear");
                
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("genre_name", genre_name);
                //jsonObject.addProperty("star_name", star_name);
              //  jsonObject.addProperty("star_dob", star_dob);
                
                jsonArray.add(jsonObject);
            }
            }else {
            	query = "Select title from movies order by title limit "+ results + " offset " + (Integer.parseInt(page)-1) * 10;
            
            // Perform the query
            rs = statement.executeQuery(query);
            
           // JsonArray jsonArray = new JsonArray();
            
            // Iterate through each row of rs
            while (rs.next()) {
                String genre_name = rs.getString("name");
                //String star_name = rs.getString("name");
                //String star_dob = rs.getString("birthYear");
                
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("genre_name", genre_name);
                //jsonObject.addProperty("star_name", star_name);
              //  jsonObject.addProperty("star_dob", star_dob);
                
                jsonArray.add(jsonObject);
            }
            }
            
            out.write(jsonArray.toString());
            
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
