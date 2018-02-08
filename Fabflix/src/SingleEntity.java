

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

import com.google.gson.JsonObject;

/**
 * Servlet implementation class SingleEntity
 */
@WebServlet("/SingleEntity")
public class SingleEntity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SingleEntity() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String loginUser = "ahtrejo";
        String loginPasswd = "1996Code";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        
        String id = request.getParameter("id");
        response.setContentType("application/json"); // Response mime type
        
        PrintWriter out = response.getWriter();
       
        try {
        	System.out.println("here");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            //System.out.println("here");
            // Declare our statement
            Statement statement = dbcon.createStatement();
            
            String query = "SELECT * from movies where movies.id = " + "'"+ id + "'";
            // Perform the query
            ResultSet rs = statement.executeQuery(query);
            
            JsonObject jsonObject = new JsonObject();

    		//System.out.println("inside while loop");
    		String movieID = rs.getString("id");
    		String movieTitle = rs.getString("title");
    		String movieYear = rs.getString("year");
    		String movieDirector = rs.getString("director");
            
    		jsonObject.addProperty("movieid", movieID);
    		jsonObject.addProperty("title", movieTitle);
    		jsonObject.addProperty("year", movieYear);
    		jsonObject.addProperty("director", movieDirector);
    		
    		out.write(jsonObject.toString());
            
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
