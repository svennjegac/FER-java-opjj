package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.jvdraw.actions.CircleButtonAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.ExportAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.FileMenuAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.FilledCircleButtonAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.LineButtonAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.OpenFileAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAsAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.Saver;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * JVDraw is an implementation of paint.
 * This class allows user to draw lines and circles, to view them
 * in list, delete them, change their colors and positions.
 * User can save current drawing, open existing drawing or export drawing as
 * an image.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class JVDraw extends JFrame {

	/** UID. */
	private static final long serialVersionUID = 1L;
	/** Program name. */
	private static final String PROGRAM_NAME = "JVDraw";
	/** Width of paint. */
	private static final int DEFAULT_WIDTH = 1000;
	/** Height of paint. */
	private static final int DEFAULT_HEIGHT = 600;
	/** Width of list showing objects drawn in paint. */
	private static final int LIST_WIDTH = 150;
	
	/**
	 * Constructor.
	 */
	public JVDraw() {
		setLocation(100, 20);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setTitle(PROGRAM_NAME);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		initGUI();
	}

	/**
	 * GUI initialization.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		IColorProvider fgColorProvider = new JColorArea(Color.RED);
		IColorProvider bgColorProvider = new JColorArea(Color.BLUE);
		
		DrawingModel drawingModel = JVDrawDM.getInstance();
		
		JDrawingCanvas drawingCanvas = new JDrawingCanvas(drawingModel);
		cp.add(drawingCanvas, BorderLayout.CENTER);
		
		JList<GeometricalObject> goList = new JVDrawList(new DrawingObjectListModel(drawingModel));
		goList.setFixedCellWidth(LIST_WIDTH);
		cp.add(new JScrollPane(goList), BorderLayout.LINE_END);
		
		ICanvasObjectsProvider coProvider = new CanvasObjectsProvider(
						drawingCanvas, drawingModel, fgColorProvider,bgColorProvider);
		
		initMenu();
		initToolBar(fgColorProvider, bgColorProvider, coProvider);
		initColorDisplay(fgColorProvider, bgColorProvider);
		initClosingOperation();
	}

	/**
	 * Menu initialization.
	 */
	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu(new FileMenuAction("File"));
		fileMenu.add(new JMenuItem(new OpenFileAction("Open")));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(new SaveAction("Save")));
		fileMenu.add(new JMenuItem(new SaveAsAction("Save As")));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(new ExportAction("Export image")));
		
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}

	/**
	 * Tool bar initialization.
	 * 
	 * @param fgColorProvider foreground color provider
	 * @param bgColorProvider background color provider
	 * @param coProvider canvas objects provider
	 */
	private void initToolBar(IColorProvider fgColorProvider,
			IColorProvider bgColorProvider, ICanvasObjectsProvider coProvider) {
		
		JToolBar toolBar = new JToolBar();
		toolBar.add((JColorArea) fgColorProvider);
		toolBar.add((JColorArea) bgColorProvider);
		
		JToggleButton lineButton = new JToggleButton(new LineButtonAction(coProvider, "Line"));
		JToggleButton circleButton = new JToggleButton(new CircleButtonAction(coProvider, "Circle"));
		JToggleButton filledCircleButton = new JToggleButton(new FilledCircleButtonAction(coProvider, "FilledCircle"));
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(lineButton);
		buttonGroup.add(circleButton);
		buttonGroup.add(filledCircleButton);
		
		toolBar.add(lineButton);
		toolBar.add(circleButton);
		toolBar.add(filledCircleButton);
		
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	/**
	 * Initialization of color display.
	 * 
	 * @param fgColorProvider foreground color provider
	 * @param bgColorProvider background color provider
	 */
	private void initColorDisplay(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		getContentPane().add(new JColorDisplay(fgColorProvider, bgColorProvider), BorderLayout.PAGE_END);
	}
	
	/**
	 * Managing program closing.
	 */
	private void initClosingOperation() {
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				if (!JVDrawDM.getInstance().getState().isModified()) {
					JVDraw.this.dispose();
					return;
				}
				
				if (!Saver.askUserToSaveChanges()) {
					return;
				}
				
				JVDraw.this.dispose();
			}
		});	
	}
	
	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}
}
