package elements_actions;

import gui.GElement;
import gui.LayoutGUI;

import java.util.ArrayList;

import layout.ErrorMessage;
import parser.LayoutParserTreeElement;
import tree.GenericTreeNode;

/**
 * 
 * Glue together more elements passed in constructor
 * 
 * @author Unknown-Revengers
 * 
 */
public class GlueElements {

	ArrayList<GElement> panels;

	LayoutGUI gui;

	/**
	 * Contructor.
	 * 
	 * @param panels Element panels.
	 * @param gui GUI.
	 */
	public GlueElements(ArrayList<GElement> panels, LayoutGUI gui) {
		this.panels = panels;
		this.gui = gui;

		// Call glue method.
		this.glueElements();
	}

	/**
	 * Glue more elements together
	 */
	private void glueElements() {

		// Get parent of the first selected node.
		GenericTreeNode<LayoutParserTreeElement> parent = this.panels.get(0).element
				.getParent();

		// Check if elements are inside the same parent.
		for (int i = 1; i < panels.size(); i++) {
			if (this.panels.get(i).element.getParent() != parent) {
				ErrorMessage.show("Elements should be inside the same parent.",
						false);
				return;
			}
		}

		// Glue elements.
		for (int j = panels.size() - 1; j >= 1; j--) {
			this.gui.layoutParser.mergeNodeIntoOtherNode(panels.get(j).element,
					panels.get(0).element);
		}

		// Reload GUI blocks.
		this.gui.loadElements(this.gui.visibleElements);
	}
}
