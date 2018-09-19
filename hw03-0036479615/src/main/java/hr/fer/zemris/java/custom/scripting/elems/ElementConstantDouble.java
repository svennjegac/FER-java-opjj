package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element containing a double value.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ElementConstantDouble extends Element {

	/** Property in which double value is stored. */
	private double value;
	
	/**
	 * Constructor accepts double value and stores it
	 * in its property.
	 * 
	 * @param value value which will be stored
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return Double.toString(value);
	}
}
