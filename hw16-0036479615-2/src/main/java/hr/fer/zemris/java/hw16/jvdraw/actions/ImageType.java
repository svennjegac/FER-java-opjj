package hr.fer.zemris.java.hw16.jvdraw.actions;

/**
 * Image types.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public enum ImageType {

	/** PNG type. */
	PNG("png"),
	/** PNG extension. */
	PNG_EXT("." + ImageType.PNG.getValue()),
	/** GIF type. */
	GIF("gif"),
	/** GIF extension. */
	GIF_EXT("." + ImageType.GIF.getValue()),
	/** JPG type. */
	JPG("jpg"),
	/** JPG extension. */
	JPG_EXT("." + ImageType.JPG.getValue());
	
	/** Value. */
	private String value;
	
	/**
	 * Constructor accepting value.
	 * 
	 * @param value value
	 */
	private ImageType(String value) {
		this.value = value;
	}
	
	/**
	 * Getter for value.
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}
}
