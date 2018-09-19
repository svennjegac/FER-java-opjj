package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element containing an Integer value.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ElementConstantInteger extends Element {

	/** Property in which int value is stored. */
	private int value;
	
	/**
	 * Constructor accepts int and stores it in
	 * its property.
	 * 
	 * @param value value which will be stored
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return Integer.toString(value);
	}
}
