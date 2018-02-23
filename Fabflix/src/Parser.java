
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {
	HashMap<String, String> genres;
    List<Movie> myMovies;
    Document dom;

    public Parser() {
        //create a list to hold the employee objects
        myMovies = new ArrayList<Movie>();
        genres = new HashMap<String, String>();
    }

    
    public List<Movie> getMovieList(){
    			return myMovies;
    }
    
    public void runExample() {

        //parse the xml file and get the dom object
        parseXmlFile();

        //get each employee element and create a Employee object
        //parseDocument();
        parseFilms();
        //Iterate through the list and print the data
        printData();

    }

    private void parseXmlFile() {
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            dom = db.parse("../Fabflix/project3data/mains243.xml");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void parseDocument() {
        //get the root elememt
        Element docEle = dom.getDocumentElement();

        //get a nodelist of <employee> elements
        NodeList nl = docEle.getElementsByTagName("directorfilms");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                //get the employee element
                Element el = (Element) nl.item(i);
                String director = getTextValue(el, "dirname");
                //parseFilms(docEle, director);
            }
        }
    }
    
    
    private void parseFilms() {
    	Element docEle = dom.getDocumentElement();
    	NodeList n2 = docEle.getElementsByTagName("film");
    	
    	if (n2 != null && n2.getLength() > 0) {
            for (int i = 0; i < n2.getLength(); i++) {
            	Element el = (Element) n2.item(i);
            	String title = getTextValue(el, "t") != null ? getTextValue(el, "t"): getTextValue(el, "altt");
                int year = getIntValue(el, "year");
                String genre = getTextValue(el, "cat") != null ? genres.get(getTextValue(el, "cat")) : null;
                String director = getTextValue(el, "dirn");
                String Id = getTextValue(el, "fid");
                Movie m = new Movie(title, Id, year, director, genre);
                myMovies.add(m);
                //System.out.println("Movie was added");
            }
    	}
    }



    /**
     * I take a xml element and the tag name, look for the tag and get
     * the text content
     * i.e for <employee><name>John</name></employee> xml snippet if
     * the Element points to employee node and tagName is name I will return John
     * 
     * @param ele
     * @param tagName
     * @return
     */
    private String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            try {
            textVal = el != null ? el.getFirstChild().getNodeValue(): null;
            }
            catch (java.lang.NullPointerException e) {return textVal;}
            
        }
    	
        return textVal;
    	
    }

    /**
     * Calls getTextValue and returns a int value
     * 
     * @param ele
     * @param tagName
     * @return
     */
    private int getIntValue(Element ele, String tagName) {
        //in production application you would catch the exception
    	try{
        return Integer.parseInt(getTextValue(ele, tagName));
        }catch (java.lang.NumberFormatException e) {
        	return 0;
        }
    }

    /**
     * Iterate through the list and print the
     * content to console
     */
    private void printData() {

        System.out.println("No of Employees '" + myMovies.size() + "'.");

        Iterator<Movie> it = myMovies.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
        System.out.println(myMovies.size());
    }
    
    private void CreateGenreTable() {
    	genres.put("Susp", "Thiller");
    	genres.put("CnR", "Crime");
    	genres.put("Dram", "Drama");
    	genres.put("West", "Western");
    	genres.put("Myst", "Mystery");
    	genres.put("S.F.", "Sci-Fi");
    	genres.put("Advt", "Adventure");
    	genres.put("Horr", "Horror");
    	genres.put("Romt", "Romance");
    	genres.put("Comd", "Comedy");
    	genres.put("Musc", "Musical");
    	genres.put("Docu", "Documentary");
    	genres.put("Porn", "Adult");
    	genres.put("Biop", "Biography");
    	genres.put("TV", "TV show");
    	genres.put("TVs", "TV series");
    	genres.put("TVm", "TV miniseries");
    }

    
    public void executeMovieParser() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        //create an instance
        CreateGenreTable();
        //call run example
       // dpe.runExample();
        parseXmlFile();
        parseFilms();
        BatchInsertMovies batch = new BatchInsertMovies(getMovieList());
        batch.executeBatch();
    }
    
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Parser dpe = new Parser();
        dpe.executeMovieParser();
    }

}
