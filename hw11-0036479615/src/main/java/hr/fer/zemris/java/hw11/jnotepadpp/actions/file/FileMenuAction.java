package hr.fer.zemris.java.hw11.jnotepadpp.actions.file;

import java.awt.event.ActionEvent;


import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.AbstractJNotepadPPLocalizableAction;

/**
 * Action for clicking a file menu.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class FileMenuAction extends AbstractJNotepadPPLocalizableAction {

	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor which defines key for action name.
	 * 
	 * @param name key for localization provider
	 * @param jNotepadPP reference to notepad
	 * @param flp reference to localization provider
	 */
	public FileMenuAction(String name, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		super(name, jNotepadPP, flp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
