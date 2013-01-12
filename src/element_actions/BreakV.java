package element_actions;

import java.util.List;

import parser.Direction;
import parser.LayoutParserTreeElement;
import tree.GenericTreeNode;

/**
 * @author Unknown-Revengers
 * 
 */
public class BreakV {
	/**
	 * Constructor.
	 * 
	 * @param element Tree element.
	 * @param dir Direction: ascending or descending.
	 * @param x Coordinate.
	 * @param y Coordinate.
	 * @param height Element hight.
	 * @param width Element width.
	 */
	public BreakV(GenericTreeNode<LayoutParserTreeElement> element,
			Direction dir, int x, int y, int height, int width) {
		GenericTreeNode<LayoutParserTreeElement> parent = element.getParent();

		// Create new element.
		LayoutParserTreeElement newElementData = new LayoutParserTreeElement();

		int newLeft = x;
		int newRight = element.getData().right;
		int newTop = element.getData().top;
		int newBottom = element.getData().bottom;

		// Set new element's data.
		newElementData.left = newLeft;
		newElementData.right = newRight;
		newElementData.top = newTop;
		newElementData.bottom = newBottom;
		newElementData.elementType = element.getData().elementType;

		// Create new tree element.
		GenericTreeNode<LayoutParserTreeElement> newElement = new GenericTreeNode<LayoutParserTreeElement>();
		newElement.setData(newElementData);

		parent.addChild(newElement);

		// Setup the old element.
		element.getData().right = newElementData.left;

		List<GenericTreeNode<LayoutParserTreeElement>> children = element
				.getChildren();

		// Copy old element's children.
		for (int i = children.size() - 1; i >= 0; i--) {
			if (children.get(i).getData().left > newElementData.left
					&& children.get(i).getData().left < newElementData.right) {
				GenericTreeNode<LayoutParserTreeElement> rElement = children
						.get(i);
				element.removeChildAt(i);
				newElement.addChild(rElement);
			}
		}
	}
}
