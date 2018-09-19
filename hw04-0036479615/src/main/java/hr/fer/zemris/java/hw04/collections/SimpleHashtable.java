package hr.fer.zemris.java.hw04.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class represents a map of objects.
 * 
 * Map is realized with hash table which gives user availability to store
 * (key, value) pairs and to fetch them in complexity of O(1).
 * 
 * Also, map is implemented with parameters (K, V), so user can use it
 * for every kind of parameters, for example (String, Integer), (Double, Long)...
 * 
 * Map utilizes automatic resizing when it has ratio of stored/table.length
 * higher or equals to RESIZE_THRESHOLD, so user can count on good performances.
 * 
 * Map implements <code>Iterable</code> interface.
 * 
 * @author Sven Njegač
 * @version 1.0
 * @param <K> type of parameter Key
 * @param <V> type of parameter Value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	
	/** Number of stored pairs in map. */
	private int size;
	/** Table containing pairs. */
	private TableEntry<K, V>[] table;
	
	/** Default table capacity. */
	private static final int DEFAULT_CAPACITY = 16;
	/** Threshold indicating that resizing is needed. */
	private static final double RESIZE_THRESHOLD = 0.75;
	
	/**
	 * Counter which increases when number
	 * of pairs in table increases/decreases.
	 */
	private long modificationCount = Long.MIN_VALUE; 
	
	/**
	 * Default constructor.
	 * Sets table capacity to DEFAULT_CAPACITY.
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Constructor sets table capacity to
	 * provided capacity.
	 * 
	 * @param capacity capacity which will be set
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("Capacity of SimpleHashTable can not be less than 1; was: '" + capacity + "'");
		}
		
		table = (TableEntry<K, V>[]) new TableEntry[equalOrHigherNumberPowerOfTwo(capacity)];
		size = 0;
	}
	
	/**
	 * Checks if hash table is empty.
	 * 
	 * @return <code>true</code> if table is empty, otherwise <code>false</code>
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Returns number of stored pairs in map.
	 * 
	 * @return number of stored pairs in map
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Deletes all pairs form map.
	 */
	public void clear() {
		if (isEmpty()) {
			return;
		}
		
		modificationCount++;
		
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		
		size = 0;
	}
	
	/**
	 * Returns string representation of map in shape of
	 * [key1=value1, key2=value2]...
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		
		boolean first = true;
		
		for (TableEntry<K, V> entry : this) {
			if (first) {
				sb.append(entry.getKey().toString() + "=" + entry.getValue().toString());
				first = false;
			} else {
				sb.append(", " + entry.getKey().toString() + "=" + entry.getValue().toString());
			}
		}
		
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Interface representing a condition when should
	 * iterating stop.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 * @param <K> type of parameter Key
	 * @param <V> type of parameter Value
	 */
	private interface IterateCondition<K, V> {
		/**
		 * Method returns decides whether iterating should
		 * stop based on implementation
		 * 
		 * @param entry (key, value) pair
		 * @return <code>true</code> if iterating should stop, <code>false</code> otherwise
		 */
		boolean stopIterating(TableEntry<K, V> entry);
	}
	
	/**
	 * Method iterating through table pairs until condition determines that
	 * iterating should stop, or until it reaches end.
	 * 
	 * @param iter iterator
	 * @param condition condition when iterating should stop
	 * @return <code>true</code> if iterating stopped by condition,
	 * 			<code>false</code> if iterating stopped because there was no more elements
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private TableEntry<K, V> iterate(Iterator<TableEntry<K, V>> iter, IterateCondition condition) {
		while (iter.hasNext()) {
			TableEntry<K, V> e = iter.next();
			
			if (condition.stopIterating(e)) {
				return e;
			}
		}
		
		return null;
	}
	
	/**
	 * Method checks if map contains provided value.
	 * 
	 * @param value <code>Object</code> which will be searched for
	 * @return <code>true</code> if value is present in map, <code>false</code> otherwise
	 */
	public boolean containsValue(Object value) {
		Iterator<TableEntry<K, V>> iter = iterator();
		
		//iterate through every entry, stop when entry value is equal as provided one
		return null != iterate(iter, entry -> Objects.equals(entry.getValue(), value));
	}
	
	/**
	 * Method checks if map contains provided key.
	 * 
	 * @param key <code>Object</code> which will be searched for
	 * @return <code>true</code> if key is present in map, <code>false</code> otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}
		
		int slot = getDedicatedSlot(key, table.length);
		
		//make iterator which iterates only through one slot
		Iterator<TableEntry<K, V>> iter = iterator(slot, slot + 1);
		
		//iterate through every entry in dedicated slot, stop when entries have same key
		return null != iterate(iter, entry -> entry.getKey().equals(key));
	}
	
	/**
	 * Method removes entry with provided key,
	 * or if there is no entry with provided key, it does nothing.
	 * 
	 * @param key key of searched pair
	 */
	public void remove(Object key) {
		if (key == null) {
			return;
		}
		
		int slot = getDedicatedSlot(key, table.length);
		
		//make iterator which iterates only through one slot
		Iterator<TableEntry<K, V>> iter = iterator(slot, slot + 1);
		
		//if iterator stops because condition was true(entry with searched key was found)
		//remove that entry
		if (null != iterate(iter, entry -> entry.getKey().equals(key))) {
			iter.remove();
		}
	}
	
	/**
	 * Method returns value of entry having key same
	 * as provided one.
	 * 
	 * @param key key of searched entry
	 * @return value of searched entry, or null if entry was not found
	 */
	public V get (Object key) {
		if (key == null) {
			return null;
		}
		
		int slot = getDedicatedSlot(key, table.length);
		
		//make iterator which iterates only through one slot
		Iterator<TableEntry<K, V>> iter = iterator(slot, slot + 1);

		TableEntry<K, V> entryPointer = iterate(iter, entry -> entry.getKey().equals(key));
		
		return (entryPointer == null) ? null : entryPointer.getValue();
	}
	
	/**
	 * Method puts pair (K, V) in map.
	 * 
	 * @param key key of pair
	 * @param value value of pair
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Key can not be null.");
		}
		
		//if key was not yet in table(that entry increased size by 1)
		//change class parameters and check if resizing is needed
		if (putEntryInTable(new TableEntry<K, V>(key, value, null), table)) {
			size++;
			modificationCount++;
			resizeTableIfNeeded();
		}
	}
	
	/**
	 * Method puts entry in dedicated slot of provided table.
	 * 
	 * @param entry entry which will be added
	 * @param table table in which entry will be added
	 * @return <code>true</code> if entry resized map by one,
	 * 			<code>false</code> if entry updated existing pair
	 */
	private boolean putEntryInTable(TableEntry<K, V> entry, TableEntry<K, V>[] table) {
		int slot = getDedicatedSlot(entry.getKey(), table.length);
		
		TableEntry<K, V> entryPointer = table[slot];
		
		//if nothing is in slot
		if (entryPointer == null) {
			table[slot] = entry;
			return true;
		}
		
		while (true) {
			//update existing entry
			if (entryPointer.getKey().equals(entry.getKey())) {
				entryPointer.setValue(entry.getValue());
				return false;
			}
			
			//add entry on end of the list
			if (entryPointer.next == null) {
				entryPointer.next = entry;
				return true;
			}
			
			entryPointer = entryPointer.next;
		}
	}
	
	/**
	 * Calculates slot in which pair should be added.
	 * 
	 * @param key key of entry which should be added
	 * @param length length of table in which entry will be added
	 * @return number of slot in which entry should be added
	 */
	private int getDedicatedSlot(Object key, int length) {
		return Math.abs(key.hashCode()) % length;
	}
	
	/**
	 * Resizes table if threshold is reached.
	 */
	private void resizeTableIfNeeded() {
		if (((double) (size) / table.length) < RESIZE_THRESHOLD) {
			return;
		}
		
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[table.length * 2];
		
		for (TableEntry<K, V> e : this) { 
			putEntryInTable(new TableEntry<>(e.getKey(), e.getValue(), null), newTable);
		}
		
		table = newTable;
	}
	
	/**
	 * Method returns first number which is power of two
	 * starting from provided number and increasing that number by one.
	 * 
	 * @param number starting number
	 * @return first power of two from current number to infinity
	 */
	private int equalOrHigherNumberPowerOfTwo(int number) {
		while (true) {
			if (isThisNumberPowerOfTwo(number)) {
				return number;
			}
			
			//except 1, which is in this part of code already checked
			//there can not be more numbers which are odd and potention of 2
			if (number % 2 == 0) {
				number++;
			}
			
			number++;
		}
	}
	
	/**
	 * Check if number is power of two.
	 * 
	 * @param number provided number
	 * @return <code>true</code> if number is power of two,
	 * 			<code>false</code> otherwise
	 */
	private boolean isThisNumberPowerOfTwo(int number) {
		int powerOfTwo = 1;
		while (powerOfTwo <= number) {
			if (number == powerOfTwo) {
				return true;
			}
			
			powerOfTwo *= 2;
		}
		
		return false;
	}

	/**
	 * Returns iterator which iterates through every
	 * pair of the map.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Returns iterator which iterates through every
	 * pair from starting slot, until ending slot.
	 * 
	 * @param startSlot slot which is iterated through
	 * @param endSlot slot with which iterating ends(excluded)
	 * @return iterator
	 */
	private Iterator<TableEntry<K, V>> iterator(int startSlot, int endSlot) {
		return new IteratorImpl(startSlot, endSlot);
	}
	
	
	/**
	 * Class representing iterator which iterates through hash table pairs.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		
		/** Iterators count of modified elements. */
		private long iteratorModificationCount;
		/** Indicates if iterator is allowed to remove current element. */
		private boolean canRemoveElement;
		
		/** Reference to current entry. */
		private TableEntry<K, V> currentEntry;
		/** 
		 * Reference to entry before current entry.
		 * Is null if current entry is first in slot.
		 */
		private TableEntry<K, V> entryBefore;
		
		/** Slot in which current entry is or will be looked for. */
		private int currentSlot;
		/** End slot from which iterating stops. */
		private int endSlot;
		
		/**
		 * Default constructor.
		 */
		private IteratorImpl() {
			this(0, table.length);
		}
		
		/**
		 * Returns iterator which iterates from startSlot
		 * until endSlot.
		 * 
		 * @param startSlot slot from which iterator iterates
		 * @param endSlot when this slot is reached, iterator stops iterating
		 */
		private IteratorImpl(int startSlot, int endSlot) {
			iteratorModificationCount = modificationCount;
			currentSlot = startSlot;
			this.endSlot = endSlot;
		}
		
		/**
		 * Checks if there is more entries.
		 */
		@Override
		public boolean hasNext() {
			checkOutSideModifications();
			
			return nextElementExists();
		}

		/**
		 * Returns next entry.
		 */
		@Override
		public TableEntry<K, V> next() {
			checkOutSideModifications();
			
			if (!hasNext()) {
				throw new NoSuchElementException("There is no more elements.");
			}
			
			//set element before to current one
			entryBefore = currentEntry;
			currentEntry = iterate(currentEntry);
			
			//if current is first in the table slot
			//set element before to null
			if (currentEntry == table[currentSlot]) {
				entryBefore = null;
			}
			
			canRemoveElement = true;
			
			return currentEntry;
		}
		
		/**
		 * Removes currentEntry from map.
		 */
		@Override
		public void remove() {
			checkOutSideModifications();
			
			if (!canRemoveElement) {
				throw new IllegalStateException("Can not remove on non element.");
			}
			
			canRemoveElement = false;
			
			iteratorModificationCount++;
			modificationCount++;
			
			//if entry is first in table slot
			//elementBefore is null
			if (entryBefore == null) {
				table[currentSlot] = currentEntry.next;
				currentEntry = null;
				size--;
				return;
			}
			
			entryBefore.next = currentEntry.next;
			currentEntry = entryBefore;
			size--;
		}
		
		/**
		 * Check if there is more entries.
		 * 
		 * @return <code>true</code> if there is more elements,
		 * 			<code>false</code> otherwise
		 */
		private boolean nextElementExists() {
			int oldSlot = currentSlot;
			
			Object result = iterate(currentEntry);
			
			currentSlot = oldSlot;
			
			return result != null;
		}
		
		/**
		 * Method iterates through map pairs, until endSlot is
		 * reached. It sets currentSlot to slot in which new entry is found(if it is),
		 * or ends when current slot is same as endSlot.
		 * It returns next entry or null if there is no more entries.
		 * 
		 * @param temporaryPointer reference to current entry
		 * @return next entry or null
		 */
		private TableEntry<K, V> iterate(TableEntry<K, V> temporaryPointer) {
			while (true) {
				if (currentSlot >= endSlot) {
					return null;
				}
				
				//if temporaryPointer is null, next must be first in table slot
				if (temporaryPointer == null) {
					if (table[currentSlot] != null) {
						return table[currentSlot];
					}
					
					currentSlot++;
					continue;
				}
				
				//if temporaryPointer has reference to next entry in same slot, return it
				//or start searching table slots
				if (temporaryPointer.next != null) {
					return temporaryPointer.next;
				} else {
					temporaryPointer = null;
					currentSlot++;
					continue;
				}
			}
		}
		
		/**
		 * Method checks if modifications were made form other
		 * sides when iterator is iterating. If changes were made
		 * it will throw ConcurrentModificationException.
		 */
		private void checkOutSideModifications() {
			if (iteratorModificationCount != modificationCount) {
				throw new ConcurrentModificationException(
						"Collection was modified from outside; modCount: '" + modificationCount + "'; "
								+"iterModCount: '" + iteratorModificationCount + "'");
			}
		}
		
	}
	
	/**
	 * Class represents single entry pair (key, value) in map.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 * @param <K> type of parameter key
	 * @param <V> type of parameter value
	 */
	public static class TableEntry<K, V> {
			
		/** Key of pair. */
		private K key;
		/** Value of pair. */
		private V value;
		/** Reference to the next entry(next in same slot). */
		private TableEntry<K, V> next;
		
		
		/**
		 * Constructor of single entry.
		 * 
		 * @param key key of entry
		 * @param value value of entry
		 * @param next reference to next entry
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			super();
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Returns entry key.
		 * 
		 * @return entry key
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * Returns entry value.
		 * 
		 * @return entry value
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Sets entry value.
		 * 
		 * @param value new entry value
		 */
		public void setValue(V value) {
			this.value = value;
		}
	}
}
