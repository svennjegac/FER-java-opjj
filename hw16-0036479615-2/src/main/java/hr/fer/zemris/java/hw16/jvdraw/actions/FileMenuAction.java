package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Action when user press file menu button.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class FileMenuAction extends AbstractAction {

	/** UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param name action name
	 */
	public FileMenuAction(String name) {
		this.putValue(Action.NAME, name);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
