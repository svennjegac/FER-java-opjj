package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element containing a String.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ElementString extends Element {

	/** String representation. */
	private String value;
	
	/**
	 * Constructor accepts String and stores it in its property.
	 * 
	 * @param value String value which will be stored
	 */
	public ElementString(String value) {
		if (value == null) {
			throw new IllegalArgumentException("Value can not be null.");
		}
		
		this.value = value;
	}
	
	@Override
	public String asText() {
		return value;
	}
}
