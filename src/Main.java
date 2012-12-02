import javax.swing.JOptionPane;

import tree.GenericTree;


public class Main {
	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		/*
		if(1==1){
			String xmlExample = "<Document image='3-sizes.tif' direction='descending'>" +
								   "<TextBlock left='13' right='1089' top='26' bottom='109'><TextLine left='13' right='1089' top='26' bottom='109'><String>Nato</String><String>setzt</String></TextLine></TextBlock>" +
								   "<TextBlock left='13' right='1089' top='26' bottom='109'><TextLine left='13' right='1089' top='26' bottom='109'><String>Nato</String><String>setzt</String></TextLine></TextBlock></Document>";
			LayoutParser lp = new LayoutParser(xmlExample);
			GenericTree<Element> gt = lp.parseXML(xmlExample);
			System.out.println(gt.toStringWithDepth());
			return;
		}
		*/

		
		/*
		 *  Incarca fisierul de config, iar daca citirea fisierului de configurare
		 *  a esuat atunci inchide aplicatia cu mesaj de eroare.
		 */
		if( Config.load() == false ) {
			JOptionPane.showMessageDialog(null, "Eroare citire fisier configurare!");
			System.exit(0);
		}

		/*
		 * Afiseaza dialog pentru a selecta fisierele de intrare:
		 *    - filtru pentru XML
		 *    - filtru imagini
		 */
		FileChooser fc = new FileChooser();
		String selectedFile = fc.chooseFile();

		if (selectedFile == null) {
			System.exit(0);
		}

		/*
		 * Valideaza fisierul selectat din punct de vedere al layoutului.
		 */
		while (!FileValidator.isValid(selectedFile)){
			selectedFile = fc.chooseFile();
		}

		/*
		 * DOAR daca la input s-a dat imagine
		 * - Cauta in schemele XML daca exista analizatoare disponibile
		 *    1) Daca exista analizatoare afiseaza un dropdown cu ele si un buton de OK.
		 *    2) Daca nu exista analizatoare disponibile afiseaza un mesaj de eroare.
		 * 
		 * - Analizeaza imaginea => fisier XML cu analiza de layout.
		 */
		if (selectedFile.endsWith("xml") == false) {
			// Selecteaza analizator.
			AnalyzerSelector as = new AnalyzerSelector();
			Analyzer selectedAnalyzer = as.chooseAnalyzer();

			// Seteaza calea catre imagine.
			selectedAnalyzer.setInput(selectedFile);

			// Returneaza calea catre fisierul rezultat in urma analizei layout a imaginii.
			selectedFile = selectedAnalyzer.analyzeXML();
		}

		/**
		 * TODO In acest moment vom avea un fisier XML cu analiza de layout:
		 *         - fie a fost dat la inceput ca input
		 *         - fie a rezutat in urma analizei de layout
		 * 
		 * Urmatorul pas este sa reprezentam acest fisier in GUI
		 */
		LayoutParser lp = new LayoutParser("path catre fisierul de layout");
		LayoutGUI lg = new LayoutGUI(lp);
	}

}
