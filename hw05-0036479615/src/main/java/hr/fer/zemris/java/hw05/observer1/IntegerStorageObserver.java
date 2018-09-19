package hr.fer.zemris.java.hw05.observer1;

/**
 * Interface prescribed by IntegerStorage class.
 * It must be implemented by any class which would like to
 * register as Observer to IntegerStorage Subject.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface IntegerStorageObserver {
	
	/**
	 * Method which is invoked by IntegerStorage Subject
	 * whenever its value change,
	 * 
	 * @param istorage Integer storage as argument
	 */
	public void valueChanged(IntegerStorage istorage);
}
