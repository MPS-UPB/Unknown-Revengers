package element_actions;

import gui.GElement;
import gui.LayoutGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import layout.ErrorMessage;
import parser.Direction;

/**
 * Resize an element.
 * 
 * @author Unknown-Revengers
 */
@SuppressWarnings("serial")
public class Resize extends JFrame {

	/**
	 * Preview button.
	 */
	private JButton previewBttn;

	/**
	 * Save button.
	 */
	private JButton saveBttn;

	/**
	 * Dimensiuni.
	 */
	private JTextField textHeight;
	private JTextField textY;
	private JTextField textX;
	private JTextField textWidth;
	private JLabel labelHeight;
	private JLabel labelY;
	private JLabel labelX;
	private JLabel labelWidth;

	/**
	 * Fereastra curenta.
	 */
	private Resize frame;

	private JPanel previewPanel;

	/**
	 * Constructor.
	 * 
	 * @param pan JPanel pe care s-a facut actiunea.
	 * @param gui GUI
	 */
	public Resize(GElement pan, LayoutGUI gui) {
		frame = this;

		// New Panel.
		JPanel panel = new JPanel(new GridLayout(4, 2));

		// Creaza content panel.
		Container contentPanel = getContentPane();

		// Init the preview panel.
		previewPanel = new JPanel();
		previewPanel.setBorder(new LineBorder(Color.MAGENTA));
		previewPanel.setOpaque(false);

		// Button listeners.
		ActionListener saveListener = new SaveListenter(pan, gui);
		ActionListener previewListener = new PreviewListenter(pan, gui);

		// X
		labelX = new JLabel();
		textX = new JTextField();
		labelX.setText("X");
		textX.setText(Integer.toString(pan.getX()));
		panel.add(labelX);
		panel.add(textX);

		// Y
		labelY = new JLabel();
		textY = new JTextField();
		labelY.setText("Y");
		textY.setText(Integer.toString(pan.getY()));
		panel.add(labelY);
		panel.add(textY);

		// Width
		labelWidth = new JLabel();
		textWidth = new JTextField();
		labelWidth.setText("Width");
		textWidth.setText(Integer.toString(pan.getWidth()));
		panel.add(labelWidth);
		panel.add(textWidth);

		// Height
		labelHeight = new JLabel();
		textHeight = new JTextField();
		labelHeight.setText("Height");
		textHeight.setText(Integer.toString(pan.getHeight()));
		panel.add(labelHeight);
		panel.add(textHeight);

		// Draw panel.
		contentPanel.add(panel, BorderLayout.CENTER);

		// Adauga buton de preview.
		panel = new JPanel(new GridLayout(1, 2));
		previewBttn = new JButton("Preview");
		previewBttn.addActionListener(previewListener);
		panel.add(previewBttn);

		// Adauga buton de save.
		saveBttn = new JButton("Save");
		saveBttn.addActionListener(saveListener);
		panel.add(saveBttn);
		contentPanel.add(panel, BorderLayout.SOUTH);

		this.initFrame();
	}

	/**
	 * Validate new data.
	 * 
	 * @param x Coordinate.
	 * @param y Coordinate.
	 * @param width New width for element.
	 * @param height New height for element.
	 * @param iWidth Image width.
	 * @param iHeight Image height.
	 * 
	 * @return boolean
	 */
	public boolean validateNewData(int x, int y, int width, int height,
			int iWidth, int iHeight) {

		// Check positive values.
		if (x < 0 || y < 0 || width < 0 || height < 0) {
			return false;
		}

		// Check sizes.
		if (x + width > iWidth || y + height > iHeight) {
			return false;
		}

		return true;
	}

	/**
	 * Initialize frame.
	 */
	private void initFrame() {

		// Dispose frame.
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Set size.
		this.setSize(300, 220);
		this.setVisible(true);

		// Set location.
		setLocationRelativeTo(null);
	}

	/**
	 * Listener pentru butonul de preview.
	 * 
	 * @author Unknown-Revengers
	 */
	class PreviewListenter implements ActionListener {
		GElement panel;
		LayoutGUI gui;

		/**
		 * Constructor.
		 * 
		 * @param panel JPanel pe care s-a facut actiunea.
		 * @param gui GUI
		 */
		PreviewListenter(GElement panel, LayoutGUI gui) {
			this.panel = panel;
			this.gui = gui;

		}

		@Override
		public void actionPerformed(final ActionEvent e) {

			// Get params.
			int height = Integer.parseInt(textHeight.getText());
			int y = Integer.parseInt(textY.getText());
			int x = Integer.parseInt(textX.getText());
			int width = Integer.parseInt(textWidth.getText());

			if (frame.validateNewData(x, y, width, height,
					this.gui.getImage().getWidth(), this.gui.getImage()
							.getHeight())) {
				// Set bounds for preview panel.
				previewPanel.setBounds(x, y, width, height);

				// Draw preview panel in GUI.
				this.gui.getDraw().add(previewPanel);
			} else {
				ErrorMessage.show("Invalid new data.", false);
			}
		}
	}

	/**
	 * Listener pentru butonul de save.
	 * 
	 * @author Unknown-Revengers
	 */
	class SaveListenter implements ActionListener {
		GElement panel;
		LayoutGUI gui;

		/**
		 * Constructor.
		 * 
		 * @param panel JPanel pe care s-a facut actiunea.
		 * @param gui GUI
		 */
		SaveListenter(GElement panel, LayoutGUI gui) {
			this.panel = panel;
			this.gui = gui;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			// Get params.
			int height = Integer.parseInt(textHeight.getText());
			int y = Integer.parseInt(textY.getText());
			int x = Integer.parseInt(textX.getText());
			int width = Integer.parseInt(textWidth.getText());

			if (frame.validateNewData(x, y, width, height,
					this.gui.getImage().getWidth(), this.gui.getImage()
							.getHeight())) {

				// Set data in element.
				this.panel.element.getData().left = x;
				this.panel.element.getData().right = x + width;
				this.panel.element.getData().top = y;
				this.panel.element.getData().bottom = y + height;

				// Update element data if direction is ASCENDING.
				if (this.gui.getLayoutParser().direction == Direction.ASCENDING) {
					this.panel.element.getData().top = this.gui.getImage()
							.getHeight() - (y + height);
					this.panel.element.getData().bottom = this.gui.getImage()
							.getHeight() - y;
				}

				// Set panel bounds.
				this.panel.setBounds(x, y, width, height);
				this.panel.setTextArea(width, height, false);

				// Remove the preview panel.
				this.gui.getDraw().remove(previewPanel);

				// Redraw the old panel.
				this.gui.getDraw().add(panel);
			} else {
				ErrorMessage.show("Invalid new data.", false);
			}

			// Close Resize windows.
			frame.dispose();
		}
	}
}
