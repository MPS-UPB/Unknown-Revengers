package element_actions;

import gui.GElement;
import gui.LayoutGUI;

/**
 * <<<<<<< HEAD Bring to Front or Send to back selected element given as
 * parameter in constructor. ======= Bring to Front or Send to Back selected
 * element given as parameter in constructor. >>>>>>> branch 'front_back' of
 * https://github.com/MPS-UPB/Unknown-Revengers.git
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
	public FrontBack(GElement panel, LayoutGUI gui, String command) {

		this.panel = panel;
		this.gui = gui;
		this.command = command;

		this.switchCommand();
	}

	/**
	 * Schitch given command an do required action
	 * 
	 * @return void
	 */
	public void switchCommand() {
		switch (command) {
		case "FRONT":
			this.gui.draw.setComponentZOrder(this.panel, 0);
			break;
		case "BACK":
			this.gui.draw.setComponentZOrder(this.panel,
					this.gui.draw.getComponentCount() - 1);
			break;
		}
	}
}
