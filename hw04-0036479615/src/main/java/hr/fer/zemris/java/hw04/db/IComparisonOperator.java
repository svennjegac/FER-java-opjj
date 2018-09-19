package hr.fer.zemris.java.hw04.db;

/**
 * Interface having only one method.
 * It determines if value1 and value2 satisfies condition.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface IComparisonOperator {
	/**
	 * Method accepts two Strings, process them and
	 * returns whether they satisfies condition or not.
	 * 
	 * @param value1 first parameter
	 * @param value2 second parameter
	 * @return <code>true</code> if parameters satisfies condition,
	 * 			<code>false</code> otherwise
	 */
	boolean satisfied(String value1, String value2);
}
