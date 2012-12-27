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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
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

public class LayoutParser {

	// Pagina va fi tinuta intr-un arbore
	GenericTree<Element1> XMLTree;
	String xmlPath;
	String imagePath;
	String direction;

	/**
	 * Constructorul clasei
	 * 
	 * Reprezinta datele din fisierul de layout in memorie
	 * 
	 * @param String xmlPath
	 *   Aici va fi retinuta calea catre XML. Calea este absoluta.
	 */
	public LayoutParser(String xmlPath) {
		String xmlExample = "";
		this.xmlPath      = xmlPath;
		
		// Citeste continutul XML-ului
		try {
			xmlExample = LayoutParser.readFile(xmlPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Creaza arborele care va tine minte structura paginii
		this.XMLTree = this.parseXML(xmlExample);

		//System.out.println(this.XMLTree.getRoot().getChildren());
	}

	/**
	 * Parseaza arborele dat ca parametru si construieste XML-ul 
	 * de layout din acesta
	 * 
	 * 
	 * @param InputTree
	 *   Arborele care contine informatiile despre pagina
	 *   
	 * @return string
	 *   Returneaza XML-ul rezultat din parsarea arborelui ce va
	 *   contine informatii despre pagina
	 *   
	 * @throws TransformerException
	 */
	public String construct_xml(GenericTree<Element1> InputTree)
			throws TransformerException {
		String result_xml = null;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		// Creaza noul document
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement(InputTree.getRoot().toString());

		// Adauga radacina arborelui
		doc.appendChild(rootElement);

		// Parseaza si creaza tot arborele
		doc = addElements(doc, rootElement, InputTree.getRoot());

		// Returneaza XML-ul sub forma de String din obiectul de tip DOM
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		transformer.transform(source, result);
		result_xml = sw.toString();

		return result_xml;
	}

	/**
	 * In aceasta metoda a fost implementata logica de parsare a arborelui
	 * pentru a construi XML-ul
	 * 
	 * @param doc1
	 * 	 Arborele care este construit pentru a crea din el XML-ul
	 * 
	 * @param currentElement
	 *   Elementul curent din arborele din care se va crea XML-ul 
	 *   in care ne aflam 
	 * 
	 * @param Node
	 *   Nodul curent din arborele in care e retinuta logica paginii
	 * 
	 * @return Document
	 *   Intoarce arborele din care se va contstrui XML-ul
	 *   
	 * @throws TransformerException
	 */
	public Document addElements(Document doc1, Element currentElement,
			GenericTreeNode<Element1> Node) throws TransformerException {
		Element child;

		// Gaseste copiii nodului curent
		List<GenericTreeNode<Element1>> children = Node.getChildren();
		Iterator<GenericTreeNode<Element1>> it = children.iterator();

		// Itereaza prin fiecare copil
		while (it.hasNext()) {
			GenericTreeNode<Element1> childNode = it.next();
			Element1 childElement = childNode.getData();

			if (childElement.text != "" && childElement.toString() == "String") {
				// Este frunza
				child = doc1.createElement(childElement.toString());
				child.appendChild(doc1.createTextNode(childElement.text
						.toString()));
				currentElement.appendChild(child);
			} else {
				// Creaza elementul
				child = doc1.createElement(childElement.toString());

				// Adauga atributele
				Attr bottom = doc1.createAttribute("bottom");
				bottom.setValue(Integer.toString(childElement.bottom));
				child.setAttributeNode(bottom);
				Attr top = doc1.createAttribute("top");
				top.setValue(Integer.toString(childElement.top));
				child.setAttributeNode(top);
				Attr right = doc1.createAttribute("right");
				right.setValue(Integer.toString(childElement.right));
				child.setAttributeNode(right);
				Attr left = doc1.createAttribute("left");
				left.setValue(Integer.toString(childElement.left));
				child.setAttributeNode(left);

				// Adauga elementul la arbore
				currentElement.appendChild(child);
			}

			// Merge mai jos in arbore
			addElements(doc1, child, childNode);
		}

		return doc1;
	}

	// Citeste XML-ul dintr-un fisier primit ca parametru
	
	/**
	 * Citeste XML-ul dintr-un fisier primit ca parametru
	 * 
	 * @param path
	 * 	 Calea catre fisier
	 * 
	 * @return
	 *   Returneaza XML-ul intr-un String
	 * @throws IOException
	 */
	public static String readFile(String path) throws IOException {
		FileInputStream stream = new FileInputStream(new File(path));
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
					fc.size());
			
			return Charset.defaultCharset().decode(bb).toString();
		} finally {
			stream.close();
		}
	}

	// Parseaza XML-ul primit ca string si returneaza un arbore de tip
	// GenericTree
	
	/**
	 * Parseaza XML-ul primit ca String si returneaza un arbore de tip
	 * GenericTree
	 * 
	 * @param string layoutXML
	 *   XML-ul ce contine informatii despre pagina
	 *   
	 * @return GenericTree<Element1>
	 *   Arborele ce va contine informatii despre pagina dupa ce a parsat 
	 *   XML-ul
	 */
	public GenericTree<Element1> parseXML(String layoutXML) {
		GenericTree<Element1> newTree  = new GenericTree<Element1>();

		Document result 			   = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		InputSource source 			   = new InputSource(new StringReader(layoutXML));

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
		
		// Salveaza imaginea si directia din tag-ul Documentului
		saveImageFromXML(documentRoot);
		
		// Parseaza XML-ul si intoarce
		GenericTreeNode<Element1> rootDocument = parseXMLRow(documentRoot);

		// Creaza arbore din structura de noduri
		newTree.setRoot(rootDocument);

		return newTree;
	}
	
	/**
	 * Parseaza XML-ul folosing DFS si in acelasi timp creaza arborele
	 * 
	 * @param currentMatch
	 *   Reprezinta elementul curent
	 * 
	 * @return GenericTreeNode<Element1>
	 * 	 Returneaza nodul curent
	 */
	public GenericTreeNode<Element1> parseXMLRow(Match currentMatch) {
		int i, top = -1, bottom, right, left;

		// Parsam atributele ca sa nu dea eroare
		if (currentMatch.attr("top") != null) {
			top = Integer.parseInt(currentMatch.attr("top"));
		} else {
			top = -1;
		}

		if (currentMatch.attr("bottom") != null) {
			bottom = Integer.parseInt(currentMatch.attr("bottom"));
		} else {
			bottom = -1;
		}

		if (currentMatch.attr("left") != null) {
			left = Integer.parseInt(currentMatch.attr("left"));
		} else {
			left = -1;
		}

		if (currentMatch.attr("right") != null) {
			right = Integer.parseInt(currentMatch.attr("right"));
		} else {
			right = -1;
		}

		// Suntem in frunza
		if (currentMatch.children().size() == 0) {
			Element1 new_element = new Element1(currentMatch.tag(),
					currentMatch.content(), top, bottom, right, left,
					currentMatch.attr("image"));
			return new GenericTreeNode<Element1>(new_element);
		}

		// Cream nod parinte
		Element1 rootElement = new Element1(currentMatch.tag(),
				currentMatch.content(), top, bottom, left, right,
				currentMatch.attr("image"));
		GenericTreeNode<Element1> parentTreeNode = new GenericTreeNode<Element1>(
				rootElement);

		// Parsam copiii
		for (i = 0; i < currentMatch.children().size(); i++) {
			Match textLineElement = currentMatch.child(i);
			GenericTreeNode<Element1> newTreeNode = parseXMLRow(textLineElement);
			parentTreeNode.addChild(newTreeNode);
		}

		// Intoarcem nodul parinte
		return parentTreeNode;
	}
	
	/** 
	 *
	 * @return String
	 * 	 Returneaza calea catre imagine
	 */
	public String getImagePath(){
		return this.imagePath;
	}

	/**
	 * Muta un nod de la un parinte la altul intr-un arbore
	 * 
	 * @param movingNode
	 * 	 Nodul mutat
	 * @param toParentNode
	 *   Nodul parinte destinatie
	 *   
	 * @return boolen
	 *   True daca operatia a fost indeplinita cu succes, sau false altfel
	 */
	public boolean moveChildToParent(GenericTreeNode<Element1> movingNode,
			GenericTreeNode<Element1> toParentNode) {
		int i;

		// Gaseste parintele nodului care va fi mutat
		GenericTreeNode<Element1> parent = movingNode.getParent();

		// Adauga nodul mutat la noul nod
		toParentNode.addChild(movingNode);

		// Sterge nodul mutat de la vechiul parinte
		List<GenericTreeNode<Element1>> parentChildrenList = parent
				.getChildren();
		for (i = 0; i < parentChildrenList.size(); i++) {
			if (parentChildrenList.get(i) == movingNode) {
				// Found child
				parent.removeChildAt(i);
				break;
			}
		}

		return true;
	}
	
	/**
	 * Salveaza calea catre imagine din radacina XML-ului.
	 * Functia va functiona cum trebuie chiar daca calea XML-ului
	 * este relativa sau absoluta
	 * 
	 * @param documentRoot
	 * 	 Radacina documentului
	 *   
	 */
	private void saveImageFromXML(Match documentRoot){
    	if (documentRoot.attr("image") != null) {
    		if(xmlPath.contains("\\")){
    			String auxImagePath = documentRoot.attr("image");
    			int subpathIndex = this.xmlPath.lastIndexOf("\\") + 1;
    			this.imagePath = this.xmlPath.substring(0, subpathIndex) + auxImagePath; 
    		} else {
    			this.imagePath = documentRoot.attr("image");
    		}
    	}
    	
    	if (documentRoot.attr("direction") != null) {
    		this.direction = documentRoot.attr("direction");
    	}	  	
	}
}
