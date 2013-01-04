package gui;

import javax.swing.JPanel;

import parser.LayoutParserTreeElement;
import tree.GenericTreeNode;

public class ElementJPanel extends JPanel {

	public GenericTreeNode<LayoutParserTreeElement> element;

	public ElementJPanel(GenericTreeNode<LayoutParserTreeElement> element) {
		this.element = element;
	}
}
