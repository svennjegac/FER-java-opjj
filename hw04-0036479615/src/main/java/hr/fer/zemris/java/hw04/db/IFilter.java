package hr.fer.zemris.java.hw04.db;

/**
 * Interface having a single method.
 * It determines whether StudentRecord should be accepted or rejected.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface IFilter {
	/**
	 * Method gets a StudentRecord and determines whether
	 * it should be accepted or rejected.
	 * 
	 * @param record tested StudentRecord
	 * @return <code>true</code> if record should be accepted,
	 * 			<code>false</code> if record should be rejected
	 */
	boolean accepts(StudentRecord record);
}
