package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface which localization provider must implement.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface ILocalizationProvider {
	
	/**
	 * Adds listener to listeners.
	 * 
	 * @param l listener
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Removes listener form listeners.
	 * 
	 * @param l listener
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * Gets string translation for provided key.
	 * 
	 * @param key key for localization
	 * @return translated value for provided key
	 */
	String getString(String key);
}
