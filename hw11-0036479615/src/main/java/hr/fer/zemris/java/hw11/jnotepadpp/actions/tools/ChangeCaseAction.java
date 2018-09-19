package hr.fer.zemris.java.hw11.jnotepadpp.actions.tools;

import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.AbstractJNotepadPPLocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Action which defines behavior when case changing is wanted.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public abstract class ChangeCaseAction extends AbstractJNotepadPPLocalizableAction {

	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor which defines key for action name.
	 * 
	 * @param name key for localization provider
	 * @param jNotepadPP reference to notepad
	 * @param flp reference to localization provider
	 */
	public ChangeCaseAction(String name, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		super(name, jNotepadPP, flp);
	}
	
	/**
	 * Modification which must be done on text.
	 * 
	 * @param text selected text
	 * @return modified text
	 */
	abstract String modifyCase(String text);

	@Override
	public void actionPerformed(ActionEvent e) {
		Tab tab = jNotepadPP.getSelectedTab();
		
		JTextArea tabEditor = tab.getTabEditor();
		
		tabEditor.replaceSelection(modifyCase(tabEditor.getSelectedText()));
	}
}
