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
		Config.execs = "cale catre execs";
		Config.exec_schemas = "cale catre XSD pentru execs";
		Config.output_schemas = "cale catre XSD pentru output";
		
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
		AnalizerSelector as = new AnalizerSelector();
		Analizer selectedAnalizer = as.chooseAnalizer();
		selectedAnalizer.setInput("calea catre imagine");
		selectedAnalizer.analizeXML();
		
		/**
		 * TODO In acest moment vom avea un fisier XML rezultat in urma analizei de layout:
		 *         - fie a fost dat la inceput ca input
		 *         - fie a rezutat in urma analizei de layout
		 */
	}

}
