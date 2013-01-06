package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import parser.LayoutParserTreeElement;
import parser.TextActions;
import tree.GenericTreeNode;
import element_actions.BlockMouseListener;

public class GElement extends JPanel {

	private JScrollPane scrollPanel;
	public JTextArea textArea;
	public GenericTreeNode<LayoutParserTreeElement> element;

	public GElement(GenericTreeNode<LayoutParserTreeElement> element) {
		this.element = element;

		// Make it transparent.
		this.setOpaque(false);

		// No layout.
		this.setLayout(null);

		// Set initial border.
		this.setBorder(new LineBorder(Color.GREEN));

		// Set mouse listener for block change.
		this.addMouseListener(new BlockMouseListener());
	}

	/**
	 * 
	 * Seteaza un TextArea pentru elementul curent
	 * 
	 * @param height
	 * @param width
	 */
	public void setTextArea(int width, int height) {
		textArea = new JTextArea();

		textArea.setVisible(false);
		textArea.setBorder(BorderFactory.createLineBorder(Color.red));
		textArea.setLineWrap(true);

		textArea.setEditable(false);

		this.scrollPanel = new JScrollPane(textArea);
		this.scrollPanel.setPreferredSize(new Dimension(width, height));
		this.scrollPanel.setBounds(0, 0, width, height);
		this.scrollPanel.setVisible(false);
		this.add(scrollPanel, BorderLayout.CENTER);
	}

	/**
	 * 
	 * Seteaza TextArea-ul visibil sau nu
	 * 
	 */
	public void toggleTextAreaVisible() {
		if (textArea.isVisible()) {
			textArea.setVisible(false);
			this.scrollPanel.setVisible(false);
		} else {
			textArea.setText(TextActions.getText(element));
			this.scrollPanel.setVisible(true);
			textArea.setVisible(true);
		}
	}
}
