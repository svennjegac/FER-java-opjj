package hr.fer.zemris.java.hw04.db.lexer;

/**
 * Lexer which accepts string and extracts tokens.
 * Tokens are used to build queries for database.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class QueryLexer {
	
	/** Array of chars representing string query. */
	private char[] data;
	/** Pointer on first unprocessed char. */
	private int currentIndex;
	/** State of lexer. */
	private QueryLexerState state;
	/** Last retrieved token. */
	private QueryLexerToken token;
	
	/**
	 * Constructor accepts query string and outputs query.
	 * 
	 * @param query string query
	 */
	public QueryLexer(String query) {
		if (query == null) {
			throw new QueryLexerException("Query can not be null.");
		}
		
		data = query.toCharArray();
		currentIndex = 0;
		state = QueryLexerState.FIELD_NAME_STATE;
	}
	
	/**
	 * Sets lexer state.
	 * 
	 * @param state new lexer state
	 */
	public void setState(QueryLexerState state) {
		if (state == null) {
			throw new QueryLexerException("State can not be null.");
		}
		
		this.state = state;
	}
	
	/**
	 * Method returns next token.
	 * 
	 * @return next token
	 */
	public QueryLexerToken nextToken() {
		// can not make queries after end was reached
		if (token != null && token.getType() == QueryLexerTokenType.EOF_TOKEN_TYPE) {
			throw new QueryLexerException("Can not read more tokens. End was reached in previous iteration.");
		}
		
		//if end is reached return end of file
		if (currentIndex >= data.length) {
			return token = new QueryLexerToken(QueryLexerTokenType.EOF_TOKEN_TYPE, null);
		}
		
		//return token depending on state
		switch (state) {
			case FIELD_NAME_STATE:	return token = extractFieldName();
			case OPERATOR_STATE:	return token = extractOperator();
			case LITERAL_STATE:		return token = extractLiteral();
			case SEPARATOR_STATE:	return token = extractSeparator();
			case WHITESPACE_STATE:	return token = extractWhitespace();
			
			default:
				throw new QueryLexerException("This should never happen. Something went wrong.");
		}
	}
	
	/**
	 * Returns new QueryLexer token.
	 * 
	 * @param type token type
	 * @param start pointer on first char in data which is part of token
	 * @param end pointer on first char which is not part of data token
	 * @return QueryLexerToken
	 */
	private QueryLexerToken token(QueryLexerTokenType type, int start, int end) {
		return new QueryLexerToken(
				type,
				String.copyValueOf(data, start, end)
		);
	}
	
	/**
	 * Interface representing condition which decides
	 * whether iterating should stop or not.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private interface IterateCondition {
		/**
		 * Method decides whether iterating should stop.
		 * 
		 * @param c char
		 * @return <code>true</code> if iterating should stop, <code>false</code> otherwise
		 */
		boolean stopIterating(char c);
	}
	
	/**
	 * Method iterates until condition is completed,
	 * or when data end is reached.
	 * 
	 * @param condition condition which stops iteration
	 * @return <code>true</code> if iteration stopped by condition,
	 * 			<code>false</code> if iteration stopped because end was reached
	 */
	private boolean iterate(IterateCondition condition) {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			
			if (condition.stopIterating(c)) {
				return true;
			}
			
			currentIndex++;
		}
		
		return false;
	}
	
	/**
	 * Method extracts field name of query.
	 * 
	 * @return next token
	 */
	private QueryLexerToken extractFieldName() {
		skipBlanks();
		
		int startIndex = currentIndex;
		
		iterate(c -> !Character.isLetter(c));
		
		return token(QueryLexerTokenType.FILED_NAME_TOKEN_TYPE, startIndex, currentIndex - startIndex);
	}
	
	/**
	 * Method extracts operator from query.
	 * 
	 * @return next token
	 */
	private QueryLexerToken extractOperator() {
		skipBlanks();
		
		int startIndex = currentIndex;
		
		iterate(c -> Character.isWhitespace(c) || c == '\"');
		
		return token(QueryLexerTokenType.OPERATOR_TOKEN_TYPE, startIndex, currentIndex - startIndex);
	}
	
	/**
	 * Method extracts whitespace as token.
	 * 
	 * @return next token
	 */
	private QueryLexerToken extractWhitespace() {
		int startIndex = currentIndex;
		
		iterate(c -> !Character.isWhitespace(c));
		
		return token(QueryLexerTokenType.WHITESPACE_TOKEN_TYPE, startIndex, currentIndex - startIndex);
	}
	
	/**
	 * Method extracts literal as token.
	 * 
	 * @return next token
	 */
	private QueryLexerToken extractLiteral() {
		skipBlanks();
		validateIndex();
		
		if (data[currentIndex] != '\"') {
			throw new QueryLexerException("Literal does not start with '\"'; was '" + data[currentIndex] + "'");
		}
		
		currentIndex++;
		int startIndex = currentIndex;
		
		boolean endOfLiteralReached = iterate(c -> c == '\"');
		
		currentIndex++;
		
		if (!endOfLiteralReached) {
			throw new QueryLexerException("End of litearal never reached.");
		}
		
		return token(QueryLexerTokenType.LITERAL_TOKEN_TYPE, startIndex, currentIndex - startIndex - 1);
	}
	
	/**
	 * Method extracts separator of conditions in query.
	 * 
	 * @return next token
	 */
	private QueryLexerToken extractSeparator() {		
		firstMustBeWhitespace();
		skipBlanks();
		
		if (currentIndex >= data.length) {
			return token = new QueryLexerToken(QueryLexerTokenType.EOF_TOKEN_TYPE, null);
		}
		
		int startIndex = currentIndex;		
		
		iterate(c -> !Character.isLetter(c));
		
		QueryLexerToken token = token(QueryLexerTokenType.SEPARATOR_TOKEN_TYPE, startIndex, currentIndex - startIndex);
		return new QueryLexerToken(token.getType(), token.getValue().toLowerCase());
	}
	
	/**
	 * Throws exception if first character is not whitespace.
	 */
	private void firstMustBeWhitespace() {
		if (!Character.isWhitespace(data[currentIndex])) {
			throw new QueryLexerException("Whitespace missing.");
		}
	}
	
	/**
	 * Throws exception if end is reached.
	 */
	private void validateIndex() {
		if (currentIndex >= data.length) {
			throw new QueryLexerException("Query was not properly built.");
		}
	}
	
	/**
	 * Skips all blanks until first char which is not blank.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			
			if (Character.isWhitespace(c)) {
				currentIndex++;
				continue;
			}
			
			break;
		}
	}
}
