package hr.fer.zemris.bf.lexer;

/**
 * Exception which is thrown if lexer goes into problems.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class LexerException extends RuntimeException {

	/** Serial version UID. */
	private static final long serialVersionUID = 93750382500221L;

	/**
	 * LexerException constructor with message for user.
	 * 
	 * @param message message for user
	 */
	public LexerException(String message) {
		super(message);
	}
}
