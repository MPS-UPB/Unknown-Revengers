package element_actions;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import layout.Config;
import layout.ErrorMessage;

import parser.LayoutParser;

import gui.ElementJPanel;


import analyzer.Analyzer;
import analyzer.AnalyzerSelector;

/**
 * Button listener pentru salvare modificari.
 * 
 * @author Unknown-Revengers
 */
public class PopupListener implements ActionListener {


	private ElementJPanel panel;
	
	private LayoutParser layoutParser;
	/**
	 * Constructor.
	 * 
	 * @param panel
	 */
	public PopupListener(ElementJPanel panel, LayoutParser lp) {
		this.panel = panel;
		this.layoutParser = lp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final String action = e.getActionCommand();

		Thread actionThread = new Thread() {
			@Override
			public void run() {
				try {
					switch (action) {
					case "Analiza OCR":
						AnalyzerSelector as = new AnalyzerSelector("ocr");
						Analyzer selectedAnalyzer = as.chooseAnalyzer();
						selectedAnalyzer.setInput(layoutParser.imagePath);
						String text = selectedAnalyzer.analyzeXML("ocr", panel, layoutParser);
						
						break;

					case "Vezi text":
						ViewText tf = new ViewText(panel);
						break;

					case "Sparge bloc text":
						break;

					case "Este numar pagina":
						break;
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		actionThread.start();
	}
}
