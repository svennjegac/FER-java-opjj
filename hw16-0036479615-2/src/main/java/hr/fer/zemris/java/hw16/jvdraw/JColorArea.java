package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

/**
 * Class shows current color with small icon.
 * When user clicks on icon {@link JColorChooser} shows and color
 * can be changed.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class JColorArea extends JComponent implements IColorProvider, ColorChangeListener {

	/** UID. */
	private static final long serialVersionUID = 1L;
	/** Component width. */
	private static final int DEFAULT_WIDTH = 15;
	/** Component height. */
	private static final int DEFAULT_HEIGHT = 15;
	
	/** Selected color. */
	private Color selectedColor;
	/** Listeners. */
	private List<ColorChangeListener> registerdListeners = new ArrayList<>();
	
	/**
	 * Constructor accepting initial color.
	 * 
	 * @param selectedColor initial color
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		initListeners();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(selectedColor);
		g2d.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	/**
	 * Initialization of listeners.
	 */
	private void initListeners() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JColorChooser chooser = new JColorChooser(selectedColor);
				JDialog dialog = JColorChooser.createDialog(
						JColorArea.this,
						"Choose color",
						true,
						chooser,
						new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								fireChangeEvent(selectedColor, chooser.getColor());
							}
						},
						new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
							}
						}
				);
				dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		
		addColorChangeListener(this);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		if (!registerdListeners.contains(l)) {
			registerdListeners.add(l);
		}
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		registerdListeners.remove(l);
	}
	
	/**
	 * Sends information to all listeners that color changed.
	 * 
	 * @param oldColor old color
	 * @param newColor new color
	 */
	private void fireChangeEvent(Color oldColor, Color newColor) {
		List<ColorChangeListener> registerdListenersCopy = new ArrayList<>(registerdListeners);
		registerdListenersCopy.forEach(listener -> {
			listener.newColorSelected(JColorArea.this, oldColor, newColor);
		});
	}
	
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		selectedColor = newColor;
		repaint();
	}
}
