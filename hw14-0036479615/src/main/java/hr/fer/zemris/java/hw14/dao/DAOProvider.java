package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.dao.sql.SQLDAO;

/**
 * Singleton which decides which data layer manager should be used while working with application.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class DAOProvider {

	/** DAO. */
	private static DAO dao = new SQLDAO();
	
	/**
	 * Getter for DAO.
	 * 
	 * @return DAO
	 */
	public static DAO getDao() {
		return dao;
	}
	
}