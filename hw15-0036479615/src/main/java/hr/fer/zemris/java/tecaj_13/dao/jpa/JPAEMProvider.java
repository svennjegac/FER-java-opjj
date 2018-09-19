package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * Provider for entity managers.
 * It starts and commits transactions.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class JPAEMProvider {

	/** Map of managers for threads. */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Gets entity manager and starts transaction.
	 * 
	 * @return entity manager
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if(ldata==null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Commits transaction and removes manager from map of managers.
	 * 
	 * @throws DAOException if operation fails
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if(ldata==null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) throw dex;
	}
	
	/**
	 * Class having reference to entity manager.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class LocalData {
		/** EM. */
		EntityManager em;
	}
	
}