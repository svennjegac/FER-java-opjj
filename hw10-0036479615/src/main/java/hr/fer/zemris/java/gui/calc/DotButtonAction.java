package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;

/**
 * Class which represents an action with calculator.
 * It defines behavior when dot button is pressed.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class DotButtonAction extends CalculatorAction {
	
	/** Serial version UID. */
	private static final long serialVersionUID = 547132274667443247L;

	/**
	 * Constructor.
	 * 
	 * @param value button name
	 * @param calc calculator reference
	 */
	public DotButtonAction(String value, Calculator calc) {
		super(value, calc);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		calc.secondOperandSet = false;
		
		if (calc.newNumberExpected) {
			calc.newNumberExpected = false;
			calc.screenLabel.setText("0" + value);
		}
		
		if (calc.screenLabel.getText().indexOf(value) != -1) {
			return;
		}
		
		calc.screenLabel.setText(calc.screenLabel.getText() + value);
	}
}
