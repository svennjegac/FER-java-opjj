package hr.fer.zemris.java.custom.scripting.collections;

/**
 * Class <code>Processor</code> is a simple class with one method.
 * 
 * <p>
 * It is used in <code>forEach(Processor processor)</code> method of
 * <code>Collection</code> class to process one by one element of collection.
 * </p>
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Processor {

	/**
	 * This method process every element of single <code>Collection</code>. Its
	 * implementation here does nothing and must be redefined.
	 * 
	 * @param value
	 *            <code>Object</code> that will be processed
	 */
	public void process(Object value) {
	}
}
