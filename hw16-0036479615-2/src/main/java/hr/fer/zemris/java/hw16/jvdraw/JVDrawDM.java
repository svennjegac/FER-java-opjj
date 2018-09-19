package hr.fer.zemris.java.hw16.jvdraw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.geometry.Changeable;
import hr.fer.zemris.java.hw16.jvdraw.geometry.ChangeableListener;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * Implementation of a {@link DrawingModel} used for paint.
 * As for paint only one model can be used this class is a singleton.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class JVDrawDM implements DrawingModel, ChangeableListener {

	/** Reference to drawing model. */
	private static DrawingModel drawingModel;
	/** Listeners. */
	private List<DrawingModelListener> listeners = new ArrayList<>();
	/** Stored {@link GeometricalObject} */
	private List<GeometricalObject> geometricalObjects = new ArrayList<>();
	/** State of drawing model. */
	private State state = new State();
	
	/**
	 * Constructor.
	 */
	private JVDrawDM() {
	}
	
	/**
	 * Returns drawing model instance.
	 * If it is instantiated it returns current instance.
	 * If it is not instantiate it creates instance and returns it.
	 * 
	 * @return drawing model instance
	 */
	public static DrawingModel getInstance() {
		if (drawingModel == null) {
			drawingModel = new JVDrawDM();
		}
		
		return drawingModel;
	}
	
	@Override
	public int getSize() {
		return geometricalObjects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return geometricalObjects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		int position = getSize();
		geometricalObjects.add(position, object);
		object.addChangeableListener(this);
		
		fireChangeEvent(listener -> {
			listener.objectsAdded(this, position, position);
		});
		
		state.setModified(true);
	}
	
	@Override
	public void add(Collection<GeometricalObject> objects) {
		if (objects.isEmpty()) {
			return;
		}
		
		int size = getSize();
		geometricalObjects.addAll(objects);
		geometricalObjects.forEach(object -> {
			object.addChangeableListener(this);
		});
	
		fireChangeEvent(listener -> {
			listener.objectsAdded(this, size, size + objects.size() - 1);
		});
		
		state.setModified(true);
	}

	@Override
	public void remove(GeometricalObject object) {
		int position = geometricalObjects.indexOf(object);
		geometricalObjects.remove(object);
		object.removeChangeableListener(this);
		
		fireChangeEvent(listener -> {
			listener.objectsRemoved(this, position, position);
		});
		
		state.setModified(true);
	}
	
	@Override
	public void clear() {
		int size = getSize();
		if (size == 0) {
			return;
		}
		
		geometricalObjects.forEach(object -> {
			object.removeChangeableListener(this);
		});
		geometricalObjects = new ArrayList<>();
		
		fireChangeEvent(listener -> {
			listener.objectsRemoved(this, 0, size - 1);
		});
		
		state.setModified(true);
	}

	@Override
	public State getState() {
		return state;
	}
	
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}
	
	@Override
	public void changeableObjectChanged(Changeable source) {
		int position = geometricalObjects.indexOf(source);
		
		fireChangeEvent(listener -> {
			listener.objectsChanged(this, position, position);
		});
		
		state.setModified(true);
	}
	
	/**
	 * Interface used to invoke appropriate method on
	 * {@link DrawingModelListener} when informing about changes.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private interface ListenerInvoker {
		/**
		 * Method invokes listeners method.
		 * 
		 * @param listener {@link DrawingModelListener}
		 */
		public void invoke(DrawingModelListener listener);
	}
	
	/**
	 * Method runs through every listener on model and invokes
	 * its appropriate method.
	 * 
	 * @param invoker {@link ListenerInvoker}
	 */
	private void fireChangeEvent(ListenerInvoker invoker) {
		List<DrawingModelListener> listenersCopy = new ArrayList<>(listeners);
		listenersCopy.forEach(listener -> {
			invoker.invoke(listener);
		});
	}
}