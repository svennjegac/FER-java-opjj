package hr.fer.zemris.java.tecaj_13.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used to represent user blog entries to form on internet.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class BlogEntryForm {

	/** ID. */
	private String id;
	/** Title */
	private String title;
	/** Text. */
	private String text;
	/** Map of errors. */
	private Map<String, String> errors = new HashMap<>();
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param id ID
	 * @param title title
	 * @param text text
	 */
	public BlogEntryForm(String id, String title, String text) {
		this.id = id;
		this.title = title;
		this.text = text;
	}
	
	/**
	 * Getter for id.
	 * 
	 * @return ID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Setter for ID.
	 * 
	 * @param id ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Getter for title.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Setter for title.
	 * 
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Getter for text-
	 * 
	 * @return text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Setter for text.
	 * 
	 * @param text text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Getter for errors.
	 * 
	 * @return errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}
	
	/**
	 * Creates blog entry from current parameters.
	 * 
	 * @param user creator
	 * @return blog entry
	 */
	public BlogEntry createEntry(BlogUser user) {
		return new BlogEntry(title, text, user);
	}
	
	/**
	 * Validates if fields in form are populated validly.
	 * 
	 * @return true if fields are valid, otherwise false
	 */
	public boolean validate() {
		errors.clear();
		
		if (title.length() < 1) {
			errors.put("title", "Title must have at least one character");
		}
		
		if (text.length() < 1) {
			errors.put("text", "Text must have at least one character");
		}
		
		return errors.isEmpty();
	}
}
