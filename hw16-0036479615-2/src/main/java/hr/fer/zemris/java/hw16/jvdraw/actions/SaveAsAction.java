package hr.fer.zemris.java.hw16.jvdraw.actions;

/**
 * Action asks user to save file, user must provide file location
 * and name.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SaveAsAction extends SaveAbstractAction {

	/** UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param name action name
	 */
	public SaveAsAction(String name) {
		super(name);
	}
	
	@Override
	protected boolean chooseNewFile() {
		return true;
	}
}
