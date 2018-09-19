package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;


/**
 * Class which represents an action with calculator.
 * It defines behavior when number button is pressed.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class NumberButtonAction extends CalculatorAction {
	
	/** Serial version UID. */
	private static final long serialVersionUID = 2578098434535609506L;

	/**
	 * Constructor.
	 * 
	 * @param value button name
	 * @param calc calculator reference
	 */
	public NumberButtonAction(String value, Calculator calc) {
		super(value, calc);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		calc.secondOperandSet = false;
		
		if (calc.screenLabel.getText().equals("0") || calc.newNumberExpected) {
			calc.screenLabel.setText(value);
			calc.newNumberExpected = false;
			return;
		}
		
		calc.screenLabel.setText(calc.screenLabel.getText() + value);
	}
}
