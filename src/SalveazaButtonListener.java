import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.xml.transform.TransformerException;

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
	SalveazaButtonListener(final LayoutParser layoutParser) {
		this.layoutParser = layoutParser;
	}
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		
		FileWriter fstream = null;
		try {
			fstream = new FileWriter("resources\\3-sizes-out.xml");
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
}
