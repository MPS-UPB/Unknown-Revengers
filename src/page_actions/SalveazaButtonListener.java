package page_actions;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.xml.transform.TransformerException;

import layout.ErrorMessage;

import parser.LayoutParser;

/**
 * Button listener pentru salvare modificari.
 * 
 * @author Unknown-Revengers
 */
public class SalveazaButtonListener implements ActionListener {
	LayoutParser layoutParser;
	
	/**
	 * 
	 * @param layoutParser
	 *   Instanta a LayoutParser-ului care contine metoda de salvare
	 *   a arborelui ce abstractizeaza pagina
	 * 
	 */
	public SalveazaButtonListener(final LayoutParser layoutParser) {
		this.layoutParser = layoutParser;
	}
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		layoutParser.saveXML(true);
	}
}
