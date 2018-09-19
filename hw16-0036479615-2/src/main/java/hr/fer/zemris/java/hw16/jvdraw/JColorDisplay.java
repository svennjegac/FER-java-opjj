package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Simple display which shows which colors are currently selected
 * as foreground and background colors.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class JColorDisplay extends JLabel implements ColorChangeListener {

	/** UID. */
	private static final long serialVersionUID = 1L;
	/** Foreground color provider. */
	private IColorProvider fgColorProvider;
	/** Background color provider. */
	private IColorProvider bgColorProvider;
	
	/** Text for foreground color. */
	private String fgColorText = "";
	/** Text for background color. */
	private String bgColorText = "";
	
	/**
	 * Constructor which accepts providers for colors.
	 * 
	 * @param fgColorProvider foreground color provider
	 * @param bgColorProvider background color provider
	 */
	public JColorDisplay(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		
		fgColorProvider.addColorChangeListener(this);
		bgColorProvider.addColorChangeListener(this);
		
		updateText(fgColorProvider);
		updateText(bgColorProvider);
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		updateText(source);
	}
	
	/**
	 * Method updates text of display based on color changes.
	 * 
	 * @param source color provider
	 */
	private void updateText(IColorProvider source) {
		Color sourceColor = source.getCurrentColor();
		String colorText = "(" + sourceColor.getRed() + ", "
								+ sourceColor.getGreen() + ", "
								+ sourceColor.getBlue() + ")";
		
		if (source == fgColorProvider) {
			fgColorText = "Foreground color: " + colorText;
		} else if (source == bgColorProvider) {
			bgColorText = "Background color: " + colorText;
		}
		
		setText(fgColorText + ", " + bgColorText);
		setOpaque(true);
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setHorizontalAlignment(SwingConstants.CENTER);
	}
}
