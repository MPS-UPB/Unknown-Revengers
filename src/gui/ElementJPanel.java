package gui;

import javax.swing.JPanel;

import parser.LayoutParserTreeElement;

public class ElementJPanel extends JPanel {

	public LayoutParserTreeElement element;

	public ElementJPanel(LayoutParserTreeElement element) {
		this.element = element;
	}
}
