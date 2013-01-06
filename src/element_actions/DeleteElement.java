package element_actions;

import gui.GElement;
import gui.LayoutGUI;
import gui.VisibleElements;
import parser.LayoutParser;
import parser.LayoutParserTreeElement;
import tree.GenericTreeNode;

/**
 * Delete selected element from XMLTree and reload elements.
 * 
 * @author Unknown-Revengers
 *
 */
public class DeleteElement {
	
	private final GElement panel;

	private final LayoutGUI gui;

	/**
	 * Constructor.
	 * 
	 * @param GElement panel
	 * @param LayoutGUI gui
	 */
	public DeleteElement(GElement panel, LayoutGUI gui) {
		
		this.panel = panel;
		this.gui = gui;
		this.DeleteElement();
		
	}
	/**
	 * Function delete selected element given as constructor parameter and reload elements.
	 * 
	 * @return void
	 */
	public void DeleteElement() {
		
		GenericTreeNode<LayoutParserTreeElement> elementNode = panel.element;
		gui.layoutParser.XMLTree.delete(elementNode.getData());
		String type = panel.element.getData().elementType.toString();
		
		if (type.compareTo("TextBlock") == 0) {
			this.gui.loadElements(VisibleElements.S_BLOCK);
		} else if (type.compareTo("TextLine") == 0) {
			this.gui.loadElements(VisibleElements.S_LINE);
		}
		
	}
}
