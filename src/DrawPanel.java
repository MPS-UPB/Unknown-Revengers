import java.awt.Graphics;
import java.awt.Image;

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
	private Image image;

	/**
	 * Constructor pentru zona de desenat.
	 * 
	 * @param image Image
	 * 
	 * @return void
	 */
	public DrawPanel(Image image) {
		this.image = image;
	}

	/**
	 * Deseneaza imaginea in zona de desenat.
	 * 
	 * @param g Graphics
	 * 
	 * @return void
	 */
	@Override
	public void paintComponent(Graphics g) {
		this.setSize(image.getWidth(this), image.getHeight(this));
		g.drawImage(image, 0, 0, this);
	}
}