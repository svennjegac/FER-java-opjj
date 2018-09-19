package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract implementation of {@link ILocalizationProvider}.
 * It defines operations with listeners but does not provide {@link #getString(String)} method
 * implementation.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/** List of listeners. */
	List<ILocalizationListener> listeners = new ArrayList<>();
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Dispatches that localization is changed to all listeners.
	 */
	public void fire() {
		listeners.forEach(listener -> {
			listener.localizationChanged();
		});
	}
}
