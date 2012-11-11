import java.io.File;

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
		
		int first=path.indexOf('"');
		int last=path.lastIndexOf('"');
		
		String filePath=path.substring(first+1, last);
		
		File pathFile=new File (filePath);
		
		if(pathFile.isAbsolute()){
		}
		else{
			filePath=pathFile.getAbsolutePath();
			
		}
		return filePath;
	}
}
