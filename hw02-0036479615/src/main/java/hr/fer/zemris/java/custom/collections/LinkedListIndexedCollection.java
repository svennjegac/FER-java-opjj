package hr.fer.zemris.java.custom.collections;

/**
 * Class used to store and manipulate with objects.
 * 
 * <p>
 * Class is representing a list of objects. It has several number of public
 * methods which are giving user an easy interface to add/insert/remove objects
 * from it and much more.
 * </p>
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Class represents a single node of a list collection. Single node stores
	 * value and pointers to previous and next node.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class ListNode {
		/** Reference to previous node. */
		private ListNode previous;
		/** Reference to next node. */
		private ListNode next;
		/** Object stored in node. */
		private Object value;

		/**
		 * Constructor of a single node with provided value.
		 * 
		 * @param value
		 *            value stored in node
		 */
		public ListNode(Object value) {
			this.value = value;
			previous = null;
			next = null;
		}

		/**
		 * Constructor of single node with defined value and references to
		 * previous and next node.
		 * 
		 * @param value
		 *            object stored in node
		 * @param previous
		 *            reference to previous node
		 * @param next
		 *            reference to next node
		 */
		public ListNode(Object value, ListNode previous, ListNode next) {
			this.value = value;
			this.previous = previous;
			this.next = next;
		}

		/**
		 * Method returns next node reference.
		 * 
		 * @return next node reference
		 */
		public ListNode getNext() {
			return next;
		}

		/**
		 * Method sets next node reference.
		 * 
		 * @param next
		 *            next node reference
		 */
		public void setNext(ListNode next) {
			this.next = next;
		}

		/**
		 * Method returns previous node reference.
		 * 
		 * @return previous node reference
		 */
		public ListNode getPrevious() {
			return previous;
		}

		/**
		 * Method sets previous node reference
		 * 
		 * @param previous
		 *            previous node reference
		 */
		public void setPrevious(ListNode previous) {
			this.previous = previous;
		}

		/**
		 * Method returns object stored in node.
		 * 
		 * @return object stored in node
		 */
		public Object getValue() {
			return value;
		}
	};

	/** Number of elements stored in list. */
	private int size;
	/** Reference to first node in list. */
	private ListNode first;
	/** Reference to last node in list. */
	private ListNode last;

	/**
	 * Default constructor of list.
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * Constructor which adds all elements of other collection to new list
	 * collection.
	 * 
	 * @param other
	 *            reference to other collection
	 */
	public LinkedListIndexedCollection(Collection other) {
		this.addAll(other);
	}

	/**
	 * Method returns number of elements stored in collection.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Method adds object to collection. Method can not add null values. Object
	 * is always added to the last position in collection.
	 * 
	 * @param value
	 *            object that is going to be stored in collection
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("Cannot add null to LinkedListIndexedCollection.");
		}

		size++;

		if (first == null) {
			ListNode node = new ListNode(value);
			first = node;
			last = node;
			return;
		}

		ListNode node = new ListNode(value, last, null);
		last.setNext(node);
		last = node;
	}

	/**
	 * Method inserts object to collection on given position. It cannot insert a
	 * null value. Position can be in range[0, size]. Average complexity is O(n/2).
	 * 
	 * @param value
	 *            object stored in collection
	 * @param position
	 *            position where object should be stored
	 */
	public void insert(Object value, int position) {
		if (value == null) {
			throw new IllegalArgumentException("Cannot add null to the list.");
		}

		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Cannot insert element with invalid position.");
		}

		if (position == size) {
			add(value);
			return;
		}

		ListNode frontNode = getNode(position);
		ListNode behindNode = frontNode.getPrevious();

		size++;

		ListNode newNode;
		if (behindNode == null) {
			newNode = new ListNode(value, null, frontNode);
			frontNode.setPrevious(newNode);
			first = newNode;
			return;
		}

		newNode = new ListNode(value, behindNode, frontNode);
		frontNode.setPrevious(newNode);
		behindNode.setNext(newNode);
	}

	/**
	 * Method removes object on given index from collection. Index can be in
	 * range [0, size - 1].
	 * 
	 * @param index
	 *            index of removing object
	 */
	public void remove(int index) {
		ListNode node = getNode(index);
		ListNode behindNode = node.getPrevious();
		ListNode frontNode = node.getNext();

		if (behindNode != null) {
			behindNode.setNext(frontNode);
		} else {
			first = frontNode;
		}

		if (frontNode != null) {
			frontNode.setPrevious(behindNode);
		} else {
			last = behindNode;
		}

		size--;
	}

	/**
	 * Method removes given object from collection.
	 * 
	 * @return <code>true</code> if object is removed, <code>false</code>
	 *         otherwise
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
	 * Method discards all elements from collection.
	 */
	@Override
	public void clear() {
		last = null;
		first = null;
		size = 0;
	}

	/**
	 * Method gets object on given index. Method can get objects on indexes
	 * [0, size - 1].
	 * 
	 * @param index
	 *            index of wanted object
	 * @return object on given index
	 */
	public Object get(int index) {
		return getNode(index).getValue();
	}

	/**
	 * Method checks if given object is stored in collection.
	 * 
	 * @return <code>true</code> if object is in collection, <code>false</code>
	 *         otherwise
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	/**
	 * Method iterates over collection and calls processor.processor() method on
	 * every object.
	 * 
	 * @param processor
	 *            instance of <code>Processor</code> class
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode currentNode = first;
		
		while (currentNode != null) {
			processor.process(currentNode.getValue());
			currentNode = currentNode.getNext();
		}
	}

	/**
	 * Method return an object array representation of collection.
	 * 
	 * @return object array containing collection elements
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		ListNode currentNode = first;
		
		int i = 0;
		while (currentNode != null) {
			array[i] = currentNode.getValue();
			i++;
			currentNode = currentNode.getNext();
		}

		return array;
	}

	/**
	 * Method returns index of searched object.
	 * Average complexity is O(n**2)
	 * 
	 * @param value
	 *            searched object
	 * @return index of object or -1 if object is not stored in collection
	 */
	public int indexOf(Object value) {
		ListNode currentNode = first;
		
		int i = 0;
		while (currentNode != null) {
			if (currentNode.getValue().equals(value)) {
				return i;
			}
			
			i++;
			currentNode = currentNode.getNext();
		}

		return -1;
	}

	/**
	 * Method gets collection node on given index. Index can be in range [0,
	 * size - 1].
	 * 
	 * @param index
	 *            index of searched node
	 * @return searched node
	 */
	private ListNode getNode(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Cannot get ListNode with invalid index.");
		}

		if (index > (size / 2)) {
			return fetchFromList(last, index, size - 1, false);
		}

		return fetchFromList(first, index, 0, true);
	}

	/**
	 * Method fetches ListNode on given index from list. Index can be in range
	 * [0, size - 1]. It fetches from start/end of the list, depending how it is
	 * called.
	 * 
	 * CALL : (first, index, 0, true) if you want to fetch from start.
	 * CALL : (last, index, size - 1, false) if you want to fetch from end.
	 * 
	 * @param node
	 *            first/last node
	 * @param index
	 *            searched index
	 * @param current
	 *            see documentation
	 * @param start
	 *            see documentation
	 * @return searched ListNode
	 */
	private ListNode fetchFromList(ListNode node, int index, int current, boolean start) {
		if (node == null) {
			throw new IllegalArgumentException("Illegal first/last argument.");
		}

		if (index == current) {
			return node;
		}

		if (start) {
			return fetchFromList(node.getNext(), index, current + 1, true);
		}

		return fetchFromList(node.getPrevious(), index, current - 1, false);
	}
}
