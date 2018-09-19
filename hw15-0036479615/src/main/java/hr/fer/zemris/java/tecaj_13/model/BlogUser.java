package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Class representing a blog user.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@Entity
@Table(name="blog_users", uniqueConstraints={
		@UniqueConstraint(columnNames={"nick"})
})
public class BlogUser {

	/** ID. */
	private long id;
	/** First name. */
	private String firstName;
	/** Last name. */
	private String lastName;
	/** Nick. */
	private String nick;
	/** EMail. */
	private String email;
	/** Password hash. */
	private String passwordHash;
	/** Entries. */
	private List<BlogEntry> entries = new ArrayList<>();
	
	/**
	 * Default constructor.
	 */
	public BlogUser() {
	}
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param firstName first name
	 * @param lastName last name
	 * @param nick nick
	 * @param email email
	 * @param passwordHash passwrod hash
	 */
	public BlogUser(String firstName, String lastName, String nick, String email, String passwordHash) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nick = nick;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	/**
	 * Getter for ID.
	 * 
	 * @return ID
	 */
	@Id @GeneratedValue
	public long getId() {
		return id;
	}
	
	/**
	 * Setter for ID.
	 * 
	 * @param id ID
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Getter for first name.
	 * 
	 * @return first name
	 */
	@Column(nullable=false, length=100)
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Setter for first name.
	 * 
	 * @param firstName first name
	 * 
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Getter for last name.
	 * 
	 * @return last name
	 */
	@Column(nullable=false, length=100)
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Setter for last name.
	 * 
	 * @param lastName last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Getter for nick
	 * 
	 * @return nick
	 */
	@Column(name="nick", nullable=false, length=100)
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
	 * Getter for email.
	 * 
	 * @return email
	 */
	@Column(nullable=false, length=100)
	public String getEmail() {
		return email;
	}
	
	/**
	 * Setter for email.
	 * 
	 * @param email email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Getter for password hashed.
	 *  
	 * @return hashed password
	 */
	@Column(nullable=false, length=100)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Setter for hashed password.
	 * 
	 * @param passwordHash hashed password
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Getter for entries.
	 * 
	 * @return entries
	 */
	@OneToMany(mappedBy="creator", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	public List<BlogEntry> getEntries() {
		return entries;
	}
	
	/**
	 * Setter for entries.
	 * 
	 * @param entries entries
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogUser other = (BlogUser) obj;
		if (id != other.id)
			return false;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "" + id + "," + nick;
	}
}
