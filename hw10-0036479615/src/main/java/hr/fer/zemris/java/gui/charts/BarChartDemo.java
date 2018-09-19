package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demo program used for showing graphs from file data.
 * Program accepts one command line argument (path to file) and reads its
 * data and based on data creates model for bar chart and then draws graph of model.
 * 
 * Data by lines is:
 * 1. x axis description
 * 2. y axis description
 * 3. values for graph
 * 4. minimal y axis value
 * 5. maximal y axis value
 * 6. y axis step value
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class BarChartDemo extends JFrame {
	
	/** Serial version UID. */
	private static final long serialVersionUID = 5151434034409506855L;
	/** Lines in file. */
	private static final int LINES = 6;

	/**
	 * Constructor.
	 */
	public BarChartDemo() {
		setSize(new Dimension(650, 400));
		setLocation(250, 100);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}
	
	/**
	 * Gui initialization.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
	}
	
	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		BarChart barChartModel;
		
		try {
			barChartModel = constructBarChartFromFile(args);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		SwingUtilities.invokeLater(() -> {
			BarChartDemo chart = new BarChartDemo();
			
			JLabel fileNameLabel = new JLabel(getPath(args).toString());
			fileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			fileNameLabel.setOpaque(true);
			fileNameLabel.setBackground(Color.DARK_GRAY);
			fileNameLabel.setForeground(Color.WHITE);
			
			BarChartComponent component = new BarChartComponent(barChartModel);
			component.setOpaque(true);
			component.setBackground(Color.WHITE);
			
			chart.add(fileNameLabel, BorderLayout.PAGE_START);
			chart.add(component, BorderLayout.CENTER);
			
			chart.setVisible(true);
		});
	}
	
	/**
	 * Method constructs bar chart model from file data.
	 * 
	 * @param args array of strings - must have one string - path to file
	 * @return bar char model
	 */
	private static BarChart constructBarChartFromFile(String[] args) {
		Path filePath = getPath(args);
		
		List<String> lines = readLines(filePath);
		
		if (lines == null) {
			throw new IllegalArgumentException("Not enough lines in file.");
		}
		
		List<XYValue> values = getXYValues(lines.get(2));
		
		try {
			return new BarChart(
					values,
					lines.get(0),
					lines.get(1),
					Integer.parseInt(lines.get(3)),
					Integer.parseInt(lines.get(4)),
					Integer.parseInt(lines.get(5))
			);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Can not parse y axis values");
		}
	}
	
	/**
	 * Method reads all lines from file.
	 * 
	 * @param filePath path to file
	 * @return list of lines
	 */
	private static List<String> readLines(Path filePath) {
		try (BufferedReader r = Files.newBufferedReader(filePath)) {
			List<String> fileArgs = new ArrayList<>();
			
			for (int i = 0; i < LINES; i++) {
				String line = r.readLine();
				
				if (line == null) {
					return null;
				}
				
				fileArgs.add(line);
			}
			
			return fileArgs;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Parses string line to dedicated list of {@link XYValue}.
	 * 
	 * @param line string line
	 * @return list of {@link XYValue}
	 */
	private static List<XYValue> getXYValues(String line) {
		List<XYValue> values = new ArrayList<>();
		
		try {
			String[] bars = line.trim().split("\\s+");
			
			for (int i = 0; i < bars.length; i++) {
				String[] bar = bars[i].split(",");
				values.add(new XYValue(Integer.parseInt(bar[0]), Integer.parseInt(bar[1])));
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Can not parse XY values.");
		}
		
		return values;
	}
	
	/**
	 * Method returns path from array of strings.
	 * 
	 * @param args array of strings
	 * @return path
	 */
	private static Path getPath(String[] args) {
		if (args == null || args.length != 1) {
			throw new IllegalArgumentException("Please provide single path argument.");
		}
		
		try {
			Path filePath = Paths.get(args[0]);
			
			if (!Files.isRegularFile(filePath)) {
				throw new IllegalArgumentException("File is not regular file.");
			}
			
			return filePath;
		} catch (InvalidPathException e) {
			throw new IllegalArgumentException("Invalid path.");
		}
	}
}
