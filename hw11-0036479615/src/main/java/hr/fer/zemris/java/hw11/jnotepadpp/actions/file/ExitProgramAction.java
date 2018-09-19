package hr.fer.zemris.java.hw11.jnotepadpp.actions.file;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.AbstractJNotepadPPLocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class represents action which is performed when user wants to close program.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ExitProgramAction extends AbstractJNotepadPPLocalizableAction {

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
	public ExitProgramAction(String name, KeyStroke stroke, int event, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		super(name, stroke, event, jNotepadPP, flp);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (tryClosing(jNotepadPP, flp)) {
			jNotepadPP.dispose();
		}
	}
	
	/**
	 * Method tries to close program.
	 * 
	 * @param jNotepadPP notepad reference
	 * @param flp localization provider reference
	 * @return <code>true</code> if closing can be done, <code>false</code> otherwise
	 */
	public static boolean tryClosing(JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		//run through all tabs
		//if you find unsaved tab, try to save it
		//if it is saved or discarded, run again from start
		//you need to run from start because number of tabs may have changed and index out of bounds could occur
		while (true) {
			boolean foundUnsavedTab = false;
			int tabs = jNotepadPP.getNumberOfTabs();
			
			//run through all tabs
			for (int i = 0; i < tabs; i++) {
				Tab tab = jNotepadPP.getTab(i);
				jNotepadPP.setSelectedTab(tab);
				
				//if you find modified tab
				if (tab.isModified()) {
					foundUnsavedTab = true;
					String[] options = new String[]{ flp.getString("exitProgramSaveFile"), flp.getString("exitProgramDiscardChanges"), flp.getString("exitProgramCancelExiting") };
					
					String file = null;
					if (tab.getFilePath() == null) {
						file = Tab.DEFAULT_FILE_NAME;
					} else {
						file = tab.getFilePath().toString();
					}
					
					int answer = JOptionPane.showOptionDialog(
							jNotepadPP,
							flp.getString("exitProgramStartOfMessage") + file + flp.getString("exitProgramEndOfMessage"),
							flp.getString("exitProgramWindowTitle"),
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE,
							null,
							options,
							options[0]
					);
					
					//user want to save it
					if (answer == 0) {
						//saving failed, abort exiting
						if (!SaveAbstractAction.saveSelectedTab(jNotepadPP, false, flp)) {
							return false;
						}
						
						break;
					}
					
					//user discards changes
					if (answer == 1) {
						jNotepadPP.closeTab(tab);
						break;
					}
					
					//user aborts exiting
					if (answer == 2) {
						return false;
					}
				}
			}
			
			if (foundUnsavedTab) {
				continue;
			}
			
			return true;
		}
	}
}
