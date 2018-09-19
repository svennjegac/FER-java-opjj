package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Sven Njegač
 * @version 1.0
 */
@NamedQueries({
	@NamedQuery(name="BlogEntry.upit1",query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when"),
})

/**
 * Class representing a blog post.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@Entity
@Table(name="blog_entries")
@Cacheable(true)
public class BlogEntry {

	/** ID */
	private Long id;
	/** List of comments. */
	private List<BlogComment> comments = new ArrayList<>();
	/** Date of creation. */
	private Date createdAt = new Date();
	/** Date of last modification. */
	private Date lastModifiedAt = new Date();
	/** Blog title. */
	private String title;
	/** Blog text. */
	private String text;
	/** Blog creator. */
	private BlogUser creator;
	
	/**
	 * Default constructor.
	 */
	public BlogEntry() {
	}
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param title title
	 * @param text text
	 * @param creator creator
	 */
	public BlogEntry(String title, String text, BlogUser creator) {
		this.title = title;
		this.text = text;
		this.creator = creator;
	}

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
	 * Getter for comments.
	 * 
	 * @return comments
	 */
	@OneToMany(mappedBy="blogEntry", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * Setter for comments.
	 * 
	 * @param comments comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Getter for creation date.
	 * 
	 * @return creation date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Setter for creation date.
	 * 
	 * @param createdAt creation date
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Getter for last modified at.
	 * 
	 * @return last modified at
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Setter for last modified at.
	 * 
	 * @param lastModifiedAt last modified at
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Getter for title.
	 * 
	 * @return title
	 */
	@Column(nullable=false, length=200)
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
	 * Getter for text.
	 * 
	 * @return text
	 */
	@Column(nullable=false, length=4096)
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
	 * Getter for creator.
	 * 
	 * @return creator
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogUser getCreator() {
		return creator;
	}
	
	/**
	 * Setter for creator.
	 * 
	 * @param creator creator
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Title: " + title);
		sb.append("\ntext: " + text);
		sb.append("\nmod:" + lastModifiedAt);
		sb.append("\n cre: " + createdAt);
		sb.append("\ncretor:" + creator.getNick());
		
		return sb.toString();
	}
}