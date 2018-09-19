package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents a blog comment.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/** ID. */
	private Long id;
	/** Comment owner. */
	private BlogEntry entry;
	/** User email. */
	private String email;
	/** Comment text. */
	private String message;
	/** Date of posting comment. */
	private Date postedon;
	
	/**
	 * Getter for ID.
	 * 
	 * @return ID
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Setter for ID.
	 * 
	 * @param id ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for owner of comment.
	 * 
	 * @return entry
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return entry;
	}
	
	/**
	 * Setter for comment owner.
	 * 
	 * @param blogEntry entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.entry = blogEntry;
	}

	/**
	 * Getter for email.
	 * 
	 * @return email
	 */
	@Column(nullable=false, length=50)
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
	 * Getter for message
	 * 
	 * @return message
	 */
	@Column(nullable=false, length=4096)
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for message.
	 * 
	 * @param message message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Date of posting a comment.
	 * 
	 * @return date of posting a comment
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedon;
	}

	/**
	 * Setter for date of posting a comment.
	 * 
	 * @param postedOn new date
	 */
	public void setPostedOn(Date postedOn) {
		this.postedon = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}