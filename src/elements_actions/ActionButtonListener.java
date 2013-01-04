package elements_actions;

import gui.ElementJPanel;
import gui.LayoutGUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import layout.ErrorMessage;

/**
 * Button listener pentru OK actiuni.
 * 
 * @author Unknown-Revengers
 */
public class ActionButtonListener implements ActionListener {
	/**
	 * ComboBox cu actiuni posibile pentru blocurile selectate.
	 */
	@SuppressWarnings("rawtypes")
	private final JComboBox comboBox;

	/**
	 * The DrawPanel
	 */
	private final JPanel pane;

	private final LayoutGUI layoutGUI;

	/**
	 * Contructor
	 * 
	 * @param comboBox
	 *            ComboBox cu actiuni posibile pentru blocurile selectate.
	 * @param draw
	 *            The DrawPanel that contains the elements.
	 * @param layoutGUI
	 *            The GUI
	 */
	public ActionButtonListener(JComboBox comboBox, JPanel draw,
			LayoutGUI layoutGUI) {
		this.comboBox = comboBox;
		this.layoutGUI = layoutGUI;
		this.pane = draw;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// Get selected action.
		String action = (String) comboBox.getSelectedItem();

		// Get all panels that are drawn.
		Component[] panels = pane.getComponents();

		// Selected panels.
		ArrayList<ElementJPanel> elementPanels = new ArrayList<ElementJPanel>();

		for (Component panel : panels) {
			// Selected panels.
			if (((ElementJPanel) panel).getToolTipText() != null
					&& ((ElementJPanel) panel).getToolTipText().compareTo(
							"selected") == 0) {
				elementPanels.add((ElementJPanel) panel);
			}
		}

		if (elementPanels.size() == 0) {
			ErrorMessage.show("Nu a fost selectat niciun element", false);
		}
		else if (action.compareTo(ElementsActions.S_OCR.toString()) == 0) {

		}
		else if (action.compareTo(ElementsActions.S_GLUE.toString()) == 0) {
			new GlueElements(elementPanels, this.layoutGUI);
		}
	}
}
