package element_actions;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import gui.GElement;
import gui.LayoutGUI;

/**
 * Bring to Front or Send to Back selected element given as parameter in constructor.
 * 
 * @author Unknown-Revengers
 *
 */
public class FrontBack {
	
	// Selected panel
	private GElement panel;
	
	// Choosed command
	private String command;
	
	// LayoutGUI
	private LayoutGUI gui;

	/**
	 * Constructor.
	 * 
	 * @param GElement
	 *            panel
	 * @param String
	 *            command
	 * @param LayoutGUI
	 *            gui
	 */
	public FrontBack(GElement panel, LayoutGUI gui,  String command) {

		this.panel = panel;
		this.gui = gui;
		this.command =  command;
		
		this.switchCommand();
	}
	
	/**
	 *  Schitch given command an do required action
	 *  
	 *  @return void
	 */
	public void switchCommand() {
		
		// List with all ZOrder values in container
		ArrayList<Integer> listVal = new ArrayList<Integer>();
		for(int i = 0; i < gui.draw.getComponentCount(); i++) {
			listVal.add(gui.draw.getComponentZOrder(gui.draw.getComponent(i)));
		}
		
		int max = Collections.max(listVal);
		int min = Collections.min(listVal);
		
		switch(command) {
		case "FRONT":
			this.gui.draw.setComponentZOrder(this.panel, max - 1);
		case "BACK":
			this.gui.draw.setComponentZOrder(this.panel, min + 1);
		}
	}
}
