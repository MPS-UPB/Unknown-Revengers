package element_actions;

import gui.GElement;
import parser.LayoutParser;
import analyzer.Analyzer;
import analyzer.AnalyzerSelector;

/**
 * Afiseaza analizatoare OCR disponibile si ruleaza analiza OCR.
 * 
 * @author Unknown-Revengers
 * 
 */
public class GetText {

	private final String analyzedText;

	/**
	 * Constructor
	 * 
	 * @param panel Element panel.
	 * @param lp Layout Parser.
	 * 
	 * @throws InterruptedException
	 */
	public GetText(GElement panel, LayoutParser lp)
			throws InterruptedException {

		// Select an analyzer.
		AnalyzerSelector as = new AnalyzerSelector("ocr");
		Analyzer selectedAnalyzer = as.chooseAnalyzer();

		// Set optional properties for analyzer.
		selectedAnalyzer.setInput(lp.imagePath);
		selectedAnalyzer.setPanel(panel);
		selectedAnalyzer.setLayoutParser(lp);

		// TODO run the selected analyzer
		// String text = selectedAnalyzer.analyzeXML();

		this.analyzedText = "ceva";
	}

	/**
	 * Getter for analyzedText
	 * 
	 * @return String The OCR analyzed text
	 */
	public String getAnalizedText() {
		return this.analyzedText;
	}
}
