package gui;

import javax.swing.JPopupMenu;

/**
 * Extend the basic JPopupMenu in order to add 2 new fields that store the
 * location where the popup is initial shown.
 * 
 * @author Unknown-Revengers
 * 
 */
public class GPopup extends JPopupMenu {
	private int gX;
	private int gY;

	public void setGX(int x) {
		this.gX = x;
	}

	public void setGY(int y) {
		this.gY = y;
	}

	public int getGX() {
		return this.gX;
	}

	public int getGY() {
		return this.gY;
	}
}
