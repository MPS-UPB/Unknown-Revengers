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
		int newRight = element.getData().right;

		int newTop = y;
		int newBottom = element.getData().bottom;
		if (dir == Direction.ASCENDING) {
			newTop = height - y;
		}

		newElementData.left = newLeft;
		newElementData.right = newRight;
		newElementData.top = newTop;
		newElementData.bottom = newBottom;
		newElementData.elementType = element.getData().elementType;

		GenericTreeNode<LayoutParserTreeElement> newElement = new GenericTreeNode<LayoutParserTreeElement>();
		newElement.setData(newElementData);

		parent.addChild(newElement);

		// Setup the old element.
		element.getData().bottom = newElementData.top;

		List<GenericTreeNode<LayoutParserTreeElement>> children = element
				.getChildren();
		for (int i = children.size() - 1; i >= 0; i--) {
			if (dir == Direction.DESCENDING) {
				if (children.get(i).getData().top < newElementData.bottom
						&& children.get(i).getData().top > newElementData.top) {
					GenericTreeNode<LayoutParserTreeElement> rElement = children
							.get(i);
					element.removeChildAt(i);
					newElement.addChild(rElement);
				}
			} else {
				if (children.get(i).getData().top < element.getData().bottom
						&& children.get(i).getData().top > element.getData().top) {
					GenericTreeNode<LayoutParserTreeElement> rElement = children
							.get(i);
					element.removeChildAt(i);
					newElement.addChild(rElement);
				}
			}
		}
	}
}
