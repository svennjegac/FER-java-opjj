package hr.fer.zemris.java.hw04.db;

/**
 * Interface having a single method.
 * It gets a dedicated attribute from StudentRecord.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface IFieldValueGetter {
	/**
	 * Method which accepts a StudentRecord and returns
	 * String representation of its attribute.
	 * 
	 * @param record StudentRecord whose attribute will be fetched
	 * @return String representing attribute
	 */
	String get(StudentRecord record);
}
