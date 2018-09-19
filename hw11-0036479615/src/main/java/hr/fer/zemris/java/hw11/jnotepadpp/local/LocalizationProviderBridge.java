package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Bridge implementation provider which registers listeners to itself and then connects
 * to real localization provider. It knows how to translate keys by asking real provider and
 * it can deregister itself from real provider.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/** Real localization provider. */
	ILocalizationProvider lp;
	/** Status if it is connected to real provider. */
	boolean connected;
	/** Single listener which can be used to connect to real provider. */
	ILocalizationListener listener;
	
	/**
	 * Constructor of bridge which must have as argument
	 * some other localization provider which knows how to give translations for keys
	 * 
	 * @param lp localization provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider lp) {
		this.lp = lp;
		
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}
	
	/**
	 * Bridge connects to real provider.
	 */
	public void connect() {
		if (!connected) {
			connected = true;
			lp.addLocalizationListener(listener);
		}
	}
	
	/**
	 * Bridge disconnects from real provider.
	 */
	public void disconnect() {
		if (connected) {
			connected = false;
			lp.removeLocalizationListener(listener);
		}
	}
	
	@Override
	public String getString(String key) {
		return lp.getString(key);
	}

}
