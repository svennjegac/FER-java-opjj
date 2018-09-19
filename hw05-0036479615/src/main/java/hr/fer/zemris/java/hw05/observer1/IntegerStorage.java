package hr.fer.zemris.java.hw05.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a Subject in Observer pattern.
 * It has value. It allows Observers to register and unregister.
 * When value changes, it notifies all Observers who are registered at the moment
 * of value changing.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class IntegerStorage {
	
	/** Current value of Subject. */
	private int value;
	
	/** List of currently registered observers. */
	private List<IntegerStorageObserver> observers;
	/** List of observers that needs to be removed. */
	private List<IntegerStorageObserver> observersToRemove;
	
	/**
	 * Constructor of Subject.
	 * It accepts initial value of Subject.
	 * 
	 * @param initialValue initial value of Subject
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Method adds new observer to list of registered observers.
	 * If observer is already registered, it will do nothing.
	 * 
	 * @param observer observer that needs to be added to list of registerd observers
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observer == null) {
			throw new IllegalArgumentException("Observer can not be null.");
		}
		
		if (observers == null) {
			observers = new ArrayList<>();
		}
		
		deleteObservers();
		
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}
	
	/**
	 * Method adds observer to list of observers that needs to be removed
	 * from registered observers.
	 * If list already contains provided observer, it will do nothing.
	 * 
	 * @param observer observer that needs to be added to list of observers which will be removed
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observer == null) {
			throw new IllegalArgumentException("Observer can not be null.");
		}
		
		if (observersToRemove == null) {
			observersToRemove = new ArrayList<>();
		}
		
		if (!observersToRemove.contains(observer)) {
			observersToRemove.add(observer);
		}
	}
	
	/**
	 * Method removes all observers which are in list of observers which needs to be removed
	 * from list of registered observers.
	 */
	private void deleteObservers() {
		if (observersToRemove == null || observersToRemove.isEmpty()) {
			return;
		}
		
		if (observers == null || observers.isEmpty()) {
			observersToRemove.clear();
			return;
		}
		
		for (IntegerStorageObserver integerStorageObserver : observersToRemove) {
			observers.remove(integerStorageObserver);
		}
		
		observersToRemove.clear();
	}
	
	/**
	 * Method unregisters all observers.
	 */
	public void clearObservers() {
		if (observers != null) {
			observers.clear();
		}
		
		if (observersToRemove != null) {
			observersToRemove.clear();
		}
	}
	
	/**
	 * Returns current value.
	 * 
	 * @return current value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Sets new value to Subject.
	 * If value is different from previous value it will
	 * notify all registered observers.
	 * 
	 * @param value new value
	 */
	public void setValue(int value) {
		deleteObservers();
		// Only if new value is different than the current value:
		if (this.value != value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (observers != null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(this);
				}
			}
		}
	}
}