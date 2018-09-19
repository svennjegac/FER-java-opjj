package hr.fer.zemris.java.hw11.jnotepadpp.actions.tools;

import java.awt.event.ActionEvent;


import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.AbstractJNotepadPPLocalizableAction;

/**
 * Action invoked when change case menu is clicked.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ChangeCaseMenuAction extends AbstractJNotepadPPLocalizableAction {

	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor which defines key for action name.
	 * 
	 * @param name key for localization provider
	 * @param jNotepadPP reference to notepad
	 * @param flp reference to localization provider
	 */
	public ChangeCaseMenuAction(String name, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		super(name, jNotepadPP, flp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
