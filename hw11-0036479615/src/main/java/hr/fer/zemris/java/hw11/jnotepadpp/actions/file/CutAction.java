package hr.fer.zemris.java.hw11.jnotepadpp.actions.file;

import java.awt.event.ActionEvent;


import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.AbstractJNotepadPPLocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class represents a cut action.
 * IT cuts text and copies it to clip board.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class CutAction extends AbstractJNotepadPPLocalizableAction {

	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor which defines key for action name.
	 * 
	 * @param name key for localization provider
	 * @param jNotepadPP reference to notepad
	 * @param flp reference to localization provider
	 */
	public CutAction(String name, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		super(name, jNotepadPP, flp);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Tab tab = jNotepadPP.getSelectedTab();
		
		tab.cutFromThisTab();;
	}
}
