package hr.fer.zemris.java.hw11.jnotepadpp.actions.file;

import java.awt.event.ActionEvent;

import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Action saves file as new file.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SaveAsFileAction extends SaveAbstractAction {

	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor which defines key for action name, accelerator keys and
	 * key event for action.
	 * 
	 * @param name key for localization provider
	 * @param stroke accelerator key stroke
	 * @param event key event for mnemonic keys
	 * @param jNotepadPP reference to notepad
	 * @param flp reference to localization provider
	 */
	public SaveAsFileAction(String name, KeyStroke stroke, int event, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		super(name, stroke, event, jNotepadPP, flp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		saveSelectedTab(jNotepadPP, true, flp);
	}
}
