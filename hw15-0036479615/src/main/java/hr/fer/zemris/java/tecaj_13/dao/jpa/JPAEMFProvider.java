package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Provider of entity manager factory.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class JPAEMFProvider {

	/** Factory of entity managers. */
	public static EntityManagerFactory emf;
	
	/**
	 * Getter of EMF.
	 * 
	 * @return EMF
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Setter of EMF.
	 * 
	 * @param emf new EMF
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}