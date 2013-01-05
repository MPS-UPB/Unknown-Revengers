package parser;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import tree.GenericTreeNode;

/**
 * @author diana
 * 
 */
public class TextActions {
	/**
	 * Genereaza textul pentru elementul curent.
	 * 
	 * @param element
	 *            Elementul curent.
	 * @return String Textul elementului.
	 */
	@SuppressWarnings("unchecked")
	public static String getText(
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
			text = text.concat(getText(child));
			if (child.getData().elementType.toString().compareTo("TextLine") == 0) {
				text = text.concat("\n");
			}
			else if (child.getData().elementType.toString().compareTo("String") == 0) {
				text = text.concat(" ");
			}
		}

		// Remove space and new line added only after last component.
		text = text.replaceAll(" $", "");
		if (element.getData().elementType.toString().compareTo("TextBlock") == 0) {
			text = text.substring(0, text.length() - 1);
		}
		return text;
	}

	/**
	 * Salveaza modificarile facute in arbore.
	 * 
	 * @param text
	 *            Textul de salvat in arbore.
	 */
	public static void saveText(
			GenericTreeNode<LayoutParserTreeElement> element,
			String text) {
		/*
		 * Daca elementul curent este nod frunza, modifica atributul text.
		 */
		if (element.getData().elementType.toString().compareTo("String") == 0) {
			element.getData().text = text;
			return;
		}

		/*
		 * Daca TextBlock sau TextLine are doar o componenta atunci aceasta este
		 * text.
		 */
		String[] textComponents = { text };

		// Sparge textul dupa delimitatori.
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

				saveText(children.get(i), textComponents[i]);
				break;

			} else if (children.size() > i) {
				// Schimba textul din nodul existent children.get(i).
				saveText(children.get(i), textComponents[i]);

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
			deleteText(element, textComponents.length);
		}
	}

	/**
	 * Sterge noduri frunza daca la editare s-au sters cuvinte.
	 * 
	 * @param element
	 *            Elementul din care s-au sters cuvinte.
	 * @param textLength
	 *            Numarul de elemente ramase.
	 */
	private static void deleteText(
			GenericTreeNode<LayoutParserTreeElement> element,
			int textLength) {
		// Ia copii elementului curent.
		List<GenericTreeNode<LayoutParserTreeElement>> children = element
				.getChildren();

		// Sorteaza dupa top.
		Collections.sort(children, new ElementComparator());

		for (int i = textLength; i < children.size(); i++) {
			// Daca este frunza, sterge nodul
			if (children.get(i).getData().elementType.toString().compareTo(
					"String") == 0) {
				element.removeChildAt(i);

				// One child removed, size drops by 1.
				i--;

			} else {
				deleteText(children.get(i), 0);
			}
		}
	}

	/**
	 * @author Unknown-Revengers
	 * 
	 *         Comparator Class for Elements.
	 */
	@SuppressWarnings("rawtypes")
	public static class ElementComparator implements Comparator {
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
}
