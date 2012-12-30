import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * Button listener pentru numerotare pagina.
 * 
 * @author Unknown-Revengers
 */
public class NumeroteazaButtonListener implements ActionListener {
	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO Numerotare pagina.
		JOptionPane.showMessageDialog(null, "Numeroteaza pagina.");
	}
}
