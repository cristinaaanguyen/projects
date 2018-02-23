import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;

import java.sql.SQLException;

/*

The SQL command to create the table ft.

DROP TABLE IF EXISTS ft;
CREATE TABLE ft (
    entryID INT AUTO_INCREMENT,
    entry text,
    PRIMARY KEY (entryID),
    FULLTEXT (entry)) ENGINE=MyISAM;

*/

/*

Note: Please change the username, password and the name of the datbase.

*/

public class BatchInsertMovies {

	 List<Movie> myMovies = new ArrayList<Movie>();
     String currMovieID = "";
     int max = 0;

   static String base = "";


    public BatchInsertMovies( List<Movie> movies) {
        //create a list to hold the employee objects
        myMovies = movies;
        
    }
    
    public void executeBatch() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
   		HashMap<String, Integer> currentMovies = new HashMap<String, Integer>();
   		HashMap<String, String> xmlMovies = new HashMap<String, String>();


        Connection dbcon = null;

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
		
		String loginUser = "mytestuser";
        String loginPasswd = "mypassword";

        try {
             dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement psInsertRecord=null;
        String sqlInsertRecord=null;

        int[] iNoRows=null;
        

        sqlInsertRecord="insert into movies (id, title, year, director) values(?,?,?,?)";
        try {
        		//get max movie id
        	/*
        	Statement statementMax = dbcon.createStatement();
            ResultSet max_id = statementMax.executeQuery("select * from max_star_id");
            if (max_id.next()) {
            		base = max_id.getString("MaxMovieId").replaceAll("[0-9]", "");
        			System.out.println("base: " + base);

            		//System.out.println("max id: " + Integer.parseInt(max_id.getString("id").replaceAll("[\\D]", "")));
            		max = Integer.parseInt(max_id.getString("MaxMovieId").replaceAll("[\\D]", ""));
            		
            		if  (max >= 9999999) {
            			System.out.println("exceeding varchar limit");
            			base = "aa";
            			max = 000000;  
            		}
            }

            */
            //make hash table with movies
        	Statement statement = dbcon.createStatement();
            ResultSet currmovies = statement.executeQuery("select * from movies");
            while (currmovies.next()) {
            		currentMovies.put(currmovies.getString("title"),currmovies.getInt("year"));
            }
            
            System.out.println("Size of hashmap: " +currentMovies.size());

            
			dbcon.setAutoCommit(false);
            psInsertRecord=dbcon.prepareStatement(sqlInsertRecord);
            
            for(int i=0;i<myMovies.size();i++)
            {
            		String title = myMovies.get(i).getTitle();
            		String director = myMovies.get(i).getDirector();
            		if (title != null && !title.equals("") && (director != null && !director.equals(""))) {
            			//System.out.println(title);
            			//System.out.println(myMovies.get(i).getYear());
	            		if (!currentMovies.containsKey(title) ||
	            				(currentMovies.get(title) != myMovies.get(i).getYear())){
	            			if (!xmlMovies.containsKey(myMovies.get(i).getId()) && myMovies.get(i).getId() != null ) {
			            		max += 1;
			            		currMovieID = base + max;
			            		xmlMovies.put( myMovies.get(i).getId(),title);
			                psInsertRecord.setString(1,myMovies.get(i).getId() );
			                psInsertRecord.setString(2,myMovies.get(i).getTitle());
			                psInsertRecord.setInt(3, myMovies.get(i).getYear());
			                psInsertRecord.setString(4, myMovies.get(i).getDirector());
			                psInsertRecord.addBatch();
	            			}
	            			
	            			else {
	            				System.out.println("Duplicate movie: " + title);
	            			}
		                
	            		
            		}
	            		else {
	            			System.out.println("Duplicate movie: " + title);
	            		}
            	}
           }
            

			iNoRows=psInsertRecord.executeBatch();			
    		System.out.println("Updating current Movie id: " + currMovieID);
    		String update_query = "Update max_star_id SET MaxMovieId = ?";
    		System.out.println("before making ps for update_query");
    	    java.sql.PreparedStatement ps = dbcon.prepareStatement(update_query);
    	    ps.setString(1, currMovieID);
    		System.out.println("before executing ps for update_query");
    	    ps.executeUpdate();
    	    ps.close();
    		System.out.println("Updated new max");
		dbcon.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(psInsertRecord!=null) psInsertRecord.close();
            if(dbcon!=null) dbcon.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
}

   
}


