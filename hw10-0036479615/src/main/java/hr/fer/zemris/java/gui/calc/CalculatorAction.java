package hr.fer.zemris.java.gui.calc;

import javax.swing.AbstractAction;

/**
 * Class which represents an action with calculator.
 * It defines behavior when some button is pressed.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public abstract class CalculatorAction extends AbstractAction {

	/** Serial version UID. */
	private static final long serialVersionUID = -3015369534459555303L;
	/** Button name. */
	String value;
	/** Calculator reference. */
	Calculator calc;
	
	/**
	 * Constructor.
	 * 
	 * @param value button name
	 * @param calc calculator reference
	 */
	public CalculatorAction(String value, Calculator calc) {
		this.putValue(NAME, value);
		this.value = value;
		this.calc = calc;
	}
	
	/**
	 * Method which gives information if inversion is selected on calculator.
	 * 
	 * @return <code>true</code> if inversion is selected,
	 * 			<code>false</code> otherwise
	 */
	protected boolean inversionSelected() {
		return calc.invCheckBox.isSelected();
	}
}
