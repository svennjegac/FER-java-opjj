package hr.fer.zemris.java.hw03.prob1;

/**
 * Exception which is thrown if lexer comes into
 * problem while generating new token.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class LexerException extends RuntimeException {

	/** Serial version UID. */
	private static final long serialVersionUID = -838417587195269999L;

	/**
	 * Default exception constructor.
	 */
	public LexerException() {
	}
	
	/**
	 * Constructor with message for user.
	 * 
	 * @param message message for user
	 */
	public LexerException(String message) {
		super(message);
	}
	
	/**
	 * Constructor with throwable cause.
	 * 
	 * @param cause throwble cause
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor with message for user and throwable cause.
	 * 
	 * @param message message for user
	 * @param cause throwable cause
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}
}
