package element_actions;

import gui.GElement;
import gui.LayoutGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import layout.ErrorMessage;
import parser.LayoutParserTreeElement;

/**
 * Button listener pentru salvare modificari.
 * 
 * @author Unknown-Revengers
 */
public class PopupItemListener implements ActionListener {

	/**
	 * Selected panel.
	 */
	private GElement panel;

	/**
	 * LayoutGUI
	 */
	private LayoutGUI gui;

	/**
	 * Constructor.
	 * 
	 * @param panel
	 *            Selected panel
	 * @param gui
	 *            Gui
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
						this.panel.getPopup().getGX(), this.panel.getPopup()
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
					this.panel.getPopup().getGX(), this.panel.getPopup()
							.getGY(), this.gui.image.getHeight(),
					this.gui.image.getWidth());
			this.gui.loadElements(this.gui.visibleElements);

		} else if (action.compareTo(ElementActions.S_PAGE.toString()) == 0) {
			new PageNumberBlock(panel, gui.layoutParser);

		} else if (action.compareTo(ElementActions.S_DELETE.toString()) == 0) {

			// Fereastra de confirmare a actiunii
			int response = JOptionPane.showConfirmDialog(null,
					"Doriti sa continuati ?", "Confirmati",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (response == JOptionPane.YES_OPTION) {
				new DeleteElement(panel, gui);
			}
		} else if (action.compareTo(ElementActions.S_FRONT.toString()) == 0) {
			new FrontBack(panel, gui, "FRONT");

		} else if (action.compareTo(ElementActions.S_BACK.toString()) == 0) {
			new FrontBack(panel, gui, "BACK");
		} else if (action.compareTo(ElementActions.S_RESIZE.toString()) == 0) {
			new Resize(panel, gui);
		}
	}
}
