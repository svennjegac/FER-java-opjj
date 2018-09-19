package hr.fer.zemris.java.hw04.db;

import java.util.List;

/**
 * This class is implementation of IFilter interface which
 * means that it can determine whether StudentRecord should be in
 * filtered list or not.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class QueryFilter implements IFilter {

	/** List of conditional expressions. */
	private List<ConditionalExpression> queryList;
	
	/**
	 * Constructor of QueryFilter.
	 * Accepts list of ConditionlExpressions.
	 * 
	 * @param queryList list of conditional expressions
	 */
	public QueryFilter(List<ConditionalExpression> queryList) {
		if (queryList == null) {
			throw new IllegalArgumentException("List in query filter can not be null.");
		}
		
		this.queryList = queryList;
	}
	
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression conditionalExpression : queryList) {
			
			if (conditionalExpression.getComparisonOperator().satisfied(
							conditionalExpression.getFieldGetter().get(record),
							conditionalExpression.getStringLiteral()))
			{
				continue;
			}
			
			return false;
		}
		
		return true;
	}

}
