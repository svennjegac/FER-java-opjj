package hr.fer.zemris.java.hw16.jvdraw;

import java.util.Collection;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * Interface which defines drawing model.
 * Model stores {@link GeometricalObject} instances.
 * It can add and remove instances.
 * It has methods to add listeners which will be then
 * informed about changes to drawing model.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface DrawingModel {

	/**
	 * Getter for number of objects stored in model.
	 * 
	 * @return number of objects stored in model.
	 */
	public int getSize();
	
	/**
	 * Gets the object on specified position.
	 * 
	 * @param index position of object
	 * @return object on specified position
	 */
	public GeometricalObject getObject(int index);
	
	/**
	 * Method adds single object to model.
	 * 
	 * @param object object to be added
	 */
	public void add(GeometricalObject object);
	
	/**
	 * Method adds collection of objects to model.
	 * 
	 * @param objects collection of objects
	 */
	public void add(Collection<GeometricalObject> objects);
	
	/**
	 * Method removes object from model.
	 * 
	 * @param object object to be removed
	 */
	public void remove(GeometricalObject object);
	
	/**
	 * Method removes all objects from model.
	 */
	public void clear();
	
	/**
	 * Method gets the state of model.
	 * 
	 * @return {@link State}
	 */
	public State getState();
	
	/**
	 * Method adds listener on model.
	 * 
	 * @param l listener to be added
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Method removes listener from model.
	 * 
	 * @param l listener which will be removed
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
