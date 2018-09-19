package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class representing a text node in tree
 * structure.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class TextNode extends Node {

	/** Text property of a node. */
	private String text;
	
	/**
	 * Constructor accepts text String and
	 * stores it in its property.
	 * 
	 * @param text String which will be stored
	 */
	public TextNode(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Text can not be null.");
		}
		
		this.text = text;
	}
	
	/**
	 * Returns stored String in property.
	 * 
	 * @return String stored in a property
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Method which accepts node visitor and calls its appropriate
	 * visit method.
	 * 
	 * @param visitor node visitor
	 */
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
