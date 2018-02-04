

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
import com.google.gson.JsonArray;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("inside doGet");
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String title = request.getParameter("title");
		String year = request.getParameter("year");
		String director = request.getParameter("director");
		String starfn = request.getParameter("firstname");
		String starln = request.getParameter("lastname");
		String loginUser = "mytestuser";
        String loginPasswd = "mypassword";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?allowMultiQueries=true";
        response.setContentType("application/json"); // Response mime type
        
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        //System.out.println("Before try loop");
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // Declare our statement
            Statement statement = dbcon.createStatement();
            Statement statementStars = dbcon.createStatement();
            Statement statementGenre = dbcon.createStatement();
            System.out.println("creating query statement");
            String base = "SELECT * from movies m";
            String subquery = "";
            String query = "";
            
            if (!isEmpty(title)) {  	
            		if (subquery.contains("and") || !isEmpty(subquery)) {
            			subquery += " and m.title like " + "'%"+ title + "%'";
            		}
            		else {
            			subquery += "m.title like " + "'%"+ title + "%'";
            		}
            }
            if (!isEmpty(director)) {
	        		if (subquery.contains("and") || !isEmpty(subquery)) {
	        			subquery += " and ";
	        		}
	        		subquery += "m.director like " + "'%"+ director + "%'";
            }
            if (!isEmpty(year)) {

	        		if (subquery.contains("and") || !isEmpty(subquery)) {
	        			subquery += " and ";
	        		}
	        		subquery += "m.year = " + year;
            }
            
            if (!isEmpty(starfn) || !isEmpty(starln)) {
            		base = addStars(base);
            		subquery = addStarQuery(subquery, starfn, starln);
            }
            
            query = base + " where " + subquery;
            System.out.println(query);
            // Perform the query
            ResultSet rs = statement.executeQuery(query);
            System.out.println("after executing query");
            JsonArray jsonArray = new JsonArray();
            
            if (!rs.isBeforeFirst()) {
            	 	JsonObject jsonObject = new JsonObject();
            		System.out.println("No results found");
            		jsonObject.addProperty("title", "failed");
            		//out.write(jsonObject.toString());
            		jsonArray.add(jsonObject);
            		System.out.println(jsonObject.toString());
            		System.out.println("Wrote JSON object to string");
            	
            }
            
            else {
            		System.out.println("results found");
            		System.out.println(rs);
	            while (rs.next()) {
	            	 	JsonObject jsonObject = new JsonObject();
	            		System.out.println("inside while loop");
	            		String movieID = rs.getString("id");
	            		String movieTitle = rs.getString("title");
	            		String movieYear = rs.getString("year");
	            		String movieDirector = rs.getString("director");
	            		String m_genres = "";
	                String queryGenre = "SELECT G.name from genres G, genres_in_movies GM where G.id = GM.genreId and GM.movieId = \""+ movieID + "\"";;
	                ResultSet genresResults = statementGenre.executeQuery(queryGenre);
	                    
	                while (genresResults.next()) {
	                    		String m_gmid = genresResults.getString("name");
	                    		if (m_genres != "") 
	                    			m_genres += ", "; 
	                    		m_genres += m_gmid;
	                    }
	                
	            		String movieStar = ""; 
	            		if (!isEmpty(starfn) || !isEmpty(starln))
	            			movieStar = rs.getString("name");
	            		String queryStars = "select s.name from movies m, stars_in_movies ms, stars s where "
	            				+ "m.id = " + "'"+ movieID + "'" + " and ms.movieID = m.id and ms.starId = s.id";
	            				;
	            		ResultSet results = statementStars.executeQuery(queryStars);
	            		String actors = "";
	            		while(results.next()) {
	                		if (actors != "") 
	                			actors += ", "; 
	                		actors += results.getString("name");
	            		}
	            		
	            		
	            		jsonObject.addProperty("movieid", movieID);
	            		jsonObject.addProperty("title", movieTitle);
	            		jsonObject.addProperty("year", movieYear);
	            		jsonObject.addProperty("director", movieDirector); 
	            		jsonObject.addProperty("star", movieStar);
	            		jsonObject.addProperty("stars", actors);
	            		jsonObject.addProperty("genres", m_genres);
	            		
	            		System.out.println(jsonObject.toString());
	            		jsonArray.add(jsonObject);
	            		//out.write(jsonObject.toString());
	            }
           }
            out.write(jsonArray.toString());
            request.getSession().setAttribute("jsonArray", jsonArray);
            
            
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
	
	boolean isEmpty(String s) {
		if (s != null && !s.equals("")) {
		    return false;
		} 
		
		return true;
	}
	
	String addStars(String baseString) {
		if (!baseString.contains("stars")) {
			if (baseString.contains(","))
				baseString += "stars s, stars_in_movies ms";
			else {
				baseString += ", stars s, stars_in_movies ms";
			}
		}
		return baseString;
	}
	
	String addStarQuery(String s, String fn, String ln) {
		if (s.contains("and") || !isEmpty(s)) {
			//System.out.println(s);
			//System.out.println("Before adding to query");
			s += " and ";
		}
		
		if (!isEmpty(fn) && !isEmpty(ln)) {
			String fullname = fn + " " + ln;
	    		s += "ms.movieId = m.id and ms.starId = s.id and s.name like "  + "'%" +fullname + "%'";
			
		}
		else if (!isEmpty(fn) && isEmpty(ln)) {
			s += "ms.movieId = m.id and ms.starId = s.id and s.name like " + "'"+ fn + "%'";
			
		}
		else if (isEmpty(fn) && !isEmpty(ln)) {
			s += "ms.movieId = m.id and ms.starId = s.id and s.name like " + "'%"+ ln + "'";
		}
		return s;
	}

}
