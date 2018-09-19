package hr.fer.zemris.bf.model;

/**
 * Class representing a structure node of a variable.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class VariableNode implements Node {

	/** Variable name. */
	private String name;
	
	/**
	 * Constructor of a variable node.
	 * 
	 * @param name variable name
	 */
	public VariableNode(String name) {
		this.name = name;
	}
	
	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
	
	/**
	 * Getter for variable name.
	 * 
	 * @return variable name
	 */
	public String getName() {
		return name;
	}

}
