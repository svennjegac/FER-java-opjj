package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.collections.ArrayIndexedCollection;

/**
 * Class representing a single node in a tree structure.
 * It can have children nodes.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public abstract class Node {

	/** Collection in which children are stored. */
	ArrayIndexedCollection children = null;
	
	/**
	 * Method adds node as a child.
	 * 
	 * @param child node which will become a child
	 */
	public void addChildNode(Node child) {
		if (children == null) {
			children = new ArrayIndexedCollection();
		}
		
		children.add(child);
	}
	
	/**
	 * Method returns number of nodes children.
	 * 
	 * @return number of nodes children
	 */
	public int numberOfChildren() {
		if (children == null) {
			return 0;
		}
		
		return children.size();
	}
	
	/**
	 * Method returns child of a node on given index.
	 * Index can be in range[0, size - 1].
	 * 
	 * @param index index of a child
	 * @return child node on given index
	 */
	public Node getChild(int index) {
		if (children == null) {
			throw new IndexOutOfBoundsException("Can not retrieve children because children is null.");
		}
		
		return (Node) children.get(index);
	}
}
