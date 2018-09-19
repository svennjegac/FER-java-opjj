package hr.fer.zemris.java.hw11.jnotepadpp.actions.file;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.AbstractJNotepadPPLocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Action which defines saving of file.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public abstract class SaveAbstractAction extends AbstractJNotepadPPLocalizableAction {
	
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
	public SaveAbstractAction(String name, KeyStroke stroke, int event, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		super(name, stroke, event, jNotepadPP, flp);
	}
	
	/**
	 * Method tries to save file in selected tab.
	 * If it is saved, it returns true, otherwise false.
	 * 
	 * @param jNotepadPP reference to notepad
	 * @param useFileChooser flag if file chooser must be used(for save as action)
	 * @param flp reference to localization provider
	 * @return <code>true</code> if tab is saved, <code>false</code> otherwise
	 */
	public static boolean saveSelectedTab(JNotepadPP jNotepadPP, boolean useFileChooser, ILocalizationProvider flp) {
		Tab selectedTab = jNotepadPP.getSelectedTab();
		
		Path openedFilePath = selectedTab.getFilePath();
		
		boolean userChoosedFile = false;
		if (openedFilePath == null || useFileChooser) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(flp.getString("saveFileSaveFile"));
			
			if (fc.showSaveDialog(jNotepadPP) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						jNotepadPP,
						flp.getString("saveFileSavingCanceled"),
						flp.getString("saveFileSavingCanceledWindowTitle"),
						JOptionPane.ERROR_MESSAGE
				);
				return false;
			}
			openedFilePath = fc.getSelectedFile().toPath();
			userChoosedFile = true;
		}
		
		if (Files.exists(openedFilePath) && userChoosedFile) {
			int answer = JOptionPane.showConfirmDialog(
					jNotepadPP,
					flp.getString("saveFileOverwritingMessage"),
					flp.getString("saveFileOverwritingWindowTitle"),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE
			);
			
			if (answer == JOptionPane.NO_OPTION) {
				JOptionPane.showMessageDialog(
						jNotepadPP,
						flp.getString("saveFileSavingCanceled"),
						flp.getString("saveFileSavingCanceledWindowTitle"),
						JOptionPane.ERROR_MESSAGE
				);
				return false;
			}
		}
		
		closeFileWhichWillBeOverwritten(jNotepadPP, selectedTab, openedFilePath);
		
		try {
			Files.write(openedFilePath, selectedTab.getTabEditor().getText().getBytes(StandardCharsets.UTF_8));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(
					jNotepadPP,
					flp.getString("saveFileErrorMessage"),
					flp.getString("saveFileErrorWindowTitle"),
					JOptionPane.ERROR_MESSAGE
			);
			return false;
		}
		
		JOptionPane.showMessageDialog(
				jNotepadPP,
				flp.getString("saveFileSuccessMessage"),
				flp.getString("saveFileSuccessWindowTitle"),
				JOptionPane.INFORMATION_MESSAGE
		);
		
		selectedTab.tabSaved(openedFilePath);
		
		return true;
	}
	
	/**
	 * If some file will be overwritten it must be closed first.
	 * 
	 * @param jNotepadPP reference to notepad
	 * @param selectedTab currently selected tab
	 * @param newFilePath new file path of this file
	 */
	private static void closeFileWhichWillBeOverwritten(JNotepadPP jNotepadPP, Tab selectedTab, Path newFilePath) {
		int tabs = jNotepadPP.getNumberOfTabs();
		
		for (int i = 0; i < tabs; i++) {
			Tab tab = jNotepadPP.getTab(i);
			
			if (!tab.equals(selectedTab)) {
				if (tab.getFilePath() != null && tab.getFilePath().toString().equals(newFilePath.toString())) {
					jNotepadPP.closeTab(i);
					return;
				}
			}
		}
	}
}
