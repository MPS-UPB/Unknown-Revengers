package page_actions;

import gui.LayoutGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import layout.ErrorMessage;
import analyzer.Analyzer;
import analyzer.AnalyzerSelector;

/**
 * Button listener pentru numerotare pagina.
 * 
 * @author Unknown-Revengers
 */
public class NumeroteazaButtonListener implements ActionListener {

	/**
	 * GUI
	 */
	private LayoutGUI gui;

	/**
	 * Constructor
	 * 
	 * @param gui GUI.
	 * 
	 */
	public NumeroteazaButtonListener(LayoutGUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Thread actionThread = new Thread() {

			@Override
			public void run() {
				AnalyzerSelector as = new AnalyzerSelector("paging");
				try {
					// Choose analyzer.
					Analyzer selectedAnalyzer = as.chooseAnalyzer();

					/*
					 * Save changes first and make file as input for the
					 * analyzer.
					 */
					String analyzerInput = gui.layoutParser.saveXML(false);
					selectedAnalyzer.setInput(analyzerInput);

					String noPath = selectedAnalyzer.analyzeXML();

					gui.layoutParser.xmlPath = noPath;
					gui.layoutParser.parse();

					gui.loadElements(gui.visibleElements);

					ErrorMessage.show("S-a numerotat.", false);

				} catch (InterruptedException e) {
					ErrorMessage.show("Probleme la numerotare.", false);
				}
			}
		};

		actionThread.start();
	}
}
