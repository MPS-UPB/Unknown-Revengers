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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

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
	
	public static void parsareXML(){
		String xmlExample = "<Document image='3-sizes.tif' direction='descending'><TextBlock left='13' right='1089' top='26' bottom='109'><TextLine left='13' right='1089' top='26' bottom='109'><String>Nato</String><String>setzt</String></TextLine></TextBlock></Document>";
	}
}
