package hr.fer.zemris.bf.model;

import java.util.List;
import java.util.function.BinaryOperator;

/**
 * Class representing a structure operator node.
 * It represents a binary operator node which means that its
 * operation will be made on two operators.
 * However it can have two or more children, so if node has
 * n children, to properly execute operation of this node, operation
 * must be executed n - 1 times.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class BinaryOperatorNode implements Node {

	/** Name of node. */
	String name;
	/** List of children nodes. */
	List<Node> children;
	/** Binary operator. */
	BinaryOperator<Boolean> operator;
	
	/**
	 * Constructor of binary operator.
	 * 
	 * @param name name of node
	 * @param children children of node
	 * @param operator node operator
	 */
	public BinaryOperatorNode(String name, List<Node> children, BinaryOperator<Boolean> operator) {
		super();
		this.name = name;
		this.children = children;
		this.operator = operator;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
	
	/**
	 * Getter for binary operator node name.
	 * 
	 * @return node name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for binary operator children.
	 * 
	 * @return children
	 */
	public List<Node> getChildren() {
		return children;
	}
	
	/**
	 * Getter for binary operator operator.
	 * 
	 * @return operator
	 */
	public BinaryOperator<Boolean> getOperator() {
		return operator;
	}

}
