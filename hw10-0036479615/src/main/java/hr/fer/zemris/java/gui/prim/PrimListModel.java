package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class representing a list model for prime numbers.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class PrimListModel implements ListModel<Integer> {

	/** List of prime numbers. */
	private List<Integer> primNumbers;
	
	/** List of listeners. */
	private List<ListDataListener> listeners;
	
	/**
	 * Constructor.
	 */
	public PrimListModel() {
		primNumbers = new ArrayList<>();
		primNumbers.add(1);
		
		listeners = new ArrayList<>();
	}
	
	/**
	 * Method generates next prime number and dispatch event to all listeners.
	 */
	public void next() {
		primNumbers.add(nextPrim(primNumbers.get(primNumbers.size() - 1)));
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, primNumbers.size() - 1, primNumbers.size() - 1);
		
		listeners.forEach(l -> {
			l.intervalAdded(event);
		});
	}
	
	@Override
	public int getSize() {
		return primNumbers.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primNumbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}
	
	/**
	 * Method calculates next prime number.
	 * 
	 * @param x current number
	 * @return next prime number
	 */
	private int nextPrim(int x) {	
		while (true) {
			x++;
			
			if (isPrim(x)) {
				return x;
			}
		}
	}
	
	/**
	 * Method checks if number is prime or not.
	 * 
	 * @param x number
	 * @return <code>true</code> if number is prime, <code>false</code> otherwise
	 */
	private boolean isPrim(int x) {
		if (x < 2) {
			return false;
		}
		
		if (x == 2) {
			return true;
		}
		
		if (x % 2 == 0) {
			return false;
		}
		
		for (int i = 3; i < Math.sqrt(x); i += 2) {
			if (x % i == 0) {
				return false;
			}
		}
		
		return true;
	}
}
