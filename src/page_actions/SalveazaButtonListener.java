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
		String fileName = getSavedFileName();
		
		if(fileName == null){
			return;
		}
		
		FileWriter fstream = null;
		try {
			fstream = new FileWriter(fileName);
		} catch (IOException e1) {
			ErrorMessage.show("Eroare cand a fost deschis stream-ul de scriere");
			e1.printStackTrace();
		}
		
		BufferedWriter out = new BufferedWriter(fstream);
		
		try {
			out.write(layoutParser.constructXml());
		} catch (IOException e1) {
			ErrorMessage.show("Eroare cand a fost scris fisierul de modificari");
			e1.printStackTrace();
		} catch (TransformerException e1) {
			ErrorMessage.show("Eroare in momentul construirii XML-ului de output");
			e1.printStackTrace();
		}
		
		// Inchide fisierul de output
		try {
			out.close();
		} catch (IOException e1) {
			ErrorMessage.show("Eroare cand a fost inchis stream-ul de scriere");
			e1.printStackTrace();
		}

		//layoutParser.
		JOptionPane.showMessageDialog(null, "Modificarile au fost salvate cu succes!");
	}
	
	private String getSavedFileName() {
		String fullPath = "";
		
		FileDialog fileDialog = new FileDialog(new Frame(), "Save", FileDialog.SAVE);
        fileDialog.setFilenameFilter(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        });
        fileDialog.setFile("image_save.xml");
        fileDialog.setVisible(true);
        
        fullPath = fileDialog.getDirectory() + fileDialog.getFile();
        
        return fullPath;
	}
}
