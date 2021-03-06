package analyzer;

/**
 * @author Unknown-Revengers
 */

import gui.GElement;

import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import layout.Config;
import layout.ErrorMessage;
import parser.LayoutParser;

/**
 * Structura unui analizator.
 * 
 * @author Unknown-Revengers
 */
public class Analyzer {
	/**
	 * Analyzer's name.
	 */
	private final String name;

	/**
	 * Analyzer's description.
	 */
	private final String description;

	/**
	 * Image absolute path.
	 */
	private String input;

	/**
	 * Temporary output file absolute path.
	 */
	private String output;

	private String type;

	private GElement panel;

	private LayoutParser lp;

	/**
	 * Constructor.
	 * 
	 * @param name Analyzer's name.
	 * @param description Analyzer's description.
	 * @param type Analyzer's type.
	 */
	public Analyzer(String name, String description, String type) {
		this.name = name;
		this.description = description;
		this.type = type;
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
	 * Panel setter.
	 * 
	 * @param panel GElement
	 */
	public void setPanel(GElement panel) {
		this.panel = panel;
	}

	/**
	 * Layout Parser setter.
	 * 
	 * @param lp Layout Parser
	 */
	public void setLayoutParser(LayoutParser lp) {
		this.lp = lp;
	}

	/**
	 * Name getter.
	 * 
	 * @return String Returneaza numele analizatorului.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Description getter.
	 * 
	 * @return String Returneaza descrierea analizatorului.
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
	 * Construieste un XML care va fi input pentru analiza OCR.
	 * 
	 * @return String
	 */
	public String createOCRInputXML() {

		Rectangle r = panel.getBounds();
		int left = r.x;
		int top = r.y;
		int right = left + r.width;
		int bottom = top + r.height;

		String xml = "<task>";
		xml += "<inputFile name=" + "\"" + this.input + "\"" + "/>";
		xml += "<outputFile name=" + "\"" + this.output + "\"" + "/>";
		xml += "<processRectangle direction=" + "\"" + lp.direction + "\" "
				+ "top=" + "\"" + top + "\" " + "bottom=" + "\"" + bottom
				+ "\" " + "left=" + "\"" + left + "\" " + "right=" + "\""
				+ right + "\" " + "/>";
		xml += "</task>";

		return xml;
	}

	/**
	 * Ruleaza analizatorul pe fisierul XML temporar de input si returneaza
	 * outputul/calea fisierului.
	 * 
	 * @return String Calea catre outputul analizatorului.
	 */
	public String analyzeXML() {
		File fout = new File("");
		String xml = "";

		try {
			switch (type) {
			case "layout":
				fout = File.createTempFile("output", ".xml");
				break;
			case "paging":
				fout = File.createTempFile("output", ".xml");
				break;
			case "ocr":
				fout = File.createTempFile("output", ".txt");
				break;
			}
		} catch (IOException e) {
			ErrorMessage
					.show("Exceptie la crearea fisierului temporar de output:"
							+ e.getMessage());
		}
		fout.deleteOnExit();
		this.output = fout.getAbsolutePath();

		switch (type) {
		case "layout":
			// Creaza fisier temporar de output.
			xml = this.createAnalyzerInputXML();
			break;
		case "paging":
			// Creaza fisier temporar de output.
			xml = this.createAnalyzerInputXML();
			break;

		case "ocr":
			xml = this.createOCRInputXML();
			break;
		}

		// Creaza fisier temporar de input.
		File fin = new File("");
		try {
			fin = File.createTempFile("input", ".xml");
		} catch (IOException e) {
			ErrorMessage
					.show("Exceptie la crearea fisierului temporar de input:"
							+ e.getMessage());
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
			ErrorMessage
					.show("Exceptie la scrierea in fisierul temporar de input:"
							+ e.getMessage());
		}

		// Ruleaza analizator.
		try {
			ProcessBuilder pb = new ProcessBuilder(Config.execs + "\\"
					+ this.name, fin.getAbsolutePath());
			pb.directory(new File(Config.execs));
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
