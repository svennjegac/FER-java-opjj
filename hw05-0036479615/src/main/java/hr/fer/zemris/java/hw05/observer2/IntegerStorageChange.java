package hr.fer.zemris.java.hw05.observer2;

/**
 * Class having reference to Subject in Observer pattern, to its
 * old and to its new value. It is generated when IntegerStorage value
 * changes and then it is provided to registered observers.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class IntegerStorageChange {

	/** Reference to Subject IntegerStorage. */
	private IntegerStorage istorage;
	/** Old value of IntegerStorage. */
	private int before;
	/** New value of IntegerStorage. */
	private int current;
	
	/**
	 * Constructor of IntegerStorageChange.
	 * 
	 * @param istorage reference to IntegerStorage instance
	 * @param before old value stored in IntegerStorage
	 * @param current new value stored in IntegerStorage
	 */
	public IntegerStorageChange(IntegerStorage istorage, int before, int current) {
		if (istorage == null) {
			throw new IllegalArgumentException("istorage can not be null.");
		}
		
		this.istorage = istorage;
		this.before = before;
		this.current = current;
	}
	
	/**
	 * Returns Integer storage.
	 * 
	 * @return IntegerStorage
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}
	
	/**
	 * Returns old value of IntegerStorage.
	 * 
	 * @return old value of IntegerStorage
	 */
	public int getBefore() {
		return before;
	}
	
	/**
	 * Returns current value of IntegerStorage.
	 * 
	 * @return current value of IntegerStorage
	 */
	public int getCurrent() {
		return current;
	}
}
