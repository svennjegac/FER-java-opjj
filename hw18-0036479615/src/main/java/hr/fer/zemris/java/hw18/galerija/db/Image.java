package hr.fer.zemris.java.hw18.galerija.db;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class representing an image.
 * It stores information about image such as image name, image location,
 * image description, image thumb nail location...
 * It does not store actual image. It just describes every image.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Image {
	
	/** Location of images folder. */
	private static String IMAGES_LOCATION;
	/** Location of thumb nails folder. */
	private static String THUMBS_LOCATION;
	
	/** Thumb nail width. */
	public static final int THUMB_WIDTH = 150;
	/** Thumb nail height. */
	public static final int THUMB_HEIGHT = 150;
	
	/** Image name. */
	private String imageName;
	/** Image description. */
	private String imageDescription;
	
	/**
	 * Simple image constructor.
	 * 
	 * @param imageName image name
	 * @param imageDescription image description
	 */
	public Image(String imageName, String imageDescription) {
		this.imageName = imageName;
		this.imageDescription = imageDescription;
	}
	
	/**
	 * Getter for image name.
	 * 
	 * @return image name
	 */
	public String getImageName() {
		return imageName;
	}
	
	/**
	 * Getter for image description.
	 * 
	 * @return image description
	 */
	public String getImageDescription() {
		return imageDescription;
	}
	
	/**
	 * Getter for image path.
	 * 
	 * @return image path
	 */
	public Path getImagePath() {
		return Paths.get(IMAGES_LOCATION + imageName);
	}
	
	/**
	 * Getter for image thumb nail path.
	 * 
	 * @return image thumb nail path
	 */
	public Path getThumbPath() {
		return Paths.get(THUMBS_LOCATION + imageName);
	}
	
	/**
	 * Setter for images location.
	 * 
	 * @param iMAGES_LOCATION images location
	 */
	public static void setIMAGES_LOCATION(String iMAGES_LOCATION) {
		IMAGES_LOCATION = iMAGES_LOCATION;
	}
	
	/**
	 * Setter for thumbs location.
	 * 
	 * @param tHUMBS_LOCATION thumbs location
	 */
	public static void setTHUMBS_LOCATION(String tHUMBS_LOCATION) {
		THUMBS_LOCATION = tHUMBS_LOCATION;
	}
}