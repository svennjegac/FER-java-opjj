package hr.fer.zemris.java.hw16.jvdraw;

import java.nio.file.Path;

/**
 * Class which defines state of object which can be modified and saved.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class State {

	/** Flag if object is modified. */
	boolean modified;
	/** Path to where object should be saved. */
	Path filePath;
	
	/**
	 * Checks modified state.
	 * 
	 * @return modified state
	 */
	public boolean isModified() {
		return modified;
	}
	
	/**
	 * Sets modified state.
	 * 
	 * @param modified state
	 */
	public void setModified(boolean modified) {
		this.modified = modified;
	}
	
	/**
	 * Gets file path.
	 * 
	 * @return file path
	 */
	public Path getFilePath() {
		return filePath;
	}
	
	/**
	 * Sets file path
	 * 
	 * @param filePath file path
	 */
	public void setFilePath(Path filePath) {
		this.filePath = filePath;
	}
}
