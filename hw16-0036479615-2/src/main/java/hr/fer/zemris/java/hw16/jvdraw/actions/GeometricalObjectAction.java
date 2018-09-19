package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import hr.fer.zemris.java.hw16.jvdraw.ICanvasObjectsProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * Sets {@link GeometricalObject} for current object drawing in {@link ICanvasObjectsProvider}.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public abstract class GeometricalObjectAction extends AbstractAction {

	/** UID. */
	private static final long serialVersionUID = 1L;
	/** Reference to {@link ICanvasObjectsProvider} */
	ICanvasObjectsProvider coProvider;
	
	/**
	 * Constructor accepting provider and name.
	 * 
	 * @param coProvider {@link ICanvasObjectsProvider}
	 * @param name name of action
	 */
	public GeometricalObjectAction(ICanvasObjectsProvider coProvider, String name) {
		this.putValue(Action.NAME, name);
		this.coProvider = coProvider;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		coProvider.setGeometricalObject(getObjectCreator());
	}

	/**
	 * Method must return instance of {@link GeometricalObject} representing
	 * implementation of desired object.
	 * 
	 * @return implementation of desired object
	 */
	protected abstract GeometricalObject getObjectCreator();
}
