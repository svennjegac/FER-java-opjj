package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Class representing a geometrical object.
 * Object can be drawn, asked for information about its upper left and lowest right corners,
 * it can create instances of itself, it can be updated, changed and serialized due to
 * implementation of {@link Changeable} and {@link Serializable} interfaces.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public abstract class GeometricalObject implements Changeable, Serializable {
	
	/** Object name. */
	String name;
	/**
	 * Counter of number of generated objects by 
	 * {@link GeometricalObject#createInstance(Point, Point, Color, Color)} method.
	 */
	int counter = 0;
	/** Listeners on object changes. */
	ArrayList<ChangeableListener> listeners = new ArrayList<>();
	
	/**
	 * Method draws geometrical object.
	 * 
	 * @param g2d graphics
	 */
	public abstract void drawComponent(Graphics2D g2d);
	
	/**
	 * Method creates instance of object.
	 * It counts number of instantiated objects.
	 * 
	 * @param first first point click
	 * @param second second point click
	 * @param fgColor foreground color
	 * @param bgColor background color
	 * @return object
	 */
	public GeometricalObject createInstance(Point first, Point second, Color fgColor, Color bgColor) {
		GeometricalObject object = createTemporaryInstance(first, second, fgColor, bgColor);
		incrementCounter();
		return object;
	}
	
	/**
	 * Method creates instance of object.
	 * It does not count number of instantiated objects.
	 * 
	 * @param first first point click
	 * @param second second point click
	 * @param fgColor foreground color
	 * @param bgColor background color
	 * @return object
	 */
	public abstract GeometricalObject createTemporaryInstance(Point first, Point second, Color fgColor, Color bgColor);

	/**
	 * Method calculates upper left point of object.
	 * 
	 * @return upper left point
	 */
	public abstract Point getUpperLeftPoint();
	
	/**
	 * Method calculates lowest right point of object.
	 * 
	 * @return lowest right point
	 */
	public abstract Point getLowestRightPoint();
	
	@Override
	public void updateDataFromChangeablePanel(ChangeablePanel changeablePanel) {
		updateData(changeablePanel);
		fireChange();
	}
	
	/**
	 * Method informs listeners about object changing.
	 */
	protected void fireChange() {
		ArrayList<ChangeableListener> listenersCopy = new ArrayList<>(listeners);
		listenersCopy.forEach(listener -> {
			listener.changeableObjectChanged(this);
		});
	}
	
	/**
	 * Method updates object properties using {@link ChangeablePanel}.
	 * 
	 * @param changeablePanel {@link ChangeablePanel}
	 */
	protected abstract void updateData(ChangeablePanel changeablePanel);

	@Override
	public void addChangeableListener(ChangeableListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void removeChangeableListener(ChangeableListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Method increments counter.
	 */
	void incrementCounter() {
		counter++;
	}
	
	/**
	 * Getter for counter value.
	 * 
	 * @return counter value
	 */
	int getCounter() {
		return counter;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * Method returns color notation for object.
	 * 
	 * @param color color
	 * @return color notation
	 */
	protected String getColorNotation(Color color) {
		return "" + color.getRed() + " " + color.getGreen() + " " + color.getBlue();
	}
}
