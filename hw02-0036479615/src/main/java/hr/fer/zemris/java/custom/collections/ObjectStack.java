package hr.fer.zemris.java.custom.collections;

/**
 * Class represents object collection realized as a stack.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ObjectStack {

	/** ArrayIndexedCollection in which elements are stored. */
	private ArrayIndexedCollection array;

	/**
	 * Default constructor.
	 */
	public ObjectStack() {
		array = new ArrayIndexedCollection();
	}

	/**
	 * Method checks if stack is empty.
	 * 
	 * @return <code>true</code> if stack is empty, <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}

	/**
	 * Method returns number of stored elements in stack.
	 * 
	 * @return number of stored elements
	 */
	public int size() {
		return array.size();
	}

	/**
	 * Pushes an element on top of the stack.
	 * 
	 * @param value
	 *            object that is pushed on stack
	 */
	public void push(Object value) {
		array.add(value);
	}

	/**
	 * Method returns element on top of the stack and removes it. If stack is
	 * empty it throws an exception.
	 * 
	 * @return element on top of the stack.
	 */
	public Object pop() {
		Object result = peek();

		array.remove(size() - 1);

		return result;
	}

	/**
	 * Method returns element on top of the stack. If stack is empty it throws
	 * an exception.
	 * 
	 * @return element on top of the stack
	 */
	public Object peek() {
		if (isEmpty()) {
			throw new EmptyStackException("Empty stack.");
		}

		Object result = array.get(size() - 1);
		return result;
	}

	/**
	 * Method discards all elements from stack.
	 */
	public void clear() {
		array.clear();
	}

}
