import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CastParser {
    List<Stars> myStars;
    Document dom;

    public CastParser() {
        //create a list to hold the employee objects
        myStars = new ArrayList<Stars>();

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
            dom = db.parse("../Fabflix/project3data/casts124.xml");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
  
    
    private void parseFilms() {
    	Element docEle = dom.getDocumentElement();
    	NodeList n2 = docEle.getElementsByTagName("m");
    	
    	if (n2 != null && n2.getLength() > 0) {
            for (int i = 0; i < n2.getLength(); i++) {
            	Element el = (Element) n2.item(i);
            	String title = getTextValue(el, "t");
                //int year = getIntValue(el, "year");
                String id = getTextValue(el, "f"); 
                String Name = getTextValue(el, "a");
                Stars s = new Stars(id, Name, title);
                myStars.add(s);
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

        System.out.println("No of Employees '" + myStars.size() + "'.");

        Iterator<Stars> it = myStars.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
        System.out.println(myStars.size());
    }
    
    
    public List<Stars> getCastList(){
    		return myStars;
    }
    
    public void executeCastParser() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        //create an instance
        //call run example
       // dpe.runExample();
        parseXmlFile();
        parseFilms();
        BatchInsertCast batch = new BatchInsertCast(getCastList());
        batch.executeBatch();
    }

    public static void main(String[] args)  throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        //create an instance
        CastParser cpe = new CastParser();
        cpe.executeCastParser();
        //call run example
        //cpe.runExample();
    }
}