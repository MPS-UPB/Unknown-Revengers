/*
 Copyright 2010 Vivin Suresh Paliath
 Distributed under the BSD License
 */

package tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Unknown-Revengers
 * 
 * @param <T> Element type.
 */
public class GenericTree<T> {

	private GenericTreeNode<T> root;

	/**
	 * Cotnructor.
	 */
	public GenericTree() {
		super();
	}

	/**
	 * Getter pentru radacina.
	 * 
	 * @return Returneaza radacina.
	 */
	public GenericTreeNode<T> getRoot() {
		return this.root;
	}

	/**
	 * Setter pentru radacina.
	 * 
	 * @param root Radacina
	 */
	public void setRoot(GenericTreeNode<T> root) {
		this.root = root;
	}

	/**
	 * @return Numarul de noduri.
	 */
	public int getNumberOfNodes() {
		int numberOfNodes = 0;

		if (root != null) {
			numberOfNodes = auxiliaryGetNumberOfNodes(root) + 1; // 1 for the
																 // root!
		}

		return numberOfNodes;
	}

	private int auxiliaryGetNumberOfNodes(GenericTreeNode<T> node) {
		int numberOfNodes = node.getNumberOfChildren();

		for (GenericTreeNode<T> child : node.getChildren()) {
			numberOfNodes += auxiliaryGetNumberOfNodes(child);
		}

		return numberOfNodes;
	}

	/**
	 * @param dataToFind Elementul cautat.
	 * 
	 * @return Valoarea de adevar.
	 */
	public boolean exists(T dataToFind) {
		return (find(dataToFind) != null);
	}

	/**
	 * @param dataToFind Elementul cautat.
	 * 
	 * @return Nodul.
	 */
	public GenericTreeNode<T> find(T dataToFind) {
		GenericTreeNode<T> returnNode = null;

		if (root != null) {
			returnNode = auxiliaryFind(root, dataToFind);
		}

		return returnNode;
	}

	/**
	 * @param dataToFind Elementul dorit.
	 * 
	 * @return True pentru succes.
	 */
	public boolean delete(T dataToFind) {
		GenericTreeNode<T> returnNode = null;

		if (root != null) {
			returnNode = auxiliaryFind(root, dataToFind);
		}

		GenericTreeNode<T> parent = returnNode.getParent();
		List<GenericTreeNode<T>> childrenList = parent.getChildren();

		for (int i = 0; i < childrenList.size(); i++) {
			if (childrenList.get(i) == returnNode) {
				parent.removeChildAt(i);
				return true;
			}
		}

		return false;
	}

	private GenericTreeNode<T> auxiliaryFind(GenericTreeNode<T> currentNode,
			T dataToFind) {
		GenericTreeNode<T> returnNode = null;
		int i = 0;

		if (currentNode.getData().equals(dataToFind)) {
			returnNode = currentNode;
		}

		else if (currentNode.hasChildren()) {
			i = 0;
			while (returnNode == null && i < currentNode.getNumberOfChildren()) {
				returnNode = auxiliaryFind(currentNode.getChildAt(i),
						dataToFind);
				i++;
			}
		}

		return returnNode;
	}

	/**
	 * @return Valoarea de adevar.
	 */
	public boolean isEmpty() {
		return (root == null);
	}

	/**
	 * @param traversalOrder Ordinea elementelor.
	 * 
	 * @return Lista de noduri.
	 */
	public List<GenericTreeNode<T>> build(
			GenericTreeTraversalOrderEnum traversalOrder) {
		List<GenericTreeNode<T>> returnList = null;

		if (root != null) {
			returnList = build(root, traversalOrder);
		}

		return returnList;
	}

	/**
	 * @param node Nodul.
	 * @param traversalOrder Ordinea.
	 * 
	 * @return Rezultat.
	 */
	public List<GenericTreeNode<T>> build(GenericTreeNode<T> node,
			GenericTreeTraversalOrderEnum traversalOrder) {
		List<GenericTreeNode<T>> traversalResult = new ArrayList<GenericTreeNode<T>>();

		if (traversalOrder == GenericTreeTraversalOrderEnum.PRE_ORDER) {
			buildPreOrder(node, traversalResult);
		}

		else if (traversalOrder == GenericTreeTraversalOrderEnum.POST_ORDER) {
			buildPostOrder(node, traversalResult);
		}

		return traversalResult;
	}

	private void buildPreOrder(GenericTreeNode<T> node,
			List<GenericTreeNode<T>> traversalResult) {
		traversalResult.add(node);

		for (GenericTreeNode<T> child : node.getChildren()) {
			buildPreOrder(child, traversalResult);
		}
	}

	private void buildPostOrder(GenericTreeNode<T> node,
			List<GenericTreeNode<T>> traversalResult) {
		for (GenericTreeNode<T> child : node.getChildren()) {
			buildPostOrder(child, traversalResult);
		}

		traversalResult.add(node);
	}

	/**
	 * @param traversalOrder Ordinea.
	 * 
	 * @return Map.
	 */
	public Map<GenericTreeNode<T>, Integer> buildWithDepth(
			GenericTreeTraversalOrderEnum traversalOrder) {
		Map<GenericTreeNode<T>, Integer> returnMap = null;

		if (root != null) {
			returnMap = buildWithDepth(root, traversalOrder);
		}

		return returnMap;
	}

	/**
	 * @param node Nodul.
	 * @param traversalOrder Ordinea.
	 * 
	 * @return Rezaultat.
	 */
	public Map<GenericTreeNode<T>, Integer> buildWithDepth(
			GenericTreeNode<T> node,
			GenericTreeTraversalOrderEnum traversalOrder) {
		Map<GenericTreeNode<T>, Integer> traversalResult = new LinkedHashMap<GenericTreeNode<T>, Integer>();

		if (traversalOrder == GenericTreeTraversalOrderEnum.PRE_ORDER) {
			buildPreOrderWithDepth(node, traversalResult, 0);
		}

		else if (traversalOrder == GenericTreeTraversalOrderEnum.POST_ORDER) {
			buildPostOrderWithDepth(node, traversalResult, 0);
		}

		return traversalResult;
	}

	private void buildPreOrderWithDepth(GenericTreeNode<T> node,
			Map<GenericTreeNode<T>, Integer> traversalResult, int depth) {
		traversalResult.put(node, depth);

		for (GenericTreeNode<T> child : node.getChildren()) {
			buildPreOrderWithDepth(child, traversalResult, depth + 1);
		}
	}

	private void buildPostOrderWithDepth(GenericTreeNode<T> node,
			Map<GenericTreeNode<T>, Integer> traversalResult, int depth) {
		for (GenericTreeNode<T> child : node.getChildren()) {
			buildPostOrderWithDepth(child, traversalResult, depth + 1);
		}

		traversalResult.put(node, depth);
	}

	@Override
	public String toString() {
		/*
		 * We're going to assume a pre-order traversal by default
		 */

		String stringRepresentation = "";

		if (root != null) {
			stringRepresentation = build(
					GenericTreeTraversalOrderEnum.PRE_ORDER).toString();

		}

		return stringRepresentation;
	}

	/**
	 * @return stringul.
	 */
	public String toStringWithDepth() {
		/*
		 * We're going to assume a pre-order traversal by default
		 */

		String stringRepresentation = "";

		if (root != null) {
			stringRepresentation = buildWithDepth(
					GenericTreeTraversalOrderEnum.PRE_ORDER).toString();
		}

		return stringRepresentation;
	}
}
