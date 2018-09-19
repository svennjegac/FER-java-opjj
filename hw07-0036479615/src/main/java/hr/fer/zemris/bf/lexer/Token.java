package hr.fer.zemris.bf.lexer;

/**
 * Class representing a single token.
 * It consists of a token type and its value.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Token {

	/** Token type. */
	private TokenType tokenType;
	/** Token value. */
	private Object tokenValue;
	
	/**
	 * Constructor of a token class.
	 * 
	 * @param tokenType token type
	 * @param tokenValue token value
	 */
	public Token(TokenType tokenType, Object tokenValue) {
		super();
		
		if (tokenType == null) {
			throw new IllegalArgumentException("Token type can not be null.");
		}
		
		this.tokenType = tokenType;
		this.tokenValue = tokenValue;
	}
	
	/**
	 * Gets token type.
	 * 
	 * @return token type
	 */
	public TokenType getTokenType() {
		return tokenType;
	}
	
	/**
	 * Gets token value.
	 * 
	 * @return token value
	 */
	public Object getTokenValue() {
		return tokenValue;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type: " + tokenType + ", Value: " + tokenValue);
		
		if (tokenValue != null) {
			sb.append(", Value is instance of:\n" + tokenValue.getClass());
		}
		
		return sb.toString();
		
	}
	
}
