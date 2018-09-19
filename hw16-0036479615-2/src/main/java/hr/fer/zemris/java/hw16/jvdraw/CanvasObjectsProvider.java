package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GOProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * This class provides geometrical objects for {@link JDrawingCanvas}.
 * It has a reference to canvas. It listens for user clicks on canvas.
 * When user defines object with clicks, geometrical object is sent to
 * {@link DrawingModel} where it will be stored.
 * 
 * This class also track if user made just one click. If geometrical object is half
 * defined with one click, this class sends current representation of geometrical object
 * (first click, and current mouse position) to canvas for rendering.
 * 
 * This class stores and always knows which {@link GeometricalObject} is currently set
 * for drawing and which colors should be used.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class CanvasObjectsProvider implements ICanvasObjectsProvider {

	/** Reference to canvas. */
	private JDrawingCanvas canvas;
	/** Reference to drawing model. */
	private DrawingModel drawingModel;
	/** Reference to {@link IColorProvider} */
	private IColorProvider fgColorProvider;
	/** Reference to {@link IColorProvider} */
	private IColorProvider bgColorProvider;

	/** Geometrical object which should be used for rendering. */
	private GeometricalObject gObject;
	/** First click position. */
	private Point firstClick;
	/** Second click position. */
	private Point secondClick;
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param canvas reference to canvas
	 * @param drawingModel reference to drawing model
	 * @param fgColorProvider reference to foreground color provider
	 * @param bgColorProvider reference to background color provider
	 */
	public CanvasObjectsProvider(JDrawingCanvas canvas, DrawingModel drawingModel,
			IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.canvas = canvas;
		this.drawingModel = drawingModel;
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		
		initActions();
	}

	/**
	 * This method defines what must happen if user clicks on canvas.
	 * It remembers clicks position and sends information to canvas and
	 * drawing model.
	 */
	private void initActions() {
		canvas.addMouseListener(new MouseAdapter() {
			
			CurrentObjectDrawing drawingThread;
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (gObject == null) {
					return;
				}
				
				if (firstClick == null) {
					firstClick = e.getPoint();
					drawingThread = new CurrentObjectDrawing();
					drawingThread.start();
					return;
				}
				
				drawingThread.terminate();
				drawingThread = null;
				secondClick = e.getPoint();
				generateObject();
			}
		});
	}
	
	/**
	 * Method is called when user defined enough clicks to generate
	 * current geometrical object.
	 * Method creates geometrical object and adds it to drawing model.
	 */
	private void generateObject() {
		drawingModel.add(GOProvider.getCurrentInstance(gObject).createInstance(
							firstClick,
							secondClick,
							fgColorProvider.getCurrentColor(),
							bgColorProvider.getCurrentColor())
		);
		
		firstClick = null;
		secondClick = null;
	}

	@Override
	public void setGeometricalObject(GeometricalObject gObject) {
		this.gObject = gObject;
		firstClick = null;
		secondClick = null;
	}
	
	/**
	 * This class is a thread.
	 * It is created and invoked when geometrical object is half defined by user clicks.
	 * Its duty is to invoke canvas method for drawing single geometrical object on surface.
	 * It sends geometrical object to canvas which is defined by first click on canvas and
	 * by current mouse location.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private class CurrentObjectDrawing extends Thread {
		
		/** Indicator for stopping the thread. */
		private volatile boolean stop;
		
		/**
		 * Default constructor.
		 * Sets thread to daemon.
		 */
		public CurrentObjectDrawing() {
			setDaemon(true);
		}
		
		/**
		 * Method invoked when user wants to stop thread running.
		 */
		public void terminate() {
			stop = true;
		}
		
		@Override
		public void run() {
			while (!stop) {
				GeometricalObject obj = gObject;
				Point firstPosition = firstClick;
				Point currentPosition = canvas.getMousePosition();
				
				if (currentPosition == null) {
					currentPosition = firstPosition;
				}
				
				try {
					canvas.drawGeometricalObject(obj.createTemporaryInstance(
							firstPosition, currentPosition,
							fgColorProvider.getCurrentColor(), bgColorProvider.getCurrentColor())
					);
				} catch (Exception ignorable) {}
			}
		}
	}
}
