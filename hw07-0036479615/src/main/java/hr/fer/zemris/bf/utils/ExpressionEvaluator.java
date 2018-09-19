package hr.fer.zemris.bf.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Visitor which calculates expression evaluation for
 * given combination of booleans assigned to variables.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ExpressionEvaluator implements NodeVisitor {

	/** Combination of boolean values assigned to variables. */
	private boolean[] values;
	/** Map which indicates which position is assigned to which variable. */
	private Map<String, Integer> positions;
	/** Stacks used for calculation. */
	private Stack<Boolean> stack = new Stack<>();
	
	/**
	 * Constructor of expression evaluator which accepts list of variables.
	 * 
	 * @param variables list of variables used in expression
	 */
	public ExpressionEvaluator(List<String> variables) {
		if (variables == null) {
			throw new IllegalArgumentException("Variables can not be null.");
		}
		
		values = new boolean[variables.size()];
		positions = new HashMap<>();
		initializePositions(variables);
	}
	
	/**
	 * Method initializes variables to its positions.
	 * 
	 * @param variables variables for expression
	 * @throws IllegalArgumentException if there are duplicate variables
	 */
	private void initializePositions(List<String> variables) {
		for (int i = 0, size = variables.size(); i < size; i++) {
			if (positions.containsKey(variables.get(i))) {
				throw new IllegalArgumentException("Duplicate of variable; was: " + variables.get(i));
			}
			
			positions.put(variables.get(i), i);
		}
	}
	
	/**
	 * Method sets values of internal array which will
	 * be used to represent variables.
	 * 
	 * @param values array of boolean values used for variables
	 */
	public void setValues(boolean[] values) {
		if (values == null || values.length != positions.size()) {
			String valuesLength = values == null ? "null" : "" + values.length;
			
			throw new IllegalArgumentException("Boolean array not compatible with variables;"
					+ "was: " + valuesLength + "; number of variables: " + positions.size());
		}
		
		start();
		
		this.values = Arrays.copyOf(values, values.length);
	}
	
	/**
	 * Gets the result of evaluation.
	 * 
	 * @return result of evaluation
	 */
	public boolean getResult() {
		if (stack.size() != 1) {
			throw new IllegalStateException("Stck size not 1; was: " + stack.size());
		}
		
		return stack.peek();
	}
	
	/**
	 * Clears the stack.
	 */
	public void start() {
		stack.clear();
	}
	
	@Override
	public void visit(ConstantNode node) {
		stack.push(node.getValue());
	}

	@Override
	public void visit(VariableNode node) {
		Integer index = positions.get(node.getName());
		
		if (index == null) {
			throw new IllegalStateException("Undeclared variable; was: " + node.getName());
		}
		
		stack.push(values[index]);
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		node.getChild().accept(this);
		
		if (stack.isEmpty()) {
			throw new IllegalStateException("Unaray operator expects at least one parameter on stack; was: " + stack.size());
		}
		
		stack.push(node.getOperator().apply(stack.pop()));
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		node.getChildren().forEach(child -> child.accept(this));
		
		if (stack.size() < node.getChildren().size()) {
			throw new IllegalStateException("Stack expects to have at least number of parameters as much of children."
					+ " expected: " + node.getChildren().size() + "; was: " + stack.size());
		}
		
		for (int i = 0, size = node.getChildren().size(); i < size - 1; i++) {
			stack.push(node.getOperator().apply(stack.pop(), stack.pop()));
		}
	}

}
