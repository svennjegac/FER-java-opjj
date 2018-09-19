package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Class representing an empty tag of a document.
 * It contains an array of elements which are held in a tag.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class EchoNode extends Node {

	/** Array of elements contained in tag. */
	private Element[] elements;
	
	/**
	 * Constructor which accepts an array of elements and
	 * stores them in its property.
	 * 
	 * @param elements array which will be stored
	 */
	public EchoNode(Element[] elements) {
		if (elements == null) {
			throw new IllegalArgumentException("Elements can not be null.");
		}
		
		this.elements = elements;
	}
	
	/**
	 * Method returns array of elements.
	 * 
	 * @return array of elements
	 */
	public Element[] getElements() {
		return elements;
	}
	
	/**
	 * Method which accepts node visitor and calls its appropriate
	 * visit method.
	 * 
	 * @param visitor node visitor
	 */
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
