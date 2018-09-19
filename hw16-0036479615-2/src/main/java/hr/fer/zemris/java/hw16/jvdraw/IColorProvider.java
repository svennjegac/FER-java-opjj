package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Implemented by objects which can provide colors.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface IColorProvider {

	/**
	 * Getter for current color.
	 * 
	 * @return current color
	 */
	public Color getCurrentColor();
	
	/**
	 * Adds color change listener.
	 * 
	 * @param l listener
	 */
	public void addColorChangeListener(ColorChangeListener l);
	
	/**
	 * Removes color change listener
	 * 
	 * @param l listener
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
