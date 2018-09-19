package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface which offers fetching entites from somewhere what user does not need to know.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface DAO {

	/**
	 * Gets entries from user.
	 * 
	 * @param user user
	 * @return entries from user
	 */
	public List<BlogEntry> getBlogEntries(BlogUser user);
	
	/**
	 * Gets blog entry.
	 * 
	 * @param id entry id
	 * @return entry
	 */
	public BlogEntry getBlogEntry(Long id);
	
	/**
	 * Gets user by nick.
	 * 
	 * @param nick user nick
	 * @return user
	 */
	public BlogUser getBlogUserByNick(String nick);
	
	/**
	 * Checks if nick exists in DB.
	 * 
	 * @param nick user nick
	 * @return true if nick exists, otherwise false
	 */
	public boolean nickExists(String nick);
	
	/**
	 * Gets user with specified nick and password.
	 * 
	 * @param nick user nick
	 * @param password password
	 * @return user
	 */
	public BlogUser getBlogUserByNickAndPassword(String nick, String password);
	
	/**
	 * Adds user to DB.
	 * 
	 * @param user user
	 * @return added user
	 */
	public BlogUser addUser(BlogUser user);
	
	/**
	 * Adds entry to DB.
	 * 
	 * @param entry entry
	 */
	public void addEntry(BlogEntry entry);
	
	/**
	 * Gets all blog users.
	 * 
	 * @return all blog users
	 */
	public List<BlogUser> getBlogUsers();
	
	/**
	 * Gets entry by author and ID.
	 * 
	 * @param author author
	 * @param id id
	 * @return blog entry
	 */
	public BlogEntry getBlogEntryByAuthorAndID(BlogUser author, Long id);
	
	/**
	 * Adds comment to DB.
	 * 
	 * @param comment comment
	 */
	public void comment(BlogComment comment);
	
	/**
	 * Gets all comments by entry.
	 * 
	 * @param entry entry
	 * @return all comments of that entry
	 */
	public List<BlogComment> getComments(BlogEntry entry);
	
}