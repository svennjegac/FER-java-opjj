package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * Interface implemented by class which is able to provide geometrical
 * objects.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface ICanvasObjectsProvider {

	/**
	 * Method invoked when user wants to set some new type
	 * of {@link GeometricalObject} for creating.
	 * 
	 * @param gObject new type of geometrical objects
	 */
	public void setGeometricalObject(GeometricalObject gObject);
}
