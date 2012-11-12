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
	public static Map<String, String> dictionary =new TreeMap<String, String>();
	
	/**
	 * Extrage dintr-o linie calea catre executabile/scheme, daca nu e cale absoluta o genereaza si pune in dictionar 
	 * @param line linie din fisierul de configurare
	 */
	
	public static void getPath(String line){
		
		int index = line.indexOf('=');
		
		String path = line.substring(index+1);
		String key = line.substring(0, index);
		File filePath = new File (path);
		
		if(filePath.isAbsolute()){
			dictionary.put(key, path);
		}
		else{
			path = filePath.getAbsolutePath();
			dictionary.put(key, path);
		}
	}
	
	/**
	 * Citeste informatiile din fisierul de configurare
	 * Daca nu e eroare extrage caile catre executabile/scheme si seteaza variabele
	 * @return boolean
	 */
	
	@SuppressWarnings("finally")
	public static boolean readConfigFile(){
			
		BufferedReader br = null;
	
		try {
 
			String sCurrentLine;
			File configFile = new File("src//config");
			configFile = configFile.getAbsoluteFile();
			br = new BufferedReader(new FileReader(configFile));
 
			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentLine = sCurrentLine.replaceAll(" ", "");
				if(sCurrentLine.startsWith("#") == false && sCurrentLine.isEmpty() == false){
					Config.getPath(sCurrentLine);
				}
			}
 
		} catch (IOException e) {
		} finally {
			try {
				if (br != null){
					br.close();
					Config.execs = dictionary.get("OCR");
					Config.exec_schemas = dictionary.get("XML");
					Config.output_schemas =dictionary.get("OUTPUT");
					return true;
				}
				else return false;
			} catch (final IOException ex) {
				return false;
			}
		}
	}	
}
