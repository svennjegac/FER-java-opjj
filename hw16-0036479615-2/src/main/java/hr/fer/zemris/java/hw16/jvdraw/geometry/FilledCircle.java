package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;


/**
 * Class represents a circle whose area is filled with color.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class FilledCircle extends Circle {

	/** Name of list rendering. */
	private static final String OBJECT_NAME = "Filled circle";
	/** Serialization name. */
	public static final String SERIAL_NAME = "FCIRCLE";
	
	/** Background color. */
	private Color bgColor;
	
	/**
	 * Constructor.
	 */
	public FilledCircle() {
	}
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param center center of circle
	 * @param pointOnBorder point on circle border
	 * @param fgColor foreground color
	 * @param bgColor background color
	 * @param name name of instance
	 */
	private FilledCircle(Point center, Point pointOnBorder, Color fgColor, Color bgColor, String name) {
		super(center, pointOnBorder, fgColor, name);
		this.bgColor = bgColor;
	}

	@Override
	public void drawComponent(Graphics2D g2d) {	
		g2d.setColor(bgColor);
		g2d.fillOval(center.x - radius, center.y - radius, radius * 2, radius * 2);
		
		super.drawComponent(g2d);
	}

	@Override
	public GeometricalObject createTemporaryInstance(Point first, Point second, Color fgColor, Color bgColor) {
		return new FilledCircle(first, second, fgColor, bgColor, OBJECT_NAME + getCounter());
	}
	
	@Override
	public ChangeablePanel createChangeablePanel() {
		return ChangeablePanelFactory.createFilledCircleChangeablePanel("Center", center, "Radius", radius, fgColor, bgColor);
	}

	@Override
	protected void updateData(ChangeablePanel changeablePanel) {
		super.updateData(changeablePanel);
		bgColor = changeablePanel.getBGColor();
	}
	
	@Override
	public String serialize() {
		StringBuilder sb = new StringBuilder();
		sb.append(SERIAL_NAME + " ");
		sb.append(getCenterRadiusFGColorNotation());
		sb.append(" " + getColorNotation(bgColor));
		return sb.toString();
	}
}