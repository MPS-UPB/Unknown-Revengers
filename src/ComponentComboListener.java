import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

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
		System.out.println(component);
	}

}
