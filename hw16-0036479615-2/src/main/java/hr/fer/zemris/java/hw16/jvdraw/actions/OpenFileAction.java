package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.JVDrawDM;
import hr.fer.zemris.java.hw16.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GOProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.Line;

/**
 * Action invoked when user wants to open a file.
 * User can open JVD files.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class OpenFileAction extends AbstractAction {

	/** UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param name action name
	 */
	public OpenFileAction(String name) {
		putValue(Action.NAME, name);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (JVDrawDM.getInstance().getState().isModified()) {
			if (!Saver.askUserToSaveChanges()) {
				return;
			}
		}
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle((String) getValue(Action.NAME));
		FileFilter filter = new FileNameExtensionFilter(null, JVDFile.JVD.getValue());
		fileChooser.setFileFilter(filter);
		
		if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		Path filePath = Paths.get(fileChooser.getSelectedFile().getAbsolutePath());
		if (!filePath.toAbsolutePath().toString().endsWith(JVDFile.JVD_EXT.getValue())) {
			JOptionPane.showMessageDialog(null, "Can not open non JVD file.");
			return;
		}
		
		
		List<String> lines = readAllLines(filePath);
		List<GeometricalObject> gObjects = convertLinesToObjects(lines);
		
		JVDrawDM.getInstance().clear();
		JVDrawDM.getInstance().add(gObjects);
		JVDrawDM.getInstance().getState().setModified(false);
		JVDrawDM.getInstance().getState().setFilePath(filePath);
	}

	/**
	 * Method reads all lines from file.
	 * 
	 * @param filePath file
	 * @return all lines
	 */
	private List<String> readAllLines(Path filePath) {
		List<String> lines = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Files.newInputStream(filePath), StandardCharsets.UTF_8))) {
			
			while (true) {
				String line = reader.readLine();
				
				if (line == null) {
					break;
				}
				
				lines.add(line);
			}
		} catch (Exception e) {
		}
		
		return lines;
	}
	
	/**
	 * Method converts lines to their representation of {@link GeometricalObject}
	 * 
	 * @param lines lines
	 * @return list of {@link GeometricalObject}
	 */
	private List<GeometricalObject> convertLinesToObjects(List<String> lines) {
		List<GeometricalObject> gObjects = new ArrayList<>();
		GOProvider.reset();
		
		for (String line : lines) {
			String[] args = line.split(" ");

			switch (args[0]) {
			case Line.SERIAL_NAME:
				parseLine(args, gObjects);
				break;
			case Circle.SERIAL_NAME:
				parseCircle(args, gObjects);
				break;
			case FilledCircle.SERIAL_NAME:
				parseFilledCircle(args, gObjects);
				break;
			default:
				break;
			}
		}
		
		return gObjects;
	}

	/**
	 * Method parses line string and stores it in list of objects.
	 * 
	 * @param args array of string components
	 * @param gObjects objects
	 */
	private void parseLine(String[] args, List<GeometricalObject> gObjects) {
		try {
			Point first = new Point(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			Point second = new Point(Integer.parseInt(args[3]), Integer.parseInt(args[4]));
			Color color = new Color(Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
			gObjects.add(GOProvider.getLine().createInstance(first, second, color, null));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error while parsing '" + constructLine(args) + "'.");
		}
	}
	
	/**
	 * Method parses circle string and stores it in list of objects.
	 * 
	 * @param args array of string components
	 * @param gObjects objects
	 */
	private void parseCircle(String[] args, List<GeometricalObject> gObjects) {
		try {
			Point first = new Point(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			Point second = new Point(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			second.x += Integer.parseInt(args[3]);
			Color color = new Color(Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
			gObjects.add(GOProvider.getCircle().createInstance(first, second, color, null));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error while parsing '" + constructLine(args) + "'.");
		}
	}
	
	/**
	 * Method parses filled circle string and stores it in list of objects.
	 * 
	 * @param args array of string components
	 * @param gObjects objects
	 */
	private void parseFilledCircle(String[] args, List<GeometricalObject> gObjects) {
		try {
			Point first = new Point(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			Point second = new Point(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			second.x += Integer.parseInt(args[3]);
			Color fgColor = new Color(Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
			Color bgColor = new Color(Integer.parseInt(args[7]), Integer.parseInt(args[8]), Integer.parseInt(args[9]));
			gObjects.add(GOProvider.getFilledCircle().createInstance(first, second, fgColor, bgColor));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error while parsing '" + constructLine(args) + "'.");
		}
	}

	/**
	 * Method constructs line from array of string components.
	 * 
	 * @param args string components
	 * @return single string
	 */
	private String constructLine(String[] args) {
		StringBuilder sb = new StringBuilder();
		
		for (String string : args) {
			sb.append(string + " ");
		}
		
		return sb.toString();
	}
}
