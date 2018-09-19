package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

/**
 * Class which provides various changeablep panels.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ChangeablePanelFactory {

	/**
	 * Method creates changeable panel used for {@link Line}
	 * 
	 * @param firstPointName first point name
	 * @param firstPoint first point
	 * @param secondPointName second point name
	 * @param secondPoint second point
	 * @param fgColor foreground color
	 * @return {@link ChangeablePanel}
	 */
	public static ChangeablePanel createLineChangeablePanel(String firstPointName, Point firstPoint,
			String secondPointName, Point secondPoint, Color fgColor) {
		
		JVDrawChangeablePanel cp = new JVDrawChangeablePanel();
		
		cp.setLayout(new GridLayout(5, 2));
		cp.setFirstPointChanging(firstPointName, firstPoint);
		cp.setSecondPointChanging(secondPointName, secondPoint);
		cp.setForegroundColorChanging(fgColor);
		
		return cp;
	}

	/**
	 * Method creates changeable panel used for {@link Circle}
	 * 
	 * @param firstPointName first point name
	 * @param firstPoint first point
	 * @param propertyName property name
	 * @param value property value
	 * @param fgColor foreground color
	 * @return {@link ChangeablePanel}
	 */
	public static ChangeablePanel createCircleChangeablePanel(String firstPointName, Point firstPoint,
			String propertyName, int value, Color fgColor) {
		
		JVDrawChangeablePanel cp = new JVDrawChangeablePanel();
		
		cp.setLayout(new GridLayout(4, 2));
		cp.setFirstPointChanging(firstPointName, firstPoint);
		cp.setIntegerPropertyChanging(propertyName, value);
		cp.setForegroundColorChanging(fgColor);
		
		return cp;
	}
	
	/**
	 * Method creates changeable panel used for {@link FilledCircle}
	 * 
	 * @param firstPointName first point name
	 * @param firstPoint first point
	 * @param propertyName property name
	 * @param value property value
	 * @param fgColor foreground color
	 * @param bgColor background color
	 * @return {@link ChangeablePanel}
	 */
	public static ChangeablePanel createFilledCircleChangeablePanel(String firstPointName, Point firstPoint,
			String propertyName, int value, Color fgColor, Color bgColor) {
		
		JVDrawChangeablePanel cp =
				(JVDrawChangeablePanel) createCircleChangeablePanel(firstPointName, firstPoint, propertyName, value, fgColor);
		
		cp.setLayout(new GridLayout(5, 2));
		cp.setBackgroundColorChanging(bgColor);
		return cp;
	}
}
