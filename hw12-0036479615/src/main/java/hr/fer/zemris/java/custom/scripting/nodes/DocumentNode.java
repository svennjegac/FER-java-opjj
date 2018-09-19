package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class representing a root of a document.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class DocumentNode extends Node {
	
	/**
	 * Method which accepts node visitor and calls its appropriate
	 * visit method.
	 * 
	 * @param visitor node visitor
	 */
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
