package hr.fer.zemris.java.gui.layouts;

/**
 * Class which represents positions which are used in
 * {@link CalcLayout}.
 * It has x and y indication of position.
 * It has {@link #SPECIAL} special position which indicates that on that
 * position is component of different dimensions.
 * It has dimensions of same height, but width is {@link #SPECIAL_WIDTH} times wider
 * than other components widths.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class RCPosition {
	
	/** Component with special dimensions. */
	public static final RCPosition SPECIAL = new RCPosition(1, 1);
	/** Width of component wit special dimensions. */
	public static final int SPECIAL_WIDTH = 5;

	/** Row of component. */
	private int row;
	/** Column of component. */
	private int column;
	
	/**
	 * Constructor.
	 * 
	 * @param row row in which component will be placed
	 * @param column column in which component will be placed
	 */
	public RCPosition(int row, int column) {
		if (row < 1 || row > 5 || column < 1 || column > 7) {
			throw new IllegalArgumentException("Invalid row and column; were: row='" + row + "', column='" + column + "'.");
		}
		
		if (row == 1 && (column > 1 && column <= 5)) {
			throw new IllegalArgumentException("Forbidden row and column; were: row='" + row + "', column='" + column + "'.");
		}
		
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Getter for row position.
	 * 
	 * @return row position
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Getter for column position.
	 * 
	 * @return column position
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("Row: %d, Column: %d", row, column);
	}

	/**
	 * Method makes Object which can be used as a constraint in component
	 * positioning in a parent component.
	 * 
	 * @param constraints constraints as a string
	 * @return constraints as an object
	 */
	public static Object makeFromString(String constraints) {
		constraints = constraints.replaceAll("\\s", "");
		
		String regex = "([0-9]+),([0-9]+)";
		
		if (!constraints.matches(regex)) {
			throw new IllegalArgumentException("Invalid string entered; was: " + constraints);
		}
		
		String[] arguments = constraints.split(",");
		
		try {
			return new RCPosition(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Numbers can not be parsed.");
		}
	}
}
