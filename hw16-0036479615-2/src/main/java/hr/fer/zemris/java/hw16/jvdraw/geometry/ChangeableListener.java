package hr.fer.zemris.java.hw16.jvdraw.geometry;

/**
 * Interface implemented by classes which want
 * to receive information that {@link Changeable} object changed.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface ChangeableListener {

	/**
	 * Method called when object changed.
	 * 
	 * @param source object which has changeds
	 */
	public void changeableObjectChanged(Changeable source);
}
