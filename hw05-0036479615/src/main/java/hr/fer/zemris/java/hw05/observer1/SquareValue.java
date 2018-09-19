package hr.fer.zemris.java.hw05.observer1;

/**
 * Observer class, available to observe changes in IntegerStorage class.
 * When registered to Subject, class outputs to standard output square value
 * of new value set to Subject.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Default constructor.
	 */
	public SquareValue() {
	}
	
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println(
				"Provided new value: " + istorage.getValue() +
				", square is " + istorage.getValue() * istorage.getValue()
		);
	}

}
