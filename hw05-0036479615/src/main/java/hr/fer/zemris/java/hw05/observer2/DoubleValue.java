package hr.fer.zemris.java.hw05.observer2;

/**
 * Observer class, available to observe changes in IntegerStorage class.
 * When registered to Subject, class outputs to standard output new value
 * stored in Subject, multiplied by 2.(Doubled).
 * 
 * After n times(n is defined when constructing an Observer) IntegerStorage changed
 * its state, this Observer automatically unregisters itself from Subject.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class DoubleValue implements IntegerStorageObserver {

	/** Counter of changed states in Subject. */
	private int counter;
	
	/**
	 * Constructor which accepts parameter.
	 * Parameter defines after how many IntegerStorage state
	 * changes Observer will unregister.
	 * 
	 * @param counter counter of IntegerStorage changed states
	 */
	public DoubleValue(int counter) {
		if (counter < 1) {
			throw new IllegalArgumentException("Counter can not be less than 1; was: " + counter);
		}
		
		this.counter = counter;
	}
	
	@Override
	public void valueChanged(IntegerStorageChange change) {
		counter--;
		
		System.out.println("Double value: " + change.getCurrent() * 2);
	
		if (counter <= 0) {
			change.getIstorage().removeObserver(this);;
		}
	}

}
