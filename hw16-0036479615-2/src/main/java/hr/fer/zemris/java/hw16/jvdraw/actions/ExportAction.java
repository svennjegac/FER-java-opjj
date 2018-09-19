package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.JVDrawDM;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * Called when user wants to export drawing.
 * Drawing can be exported as a PNG, JPG or GIF file.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ExportAction extends AbstractAction {

	/** UID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param name name of action
	 */
	public ExportAction(String name) {
		putValue(Action.NAME, name);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle((String) getValue(Action.NAME));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileNameExtensionFilter("Images",
				ImageType.JPG.getValue(), ImageType.PNG.getValue(), ImageType.GIF.getValue()));
		
		if (fileChooser.showDialog(null, "Export image") != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		Path filePath = setAppropriateExtension(Paths.get(fileChooser.getSelectedFile().getAbsolutePath()));
		if (filePath == null) {
			JOptionPane.showMessageDialog(null, "Exporting canceled.");
			return;
		}
		
		if (Files.exists(filePath)) {
			if (JOptionPane.showConfirmDialog(null, "File '" + filePath.toString() + "'"
					+ "already exist. Do you want to overwrite it?") != JOptionPane.YES_OPTION) {
				return;
			}
		}
		
		List<GeometricalObject> objects = getGeometricalObjects();
		BufferedImage bim = createImage(objects);
		
		try {
			ImageIO.write(
					bim,
					filePath.toString().substring(filePath.toString().lastIndexOf(".") + 1),
					Files.newOutputStream(filePath));
			JOptionPane.showMessageDialog(null, "Exporting successful.");
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Exporting failed.");
		}
	}

	/**
	 * Sets appropriate file path for exporting taking in mid allowed extensions.
	 * 
	 * @param path file path
	 * @return corrected file path
	 */
	private Path setAppropriateExtension(Path path) {
		String ext = "";
		if (path.toString().lastIndexOf(".") != -1) {
			ext = path.toString().substring(path.toString().lastIndexOf("."));
		}
		
		if (!ext.equals(ImageType.PNG_EXT.getValue())
				&& !ext.equals(ImageType.GIF_EXT.getValue())
				&& !ext.equals(ImageType.JPG_EXT.getValue())) {
			
			ext = askUserForExtension();
		}
		
		return ext == null ? null : setExstension(path, ext);
	}

	/**
	 * Asks user to provide wanted extension.
	 * 
	 * @return extension
	 */
	private String askUserForExtension() {
		String[] options = new String[]{
				ImageType.PNG_EXT.getValue(),
				ImageType.JPG_EXT.getValue(),
				ImageType.GIF_EXT.getValue()
		};
		
		return (String) JOptionPane.showInputDialog(
				null, "Choose extension", "Extension", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}

	/**
	 * Adds desired extension to path if it is not already set.
	 * 
	 * @param path file path
	 * @param ext extension
	 * @return file path
	 */
	private Path setExstension(Path path, String ext) {
		if (!path.toString().endsWith(ext)) {
			return Paths.get(path.toString() + ext);
		}
		
		return path;
	}

	/**
	 * Returns list of currently stored geometrical objects in model.
	 * 
	 * @return list of currently stored geometrical objects in model
	 */
	private List<GeometricalObject> getGeometricalObjects() {
		List<GeometricalObject> objects = new ArrayList<>();
		
		for (int i = 0, size = JVDrawDM.getInstance().getSize(); i < size; i++) {
			objects.add(JVDrawDM.getInstance().getObject(i));
		}
		
		return objects;
	}
	
	/**
	 * Creates image representing current drawing.
	 * 
	 * @param objects {@link GeometricalObject}
	 * @return image
	 */
	private BufferedImage createImage(List<GeometricalObject> objects) {
		Point upperLeftPoint = findUpperLeftPoint(objects);
		Point lowestRightPoint = findLowestRightPoint(objects);
		
		int width = lowestRightPoint.x - upperLeftPoint.x + 1;
		int height = lowestRightPoint.y - upperLeftPoint.y + 1;
		
		BufferedImage bim = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();
		
		//color canvas background
		g2d.setColor(JDrawingCanvas.BACKGROUND_COLOR);
		g2d.fillRect(0, 0, width, height);
		
		//draw geometrical objects
		g2d.translate(-upperLeftPoint.x, -upperLeftPoint.y);
		objects.forEach(object -> {
			object.drawComponent(g2d);
		});
		
		g2d.dispose();
		
		return bim;
	}
	
	/**
	 * Finds upper left point of current drawing.
	 * 
	 * @param objects {@link GeometricalObject}
	 * @return upper left point
	 */
	private Point findUpperLeftPoint(List<GeometricalObject> objects) {
		int x = Integer.MAX_VALUE;
		int y = Integer.MAX_VALUE;
		
		for (GeometricalObject object : objects) {
			Point uppLeft = object.getUpperLeftPoint();
			
			if (uppLeft.x < x) {
				x = uppLeft.x;
			}
			
			if (uppLeft.y < y) {
				y = uppLeft.y;
			}
		}
		
		return new Point(x, y);
	}
	
	/**
	 * Finds lowest right point of current drawing.
	 * 
	 * @param objects {@link GeometricalObject}
	 * @return lowest right point
	 */
	private Point findLowestRightPoint(List<GeometricalObject> objects) {
		int x = Integer.MIN_VALUE;
		int y = Integer.MIN_VALUE;
		
		for (GeometricalObject object : objects) {
			Point lowRight = object.getLowestRightPoint();
			
			if (lowRight.x > x) {
				x = lowRight.x;
			}
			
			if (lowRight.y > y) {
				y = lowRight.y;
			}
		}
		
		return new Point(x, y);
	}
}
