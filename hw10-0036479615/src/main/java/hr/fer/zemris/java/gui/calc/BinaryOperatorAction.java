package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;
import java.util.function.BinaryOperator;

/**
 * Class which represents an action with calculator.
 * It defines behavior when binary operator button is pressed.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class BinaryOperatorAction extends CalculatorAction {
	
	/** Serial version UID. */
	private static final long serialVersionUID = 6804144599059097928L;
	/** Button main operation. */
	BinaryOperator<Double> mainOperator;
	/** Button inverse operation. */
	BinaryOperator<Double> inverseOperator;
	
	/**
	 * Constructor.
	 * 
	 * @param value button name
	 * @param calc calculator reference
	 * @param mainOperator button main operation
	 */
	public BinaryOperatorAction(String value, Calculator calc, BinaryOperator<Double> mainOperator) {
		super(value, calc);
		this.mainOperator = mainOperator;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param value button name
	 * @param calc calculator reference
	 * @param mainOperator button main operation
	 * @param inverseOperator button inverse operaation
	 */
	public BinaryOperatorAction(String value, Calculator calc, BinaryOperator<Double> mainOperator, BinaryOperator<Double> inverseOperator) {
		super(value, calc);
		this.mainOperator = mainOperator;
		this.inverseOperator = inverseOperator;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		BinaryOperator<Double> currentOperator;
		
		if (inversionSelected() && inverseOperator != null) {
			currentOperator = inverseOperator;
		} else {
			currentOperator = mainOperator;
		}
		
		if (calc.operands.size() > 0 && !calc.newNumberExpected && !calc.equalsWasLast) {
			calc.screenLabel.setText((calc.currentBinaryOperator.apply(calc.operands.get(0), Double.parseDouble(calc.screenLabel.getText()))).toString());
		}
		
		if (calc.operands.size() > 0) {
			calc.operands.remove(0);
		}
		
		calc.operands.add(0, Double.parseDouble(calc.screenLabel.getText()));
		
		calc.currentBinaryOperator = currentOperator;
		calc.newNumberExpected = true;
		calc.secondOperandSet = false;
		calc.equalsWasLast = false;
		
		if (calc.operands.size() > 1) {
			calc.operands.remove(1);
		}
	}
}
