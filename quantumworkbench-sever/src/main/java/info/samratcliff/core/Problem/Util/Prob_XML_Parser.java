/**
 *
 */
package info.samratcliff.core.Problem.Util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Sam
 */
public class Prob_XML_Parser {
    public static void main(String[] args) {
        Prob_XML_Parser p = new Prob_XML_Parser("Problems.xml");
        HashMap<String, ProblemTag> probmap = p.parseDocument();
        Iterator<String> iter = probmap.keySet().iterator();
        while (iter.hasNext()) {
            String Name = iter.next();
            String Class = probmap.get(Name).Def_File;
            System.out.println("Name : " + Name + " Def File : " + Class);
        }

    }

    private HashMap<String, ProblemTag> probmat;

    private Document dom;

    public Prob_XML_Parser(String filename) {
        probmat = new HashMap<String, ProblemTag>();
        parseXmlFile(filename);
    }

    private ProblemTag getSearchEngine(Element empEl) {

        // for each <employee> element get text or int values of
        // name ,id, age and name
        String Name = getTextValue(empEl, "Name");
        String DefFile = getTextValue(empEl, "DefFile");
        String Desc = getTextValue(empEl, "Desc");

        // Create a new Employee with the value read from the xml nodes
        ProblemTag e = new ProblemTag(Name, DefFile, Desc);

        return e;
    }

    private String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if ((nl != null) && (nl.getLength() > 0)) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }

    public HashMap<String, ProblemTag> parseDocument() {
        // get the root elememt
        Element docEle = dom.getDocumentElement();

        // get a nodelist of <employee> elements
        NodeList nl = docEle.getElementsByTagName("prob");
        if ((nl != null) && (nl.getLength() > 0)) {
            for (int i = 0; i < nl.getLength(); i++) {

                // get the employee element
                Element el = (Element) nl.item(i);

                // get the Employee object
                ProblemTag e = getSearchEngine(el);

                // add it to list
                probmat.put(e.Name, e);
            }
        }
        return probmat;
    }

    private void parseXmlFile(String filename) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // Validator validator = schema.newValidator();
        try {

            // Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            // parse using builder to get DOM representation of the XML file
            dom = db.parse(this.getClass().getClassLoader().getResourceAsStream(filename));

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
