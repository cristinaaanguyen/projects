

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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * Servlet implementation class MovieList
 */
@WebServlet("/MovieList")
public class MovieList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieList() {
        super();
        // TODO Auto-generated constructor stub
    }
    

    public void queryGenre(Statement statement, ResultSet rs, JsonArray jsonArray, String genre ) {
    	String query = "select m.id, m.title, m.year, m.director from movies m, genres g, genres_in_movies gm "+
    "where m.id = gm.movieId and g.id = gm.genreId and g.name = "+ genre + " order by title";
    	try {
    		rs = statement.executeQuery(query);
       // JsonArray jsonArray = new JsonArray();
        
        // Iterate through each row of rs
    		while (rs.next()) {
    			String genre_name = rs.getString("name");
            
    			JsonObject jsonObject = new JsonObject();
    			jsonObject.addProperty("genre_name", genre_name);
            
    			jsonArray.add(jsonObject);
        }
        
    } catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception:  " + ex.getMessage());
                ex = ex.getNextException();
            } // end while
        } // end catch SQLException*/
        
    }
    
    
    public String browseQuery(String genre, String title) {
    	String query;
    	System.out.println("genre = " + genre);

    	if (genre != null) {
    		query = "select m.id, m.title, m.year, m.director from movies m, genres g, genres_in_movies gm "+
    			    "where m.id = gm.movieId and g.id = gm.genreId and g.name = \""+ genre + "\"";
    	} else {
    		query = "select m.id, m.title, m.year, m.director from movies m where m.title like '" + title + "%'";
	        }
    	return query;
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Print parameter from url");
		//System.out.println(request.getParameter("title"));

		String browse = request.getParameter("browse");
		String genre = request.getParameter("genre");
		String title = request.getParameter("title");
		String year = request.getParameter("year");
		String director = request.getParameter("director");
		String starfn = request.getParameter("starfn");
		String starln = request.getParameter("starln");
		System.out.println("Print url");
		String url = request.getRequestURL().toString() + request.getQueryString();
		System.out.println(url);

		System.out.println("Print title parameter or artist");
		System.out.println(title);
		System.out.println(starfn);

		String loginUser = "mytestuser";
        String loginPasswd = "mypassword";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?allowMultiQueries=true";
        response.setContentType("application/json"); // Response mime type

        String query;
        //System.out.println("browse is"+ browse);
        if (browse != null && !isEmpty(browse) ) {
        	query = browseQuery(genre, title);
        }else {
        	query = makeQuery(request, title, year, director, starfn, starln);
        }
            String type = request.getParameter("order");
            int limit = 10;
            String ordering = request.getParameter("ordering");
         
            System.out.println("here");
            if (!isEmpty(request.getParameter("limit"))){
            		System.out.println("limit is not empty");
                limit = Integer.parseInt(request.getParameter("limit"));
                
            }
            
        System.out.println("Printing type to sort by");
        System.out.println(type);
       // if ("title".equals(type) ||"year".equals(type) ) {
        query = updateQuery(query, ordering, type, limit, 0);
       // }
    
        System.out.println(query);
        PrintWriter out = response.getWriter();
        executeSearchQuery(request, query, out, starfn, starln);        // Output stream to STDOUT
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
	
	String makeQuery(HttpServletRequest request, String title, String year, String director, String starln, String starfn) {
        String base = "SELECT * from movies m";
        String subquery = "";
        String query = "";
        
        if (!isEmpty(title)) {  	
        		request.getSession().setAttribute("title", title);
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
        
        return base + " where " + subquery;
  
	}
	
	
	void executeSearchQuery(HttpServletRequest request, String query, PrintWriter out, String starfn, String starln) {
		try {

			String loginUser = "ahtrejo";
	        String loginPasswd = "1996Code";


	        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?allowMultiQueries=true";
	        Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	        
			Statement statement = dbcon.createStatement();
	        Statement statementStars = dbcon.createStatement();
	        Statement statementGenre = dbcon.createStatement();
	        ResultSet rs = statement.executeQuery(query);
	        System.out.println("after executing query");
	        JsonArray jsonArray = new JsonArray();
	        
	        if (!rs.isBeforeFirst()) {
	        	 	JsonObject jsonObject = new JsonObject();
	        		System.out.println("No results found");

	        		jsonObject.addProperty("errmsg", "failed");

	        		//out.write(jsonObject.toString());
	        		jsonArray.add(jsonObject);
	        		System.out.println(jsonObject.toString());
	        		System.out.println("Wrote JSON object to string");
	        	
	        }
	        
	        else {
	        		System.out.println("results found");
	        		System.out.println(rs);

	        		JsonObject jsonObj = new JsonObject();
	        		jsonObj.addProperty("errmsg", "success");
	        		jsonArray.add(jsonObj);
	            while (rs.next()) {
	        			JsonObject jsonObject = new JsonObject();

	            		//System.out.println("inside while loop");
	            		String movieID = rs.getString("id");
	            		String movieTitle = rs.getString("title");
	            		String movieYear = rs.getString("year");
	            		String movieDirector = rs.getString("director");
	            		String m_genres = "";
	            		String queryGenre = "SELECT G.name from genres G, genres_in_movies GM where G.id = GM.genreId and GM.movieId = \""+ movieID + "\"";

            			String movieStar = ""; 
            			if (!isEmpty(starfn) || !isEmpty(starln))
            				movieStar = rs.getString("name");
	                ResultSet genresResults = statementGenre.executeQuery(queryGenre);
	                    
	                while (genresResults.next()) {
	                    		String m_gmid = genresResults.getString("name");
	                    		if (m_genres != "") 
	                    			m_genres += ", "; 
	                    		m_genres += m_gmid;
	                    }
	                

	            		String queryStars = "select s.name, s.id from movies m, stars_in_movies ms, stars s where "
	            				+ "m.id = " + "'"+ movieID + "'" + " and ms.movieID = m.id and ms.starId = s.id";
	            				;
	            		ResultSet results = statementStars.executeQuery(queryStars);

	            		JsonArray jsonStarArray = new JsonArray();
	            		while(results.next()) {
	            			JsonObject jsonStarInfo = new JsonObject();
	            			jsonStarInfo.addProperty("starid", results.getString("id"));
	            			jsonStarInfo.addProperty("name", results.getString("name"));
	            			jsonStarArray.add(jsonStarInfo);
	            		}
	
	            		jsonObject.addProperty("movieid", movieID);
	            		jsonObject.addProperty("title", movieTitle);
	            		jsonObject.addProperty("year", movieYear);
	            		jsonObject.addProperty("director", movieDirector); 
	            		jsonObject.addProperty("star", movieStar);
	            		System.out.println(jsonStarArray.toString());
	            		jsonObject.add("stars", jsonStarArray);
	            		jsonObject.addProperty("genres", m_genres);
	            		

	            		System.out.println(jsonObject.toString());
	            		jsonArray.add(jsonObject);
	            		//out.write(jsonObject.toString());
	            }
	       }
	        out.write(jsonArray.toString());

	        //request.getSession().setAttribute("jsonArray", jsonArray);

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
			s += "ms.movieId = m.id and ms.starId = s.id and s.name like " + "'%"+ fn + "%'";
			
		}
		else if (isEmpty(fn) && !isEmpty(ln)) {
			s += "ms.movieId = m.id and ms.starId = s.id and s.name like " + "'%"+ ln + "%'";
		}
		return s;
	}
	
	String updateQuery(String query, String order, String type, int limit, int offset) {
		
		if (!isEmpty(type)) {
			query += " order by " + "m."+type;
			if (!isEmpty(order)) {
				query += " " + order;
			}
		}
		
		
		if (limit != 0){
			query += " limit " + limit;
		}
		if (offset != 0) {
			query += " offset  " + offset;
		}
		return query;
	}

}
