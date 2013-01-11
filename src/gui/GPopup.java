package gui;

import javax.swing.JPopupMenu;

/**
 * @author Unknown-Revengers
 * 
 */
@SuppressWarnings("serial")
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
		this.gX = y;
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

	/**
	 * Store location.
	 */
	public void storeLocation() {
		this.gX = this.getParent().getX() - 20;
		this.gY = this.getParent().getY() - 95;
	}
}
