package hr.fer.zemris.java.hw14.model;

/**
 * Model representing a single poll.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Poll {

	/** Poll ID. */
	private int id;
	/** Poll title. */
	private String title;
	/** Poll message. */
	private String message;
	
	/**
	 * Constructor which accepts parameters.
	 * 
	 * @param id poll ID
	 * @param title poll title
	 * @param message poll message
	 */
	public Poll(int id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
	}
	
	/**
	 * Getter for ID.
	 * 
	 * @return poll ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter for title.
	 * 
	 * @return poll title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Getter for message.
	 * 
	 * @return poll message
	 */
	public String getMessage() {
		return message;
	}
}
