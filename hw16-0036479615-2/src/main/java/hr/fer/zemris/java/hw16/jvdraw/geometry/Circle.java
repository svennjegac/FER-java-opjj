package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Representation of a circle.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Circle extends GeometricalObject {

	/** Name for list rendering. */
	private static final String OBJECT_NAME = "Circle";
	/** Serialization name. */
	public static final String SERIAL_NAME = "CIRCLE";
	
	/** Center of a circle. */
	Point center;
	/** Circle radius. */
	int radius;
	/** Circle foreground color */
	Color fgColor;
	
	/**
	 * Constructor.
	 */
	public Circle() {
	}
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param center center of circle
	 * @param pointOnBorder point on circle border
	 * @param fgColor foreground color
	 * @param name name of instance
	 */
	public Circle(Point center, Point pointOnBorder, Color fgColor, String name) {
		this.center = center;
		this.radius = getRadius(center, pointOnBorder);
		this.fgColor = fgColor;
		this.name = name;
	}
	
	@Override
	public void drawComponent(Graphics2D g2d) {
		g2d.setColor(fgColor);
		g2d.drawOval(center.x - radius, center.y - radius, radius * 2, radius * 2);	
	}
	
	@Override
	public GeometricalObject createTemporaryInstance(Point first, Point second, Color fgColor, Color bgColor) {
		return new Circle(first, second, fgColor, OBJECT_NAME + getCounter());
	}
	
	@Override
	public Point getUpperLeftPoint() {
		return new Point(center.x - radius, center.y - radius);
	}

	@Override
	public Point getLowestRightPoint() {
		return new Point(center.x + radius, center.y + radius);
	}
	
	@Override
	public ChangeablePanel createChangeablePanel() {
		return ChangeablePanelFactory.createCircleChangeablePanel("Center", center, "Radius", radius, fgColor);
	}
	
	@Override
	protected void updateData(ChangeablePanel changeablePanel) {
		center = changeablePanel.getFirstPoint();
		radius = changeablePanel.getRadius();
		fgColor = changeablePanel.getFGColor();
	}
	
	@Override
	public String serialize() {
		StringBuilder sb = new StringBuilder();
		sb.append(SERIAL_NAME + " ");
		sb.append(getCenterRadiusFGColorNotation());
		return sb.toString();
	}
	
	/**
	 * Returns notation for center radius and color.
	 * It is used in serialization.
	 * 
	 * @return notation for center radius and color
	 */
	protected String getCenterRadiusFGColorNotation() {
		StringBuilder sb = new StringBuilder();
		sb.append(center.x + " " + center.y + " ");
		sb.append(radius + " ");
		sb.append(getColorNotation(fgColor));
		return sb.toString();
	}
	
	/**
	 * Calculates radius.
	 * 
	 * @param center center of circle
	 * @param pointOnBorder point on circle border
	 * @return radius
	 */
	public static int getRadius(Point center, Point pointOnBorder) {
		return new Double(Math.sqrt(Math.pow(center.x - pointOnBorder.x, 2) + Math.pow(center.y - pointOnBorder.y, 2))).intValue();
	}
}
