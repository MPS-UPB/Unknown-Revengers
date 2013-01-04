package elements_actions;

import gui.ElementJPanel;
import gui.LayoutGUI;

import java.util.ArrayList;

import analyzer.Analyzer;
import analyzer.AnalyzerSelector;

public class OCRComponents {
	
	//Lista de componente selectate
	ArrayList<ElementJPanel> panels;

	LayoutGUI gui;

	/*
	 * Constructor
	 */
	public OCRComponents(ArrayList<ElementJPanel> panels, LayoutGUI gui) throws InterruptedException {
		this.panels = panels;
		this.gui = gui;
		this.AnalyzeOCRComponents();
	}
	
	private void AnalyzeOCRComponents() throws InterruptedException{
		
		// Select an analyzer.
		AnalyzerSelector as = new AnalyzerSelector("ocr");
		Analyzer selectedAnalyzer = as.chooseAnalyzer();
		selectedAnalyzer.setInput(gui.layoutParser.imagePath);
		selectedAnalyzer.setLayoutParser(gui.layoutParser);
		
		//Pentru fiecare element returneaza calea fisierului de output
		String filePath = "";
		
		for (int i = 0; i < this.panels.size(); i ++) {
			
			selectedAnalyzer.setPanel(this.panels.get(i));
			filePath = selectedAnalyzer.analyzeXML();
			
		}
	}
}
