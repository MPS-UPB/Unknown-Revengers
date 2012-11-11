import java.io.*;
import java.util.*;


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
		
		
		//Creez o lista in care pastrez caile absolute catre executabile/scheme
		List<String> filePath=new ArrayList<String>();
		
		BufferedReader br = null;
	
		try {
 
			String sCurrentLine;
			File configFile=new File("src//config");
			configFile=configFile.getAbsoluteFile();
			br = new BufferedReader(new FileReader(configFile));
 
			while ((sCurrentLine = br.readLine()) != null) {
				
				if(sCurrentLine.startsWith("#")==false){
					filePath.add(Config.getPath(sCurrentLine));
				}
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		Config.execs = filePath.get(0);
		Config.exec_schemas = filePath.get(1);
		Config.output_schemas = filePath.get(2);
		
		
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
		// Returneaza calea catre fisierul rezultat in urma analizei layout a imaginii. 
		selectedAnalizer.analizeXML(); 
		
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
