package hr.fer.zemris.bf.model;

/**
 * Interface of a structure node.
 * It defines a single method accept.
 * This interface is used to utilize a
 * visitor design pattern.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface Node {

	/**
	 * Method which must be implemented by every class which
	 * implements this interface. Implementation of this method
	 * must call a visitors method dedicated to caller class.
	 * 
	 * @param visitor NodeVisitor
	 */
	void accept(NodeVisitor visitor);
}
