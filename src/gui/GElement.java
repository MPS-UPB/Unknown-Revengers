package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import parser.LayoutParserTreeElement;
import parser.TextActions;
import tree.GenericTreeNode;
import element_actions.BlockMouseListener;

public class GElement extends JScrollPane {

	public JTextArea textArea;
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
	
	/**
	 * 
	 * Seteaza un TextArea pentru elementul curent
	 * 
	 * @param height
	 * @param width
	 */
	public void setTextArea(int height, int width) {
		textArea = new JTextArea();
		
		textArea.setBounds(0,0, width, height);
		textArea.setVisible(false);
		textArea.setBorder(BorderFactory.createLineBorder(Color.red));  
		textArea.setLineWrap(true);  
		
		textArea.setText(TextActions.getText(element));
		textArea.setOpaque(false);
		
		this.add(textArea);
	}
	
	/**
	 * 
	 * Seteaza TextArea-ul visibil sau nu
	 * 
	 */
	public void toggleTextAreaVisible() {
		if(textArea.isVisible())
			textArea.setVisible(false);
		else
			textArea.setVisible(true);
	}
}
