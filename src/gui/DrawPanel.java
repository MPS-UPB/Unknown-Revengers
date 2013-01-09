package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Zona in care se deseneaza imaginea.
 * 
 * @author Unknown-Revengers
 */
@SuppressWarnings("serial")
public class DrawPanel extends JPanel {
	/**
	 * Imaginea de analizat.
	 */
	private final BufferedImage image;
	private boolean drawImageStatus;

	/**
	 * Constructor pentru zona de desenat.
	 * 
	 * @param image
	 *            Image
	 * 
	 * @return void
	 */
	public DrawPanel(BufferedImage image) {
		this.image = image;
		this.drawImageStatus = true;
	}

	/**
	 * Deseneaza imaginea in zona de desenat.
	 * 
	 * @param g
	 *            Graphics
	 * 
	 * @return void
	 */
	@Override
	public void paintComponent(Graphics g) {
		this.setSize(image.getWidth(this), image.getHeight(this));

		if (drawImageStatus == true) {
			g.drawImage(image, 0, 0, this);
		}
	}

	/**
	 * 
	 * Afiseaza sau ascunde imaginea de background
	 * 
	 * @param newStatus
	 *            Daca e true afiseaza imaginea, daca e false o sterge
	 */
	public void changeImageDrawingStatus(boolean newStatus) {
		this.drawImageStatus = newStatus;
	}
	/**
	 * Remove element from panel
	 *  
	 * @param panel
	 */
	public void DeleteElement(GElement panel) {
		this.remove(panel);
	}
}
