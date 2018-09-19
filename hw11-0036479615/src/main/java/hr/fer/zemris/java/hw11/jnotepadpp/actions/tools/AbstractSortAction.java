package hr.fer.zemris.java.hw11.jnotepadpp.actions.tools;

import java.text.Collator;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Action which defines sorting of lines based on {@link Locale}.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public abstract class AbstractSortAction extends AbstractLinesOperationAction {

	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor which defines key for action name.
	 * 
	 * @param name key for localization provider
	 * @param jNotepadPP reference to notepad
	 * @param flp reference to localization provider
	 */
	public AbstractSortAction(String name, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		super(name, jNotepadPP, flp);
	}
	
	/**
	 * Definition if ascending or descending sort is wanted.
	 * 
	 * @return <code>true</code> if ascending sort is wanted, <code>false</code> otherwise
	 */
	abstract boolean isAscending();
	
	@Override
	List<String> linesOperation(List<String> lines) {
		return sortLines(lines, isAscending());
	}
	
	/**
	 * Method gets lines and flag which sort is wanted.
	 * It returns sorted lines.
	 * 
	 * @param lines list of lines
	 * @param ascending flag which indicates sort
	 * @return sorted lines
	 */
	private List<String> sortLines(List<String> lines, boolean ascending) {
		Collator collator = Collator.getInstance(LocalizationProvider.getInstance().getLocale());
		
		return lines
			.stream()
			.sorted(new Comparator<String>() {
						@Override
						public int compare(String o1, String o2) {
							int cmp = collator.compare(o1, o2);
							
							return ascending ? cmp : -cmp;
						}
					})
			.collect(Collectors.toList());
	}
}
