package element_actions;

import java.lang.annotation.ElementType;
import java.util.List;

import gui.GElement;
import parser.LayoutParser;
import parser.LayoutParserTreeElement;
import tree.GenericTreeNode;
/**
 * Create and add to XML tree new GenericTreeNode containing a ComposedBlock type LayoutParserTreeElement
 * 
 * @author Unknown-Revengers
 *
 */
public class PageNumberBlock {
	
	private final GElement panel;

	private final LayoutParser layoutParser;

	/**
	 * Constructor.
	 * 
	 * @param GElement panel
	 * @param LayoutParser lp
	 */
	public PageNumberBlock(GElement panel, LayoutParser lp) {
		this.panel = panel;
		this.layoutParser = lp;
		
		this.addComposedBlock();
		
	}
	/**
	 * Create and add to XMLTree a new GenericTreeNode<LayoutParserTreeElement> 
	 * 
	 * @return void
	 */
	public void addComposedBlock() {
		
		GenericTreeNode<LayoutParserTreeElement> elementNode = panel.element;
		LayoutParserTreeElement element = elementNode.getData();

		String elementText = "";
		
		switch(element.elementType.toString()) {
		case "TextBlock" :
			elementText = element.text;
			break;
		case "TextLine" :
			GenericTreeNode<LayoutParserTreeElement> parent = elementNode.getParent();
			elementText =  parent.getData().text;
			
		}
		
		// Create content string of new LayoutParserTreeElement
		String composedBlockText = "<ComposedBlock type=\"page_number\">" + "<TextBlock top=\"" + element.top + "\" " + "right=\""+ element.right
				+ "\" " + "left=\""+ element.left + "\" " + "bottom=\""+ element.bottom + "\"/>" + elementText + "</TextBlock>" + "</ComposedBlock>";
		LayoutParserTreeElement newElement = new LayoutParserTreeElement("ComposedBlock", composedBlockText, element.top, element.bottom,element.right, element.left,element.image);
		
		// Creade new GenericTreeNode with newElement data
		GenericTreeNode<LayoutParserTreeElement> composedBlock = new GenericTreeNode<LayoutParserTreeElement>(newElement);
		
		// Add new composedVBlock GenericTreeNode to root
		GenericTreeNode<LayoutParserTreeElement> root = layoutParser.XMLTree.getRoot();
		root.addChild(composedBlock);
			
	}
}
