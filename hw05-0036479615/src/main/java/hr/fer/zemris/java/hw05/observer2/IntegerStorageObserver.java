package hr.fer.zemris.java.hw05.observer2;

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
	 * @param change reference to object which encapsulates
	 * 					everything important about changing of state
	 */
	public void valueChanged(IntegerStorageChange change);
}
