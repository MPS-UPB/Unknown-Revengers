import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import sun.java2d.SunGraphicsEnvironment;

/**
 * Interfata grafica (ar fi recomandat ca pentru fiecare TODO sa existe o metoda
 * / mai multe care fac acel lucru. Astfel, constructorul nu va avea sute de
 * linii). Probabil e bine ca listenerele sa fie declarate in alte fisiere, din
 * acelasi motiv.
 * 
 * @author Unknown-Revengers
 */
@SuppressWarnings("serial")
public class LayoutGUI extends JFrame {

	// Layout parser.
	LayoutParser layoutParser;

	// Imaginea ce este incarcata.
	Image image;

	// Panelul in care se deseneaza imaginea.
	DrawPanel draw;

	// Scroll pane-ul in care se pun elementele din imagine.
	JScrollPane scrollPane;

	public LayoutGUI(LayoutParser layoutParser) {

		this.layoutParser = layoutParser;

		// TODO Incarca imaginea in fereastra. Trebuie luata din layoutParser
		// imaginea.
		File f = new File("resources\\3-sizes.jpg");
		this.image = new ImageIcon(f.getAbsolutePath()).getImage();

		// Initializeaza fereastra.
		this.initFrame();

		// Incarca zona in care se deseanza.
		this.loadDrawZone();

		// Incarca elementele din pagina.
		this.loadElements();

		// Adauga filtre si actiuni pentru document/elemente.
		this.addFilters();

		// Actiuni finale asupra documentului.
		this.addFinalActions();

		// Afiseaza fereastra.
		this.setVisible(true);
	}

	/**
	 * Initializeaza fereastra
	 * 
	 * @return void
	 */
	private void initFrame() {

		// Seteaza layout.
		getContentPane().setLayout(null);

		// Seteaza window maximized.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setUndecorated(false);
		this.pack();
		GraphicsConfiguration config = this.getGraphicsConfiguration();
		Rectangle usableBounds = SunGraphicsEnvironment.getUsableBounds(config
				.getDevice());
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setMaximizedBounds(new Rectangle(0, 0, usableBounds.width,
				usableBounds.height));

		// Info label 1.
		JLabel lblNewLabel = new JLabel(
				"Pentru a selecta mai multe componente se va da click pe o componenta. "
						+ "Click pe o componenta selectata o va deselecta.");
		lblNewLabel.setBounds(20, 50, this.getMaximizedBounds().width - 40, 15);
		getContentPane().add(lblNewLabel);

		// Info label 2.
		JLabel lblClickDreaptaPe = new JLabel(
				"Click dreapta pe o componenta pentru actiunile specifice componentei.");
		lblClickDreaptaPe.setBounds(20, 65,
				this.getMaximizedBounds().width - 40, 15);
		getContentPane().add(lblClickDreaptaPe);
	}

	/**
	 * Inizializeaza zona in care se va desena.
	 * 
	 * @return void
	 */
	private void loadDrawZone() {
		// Initializeaza draw panel.
		draw = new DrawPanel(image);
		draw.setPreferredSize(new Dimension(image.getWidth(this), image
				.getHeight(this)));
		draw.setLayout(null);

		// Inizializeaza scroll panel si adauga draw panel in el.
		scrollPane = new JScrollPane(draw);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(20, 95, this.getMaximizedBounds().width - 40,
				this.getMaximizedBounds().height - 180);

		// Adauga scroll panel in fereastra.
		getContentPane().add(scrollPane);
	}

	/**
	 * TODO Incarca elementele in fereastra parsand fisierul de layout (se
	 * folosesc functii din this.layoutParser)
	 * 
	 * @return void
	 */
	private void loadElements() {

		/**
		 * TODO Incarca elemente
		 */
		/*
		 * Element 1
		 */
		JPanel panel = new JPanel();
		panel.addMouseListener(new BlockMouseListener());
		panel.setBorder(new LineBorder(Color.GREEN));
		panel.setOpaque(false);
		panel.setBounds(10, 20, 209, 87);
		draw.add(panel);

		JPopupMenu popupMenu = new JPopupMenu();
		// Face analiza OCR.
		popupMenu.add(new JMenuItem("Analiza OCR"));
		// Sparge blocul de text.
		popupMenu.add(new JMenuItem("Sparge bloc text"));
		// Marcheaza blocul de text ca fiind numar pagina.
		popupMenu.add(new JMenuItem("Este numar pagina"));
		panel.setComponentPopupMenu(popupMenu);

		/*
		 * Element 2
		 */
		JPanel panel_1 = new JPanel();
		panel_1.addMouseListener(new BlockMouseListener());
		panel_1.setOpaque(false);
		panel_1.setBorder(new LineBorder(Color.GREEN));
		panel_1.setBounds(230, 20, 209, 87);
		draw.add(panel_1);

		JPopupMenu popupMenu_1 = new JPopupMenu();
		// Face analiza OCR.
		popupMenu_1.add(new JMenuItem("Analiza OCR"));
		// Sparge blocul de text.
		popupMenu_1.add(new JMenuItem("Sparge bloc text"));
		// Marcheaza blocul de text ca fiind numar pagina.
		popupMenu_1.add(new JMenuItem("Este numar pagina"));
		panel_1.setComponentPopupMenu(popupMenu_1);
	}

	/**
	 * TODO Adauga filtre pentru selectia elementelor din imagine.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addFilters() {

		// Label componentente.
		JLabel lblComponente = new JLabel("Componente:");
		lblComponente.setBounds(20, 20, 75, 20);
		getContentPane().add(lblComponente);

		/*
		 * Dropdown componenta - atunci cand se schimba selectia, trebui sa se
		 * schimbe si ceea ce este afisat peste imagine (litere, randuri sau
		 * blocuri).
		 */
		JComboBox comboElements = new JComboBox();
		comboElements.setModel(new DefaultComboBoxModel(new String[] {
				"Litere", "Randuri", "Blocuri" }));
		comboElements.setBounds(105, 20, 125, 20);
		getContentPane().add(comboElements);

		// Actiuni posibile pentru selectia curenta.
		JLabel lblActiuni = new JLabel("Actiuni:");
		lblActiuni.setBounds(this.getMaximizedBounds().width - 310, 20, 50, 25);
		getContentPane().add(lblActiuni);

		/*
		 * Se poate face (DOAR PENTRU COMPONENTELE SELECTATE). Componentele
		 * selectate pot fi luate verificand daca in tooltip este scris
		 * selected. Vezi mouseClicked din BlockMouseListener. - analiza ocr -
		 * unire componente - spargere componente
		 */
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {
				"Analiza OCR", "Uneste comonente", "Sparge componente" }));
		comboBox.setBounds(this.getMaximizedBounds().width - 250, 20, 160, 25);
		getContentPane().add(comboBox);

		// OK pentru actiunile posibile.
		JButton btnNewButton = new JButton("OK");
		btnNewButton
				.setBounds(this.getMaximizedBounds().width - 80, 20, 60, 25);
		getContentPane().add(btnNewButton);
	}

	/**
	 * TODO Buton pentru salvat schimbarile facute intr-un fisier de output.
	 */
	private void addFinalActions() {
		// TODO Ruleaza modul de numerotare a paginii.
		JButton btnNumeroteaza = new JButton("Numeroteaza pagina");
		btnNumeroteaza.setBounds(this.getMaximizedBounds().width - 290,
				this.getMaximizedBounds().height - 70, 170, 23);
		getContentPane().add(btnNumeroteaza);

		// TODO Salveaza schimbarile facute intr-un fisier de output.
		JButton btnSalveaza = new JButton("Salveaza");
		btnSalveaza.setBounds(this.getMaximizedBounds().width - 110,
				this.getMaximizedBounds().height - 70, 90, 23);
		getContentPane().add(btnSalveaza);
	}
}
