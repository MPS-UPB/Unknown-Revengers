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
	public VisibleElements elementType;

	public GElement(GenericTreeNode<LayoutParserTreeElement> element, VisibleElements elementType) {
		this.element = element;
		this.elementType = elementType;

		// Make it transparent.
		this.setOpaque(false);
		this.getViewport().setOpaque(false);

		// Set initial border.
		this.setBorder(new LineBorder(Color.GREEN));

		//
		this.addMouseListener(new BlockMouseListener());
	}
	
	public void removeExistingPanel(){
		if(this.textArea != null){
			this.removeAll();
			this.textArea.setVisible(false);
		}
	}
	
	/**
	 * 
	 * Seteaza un TextArea pentru elementul curent
	 * 
	 * @param height
	 * @param width
	 */
	public void setTextArea(int height, int width, boolean visible) {
		boolean alreadyExisted = true;
		if(textArea == null){
			textArea = new JTextArea();
			alreadyExisted = false;
		}

		textArea.setBounds(0,0, width, height);
		textArea.setBorder(BorderFactory.createLineBorder(Color.red));  
		textArea.setLineWrap(true);  
		
		textArea.setVisible(visible);
		
		textArea.setText(TextActions.getText(element));
		textArea.setOpaque(false);
		textArea.setFocusable(false);
		textArea.setEditable(false);
		
		if(alreadyExisted == false)
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
	
	/**
	 * 
	 * Modifica vizibilitatea si existenta TextArea-ului 
	 * asociat GElement-ului
	 * 
	 * @param visible
	 */
	public void setTextAreaVisible(boolean visible) {
		textArea.removeAll();
		textArea.setVisible(visible);
		textArea.repaint();
	}
}
