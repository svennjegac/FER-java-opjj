package hr.fer.zemris.java.hw16.jvdraw;

import javax.swing.AbstractListModel;
import javax.swing.JList;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * This class is model for {@link JList}.
 * It stores {@link GeometricalObject} which can be then shown on list.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/** UID. */
	private static final long serialVersionUID = 1L;
	/** Reference to drawing model. */
	private DrawingModel drawingModel;
	
	/**
	 * Constructor accepting a drawing model.
	 * 
	 * @param drawingModel drawing model
	 */
	public DrawingObjectListModel(DrawingModel drawingModel) {
		this.drawingModel = drawingModel;
		drawingModel.addDrawingModelListener(this);
	}
	
	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return drawingModel.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}
}
