package hr.fer.zemris.java.hw04.db.lexer;

/**
 * Class for throwing QueryLexer exceptions.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class QueryLexerException extends RuntimeException {

	/** Serial Version UID */
	private static final long serialVersionUID = -37959325792311L;

	/**
	 * Constructor of exception.
	 * Throws provided message.
	 * 
	 * @param message message which will be thrown
	 */
	public QueryLexerException(String message) {
		super(message);
	}
}
