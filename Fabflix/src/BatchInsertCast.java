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


public class BatchInsertCast {

	 List<Stars> myCast = new ArrayList<Stars>();
     //String currStarID = "";
     int max = 0;

   static String base = "";


    public BatchInsertCast( List<Stars> cast) {
        //create a list to hold the employee objects
        myCast = cast;
        
    }
    
    public void executeBatch() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
   		HashMap<String, String> currentStars = new HashMap<String, String>(); //id and name for star
   		HashMap<String, String> xmlCast = new HashMap<String, String>(); //name and moveid for cast member 
   		HashMap<String, String> starsinmovies = new HashMap<String, String>(); //starid and movieid
   		HashMap<String, String> movies = new HashMap<String, String>(); //movieid, movie title

   		

   		

   		

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
        

        sqlInsertRecord="insert into stars_in_movies (starId, movieId) values(?,?)";
        try {
        		//get max movie id
        	Statement statement2 = dbcon.createStatement();
        	ResultSet rs = statement2.executeQuery("select * from stars_in_movies");
            while (rs.next()) {
        		starsinmovies.put(rs.getString("starId"), rs.getString("movieId"));
        }
   
            
            //make hash table with movies
        	Statement statement = dbcon.createStatement();
            ResultSet currstars = statement.executeQuery("select * from stars");
            while (currstars.next()) {
            		currentStars.put(currstars.getString("name"), currstars.getString("id"));
            }
            
        	Statement statement3 = dbcon.createStatement();
            ResultSet m = statement3.executeQuery("select * from movies");
            while (m.next()) {
            		movies.put(m.getString("id"), m.getString("title"));
            }
            
            System.out.println("Size of hashmap: " +currentStars.size());

            
			dbcon.setAutoCommit(false);
            psInsertRecord=dbcon.prepareStatement(sqlInsertRecord);
            
            for(int i=0;i<myCast.size();i++)
            {
            		String name =  myCast.get(i).getName();
            		String id = currentStars.get(name);
            		String mid = starsinmovies.get(id);
            		
            		if (name != null && !name.equals("") && currentStars.get(name)!=null) {

	            		/*if (currentStars.containsKey(name) && (!starsinmovies.containsKey(id)  || 
	            				!starsinmovies.get(id).equals(myCast.get(i).getId()))) {*/
            			
            			if(movies.containsKey(myCast.get(i).getId())) {
		                psInsertRecord.setString(1, currentStars.get(name));
		                psInsertRecord.setString(2,myCast.get(i).getId());
		                psInsertRecord.addBatch();
            			}
	            		
	            		else {
	            				System.out.println("Cast in movie does not exist in movies");
	            		}
            		}
         }
            
			iNoRows=psInsertRecord.executeBatch();			
 
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
