package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface of a node visitor.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface INodeVisitor {
	
	/**
	 * Method for visiting text node
	 * 
	 * @param node text node
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Method for visiting for loop node
	 * 
	 * @param node for loop node
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Method for visiting echo node.
	 * 
	 * @param node echo node
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Method for visiting document node
	 * 
	 * @param node document node
	 */
	public void visitDocumentNode(DocumentNode node);
}
