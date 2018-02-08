

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
        
        String MovieId = request.getParameter("Movieid");
        String StarId = request.getParameter("Starid");
        //System.out.println(MovieId);
        response.setContentType("application/json"); // Response mime type
        
        PrintWriter out = response.getWriter();
       
        try {
        	System.out.println("here");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            //System.out.println("here");
            // Declare our statement
           
            JsonObject jsonObject = new JsonObject();

            
            if (MovieId != null) {
            	System.out.println("In Movie");
            Statement statement = dbcon.createStatement();
            Statement statementStars = dbcon.createStatement();
            Statement statementGenre = dbcon.createStatement();
            
            String query = "SELECT * from movies where movies.id = " + "'"+ MovieId + "'";
            // Perform the query
            ResultSet rs = statement.executeQuery(query);
            

            rs.next();
    		//System.out.println("inside while loop");
    		String movieID = rs.getString("id");
    		String movieTitle = rs.getString("title");
    		String movieYear = rs.getString("year");
    		String movieDirector = rs.getString("director");
    		
    		String queryGenre = "SELECT G.name from genres G, genres_in_movies GM where G.id = GM.genreId and GM.movieId = \""+ movieID + "\"";

    		ResultSet genreResults = statementGenre.executeQuery(queryGenre);
            
    		JsonArray jsonGenreArray = new JsonArray();
    		while(genreResults.next()) {
    			JsonObject jsonGenreInfo = new JsonObject();
    			//jsonGenreInfo.addProperty("starid", genreResults.getString("id"));
    			jsonGenreInfo.addProperty("name", genreResults.getString("name"));
    			jsonGenreArray.add(jsonGenreInfo);
    		}
    		
    		String queryStars = "select s.name, s.id from movies m, stars_in_movies ms, stars s where "
    				+ "m.id = " + "'"+ movieID + "'" + " and ms.movieID = m.id and ms.starId = s.id";
    				
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
    		jsonObject.add("genres", jsonGenreArray);
    		jsonObject.add("stars", jsonStarArray);
    		
    		rs.close();
    		genreResults.close();
    		results.close();
            statement.close();
            statementStars.close();
            statementGenre.close();
            } else {
            	System.out.println("In Stars");
            	Statement StarStatement = dbcon.createStatement();
            	Statement MovieStatement = dbcon.createStatement();

            	
            	String starQuery = "SELECT * from stars where stars.id = " + "'"+ StarId + "'";
            	
                ResultSet StarRs = StarStatement.executeQuery(starQuery);
                
                StarRs.next();
                
                String StarID = StarRs.getString("id");
        		String StarName = StarRs.getString("name");
        		String StarBirthYear = StarRs.getString("birthYear");
        		
        		String queryMovie = "SELECT m.id, m.title from movies m, stars_in_movies SM where m.id = SM.movieId and SM.starId = \""+ StarID + "\"";
        		
        		ResultSet movieResults = MovieStatement.executeQuery(queryMovie);
        		
        		JsonArray jsonMovieArray = new JsonArray();
        		while(movieResults.next()) {
        			JsonObject jsonMovieInfo = new JsonObject();
        			jsonMovieInfo.addProperty("movieid", movieResults.getString("id"));
        			jsonMovieInfo.addProperty("title", movieResults.getString("title"));
        			jsonMovieArray.add(jsonMovieInfo);
        		}
        		
        		jsonObject.addProperty("starid", StarID);
        		jsonObject.addProperty("name", StarName);
        		jsonObject.addProperty("birthYear", StarBirthYear);
        		jsonObject.add("movies", jsonMovieArray);
        		
        		StarRs.close();
        		movieResults.close();
        		StarStatement.close();
        		MovieStatement.close();	
            }
    		
    		out.write(jsonObject.toString());
            
            
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
