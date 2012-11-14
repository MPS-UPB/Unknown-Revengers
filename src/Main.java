import javax.swing.JOptionPane;


public class Main {
	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		/**
		 * TODO
		 * Verifica daca in fisierul de config sunt specificate cai catre si le seteaza in Config.
		 *    - executabilele sistemului OCR
		 *    - schemele fisierelor XSD
		 *    - schemele formatelor de iesire
		 */

		// Daca citirea fisierului de configurare a esuat se inchide aplicatia
		if( Config.load() == false ) {
			JOptionPane.showMessageDialog(null, "Eroare citire fisier configurare!");
			System.exit(0);
		}

		/**
		 * Afiseaza dialog pentru a selecta fisierele de intrare:
		 *    - filtru pentru XML
		 *    - filtru imagini
		 */
		FileChooser fc = new FileChooser();
		String selectedFile = fc.chooseFile();

		/**
		 * TODO
		 * Valideaza fisierul selectat din punct de vedere al layoutului.
		 */
		while (!FileValidator.isValid(selectedFile)){
			selectedFile = fc.chooseFile();
		}

		/**
		 * TODO DOAR daca la input s-a dat imagine
		 * - Cauta in schemele XML daca exista analizatoare disponibile
		 *    1) Daca exista analizatoare afiseaza un dropdown cu ele si un buton de OK.
		 *    2) Daca nu exista analizatoare disponibile afiseaza un mesaj de eroare.
		 * 
		 * - Analizeaza imaginea => fisier XML cu analiza de layout.
		 */
		AnalyzerSelector as = new AnalyzerSelector();
		Analyzer selectedAnalyzer = as.chooseAnalyzer();
		System.out.println(selectedAnalyzer.name);
		System.out.println(selectedAnalyzer.description);
		selectedAnalyzer.setInput("calea catre imagine");
		// Returneaza calea catre fisierul rezultat in urma analizei layout a imaginii.
		selectedAnalyzer.analizeXML();

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
