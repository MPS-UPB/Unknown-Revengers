import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
	private JComboBox comboBox;

	/**
	 * The DrawPanel
	 */
	private JPanel pane;

	/**
	 * Contructor
	 * 
	 * @param comboBox ComboBox cu actiuni posibile pentru blocurile selectate.
	 * @param draw     The DrawPanel that contains the elements.
	 */
	public ActionButtonListener(JComboBox comboBox, JPanel draw) {
		this.comboBox = comboBox;
		this.pane = draw;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// Get selected action.
		String action = (String) comboBox.getSelectedItem();

		Component[] panels = pane.getComponents();
		for (int i = 0; i < panels.length; i++) {
			// Componentele selectate.
			if (((JPanel)panels[i]).getToolTipText() != null
					&& ((JPanel)panels[i]).getToolTipText().compareTo(
							"selected") == 0) {
				// TODO Executa actiunea pe componentele selectate.
				JOptionPane.showMessageDialog(null, "Executa actiunea '" + action + "' pe componentele selectate.");
			}
		}
	}
}
