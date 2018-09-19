package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.io.BufferedOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.JVDrawDM;

/**
 * Class which implements saving to location, asking user for location
 * and everything connected with saving a file.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Saver {

	/** Save button text. */
	private static final String SAVE = "Save changes";
	/** Discard button text. */
	private static final String DISCARD = "Discard changes";
	/** Cancel button text. */
	private static final String CANCEL = "Cancel operation";
	
	/**
	 * Method asks user if he wants to save changes or not.
	 * 
	 * @return <code>true</code> if user successfully saved changes or discarded them,
	 * 			<code>false</code> otherwise
	 */
	public static boolean askUserToSaveChanges() {
		String[] options = new String[]{ SAVE, DISCARD, CANCEL };
		int answer = JOptionPane.showOptionDialog(
				null,
				"Save changes before closing current workspace?",
				"Warning",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				options,
				options[0]
		);
		
		switch (answer) {
		case 2:
			return false;
		case 1:
			break;
		case 0:
			if (!Saver.save(SAVE, false)) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Method saves current drawing.
	 * 
	 * @param title title of file chooser text
	 * @param chooseNewFile <code>true</code> if user wants to choose new file, otherwise <code>false</code>
	 * @return <code>true</code> if saving is successful, <code>false</code> otherwise
	 */
	public static boolean save(String title, boolean chooseNewFile) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle(title);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(JVDFile.DESCRIPTION.getValue(), JVDFile.JVD.getValue());
		fileChooser.setFileFilter(filter);
		
		Path filePath = null;
		if (chooseNewFile || JVDrawDM.getInstance().getState().getFilePath() == null) {
			if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "Saving canceled.");
				return false;
			}
			
			File file = fileChooser.getSelectedFile();
			filePath = Paths.get(file.getAbsolutePath());
		} else {
			filePath = JVDrawDM.getInstance().getState().getFilePath();
		}
		
		if (!filePath.toAbsolutePath().toString().endsWith(JVDFile.JVD_EXT.getValue())) {
			filePath = Paths.get(filePath.toAbsolutePath().toString() + JVDFile.JVD_EXT.getValue());
		}
		
		if (Files.exists(filePath) && chooseNewFile) {
			int answer = JOptionPane.showConfirmDialog(null, "File '" + filePath.toString() + "' already exist."
					+ " Do you want to overwrite it?");
			
			if (answer == JOptionPane.NO_OPTION) {
				return false;
			}
		}
		
		String data = createJVDSaveFile();
		if (!writeData(filePath, data)) {
			JOptionPane.showMessageDialog(null, "Saving failed.");
			return false;
		} else {
			JVDrawDM.getInstance().getState().setModified(false);
			JVDrawDM.getInstance().getState().setFilePath(filePath);
			JOptionPane.showMessageDialog(null, "Saving successful.");
			return true;
		}
	}
	
	/**
	 * Method creates representation of JVD file.
	 * 
	 * @return string representation of JVD file
	 */
	private static String createJVDSaveFile() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0, size = JVDrawDM.getInstance().getSize(); i < size; i++) {
			sb.append(JVDrawDM.getInstance().getObject(i).serialize() + "\r\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * Method writes data to file path.
	 * 
	 * @param filePath file path
	 * @param data data
	 * @return <code>true</code> if data is written successfully,
	 * 			<code>false</code> otherwise
	 */
	private static boolean writeData(Path filePath, String data) {
		try (BufferedOutputStream os = new BufferedOutputStream(Files.newOutputStream(filePath))) {
			byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
			os.write(dataBytes);
			os.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
