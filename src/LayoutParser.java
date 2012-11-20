/**
 * TODO
 * Parseaza fisierul de layout XML si reprezinta datele in memorie.
 * 
 * 1) Metode pentru a naviga prin fisierul de layout XML.
 *      - nextElement()
 *      - prevElement()
 * 
 * 2) Metode pentru a uni 2 elemente.
 *      - mergeElement()
 * 
 * 3) Medoda pentru a salva datele intr-un XML:
 * 	    - saveLayout()
 * 
 * @author Unknown-Revengers
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.joox.JOOX.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.joox.Match;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import tree.GenericTree;
public class LayoutParser {

	//TODO structura pentru a retine organizarea fisierului.. cred ca ar merge un arbore.
	GenericTree<Element> XMLTree = new GenericTree<Element>();

	/**
	 * TODO
	 * 
	 * Reprezinta datele din fisierul de layout in memorie
	 * 
	 * @param layoutXML
	 */
	public LayoutParser(String layoutXML){

	}

	public String construct_xml(GenericTree<Element> InputTree) {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// root elements
		Document doc = docBuilder.newDocument();
		Element root = InputTree.getRoot().getData();

		doc.appendChild(doc.createElement(root.text));

		return "";
	}
	
	public static void parseXML(){
		String xmlExample = "<Document image='3-sizes.tif' direction='descending'><TextBlock left='13' right='1089' top='26' bottom='109'><TextLine left='13' right='1089' top='26' bottom='109'><String>Nato</String><String>setzt</String></TextLine></TextBlock></Document>";
		InputStream xmlStream = null;
		try {
			xmlStream = new ByteArrayInputStream(xmlExample.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		Document result = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        InputSource source = new InputSource(new StringReader(xmlExample));

        try {
            result = factory.newDocumentBuilder().parse(source);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
		
        int i, j;
		Match documentRoot = $(result).first();
		Match textBlockElements = documentRoot.children();
		for(i = 0; i < textBlockElements.size(); i++){
			Match textLineElement = textBlockElements.child(i);
		    Match stringElements  = textLineElement.children();
		    for(j = 0; j < stringElements.size(); j++){
		    	System.out.println(stringElements.content(j));
		    }
		}
		
		//System.out.println(textBlockChildren);	
	}
}
