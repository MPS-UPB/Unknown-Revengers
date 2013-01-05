package element_actions;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

/**
 * Mouse listener pentru un block.
 * 
 * @author Unknown-Revengers
 */
public class BlockMouseListener implements MouseListener {

	/**
	 * Mouse over. Schimba culoare border in albastru.
	 * 
	 * @param e
	 *            MouseEvent
	 * 
	 * @return void
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		((JTextArea) e.getSource()).setBorder(new LineBorder(Color.BLUE));
	}

	/**
	 * Mouse click. Selecteaza / deselecteaza element. Selectia este tinuta in
	 * tooltip.
	 * 
	 * @param e
	 *            MouseEvent
	 * 
	 * @return void
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// Elementul nu este selectat => selecteaza.
		if (((JTextArea) e.getSource()).getToolTipText() == null
				|| ((JTextArea) e.getSource()).getToolTipText().compareTo(
						"selected") != 0) {
			((JTextArea) e.getSource()).setBorder(new LineBorder(Color.YELLOW));
			((JTextArea) e.getSource()).setToolTipText("selected");
		}
		// Elementul este selectat => deselecteaza.
		else {
			((JTextArea) e.getSource()).setBorder(new LineBorder(Color.GREEN));
			((JTextArea) e.getSource()).setToolTipText("");
		}
	}

	/**
	 * Mouse exit. Schimba culoare border in verde daca nu e selectat si in
	 * galben daca este selectat.
	 * 
	 * @param e
	 *            MouseEvent
	 * 
	 * @return void
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// Elementul nu este selectat.
		if (((JTextArea) e.getSource()).getToolTipText() == null
				|| ((JTextArea) e.getSource()).getToolTipText().compareTo(
						"selected") != 0) {
			((JTextArea) e.getSource()).setBorder(new LineBorder(Color.GREEN));
		}
		// Elementul este selectat.
		else {
			((JTextArea) e.getSource()).setBorder(new LineBorder(Color.YELLOW));
		}
	}

	/**
	 * Mouse pressed.
	 * 
	 * @param e
	 *            MouseEvent
	 * 
	 * @return void
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * Mouse released.
	 * 
	 * @param e
	 *            MouseEvent
	 * 
	 * @return void
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
