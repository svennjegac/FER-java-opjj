package hr.fer.zemris.java.hw11.jnotepadpp.actions.tools;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Action invoked when user wants to invert case of selected text.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class InvertCaseAction extends ChangeCaseAction {

	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor which defines key for action name.
	 * 
	 * @param name key for localization provider
	 * @param jNotepadPP reference to notepad
	 * @param flp reference to localization provider
	 */
	public InvertCaseAction(String name, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		super(name, jNotepadPP, flp);
	}

	@Override
	String modifyCase(String text) {
		return invertCase(text);
	}
	
	/**
	 * Method inverts case of provided text.
	 * 
	 * @param text provided text
	 * @return inverted text
	 */
	private String invertCase(String text) {
		char[] data = text.toCharArray();
		
		for (int i = 0; i < data.length; i++) {
			data[i] = Character.isUpperCase(data[i]) ? Character.toLowerCase(data[i]) : Character.toUpperCase(data[i]);
		}
		
		return new String(data);
	}
}
