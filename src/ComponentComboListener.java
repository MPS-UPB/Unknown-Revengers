import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 * Combo listener pentru dropdown cu componente.
 * 
 * @author Unknown-Revengers
 */
public class ComponentComboListener implements ActionListener {
	@Override
	public void actionPerformed(final ActionEvent e) {
		@SuppressWarnings("rawtypes")
		JComboBox cb = (JComboBox) e.getSource();
		String component = (String) cb.getSelectedItem();

		// TODO Afiseaza elementele conform selectiei: litere, randuri, blocuri
		JOptionPane.showMessageDialog(null, "Afiseaza elementele conform selectiei: " + component + ".");
	}

}
