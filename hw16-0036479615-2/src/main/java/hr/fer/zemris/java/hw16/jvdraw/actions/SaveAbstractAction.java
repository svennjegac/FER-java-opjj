package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Method defines saving as an abstraction.
 * It allows user to specify if he wants to save as an existing or a new file.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public abstract class SaveAbstractAction extends AbstractAction {

	/** UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param name action name
	 */
	SaveAbstractAction(String name) {
		putValue(Action.NAME, name);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Saver.save((String) getValue(Action.NAME), chooseNewFile());
	}
	
	/**
	 * Method defines if user wants to choose new file or save to existing
	 * file.
	 * 
	 * @return <code>true</code> if user wants to choose new file, otherwise <code>false</code>
	 */
	protected abstract boolean chooseNewFile();
}
