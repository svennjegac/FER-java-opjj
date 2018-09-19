package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class representing lexer exception.
 * Lexer throws an exception if given String can not
 * be parsed because it does not accomplish lexing rules.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SmartScriptLexerException extends RuntimeException {
	
	/** SVUID */
	private static final long serialVersionUID = 17582502302323L;

	/** Default exception constructor. */
	public SmartScriptLexerException() {
	}
	
	/**
	 * Constructor for exception with message for user.
	 * 
	 * @param message message for user
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}
	
	/**
	 * Constructor for exception which re throws cause.
	 * 
	 * @param cause throwable class
	 */
	public SmartScriptLexerException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor for exception which has message for user
	 * and re throws a cause.
	 * 
	 * @param message message for user
	 * @param cause throwable class
	 */
	public SmartScriptLexerException(String message, Throwable cause) {
		super(message, cause);
	}
}
