package element_actions;

import gui.GElement;
import gui.LayoutGUI;
import parser.LayoutParserTreeElement;
import tree.GenericTreeNode;

/**
 * Delete selected element from XMLTree and reload elements.
 * 
 * @author Unknown-Revengers
 * 
 */
public class DeleteElement {

	private GElement panel;

	private LayoutGUI gui;

	/**
	 * Constructor.
	 * 
	 * @param panel Element panel.
	 * @param gui GUI.
	 */
	public DeleteElement(GElement panel, LayoutGUI gui) {
		this.panel = panel;
		this.gui = gui;
		this.Delete();

	}

	/**
	 * Function delete selected element given as constructor parameter and
	 * reload elements.
	 */
	private void Delete() {
		// Delete element from XMLTree
		GenericTreeNode<LayoutParserTreeElement> elementNode = panel.element;
		gui.layoutParser.XMLTree.delete(elementNode.getData());

		// Delete element from panel
		gui.draw.DeleteElement(panel);
	}
}
