package page_actions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import analyzer.Analyzer;
import analyzer.AnalyzerSelector;

import element_actions.ElementActions;
import element_actions.GetText;
import element_actions.ViewText;
import gui.LayoutGUI;

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
	 * @param LayoutGUI gui
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
					Analyzer selectedAnalyzer = as.chooseAnalyzer();
					selectedAnalyzer.setInput(gui.layoutParser.xmlPath);
					String outputPath = selectedAnalyzer.analyzeXML();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};

		actionThread.start();
	}
}
