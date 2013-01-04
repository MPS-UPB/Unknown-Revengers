package elements_actions;

import gui.ElementJPanel;
import gui.LayoutGUI;

import java.util.ArrayList;

import layout.ErrorMessage;
import parser.LayoutParserTreeElement;

/**
 * 
 * Glue together more elements passed in constructor
 * 
 * @author Unknown-Revengers
 * 
 */
public class GlueElements {

	ArrayList<ElementJPanel> panels;

	LayoutGUI gui;

	/*
	 * Glue elements contructor
	 */
	public GlueElements(ArrayList<ElementJPanel> panels, LayoutGUI gui) {
		this.panels = panels;
		this.gui = gui;

		// Call glue method.
		this.glueElements();
	}

	/**
	 * Glue more elements together
	 * 
	 * return void
	 */
	private void glueElements() {

		// Get parent of the first selected node.
		LayoutParserTreeElement parent = this.gui.layoutParser.XMLTree
				.find(this.panels.get(0).element).getParent().getData();

		// Check if elements are inside the same parent.
		for (int i = 1; i < panels.size(); i++) {
			if (this.gui.layoutParser.XMLTree.find(this.panels.get(i).element)
					.getParent().getData() != parent) {
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
