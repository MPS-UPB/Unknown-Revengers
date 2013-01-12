package page_actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import parser.LayoutParser;

/**
 * Button listener pentru salvare modificari.
 * 
 * @author Unknown-Revengers
 */
public class SalveazaButtonListener implements ActionListener {
	private LayoutParser layoutParser;

	/**
	 * 
	 * @param layoutParser Instanta a LayoutParser-ului care contine metoda de
	 *            salvare a arborelui ce abstractizeaza pagina
	 * 
	 */
	public SalveazaButtonListener(LayoutParser layoutParser) {
		this.layoutParser = layoutParser;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		layoutParser.saveXML(true);
	}
}
