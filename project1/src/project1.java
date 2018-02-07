import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;


@WebServlet("/project1")
public class project1 extends HttpServlet {
    public String getServletInfo() {
        return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String loginUser = "ahtrejo";
        String loginPasswd = "1996Code";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?allowMultiQueries=true";
        

        response.setContentType("text/html"); // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        out.println("<HTML><HEAD><TITLE>MovieDB</TITLE></HEAD>");
        out.println("<BODY><H1>MovieDB</H1>");

        try {
            //Class.forName("org.gjt.mm.mysql.Driver");
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // Declare our statement
            Statement statement = dbcon.createStatement();
            Statement statementGenre = dbcon.createStatement();
            Statement statementStars = dbcon.createStatement();

            String query = "SELECT * from  movies M, ratings R where M.id = R.movieId ORDER BY R.rating DESC LIMIT 20";
            // Perform the query
           
            ResultSet rs = statement.executeQuery(query);
            


            out.println("<TABLE border>");

            // Iterate through each row of rs
            /*
            while (genresResults.next()){
            	out.println(genresResults.getString("movieId"));
            	
            }
            */

            while (rs.next()) {
                String m_id = rs.getString("id");
                String m_title = rs.getString("title");
                String m_year = rs.getString("year");
                String m_director = rs.getString("director");
                String m_rating = rs.getString("rating");
                String m_genres = "";
                String queryGenre = "SELECT G.name from genres G, genres_in_movies GM where G.id = GM.genreId and GM.movieId = \""+ m_id + "\"";
                ;
                ResultSet genresResults = statementGenre.executeQuery(queryGenre);
                
                while (genresResults.next()) {
                		String m_gmid = genresResults.getString("name");
                		if (m_genres != "") 
                			m_genres += ", "; 
                		m_genres += m_gmid;
                }
                
                
                String m_stars = "";
                String queryStars = "SELECT S.name from stars S, stars_in_movies SM where S.id = SM.starId and SM.movieId = \""+ m_id + "\"";
                ;
                ResultSet starsResults = statementStars.executeQuery(queryStars);
                
                while (starsResults.next()) {
                		String m_smid = starsResults.getString("name");
                		if (m_stars != "") 
                			m_stars += ", "; 
                		m_stars += m_smid;
                }
                
                out.println("<tr>" + "<td>" + m_id + "</td>" + "<td>" + m_title + "</td>" + "<td>" + m_year + "</td>"
                         + "<td>" + m_director + "</td>" + "<td>" + m_genres + "</td>" +
                         "<td>" + m_stars + "</td>" + "<td>" + m_rating + "</td>" +
                			"</tr>");
            }

            out.println("</TABLE>");

            rs.close();
           // genresResults.close();
            statement.close();
            //statementGenre.close();
            dbcon.close();
        } catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception:  " + ex.getMessage());
                ex = ex.getNextException();
            } // end while
        } // end catch SQLException

        catch (java.lang.Exception ex) {
            out.println("<HTML>" + "<HEAD><TITLE>" + "MovieDB: Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>SQL error in doGet: " + ex.getMessage() + "</P></BODY></HTML>");
            return;
        }
        out.close();
    }
}