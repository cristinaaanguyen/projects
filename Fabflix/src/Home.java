

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		System.out.println("inside doGet");
		String query = request.getParameter("query");
		//String query = "Good U";
		System.out.println("query: " + query);
		String loginUser = "mytestuser";
        String loginPasswd = "mypassword";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
		//response.getWriter().append("Served at: ").append(request.getContextPath());
        response.setContentType("application/json"); // Response mime type
      
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        try {	
        				System.out.println("Inside home java, making connection");
		            Class.forName("com.mysql.jdbc.Driver").newInstance();
		            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

				if (!isEmpty(query)){ //if user wants to use advanced search
						//if (request.getSession().getAttribute(query) == null){ //query hasnt been searched for so make the search //ifelse0
							System.out.println("query: " + query + "hasn't been searched for");
							String moviequery = "select * from movies where MATCH(movies.title) AGAINST "; 
							//String moviequery = "select * from movies where MATCH(movies.title) AGAINST ( ? in boolean mode)"; 

							/*
							PreparedStatement movieps = dbcon.prepareStatement(moviequery);
							movieps.setString(1, "+" + query);
							ResultSet moviers = movieps.executeQuery();
							*/
							
							
							
							PreparedStatement movieps = MakePS(moviequery,query, dbcon, 5);
							ResultSet moviers   = movieps.executeQuery();
							
							
							
							System.out.println("after executing search for movies");
					        JsonArray jsonMovieAndStars = new JsonArray();
						        if (!moviers.isBeforeFirst()) {//startif1
					        		System.out.println("No results found");
					        		//jsonMovieAndStars.add("movies", jsonMovieArray);
						        }
						        else {
						        		System.out.println("movie results found");
						            while (moviers.next()) {

										jsonMovieAndStars.add(generateJsonMovieObject(moviers.getString("id"),
												moviers.getString("title"),moviers.getString("year"), 
												moviers.getString("director"), "movies"));

						            	
										System.out.println("Added movie: " + moviers.getString("title")  + " to main array");
		
						            }
		
						            //close moviers
						            moviers.close();
						            movieps.close();
						       } //endif1
						        
						       //search for stars
								//String starquery = "select * from stars where MATCH(stars.name) AGAINST (? in boolean mode)"; 
								String starquery = "select * from stars where MATCH(stars.name) AGAINST "; 
								
								PreparedStatement starps = MakePS(starquery,query, dbcon, 5);

								//PreparedStatement starps = dbcon.prepareStatement(starquery);
								//starps.setString(1, "+" + query);
								ResultSet starrs   = starps.executeQuery();
								
								
			        				System.out.println("After executing star query");
			        				if (!starrs.isBeforeFirst()) {//startif2
						        		System.out.println("No star results found");
							        }
						        
							        else {
							        		
							        		System.out.println(" star results found");
							        		System.out.println(starrs);

							            while (starrs.next()) {

											jsonMovieAndStars.add(generateJsonStarObject(starrs.getString("id"), 
													starrs.getString("name"), starrs.getString("birthYear"), "stars"));
							            }
							            
					        				System.out.println("added stars to jsonMovieAndStars");
		
							       } //endif2
		
							 out.write(jsonMovieAndStars.toString());
							 System.out.println(jsonMovieAndStars.toString());

		
						}//ifelse0

           }//endtry
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
	
	PreparedStatement MakePS(String q,String s, Connection dbcon, int limit ) throws SQLException {
		//String moviequery = "select * from movies where MATCH(movies.title) AGAINST --> q
		//(? in boolean mode) limit 10"; 
		
		String [] params = s.split(" ");
		//String filler = "(? ";
		String filler = "(";
		System.out.println("# of words to match " + params.length);
		
		for (int i = 0; i < params.length; i++ ) {
			filler += "? ";
			
			/*
			if (i != params.length-1) {
				filler += ",";
			
			}*/
		} 
		
		filler += "in boolean mode)";
		q += filler + " limit " + limit;
		
		System.out.println("new ps in MakePS: " + q);
		PreparedStatement ps = dbcon.prepareStatement(q);
		//ps.setString(1, "+" +q);

		
		for (int i = 0; i < params.length; i++ ) {
			ps.setString(i+1, "+"+params[i] + "*");
		}
		
		return  ps;
	}
	
	
	/*
	 * Generate the JSON Object from hero and category to be like this format:
	 * {
	 *   "value": "Good Ugly Bad",
	 *   "data": { "category": "movies", "movieid": "someid", "year": "someyear", "director": "somedirector" }
	 * }
	 * 
	 */
	private static JsonObject generateJsonMovieObject(String movieid, String title, String year, String director, String categoryName ) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("value", title);
		
		JsonObject additionalDataJsonObject = new JsonObject();
		additionalDataJsonObject.addProperty("category", categoryName);
		additionalDataJsonObject.addProperty("movieid", movieid);
		additionalDataJsonObject.addProperty("year", year);
		additionalDataJsonObject.addProperty("director", director);


		
		
		jsonObject.add("data", additionalDataJsonObject);
		return jsonObject;
	}
	
	private static JsonObject generateJsonStarObject(String starid, String starname,  String birthyear, String categoryName ) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("value", starname);
		
		JsonObject additionalDataJsonObject = new JsonObject();
		additionalDataJsonObject.addProperty("category", categoryName);
		additionalDataJsonObject.addProperty("starid", starid);
		additionalDataJsonObject.addProperty("birthyear", birthyear);		
		jsonObject.add("data", additionalDataJsonObject);
		return jsonObject;
	}

}