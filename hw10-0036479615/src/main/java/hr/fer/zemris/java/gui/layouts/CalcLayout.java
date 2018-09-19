package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * This class is a simple layout manager for {@link Calculator}.
 * It has five rows and seven columns. Every position holds one component.
 * Exception is position 1,1 which takes first five positions of first row.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class CalcLayout implements LayoutManager2 {
	
	/** Number of rows. */
	private static final int ROWS = 5;
	/** Number of columns. */
	private static final int COLUMNS = 7;
	/** Gap between components. */
	private int gap;
	
	/** Map which remembers which component is on which position. */
	private Map<Component, RCPosition> components = new HashMap<>();
	
	/**
	 * Default constructor with gap set to 0.
	 */
	public CalcLayout() {
		this(0);
	}
	
	/**
	 * Constructor which accepts user provided gap.
	 * 
	 * @param gap gap between components
	 */
	public CalcLayout(int gap) {
		if (gap < 0) {
			throw new IllegalArgumentException("Gap can not be less than 0; was: " + gap);
		}
		
		this.gap = gap;
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}
	
	/**
	 * Simple strategy which offers user a way to indicate which
	 * component size he wants to get.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private interface SizeGetter {
		/**
		 * Gets the size of component.
		 * 
		 * @param container component
		 * @return size
		 */
		Dimension getSize(Component container);
	}
	
	/**
	 * Returns the size of this component which is based on its child components and type of wanted size
	 * (minimum, maximum, preferred).
	 * 
	 * @param parent component which size is going to be calculated
	 * @param sizeGetter which size we want to look up (minimum, maximum, preferred)
	 * @param findSmallest do you want to find smallest or biggest size of component children (you want biggest minimum and preferred, and smallest maximum)
	 * @return dimension
	 */
	private Dimension getSizeConsideringChildComponents(Container parent, SizeGetter sizeGetter, boolean findSmallest) {
		double componentWidth = findComponentSize(parent, sizeGetter, findSmallest, true);
		double componentHeight = findComponentSize(parent, sizeGetter, findSmallest, false);
		
		Insets parentInsets = parent.getInsets();
		
		double totalWidth = componentWidth * COLUMNS + gap * (COLUMNS - 1) + parentInsets.left + parentInsets.right;
		double totalHeight = componentHeight * ROWS + gap * (ROWS - 1) + parentInsets.top + parentInsets.bottom;
		
		return new Dimension((int) totalWidth, (int) totalHeight);
	}
	
	/**
	 * Method is able to find smallest or biggest component length. Length can be either height or width.
	 * Furthermore component can find either of this values of minimum, maximum or preferred size of component.
	 * 
	 * @param parent component which size is going to be calculated
	 * @param sizeGetter which size we want to look up (minimum, maximum, preferred)
	 * @param findSmallest do you want to find smallest or biggest size of component children (you want biggest minimum and preferred, and smallest maximum)
	 * @param width do you want to find width or height
	 * @return wanted length
	 */
	private double findComponentSize(Container parent, SizeGetter sizeGetter, boolean findSmallest, boolean width) {
		double length;
		
		if (findSmallest) {
			length = Double.MAX_VALUE;
		} else {
			length = Double.MIN_VALUE;
		}
		
		for (int i = 0, size = parent.getComponentCount(); i < size; i++) {
			Component component = parent.getComponent(i);
			
			if (sizeGetter.getSize(component) == null) {
				continue;
			}
			
			double currentLength = width ? sizeGetter.getSize(component).width : sizeGetter.getSize(component).height;
			
			if (components.get(component).equals(RCPosition.SPECIAL) && width) {
				currentLength = (currentLength - gap * (RCPosition.SPECIAL_WIDTH - 1)) / (double) RCPosition.SPECIAL_WIDTH;
			}
			
			if (findSmallest) {
				length = currentLength < length ? currentLength : length;
			} else {
				length = currentLength > length ? currentLength : length;
			}
		}
		
		return length;
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		if (parent == null) {
			throw new IllegalArgumentException("Parent can not be null.");
		}
		
		return getSizeConsideringChildComponents(parent, Component::getPreferredSize, false);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		if (parent == null) {
			throw new IllegalArgumentException("Parent can not be null.");
		}
		
		return getSizeConsideringChildComponents(parent, Component::getMinimumSize, false);
	}

	@Override
	public void layoutContainer(Container parent) {
		double componentWidth = (double) (parent.getWidth() - (COLUMNS - 1) * gap) / COLUMNS;
		double componentHeight = (double) (parent.getHeight() - (ROWS - 1) * gap) / ROWS;
		Insets parentInsets = parent.getInsets();
		
		for (int i = 0, size = parent.getComponentCount(); i < size; i++) {
			Component component = parent.getComponent(i);
			RCPosition rcPosition = components.get(component);
		
			int x = (int) ((rcPosition.getColumn() - 1) * (componentWidth + gap) + parentInsets.left);
			int y = (int) ((rcPosition.getRow() - 1) * (componentHeight + gap) + parentInsets.top);
			
			component.setBounds(
					x,
					y,
					rcPosition.equals(RCPosition.SPECIAL) ? (int) (componentWidth * RCPosition.SPECIAL_WIDTH + (RCPosition.SPECIAL_WIDTH - 1) * gap) : (int) componentWidth,
					(int) componentHeight
			);
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints instanceof RCPosition) {
			RCPosition rcPosition = (RCPosition) constraints;
			
			if (components.containsValue(rcPosition)) {
				throw new IllegalArgumentException("Component on provided position already exists; was: " + rcPosition.toString());
			}
			
			components.put(comp, rcPosition);
			return;
		}
		
		if (constraints instanceof String) {
			addLayoutComponent(comp, RCPosition.makeFromString((String) constraints));
			return;
		}
		
		throw new IllegalArgumentException("Constraints were invalid class; were: " + constraints.getClass().toString());
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		if (target == null) {
			throw new IllegalArgumentException("Target can not be null.");
		}
		
		return getSizeConsideringChildComponents(target, Component::getMaximumSize, true);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}
}
