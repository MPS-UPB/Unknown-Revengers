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

import static org.joox.JOOX.$;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.joox.Match;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import tree.GenericTree;
import tree.GenericTreeNode;
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

	public String construct_xml(GenericTree<Element> InputTree) throws TransformerException {
		String result_xml = null;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Document doc = docBuilder.newDocument();
		Element root = InputTree.getRoot().getData();
		doc.appendChild(doc.createElement(root.text));
		addElements(doc, InputTree.getRoot());
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(result_xml);
		transformer.transform(source, result);
		return result_xml;
	}

	public void addElements(Document doc, GenericTreeNode<Element> Node) {
		List<GenericTreeNode<Element>> children = Node.getChildren();
		Iterator<GenericTreeNode<Element>> it =  children.iterator();
		while(it.hasNext()) {
			GenericTreeNode<Element> nextNode = it.next();
			Element nextElement = nextNode.getData();
			String string = "";
			if(nextElement.text != "") {
				string = "<String>" + nextElement.text + "</String>";
			}
			$(doc).find(nextElement.text).append("<" +  nextElement.type + " bottom= " + nextElement.bottom + " top= " + nextElement.top
					+ " right= " + nextElement.right + " left= " + nextElement.left+ "\">" + string +  "</" + nextElement.type +">");

			addElements(doc, nextNode);
		}
	}

	public static GenericTree<Element> parseXML(){
        int i, j;
        GenericTree<Element> newTree = new GenericTree<Element>();
        
		String xmlExample = "<Document image='3-sizes.tif' direction='descending'><TextBlock left='13' right='1089' top='26' bottom='109'><TextLine left='13' right='1089' top='26' bottom='109'><String>Nato</String><String>setzt</String></TextLine></TextBlock></Document>";
		InputStream xmlStream = null;
		
		// Creare stream XML pentru a crea obiectul JOOX
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

        // Parsare XML
		Match documentRoot = $(result).first();
		
		// Parseaza XML-ul si intoarce
		GenericTreeNode<Element> rootDocument = parseXMLRow(documentRoot);
		
		// Creaza arbore din structura de noduri
		newTree.setRoot(rootDocument);
		
		return newTree;
	}
	
	// Parseaza XML-ul folosind DFS si in acelasi timp creeaza arborele
	public static GenericTreeNode<Element> parseXMLRow(Match currentMatch){
		int i;
		
		// Suntem in frunza
		if(currentMatch.children().size() == 0)
		{			
			Element new_element = new Element(currentMatch.tag(), currentMatch.content());
			return new GenericTreeNode<Element>(new_element);
		}
	
		// Cream nod parinte
		Element rootElement = new Element(currentMatch.tag(), currentMatch.content());
		GenericTreeNode<Element> parentTreeNode = new GenericTreeNode<Element>(rootElement);
		
		// Parsam copiii
		for(i=0; i < currentMatch.children().size(); i++){
			Match textLineElement = currentMatch.child(i);
			GenericTreeNode<Element> newTreeNode = parseXMLRow(textLineElement);
			parentTreeNode.addChild(newTreeNode);
		}
		
		// Intoarcem nodul parinte
		return parentTreeNode;
	}
}
