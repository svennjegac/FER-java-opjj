package hr.fer.zemris.java.hw04.db;

/**
 * Class having seven different implementations of
 * IComparisonOperator interface.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ComparisonOperators {

	/** Checks that first value is less than second. */
	public static final IComparisonOperator LESS = (value1, value2) -> value1.compareTo(value2) < 0;
	
	/** Checks that first value is less than second or equal. */
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) <= 0; ;
	
	/** Checks that first value is greater than second. */
	public static final IComparisonOperator GREATER = (value1, value2) -> value1.compareTo(value2) > 0;
	
	/** Checks that first value is greater than second or equal. */
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) >= 0;
	
	/** Checks that values are equal. */
	public static final IComparisonOperator EQUALS = (value1, value2) -> value1.compareTo(value2) == 0;
	
	/** Checks that values are not equal. */
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> value1.compareTo(value2) != 0;
	
	/**
	 * Checks if first value accomplishes provided pattern.
	 * Pattern can have zero or one '*' which are wildcard signs.
	 * On position of that sign can everything occur, possibly even nothing.
	 */
	public static final IComparisonOperator LIKE = new IComparisonOperator() {
		
		/**
		 * Checks if value1 accomplishes pattern.
		 */
		@Override
		public boolean satisfied(String value1, String pattern) {
			//if pattern do not have '*', value1 must be equal as pattern
			if (pattern.indexOf('*') == -1) {
				return value1.equals(pattern);
			}
			
			//extract what is on left and right side of pattern
			String leftSideOfPattern = pattern.substring(0, pattern.indexOf('*'));
			String rightSideOfPattern = pattern.substring(pattern.indexOf('*') + 1, pattern.length());
			
			//check that value1 have enough length to accomplish patter
			if (value1.length() < leftSideOfPattern.length() + rightSideOfPattern.length()) {
				return false;
			}
			
			//extract from value1 length of leftSide of pattern
			String value1LeftSide = value1.substring(0, leftSideOfPattern.length());
			//extract from end of value1 length of rightSide of patter
			String value1RightSide = value1.substring(value1.length() - rightSideOfPattern.length(), value1.length());
			
			if (!value1LeftSide.equals(leftSideOfPattern) || !value1RightSide.equals(rightSideOfPattern)) {
				return false;
			}
			
			return true;
		}
	};
}
