package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Excpetion thrown if something with persistance layer fails.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class DAOException extends RuntimeException {

	/** UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Exception with message for user and cause.
	 * 
	 * @param message message for user
	 * @param cause cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Exception with message for user.
	 * 
	 * @param message message for user
	 */
	public DAOException(String message) {
		super(message);
	}
}