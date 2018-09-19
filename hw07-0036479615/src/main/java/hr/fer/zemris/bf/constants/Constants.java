package hr.fer.zemris.bf.constants;

/**
 * Enumeration representing all constants and
 * value of every constant.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public enum Constants {

	/** TRUE constant. */
	TRUE("true", true),
	/** FALSE constant. */
	FALSE("false", false),
	/** NUMBER_TRUE constant. */
	NUMBER_TRUE("1", true),
	/** NUMBER_FALSE constant. */
	NUMBER_FALSE("0", false);
	
	/** Constant name. */
	private final String name;
	/** Constant value. */
	private final boolean value;
	
	/**
	 * Constant enumeration constructor.
	 * 
	 * @param name constant name
	 * @param value constant value
	 */
	private Constants(String name, boolean value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * Getter for constant name.
	 * 
	 * @return constant name
	 */
	public String getName() {
		return name;
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
