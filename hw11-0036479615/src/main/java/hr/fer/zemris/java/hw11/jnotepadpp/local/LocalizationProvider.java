package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Localization provider which knows how to fetch translations based on key.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/** Singleton instance. */
	private static final LocalizationProvider INSTANCE = new LocalizationProvider();
	/** Resource bundle. */
	private ResourceBundle bundle;
	/** Current locale. */
	private Locale locale;
	
	/** Croatian shortcut. */
	public static final String CROATIAN = "hr";
	/** English shortcut. */
	public static final String ENGLISH = "en";
	/** German shortcut. */
	public static final String GERMAN = "de";
	
	/**
	 * Default constructuor.
	 */
	public LocalizationProvider() {
	}
	
	/**
	 * Getter for singleton instance.
	 * 
	 * @return singleton instance
	 */
	public static LocalizationProvider getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Sets current language localization.
	 * 
	 * @param language current language
	 */
	public void setLanguage(String language) {
		locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.translations", locale);
		fire();
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
	
	/**
	 * Getter for current locale.
	 * 
	 * @return current locale
	 */
	public Locale getLocale() {
		return locale;
	}
}
