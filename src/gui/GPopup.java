package gui;

import javax.swing.JPopupMenu;

public class GPopup extends JPopupMenu {
	private int gX;
	private int gY;

	public void setGX(int x) {
		this.gX = x;
	}

	public void setGY(int y) {
		this.gX = y;
	}

	public int getGX() {
		return this.gX;
	}

	public int getGY() {
		return this.gY;
	}

	public void storeLocation() {
		this.gX = this.getParent().getX() - 20;
		this.gY = this.getParent().getY() - 95;
	}
}
