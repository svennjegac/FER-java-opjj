package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element containing a variable name.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ElementVariable extends Element {

	/** Variable name. */
	String name;
	
	/**
	 * Constructor accepts variable name and stores it
	 * in its property.
	 * 
	 * @param name name which is going to be stored
	 */
	public ElementVariable(String name) {
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
