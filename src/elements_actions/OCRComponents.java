package elements_actions;

import gui.GElement;
import gui.LayoutGUI;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import layout.ErrorMessage;
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
	 * @throws InterruptedException Another thread intrrupts this one by using
	 *             the interrupt method.
	 * @throws IOException When the compiler encounters a problem while
	 *             attempting to run a program.
	 */
	public OCRComponents(ArrayList<GElement> panels, LayoutGUI gui)
			throws InterruptedException, IOException {
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
	 * @throws IOException
	 */
	private void AnalyzeOCRComponents() throws InterruptedException,
			IOException {
		// For each selected component run OCR analyzer
		for (int i = 0; i < this.panels.size(); i++) {
			this.analyzer.setPanel(panels.get(i));

			// Run the selected analyzer
			String outputFile = this.analyzer.analyzeXML();

			// Get the text from the outputed file.
			String text = this.readEntireFile(outputFile);

			TextActions.saveText(panels.get(i).element, text);
		}

		ErrorMessage.show("Analiza OCR s-a efectuat cu succes!", false);
	}

	/**
	 * Read and entire file.
	 * 
	 * @param filename The input filename
	 * @return String
	 * @throws IOException
	 */
	private String readEntireFile(String filename) throws IOException {
		FileReader in = new FileReader(filename);
		StringBuilder contents = new StringBuilder();

		char[] buffer = new char[4096];
		int read = 0;
		do {
			contents.append(buffer, 0, read);
			read = in.read(buffer);
		} while (read >= 0);

		in.close();

		return contents.toString();
	}
}
