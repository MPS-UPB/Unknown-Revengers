package gui;

import javax.swing.JTextArea;

import parser.LayoutParserTreeElement;
import tree.GenericTreeNode;

public class ElementJPanel extends JTextArea {

	public GenericTreeNode<LayoutParserTreeElement> element;

	public ElementJPanel(GenericTreeNode<LayoutParserTreeElement> element) {
		this.element = element;
		this.setEditable(false);
	}
}
