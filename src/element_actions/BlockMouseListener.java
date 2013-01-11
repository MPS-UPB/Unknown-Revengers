package element_actions;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
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
	 * @param e MouseEvent
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		((JPanel) e.getSource()).setBorder(new LineBorder(Color.BLUE));
	}

	/**
	 * Mouse click. Selecteaza / deselecteaza element. Selectia este tinuta in
	 * tooltip.
	 * 
	 * @param e MouseEvent
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// Elementul nu este selectat => selecteaza.
		if (((JPanel) e.getSource()).getToolTipText() == null
				|| ((JPanel) e.getSource()).getToolTipText().compareTo(
						"selected") != 0) {
			((JPanel) e.getSource())
					.setBorder(new LineBorder(Color.YELLOW));
			((JPanel) e.getSource()).setToolTipText("selected");
		}

		// Elementul este selectat => deselecteaza.
		else {
			((JPanel) e.getSource())
					.setBorder(new LineBorder(Color.GREEN));
			((JPanel) e.getSource()).setToolTipText("");
		}

		if (e.isPopupTrigger()) {
			System.out.println("aaa");
		}
	}

	/**
	 * Mouse exit. Schimba culoare border in verde daca nu e selectat si in
	 * galben daca este selectat.
	 * 
	 * @param e MouseEvent
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// Elementul nu este selectat.
		if (((JPanel) e.getSource()).getToolTipText() == null
				|| ((JPanel) e.getSource()).getToolTipText().compareTo(
						"selected") != 0) {
			((JPanel) e.getSource())
					.setBorder(new LineBorder(Color.GREEN));
		}
		// Elementul este selectat.
		else {
			((JPanel) e.getSource())
					.setBorder(new LineBorder(Color.YELLOW));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
