package hr.fer.zemris.java.hw16.util;

/**
 * Counter class.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Counter {
	
	/** Counter value. */
	int counter = 0;
	
	/**
	 * Increment of counter.
	 */
	public void increment() {
		counter++;
	}
	
	/**
	 * Gets counter value.
	 * 
	 * @return counter value
	 */
	public int getCounter() {
		return counter;
	}
}