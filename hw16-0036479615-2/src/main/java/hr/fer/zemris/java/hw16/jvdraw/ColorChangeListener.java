package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Interface which must be implemented by class which wants to receive
 * information about color changing in {@link IColorProvider}.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface ColorChangeListener {

	/**
	 * Method invoked when {@link IColorProvider} changes selected color.
	 * 
	 * @param source reference to color provider
	 * @param oldColor old color
	 * @param newColor new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
