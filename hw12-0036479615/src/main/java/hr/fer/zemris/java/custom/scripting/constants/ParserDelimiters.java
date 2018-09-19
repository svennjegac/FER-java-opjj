package hr.fer.zemris.java.custom.scripting.constants;

/**
 * String constants which are used to properly
 * parse document.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public enum ParserDelimiters {

	/** Treated as tag opener. */
	OPEN_TAG_DELIMITER("{$"),
	/** Treated as closing tag. */
	CLOSE_TAG_DELIMITER("$}"),
	/** Treated as start/end of string.(Only in tags) */
	STRING_DELIMITER("\""),
	/** Treated as sign which indicates that next char is going to be escaped. */
	ESCAPE_DELIMITER("\\"),
	/** Indication of function.(Only in tags) */
	FUNCTION_DELIMITER("@"),
	/** Treated as for loop start.(Only in tags) */
	FOR_LOOP_DELIMITER("for"),
	/** Treated as end of non empty tag.(Only in tags) */
	END_LOOP_DELIMITER("end"),
	/** Treated as echo tag start.(Only in tags) */
	ECHO_TAG_DELIMITER("=");
	
	/** Value of constant. */
	private final String value;
	
	/**
	 * Constructor which initialize constants.
	 * 
	 * @param constant constant string
	 */
	private ParserDelimiters(String constant) {
		this.value = constant;
	}
	
	/**
	 * Method returns constant value.
	 * 
	 * @return constant value
	 */
	public String getValue() {
		return value;
	}
}
