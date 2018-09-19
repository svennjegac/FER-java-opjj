package hr.fer.zemris.java.hw04.db;

/**
 * Class representing exception which is thrown by QueryParser.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class QueryParserException extends RuntimeException {

	/** Serial Version UID. */
	private static final long serialVersionUID = -2197479214241L;

	/**
	 * Constructor of exception with String message.
	 * 
	 * @param message exception message
	 */
	public QueryParserException(String message) {
		super(message);
	}
	
	/**
	 * Constructor of exception which throws provided cause.
	 * 
	 * @param cause throwable cause
	 */
	public QueryParserException(Throwable cause) {
		super(cause);
	}
}
