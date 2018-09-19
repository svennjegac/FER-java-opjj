package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;
import java.util.function.UnaryOperator;

/**
 * Class which represents an action with calculator.
 * It defines behavior when unary operator button is pressed.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class UnaryOperatorAction extends CalculatorAction {

	/** Serial version UID. */
	private static final long serialVersionUID = -6355062839461312881L;
	/** Button main operator. */
	UnaryOperator<Double> mainOperator;
	/** Button inverse operator. */
	UnaryOperator<Double> inverseOperator;
	
	/**
	 * Constructor.
	 * 
	 * @param value button name
	 * @param calc calculator reference
	 * @param mainOperator button main operator
	 */
	public UnaryOperatorAction(String value, Calculator calc, UnaryOperator<Double> mainOperator) {
		super(value, calc);
		this.mainOperator = mainOperator;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param value button name
	 * @param calc calculator reference
	 * @param mainOperator button main operator
	 * @param inverseOperator button inverse operator
	 */
	public UnaryOperatorAction(String value, Calculator calc, UnaryOperator<Double> mainOperator, UnaryOperator<Double> inverseOperator) {
		super(value, calc);
		this.mainOperator = mainOperator;
		this.inverseOperator = inverseOperator;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		UnaryOperator<Double> currentOperator;
		
		if (inversionSelected() && inverseOperator != null) {
			currentOperator = inverseOperator;
		} else {
			currentOperator = mainOperator;
		}
		
		calc.screenLabel.setText(currentOperator.apply(Double.parseDouble(calc.screenLabel.getText())).toString());
		calc.newNumberExpected = true;
	}
}
