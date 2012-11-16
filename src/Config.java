import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;

/**
 * Configurarile aplicatiei.
 * 
 * @author Unknown-Revengers
 */
public class Config {
	// Folderul cu executabile
	public static String execs;
	
	// Folderul cu XSD pentru executabile
	public static String exec_schemas;
	
	// Folderul cu XSD pentru output
	public static String output_schemas;
	
	// Dictionar in care retin calea catre executabile/scheme
	private static Map<String, String> dictionary = new TreeMap<String, String>();
	
	/**
	 * Extrage dintr-o linie calea catre executabile/scheme
	 * Daca nu e cale absoluta o genereaza si pune in dictionar 
	 * 
	 * @param line linie din fisierul de configurare
	 */
	
	private static void getPath(String line) {
		
		int index = line.indexOf('=');
		
		String path = line.substring(index+1);
		path = path.trim();
		String key = line.substring(0, index);
		key = key.trim();
		File filePath = new File (path);
		
		if(filePath.isAbsolute()) {
			dictionary.put(key, path);
		}
		else {
			path = filePath.getAbsolutePath();
			dictionary.put(key, path);
		}
	}
	
	/**
	 * Citeste informatiile din fisierul de configurare
	 * Daca nu e eroare extrage caile catre executabile/scheme si seteaza variabele
	 * 
	 * @return boolean
	 */
	
	@SuppressWarnings("finally")
	public static boolean load() {
			
		BufferedReader br = null;
	
		try {
 
			String sCurrentLine;
			File configFile = new File("src//config");
			configFile = configFile.getAbsoluteFile();
			br = new BufferedReader(new FileReader(configFile));
 
			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.startsWith("#") == false && sCurrentLine.isEmpty() == false) {
					Config.getPath(sCurrentLine);
				}
			}
 
		} catch (IOException e) {
			ErrorMessage.show("Exceptie la citirea fisierului de config:"
					+ e.getMessage());
		} finally {
			try {
				if (br != null) {
					br.close();
					if (dictionary.containsKey("OCR")) {
						Config.execs = dictionary.get("OCR");
					}
					else {
						JOptionPane.showMessageDialog(null, "OCR nu este specificat in fisierul de config.");
						System.exit(0);
					}
					
					if (dictionary.containsKey("XML")) {
						Config.exec_schemas = dictionary.get("XML");
					}
					else {
						JOptionPane.showMessageDialog(null, "XML nu este specificat in fisierul de config.");
						System.exit(0);
					}
					
					if (dictionary.containsKey("OUTPUT")) {
						Config.output_schemas = dictionary.get("OUTPUT");
					}
					else {
						JOptionPane.showMessageDialog(null, "OUTPUT nu este specificat in fisierul de config.");
						System.exit(0);
					}
					
					return true;
				}
				else {
					return false;
				}
			} catch (final IOException ex) {
				ErrorMessage.show("Exceptie la citirea fisierului de config:"
						+ ex.getMessage());
			}
		}
		return false;
	}	
}
