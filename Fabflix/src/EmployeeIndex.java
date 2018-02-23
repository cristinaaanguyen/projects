

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
import java.sql.CallableStatement;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.cj.jdbc.PreparedStatement;

/**
 * Servlet implementation class EmployeeIndex
 */
@WebServlet("/EmployeeIndex")
public class EmployeeIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeIndex() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//star form
		String starname = request.getParameter("starname");
		String birthyear = request.getParameter("birthyear");
		System.out.println("Star name: " + starname);
		System.out.println("Star birthyear: " + birthyear);
		String max_star_id = "";
		



		

		String loginUser = "mytestuser";
        String loginPasswd = "mypassword";

        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
        response.setContentType("application/json"); // Response mime type
        
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        //System.out.println("Before try loop");
        try {
        		System.out.println("here");
        		String query = "";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            dbcon.setAutoCommit(false);
            //System.out.println("here");
	           JsonObject jsonObject = new JsonObject();


            if (starname != null && !starname.equals("")) {
		            Statement statement = dbcon.createStatement();
		            if (birthyear == null || birthyear.equals("")) {
		            System.out.println("Empty birthyear");
		            	 query = "select * from stars where stars.name = \""+ starname +"\" and stars.birthyear is NULL or stars.birthyear = \""+ birthyear + "\"";
		           }
		           else {
		            	query = "select * from stars where stars.name = \""+ starname +"\" and stars.birthyear = \""+ birthyear + "\"";
		           }
		           ResultSet rs = statement.executeQuery(query);
		   		if (rs.next()) {
		    				System.out.println("duplicate star, don't add");
		    	            jsonObject.addProperty("status", "failed");
		    	            jsonObject.addProperty("message", "failed to add new star: " + starname);
		    				
		    			}
		    			else {
		    	            Statement statementMax = dbcon.createStatement();
		    	            ResultSet max_id = statementMax.executeQuery("select * from max_star_id");
		    	            if (max_id.next()) {
		    	            		String base = max_id.getString("id").replaceAll("[0-9]", "");
	    	            			System.out.println("base: " + base);

		    	            		//System.out.println("max id: " + Integer.parseInt(max_id.getString("id").replaceAll("[\\D]", "")));
		    	            		int max = Integer.parseInt(max_id.getString("id").replaceAll("[\\D]", "")) + 1;
		    	            		if  (max >= 9999999) {
		    	            			System.out.println("exceeding varchar limit");
		    	            			base = "aa";
		    	            			max_star_id = base + "000000";  
		    	            		}
		    	            		else {
		    	            			max_star_id = base + max;
		    	            		}
		    	            		System.out.println("Updating new max_star_id: " + max_star_id);
		
		    	            		String update_query = "Update max_star_id SET id = ?";
		    	            		System.out.println("before making ps for update_query");
		    	            	    java.sql.PreparedStatement ps = dbcon.prepareStatement(update_query);
		    	            	    ps.setString(1, max_star_id);
		    	            		System.out.println("before executing ps for update_query");
		    	            	    ps.executeUpdate();
		    	            	    ps.close();
		    	            		System.out.println("Updated new max");
		
		    	            		System.out.println("Inserting new star");
		
		    	            	    String insert_query = "insert into stars (id, name, birthyear)" + " values (?,?,?)";
		    	            	    java.sql.PreparedStatement p_stmt = dbcon.prepareStatement(insert_query);
		    	            	    p_stmt.setString(1, max_star_id);
		    	            	    p_stmt.setString(2, starname);
		    	            	    if (birthyear == null || birthyear.equals(""))
		    	            	    		p_stmt.setString(3, null);
		    	            	    else
		    	            	    		p_stmt.setInt(3, Integer.parseInt(birthyear));
		    	            	    p_stmt.execute();
		    	            	    p_stmt.close();
		    	            		System.out.println("Inserted new star");
		    	    	            jsonObject.addProperty("status", "success");
		    	    	            jsonObject.addProperty("message", "Successfully added new star: " + starname);
		    	    	        
		    	            }
		    			}
		    			out.write(jsonObject.toString());
		            statement.close();
		            dbcon.close();
            } //end of first if 
            
    		//movie form
    		String mstar = request.getParameter("mstar");
    		String genre = request.getParameter("genre");
    		String title = request.getParameter("title");
    		String director = request.getParameter("director");
    		String year = request.getParameter("year");
            
        //begin if statement for movie form 
    		if (isEmpty(mstar) || isEmpty(genre) || isEmpty(title) || isEmpty(director) || isEmpty(year)) {
    			System.out.println("missing required fields for adding movie");
    		} //end second if 
    		else {
    			System.out.println("all fields are exist");
 	        JsonObject jsonObject2 = new JsonObject();

    			CallableStatement cs = dbcon.prepareCall("{call add_movie(?, ?, ?, ?,?,?)}");
    			System.out.println("preparing callable statement");
    			cs.setString(1, title);
    			cs.setInt(2, Integer.parseInt(year));
    			cs.setString(3, director);
    			cs.setString(4, mstar);
    			cs.setString(5, genre);
    			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
    			System.out.println("before executing cs");
    		    cs.execute();
    			System.out.println("after executing cs");
    			dbcon.commit();
    		    String message = cs.getString(6);
    		    jsonObject2.addProperty("status", "success");    		    
    		    System.out.println("message from stored procedure: " + message);
    		    jsonObject2.addProperty("spmsg", message);
    			out.write(jsonObject2.toString());

   			
    		} //end second else

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
	
	boolean isEmpty(String s) {
		if (s != null && !s.equals("") && !s.equals("null")) {
		    return false;
		} 
		
		return true;
	}

}
