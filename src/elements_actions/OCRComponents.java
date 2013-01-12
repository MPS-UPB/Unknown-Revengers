package elements_actions;

import gui.GElement;
import gui.LayoutGUI;

import java.util.ArrayList;

import parser.TextActions;
import analyzer.Analyzer;
import analyzer.AnalyzerSelector;

/**
 * Run OCR analyzer on list of components passed in constructor
 * 
 * @author Unknown-revengers
 * 
 */
public class OCRComponents {

	// List of selected components
	private ArrayList<GElement> panels;

	private LayoutGUI gui;

	private Analyzer analyzer;

	/**
	 * Constructor.
	 * 
	 * @param panels Element panels.
	 * @param gui GUI.
	 * 
	 * @throws InterruptedException
	 */
	public OCRComponents(ArrayList<GElement> panels, LayoutGUI gui)
			throws InterruptedException {
		this.panels = panels;
		this.gui = gui;

		// Select an analyzer.
		AnalyzerSelector as = new AnalyzerSelector("ocr");
		this.analyzer = as.chooseAnalyzer();

		// Set optional properties for analyzer.
		this.analyzer.setInput(this.gui.getLayoutParser().getImagePath());
		this.analyzer.setLayoutParser(this.gui.getLayoutParser());

		// Call OCR analyzer method
		this.AnalyzeOCRComponents();
	}

	/**
	 * Run OCR analyzer on each selected components
	 * 
	 * @throws InterruptedException
	 */
	private void AnalyzeOCRComponents() throws InterruptedException {
		// For each selected component run OCR analyzer
		for (int i = 0; i < this.panels.size(); i++) {
			this.analyzer.setPanel(panels.get(i));

			// TODO run the selected analyzer
			// String text = selectedAnalyzer.analyzeXML();
			String text = "ceva frumos";

			TextActions.saveText(panels.get(i).element, text);
		}

		// TODO
	}
}
