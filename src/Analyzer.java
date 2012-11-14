import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Structura unui analizator.
 * 
 * @author Unknown-Revengers
 */
public class Analyzer {
	String name;
	String description;
	String input;
	String output;

	public Analyzer(String name, String description){
		this.name = name;
		this.description = description;
	}

	/**
	 * Seteaza imaginea de input pentru analizator.
	 *
	 * @param input
	 */
	public void setInput(String input){
		this.input = input;
	}

	/**
	 * Returneaza numele analizatorului.
	 *
	 * @return String
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Se construieste un XML care va fi input pentru analizator.
	 * 
	 * @return String
	 */
	private String createAnalyzerInputXML(){
		String xml = "<task>";
		xml += "<inputFile name=" + "\"" + this.input + "\"" + "/>";
		xml += "<outputFile name=" + "\"" + this.output + "\"" + "/>";
		xml += "</task>";

		return xml;
	}

	/**
	 * Ruleaza analizatorul pe fisierul XML temporar de input si returneaza outputul/calea fisierului.
	 * 
	 * @return String Calea catre outputul analizatorului.
	 */
	public String analizeXML(){
		// Creaza fisier temporar de output.
		File fout = new File("");
		try {
			fout = fout.createTempFile("output", ".xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		fout.deleteOnExit();

		// Set output file.
		this.output = fout.getAbsolutePath();

		// Creaza xml pentru input.
		String xml = this.createAnalyzerInputXML();

		// Creaza fisier temporar de input.
		File fin = new File("");
		try {
			fin = fin.createTempFile("input", ".xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		fin.deleteOnExit();

		try {
			// Scrie xml-ul in fisierul de intrare.
			FileWriter fstream = new FileWriter(fin.getAbsolutePath());
			BufferedWriter in = new BufferedWriter(fstream);
			in.write(xml);

			// Close the input stream.
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Ruleaza analizator.
		String[] params = {fin.getAbsolutePath()};
		try {
			ProcessBuilder pb = new ProcessBuilder(Config.execs + "\\" + this.name, fin.getAbsolutePath());
			Process p = pb.start();

			// Asteapta incheierea procesului.
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Inchide procesul.
			p.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this.output;
	}
}