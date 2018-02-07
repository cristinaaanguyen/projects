

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
	response.setContentType("application/json"); // Response mime type
        
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        try {
        	JsonObject jsonObject = new JsonObject();
        	jsonObject.addProperty("title", title);
	        jsonObject.addProperty("year", year);
	        jsonObject.addProperty("director", director);
	        jsonObject.addProperty("starfn", starfn);
	        jsonObject.addProperty("starln", starln);
	        System.out.println(jsonObject.toString());
			out.write(jsonObject.toString());
           }
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
