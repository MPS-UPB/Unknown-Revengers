package element_actions;

import gui.DrawPanel;
import gui.GElement;
import gui.GPopup;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

public class PopupItemMouseListener implements MouseListener {

	JLabel label;

	GElement panel;

	DrawPanel draw;

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
			x = ((GPopup) ((JMenuItem) arg0.getSource()).getParent())
					.getParent().getX() - 20;
			width = 1;
			y = this.panel.getBounds().y;
			height = this.panel.getBounds().height;
		} else if (((JMenuItem) arg0.getSource()).getText() == ElementActions.S_BREAK_H
				.toString()) {
			y = ((GPopup) ((JMenuItem) arg0.getSource()).getParent())
					.getParent().getY() - 95;
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
