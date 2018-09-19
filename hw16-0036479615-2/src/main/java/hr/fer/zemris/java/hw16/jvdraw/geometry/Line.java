package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Representation of a line as a geometrical object.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Line extends GeometricalObject {
	
	/** Name for list rendering. */
	private static final String OBJECT_NAME = "Line";
	/** Serialization name. */
	public static final String SERIAL_NAME = "LINE";
	
	/** Line start. */
	private Point start;
	/** Line end. */
	private Point end;
	/** Line color. */
	private Color color;
	
	/**
	 * Constructor.
	 */
	public Line() {
	}
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param start start of line
	 * @param end end of line
	 * @param color line color
	 * @param name line name
	 */
	private Line(Point start, Point end, Color color, String name) {
		this.start = start;
		this.end = end;
		this.color = color;
		this.name = name;
	}

	@Override
	public void drawComponent(Graphics2D g2d) {
		try {
			g2d.setColor(color);
			g2d.drawLine(start.x, start.y, end.x, end.y);
		} catch (Exception ignorable) {}
	}

	@Override
	public GeometricalObject createTemporaryInstance(Point first, Point second, Color fgColor, Color bgColor) {
		return new Line(first, second, fgColor, OBJECT_NAME + getCounter());
	}
	
	@Override
	public Point getUpperLeftPoint() {
		int x = start.x < end.x ? start.x : end.x;
		int y = start.y < end.y ? start.y : end.y;
		
		return new Point(x, y);
	}

	@Override
	public Point getLowestRightPoint() {
		int x = start.x > end.x ? start.x : end.x;
		int y = start.y > end.y ? start.y : end.y;
		
		return new Point(x, y);
	}

	@Override
	public ChangeablePanel createChangeablePanel() {
		return ChangeablePanelFactory.createLineChangeablePanel("Start", start, "End", end, color);
	}
	
	@Override
	protected void updateData(ChangeablePanel changeablePanel) {
		start = changeablePanel.getFirstPoint();
		end = changeablePanel.getSecondPoint();
		color = changeablePanel.getFGColor();	
	}
	
	@Override
	public String serialize() {
		StringBuilder sb = new StringBuilder();
		sb.append(SERIAL_NAME + " ");
		sb.append(start.x + " " + start.y + " ");
		sb.append(end.x + " " + end.y + " ");
		sb.append(getColorNotation(color));
		return sb.toString();
	}
}
