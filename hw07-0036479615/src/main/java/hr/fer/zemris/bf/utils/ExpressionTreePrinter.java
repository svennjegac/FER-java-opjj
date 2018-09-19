package hr.fer.zemris.bf.utils;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Implementation of node visitor which prints whole tree.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ExpressionTreePrinter implements NodeVisitor {

	/** Indentation of visitor. */
	private int indent;
	
	/**
	 * Default constructor.
	 */
	public ExpressionTreePrinter() {
		indent = 1;
	}
	
	/**
	 * Method prints argument with dedicated indentation.
	 * 
	 * @param argument argument which will be printed
	 */
	private void print(Object argument) {
		System.out.println(String.format("%" + indent + "s%s", " ", argument));
	}
	
	@Override
	public void visit(ConstantNode node) {
		print(node.getValue() == true ? 1 : 0);
	}

	@Override
	public void visit(VariableNode node) {
		print(node.getName());
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		print(node.getName());
		indent += 2;
		node.getChild().accept(this);
		indent -= 2;
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		print(node.getName());
		indent += 2;
		
		for (Node child : node.getChildren()) {
			child.accept(this);
		}
		
		indent -= 2;
	}
}
