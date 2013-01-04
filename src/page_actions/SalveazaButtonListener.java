package page_actions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * Button listener pentru salvare modificari.
 * 
 * @author Unknown-Revengers
 */
public class SalveazaButtonListener implements ActionListener {
	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO Salveaza modificari.
		JOptionPane.showMessageDialog(null, "Salveaza modificari.");
	}
}
