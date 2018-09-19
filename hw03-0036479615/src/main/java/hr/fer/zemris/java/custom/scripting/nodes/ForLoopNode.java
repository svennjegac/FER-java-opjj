package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Class representing a for loop tag in a document.
 * It contains for loop parameters.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ForLoopNode extends Node {

	/** Variable of for loop. */
	private ElementVariable variable;
	/** For loop start expression. */
	private Element startExpression;
	/** For loop end expression. */
	private Element endExpression;
	/** For loop step expression. */
	private Element stepExpression;
	
	/**
	 * Constructor of a for loop node.
	 * It accepts all for loop properties.
	 * Only step expression property can be null.
	 * 
	 * @param variable variable of a for loop
	 * @param startExpression start expression of a for loop
	 * @param endExpression end expression of a for loop
	 * @param stepExpression step expression of a for loop
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		if (variable == null || startExpression == null || endExpression == null) {
			throw new IllegalArgumentException("Neither of first 3 parameters can not be null; was: "
					+ "variable - " + variable
					+ "startExpression - " + startExpression
					+ "endExpression - " + endExpression
					+ "."
			);
		}
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Returns for loop variable.
	 * 
	 * @return for loop variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Returns for loop start expression.
	 * 
	 * @return for loop start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Returns for loop end expression.
	 * 
	 * @return for loop end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Returns for loop step expression.
	 * 
	 * @return for loop step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
}
