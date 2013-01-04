package element_actions;

import gui.ElementJPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import parser.LayoutParser;

/**
 * Button listener pentru salvare modificari.
 * 
 * @author Unknown-Revengers
 */
public class PopupListener implements ActionListener {

	private final ElementJPanel panel;

	private final LayoutParser layoutParser;

	/**
	 * Constructor.
	 * 
	 * @param panel
	 */
	public PopupListener(ElementJPanel panel, LayoutParser lp) {
		this.panel = panel;
		this.layoutParser = lp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final String action = e.getActionCommand();

		Thread actionThread = new Thread() {
			@Override
			public void run() {
				try {
					if (action.compareTo(ElementActions.S_OCR.toString()) == 0) {
						new GetText(panel, layoutParser);
					}
					else if (action.compareTo(ElementActions.S_TEXT.toString()) == 0) {
						new ViewText(panel);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		actionThread.start();
	}
}
