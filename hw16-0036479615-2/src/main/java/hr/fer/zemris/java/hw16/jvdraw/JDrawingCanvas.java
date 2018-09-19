package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * Class representing a canvas for paint.
 * This class can draw {@link GeometricalObject} on itself.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {
	
	/** UID. */
	private static final long serialVersionUID = 1L;
	/** Drawing model */
	private DrawingModel drawingModel;
	/** Canvas background color. */
	public static final Color BACKGROUND_COLOR = Color.WHITE;
	
	/**
	 * Constructor.
	 * 
	 * @param drawingModel drawing model
	 */
	public JDrawingCanvas(DrawingModel drawingModel) {
		drawingModel.addDrawingModelListener(this);
		this.drawingModel = drawingModel;
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		paintBackground((Graphics2D) g);
		
		for (int i = 0, size = drawingModel.getSize(); i < size; i++) {
			drawingModel.getObject(i).drawComponent((Graphics2D) g);
		}
	}
	
	/**
	 * Method paints canvas background.
	 * 
	 * @param g2d graphics
	 */
	private void paintBackground(Graphics2D g2d) {
		g2d.setColor(BACKGROUND_COLOR);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	/**
	 * Method draws {@link GeometricalObject} on canvas.
	 * 
	 * @param object geometrical object
	 */
	public void drawGeometricalObject(GeometricalObject object) {
		repaint();
		Graphics2D g2d = (Graphics2D) getGraphics();
		object.drawComponent(g2d);
	}
}
