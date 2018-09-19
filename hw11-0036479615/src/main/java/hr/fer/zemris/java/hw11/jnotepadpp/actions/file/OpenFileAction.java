package hr.fer.zemris.java.hw11.jnotepadpp.actions.file;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
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
 * Action performed when user wants to open existing file.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class OpenFileAction extends AbstractJNotepadPPLocalizableAction {

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
	public OpenFileAction(String name, KeyStroke stroke, int event, JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		super(name, stroke, event, jNotepadPP, flp);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(flp.getString("openFileDialogTitle"));
		
		if (fc.showOpenDialog(jNotepadPP) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		File fileName = fc.getSelectedFile();
		
		Path filePath = fileName.toPath();
		
		if (!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(
					jNotepadPP,
					flp.getString("openFileNotReadableStartOfMessage") + filePath + flp.getString("openFileNotReadableEndOfMessage"),
					flp.getString("openFileNotReadableWindowTitle"),
					JOptionPane.ERROR_MESSAGE
			);
			return;
		}
		
		int index = checkIfFileIsAlreadyOpened(filePath);
		
		if (index != -1) {
			jNotepadPP.setSelectedTab(index);
			return;
		}
		
		byte[] data = null;
		
		try {
			data = Files.readAllBytes(filePath);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(
					jNotepadPP,
					flp.getString("openFileErrorWhileReadingFileStartOfMessage") + filePath + flp.getString("openFileErrorWhileReadingFileEndOfMessage"),
					flp.getString("openFileErrorWhileReadingFileWindowTitle"),
					JOptionPane.ERROR_MESSAGE
			);
			return;
		}
		
		String text = new String(data, StandardCharsets.UTF_8);
		
		Tab tab = new Tab(jNotepadPP, text, filePath, false);
		jNotepadPP.setSelectedTab(tab);
	}
	
	/**
	 * Method checks if there is already opened tab which user asked to open.
	 * 
	 * @param filePath path of file
	 * @return index of opened same file, or -1 if it is not opened
	 */
	private int checkIfFileIsAlreadyOpened(Path filePath) {
		int tabs = jNotepadPP.getNumberOfTabs();
		
		for (int i = 0; i < tabs; i++) {
			Tab tab = jNotepadPP.getTab(i);
			
			if (tab.getFilePath() != null && tab.getFilePath().toString().equals(filePath.toString())) {
				return i;
			}
		}
		
		return -1;
	}
}
