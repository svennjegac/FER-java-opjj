package hr.fer.zemris.java.hw11.jnotepadpp.actions.file;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.AbstractJNotepadPPLocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Action invoked when document statistics are requested.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class StatisticalInfoAction extends AbstractJNotepadPPLocalizableAction {

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
	public StatisticalInfoAction(String name, KeyStroke stroke, int event, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		super(name, stroke, event, jNotepadPP, flp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Tab tab = jNotepadPP.getSelectedTab();
		
		Document document = tab.getTabEditor().getDocument();
		
		String text = null;
		try {
			text = document.getText(0, document.getLength());
		} catch (BadLocationException ignorable) {}
		
		int numberOfChars = document.getLength();
		int numberOfNonBlankChars = text.replaceAll("\\s+", "").length();
		int numberOfLines = tab.getTabEditor().getLineCount();
	
		JOptionPane.showMessageDialog(
				jNotepadPP,
				flp.getString("statisticalInfoYourDocHas") + 
				numberOfChars + 
				flp.getString("statisticalInfoCharacters") + 
				numberOfNonBlankChars + 
				flp.getString("statisticalInfoNonBlankCharacters") + numberOfLines +
				flp.getString("statisticalInfoNumberOfLines")
		);
	}
}
