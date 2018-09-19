package hr.fer.zemris.java.hw16.jvdraw.geometry;

/**
 * Classes which needs to be serialized must implement this interface.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface Serializable {

	/**
	 * Method returns string representation of serialization of object.
	 * 
	 * @return string representation of serialization of object
	 */
	public String serialize();
}
