package page_actions;

import gui.LayoutGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import layout.ErrorMessage;
import parser.LayoutParser;
import analyzer.Analyzer;
import analyzer.AnalyzerSelector;

/**
 * Button listener pentru numerotare pagina.
 * 
 * @author Unknown-Revengers
 */
public class NumeroteazaButtonListener implements ActionListener {

	private LayoutGUI gui;

	/**
	 * Constructor
	 * 
	 * @param gui
	 *            gui.
	 * 
	 */
	public NumeroteazaButtonListener(LayoutGUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		JOptionPane.showMessageDialog(null, "Numeroteaza pagina.");
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
					System.out.println("============================");
					System.out.println(analyzerInput);
					selectedAnalyzer.setInput(analyzerInput);

					String noPath = selectedAnalyzer.analyzeXML();

					gui.layoutParser = new LayoutParser(noPath);

					gui.loadElements(gui.visibleElements);

				} catch (InterruptedException e) {
					ErrorMessage.show("Probleme la numerotare.", false);
				}

			}
		};

		actionThread.start();
	}
}
