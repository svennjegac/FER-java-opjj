package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class representing one token.
 * Consists of type of token and its value.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Token {
	
	/** Type of generated token. */
	private TokenType tokenType;
	/** Value stored in token. */
	private Object value;

	/**
	 * Constructor of token.
	 * 
	 * @param type type of constructed token
	 * @param value stored value in token
	 */
	public Token(TokenType type, Object value) {
		if (type == null) {
			throw new SmartScriptLexerException("tokenType can not be null.");
		}
		
		this.tokenType = type;
		this.value = value;
	}
	
	/**
	 * Method returns token value.
	 * 
	 * @return token value
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Method returns token type.
	 * 
	 * @return token type.
	 */
	public TokenType getType() {
		return tokenType;
	}
}
