package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw04.db.lexer.QueryLexer;
import hr.fer.zemris.java.hw04.db.lexer.QueryLexerException;
import hr.fer.zemris.java.hw04.db.lexer.QueryLexerState;
import hr.fer.zemris.java.hw04.db.lexer.QueryLexerToken;
import hr.fer.zemris.java.hw04.db.lexer.QueryLexerTokenType;

/**
 * Class representing a parser of database query.
 * It accepts a query, parses it and makes a list of ConditionalExpressions
 * if query is valid.
 * If query is not valid it will throw QueryParserException.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class QueryParser {

	/** Lexer for processing queries. */
	QueryLexer lexer;
	/** List of conditional expressions. */
	List<ConditionalExpression> queryList;
	
	/** First name attribute definition. */
	private static final String FIRST_NAME = "firstName";
	/** Last name attribute definition. */
	private static final String LAST_NAME = "lastName";
	/** JMBAG attribute definition. */
	private static final String JMBAG = "jmbag";
	
	/** Less operator definition. */
	private static final String LESS = "<";
	/** Less or equals operator definition. */
	private static final String LESS_OR_EQUALS = "<=";
	/** Greater operator definition. */
	private static final String GREATER = ">";
	/** Greater or equals operator definition. */
	private static final String GREATER_OR_EQUALS = ">=";
	/** Equals operator definition. */
	private static final String EQUALS = "=";
	/** Not equals operator definition. */
	private static final String NOT_EQUALS = "!=";
	/** LIKE operator definition. */
	private static final String LIKE = "LIKE";
	
	/**
	 * Constructor of QueryParser.
	 * It accepts query and parses it.
	 * 
	 * @param query database query
	 */
	public QueryParser(String query) {
		if (query == null) {
			throw new IllegalArgumentException("Query can not be null.");
		}
		
		queryList = new ArrayList<>();
		lexer = new QueryLexer(query);
		
		parse();
	}
	
	/**
	 * Gets next token from lexer.
	 * 
	 * @return next token
	 */
	private QueryLexerToken getToken() {
		try {
			QueryLexerToken token = lexer.nextToken();
			
			return token;
		} catch (QueryLexerException ex) {
			throw new QueryParserException(ex.getMessage());
		}
	}
	
	/**
	 * Method determines if parsed query was direct.
	 * Direct queries are those who have only one ConditionalExpression,
	 * field must be JMBAG and operator must be equals.
	 * 
	 * @return <code>true</code> if query is direct, <code>false</code> otherwise
	 */
	public boolean isDirectQuery() {
		if (queryList.size() != 1) {
			return false;
		}
		
		ConditionalExpression expression =  queryList.get(0);
		
		if (expression.getFieldGetter() != FieldValueGetters.JMBAG) {
			return false;
		}
		
		if (expression.getComparisonOperator() != ComparisonOperators.EQUALS) {
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * If query was direct this method returns literal
	 * of ConditionalExpression.
	 * If it was not direct query it will throw IllegalStateException.
	 * 
	 * @return literal of ConditionalExpression.
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException("Query is not direct.");
		}
		
		return queryList.get(0).getStringLiteral();
	}
	
	/**
	 * Method returns list containing all ConditionalExpressions of query.
	 * 
	 * @return list of query ConditionalExpressions
	 */
	public List<ConditionalExpression> getQuery() {
		return queryList;
	}
	
	/**
	 * Method parses query.
	 */
	private void parse() {
		while (true) {
			QueryLexerToken token = getToken();
			
			if (token.getType() == QueryLexerTokenType.EOF_TOKEN_TYPE) {
				break;
			}
			
			if (token.getType() == QueryLexerTokenType.FILED_NAME_TOKEN_TYPE) {
				processCondition(token);
				continue;
			}
			
			if (token.getType() == QueryLexerTokenType.SEPARATOR_TOKEN_TYPE) {
				processSeparator(token);
				continue;
			}
		}
		
		if (queryList.isEmpty()) {
			throw new QueryParserException("Provide valid query");
		}
	}
	
	/**
	 * Method accepts one token(FIELD_NAME_TOKEN_TYPE) and
	 * constructs new ConditionalExpression.
	 * 
	 * @param token first token representing field name of conditional expression
	 */
	private void processCondition(QueryLexerToken token) {
		IFieldValueGetter fieldGetter = getIFieldValueGetter(token);
		
		lexer.setState(QueryLexerState.OPERATOR_STATE);
		IComparisonOperator operator = getIComparisonOperator(getToken());
		
		//if operator is like operator, next must be at least one whitespace
		if (operator.equals(ComparisonOperators.LIKE)) {
			processWhitespace();
		}
	
		lexer.setState(QueryLexerState.LITERAL_STATE);
		String literal = getLiteral(getToken());
		
		//if operator was like operator, check that literal has valid pattern
		if (operator.equals(ComparisonOperators.LIKE)) {
			likePatternValid(literal);
		}
		
		//if there will be more tokens, they must be separated by separator,
		//so next state is separator state
		lexer.setState(QueryLexerState.SEPARATOR_STATE);
		
		ConditionalExpression conditionalExpression = new ConditionalExpression(fieldGetter, literal, operator);
		
		queryList.add(conditionalExpression);
	}
	
	/**
	 * Accepts token which is FIELD_NAME_TOKEN_TYPE and
	 * returns dedicated FieldValueGetter.
	 * 
	 * @param token processed token
	 * @return dedicated FieldValueGetter
	 */
	private IFieldValueGetter getIFieldValueGetter(QueryLexerToken token) {
		validateToken(token, QueryLexerTokenType.FILED_NAME_TOKEN_TYPE);
		
		switch (token.getValue()) {
		case FIRST_NAME:
			return FieldValueGetters.FIRST_NAME;
		case LAST_NAME:
			return FieldValueGetters.LAST_NAME;
		case JMBAG:
			return FieldValueGetters.JMBAG;
		default:
			throw new QueryParserException("Not recognizable field; was '" + token.getValue() + "'");
		}
	}
	
	/**
	 * Accepts token which is OPERATOR_TOKEN_TYPE and
	 * returns dedicated ComparisonOperator.
	 * 
	 * @param token processed token
	 * @return dedicated ComparisonOperator
	 */
	private IComparisonOperator getIComparisonOperator(QueryLexerToken token) {
		validateToken(token, QueryLexerTokenType.OPERATOR_TOKEN_TYPE);
		
		switch (token.getValue()) {
		case LESS:
			return ComparisonOperators.LESS;
		case LESS_OR_EQUALS:
			return ComparisonOperators.LESS_OR_EQUALS;
		case GREATER:
			return ComparisonOperators.GREATER;
		case GREATER_OR_EQUALS:
			return ComparisonOperators.GREATER_OR_EQUALS;
		case EQUALS:
			return ComparisonOperators.EQUALS;
		case NOT_EQUALS:
			return ComparisonOperators.NOT_EQUALS;
		case LIKE:
			return ComparisonOperators.LIKE;
		default:
			throw new QueryParserException("Operator was not valid; was '" + token.getValue() + "'");
		}
	}
	
	/**
	 * Accepts token of literal type and
	 * returns its value.
	 * 
	 * @param token provided token
	 * @return literal value
	 */
	private String getLiteral(QueryLexerToken token) {
		validateToken(token, QueryLexerTokenType.LITERAL_TOKEN_TYPE);
		
		return token.getValue();
	}
	
	/**
	 * Accepts SEPARATOR_TOKEN_TYPE, checks if it is valid
	 * separator and delegates construction of next expression to
	 * other method.
	 * 
	 * @param token separator token
	 */
	private void processSeparator(QueryLexerToken token) {
		validateToken(token, QueryLexerTokenType.SEPARATOR_TOKEN_TYPE);
		
		if (!token.getValue().equals("and")) {
			throw new QueryParserException("Separator was not valid; expected 'and'; was '" + token.getValue() + "'");
		}
		
		lexer.setState(QueryLexerState.FIELD_NAME_STATE);
		
		QueryLexerToken fieldToken = getToken();
		processCondition(fieldToken);
	}
	
	/**
	 * Method checks if next token was whitespace and if
	 * it has any white spaces.
	 */
	private void processWhitespace() {		
		lexer.setState(QueryLexerState.WHITESPACE_STATE);
		
		QueryLexerToken token = getToken();
		validateToken(token, QueryLexerTokenType.WHITESPACE_TOKEN_TYPE);
		
		if (token.getValue().length() <= 0) {
			throw new QueryParserException("After LIKE operator, whitespace is expected.");
		}
	}
	
	/**
	 * Validates if token is of selected type.
	 * 
	 * @param token tested token
	 * @param type expected type
	 */
	private void validateToken(QueryLexerToken token, QueryLexerTokenType type) {
		if (token.getType() != type) {
			throw new QueryParserException("Token type expected: " + type + "; was: " + token.getType());
		}
	}
	
	/**
	 * Method checks if like pattern is valid.
	 * Valid like patterns have maximum of 1 wild card character('*').
	 * 
	 * @param pattern <code>true</code> if pattern is valid, <code>false</code> otherwise
	 */
	private void likePatternValid(String pattern) {
		if (pattern == null) {
			throw new QueryParserException("Pattern can not be null.");
		}
		
		boolean asterixOccured = false;
		
		for (int i = 0; i < pattern.length(); i++) {
			if (pattern.charAt(i) == '*') {
				if (asterixOccured) {
					throw new QueryParserException("Pattern has too much '*'; was '" + pattern + "'");
				}
				
				asterixOccured = true;
			}
		}
	}
}
