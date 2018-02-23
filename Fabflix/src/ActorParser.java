
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

/**
 * @author Alext
 *
 */
public class ActorParser {
    List<Stars> myActors;
    Document dom;

    public ActorParser() {
        //create a list to hold the employee objects
        myActors = new ArrayList<Stars>();
    }

    public void runExample() {

        //parse the xml file and get the dom object
        parseXmlFile();

        //get each employee element and create a Employee object
        parseDocument();

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
            dom = db.parse("../Fabflix/project3data/actors63.xml");

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
        NodeList nl = docEle.getElementsByTagName("actor");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                //get the employee element
                Element el = (Element) nl.item(i);
                //String name = getTextValue(el, "stagename");
                //get the Employee object
                Stars a = getActor(el);
                //parseFilms(docEle, director);
                //add it to list
                myActors.add(a);
            }
        }
    }
    
    /**
     * I take an employee element and read the values in, create
     * an Employee object and return it
     * 
     * @param empEl
     * @return
     */
    private Stars getActor(Element empEl) {

        //for each <employee> element get text or int values of 
        //name ,id, age and name
        String name = getTextValue(empEl, "stagename");
        int birthYear = getIntValue(empEl, "dob");
        //int age = getIntValue(empEl, "Age");

        //String type = empEl.getAttribute("type");

        //Create a new Employee with the value read from the xml nodes
        Stars a = new Stars(name, birthYear);

        return a;
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
            catch (java.lang.NullPointerException e) {
            	return textVal;
            	}
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
    
    public List<Stars> getStarList(){
    		return myActors;
    }
    
    private void printData() {

        System.out.println("No of Employees '" + myActors.size() + "'.");

        Iterator<Stars> it = myActors.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
        System.out.println(myActors.size());
    }
    
    
    public void executeStarParser() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        //create an instance
        //call run example
       // dpe.runExample();
        parseXmlFile();
        parseDocument();
        BatchInsertStars batch = new BatchInsertStars(getStarList());
        batch.executeBatch();
    }
   

    
    public static void main(String[] args)throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        //create an instance
        ActorParser dpe = new ActorParser();
        //call run example
        dpe.executeStarParser();
        
        
    }
}

