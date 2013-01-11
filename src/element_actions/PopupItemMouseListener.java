package element_actions;

import gui.DrawPanel;
import gui.GElement;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

/**
 * @author Unknown-Revengers
 * 
 */
public class PopupItemMouseListener implements MouseListener {

	JLabel label;

	GElement panel;

	DrawPanel draw;

	/**
	 * Constructor.
	 * 
	 * @param panel Element panel.
	 * @param draw Draw Panel.
	 */
	public PopupItemMouseListener(GElement panel, DrawPanel draw) {
		this.panel = panel;
		this.draw = draw;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.draw.remove(this.label);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;

		if (((JMenuItem) arg0.getSource()).getText() == ElementActions.S_BREAK_V
				.toString()) {
			x = this.panel.getPopup().getGX();
			width = 1;
			y = this.panel.getBounds().y;
			height = this.panel.getBounds().height;

		} else if (((JMenuItem) arg0.getSource()).getText() == ElementActions.S_BREAK_H
				.toString()) {
			y = this.panel.getPopup().getGY();

			width = this.panel.getBounds().width;
			x = this.panel.getBounds().x;
			height = 1;
		}

		this.label = new JLabel();
		this.label.setBounds(x, y, width, height);
		this.label.setOpaque(true);
		this.label.setBackground(Color.GREEN);

		this.draw.add(this.label);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.draw.remove(this.label);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		this.draw.remove(this.label);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		this.draw.remove(this.label);
	}

}
