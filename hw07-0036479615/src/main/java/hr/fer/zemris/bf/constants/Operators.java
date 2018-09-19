package hr.fer.zemris.bf.constants;

/**
 * Enumeration representing all operators and signs of
 * every operator.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public enum Operators {

	/** OR operator. */
	OR_OPERATOR("or", "+"),
	/** AND operator. */
	AND_OPERATOR("and", "*"),
	/** XOR operator. */
	XOR_OPERATOR("xor", ":+:"),
	/** NOT operator. */
	NOT_OPERATOR("not", "!");
	
	/** Operator name. */
	private final String name;
	/** Operator sign. */
	private final String sign;
	
	/**
	 * Constructor of operator enumeration.
	 * 
	 * @param name operator name
	 * @param sign operator sign
	 */
	private Operators(String name, String sign) {
		this.name = name;
		this.sign = sign;
	}
	
	/**
	 * Getter for operator name.
	 * 
	 * @return operator name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for operator sign.
	 * 
	 * @return operator sign
	 */
	public String getSign() {
		return sign;
	}
}
