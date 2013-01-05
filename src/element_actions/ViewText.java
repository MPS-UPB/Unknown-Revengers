package element_actions;

import gui.ElementJPanel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import parser.LayoutParserTreeElement;
import tree.GenericTreeNode;

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
	private final Container contentPanel;

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
		this.addText();

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
				ViewText.this.changeText(elementPanel.element,
						descriptionArea.getText());

				frame.dispose();
			}
		});

		panel.add(saveBttn);
		contentPanel.add(panel, BorderLayout.SOUTH);
	}

	/**
	 * Salveaza modificarile facute in arbore.
	 * 
	 * @param text
	 *            Textul de salvat in arbore.
	 */
	private void changeText(GenericTreeNode<LayoutParserTreeElement> element,
			String text) {
		/*
		 * Daca elementul curent este nod frunza, modifica atributul text.
		 */
		if (element.getData().elementType.toString().compareTo("String") == 0) {
			element.getData().text = text;
			return;
		}

		/*
		 * Daca TextBlock sau TextLine are doar o componenta
		 * (textComponents.length = 0) atunci aceasta este text.
		 */
		String[] textComponents = { text };

		if (element.getData().elementType.toString().compareTo("TextBlock") == 0
				&& text.contains("\n")) {
			// Split text in lines.
			textComponents = text.split("\n");

		} else if (element.getData().elementType.toString().compareTo(
				"TextLine") == 0) {

			if (text.contains("\n")) {
				text = text.replace("\n", " ");
			}

			text = text.trim();
			text = text.replaceAll("( )+", " ");
			textComponents = text.split(" ");
		}

		// Ia copii elementului curent.
		List<GenericTreeNode<LayoutParserTreeElement>> children = element
				.getChildren();

		// Sorteaza dupa top.
		Collections.sort(children, new ElementComparator());

		// Adauga si modifica text in arbore.
		for (int i = 0; i < textComponents.length; i++) {
			if (children.size() == i + 1
					&& element.getData().elementType.toString().compareTo(
							"TextBlock") == 0) {
				/*
				 * Daca numarul de noduri TextLine din TextBlock este mai mare
				 * decat numarul de noduri TextLine existente in arbore, adauga
				 * ultimile TextLine la ultimul nod TextLine din arbore.
				 */
				for (int j = i + 1; j < textComponents.length; j++) {
					textComponents[i] = textComponents[i].concat(" ")
							.concat(textComponents[j]);
				}

				textComponents[i] = textComponents[i].trim();
				textComponents[i] = textComponents[i].replaceAll("( )+", " ");

				this.changeText(children.get(i), textComponents[i]);
				break;

			} else if (children.size() > i) {
				// Schimba textul din nodul existent children.get(i).
				this.changeText(children.get(i), textComponents[i]);

			} else if (element.getData().elementType.toString().compareTo(
					"TextLine") == 0) {
				// Creaza frunze (elemente de tipul String).
				LayoutParserTreeElement frunza = new LayoutParserTreeElement();
				frunza.text = textComponents[i];
				frunza.elementType = parser.LayoutParserTreeElement.ElementType.STRING;

				GenericTreeNode<LayoutParserTreeElement> x = new GenericTreeNode<LayoutParserTreeElement>();
				x.setData(frunza);

				element.addChild(x);
			}
		}

		// Sterge text din arbore.
		if (children.size() > textComponents.length) {
			for (int i = textComponents.length; i < children.size(); i++) {
				// Daca este frunza, sterge nodul
				if (children.get(i).getData().elementType.toString().compareTo(
						"String") == 0) {
					element.removeChildAt(i);
				}

				// Daca este TextLine sterge frunzele.
				else if (children.get(i).getData().elementType.toString()
						.compareTo(
								"TextLine") == 0) {
					for (int j = 0; j < children.get(i).getNumberOfChildren(); j++) {
						children.get(i).removeChildAt(j);
					}
				}
			}
		}
	}

	/**
	 * Adauga text la fereastra.
	 */
	private void addText() {
		// New Panel.
		JPanel panel = new JPanel();

		String text = this.generateText(elementPanel.element);

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
	 * Genereaza textul pentru elementul curent.
	 * 
	 * @param element
	 *            Elementul curent.
	 * @return String Textul elementului.
	 */
	@SuppressWarnings("unchecked")
	private String generateText(
			GenericTreeNode<LayoutParserTreeElement> element) {

		// Daca elementul este nod frunza returneaza textul.
		if (element.getData().elementType.toString().compareTo("String") == 0) {
			return element.getData().text;
		}

		// Ia copii elementului curent.
		List<GenericTreeNode<LayoutParserTreeElement>> children = element
				.getChildren();

		// Sorteaza dupa top.
		Collections.sort(children, new ElementComparator());

		String text = "";
		for (GenericTreeNode<LayoutParserTreeElement> child : children) {
			text = text.concat(this.generateText(child));
			if (child.getData().elementType.toString().compareTo("TextLine") == 0) {
				text = text.concat("\n");
			}
			else if (child.getData().elementType.toString().compareTo("String") == 0) {
				text = text.concat(" ");
			}
		}

		// Remove space or newline added after last component.
		text = text.trim();
		return text;
	}

	/**
	 * @author Unknown-Revengers
	 * 
	 *         Comparator Class for Elements.
	 */
	@SuppressWarnings("rawtypes")
	public class ElementComparator implements Comparator {
		@SuppressWarnings("unchecked")
		@Override
		public int compare(Object node1, Object node2) {
			// Nu compara nodurile frunza, ele nu au top.
			if (((GenericTreeNode<LayoutParserTreeElement>) node1).getData().elementType
					.toString().compareTo("String") != 0) {
				int top1 = ((GenericTreeNode<LayoutParserTreeElement>) node1)
						.getData().top;
				int top2 = ((GenericTreeNode<LayoutParserTreeElement>) node2)
						.getData().top;

				if (top1 != top2) {
					return top1 > top2 ? -1 : 1;
				}
			}

			return 0;
		}
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
