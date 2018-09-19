package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class representing an exception which is thrown if {@link SmartScriptEngine}
 * goes in problems.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SmartScriptEngineException extends RuntimeException {

	/** UID */
	private static final long serialVersionUID = 1L;

	/**
	 * Exception with string message
	 * 
	 * @param message message for user
	 */
	public SmartScriptEngineException(String message) {
		super(message);
	}
	
	/**
	 * Exception with throwable cause
	 * 
	 * @param cause throwable cause
	 */
	public SmartScriptEngineException(Throwable cause) {
		super(cause);
	}
}
