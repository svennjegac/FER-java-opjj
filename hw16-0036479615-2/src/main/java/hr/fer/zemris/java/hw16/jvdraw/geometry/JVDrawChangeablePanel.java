package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Implementation of {@link ChangeablePanel}.
 * It is used to provide user information about object properties which can be changed, to store
 * that info and return it to user.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class JVDrawChangeablePanel extends JPanel implements ChangeablePanel {
	
	/** UID. */
	private static final long serialVersionUID = 1L;
	/** X axis name. */
	private static final String X_COORD = "X";
	/** Y axis name. */
	private static final String Y_COORD = "Y";
	
	/** Foreground color text. */
	private static final String FG_COLOR_NAME = "Foreground color: ";
	/** Background color text. */
	private static final String BG_COLOR_NAME = "Background color: ";
	
	/** First point X text field. */
	private JTextField firstPointXTF;
	/** First point Y text field. */
	private JTextField firstPointYTF;
	/** Second point X text field. */
	private JTextField secondPointXTF;
	/** Second point Y text field. */
	private JTextField secondPointYTF;
	/** Integer property text field. */
	private JTextField integerProperty;
	/** List of properties which are added to instance of {@link JVDrawChangeablePanel}. */
	private List<JTextField> addedProperties = new ArrayList<>();
	
	/** Foreground color wrapper. */
	private ColorWrapper fgColorWrapper;
	/** Background color wrapper. */
	private ColorWrapper bgColorWrapper;
	
	/**
	 * Sets first point changing available on panel.
	 * 
	 * @param firstPointName first point name
	 * @param firstPoint first point value
	 */
	public void setFirstPointChanging(String firstPointName, Point firstPoint) {		
		JLabel firstPointXLabel = new JLabel(firstPointName + X_COORD);
		firstPointXTF = new JTextField("" + firstPoint.x);
		add(firstPointXLabel);
		add(firstPointXTF);
		
		JLabel firstPointYLabel = new JLabel(firstPointName + Y_COORD);
		firstPointYTF = new JTextField("" + firstPoint.y);
		add(firstPointYLabel);
		add(firstPointYTF);
		
		addedProperties.add(firstPointXTF);
		addedProperties.add(firstPointYTF);
	}
	
	/**
	 * Sets second point changing available on panel.
	 * 
	 * @param secondPointName second point name
	 * @param secondPoint second point value
	 */
	public void setSecondPointChanging(String secondPointName, Point secondPoint) {
		JLabel secondPointXLabel = new JLabel(secondPointName + X_COORD);
		secondPointXTF = new JTextField("" + secondPoint.x);
		add(secondPointXLabel);
		add(secondPointXTF);
		
		JLabel secondPointYLabel = new JLabel(secondPointName + Y_COORD);
		secondPointYTF = new JTextField("" + secondPoint.y);
		add(secondPointYLabel);
		add(secondPointYTF);
		
		addedProperties.add(secondPointXTF);
		addedProperties.add(secondPointYTF);
	}
	
	/**
	 * Sets integer property changing available on panel.
	 * 
	 * @param propertyName property name
	 * @param value property value
	 */
	public void setIntegerPropertyChanging(String propertyName, int value) {
		JLabel propertyLabel = new JLabel(propertyName);
		integerProperty = new JTextField("" + value);
		add(propertyLabel);
		add(integerProperty);
		
		addedProperties.add(integerProperty);
	}
	
	/**
	 * Sets foreground color changing available on panel.
	 * 
	 * @param fgColor foreground color
	 */
	public void setForegroundColorChanging(Color fgColor) {
		fgColorWrapper = new ColorWrapper(fgColor);
		JLabel fgColorLabel = new JLabel(FG_COLOR_NAME);
		JButton fgColorButton = new JButton("Open color chooser");
		fgColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser(fgColorWrapper.getColor());
				JDialog dialog = JColorChooser.createDialog(
						fgColorButton,
						"Choose color",
						true,
						chooser,
						new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								fgColorWrapper.setColor(chooser.getColor());
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
		add(fgColorLabel);
		add(fgColorButton);
	}
	
	/**
	 * Sets background color changing available on panel.
	 * 
	 * @param bgColor background color
	 */
	public void setBackgroundColorChanging(Color bgColor) {
		bgColorWrapper = new ColorWrapper(bgColor);
		JLabel bgColorLabel = new JLabel(BG_COLOR_NAME);
		JButton bgColorButton = new JButton("Open color chooser");
		bgColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser(bgColorWrapper.getColor());
				JDialog dialog = JColorChooser.createDialog(
						bgColorButton,
						"Choose color",
						true,
						chooser,
						new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								bgColorWrapper.setColor(chooser.getColor());
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
		add(bgColorLabel);
		add(bgColorButton);
	}

	@Override
	public Point getFirstPoint() {
		return new Point(Integer.parseInt(firstPointXTF.getText()), Integer.parseInt(firstPointYTF.getText()));
	}

	@Override
	public Point getSecondPoint() {
		return new Point(Integer.parseInt(secondPointXTF.getText()), Integer.parseInt(secondPointYTF.getText()));
	}
	
	@Override
	public int getRadius() {
		return Integer.parseInt(integerProperty.getText());
	}

	@Override
	public Color getFGColor() {
		return fgColorWrapper.getColor();
	}

	@Override
	public Color getBGColor() {
		return bgColorWrapper.getColor();
	}
	
	@Override
	public boolean validatePanelData() {
		try {
			addedProperties.forEach(tf -> {
				Integer.parseInt(tf.getText());
			});
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
