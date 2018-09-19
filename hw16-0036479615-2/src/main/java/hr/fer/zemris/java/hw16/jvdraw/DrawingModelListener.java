package hr.fer.zemris.java.hw16.jvdraw;

/**
 * This interface must be implemented by every class which wants to receive
 * information about changes on {@link DrawingModel}
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface DrawingModelListener {

	/**
	 * Method invoked if objects were added to model.
	 * 
	 * @param source drawing model
	 * @param index0 index of first added object
	 * @param index1 index of last added object
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);
	
	/**
	 * Method invoked if objects were removed from model.
	 * 
	 * @param source drawing model
	 * @param index0 index of first removed object
	 * @param index1 index of last removed object
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	
	/**
	 * Method invoked if objects were changed in model.
	 * 
	 * @param source drawing model
	 * @param index0 index of first changed object
	 * @param index1 index of last changed object
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
