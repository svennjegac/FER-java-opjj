package hr.fer.zemris.java.hw05.observer1;

/**
 * Observer class, available to observe changes in IntegerStorage class.
 * When registered to Subject, class outputs to standard output how
 * many time Subjects state changed.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ChangeCounter implements IntegerStorageObserver {

	/** Counter of changed states since registration. */
	private int counter;
	
	/**
	 * Default constructor with counter set to 0.
	 */
	public ChangeCounter() {
		counter = 0;
	}
	
	@Override
	public void valueChanged(IntegerStorage istorage) {
		counter++;
		System.out.println("Number of value changes since tracking: " + counter);
	}

}
