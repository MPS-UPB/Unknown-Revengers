package element_actions;

import gui.ElementJPanel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author Unknown-Revengers
 * 
 *         Frame pentru afisarea textului unui element.
 */
@SuppressWarnings("serial")
public class ViewText extends JFrame {
	/**
	 * Fereastra curenta.
	 */
	JFrame frame = this;

	/**
	 * Content panel.
	 */
	private Container contentPanel;

	/**
	 * Save button.
	 */
	private JButton saveBttn;

	/**
	 * Elementul selectat.
	 */
	public ElementJPanel elementPanel;

	/**
	 * TextArea pentru textul elementului.
	 */
	JTextArea descriptionArea;

	/**
	 * Constructor
	 * 
	 * @param panel
	 *            Elementul selectat.
	 */
	public ViewText(ElementJPanel panel) {
		this.elementPanel = panel;

		// Creaza content panel.
		contentPanel = this.getContentPane();

		// Adauga textul elementului la frame.
		this.addText(elementPanel.element.text);

		// Adauga buton.
		this.addButton();

		// Init frame
		this.initFrame();
	}

	/**
	 * Adauga buton pentru Cancel si buton pentru Save.
	 */
	private void addButton() {
		// New Panel.
		JPanel panel = new JPanel();

		// Creeaza buton de save.
		saveBttn = new JButton("Save");

		// Adauga listener pentru butonul de save.
		this.saveBttn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Save text.
				elementPanel.element.text = descriptionArea.getText();

				frame.dispose();
			}
		});

		panel.add(saveBttn);
		contentPanel.add(panel, BorderLayout.SOUTH);
	}

	/**
	 * Adauga text la fereastra.
	 * 
	 * @param text
	 *            Textul de adaugat.
	 */
	private void addText(String text) {
		// New Panel.
		JPanel panel = new JPanel();

		// Creaza Text Area pentru text.
		descriptionArea = new JTextArea(text);
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setEditable(true);

		// Adauga Text Area la un scroll panel.
		JScrollPane spanel = new JScrollPane(descriptionArea);
		spanel.setPreferredSize(new Dimension(200, 100));

		// Adauga scroll panel la content panel.
		panel = new JPanel();
		panel.setSize(200, 100);
		panel.add(spanel);

		contentPanel.add(panel, BorderLayout.CENTER);
	}

	/**
	 * Initializeaza frame.
	 */
	private void initFrame() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(300, 220);
		this.setVisible(true);

		setLocationRelativeTo(null);
	}
}
