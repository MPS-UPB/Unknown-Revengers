package element_actions;

import java.util.List;

import parser.Direction;
import parser.LayoutParserTreeElement;
import tree.GenericTreeNode;

public class BreakH {
	public <child> BreakH(GenericTreeNode<LayoutParserTreeElement> element,
			Direction dir, int x, int y, int height, int width) {
		GenericTreeNode<LayoutParserTreeElement> parent = element.getParent();

		// Setup the new element.
		LayoutParserTreeElement newElementData = new LayoutParserTreeElement();

		int newLeft = element.getData().left;

		int newTop = y;
		if (dir == Direction.ASCENDING) {
			newTop = height - y;
		}

		int newRight = element.getData().right;

		int newBottom = element.getData().bottom;

		newElementData.left = newLeft;
		newElementData.right = newRight;
		newElementData.top = newTop;
		newElementData.bottom = newBottom;
		newElementData.elementType = element.getData().elementType;

		GenericTreeNode<LayoutParserTreeElement> newElement = new GenericTreeNode<LayoutParserTreeElement>();
		newElement.setData(newElementData);

		parent.addChild(newElement);

		// Setup the old element.
		element.getData().bottom = y;
		if (dir == Direction.ASCENDING) {
			element.getData().bottom = height - y;
		}

		List<GenericTreeNode<LayoutParserTreeElement>> children = newElement
				.getChildren();
		for (int i = children.size() - 1; i >= 0; i--) {
			if (children.get(i).getData().top > element.getData().bottom) {
				GenericTreeNode<LayoutParserTreeElement> rElement = children
						.get(i);
				element.removeChildAt(i);
				newElement.addChild(rElement);
			}
		}
	}
}
