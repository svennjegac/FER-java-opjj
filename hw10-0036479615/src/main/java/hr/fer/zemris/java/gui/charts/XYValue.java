package hr.fer.zemris.java.gui.charts;

/**
 * Class having pairs of x and y values, representing coordinates
 * in {@link BarChart}.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class XYValue {

	/** X value. */
	private int x;
	/** Y value. */
	private int y;
	
	/**
	 * Constructor.
	 * 
	 * @param x x value
	 * @param y y value
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter for x value.
	 * 
	 * @return x value
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Getter for y value.
	 * 
	 * @return y value
	 */
	public int getY() {
		return y;
	}
}
