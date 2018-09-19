package hr.fer.zemris.java.hw18.galerija.db;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class representing a simple tag - image database.
 * Class can fetch images for provided tag or it can fetch
 * all tags associated with provided image.
 * Also it provides to user an option for fetching all tags
 * that exists.
 * 
 * Class is written as singleton and should be used in
 * appropriate way to that pattern.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Database {

	/** Class instance. */
	private static Database instance;
	/** Descriptor location. */
	private static String DESCRIPTOR;
	/** Map of association tag to images. */
	private Map<String, Set<Image>> tagsToImages;
	
	/**
	 * Simple constructor which initializes database.
	 */
	private Database() {
		Path descriptorPath = Paths.get(DESCRIPTOR).toAbsolutePath();
		initializeDatabase(descriptorPath);
	}

	/**
	 * Getter for DB instance.
	 * 
	 * @return DB instance
	 */
	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}
		
		return instance;
	}
	
	/**
	 * Returns list of all tags.
	 * 
	 * @return list of all tags
	 */
	public List<String> getTags() {
		return new ArrayList<String>(tagsToImages.keySet());
	}
	
	/**
	 * Returns list of all images associated with provided tag.
	 * 
	 * @param tag provided tag
	 * @return list of all images associated with provided tag
	 */
	public List<Image> getTagImages(String tag) {
		return new ArrayList<Image>(tagsToImages.get(tag));
	}
	
	/**
	 * Returns list of tags associated with provided image.
	 * 
	 * @param image provided image
	 * @return list of tags associated with provided image
	 */
	public List<String> getImageTags(Image image) {
		List<String> imageTags = new ArrayList<>();
		
		tagsToImages.entrySet().forEach(entry -> {
			Set<Image> imageSet = entry.getValue();
			
			imageSet.forEach(img -> {
				if (img.getImageName().equals(image.getImageName())) {
					imageTags.add(entry.getKey());
				}
			});
			
		});
		
		return imageTags;
	}
	
	/**
	 * Method initializes database.
	 * 
	 * @param descriptorPath path to descriptor of database
	 */
	private void initializeDatabase(Path descriptorPath) {
		tagsToImages = new HashMap<>();
		
		try (BufferedReader reader = Files.newBufferedReader(descriptorPath)) {
			String imageName;
			while ((imageName = reader.readLine()) != null) {
				String imageDescription = reader.readLine();
				String tagLine = reader.readLine();
				
				if (imageDescription == null || tagLine == null) {
					return;
				}
				
				String[] tags = tagLine.split(",");
				for (String tag : tags) {
					String tagTrim = tag.trim();
					
					if (!tagsToImages.containsKey(tagTrim)) {
						tagsToImages.put(tagTrim, new LinkedHashSet<>());
					}
					
					tagsToImages.get(tagTrim).add(new Image(imageName, imageDescription));
				}
			}
		} catch (Exception ignorable) {
			ignorable.printStackTrace();
		}
	}
	
	/**
	 * Setter for descriptor path.
	 * 
	 * @param dESCRIPTOR descriptor path
	 */
	public static void setDESCRIPTOR(String dESCRIPTOR) {
		DESCRIPTOR = dESCRIPTOR;
	}
}