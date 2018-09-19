package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;

/**
 * Class which represents an action with calculator.
 * It defines behavior when equals button is pressed.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class EqualsButtonAction extends CalculatorAction {
	
	/** Serial version UID. */
	private static final long serialVersionUID = 6963525589063606608L;

	/**
	 * Constructor.
	 * 
	 * @param value button name
	 * @param calc calculator reference
	 */
	public EqualsButtonAction(String value, Calculator calc) {
		super(value, calc);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (calc.equalsWasLast) {
			if (calc.operands.size() != 0) {
				calc.operands.remove(0);
			}
			
			calc.operands.add(0, Double.parseDouble(calc.screenLabel.getText()));
		}
		
		if (calc.currentBinaryOperator != null) {
			if (!calc.secondOperandSet && !calc.equalsWasLast) {
				if (calc.operands.size() > 1) {
					calc.operands.remove(1);
				}
				
				calc.operands.add(1, Double.parseDouble(calc.screenLabel.getText()));
				calc.secondOperandSet = true;
			}
			
			double firstOperand = calc.operands.remove(0);
			double secondOperand = calc.operands.get(0);
			
			calc.operands.add(0, calc.currentBinaryOperator.apply(firstOperand, secondOperand));
			calc.screenLabel.setText(Double.valueOf(calc.operands.get(0)).toString());
		}
		
		calc.newNumberExpected = true;
		calc.equalsWasLast = true;
	}
}