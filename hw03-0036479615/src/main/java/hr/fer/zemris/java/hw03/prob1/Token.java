package hr.fer.zemris.java.hw03.prob1;

/**
 * Token which is generated as single output
 * of lexer.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Token {
	
	/** Type of token. */
	private TokenType tokenType;
	/** Token value. */
	private Object value;

	/**
	 * Constructor accepting token type and value.
	 * 
	 * @param type token type
	 * @param value token value
	 */
	public Token(TokenType type, Object value) {
		if (type == null) {
			throw new LexerException("tokenType can not be null.");
		}
		
		this.tokenType = type;
		this.value = value;
	}
	
	/**
	 * Returns token value.
	 * 
	 * @return token value
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Returns token type.
	 * 
	 * @return token type
	 */
	public TokenType getType() {
		return tokenType;
	}
}
