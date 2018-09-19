package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element containing a single operator.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ElementOperator extends Element {

	/** Property in which operator will be stored. */
	private String symbol;
	
	/**
	 * Constructor accepts String symbol and
	 * stores it in its property.
	 * 
	 * @param symbol symbol which will be stored
	 */
	public ElementOperator(String symbol) {
		if (symbol == null) {
			throw new IllegalArgumentException("Symbol can not be null");
		}
		
		this.symbol = symbol;
	}
	
	@Override
	public String asText() {
		return symbol;
	}
}
