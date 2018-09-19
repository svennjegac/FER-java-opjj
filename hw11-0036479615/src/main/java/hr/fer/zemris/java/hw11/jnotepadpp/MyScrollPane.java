package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

/**
 * Implementation of scroll pane which has reference to tab.
 * It must have reference to tab in which it is constructed.
 * This class offers user to fetch {@link Tab} based on fetched
 * {@link Component} from tabbed pane. It is possible because in tabbed pane are always added
 * this classes which can then have other children components.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class MyScrollPane extends JScrollPane {
	
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	/** Reference to its tab. */
	private Tab tab;
	
	/**
	 * Constructor which has reference to its tab owner and to component
	 * which must be added as child.
	 * 
	 * @param tab owner tab
	 * @param component child component
	 */
	public MyScrollPane(Tab tab, JComponent component) {
		super(component);
		this.tab = tab;
	}
	
	/**
	 * Getter for tab owner.
	 * 
	 * @return owner tab
	 */
	public Tab getTab() {
		return tab;
	}
}
