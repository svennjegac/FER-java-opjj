package hr.fer.zemris.java.hw14.dao;

/**
 * Exception thrown if error occurs while accesing data layer.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class DAOException extends RuntimeException {

	/** UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public DAOException() {
	}

	/**
	 * Constructor with detailed parameters.
	 * 
	 * @param message message for user
	 * @param cause throwable cause
	 * @param enableSuppression suppression enabling
	 * @param writableStackTrace stack tracing
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor with message for user and throwable cause.
	 * 
	 * @param message message for user
	 * @param cause throwable cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor with message for user.
	 * 
	 * @param message message for user
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructor with throwable cause.
	 * 
	 * @param cause throwable cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}