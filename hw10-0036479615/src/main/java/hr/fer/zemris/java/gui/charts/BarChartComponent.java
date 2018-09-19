package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JComponent;

/**
 * Component which on itself draws graph defined by
 * {@link BarChart}.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class BarChartComponent extends JComponent {

	/** Serial version UID. */
	private static final long serialVersionUID = -2565046630183891022L;
	/** Spacing between string, axis and several other entities. */
	private static final int SPACING = 6;
	/** Space between bar and vertical axis from both sides. */
	private static final int BAR_SPACING = 2;
	/** Arrow length. */
	private static final int ARROW_LENGTH = 3;
	/** Multiplier for crossing axis. */
	private static final int CROSS_AXIS_MULTIPLIER = 2;
	
	/** Insets. */
	private Insets componentInsets;
	/** Font metrics. */
	private FontMetrics fm;
	
	/** Reference to model. */
	private BarChart barChart;
	/** Values for graph drawing. */
	private List<XYValue> values;
	
	/** Space between x axis and bottom. */
	private double xAxisBottomOffset;
	/** Space between highest x axis and top. */
	private double xAxisTopOffset;
	/** Space between most right y axis and right side. */
	private double yAxisRightOffset;
	/** Space between y axis and left side. */
	private double yAxisLeftOffset;
	
	/** y coordinate of x axis. */
	private double xAxisYLocation;
	
	/** Step between two y axis. */
	private double stepBetweenTwoYAxis;
	/** Step between two x axis. */
	private double stepBetweenTwoXAxis;
	/** Maximal value on y axis. */
	private double yMax;
	/** Maximal string length of every string number written with y axis. */
	private double yNumbersMaxStringLength;
	/** Offset from left side to y axis numbers. */
	private double offsetYAxisNumbersPostion;
	
	/**
	 * Constructor.
	 * 
	 * @param barChart reference to model with data
	 */
	public BarChartComponent(BarChart barChart) {
		if (barChart == null) {
			throw new IllegalArgumentException("Bar chart can not be null.");
		}
		
		this.barChart = barChart;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		initDrawingConstants(g2d);
		
		paintBackground(g2d);
		
		writeYAxisDescription(g2d);
		writeXAxisDescription(g2d);
		
		writeYNumbers(g2d);
		writeXNumbers(g2d);
		
		drawYLines(g2d);
		drawXLines(g2d);
		
		drawYAxisArrow(g2d);
		drawXAxisArrow(g2d);
		
		drawBars(g2d);
	}
	
	/**
	 * Method paints background of component.
	 * 
	 * @param g2d this components graphics
	 */
	private void paintBackground(Graphics2D g2d) {
		if (isOpaque()) {
			g2d.setColor(getBackground());
			g2d.fillRect(
					componentInsets.left,
					componentInsets.top,
					this.getWidth() - componentInsets.left - componentInsets.right,
					this.getHeight() - componentInsets.top - componentInsets.bottom
			);
			g2d.setColor(getForeground());
		}
	}
	
	/**
	 * Method writes description on y axis.
	 * 
	 * @param g2d this components graphics
	 */
	private void writeYAxisDescription(Graphics2D g2d) {
		AffineTransform atDefault = g2d.getTransform();
		
		AffineTransform atDrawing = new AffineTransform();
		atDrawing.rotate(-Math.PI / 2.0);
		
		g2d.setTransform(atDrawing);
		
		double x = -(xAxisTopOffset + (double) (this.getHeight() - xAxisTopOffset - xAxisBottomOffset) / 2.0 + (double) fm.stringWidth(barChart.getyDescription()) / 2.0 + (double) fm.getHeight());
		double y = componentInsets.left + SPACING + fm.getAscent();
		
		g2d.drawString(barChart.getyDescription(), (float) x, (float) y);
		
		g2d.setTransform(atDefault);
	}
	
	/**
	 * Method writes description on x axis.
	 * 
	 * @param g2d this components graphics
	 */
	private void writeXAxisDescription(Graphics2D g2d) {
		double x = yAxisLeftOffset + (double) (this.getWidth() - yAxisLeftOffset - yAxisRightOffset) / 2.0 - (double) fm.stringWidth(barChart.getxDescription()) / 2.0;
		double y = this.getHeight() - (componentInsets.bottom + SPACING + fm.getAscent());
		
		g2d.drawString(barChart.getxDescription(), (float) x, (float) y);
	}
	
	/**
	 * Method writes numbers on y axis.
	 * 
	 * @param g2d this components graphics
	 */
	private void writeYNumbers(Graphics2D g2d) {
		double yLinePosition = xAxisYLocation;
		
		for (int i = barChart.getMinY(); i <= yMax; i += barChart.getStepY()) {
			String num = Integer.valueOf(i).toString();
			
			g2d.drawString(
					num,
					(float) (offsetYAxisNumbersPostion + (yNumbersMaxStringLength - fm.stringWidth(num))),
					(float) (yLinePosition + (double) fm.getAscent() / 2.0)
			);
		
			yLinePosition -= stepBetweenTwoXAxis;
		}
	}
	
	/**
	 * Method writes numbers on x axis.
	 * 
	 * @param g2d this components graphics
	 */
	private void writeXNumbers(Graphics2D g2d) {
		for (int i = 0, size = values.size(); i < size; i++) {
			String num = Integer.valueOf(values.get(i).getX()).toString();
			
			g2d.drawString(
					num,
					(float) (yAxisLeftOffset + i * stepBetweenTwoYAxis + stepBetweenTwoYAxis / 2.0 - (double) fm.stringWidth(num) / 2.0),
					(float) (this.getHeight() - (xAxisBottomOffset - SPACING * CROSS_AXIS_MULTIPLIER - fm.getHeight() + fm.getAscent()))
			);
		}
	}
	
	/**
	 * Method draws  vertical lines.
	 * 
	 * @param g2d this component graphics
	 */
	private void drawYLines(Graphics2D g2d) {
		int numberOfLines = values.size();
		
		for (int i = 0; i <= numberOfLines; i++) {
			g2d.drawLine(
					(int) (yAxisLeftOffset + i * stepBetweenTwoYAxis),
					(int) (this.getHeight() - (xAxisBottomOffset - SPACING)),
					(int) (yAxisLeftOffset + i * stepBetweenTwoYAxis),
					(int) (xAxisTopOffset - SPACING)
			);
		}
	}
	
	/**
	 * Method draws  horizontal lines.
	 * 
	 * @param g2d this component graphics
	 */
	private void drawXLines(Graphics2D g2d) {
		double yLinePosition = xAxisYLocation;
		
		for (int i = barChart.getMinY(); i <= yMax; i += barChart.getStepY()) {
			g2d.drawLine(
					(int) (yAxisLeftOffset - SPACING),
					(int) yLinePosition,
					(int) (this.getWidth() - yAxisRightOffset + SPACING),
					(int) yLinePosition
			);
			
			yLinePosition -= stepBetweenTwoXAxis;
		}
	}
	
	/**
	 * Method draws y axis arrow.
	 * 
	 * @param g2d this component graphics
	 */
	private void drawYAxisArrow(Graphics2D g2d) {		
		drawArrow(
				g2d,
				(int) (yAxisLeftOffset - SPACING),
				(int) (xAxisTopOffset - SPACING),
				(int) (yAxisLeftOffset + SPACING),
				(int) (xAxisTopOffset - SPACING),
				(int) (yAxisLeftOffset),
				(int) (xAxisTopOffset - SPACING - ARROW_LENGTH * SPACING)
		);
	}
	
	/**
	 * Method draws x axis arrow.
	 * 
	 * @param g2d this component graphics
	 */
	private void drawXAxisArrow(Graphics2D g2d) {		
		drawArrow(
				g2d,
				(int) (this.getWidth() - yAxisRightOffset + SPACING),
				(int) (xAxisYLocation - SPACING),
				(int) (this.getWidth() - yAxisRightOffset + SPACING),
				(int) (xAxisYLocation + SPACING),
				(int) (this.getWidth() - yAxisRightOffset + SPACING + ARROW_LENGTH * SPACING),
				(int) (xAxisYLocation)
		);
	}
	
	/**
	 * Method draws bars on graph.
	 * 
	 * @param g2d this component graphics
	 */
	private void drawBars(Graphics2D g2d) {
		g2d.setColor(Color.BLUE);
		
		for (int i = 0, size = values.size(); i < size; i++) {
			double barValue = values.get(i).getY() > yMax ? yMax : values.get(i).getY();
			double barHeight = (barValue - (double) barChart.getMinY()) * stepBetweenTwoXAxis / (double) barChart.getStepY();
			
			g2d.fillRect(
					(int) (yAxisLeftOffset + i * stepBetweenTwoYAxis + BAR_SPACING),
					(int) (this.getHeight() - xAxisBottomOffset - barHeight),
					(int) (stepBetweenTwoYAxis - BAR_SPACING * 2 + 1),
					(int) (barHeight)
			);
		}
		
		g2d.setColor(getForeground());
	}
	
	/**
	 * Method draws triangle. Used for drawing arrows.
	 * 
	 * @param g2d this component graphics
	 * @param xStart first point of triangle, x coordinate
	 * @param yStart first point of triangle, y coordinate
	 * @param xStep second point of triangle, x coordinate
	 * @param yStep second point of triangle, y coordinate
	 * @param xEnd third point of triangle, x coordinate
	 * @param yEnd third point of triangle, y coordinate
	 */
	private void drawArrow(Graphics2D g2d, int xStart, int yStart, int xStep, int yStep, int xEnd, int yEnd) {
		g2d.fillPolygon(
				new int[]{ xStart, xStep, xEnd },
				new int[] { yStart, yStep, yEnd	},
				3
		);
	}
	
	/**
	 * Method initialize constants used for drawing.
	 * 
	 * @param g2d this component graphics
	 */
	private void initDrawingConstants(Graphics2D g2d) {
		componentInsets = this.getInsets();
		fm = g2d.getFontMetrics();
		values = barChart.getValues().stream().sorted((x, y) -> Integer.compare(x.getX(), y.getX())).collect(Collectors.toList());
		
		//scale yMax to first mutual factor of bar chart values(minimum, maximum, step)
		yMax = barChart.getMaxY();
		
		if ((yMax - barChart.getMinY()) % barChart.getStepY() != 0) {
			yMax = yMax + (barChart.getStepY() - (yMax - barChart.getMinY()) % barChart.getStepY());
		}
		
		//calculate maximum string length on y axis
		int minYStringLength = fm.stringWidth(Integer.valueOf(barChart.getMinY()).toString());
		int maxYStringLength = fm.stringWidth(Double.valueOf(yMax).toString());
		yNumbersMaxStringLength = minYStringLength > maxYStringLength ? minYStringLength : maxYStringLength;
		
		//offset for writing y axis numbers
		offsetYAxisNumbersPostion = componentInsets.left + SPACING + fm.getHeight() + SPACING;
		
		//offsets of every axis to closest side
		yAxisLeftOffset = offsetYAxisNumbersPostion + yNumbersMaxStringLength + SPACING * CROSS_AXIS_MULTIPLIER;
		xAxisBottomOffset = componentInsets.bottom + SPACING + fm.getHeight() + SPACING + fm.getHeight() + SPACING * CROSS_AXIS_MULTIPLIER;
		xAxisTopOffset = componentInsets.top + SPACING + ARROW_LENGTH * SPACING + SPACING * CROSS_AXIS_MULTIPLIER;
		yAxisRightOffset = componentInsets.right + SPACING + ARROW_LENGTH * SPACING + SPACING * CROSS_AXIS_MULTIPLIER;
		
		//step between 2 y axis
		stepBetweenTwoYAxis = (double) (this.getWidth() - yAxisLeftOffset - yAxisRightOffset) / values.size();
		
		//x axis y location
		xAxisYLocation = this.getHeight() - xAxisBottomOffset;
		
		//step between 2 x axis
		int totalNumbers = ((int) yMax - barChart.getMinY()) / barChart.getStepY();
		stepBetweenTwoXAxis = (double) (xAxisYLocation - xAxisTopOffset) / (double) totalNumbers;
	}
}
