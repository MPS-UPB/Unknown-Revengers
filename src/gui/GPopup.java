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

	/**
	 * Setter for gX attribute.
	 * 
	 * @param x Coordinate.
	 */
	public void setGX(int x) {
		this.gX = x;
	}

	/**
	 * Setter for gY attribute.
	 * 
	 * @param y Coordinate.
	 */
	public void setGY(int y) {
		this.gY = y;
	}

	/**
	 * Getter for gX attribute.
	 * 
	 * @return int
	 */
	public int getGX() {
		return this.gX;
	}

	/**
	 * Getter for gY attribute.
	 * 
	 * @return int
	 */
	public int getGY() {
		return this.gY;
	}
}
