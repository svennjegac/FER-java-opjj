package hr.fer.zemris.java.custom.collections;

/**
 * Class used to store and manipulate with objects.
 * 
 * <p>
 * Class is representing an array of objects. It has several number of public
 * methods which are giving user an easy interface to add/insert/remove objects
 * from it and much more.
 * </p>
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * Constant used to define initial capacity of array(when user doesn't
	 * specify it).
	 */
	private static final int defaultCapacity = 16;
	/**
	 * Multiplier which will determine how much times arrays capacity must be
	 * increased.
	 */
	private static final int reallocateFactor = 2;

	/** Number of objects currently stored in an array. */
	private int size = 0;
	/**
	 * Number representing how much objects can be stored in an array.(With
	 * currently allocated memory).
	 */
	private int capacity;
	/** Array of Object references */
	private Object elements[];

	/**
	 * Default constructor. Initialize array with capacity defined by
	 * <code>defaultCapacity</code> and initialize all values to
	 * <code>null</code>.
	 */
	public ArrayIndexedCollection() {
		this(defaultCapacity);
	}

	/**
	 * Constructor which initialize array of capacity defined by user.
	 * Initialize all values to <code>null</code>.
	 * 
	 * @param initialCapacity
	 *            defines initial capacity of array
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Cannot instantiate ArrayIndexedCollection with capacity less than 1.");
		}

		capacity = initialCapacity;
		elements = new Object[initialCapacity];
	}

	/**
	 * Constructor initialize array of elements with
	 * <code>defaultCapacity</code>. Constructor fills array with given
	 * collection.
	 * 
	 * @param other
	 *            collection whose objects will be added to array
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, defaultCapacity);
	}

	/**
	 * Constructor initialize array of capacity defined by user. Constructor
	 * fills array with given collection.
	 * 
	 * @param other
	 *            collection whose objects will be added to array
	 * @param initialCapacity
	 *            defines initial capacity of array
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this(initialCapacity);
		this.addAll(other);
	}

	/**
	 * Method returns number of objects currently stored in collection.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Method adds an object to the first empty place in collection.
	 * 
	 * <p>
	 * That is the last position in an array. If array is full, it will
	 * reallocate it to double size and then add an element to collection. Null
	 * can not be added to collection. Average complexity of this method is
	 * O(1).
	 * </p>
	 * 
	 * @param value
	 *            object that will be added
	 */
	@Override
	public void add(Object value) {
		insert(value, size);
	}

	/**
	 * Method inserts an element to array on given position.
	 * 
	 * <p>
	 * It shifts every element from that position to the last element for one
	 * position forward. If array is full of elements, it will be reallocated to
	 * double its size. <code>Null</code> values can not be added. Elements can
	 * be inserted on positions [0, size]. Otherwise IndexOutOfBoundsException
	 * will be thrown. Average complexity is O(n).
	 * </p>
	 * 
	 * @param value
	 *            parameter which will be added
	 * @param position
	 *            position on which new parameter will be added
	 */
	public void insert(Object value, int position) {
		if (value == null) {
			throw new IllegalArgumentException("Cannot add null to the ArrayIndexedCollection.");
		}

		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Cannot insert value on invalid position.");
		}

		if (size == capacity) {
			reallocateDoubleCapacity();
		}

		if (position == size) {
			elements[size] = value;
			size++;
			return;
		}

		shiftFrontArray(position);

		for (int i = 0; i < capacity; i++) {
			if (elements[i] == null) {
				elements[i] = value;
				size++;
				return;
			}
		}
	}

	/**
	 * Method search for element. If element is found, it will be removed and
	 * result will be <code>true</code>. Otherwise it will return false.
	 */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);

		if (index == -1) {
			return false;
		}

		remove(index);
		return true;
	}

	/**
	 * Method removes value on given index from collection.
	 * 
	 * <p>
	 * Method can remove values on indexes in range [0, size - 1]. All elements
	 * with higher index than element which is going to be removed will be
	 * shifted backwards for one position.
	 * </p>
	 * 
	 * @param index
	 *            element on this index will be removed
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Cannot remove value with invalid index.");
		}

		shiftBackArray(index);
		size--;
	}

	/**
	 * Method discards all elements from collection and sets its size to 0.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}

		size = 0;
	}

	/**
	 * Method return <code>Object</code> on specified index. It can be used for
	 * indexes in range [0, size - 1].
	 * 
	 * @param index
	 *            element on this index will be returned
	 * @return <code>Object</code> on specified index
	 */
	public Object get(int index) {
		if (index < 0 || index > (size - 1)) {
			throw new IndexOutOfBoundsException(
					"Cannot get the element with invalid index from the ArrayIndexedCollection.");
		}

		return elements[index];
	}

	/**
	 * Method searches through array and returns index of element which was
	 * trying to be found. Average complexity is O(n).
	 * 
	 * @param value
	 *            <code>Object</code> which will be looked up in an array
	 * @return index of searched <code>Object</code> or -1 if
	 *         <code>Object</code> is not in array
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Method test if <code>Object</code> reference is stored in collection.
	 * 
	 * @param value
	 *            <code>Object</code> reference which will be searched for in
	 *            collection.
	 * 
	 * @return <code>true</code> if element is in collection, <code>false</code>
	 *         if element is not in collection
	 */
	@Override
	public boolean contains(Object value) {
		if (indexOf(value) != -1) {
			return true;
		}

		return false;
	}

	/**
	 * Method iterate over every element in collection. For each element is
	 * called processor.process() method.
	 * 
	 * @param processor
	 *            instance of <code>Processor</code> which operate on elements
	 */
	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	/**
	 * Method returns collection as an <code>Object[]</code> array.
	 * 
	 * @return Object[] array of elements
	 */
	@Override
	public Object[] toArray() {
		Object[] result = copyArray(elements, size);

		return result;
	}

	/**
	 * Method reallocates array to have double capacity.
	 */
	private void reallocateDoubleCapacity() {
		Object oldElements[] = elements;
		capacity *= reallocateFactor;
		size = 0;
		elements = new Object[capacity];

		for (Object object : oldElements) {
			this.add(object);
		}
	}

	/**
	 * Method copies source array to new array having capacity of specified
	 * size.
	 * 
	 * @param source
	 *            source array
	 * @param size
	 *            size of source array which will determine result array
	 * @return array having elements same as source array and capacity equal to
	 *         its size
	 */
	private Object[] copyArray(Object[] source, int size) {
		Object[] destination = new Object[size];

		for (int i = 0; i < size; i++) {
			destination[i] = source[i];
		}

		return destination;
	}

	/**
	 * Shifts all elements from specified position forward for one position.
	 * <p>
	 * If array is full it will be reallocated to double size. Method accepts
	 * arguments in range [0, capacity - 1]. Position from which elements were
	 * shifted will be set to null.
	 * </p>
	 * 
	 * @param position
	 *            position from which elements will be shifted
	 */
	private void shiftFrontArray(int position) {
		if (position < 0 || position >= capacity) {
			throw new IndexOutOfBoundsException("Cannot shiftFront array with invalid position given.");
		}

		if (size == capacity) {
			reallocateDoubleCapacity();
		}

		for (int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}

		elements[position] = null;
	}

	/**
	 * Shifts all elements with higher index than one given to method for one
	 * position backwards. Element on specified position will be overridden,
	 * Method accepts arguments in range [0, capacity - 1].
	 * 
	 * @param position
	 *            position from which elements will be shifted
	 */
	private void shiftBackArray(int position) {
		if (position < 0 || position >= capacity) {
			throw new IndexOutOfBoundsException("Cannot shiftBack array with invalid position given.");
		}

		for (int i = position; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}

		elements[size - 1] = null;
	}
}
