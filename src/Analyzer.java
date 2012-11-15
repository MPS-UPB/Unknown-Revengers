/**
 * @author Unknown-Revengers
 */

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
	/**
	 * Analyzer's name.
	 */
	private String name;

	/**
	 * Analyzer's description.
	 */
	private String description;

	/**
	 * Image absolute path.
	 */
	private String input;

	/**
	 * Temporary output file absolute path.
	 */
	private String output;

	/**
	 * Constructor.
	 *
	 * @param name        Analyzer's name.
	 * @param description Analyzer's description.
	 */
	public Analyzer(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * Seteaza imaginea de input pentru analizator.
	 *
	 * @param input Calea absoluta a imaginii de input.
	 */
	public void setInput(String input) {
		this.input = input;
	}

	/**
	 * Returneaza numele analizatorului.
	 *
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returneaza descrierea analizatorului.
	 *
	 * @return String
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Se construieste un XML care va fi input pentru analizator.
	 *
	 * @return String
	 */
	private String createAnalyzerInputXML() {
		String xml = "<task>";
		xml += "<inputFile name=" + "\"" + this.input + "\"" + "/>";
		xml += "<outputFile name=" + "\"" + this.output + "\"" + "/>";
		xml += "</task>";

		return xml;
	}

	/**
	 * Ruleaza analizatorul pe fisierul XML temporar
	 * de input si returneaza outputul/calea fisierului.
	 *
	 * @return String Calea catre outputul analizatorului.
	 */
	public String analyzeXML() {
		// Creaza fisier temporar de output.
		File fout = new File("");
		try {
			fout = File.createTempFile("output", ".xml");
		} catch (IOException e) {
			ErrorMessage.show("Exceptie la crearea fisierului temporar de output:"
					+ e.getMessage());
		}
		fout.deleteOnExit();

		// Set output file.
		this.output = fout.getAbsolutePath();

		// Creaza xml pentru input.
		String xml = this.createAnalyzerInputXML();

		// Creaza fisier temporar de input.
		File fin = new File("");
		try {
			fin = File.createTempFile("input", ".xml");
		} catch (IOException e) {
			ErrorMessage.show("Exceptie la crearea fisierului temporar de input:"
					+ e.getMessage());
		}
		fin.deleteOnExit();

		try {
			// Scrie xml-ul in fisierul de intrare.
			FileWriter fstream = new FileWriter(
					fin.getAbsolutePath());
			BufferedWriter in = new BufferedWriter(fstream);
			in.write(xml);

			// Close the input stream.
			in.close();
		} catch (Exception e) {
			ErrorMessage.show("Exceptie la scrierea in fisierul temporar de input:"
					+ e.getMessage());
		}

		// Ruleaza analizator.
		try {
			ProcessBuilder pb = new ProcessBuilder(Config.execs
					+ "\\" + this.name, fin.getAbsolutePath());
			Process p = pb.start();

			// Asteapta incheierea procesului.
			p.waitFor();

			// Inchide procesul.
			p.destroy();
		} catch (Exception e) {
			ErrorMessage.show("Exceptie la rularea analizatorului:"
					+ e.getMessage());
		}

		return this.output;
	}
}
