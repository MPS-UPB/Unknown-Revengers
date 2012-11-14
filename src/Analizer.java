/**
 * Structura unui analizator.
 * 
 * @author Unknown-Revengers
 */
public class Analizer {
	String name;
	String description;
	String input;
	String output;

	public Analizer(String name, String description){
		this.name = name;
		this.description = description;
	}

	/**
	 * Seteaza inputul pentru analizator.
	 * 
	 * @param input
	 */
	public void setInput(String input){
		this.input = input;
	}

	public String getName(){
		return this.name;
	}

	/**
	 * TODO Se construieste un XML temporar care va fi input pentru analizator.
	 * 
	 * @return String
	 */
	private String createAnalizerInputXML(){
		return null;
	}

	/**
	 * TODO Ruleaza analizatorul pe fisierul XML de input si returneaza outputul/calea fisierului.
	 * 
	 * @return String Calea catre outputul analizatorului.
	 */
	public String analizeXML(){

		this.createAnalizerInputXML();

		return null;
	}
}