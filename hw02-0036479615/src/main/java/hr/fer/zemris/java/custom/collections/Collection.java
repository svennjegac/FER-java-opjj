package hr.fer.zemris.java.custom.collections;

/**
 * Class <code>Collection</code> represents some general collection of objects.
 * 
 * <p>
 * It has many methods and most of them must be redefined when this class is
 * extended to utilize full functionality.
 * </p>
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Collection {

	/**
	 * Default class constructor.
	 */
	protected Collection() {
	}

	/**
	 * Method test if collection does not have any elements stored in itself.
	 * 
	 * @return <code>true</code> if collection is empty, <code>false</code> if
	 *         collection is not empty
	 */
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * Method returns number of stored elements in collection. Method
	 * implementation always returns 0.
	 * 
	 * @return 0
	 */
	public int size() {
		return 0;
	}

	/**
	 * Method add an <code>Object</code> to collection. Method implementation
	 * does nothing.
	 * 
	 * @param value
	 *            <code>Object</code> reference which is going to be added.
	 */
	public void add(Object value) {
	}

	/**
	 * Method test if <code>Object</code> reference is stored in collection.
	 * This implementation always returns false.
	 * 
	 * @param value
	 *            <code>Object</code> reference which will be searched for in
	 *            collection.
	 * 
	 * @return <code>false</code>
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Method searches for element. If element is found, it will be removed and
	 * result will be <code>true</code>. This implementation always returns
	 * <code>false</code>.
	 * 
	 * @param value
	 *            element which will be tried to remove
	 * @return <code>false</code>
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Method returns collection as an <code>Object[]</code> array. This
	 * implementation always throw <code>UnsupportedOperationException</code>.
	 * 
	 * @return Object[] (this implementation never returns anything)
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException("Not allowed to call 'toArray()' method of this class.");
	}

	/**
	 * Method iterate over every element in collection. For each element is
	 * called processor.process() method. This implementation does nothing.
	 * 
	 * @param processor
	 *            <code>Processor</code> class with single processor method
	 */
	public void forEach(Processor processor) {
	}

	/**
	 * Method adds all elements from other collection to this collection.
	 * 
	 * @param other
	 *            collection whose elements will be added
	 */
	public void addAll(Collection other) {
		if (other == null) {
			throw new IllegalArgumentException("Null was provided as a collection.");
		}

		class LocalProcessor extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		;
		Processor processor = new LocalProcessor();

		other.forEach(processor);
	}

	/**
	 * Method discards all collection elements and sets size to 0.
	 * Here its implementation does nothing.
	 */
	public void clear() {
	}
}
