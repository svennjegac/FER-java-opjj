package hr.fer.zemris.bf.model;

import java.util.function.UnaryOperator;

/**
 * Class representing a unary operator.
 * It can have one child and its operation will be
 * made on its child.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class UnaryOperatorNode implements Node {

	/** Operators name. */
	private String name;
	/** Operators child. */
	private Node child;
	/** Operators operator. */
	private UnaryOperator<Boolean> operator;
	
	/**
	 * Constructor of unary operator.
	 * 
	 * @param name operators name
	 * @param child operators child
	 * @param operator operators operator
	 */
	public UnaryOperatorNode(String name, Node child, UnaryOperator<Boolean> operator) {
		this.name = name;
		this.child = child;
		this.operator = operator;
	}
	
	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
	
	/**
	 * Getter for operators name.
	 * 
	 * @return operators name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for operators child.
	 * 
	 * @return operators child
	 */
	public Node getChild() {
		return child;
	}
	
	/**
	 * Getter for operators operator.
	 * 
	 * @return operators operator
	 */
	public UnaryOperator<Boolean> getOperator() {
		return operator;
	}

}
