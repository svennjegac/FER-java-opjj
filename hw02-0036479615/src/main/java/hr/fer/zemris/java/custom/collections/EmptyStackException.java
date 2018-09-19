package hr.fer.zemris.java.custom.collections;

/**
 * Class utilize exception which is thrown when stack is empty and user tries to
 * pop/peek it.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException {

	/** Serial version UID */
	private static final long serialVersionUID = 186532265608263L;

	/**
	 * Default constructor
	 */
	public EmptyStackException() {
	}

	/**
	 * Constructor with appropriate message.
	 * 
	 * @param message
	 *            appropriate message
	 */
	public EmptyStackException(String message) {
		super(message);
	}

	/**
	 * Constructor with throwable cause.
	 * 
	 * @param cause
	 *            throwable cause
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor with throwable cause and message.
	 * 
	 * @param message
	 *            appropriate message
	 * @param cause
	 *            throwable cause
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
}
