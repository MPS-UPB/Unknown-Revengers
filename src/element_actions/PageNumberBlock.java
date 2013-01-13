package element_actions;

import gui.GElement;

import java.util.List;

import parser.LayoutParser;
import parser.LayoutParserTreeElement;
import tree.GenericTreeNode;
import tree.GenericTreeTraversalOrderEnum;

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
	 * @param panel Element panel.
	 * @param layoutParser Layout Parser
	 */
	public PageNumberBlock(GElement panel, LayoutParser layoutParser) {
		this.panel = panel;
		this.layoutParser = layoutParser;

		this.removeComposedBlocks();
		this.addComposedBlock();
	}

	/**
	 * Remove CoposedBlocks.
	 */
	private void removeComposedBlocks() {
		List<GenericTreeNode<LayoutParserTreeElement>> list = this.layoutParser.XMLTree
				.build(GenericTreeTraversalOrderEnum.PRE_ORDER);

		for (GenericTreeNode<LayoutParserTreeElement> element : list) {

			if (element.getData().elementType == LayoutParserTreeElement.ElementType.COMPOSEDBLOCK) {
				// Get all children.
				List<GenericTreeNode<LayoutParserTreeElement>> childrenList = element
						.getChildren();

				// Get parrent.
				GenericTreeNode<LayoutParserTreeElement> parent = element
						.getParent();

				// Add ComposedBlock's children to it's parent.
				for (GenericTreeNode<LayoutParserTreeElement> child : childrenList) {
					parent.addChild(child);
				}

				// Delete ComposedBlock.
				for (int i = 0; i < parent.getNumberOfChildren(); i++) {
					if (parent.getChildAt(i) == element) {
						parent.removeChildAt(i);
						break;
					}
				}
			}
		}
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
