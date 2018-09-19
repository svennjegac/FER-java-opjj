package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element containing a function name.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ElementFunction extends Element {

	/** Function name. */
	private String name;
	
	/**
	 * Constructor accepts function name and stores it
	 * in its property.
	 * 
	 * @param name name of a function which will be stored
	 */
	public ElementFunction(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name can not be null.");
		}
		
		this.name = name;
	}
	
	@Override
	public String asText() {
		return name;
	}
}
