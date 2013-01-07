package elements_actions;

import gui.GElement;
import gui.LayoutGUI;
import gui.VisibleElements;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

/**
 * Combo listener pentru dropdown cu componente.
 * 
 * @author Unknown-Revengers
 */
public class ComponentComboListener implements ActionListener {
	private LayoutGUI gui;

	public ComponentComboListener(LayoutGUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		@SuppressWarnings("rawtypes")
		JComboBox cb = (JComboBox) e.getSource();
		String component = (String) cb.getSelectedItem();

		// Afiseaza elementele conform selectiei: litere, randuri, blocuri
		if (component.compareTo(VisibleElements.S_BLOCK.toString()) == 0) {
			this.gui.loadElements(VisibleElements.S_BLOCK);
		} else if (component.compareTo(VisibleElements.S_LINE.toString()) == 0) {
			this.gui.loadElements(VisibleElements.S_LINE);
		}
		
		Component[] gList = gui.getDraw().getComponents();	
		for (int i = 0; i < gList.length; i++) {
			GElement gElem = (GElement) gList[i];
			
			if(gui.visCombo.getSelectedItem().toString() == "Image"){
				gElem.setTextAreaVisible(false);
			} else {
				if(gElem.elementType.toString().compareTo(gui.visibleElements.toString()) != 0){
					gElem.setTextAreaVisible(false);
				} else {
					gElem.setTextAreaVisible(true);
				}
			}
		
		}
		
	}

}
