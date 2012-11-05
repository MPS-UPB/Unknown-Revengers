/**
 * Valideaza un fisier dat ca input.
 * 
 * @author Unknown-Revengers
 *
 */
public class FileValidator {
	
	/**
	 * TODO Verifica daca e un fisier valid (imagine JPG sau fisier XML formatat conform cu layout specifications).
	 * 
	 * @return boolean
	 */
	public static boolean isValid(String fileName){
		
		// TODO verifica extensie fisier
		
		// TODO daca e imagine atunci incarca imaginea este valida
		
		// TODO daca e XML atunci se verifica sa fie respectat XSD-ul de layout
		
		return true;
	}
	
	/**
	 * TODO Verifica daca XML-ul dat este OK din pct de vedere al layoutului.
	 * 
	 * @param  XMLPath  Calea catre fisierul XML analizat.
	 * 
	 * @return boolean
	 */
	private boolean validXML(String XMLPath){
		// TODO verifica XML pe baza unui XML http://stackoverflow.com/questions/15732/whats-the-best-way-to-validate-an-xml-file-against-an-xsd-file
		
		return true;
	}
	
	/**
	 * TODO Verifica daca imaginea data este valida (am vazut ca exista diverse moduri de a face asta).
	 * 
	 * @param imagePath
	 * 
	 * @return boolean
	 */
	private boolean validImage(String imagePath){
		return true;
	}
}
