package hr.fer.zemris.java.hw11.jnotepadpp.actions.tools;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.AbstractJNotepadPPLocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Action which defines generic methods which are used when user wants to operate on selected lines.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public abstract class AbstractLinesOperationAction extends AbstractJNotepadPPLocalizableAction {

	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor which defines key for action name.
	 * 
	 * @param name key for localization provider
	 * @param jNotepadPP reference to notepad
	 * @param flp reference to localization provider
	 */
	public AbstractLinesOperationAction(String name, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		super(name, jNotepadPP, flp);
	}
	
	/**
	 * Method gets selected lines as argument and must do operation with them.
	 * 
	 * @param lines selected lines
	 * @return lines after operation
	 */
	abstract List<String> linesOperation(List<String> lines);
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Tab tab = jNotepadPP.getSelectedTab();
		
		JTextArea tabEditor = tab.getTabEditor();
		
		int startLineOfSelection = 0;
		int endLineOfSelection = 0;
		
		try {
			startLineOfSelection = tabEditor.getLineOfOffset(tabEditor.getSelectionStart());
			endLineOfSelection = tabEditor.getLineOfOffset(tabEditor.getSelectionEnd());
		} catch (BadLocationException ignorable) {}
		
		if (startLineOfSelection == endLineOfSelection) {
			return;
		}
		
		List<String> lines = getSelectedLines(tabEditor, startLineOfSelection, endLineOfSelection);
		
		lines = linesOperation(lines);
		
		String sortedLinesAsString = makeStringFromLines(lines);
		
		try {
			tabEditor.replaceRange(sortedLinesAsString, tabEditor.getLineStartOffset(startLineOfSelection), tabEditor.getLineEndOffset(endLineOfSelection));
		} catch (BadLocationException ignorable) {}
	}

	/**
	 * Gets selected lines as list
	 * 
	 * @param tabEditor tab editor
	 * @param startLineOfSelection index of start line
	 * @param endLineOfSelection index of end line
	 * @return list od selected lines
	 */
	private List<String> getSelectedLines(JTextArea tabEditor, int startLineOfSelection, int endLineOfSelection) {
		List<String> lines = new ArrayList<>();
		
		for (int i = startLineOfSelection; i <= endLineOfSelection; i++) {
			String line = null;
			
			try {
				int lineStart = tabEditor.getLineStartOffset(i);
				int lineEnd = tabEditor.getLineEndOffset(i);
				line = tabEditor.getText(lineStart, lineEnd - lineStart);
			} catch (BadLocationException ignorable) {}
			
			lines.add(line);
		}
		
		return lines;
	}
	
	/**
	 * Method makes string from lines.
	 * 
	 * @param lines list of lines
	 * @return string representing lines
	 */
	private String makeStringFromLines(List<String> lines) {
		StringBuilder sb = new StringBuilder();
		
		lines.forEach(line -> {
			sb.append(line);
		});
		
		return sb.toString();
	}
}
