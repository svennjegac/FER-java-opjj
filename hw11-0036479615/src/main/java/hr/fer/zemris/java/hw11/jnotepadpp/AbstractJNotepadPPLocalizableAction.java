package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Abstract action for {@link JNotepadPP} class.
 * It defines two main constructors and registers action name
 * to {@link LocalizationProvider}.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public abstract class AbstractJNotepadPPLocalizableAction extends AbstractAction {

	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	/** Reference to notepad. */
	protected JNotepadPP jNotepadPP;
	/** Reference to localization provider. */
	protected ILocalizationProvider flp;
	
	/**
	 * Constructor which defines key for action name, accelerator keys and
	 * key event for action.
	 * 
	 * @param key key for localization provider
	 * @param stroke accelerator key stroke
	 * @param event key event for mnemonic keys
	 * @param jNotepadPP reference to notepad
	 * @param flp reference to localization provider
	 */
	public AbstractJNotepadPPLocalizableAction(String key, KeyStroke stroke, int event, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		this.putValue(Action.NAME, flp.getString(key));
		this.putValue(Action.ACCELERATOR_KEY, stroke);
		this.putValue(Action.MNEMONIC_KEY, event);
		this.jNotepadPP = jNotepadPP;
		this.flp = flp;
	
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				AbstractJNotepadPPLocalizableAction.this.putValue(Action.NAME, flp.getString(key));
			}
		});
	}
	
	/**
	 * Constructor which defines key for action name.
	 * 
	 * @param key key for localization provider
	 * @param jNotepadPP reference to notepad
	 * @param flp reference to localization provider
	 */
	public AbstractJNotepadPPLocalizableAction(String key, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		this.putValue(Action.NAME, flp.getString(key));
		this.jNotepadPP = jNotepadPP;
		this.flp = flp;
	
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				AbstractJNotepadPPLocalizableAction.this.putValue(Action.NAME, flp.getString(key));
			}
		});
	}
}
