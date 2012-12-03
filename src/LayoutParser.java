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

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
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
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import tree.GenericTree;
import tree.GenericTreeNode;
//import Element;
public class LayoutParser {

	//TODO structura pentru a retine organizarea fisierului.. cred ca ar merge un arbore.
	GenericTree<Element1> XMLTree = new GenericTree<Element1>();

	/**
	 * TODO
	 * 
	 * Reprezinta datele din fisierul de layout in memorie
	 * 
	 * @param layoutXML
	 */
	public LayoutParser(String layoutXML){

	}

	public String construct_xml(GenericTree<Element1> InputTree) throws TransformerException {
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
		Element rootElement = doc.createElement(InputTree.getRoot().toString());
		doc.appendChild(rootElement);
		doc  = addElements(doc, InputTree.getRoot());
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		transformer.transform(source, result);
		result_xml = sw.toString();

		return result_xml;
	}

	public Document addElements(Document doc1, GenericTreeNode<Element1> Node) throws TransformerException {
		List<GenericTreeNode<Element1>> children = Node.getChildren();
		Iterator<GenericTreeNode<Element1>> it =  children.iterator();
		Document doc = doc1;
		Element root = doc1.getDocumentElement();
		Element current_element = root;
		while(it.hasNext()) {
			GenericTreeNode<Element1> nextNode = it.next();
			Element1 nextElement = nextNode.getData();
			if(nextElement.text != "" && nextElement.toString() == "String") {
				Element current_string = doc.createElement(nextElement.toString());
				current_string.appendChild(doc.createTextNode(nextElement.text.toString()));
				current_element.appendChild(current_string);
			}
			else {

				Element child = doc.createElement(nextElement.toString());
				Attr bottom = doc.createAttribute("bottom");
				bottom.setValue(Integer.toString(nextElement.bottom));
				child.setAttributeNode(bottom);
				Attr top = doc.createAttribute("top");
				top.setValue(Integer.toString(nextElement.top));
				child.setAttributeNode(top);
				Attr right = doc.createAttribute("right");
				right.setValue(Integer.toString(nextElement.right));
				child.setAttributeNode(right);
				Attr left = doc.createAttribute("left");
				left.setValue(Integer.toString(nextElement.left));
				child.setAttributeNode(left);
				current_element.appendChild(child);
				current_element= child;
			}
			addElements(doc, nextNode);
		}

		return doc;
	}

	// Parseaza XML-ul primit ca string si returneaza un arbore de tip GenericTree
	public GenericTree<Element1> parseXML(String layoutXML){
		GenericTree<Element1> newTree = new GenericTree<Element1>();

		Document result = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		InputSource source = new InputSource(new StringReader(layoutXML));

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
		GenericTreeNode<Element1> rootDocument = parseXMLRow(documentRoot);

		// Creaza arbore din structura de noduri
		newTree.setRoot(rootDocument);

		return newTree;
	}

	// Parseaza XML-ul folosind DFS si in acelasi timp creeaza arborele
	public GenericTreeNode<Element1> parseXMLRow(Match currentMatch){
		int i, top = -1, bottom, right, left;

		// Parsam atributele ca sa nu dea eroare
		if(currentMatch.attr("top") != null){
			top = Integer.parseInt(currentMatch.attr("top"));
		} else {
			top = -1;
		}

		if(currentMatch.attr("bottom") != null){
			bottom = Integer.parseInt(currentMatch.attr("bottom"));
		} else {
			bottom = -1;
		}

		if(currentMatch.attr("left") != null){
			left = Integer.parseInt(currentMatch.attr("left"));
		} else {
			left = -1;
		}

		if(currentMatch.attr("right") != null){
			right = Integer.parseInt(currentMatch.attr("right"));
		} else {
			right = -1;
		}

		// Suntem in frunza
		if(currentMatch.children().size() == 0)
		{
			Element1 new_element = new Element1(currentMatch.tag(), currentMatch.content(),
					top, bottom, right , left, currentMatch.attr("image"));
			return new GenericTreeNode<Element1>(new_element);
		}

		// Cream nod parinte
		Element1 rootElement = new Element1( currentMatch.tag(), currentMatch.content(),
				top, bottom, left, right, currentMatch.attr("image"));
		GenericTreeNode<Element1> parentTreeNode = new GenericTreeNode<Element1>(rootElement);

		// Parsam copiii
		for(i = 0; i < currentMatch.children().size(); i++){
			Match textLineElement = currentMatch.child(i);
			GenericTreeNode<Element1> newTreeNode = parseXMLRow(textLineElement);
			parentTreeNode.addChild(newTreeNode);
		}

		// Intoarcem nodul parinte
		return parentTreeNode;
	}

	// Muta un nod de la un parinte la altul
	public boolean moveChildToParent(GenericTreeNode<Element1> movingNode, GenericTreeNode<Element1> toParentNode){
		int i;

		// Gaseste parintele nodului care va fi mutat
		GenericTreeNode<Element1> parent = movingNode.getParent();

		// Adauga nodul mutat la noul nod
		toParentNode.addChild(movingNode);

		// Sterge nodul mutat de la vechiul parinte
		List<GenericTreeNode<Element1>> parentChildrenList = parent.getChildren();
		for(i = 0; i < parentChildrenList.size(); i++){
			if(parentChildrenList.get(i) == movingNode){
				// Found child
				parent.removeChildAt(i);
				break;
			}
		}

		return true;
	}
}
