package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.ImageIcon;

/**
 * Util class which offers methods for fetching unmodified and modified tab icons.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Util {
	
	/** Unmodified icon path. */
	private static final String UNMODIFIED_TAB = "icons/unmodified.png";
	/** Modified icon path. */
	private static final String MODIFIED_TAB = "icons/modified.png";
	/** Singleton instance. */
	private static final Util INSTANCE = new Util();
	
	/**
	 * Getter for unmodified icon.
	 * 
	 * @return unmodified icon
	 */
	public static ImageIcon getUnmodifiedIcon() {
		return getImage(UNMODIFIED_TAB);
	}
	
	/**
	 * Getter for modified icon
	 * 
	 * @return modified icon
	 */
	public static ImageIcon getModifiedIcon() {
		return getImage(MODIFIED_TAB);
	}
	
	/**
	 * Gets the icon based on location.
	 * 
	 * @param imageLocation location of icon
	 * @return icon
	 */
	private static ImageIcon getImage(String imageLocation) {
		byte[] bytes = new byte[1024];
		byte[] imageBytes = null;
			
		try (BufferedInputStream is = new BufferedInputStream(INSTANCE.getClass().getResourceAsStream(imageLocation))) {
			while (true) {
				int num = -1;
			
				num = is.read(bytes);
				
				if (num == -1) {
					break;
				}
				
				if (imageBytes == null) {
					imageBytes = Arrays.copyOfRange(bytes, 0, num);
				} else {
					byte[] both = new byte[imageBytes.length + num];
					
					for (int i = 0; i < imageBytes.length; i++) {
						both[i] = imageBytes[i];
					}
					
					for (int i = 0; i < num; i++) {
						both[i + imageBytes.length] = bytes[i];
					}
					
					imageBytes = both;
				}
			}
		} catch (IOException e) {
			return null;
		}
		
		return new ImageIcon(imageBytes);
	}
}
