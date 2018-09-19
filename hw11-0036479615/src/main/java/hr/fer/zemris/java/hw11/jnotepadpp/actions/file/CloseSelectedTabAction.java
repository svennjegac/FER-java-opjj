package hr.fer.zemris.java.hw11.jnotepadpp.actions.file;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.AbstractJNotepadPPLocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Action class which is performed when tab closing is invoked.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class CloseSelectedTabAction extends AbstractJNotepadPPLocalizableAction {

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
	public CloseSelectedTabAction(String name, KeyStroke stroke, int event, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		super(name, stroke, event, jNotepadPP, flp);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		closeSelectedTab(jNotepadPP, flp);
	}
	
	/**
	 * Method tries to close selected tab.
	 * 
	 * @param jNotepadPP reference to notepad
	 * @param flp reference to localization provider
	 * @return <code>true</code> if tab closed, <code>false</code> otherwise
	 */
	public static boolean closeSelectedTab(JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		Tab tab = jNotepadPP.getSelectedTab();
		
		if (tab == null) {
			return true;
		}
		
		if (!tab.isModified()) {
			jNotepadPP.closeTab(tab);
			return true;
		}
		
		String[] options = new String[]{flp.getString("closeSelectedTabSaveAndClose"), flp.getString("closeSelectedTabCloseWithoutSaving"), flp.getString("closeSelectedTabCancelClosing")};
		
		int answer = JOptionPane.showOptionDialog(
				jNotepadPP,
				flp.getString("closeSelectedTabStartOfMessage") + tab.getFileName() + flp.getString("closeSelectedTabEndOfMessage"),
				flp.getString("closeSelectedTabWindowTitle"),
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				options,
				options[0]
		);
		
		if (answer == 0) {
			if (SaveAbstractAction.saveSelectedTab(jNotepadPP, false, flp)) {
				jNotepadPP.closeTab(tab);
				return true;
			}
			
			return false;
		}
		
		if (answer == 1) {
			jNotepadPP.closeTab(tab);
			return true;
		}
		
		return false;
	}
}
