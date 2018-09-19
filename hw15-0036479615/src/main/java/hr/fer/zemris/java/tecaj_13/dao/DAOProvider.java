package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Provider of used DAO implementation.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class DAOProvider {

	/** DAO. */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Getter for DAO.
	 * 
	 * @return DAO
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}