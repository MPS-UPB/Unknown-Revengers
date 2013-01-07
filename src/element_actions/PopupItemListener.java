package element_actions;

import gui.GElement;
import gui.GPopup;
import gui.LayoutGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import layout.ErrorMessage;
import parser.LayoutParserTreeElement;

/**
 * Button listener pentru salvare modificari.
 * 
 * @author Unknown-Revengers
 */
public class PopupItemListener implements ActionListener {

	private GElement panel;

	private LayoutGUI gui;

	/**
	 * Constructor.
	 * 
	 * @param panel
	 */
	public PopupItemListener(GElement panel, LayoutGUI gui) {
		this.panel = panel;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final String action = e.getActionCommand();

		if (action.compareTo(ElementActions.S_OCR.toString()) == 0) {
			Thread actionThread = new Thread() {
				@Override
				public void run() {
					try {
						new GetText(panel, gui.layoutParser);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};

			actionThread.start();
		} else if (action.compareTo(ElementActions.S_TEXT.toString()) == 0) {

			new ViewText(panel);

		} else if (action.compareTo(ElementActions.S_BREAK_V.toString()) == 0) {
			if (panel.element.getData().elementType == LayoutParserTreeElement.ElementType.TEXTBLOCK
					|| panel.element.getData().elementType == LayoutParserTreeElement.ElementType.BLOCK) {
				new BreakV(panel.element, this.gui.layoutParser.direction,
						((GPopup) ((JMenuItem) e.getSource()).getParent())
								.getGX(),
						((GPopup) ((JMenuItem) e.getSource()).getParent())
								.getGY(), this.gui.image.getHeight(),
						this.gui.image.getWidth());
				this.gui.loadElements(this.gui.visibleElements);
			} else {
				ErrorMessage.show(
						"You can't split vertical "
								+ panel.element.getData().elementType
										.toString() + ".", false);
			}

		} else if (action.compareTo(ElementActions.S_BREAK_H.toString()) == 0) {

			new BreakH(panel.element, this.gui.layoutParser.direction,
					((GPopup) ((JMenuItem) e.getSource()).getParent()).getGX(),
					((GPopup) ((JMenuItem) e.getSource()).getParent()).getGY(),
					this.gui.image.getHeight(), this.gui.image.getWidth());
			this.gui.loadElements(this.gui.visibleElements);

		}
		else if (action.compareTo(ElementActions.S_PAGE.toString()) == 0) {
			new PageNumberBlock(panel, gui.layoutParser);
		}
	}
}
