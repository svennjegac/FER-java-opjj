package hr.fer.zemris.java.hw04.db;

/**
 * Class representing a single conditional expression which can
 * be executed on a single student row.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ConditionalExpression {

	/** Gets dedicated student attribute. */
	private IFieldValueGetter fieldGetter;
	/** Literal with which comparison of attribute will be made with. */
	private String stringLiteral;
	/** Operation which will be made between attribute and literal. */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor accepts field getter, literal and
	 * comparison operator and make conditional expression.
	 * 
	 * @param fieldGetter getter of records field
	 * @param literal String literal to compare with
	 * @param comparisonOperator operator of expression
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String literal,
			IComparisonOperator comparisonOperator) {
		super();
		
		if (fieldGetter == null || literal == null || comparisonOperator == null) {
			throw new IllegalArgumentException("Constructor can not accept null fields.");
		}
		
		this.fieldGetter = fieldGetter;
		this.stringLiteral = literal;
		this.comparisonOperator = comparisonOperator;
	}
	
	/**
	 * Returns comparisonOperator.
	 * 
	 * @return comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	/**
	 * Returns fieldGetter.
	 * 
	 * @return fieldGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}
	
	/**
	 * Returns String literal.
	 * 
	 * @return String literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}
}
