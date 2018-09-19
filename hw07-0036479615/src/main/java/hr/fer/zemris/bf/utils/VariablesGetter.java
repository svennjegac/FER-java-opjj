package hr.fer.zemris.bf.utils;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Class that implements {@link NodeVisitor} interface.
 * It goes through whole tree structure and remembers all variables.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class VariablesGetter implements NodeVisitor {
	
	/** Set in which variables are stored. */
	SortedSet<String> variableSet;
	
	/** Default constructor. */
	public VariablesGetter() {
		variableSet = new TreeSet<>();
	}
	
	@Override
	public void visit(ConstantNode node) {
		return;
	}

	@Override
	public void visit(VariableNode node) {
		variableSet.add(node.getName());
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		node.getChild().accept(this);
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		node.getChildren().forEach(n -> n.accept(this));
	}
	
	/**
	 * Getter for all variables.
	 * 
	 * @return all variables
	 */
	public List<String> getVariables() {
		return variableSet.stream().collect(Collectors.toList());
	}
}
