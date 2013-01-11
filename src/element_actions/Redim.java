package element_actions;

import gui.GElement;
import gui.LayoutGUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import parser.LayoutParserTreeElement;

import analyzer.Analyzer;
import analyzer.AnalyzerSelector;

import sun.awt.HorizBagLayout;
import tree.GenericTreeNode;


public class Redim extends JFrame{
	
	/**
	 * Preview button.
	 */
	private JButton previewBttn;

	/**
	 * Save button.
	 */
	private JButton saveBttn;
	
	/**
	 * Dimensiuni.
	 */
	private JTextField textBott;
	private JTextField textTop;
	private JTextField textLeft;
	private JTextField textRight;
	private JLabel labelBot;
	private JLabel labelTop;
	private JLabel labelLeft;
	private JLabel labelRight;
	
	/**
	 * Fereastra curenta.
	 */
	private JFrame frame = this;

	/**
	 * Constructor.
	 */
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Redim(GElement pan, LayoutGUI gui) {
		
		// New Panel.
		JPanel panel = new JPanel(new GridLayout(4,2));
		
		// Creaza content panel.
		Container contentPanel = getContentPane();
		
		ActionListener saveListener = new SaveListenter(pan, gui);
		//dimensiuni
		
		//bottom
		labelBot = new JLabel();
		textBott = new JTextField();
		labelBot.setText("Bottom");
		textBott.setText(Integer.toString(pan.element.getData().bottom));
	
		panel.add(labelBot);
		panel.add(textBott);
		
		//top
		labelTop = new JLabel();
		textTop = new JTextField();
		labelTop.setText("Top");
		textTop.setText(Integer.toString(pan.element.getData().top));
		panel.add(labelTop);
		panel.add(textTop);


		
		//left
		labelLeft = new JLabel();
		textLeft = new JTextField();
		labelLeft.setText("Left");
		textLeft.setText(Integer.toString(pan.element.getData().left));
		panel.add(labelLeft);
		panel.add(textLeft);
		
		//right
		labelRight = new JLabel();
		textRight = new JTextField();
		labelRight.setText("Right");
		textRight.setText(Integer.toString(pan.element.getData().right));
		panel.add(labelRight);
		panel.add(textRight);
		contentPanel.add(panel, BorderLayout.CENTER);
		
		
		
		// Adauga buton de preview.
		panel = new JPanel(new GridLayout(1,2));
		previewBttn = new JButton("Preview");
		this.previewBttn.addActionListener(saveListener);
		panel.add(previewBttn);
		
		// Adauga buton de save.
		saveBttn = new JButton("Save");
		this.previewBttn.addActionListener(saveListener);
		panel.add(saveBttn);
		contentPanel.add(panel, BorderLayout.SOUTH);
		
		
		this.initFrame();
	}

	
	private void initFrame() {

			// Dispose frame for page and OCR analyzers.
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


		this.setSize(300, 220);
		this.setVisible(true);

		setLocationRelativeTo(null);
	}
	/**
	 * Listener pentru butonul de preview.
	 * 
	 * @author Unknown-Revengers
	 */
	class PreviewListenter implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			System.out.println("preview");
		}
	}


	/**
	 * Listener pentru butonul de save.
	 * 
	 * @author Unknown-Revengers
	 */
	class SaveListenter implements ActionListener {
			GElement panel;
			LayoutGUI gui;
		
			SaveListenter(GElement panel, LayoutGUI gui) {
				this.panel = panel;
				this.gui = gui;

		}
		
		@Override
		public void actionPerformed(final ActionEvent e) {
			
			LayoutParserTreeElement newData = new LayoutParserTreeElement();
			newData.bottom =  Integer.parseInt(textBott.getText());
			newData.top = Integer.parseInt(textTop.getText());
			newData.left = Integer.parseInt(textLeft.getText());
			newData.right = Integer.parseInt(textRight.getText());	
			
			
			System.out.println("save");
		}
	}

}



