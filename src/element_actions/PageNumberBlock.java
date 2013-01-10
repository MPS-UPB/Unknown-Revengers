package element_actions;

import gui.GElement;
import parser.LayoutParser;
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

	private LayoutParser layoutParser;

	/**
	 * Constructor.
	 * 
	 * @param GElement
	 *            panel
	 * @param LayoutParser
	 *            lp
	 */
	public PageNumberBlock(GElement panel, LayoutParser lp) {
		this.panel = panel;
		this.layoutParser = lp;

		this.addComposedBlock();

	}

	/**
	 * Create and add to XMLTree a new GenericTreeNode<LayoutParserTreeElement>
	 * 
	 * @return void
	 */
	public void addComposedBlock() {

		GenericTreeNode<LayoutParserTreeElement> element = panel.element;
		LayoutParserTreeElement elementData = element.getData();

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
