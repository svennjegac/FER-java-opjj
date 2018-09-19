package hr.fer.zemris.java.hw11.jnotepadpp.actions.tools;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Action invoked when only unique lines of selectd ones must remain.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class UniqueAction extends AbstractLinesOperationAction {

	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor which defines key for action name.
	 * 
	 * @param name key for localization provider
	 * @param jNotepadPP reference to notepad
	 * @param flp reference to localization provider
	 */
	public UniqueAction(String name, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		super(name, jNotepadPP, flp);
	}
	
	@Override
	List<String> linesOperation(List<String> lines) {
		List<String> uniqueLines = new ArrayList<>();
		
		while (lines.size() > 0) {
			String firstLine = lines.get(0);
			uniqueLines.add(firstLine);
			while (lines.remove(firstLine));
		}
		
		return uniqueLines;
	}
}
