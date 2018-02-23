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


public class BatchInsertStars {

	 List<Stars> myStars = new ArrayList<Stars>();
     String currStarID = "";
     int max = 0;

   static String base = "";


    public BatchInsertStars( List<Stars> stars) {
        //create a list to hold the employee objects
        myStars = stars;
        
    }
    
    public void executeBatch() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
   		HashMap<String, Integer> currentStars = new HashMap<String, Integer>();
   		HashMap<String, Integer> xmlStars = new HashMap<String, Integer>(); //name and dob 

   		

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
        

        sqlInsertRecord="insert into stars (id, name, birthYear) values(?,?,?)";
        try {
        		//get max movie id
        	Statement statementMax = dbcon.createStatement();
            ResultSet max_id = statementMax.executeQuery("select * from max_star_id");
            if (max_id.next()) {
            		base = max_id.getString("id").replaceAll("[0-9]", "");
        			System.out.println("base: " + base);

            		//System.out.println("max id: " + Integer.parseInt(max_id.getString("id").replaceAll("[\\D]", "")));
            		max = Integer.parseInt(max_id.getString("id").replaceAll("[\\D]", ""));
            		
            		if  (max >= 9999999) {
            			System.out.println("exceeding varchar limit");
            			base = "aa";
            			max = 000000;  
            		}
            }

            
          
            
            //make hash table with movies
        	Statement statement = dbcon.createStatement();
            ResultSet currstars = statement.executeQuery("select * from stars");
            while (currstars.next()) {
            		currentStars.put(currstars.getString("name"),currstars.getInt("birthYear"));
            }
            
            System.out.println("Size of hashmap: " +currentStars.size());

            
			dbcon.setAutoCommit(false);
            psInsertRecord=dbcon.prepareStatement(sqlInsertRecord);
            
            for(int i=0;i<myStars.size();i++)
            {
            		String name = myStars.get(i).getName();
            		if (name != null && !name.equals("")) {
            			//System.out.println(title);
            			//System.out.println(myMovies.get(i).getYear());
	            		if (!currentStars.containsKey(name) ||
	            				(currentStars.containsKey(name) &&
	            						(myStars.get(i).getBirthYear() != currentStars.get(name)))){
	            			if (!xmlStars.containsKey(myStars.get(i).getName()) || 
	            					myStars.get(i).getBirthYear() != xmlStars.get(name)) {

		            		max += 1;
		            		currStarID = base + max;
		                psInsertRecord.setString(1, currStarID);
		                psInsertRecord.setString(2,name);
		                psInsertRecord.setInt(3, myStars.get(i).getBirthYear());
		                psInsertRecord.addBatch();
	            		}
	            		
	            			else {
	            				System.out.println("Duplicate star");
	            			}
	            		
            		}
            	}
           }
            

			iNoRows=psInsertRecord.executeBatch();			
    		System.out.println("Updating current Star id: " + currStarID);
    		String update_query = "Update max_star_id SET id = ?";
    		System.out.println("before making ps for update_query");
    	    java.sql.PreparedStatement ps = dbcon.prepareStatement(update_query);
    	    ps.setString(1, currStarID);
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
