package element_actions;

import gui.ElementJPanel;
import parser.LayoutParser;
import analyzer.Analyzer;
import analyzer.AnalyzerSelector;

public class GetText {

	private final String analyzedText;

	public GetText(ElementJPanel panel, LayoutParser lp)
			throws InterruptedException {
		AnalyzerSelector as = new AnalyzerSelector("ocr");
		Analyzer selectedAnalyzer = as.chooseAnalyzer();
		selectedAnalyzer.setInput(lp.imagePath);
		selectedAnalyzer.setPanel(panel);
		selectedAnalyzer.setLayoutParser(lp);
		// TODO run the selected analyzer
		// String text = selectedAnalyzer.analyzeXML();
		this.analyzedText = "ceva";
	}

	public String getAnalizedText() {
		return this.analyzedText;
	}
}
