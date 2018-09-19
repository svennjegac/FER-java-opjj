package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a map which has Stacks bounded to Strings.
 * Strings represents keys, while stack bounded to key is a value.
 * 
 * If key does not exist in map, new key is made with new stack, and value is pushed
 * on stack.
 * 
 * If key exists on stack, new value is pushed on top of stack.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ObjectMultistack {

	/** Map containing keys(String), values(stored on stacks). */
	private Map<String, MultistackEntry> map;
	
	/**
	 * Default constructor.
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}
	
	/**
	 * Method pushes new value on dedicated stack for provided key.
	 * If key does not exist, new key is made in map and new stack is
	 * made for that key.
	 * 
	 * @param name key name
	 * @param valueWrapper value that needs to be put on stack
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if (name == null || valueWrapper == null) {
			throw new IllegalArgumentException("Arguments can not be null; name: " + name + ", valueW: " + valueWrapper);
		}
		
		if (!map.containsKey(name)) {
			map.put(name, new MultistackEntry(valueWrapper, null));
			return;
		}
		
		map.put(name, new MultistackEntry(valueWrapper, map.get(name)));
	}
	
	/**
	 * Method pops and returns last value put on stack.
	 * If it is last empty, key and stack are removed from map.
	 * If key and stack do not exist, exception is thrown.
	 * 
	 * @param name key name
	 * @return last value on stack
	 */
	public ValueWrapper pop(String name) {
		if (isEmpty(name)) {
			throw new EmptyStackException();
		}
		
		MultistackEntry topOfStack = map.get(name);
		
		if (topOfStack.next == null) {
			map.remove(name);
			return topOfStack.value;
		}
		
		map.put(name, topOfStack.next);
		return topOfStack.value;
	}
	
	/**
	 * Method looks and returns what is last value put on stack.
	 * If map does not contain provided key, it throws exception.
	 * 
	 * @param name key name for stack
	 * @return last value put on stack
	 */
	public ValueWrapper peek(String name) {
		if (isEmpty(name)) {
			throw new EmptyStackException();
		}
		
		return map.get(name).value;
	}
	
	/**
	 * Method checks if stack is empty for provided key.
	 * 
	 * @param name key name for stack
	 * @return <code>true</code> if key and stack does not exist,
	 * 			<code>false</code> otherwise
	 */
	public boolean isEmpty(String name) {
		validateName(name);
		
		return !map.containsKey(name);
	}
	
	/**
	 * Method checks if key name is valid.
	 * 
	 * @param name key stack name
	 */
	private void validateName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name can not be null.");
		}
	}
	
	/**
	 * Node used for stack implementation.
	 * Each node consists of value and reference to another node.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class MultistackEntry {
		
		/** Value stored in a single node. */
		private ValueWrapper value;
		/** Reference to next node. */
		private MultistackEntry next;
		
		/**
		 * Constructor which accepts reference to value and
		 * next node.
		 * 
		 * @param value value reference
		 * @param next next node reference
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			super();
			this.value = value;
			this.next = next;
		}
	}
}
