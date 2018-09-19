package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Point;

/**
 * Interface which must provide information about properties which
 * could be changed.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface ChangeablePanel {

	/**
	 * Returns first point info.
	 * 
	 * @return first point info
	 */
	public Point getFirstPoint();
	
	/**
	 * Return second point info.
	 * 
	 * @return second point info
	 */
	public Point getSecondPoint();
	
	/**
	 * Returns radius.
	 * 
	 * @return radius
	 */
	public int getRadius();
	
	/**
	 * Returns foreground color.
	 * 
	 * @return foreground color
	 */
	public Color getFGColor();
	
	/**
	 * Returns background color.
	 * 
	 * @return background color
	 */
	public Color getBGColor();
	
	/**
	 * Method validates panel data.
	 * 
	 * @return <code>true</code> if data is valid, <code>false</code> otherwise
	 */
	public boolean validatePanelData();
}
