package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception which is thrown if parser can not
 * parse a document.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SmartScriptParserException extends RuntimeException {

	/** Serial version UID. */
	private static final long serialVersionUID = 986598269812L;

	/**
	 * Default constructor.
	 */
	public SmartScriptParserException() {
	}
	
	/**
	 * Constructor with message for user.
	 * 
	 * @param message message for user
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
	
	/**
	 * Constructor with throwable cause.
	 * 
	 * @param cause throwable cause
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor with message for user and throwable cause.
	 * 
	 * @param message message for user
	 * @param cause throwable cause
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
