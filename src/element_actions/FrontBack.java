package element_actions;

import gui.GElement;
import gui.LayoutGUI;

/**
 * Bring to Front or Send to Back selected element given as parameter in
 * constructor.
 * 
 * @author Unknown-Revengers
 * 
 */
public class FrontBack {

	/**
	 * Selected panel
	 */
	private GElement panel;

	/**
	 * Choosed command
	 */
	private String command;

	/**
	 * LayoutGUI
	 */
	private LayoutGUI gui;

	/**
	 * Constructor.
	 * 
	 * @param panel
	 *            Selected Panel.
	 * @param gui
	 *            LayoutGUI.
	 * @param command
	 *            Choosed command.
	 * 
	 */
	public FrontBack(GElement panel, LayoutGUI gui, String command) {

		this.panel = panel;
		this.gui = gui;
		this.command = command;

		this.switchCommand();
	}

	/**
	 * Switch given command and do required action.
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
