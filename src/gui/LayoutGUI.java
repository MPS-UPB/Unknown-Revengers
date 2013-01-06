package gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import page_actions.NumeroteazaButtonListener;
import page_actions.SalveazaButtonListener;
import parser.Direction;
import parser.LayoutParser;
import parser.LayoutParserTreeElement;
import sun.java2d.SunGraphicsEnvironment;
import tree.GenericTreeNode;
import tree.GenericTreeTraversalOrderEnum;
import element_actions.ElementActions;
import element_actions.PopupItemListener;
import element_actions.PopupItemMouseListener;
import element_actions.PopupListener;
import elements_actions.ActionButtonListener;
import elements_actions.ComponentComboListener;
import elements_actions.ElementsActions;

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
	/**
	 * Layout parser.
	 */
	public LayoutParser layoutParser;

	/**
	 * Imaginea ce este incarcata.
	 */
	public BufferedImage image;

	/**
	 * Panelul in care se deseneaza imaginea.
	 */
	public DrawPanel draw;

	/**
	 * Scroll pane-ul in care se pun elementele din imagine.
	 */
	private JScrollPane scrollPane;

	public VisibleElements visibleElements;

	/**
	 * Constructor.
	 * 
	 * @param layoutParser
	 * @throws IOException
	 */
	public LayoutGUI(LayoutParser layoutParser) throws IOException {

		this.layoutParser = layoutParser;

		// Incarca imaginea in fereastra. Trebuie luata din layoutParser
		// imaginea.
		File f = new File(this.layoutParser.imagePath);
		this.image = ImageIO.read(f);

		// Initializeaza fereastra.
		this.initFrame();

		// Incarca zona in care se deseanza.
		this.loadDrawZone();

		// Adauga filtre si actiuni pentru document/elemente.
		this.addFilters();

		// Incarca elementele din pagina.
		this.loadElements(VisibleElements.S_BLOCK);

		// Actiuni e asupra documentului.
		this.addActions();

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
	 * Incarca elementele in fereastra parsand fisierul de layout (se folosesc
	 * functii din this.layoutParser)
	 * 
	 * @return void
	 */
	public void loadElements(VisibleElements type) {

		this.visibleElements = type;

		// Remove all drawed elements.
		draw.removeAll();

		List<GenericTreeNode<LayoutParserTreeElement>> list = this.layoutParser.XMLTree
				.build(GenericTreeTraversalOrderEnum.PRE_ORDER);

		// Go through all elements.
		for (int i = 0; i < list.size(); i++) {

			LayoutParserTreeElement e = list.get(i).getData();

			// Check if element is of selected type.
			if (e.elementType == type.toType()) {

				GElement panel = new GElement(list.get(i));

				// Set height and width so that the element is visible..
				int width = e.right - e.left > 1 ? e.right - e.left : 3;
				int height = e.bottom - e.top > 1 ? e.bottom - e.top : 3;

				// Check direction.
				if (this.layoutParser.direction == Direction.DESCENDING) {
					panel.setBounds(e.left, e.top, width, height);
				} else {
					int m_height = this.image.getHeight(this);
					panel.setBounds(e.left, m_height - e.bottom, width, height);
				}

				// Draw panel
				draw.add(panel);

				// Create the popup menu for the current panel
				JPopupMenu popupMenu = new GPopup();

				/*
				 * Action listener pentru popupMenu Primeste ca parametru
				 * ElementJPanel pentru a extrage LayoutParserTreeElement
				 */
				ActionListener actionListener = new PopupItemListener(panel,
						this);

				// Face analiza OCR.
				JMenuItem ocrItem = new JMenuItem(
						ElementActions.S_OCR.toString());
				ocrItem.addActionListener(actionListener);
				popupMenu.add(ocrItem);

				// Sparge blocul de text orizontal.
				JMenuItem splitItemH = new JMenuItem(
						ElementActions.S_BREAK_H.toString());
				splitItemH.addActionListener(actionListener);
				splitItemH.addMouseListener(new PopupItemMouseListener(panel,
						draw));
				popupMenu.add(splitItemH);

				// Sparge blocul de text veritcal.
				JMenuItem splitItemV = new JMenuItem(
						ElementActions.S_BREAK_V.toString());
				splitItemV.addActionListener(actionListener);
				splitItemV.addMouseListener(new PopupItemMouseListener(panel,
						draw));
				popupMenu.add(splitItemV);

				// Marcheaza blocul de text ca fiind numar pagina.
				JMenuItem paginaItem = new JMenuItem(
						ElementActions.S_PAGE.toString());
				paginaItem.addActionListener(actionListener);
				popupMenu.add(paginaItem);

				// Marcheaza blocul de text ca fiind numar pagina.
				JMenuItem deleteItem = new JMenuItem(
						ElementActions.S_DELETE.toString());
				deleteItem.addActionListener(actionListener);
				popupMenu.add(deleteItem);

				// Vezi textul.
				JMenuItem textItem = new JMenuItem(
						ElementActions.S_TEXT.toString());
				textItem.addActionListener(actionListener);
				popupMenu.add(textItem);

				panel.setComponentPopupMenu(popupMenu);
				popupMenu.addPopupMenuListener(new PopupListener());
			}
		}

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
				VisibleElements.S_BLOCK.toString(),
				VisibleElements.S_LINE.toString() }));
		// Adauga listener pentru combo cu componente.
		comboElements.addActionListener(new ComponentComboListener(this));
		comboElements.setBounds(105, 20, 125, 20);
		getContentPane().add(comboElements);

		// Label actiuni posibile pentru selectia curenta.
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
				ElementsActions.S_OCR.toString(),
				ElementsActions.S_GLUE.toString() }));
		comboBox.setBounds(this.getMaximizedBounds().width - 250, 20, 160, 25);
		getContentPane().add(comboBox);

		// OK pentru actiunile posibile.
		JButton btnNewButton = new JButton("OK");
		btnNewButton
				.setBounds(this.getMaximizedBounds().width - 80, 20, 60, 25);
		// Adauga listener pentru combo cu actiuni.
		btnNewButton.addActionListener(new ActionButtonListener(comboBox, draw,
				this));
		getContentPane().add(btnNewButton);
	}

	/**
	 * TODO Buton pentru salvat schimbarile facute intr-un fisier de output.
	 */
	private void addActions() {
		// TODO Ruleaza modul de numerotare a paginii.
		JButton btnNumeroteaza = new JButton("Numeroteaza pagina");
		btnNumeroteaza.setBounds(this.getMaximizedBounds().width - 290,
				this.getMaximizedBounds().height - 70, 170, 23);
		// Adauga listener pentru buton de numerotare pagina.
		btnNumeroteaza.addActionListener(new NumeroteazaButtonListener());
		getContentPane().add(btnNumeroteaza);

		JButton btnSalveaza = new JButton("Salveaza");
		btnSalveaza.setBounds(this.getMaximizedBounds().width - 110,
				this.getMaximizedBounds().height - 70, 90, 23);
		// Adauga listener pentru buton de numerotare pagina.
		btnSalveaza.addActionListener(new SalveazaButtonListener(layoutParser));
		
		
		getContentPane().add(btnSalveaza);
	}
}
