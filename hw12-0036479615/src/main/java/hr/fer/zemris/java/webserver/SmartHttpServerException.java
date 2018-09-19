package hr.fer.zemris.java.webserver;

/**
 * Exception thrown by {@link SmartHttpServer} if problem occurs.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SmartHttpServerException extends RuntimeException {

	/** UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Exception with message for user.
	 * 
	 * @param message message for user
	 */
	public SmartHttpServerException(String message) {
		super(message);
	}
}
