package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;

/**
 * Wrapper which has single property color and
 * its getter and setter.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ColorWrapper {

	/** Color. */
	private Color color;

	/**
	 * Color wrapper constructor.
	 * 
	 * @param color color value
	 */
	public ColorWrapper(Color color) {
		super();
		this.color = color;
	}
	
	/**
	 * Getter for color value.
	 * 
	 * @return color value
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Setter for color value.
	 * 
	 * @param color color value
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
