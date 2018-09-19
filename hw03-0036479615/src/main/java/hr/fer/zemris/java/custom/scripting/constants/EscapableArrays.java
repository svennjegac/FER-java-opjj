package hr.fer.zemris.java.custom.scripting.constants;

/**
 * Class with arrays containing chars which can be escaped
 * in different modes of processing tokens.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class EscapableArrays {

	/** Chars which can be escaped in text reading mode. */
	private static final char[] TEXT_ESCAPABLE = {'\\', '{'};
	/** Chars which can be escaped in Strings. */
	private static final char[] STRING_ESCAPABLE = {'\\', '\"'};
	
	/**
	 * Returns chars which can be escaped in text reading mode.
	 * 
	 * @return chars which can be escaped in text reading mode.
	 */
	public static char[] textEscapable() {
		return TEXT_ESCAPABLE;
	}
	
	/**
	 * Returns chars which can be escaped in String reading mode.
	 * 
	 * @return chars which can be escaped in String reading mode.
	 */
	public static char[] stringEscpable() {
		return STRING_ESCAPABLE;
	}
}
