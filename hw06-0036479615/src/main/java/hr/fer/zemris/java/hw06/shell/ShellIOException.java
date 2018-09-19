package hr.fer.zemris.java.hw06.shell;

/**
 * Exception which is thrown if environment can not read or write.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ShellIOException extends RuntimeException {

	/** Serial version UID. */
	private static final long serialVersionUID = 8935662391L;

	/**
	 * Exception with message for user.
	 * 
	 * @param message message for user
	 */
	public ShellIOException(String message) {
		super(message);
	}
	
	/**
	 * Exception with throwable cause.
	 * 
	 * @param cause throwable cause
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}
}
