package elements_actions;

import gui.GElement;
import gui.LayoutGUI;
import gui.VisibleElements;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;

import parser.LayoutParserTreeElement;
import tree.GenericTreeNode;
import tree.GenericTreeTraversalOrderEnum;

public class VisibilityComboListener implements ActionListener {
	private LayoutGUI gui;

	public VisibilityComboListener(LayoutGUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		@SuppressWarnings("rawtypes")
		JComboBox cb = (JComboBox) e.getSource();
		String component = (String) cb.getSelectedItem();
		
		// Afiseaza sau ascunde fiecare TextArea apartinand GElement
		Component[] gList = gui.getDraw().getComponents();	
		for (int i = 0; i < gList.length; i++) {
			GElement gElem = (GElement) gList[i];
			gElem.toggleTextAreaVisible();		
		}
		
		// Afiseaza sau ascunde imaginea de background
		if(component.compareTo("Image") == 0) {
			gui.getDraw().changeImageDrawingStatus(true);
		} else {
			gui.getDraw().changeImageDrawingStatus(false);
		}
	}
}
