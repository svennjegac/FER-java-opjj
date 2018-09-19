package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;

/**
 * Class which represents an action with calculator.
 * It defines behavior when negate button is pressed.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class NegateButtonAction extends CalculatorAction {
	
	/** Serial version UID. */
	private static final long serialVersionUID = -2089477763999366999L;

	/**
	 * Constructor.
	 * 
	 * @param value button name
	 * @param calc calculator reference
	 */
	public NegateButtonAction(String value, Calculator calc) {
		super(value, calc);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		calc.newNumberExpected = false;
		calc.secondOperandSet = false;
		
		if (calc.screenLabel.getText().equals("0")) {
			return;
		}
		
		if (calc.screenLabel.getText().startsWith("-")) {
			calc.screenLabel.setText(calc.screenLabel.getText().substring(1));
			return;
		}
		
		calc.screenLabel.setText("-" + calc.screenLabel.getText());
		
	}
}