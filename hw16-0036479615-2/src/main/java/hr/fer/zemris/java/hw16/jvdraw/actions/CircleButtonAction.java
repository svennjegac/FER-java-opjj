package hr.fer.zemris.java.hw16.jvdraw.actions;

import hr.fer.zemris.java.hw16.jvdraw.ICanvasObjectsProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GOProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * Sets circle for current object drawing in {@link ICanvasObjectsProvider}.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class CircleButtonAction extends GeometricalObjectAction {

	/** UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param coProvider reference to {@link ICanvasObjectsProvider}
	 * @param name name of action
	 */
	public CircleButtonAction(ICanvasObjectsProvider coProvider, String name) {
		super(coProvider, name);
	}
	
	@Override
	protected GeometricalObject getObjectCreator() {
		return GOProvider.getCircle();
	}
}
