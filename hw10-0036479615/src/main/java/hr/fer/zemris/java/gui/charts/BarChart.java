package hr.fer.zemris.java.gui.charts;

import java.util.Arrays;
import java.util.List;

/**
 * Class which represents a model for {@link BarChartComponent}.
 * It defines graph by values, axis descriptions and minimum and maximum
 * axis length and step.
 * 
 * Graph is modeled with this class and drawn by {@link BarChartComponent}.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class BarChart {

	/** Graph values. */
	private List<XYValue> values;
	/** Description of x axis. */
	private String xDescription;
	/** Description of y axis. */
	private String yDescription;
	/** Minimal value on y axis. */
	private int minY;
	/** Maximal value on y axis. */
	private int maxY;
	/** Step on y axis. */
	private int stepY;
	
	/**
	 * Constructor.
	 * 
	 * @param values values which will be drawn
	 * @param xDescription description of x axis
	 * @param yDescription description of y axis
	 * @param minY minimal value on y axis
	 * @param maxY maximal value on y axis
	 * @param stepY step on y axis
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription, int minY, int maxY, int stepY) {
		if (xDescription == null || yDescription == null) {
			throw new IllegalArgumentException("Axis descriptions must not be null.");
		}
		
		validateValues(values);
		validateYAxis(minY, maxY, stepY);
		
		this.values = Arrays.asList(values.toArray(new XYValue[values.size()]));
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minY = minY;
		this.maxY = maxY;
		this.stepY = stepY;
	}
	
	/**
	 * Getter for values.
	 * 
	 * @return values
	 */
	public List<XYValue> getValues() {
		return values;
	}
	
	/**
	 * Getter for y axis description.
	 * 
	 * @return y axis description
	 */
	public String getyDescription() {
		return yDescription;
	}
	
	/**
	 * Getter for x axis description.
	 * 
	 * @return x axis description
	 */
	public String getxDescription() {
		return xDescription;
	}
	
	/**
	 * Getter for minimal y on y axis.
	 * 
	 * @return minimal y on y axis
	 */
	public int getMinY() {
		return minY;
	}
	
	/**
	 * Getter for maximal y on y axis.
	 * 
	 * @return maximal y on y axis
	 */
	public int getMaxY() {
		return maxY;
	}
	
	/**
	 * Getter for step on y axis.
	 * 
	 * @return step on y axis
	 */
	public int getStepY() {
		return stepY;
	}
	
	/**
	 * Method validates if values exists.
	 * Also it validates if there is more than one value with same x axis coordinate.
	 * 
	 * @param values values for graph
	 */
	private void validateValues(List<XYValue> values) {
		if (values == null) {
			throw new IllegalArgumentException("Values can not be null.");
		}
		
		if (values.isEmpty()) {
			throw new IllegalArgumentException("Values can not be empty.");
		}
		
		for (int i = 0, size = values.size(); i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				if (values.get(i).getX() == values.get(j).getX()) {
					throw new IllegalArgumentException("Values can not have more than one element with same x coordinate; x: " + values.get(i).getX());
				}
			}
		}
	}
	
	/**
	 * Method validates parameters for y axis.
	 * 
	 * @param minY minimal y on y axis
	 * @param maxY maximal y on y axis
	 * @param stepY step on y axis
	 */
	private void validateYAxis(int minY, int maxY, int stepY) {
		if (stepY <= 0) {
			throw new IllegalArgumentException("Step must be higer than 0; was: " + stepY);
		}
		
		if (minY >= maxY) {
			throw new IllegalArgumentException("Max y must be higher than min y; were: maxY: " + maxY + ", minY: " + minY);
		}
	}
}
