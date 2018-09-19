package hr.fer.zemris.java.hw11.jnotepadpp.actions.tools;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Action invoked when user wants to sort lines in descending order.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class DescendingSortAction extends AbstractSortAction {

	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor which defines key for action name.
	 * 
	 * @param name key for localization provider
	 * @param jNotepadPP reference to notepad
	 * @param flp reference to localization provider
	 */
	public DescendingSortAction(String name, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		super(name, jNotepadPP, flp);
	}
	
	@Override
	boolean isAscending() {
		return false;
	}
}
