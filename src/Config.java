import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	/**
	 * Extrage calea din fisierul config, verifica daca e cale absoluta, daca nu o genereaza si returneaza string-ul
	 * @return String
	 */
	
	public static String getPath(String path){
		
		int first = path.indexOf('"');
		int last = path.lastIndexOf('"');
		
		String filePath = path.substring(first+1, last);
		
		File pathFile = new File (filePath);
		
		if(pathFile.isAbsolute()){
		}
		else{
			filePath = pathFile.getAbsolutePath();
			
		}
		return filePath;
	}
	
	/**
	 * Citeste informatiile din fisierul de configurare
	 * Daca nu e eroare extrage caile catre executabile/scheme si seteaza variabele
	 * @return boolean
	 */
	
	public static boolean readConfigFile(){
		
		//Creez o lista in care pastrez caile absolute catre executabile/scheme
		List<String> filePath = new ArrayList<String>();
		
		BufferedReader br = null;
	
		try {
 
			String sCurrentLine;
			File configFile = new File("src//config");
			configFile = configFile.getAbsoluteFile();
			br = new BufferedReader(new FileReader(configFile));
 
			while ((sCurrentLine = br.readLine()) != null) {
				
				sCurrentLine = sCurrentLine.trim();
				
				if(sCurrentLine.startsWith("#") == false && sCurrentLine.isEmpty() == false){
					filePath.add(Config.getPath(sCurrentLine));
				}
			}
 
		} catch (IOException e) {
		} finally {
			try {
				if (br != null){
					br.close();
					Config.execs = filePath.get(0);
					Config.exec_schemas = filePath.get(1);
					Config.output_schemas = filePath.get(2);
					return true;
				}
				else return false;
			} catch (IOException ex) {
	
			}
		}
		
		return true;
	}

	
}
