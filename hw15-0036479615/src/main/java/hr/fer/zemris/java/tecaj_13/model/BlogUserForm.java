package hr.fer.zemris.java.tecaj_13.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a form which user filled to register or login.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class BlogUserForm {

	/** First name. */
	private String firstName;
	/** Last name. */
	private String lastName;
	/** Nick. */
	private String nick;
	/** Email. */
	private String email;
	/** Hashed password. */
	private String passwordHash;
	/** Errors. */
	private Map<String, String> errors = new HashMap<>();
	
	/**
	 * Default constructor.
	 */
	public BlogUserForm() {
	}
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param firstName first name
	 * @param lastName last name
	 * @param nick nick
	 * @param email email
	 * @param passwordHash password hash
	 */
	public BlogUserForm(String firstName, String lastName, String nick, String email, String passwordHash) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nick = nick;
		this.email = email;
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Getter for first name.
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Getter for last name.
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Getter for email.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Getter for nick.
	 * 
	 * @return nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for nick.
	 * 
	 * @param nick nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Getter for errors.
	 * 
	 * @return errors.
	 */
	public Map<String, String> getErrors() {
		return errors;
	}
	
	/**
	 * Adds error.
	 * 
	 * @param key key of errors
	 * @param value value of errors
	 */
	public void addError(String key, String value) {
		errors.put(key, value);
	}
	
	/**
	 * Validates if form is filled validly.
	 * 
	 * @return true if form is valid, false if it is invalid
	 */
	public boolean validate() {
		errors.clear();
		
		if (email.indexOf("@") == -1 || email.indexOf("@") != email.lastIndexOf("@")) {
			errors.put("email", "Not valid number of '@' in email. 1 expected.");
		}
		
		if (nick.length() < 1) {
			errors.put("nick", "Nick must be at least one character long.");
		}
		
		return errors.isEmpty();
	}
	
	/**
	 * Creates blog user from current form fields.
	 * 
	 * @return blog user
	 */
	public BlogUser createBlogUser() {
		return new BlogUser(firstName, lastName, nick, email, passwordHash);
	}
}
