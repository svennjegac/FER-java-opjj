package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;
import javax.persistence.Query;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Implementation of DAO which can be used to fetch and store data.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class JPADAOImpl implements DAO {
	
	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getBlogEntries(BlogUser user) {
		Query query = JPAEMProvider.getEntityManager().createQuery("select e from BlogEntry as e where e.creator=:creator");
		query.setParameter("creator", user);
		return query.getResultList();
	}

	@Override
	public BlogUser getBlogUserByNick(String nick) {
		Query query = JPAEMProvider.getEntityManager().createQuery("select u from BlogUser u where u.nick=:nick");
		query.setParameter("nick", nick);
		
		try {
			return (BlogUser) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public boolean nickExists(String nick) {
		return getBlogUserByNick(nick) != null;
	}
	
	@Override
	public BlogUser getBlogUserByNickAndPassword(String nick, String password) {
		BlogUser user = getBlogUserByNick(nick);
		
		if (user == null || !ChecksumCalculator.checkSum(password).equals(user.getPasswordHash())) {
			return null;
		}
		
		return user;
	}
	
	@Override
	public BlogUser addUser(BlogUser user) {
		JPAEMProvider.getEntityManager().persist(user);
		return getBlogUserByNick(user.getNick());
	}
	
	@Override
	public void addEntry(BlogEntry entry) {
		JPAEMProvider.getEntityManager().persist(entry);
		entry.getCreator().getEntries().add(entry);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getBlogUsers() {
		return JPAEMProvider.getEntityManager().createQuery("select u from BlogUser u").getResultList();
	}
	
	@Override
	public BlogEntry getBlogEntryByAuthorAndID(BlogUser author, Long id) {
		Query query = JPAEMProvider.getEntityManager().createQuery("select e from BlogEntry as e where e.id=:id and e.creator=:creator");
		query.setParameter("id", id);
		query.setParameter("creator", author);
		
		try {
			return (BlogEntry) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public void comment(BlogComment comment) {
		JPAEMProvider.getEntityManager().persist(comment);
		getBlogEntry(comment.getBlogEntry().getId()).getComments().add(comment);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogComment> getComments(BlogEntry entry) {
		Query q = JPAEMProvider.getEntityManager().createQuery("select c from BlogComment as c WHERE c.blogEntry.id=:id");
		q.setParameter("id", entry.getId());
		return q.getResultList();
	}
}