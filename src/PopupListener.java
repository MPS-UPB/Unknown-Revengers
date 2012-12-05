import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * Button listener pentru salvare modificari.
 * 
 * @author Unknown-Revengers
 */
public class PopupListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Realizeaza actiune selectata
		JOptionPane.showMessageDialog(null, "Realizeaza actiunea selectata: " + e.getActionCommand() + ".");

	}

}
