package elements_actions;

import gui.LayoutGUI;
import gui.VisibleElements;

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
	private LayoutGUI gui;
	
	public ComponentComboListener(LayoutGUI gui) {
		this.gui=gui;
	}
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		
		@SuppressWarnings("rawtypes")
		JComboBox cb = (JComboBox) e.getSource();
		String component = (String) cb.getSelectedItem();

		// TODO Afiseaza elementele conform selectiei: litere, randuri, blocuri
		JOptionPane.showMessageDialog(null, "Afiseaza elementele conform selectiei: " + component + ".");
		if (component.compareTo(VisibleElements.S_BLOCK.toString()) == 0) {
			this.gui.loadElements(VisibleElements.S_BLOCK);
		}
		else if (component.compareTo(VisibleElements.S_LINE.toString()) == 0) {
			this.gui.loadElements(VisibleElements.S_LINE);
		}
	}

}
