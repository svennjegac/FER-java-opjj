package hr.fer.zemris.bf.parser;

/**
 * Exception which is thrown if parser runs into problems.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ParserException extends RuntimeException {

	/** Serial version UID. */
	private static final long serialVersionUID = -6019488159663711661L;

	/**
	 * Constructor of exception with message for user.
	 * 
	 * @param message message for user
	 */
	public ParserException(String message) {
		super(message);
	}
	
	/**
	 * Constructor of exception with throwable cause.
	 * 
	 * @param cause throwable cause
	 */
	public ParserException(Throwable cause) {
		super(cause);
	}
}
