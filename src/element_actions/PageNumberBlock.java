package element_actions;

import gui.GElement;
import parser.LayoutParserTreeElement;
import tree.GenericTreeNode;

/**
 * Create and add to XML tree new GenericTreeNode containing a ComposedBlock
 * type LayoutParserTreeElement
 * 
 * @author Unknown-Revengers
 * 
 */
public class PageNumberBlock {

	private GElement panel;

	/**
	 * Constructor.
	 * 
	 * @param panel Element panel.
	 */
	public PageNumberBlock(GElement panel) {
		this.panel = panel;

		this.addComposedBlock();
	}

	/**
	 * Create and add to XMLTree a new GenericTreeNode<LayoutParserTreeElement>
	 */
	private void addComposedBlock() {

		GenericTreeNode<LayoutParserTreeElement> element = panel.element;

		// Get parent element for elementNode.
		GenericTreeNode<LayoutParserTreeElement> parent = element
				.getParent();

		// Create ComposedBlock element.
		LayoutParserTreeElement composedElement = new LayoutParserTreeElement();
		composedElement.elementType = LayoutParserTreeElement.ElementType.COMPOSEDBLOCK;
		composedElement.hasPage = true;

		// Creade new GenericTreeNode with composedElement data
		GenericTreeNode<LayoutParserTreeElement> composedBlock = new GenericTreeNode<LayoutParserTreeElement>(
				composedElement);

		// Add element to composedBlock's children.
		composedBlock.addChild(element);

		// Add composedBlock as child for parent and remove element.
		for (int i = 0; i < parent.getNumberOfChildren(); i++) {
			if (parent.getChildAt(i) == element) {
				parent.removeChildAt(i);
				parent.addChildAt(i, composedBlock);
				break;
			}
		}
	}
}
