package hr.fer.zemris.java.hw16.jvdraw.actions;

/**
 * Enumeration representing JVD file for saving.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public enum JVDFile {

	/** Description. */
	DESCRIPTION("jvd files"),
	/** JVD type. */
	JVD("jvd"),
	/** JVD extension. */
	JVD_EXT("." + JVDFile.JVD.getValue());
	
	/** Value. */
	private final String value;
	
	/**
	 * Constructor accepting value.
	 * 
	 * @param value value
	 */
	private JVDFile(String value) {
		this.value = value;
	}
	
	/**
	 * Getter for value
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}
}
