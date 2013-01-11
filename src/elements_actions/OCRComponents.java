package elements_actions;

import gui.GElement;
import gui.LayoutGUI;

import java.util.ArrayList;

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

		// Call OCR analyzer method
		this.AnalyzeOCRComponents();
	}

	/**
	 * Run OCR analyzer on each selected components
	 * 
	 * @throws InterruptedException
	 */
	private void AnalyzeOCRComponents() throws InterruptedException {

		// Select an analyzer.
		AnalyzerSelector as = new AnalyzerSelector("ocr");
		Analyzer selectedAnalyzer = as.chooseAnalyzer();
		selectedAnalyzer.setInput(gui.getLayoutParser().getImagePath());
		selectedAnalyzer.setLayoutParser(gui.getLayoutParser());

		// For each selected component return output file path
		String filePath = "";

		// For each selected component run OCR analyzer
		for (int i = 0; i < this.panels.size(); i++) {

			selectedAnalyzer.setPanel(this.panels.get(i));
			filePath = selectedAnalyzer.analyzeXML();

		}

		// TODO
	}
}
