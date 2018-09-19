package hr.fer.zemris.bf.model;

/**
 * Visitor interface which offers one method for each node type.
 * This visitor is used to implement different actions for node visiting.
 * Every action should be implemented as a single class.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface NodeVisitor {

	/**
	 * Method for visiting a {@link ConstantNode}.
	 * 
	 * @param node visited {@link ConstantNode}
	 */
	void visit(ConstantNode node);
	
	/**
	 * Method for visiting a {@link VariableNode}.
	 * 
	 * @param node visited {@link VariableNode}
	 */
	void visit(VariableNode node);
	
	/**
	 * Method for visiting a {@link UnaryOperatorNode}.
	 * 
	 * @param node visited {@link UnaryOperatorNode}
	 */
	void visit(UnaryOperatorNode node);
	
	/**
	 * Method for visiting a {@link BinaryOperatorNode}.
	 * 
	 * @param node visited {@link BinaryOperatorNode}
	 */
	void visit(BinaryOperatorNode node);
}
