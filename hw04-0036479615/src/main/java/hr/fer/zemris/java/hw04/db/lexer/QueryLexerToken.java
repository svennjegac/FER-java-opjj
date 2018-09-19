package hr.fer.zemris.java.hw04.db.lexer;

/**
 * Class representing token.
 * Token is ouput of QueryLexer class.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class QueryLexerToken {
	
	/** Token type. */
	QueryLexerTokenType type;
	/** Value of token. */
	String value;
	
	/**
	 * Constructor accepts token type and string.
	 * 
	 * @param type type of token
	 * @param value value of token
	 */
	public QueryLexerToken(QueryLexerTokenType type, String value) {
		if (type == null) {
			throw new IllegalArgumentException("Token type can not be null.");
		}
		
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns type of token.
	 * 
	 * @return type of token
	 */
	public QueryLexerTokenType getType() {
		return type;
	}
	
	/**
	 * Returns value of token.
	 * 
	 * @return value of token
	 */
	public String getValue() {
		return value;
	}
}
