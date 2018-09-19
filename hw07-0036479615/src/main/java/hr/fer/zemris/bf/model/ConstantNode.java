package hr.fer.zemris.bf.model;

/**
 * Class representing a structure node of a constant.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ConstantNode implements Node {

	/** Constant value. */
	private boolean value;
	
	/**
	 * Constructor of constant node.
	 * 
	 * @param value constant value
	 */
	public ConstantNode(boolean value) {
		this.value = value;
	}
	
	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
	
	/**
	 * Getter for constant value.
	 * 
	 * @return constant value
	 */
	public boolean getValue() {
		return value;
	}

}
