package hr.fer.zemris.bf.model;

import java.util.List;

import hr.fer.zemris.bf.constants.Operators;

/**
 * Class offering static methods to make new instances of wanted operators nodes.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class OperatorNodeFactory {

	/**
	 * Method which makes a OR operator node.
	 * 
	 * @param children node children
	 * @return binary operator OR
	 */
	public static BinaryOperatorNode makeOROperatorNode(List<Node> children) {
		return new BinaryOperatorNode(Operators.OR_OPERATOR.getName(), children, (a, b) -> a || b);
	}
	
	/**
	 * Method which makes XOR operator node.
	 * 
	 * @param children node children
	 * @return binary operator XOR
	 */
	public static BinaryOperatorNode makeXOROperatorNode(List<Node> children) {
		return new BinaryOperatorNode(Operators.XOR_OPERATOR.getName(), children, (a, b) -> a ^ b);
	}
	
	/**
	 * Method which makes AND operator node.
	 * 
	 * @param children node children
	 * @return binary operator AND
	 */
	public static BinaryOperatorNode makeANDOperatorNode(List<Node> children) {
		return new BinaryOperatorNode(Operators.AND_OPERATOR.getName(), children, (a, b) -> a && b);
	}
	
	/**
	 * Method which makes NOT operator node.
	 * 
	 * @param child child node
	 * @return unary operator NOT
	 */
	public static UnaryOperatorNode makeNOTOperatorNode(Node child) {
		return new UnaryOperatorNode(Operators.NOT_OPERATOR.getName(), child, a -> !a);
	}

}
