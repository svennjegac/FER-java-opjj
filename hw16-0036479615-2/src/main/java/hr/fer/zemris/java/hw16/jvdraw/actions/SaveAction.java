package hr.fer.zemris.java.hw16.jvdraw.actions;

/**
 * Action saves drawing as an existing file.
 * If drawing is not linked to existing file it will ask user for
 * file.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SaveAction extends SaveAbstractAction {

	/** UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param name action name
	 */
	public SaveAction(String name) {
		super(name);
	}
	
	@Override
	protected boolean chooseNewFile() {
		return false;
	}
}
