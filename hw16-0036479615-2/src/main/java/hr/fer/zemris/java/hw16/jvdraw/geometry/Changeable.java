package hr.fer.zemris.java.hw16.jvdraw.geometry;

/**
 * Interface implemented by object whose properties can be changed
 * and who wants to inform listeners about changes.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface Changeable {

	/**
	 * Method accepts {@link ChangeablePanel} from which it reads properties
	 * and sets current values to ones that was read.
	 * 
	 * @param changeablePanel {@link ChangeablePanel}
	 */
	public void updateDataFromChangeablePanel(ChangeablePanel changeablePanel);
	
	/**
	 * Method creates changeable panel which is used for this object.
	 * 
	 * @return {@link ChangeablePanel}
	 */
	public ChangeablePanel createChangeablePanel();

	/**
	 * Method adds listener.
	 * 
	 * @param listener listener
	 */
	public void addChangeableListener(ChangeableListener listener);
	
	/**
	 * Method removes listener
	 * 
	 * @param listener listener
	 */
	public void removeChangeableListener(ChangeableListener listener);
}
