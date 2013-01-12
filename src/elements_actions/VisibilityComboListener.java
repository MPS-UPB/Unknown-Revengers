package elements_actions;

import gui.GElement;
import gui.LayoutGUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

/**
 * @author Unknown-Revengers
 * 
 */
public class VisibilityComboListener implements ActionListener {
	private LayoutGUI gui;

	/**
	 * Constructor.
	 * 
	 * @param gui GUI.
	 */
	public VisibilityComboListener(LayoutGUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		@SuppressWarnings("rawtypes")
		JComboBox cb = (JComboBox) e.getSource();
		String component = (String) cb.getSelectedItem();

		// Afiseaza sau ascunde imaginea de background
		if (component.compareTo(ElementsVisibility.S_IMAGE.toString()) == 0) {
			gui.getDraw().changeImageDrawingStatus(true);
		} else {
			gui.getDraw().changeImageDrawingStatus(false);
		}

		// Afiseaza elementele conform selectiei: randuri, blocuri
		Component[] gList = gui.getDraw().getComponents();

		for (Component element : gList) {
			GElement gElem = (GElement) element;

			if (gui.visCombo.getSelectedItem().toString()
					.compareTo(ElementsVisibility.S_IMAGE.toString()) == 0) {
				gElem.setTextAreaVisible(false);
			} else {
				if (gElem.element.getData().elementType == gui.visibleElements
						.toType()) {
					gElem.setTextAreaVisible(true);
				} else {
					gElem.setTextAreaVisible(false);
				}
			}
		}

	}
}