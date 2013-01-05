package gui;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import parser.LayoutParserTreeElement;
import tree.GenericTreeNode;
import element_actions.BlockMouseListener;

public class GElement extends JScrollPane {

	public GenericTreeNode<LayoutParserTreeElement> element;

	public GElement(GenericTreeNode<LayoutParserTreeElement> element) {
		this.element = element;

		// Make it transparent.
		this.setOpaque(false);
		this.getViewport().setOpaque(false);

		// Set initial border.
		this.setBorder(new LineBorder(Color.GREEN));

		//
		this.addMouseListener(new BlockMouseListener());
	}
}
