package gui;

import javax.swing.JTextArea;

import parser.LayoutParserTreeElement;
import tree.GenericTreeNode;

public class GElement extends JTextArea {

	public GenericTreeNode<LayoutParserTreeElement> element;

	public GElement(GenericTreeNode<LayoutParserTreeElement> element) {
		this.element = element;
		this.setEditable(false);
	}
}
