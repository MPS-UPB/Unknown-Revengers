package element_actions;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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

	/**
	 * Constructor.
	 * 
	 * @param panel
	 */
	public PopupListener(ElementJPanel panel) {
		this.panel = panel;
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
