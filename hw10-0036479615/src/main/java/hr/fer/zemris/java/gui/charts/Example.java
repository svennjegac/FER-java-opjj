package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Example for {@link BarChartComponent} class.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Example extends JFrame {

	/** Serial version UID. */
	private static final long serialVersionUID = -8063148337272564001L;

	/**
	 * Constructor.
	 */
	public Example() {
		setSize(new Dimension(600, 600));
		setLocation(500, 50);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}
	
	/**
	 * GUI initialization.
	 */
	private void initGUI() {
		BarChart model = new BarChart(
				Arrays.asList(new XYValue(1,-10), new XYValue(2,8), new XYValue(3,18), new XYValue(7,28), new XYValue(6,-18)),
				"Number of people in the car",
				"Frequency",
				-10,// y-os kreće od 0
				22,// y-os ide do 22
				7
		);
		
		Container cp = getContentPane();
		cp.setBackground(Color.RED);
		

		BarChartComponent comp = new BarChartComponent(model);
		comp.setOpaque(true);
		comp.setBackground(Color.WHITE);
		comp.setForeground(Color.BLACK);
		comp.setBorder(BorderFactory.createMatteBorder(10, 20, 30, 40, Color.GREEN));
		

		cp.add(comp);
	}
	
	/**
	 * Metod which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Example().setVisible(true);
		});
	}

}
