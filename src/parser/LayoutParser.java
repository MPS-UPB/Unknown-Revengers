package parser;

import static org.joox.JOOX.$;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import layout.ErrorMessage;

import org.joox.Match;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import tree.GenericTree;
import tree.GenericTreeNode;

/**
 * Parseaza fisierul de layout XML si reprezinta datele in memorie.
 * 
 * @author Unknown-Revengers
 */
public class LayoutParser {

	/**
	 * Pagina va fi tinuta intr-un arbore
	 */
	public GenericTree<LayoutParserTreeElement> XMLTree;

	/**
	 * Calea catre xml.
	 */
	public String xmlPath;

	/**
	 * Calea catre imagine.
	 */
	private String imagePath;

	/**
	 * Ascending sau descending.
	 */
	public Direction direction;

	/**
	 * Constructorul clasei
	 * 
	 * Reprezinta datele din fisierul de layout in memorie
	 * 
	 * @param xmlPath Aici va fi retinuta calea catre XML. Calea este absoluta.
	 */
	public LayoutParser(String xmlPath) {

		this.xmlPath = xmlPath;

		// Parse the input file.
		this.parse();
	}

	/**
	 * Parse the input file and rebuild tree.
	 */
	public void parse() {
		String xmlExample = "";

		// Citeste continutul XML-ului
		try {
			xmlExample = this.readFile(xmlPath);
		} catch (IOException e) {
			ErrorMessage.show("EROARE: XML-ul nu a fost citit cum trebuie",
					false);
			e.printStackTrace();
		}

		// Creaza arborele care va tine minte structura paginii.
		this.XMLTree = this.parseXML(xmlExample);
	}

	/**
	 * Parseaza arborele dat ca parametru si construieste XML-ul de layout din
	 * acesta.
	 * 
	 * @return string Returneaza XML-ul rezultat din parsarea arborelui ce va
	 *         contine informatii despre pagina.
	 * 
	 * @throws TransformerException Exceptie pentru Transformer.
	 */
	public String constructXml() throws TransformerException {
		String result_xml = null;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;

		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			ErrorMessage.show(
					"EROARE: A fost o eroare cand a fost creat documentul",
					false);
		}

		// Creaza noul document
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement(XMLTree.getRoot().toString());

		// Sets the direction attribute
		Attr directionTag = doc.createAttribute("direction");
		directionTag.setValue(this.direction.toString().toLowerCase());
		rootElement.setAttributeNode(directionTag);

		// Sets the image attribute
		Attr imageTag = doc.createAttribute("image");
		imageTag.setValue(getImagePath());
		rootElement.setAttributeNode(imageTag);

		// Adauga radacina arborelui
		doc.appendChild(rootElement);

		// Parseaza si creaza tot arborele
		doc = addElements(doc, rootElement, XMLTree.getRoot());

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
	 * Salveaza XML-ul intr-un fisier
	 * 
	 * @param withDialog Daca e true, XML-ul va fi salvat intr-un fisier ales
	 *            din dialog Daca e false, XML-ul va fi salvat intr-un fisier
	 *            temporar
	 * 
	 * @return Calea catre fisier daca fisierul a fost salvat cu succes, sau
	 *         null contrar
	 * 
	 */
	public String saveXML(boolean withDialog) {
		String fileName = "";
		FileWriter fstream = null;
		File tempFile = null;

		if (withDialog == true) {
			fileName = getSavedFileName();

			if (fileName == null) {
				return null;
			}

			try {
				fstream = new FileWriter(fileName);
			} catch (IOException e1) {
				ErrorMessage
						.show("Eroare cand a fost deschis stream-ul de scriere");
				e1.printStackTrace();
				return null;
			}

		} else {
			try {
				tempFile = File.createTempFile("temp", ".xml");
				fileName = tempFile.getAbsolutePath();

				fstream = new FileWriter(tempFile);
			} catch (IOException e) {
				ErrorMessage
						.show("Eroare cand a fost deschis stream-ul de scriere");
				e.printStackTrace();
			}
		}

		boolean result = writeToFile(fstream);

		if (withDialog) {
			if (result == true) {
				JOptionPane.showMessageDialog(null,
						"Modificarile au fost salvate cu succes!");
			} else {
				JOptionPane
						.showMessageDialog(null,
								"A fost o eroare in timpul salvarii. Va rog sa incercati din nou!");
			}
		}

		return fileName;
	}

	/**
	 * Scrie in fisier pentru salvare.
	 * 
	 * @param fstream Fisierul in care se va scrie.
	 * 
	 * @return boolean True daca totul merge bine.
	 */
	private boolean writeToFile(FileWriter fstream) {
		BufferedWriter out = new BufferedWriter(fstream);

		try {
			out.write(constructXml());
		} catch (IOException e1) {
			ErrorMessage.show(
					"Eroare cand a fost scris fisierul de modificari", false);
			e1.printStackTrace();
			return false;
		} catch (TransformerException e1) {
			ErrorMessage.show(
					"Eroare in momentul construirii XML-ului de output", false);
			return false;
		}

		// Inchide fisierul de output
		try {
			out.close();
		} catch (IOException e1) {
			ErrorMessage.show("Eroare cand a fost inchis stream-ul de scriere",
					false);
			return false;
		}

		try {
			out.close();
		} catch (IOException e) {
			ErrorMessage.show("Eroare cand a fost inchis stream-ul de scriere",
					false);
			return false;
		}

		return true;
	}

	/**
	 * Afiseaza un dialog de salvare
	 * 
	 * @return Calea absoluta catre fisierul in care se va salva XML-ul
	 */
	private String getSavedFileName() {
		FileDialog fileDialog = new FileDialog(new Frame(), "Save",
				FileDialog.SAVE);
		fileDialog.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		});

		fileDialog.setFile("untitled.xml");
		fileDialog.setVisible(true);

		if (fileDialog.getFile() == null) {
			return null;
		} else {
			return (fileDialog.getDirectory() + fileDialog.getFile());
		}
	}

	/**
	 * In aceasta metoda a fost implementata logica de parsare a arborelui
	 * pentru a construi XML-ul
	 * 
	 * @param doc Arborele care este construit pentru a crea din el XML-ul
	 * @param currentElement Elementul curent din arborele din care se va crea
	 *            XML-ul in care ne aflam
	 * @param Node Nodul curent din arborele in care e retinuta logica paginii
	 * 
	 * @return Document Intoarce arborele din care se va contstrui XML-ul
	 * 
	 * @throws TransformerException
	 */
	private Document addElements(Document doc, Element currentElement,
			GenericTreeNode<LayoutParserTreeElement> Node)
			throws TransformerException {
		Element child;

		// Gaseste copiii nodului curent
		List<GenericTreeNode<LayoutParserTreeElement>> children = Node
				.getChildren();
		Iterator<GenericTreeNode<LayoutParserTreeElement>> it = children
				.iterator();

		// Itereaza prin fiecare copil
		while (it.hasNext()) {
			GenericTreeNode<LayoutParserTreeElement> childNode = it.next();
			LayoutParserTreeElement childElement = childNode.getData();

			if (childElement.text.isEmpty() == false
					&& childElement.toString().compareTo("String") == 0) {
				// Este frunza
				child = doc.createElement(childElement.toString());
				child.appendChild(doc.createTextNode(childElement.text
						.toString()));

			} else if (childElement.text.isEmpty() == true
					&& childElement.toString().compareTo("String") == 0) {

				child = doc.createElement(childElement.toString());
			} else {
				// Creaza elementul
				child = doc.createElement(childElement.toString());

				// Adauga atributele
				if (childElement.shouldOutputCoords()) {
					// Bottom
					Attr bottom = doc.createAttribute("bottom");
					bottom.setValue(Integer.toString(childElement.bottom));
					child.setAttributeNode(bottom);

					// Top
					Attr top = doc.createAttribute("top");
					top.setValue(Integer.toString(childElement.top));
					child.setAttributeNode(top);

					// Right
					Attr right = doc.createAttribute("right");
					right.setValue(Integer.toString(childElement.right));
					child.setAttributeNode(right);

					// Left
					Attr left = doc.createAttribute("left");
					left.setValue(Integer.toString(childElement.left));
					child.setAttributeNode(left);
				}

				// Is type point
				if (childElement.x != null) {
					Attr x = doc.createAttribute("x");
					x.setValue(childElement.x);
					child.setAttributeNode(x);
					Attr y = doc.createAttribute("y");
					y.setValue(childElement.y);
					child.setAttributeNode(y);
				}

				// Are atribut hasPage
				if (childElement.hasPage == true) {
					Attr hasPage = doc.createAttribute("type");
					hasPage.setValue("page_number");
					child.setAttributeNode(hasPage);
				}

				// Adauga elementul la arbore
				currentElement.appendChild(child);
			}

			currentElement.appendChild(child);

			// Merge mai jos in arbore
			addElements(doc, child, childNode);
		}

		return doc;
	}

	/**
	 * Citeste XML-ul dintr-un fisier primit ca parametru
	 * 
	 * @param path Calea catre fisier
	 * 
	 * @return Returneaza XML-ul intr-un String
	 * @throws IOException
	 */
	private String readFile(String path) throws IOException {
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

	/**
	 * Parseaza XML-ul primit ca String si returneaza un arbore de tip
	 * GenericTree
	 * 
	 * @param string layoutXML XML-ul ce contine informatii despre pagina
	 * 
	 * @return GenericTree<LayoutParserTreeElement> Arborele ce va contine
	 *         informatii despre pagina dupa ce a parsat XML-ul
	 */
	private GenericTree<LayoutParserTreeElement> parseXML(String layoutXML) {
		GenericTree<LayoutParserTreeElement> newTree = new GenericTree<LayoutParserTreeElement>();

		Document result = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		InputSource source = new InputSource(new StringReader(layoutXML));

		try {
			result = factory.newDocumentBuilder().parse(source);
		} catch (SAXException e) {
			ErrorMessage.show("Eroare SAX", false);
			e.printStackTrace();
		} catch (IOException e) {
			ErrorMessage.show("Eroare IOException", false);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			ErrorMessage.show("Eroare ParserConfigurationException", false);
			e.printStackTrace();
		}

		// Parsare XML
		Match documentRoot = $(result).first();

		// Salveaza imaginea si directia din tag-ul Documentului
		saveImageFromXML(documentRoot);

		// Parseaza XML-ul si intoarce
		GenericTreeNode<LayoutParserTreeElement> rootDocument = parseXMLRow(documentRoot);

		// Creaza arbore din structura de noduri
		newTree.setRoot(rootDocument);

		return newTree;
	}

	/**
	 * Parseaza XML-ul folosing DFS si in acelasi timp creaza arborele
	 * 
	 * @param currentMatch Reprezinta elementul curent
	 * 
	 * @return GenericTreeNode<LayoutParserTreeElement> Returneaza nodul curent
	 */
	private GenericTreeNode<LayoutParserTreeElement> parseXMLRow(
			Match currentMatch) {
		GenericTreeNode<LayoutParserTreeElement> parentTreeNode = null;
		int i;
		int top = -1;
		int bottom;
		int right;
		int left;

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

		if (currentMatch.tag().compareTo("Point") == 0) {
			LayoutParserTreeElement new_element = new LayoutParserTreeElement(
					LayoutParserTreeElement.ElementType.valueOf(currentMatch
							.tag().toUpperCase()), currentMatch.attr("x"),
					currentMatch.attr("y"));
			return new GenericTreeNode<LayoutParserTreeElement>(new_element);
		} else if (currentMatch.children().size() == 0) {
			// Suntem in frunza
			LayoutParserTreeElement new_element = new LayoutParserTreeElement(
					LayoutParserTreeElement.ElementType.valueOf(currentMatch
							.tag().toUpperCase()), currentMatch.content(), top,
					bottom,
					right, left, currentMatch.attr("image"));
			return new GenericTreeNode<LayoutParserTreeElement>(new_element);
		}

		// Cream nod parinte
		LayoutParserTreeElement rootElement = null;
		if (currentMatch.tag().compareTo("ComposedBlock") == 0) {
			if (currentMatch.attr("type").compareTo("page_number") == 0) {
				rootElement = new LayoutParserTreeElement(
						LayoutParserTreeElement.ElementType.valueOf(currentMatch
								.tag().toUpperCase()),
						true);
			} else {
				rootElement = new LayoutParserTreeElement(
						LayoutParserTreeElement.ElementType.valueOf(currentMatch
								.tag().toUpperCase()),
						false);
			}
		} else {
			rootElement = new LayoutParserTreeElement(
					LayoutParserTreeElement.ElementType.valueOf(currentMatch
							.tag().toUpperCase()),
					currentMatch.content(), top, bottom, right, left,
					currentMatch.attr("image"));

		}

		parentTreeNode = new GenericTreeNode<LayoutParserTreeElement>(
				rootElement);

		// Parsam copiii
		for (i = 0; i < currentMatch.children().size(); i++) {
			Match textLineElement = currentMatch.child(i);
			GenericTreeNode<LayoutParserTreeElement> newTreeNode = parseXMLRow(textLineElement);
			parentTreeNode.addChild(newTreeNode);
		}

		// Intoarcem nodul parinte
		return parentTreeNode;
	}

	/**
	 * Getter pentru calea imaginii.
	 * 
	 * @return String Returneaza calea catre imagine
	 */
	public String getImagePath() {
		return this.imagePath;
	}

	/**
	 * Muta un nod de la un parinte la altul intr-un arbore.
	 * 
	 * @param movingNode Nodul mutat
	 * @param toParentNode Nodul parinte destinatie
	 * 
	 * @return boolen True daca operatia a fost indeplinita cu succes, sau false
	 *         altfel
	 */
	public boolean moveChildToParent(
			GenericTreeNode<LayoutParserTreeElement> movingNode,
			GenericTreeNode<LayoutParserTreeElement> toParentNode) {

		int i;

		// Gaseste parintele nodului care va fi mutat
		GenericTreeNode<LayoutParserTreeElement> parent = movingNode
				.getParent();

		// Adauga nodul mutat la noul nod
		toParentNode.addChild(movingNode);

		// Sterge nodul mutat de la vechiul parinte
		List<GenericTreeNode<LayoutParserTreeElement>> parentChildrenList = parent
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
	 * Merge all children from one node to another and then remove the first
	 * node
	 * 
	 * @param movingNode Original node.
	 * @param newNode New node.
	 */
	public void mergeNodeIntoOtherNode(
			GenericTreeNode<LayoutParserTreeElement> movingNode,
			GenericTreeNode<LayoutParserTreeElement> newNode) {

		newNode.getData().left = Math.min(newNode.getData().left,
				movingNode.getData().left);
		newNode.getData().right = Math.max(newNode.getData().right,
				movingNode.getData().right);
		newNode.getData().top = Math.min(newNode.getData().top,
				movingNode.getData().top);
		newNode.getData().bottom = Math.max(newNode.getData().bottom,
				movingNode.getData().bottom);

		List<GenericTreeNode<LayoutParserTreeElement>> children = movingNode
				.getChildren();

		for (int i = 0; i < children.size(); i++) {
			this.moveChildToParent(children.get(i), newNode);
		}

		this.XMLTree.delete(movingNode.getData());
	}

	/**
	 * Salveaza calea catre imagine din radacina XML-ului. Functia va functiona
	 * cum trebuie chiar daca calea XML-ului este relativa sau absoluta
	 * 
	 * @param documentRoot Radacina documentului
	 * 
	 */
	private void saveImageFromXML(Match documentRoot) {
		if (documentRoot.attr("image") != null) {
			File filePath = new File(documentRoot.attr("image"));

			// Verifica daca path-ul este absolut.
			if (filePath.isAbsolute()) {
				this.imagePath = filePath.getAbsolutePath();
			} else {
				this.imagePath = (new File(this.xmlPath)).getParent() + "\\"
						+ documentRoot.attr("image");
			}
		}

		if (documentRoot.attr("direction") != null) {
			switch (documentRoot.attr("direction")) {
			case "ascending":
				this.direction = Direction.ASCENDING;
				break;

			case "descending":
				this.direction = Direction.DESCENDING;
				break;
			}
		}
	}
}
