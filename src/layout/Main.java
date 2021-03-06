package layout;

import gui.LayoutGUI;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.xml.transform.TransformerException;

import parser.LayoutParser;
import analyzer.Analyzer;
import analyzer.AnalyzerSelector;

/**
 * @author Unknown-Revengers
 * 
 */
public class Main {
	/**
	 * @param args Parametri.
	 * 
	 * @throws InterruptedException Another thread intrrupts this one by using
	 *             the interrupt method.
	 * @throws TransformerException Exceptie pentru Transformer.
	 * @throws IOException Exceptie IO.
	 */
	public static void main(String[] args) throws InterruptedException,
			TransformerException, IOException {

		/*
		 * Incarca fisierul de config, iar daca citirea fisierului de
		 * configurare a esuat atunci inchide aplicatia cu mesaj de eroare.
		 */
		if (Config.load() == false) {
			JOptionPane.showMessageDialog(null,
					"Eroare citire fisier configurare!");
			System.exit(0);
		}

		/*
		 * Afiseaza dialog pentru a selecta fisierele de intrare: - filtru
		 * pentru XML - filtru imagini
		 */
		FileChooser fc = new FileChooser();
		String selectedFile = fc.chooseFile();

		if (selectedFile == null) {
			System.exit(0);
		}

		/*
		 * Valideaza fisierul selectat din punct de vedere al layoutului.
		 */
		while (!FileValidator.isValid(selectedFile)) {
			selectedFile = fc.chooseFile();
		}

		/*
		 * DOAR daca la input s-a dat imagine - Cauta in schemele XML daca
		 * exista analizatoare disponibile 1) Daca exista analizatoare afiseaza
		 * un dropdown cu ele si un buton de OK. 2) Daca nu exista analizatoare
		 * disponibile afiseaza un mesaj de eroare.
		 * 
		 * - Analizeaza imaginea => fisier XML cu analiza de layout.
		 */
		if (selectedFile.endsWith("xml") == false) {
			// Selecteaza analizator.
			AnalyzerSelector as = new AnalyzerSelector("layout");
			Analyzer selectedAnalyzer = as.chooseAnalyzer();

			// Seteaza calea catre imagine.
			selectedAnalyzer.setInput(selectedFile);

			// Returneaza calea catre fisierul rezultat in urma analizei layout
			// a imaginii.
			selectedFile = selectedAnalyzer.analyzeXML();
		}

		/**
		 * Reprezentare fisierului de input in GUI.
		 */
		LayoutParser lp = new LayoutParser(selectedFile);
		new LayoutGUI(lp);
	}
}
