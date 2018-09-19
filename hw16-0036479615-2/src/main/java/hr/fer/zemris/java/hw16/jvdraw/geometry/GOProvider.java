package hr.fer.zemris.java.hw16.jvdraw.geometry;

/**
 * Class which can be used to provide {@link GeometricalObject}.
 * It can reset objects (reset their counters) used for name
 * rendering on a list.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class GOProvider {

	/** Line. */
	private static Line line;
	/** Circle. */
	private static Circle circle;
	/** Filled circle. */
	private static FilledCircle filledCircle;
	
	static {
		reset();
	}
	
	/**
	 * Getter for line.
	 * 
	 * @return line
	 */
	public static Line getLine() {
		return line;
	}
	
	/**
	 * Getter for circle.
	 * 
	 * @return circle
	 */
	public static Circle getCircle() {
		return circle;
	}
	
	/**
	 * Getter for filled circle.
	 * 
	 * @return filled circle
	 */
	public static FilledCircle getFilledCircle() {
		return filledCircle;
	}
	
	/**
	 * Method resets objects (resets their counters).
	 */
	public static void reset() {
		line = new Line();
		circle = new Circle();
		filledCircle = new FilledCircle();
	}
	
	/**
	 * Method returns current (last reseted) instance of
	 * wanted object.
	 * 
	 * @param object wanted object
	 * @return last reseted instance of object
	 */
	public static GeometricalObject getCurrentInstance(GeometricalObject object) {
		if (object instanceof Line) {
			return line;
		} else if (object instanceof FilledCircle) {
			return filledCircle;
		} else {
			return circle;
		}
	}
}
