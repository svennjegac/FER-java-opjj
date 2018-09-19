package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.BinaryOperator;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Class which is implementation of calculator.
 * Calculators behavior is exactly the same as Windows calculator.
 * For same sequence of button pressed, every time on the screen the same 
 * numbers will appear.
 * 
 * It offers multiple equals button pressed operations for multiple
 * operation with same operand and so on.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Calculator extends JFrame {

	/** Serial version UID. */
	private static final long serialVersionUID = 7776950784217605791L;
	
	/** Calculator screen. */
	JLabel screenLabel;
	/** Panel for calculator components. */
	JPanel panel;
	/** Equals button. */
	JButton equalsButton;
	/** Check box for inversion of operations. */
	JCheckBox invCheckBox;
	
	/** Cached operands in calculator. */
	List<Double> operands = new ArrayList<>();
	/** Stack which stores results which are pushed to stack. */
	Stack<Double> memoryStack = new Stack<>();
	
	/** Reference to currently set binary operator. */
	BinaryOperator<Double> currentBinaryOperator;
	
	/** Flag which indicates if second operand is set. */
	boolean secondOperandSet;
	/** Flag which indicates if new number is expected from user. */
	boolean newNumberExpected;
	/** Flag which indicates if equals button was last pressed. */
	boolean equalsWasLast;
	
	/**
	 * Constructor.
	 */
	private Calculator() {
		setTitle("Calculator");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
	}
	
	/**
	 * Method which is run when porgram starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
	
	/**
	 * GUI initialization.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		
		panel = new JPanel(new CalcLayout(10));
		cp.add(panel);
		
		initSpecialButtons();
		initNumberButtons();
		initBinaryOperators();
		initUnaryOperators();
		
		this.setMinimumSize(panel.getMinimumSize());
		this.setSize(panel.getPreferredSize());
		this.setMaximumSize(panel.getMaximumSize());
		this.setLocation(700, 200);
	}
	
	/**
	 * Initialization of number buttons.
	 */
	private void initNumberButtons() {
		JButton button1 = new JButton();
		JButton button2 = new JButton();
		JButton button3 = new JButton();
		JButton button4 = new JButton();
		JButton button5 = new JButton();
		JButton button6 = new JButton();	
		JButton button7 = new JButton();
		JButton button8 = new JButton();
		JButton button9 = new JButton();
		JButton button0 = new JButton();
		
		button1.setAction(new NumberButtonAction("1", this));
		button2.setAction(new NumberButtonAction("2", this));
		button3.setAction(new NumberButtonAction("3", this));
		button4.setAction(new NumberButtonAction("4", this));
		button5.setAction(new NumberButtonAction("5", this));
		button6.setAction(new NumberButtonAction("6", this));
		button7.setAction(new NumberButtonAction("7", this));
		button8.setAction(new NumberButtonAction("8", this));
		button9.setAction(new NumberButtonAction("9", this));
		button0.setAction(new NumberButtonAction("0", this));
		
		panel.add(button1, "4,3");
		panel.add(button2, "4,4");
		panel.add(button3, "4,5");
		panel.add(button4, "3,3");
		panel.add(button5, "3,4");
		panel.add(button6, "3,5");
		panel.add(button7, "2,3");
		panel.add(button8, "2,4");
		panel.add(button9, "2,5");
		panel.add(button0, "5,3");
	}
	
	/**
	 * Initialization of binary operators.
	 */
	private void initBinaryOperators() {
		JButton addButton = new JButton();
		addButton.setAction(new BinaryOperatorAction("+", this, (a, b) -> a + b));
		panel.add(addButton, "5,6");
		
		JButton subButton = new JButton();
		subButton.setAction(new BinaryOperatorAction("-", this, (a, b) -> a - b));
		panel.add(subButton, "4,6");
		
		JButton mulButton = new JButton();
		mulButton.setAction(new BinaryOperatorAction("*", this, (a, b) -> a * b));
		panel.add(mulButton, "3,6");
		
		JButton divButton = new JButton();
		divButton.setAction(new BinaryOperatorAction("/", this, (a, b) -> a / b));
		panel.add(divButton, "2,6");
		
		JButton powButton = new JButton();
		powButton.setAction(new BinaryOperatorAction("x^n", this, Math::pow, (a, b) -> Math.pow(a, (1.0 / b))));
		panel.add(powButton, "5,1");
	}
	
	/**
	 * Initialization of unary operators.
	 */
	private void initUnaryOperators() {
		JButton sinButton = new JButton();
		sinButton.setAction(new UnaryOperatorAction("sin", this, Math::sin, Math::asin));
		panel.add(sinButton, "2,2");
		
		JButton cosButton = new JButton();
		cosButton.setAction(new UnaryOperatorAction("cos", this,  Math::cos, Math::acos));
		panel.add(cosButton, "3,2");
		
		JButton tanButton = new JButton();
		tanButton.setAction(new UnaryOperatorAction("tan", this,  Math::tan, Math::atan));
		panel.add(tanButton, "4,2");
		
		JButton ctgButton = new JButton();
		ctgButton.setAction(new UnaryOperatorAction("ctg", this,  a -> { return 1.0 / Math.tan(a); }, a -> { return 1.0 / Math.atan(a); }));
		panel.add(ctgButton, "5,2");
		
		JButton lnButton = new JButton();
		lnButton.setAction(new UnaryOperatorAction("ln", this,  Math::log, Math::exp));
		panel.add(lnButton, "4,1");
		
		JButton logButton = new JButton();
		logButton.setAction(new UnaryOperatorAction("log", this, Math::log10, a -> Math.pow(10.0, a)));
		panel.add(logButton, "3,1");
		
		JButton oneDivXButton = new JButton();
		oneDivXButton.setAction(new UnaryOperatorAction("1/x", this,  a -> { return 1.0 / a; }));
		panel.add(oneDivXButton, "2,1");
	}
	
	/**
	 * Initialization of special buttons and screen.
	 */
	private void initSpecialButtons() {
		//label where result appears
		screenLabel = new JLabel("0");
		screenLabel.setOpaque(true);
		screenLabel.setBackground(Color.ORANGE);
		screenLabel.setForeground(Color.BLACK);
		screenLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		screenLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		screenLabel.setPreferredSize(new Dimension(370, 50));
		screenLabel.setMinimumSize(new Dimension(370, 50));
		panel.add(screenLabel, "1,1");
		
		//equals button
		equalsButton = new JButton();
		equalsButton.setAction(new EqualsButtonAction("=", this));
		panel.add(equalsButton, "1,6");
		
		//reset button
		JButton resButton = new JButton("res");
		resButton.addActionListener(e -> {
			resetCalculator();
		});
		panel.add(resButton, "2,7");
	
		//clear button
		JButton clrButton = new JButton("clr");
		clrButton.addActionListener(e -> {
			screenLabel.setText("0");
		});
		panel.add(clrButton, "1,7");
		
		//inversion check box
		invCheckBox = new JCheckBox("Inv");
		invCheckBox.setOpaque(true);
		invCheckBox.setForeground(Color.BLACK);
		invCheckBox.setBackground(Color.LIGHT_GRAY);
		invCheckBox.setVisible(true);
		panel.add(invCheckBox, "5,7");
		
		//dot button
		JButton dotButton = new JButton();
		dotButton.setAction(new DotButtonAction(".", this));
		panel.add(dotButton, "5,5");
		
		//negate button
		JButton negateButton = new JButton();
		negateButton.setAction(new NegateButtonAction("+/-", this));
		panel.add(negateButton, "5,4");
		
		//push button
		JButton pushButton = new JButton("push");
		pushButton.addActionListener(e -> {
			memoryStack.push(Double.parseDouble(screenLabel.getText()));
		});
		panel.add(pushButton, "3,7");
		
		//pop button
		JButton popButton = new JButton("pop");
		popButton.addActionListener(e -> {
			if (memoryStack.isEmpty()) {
				JOptionPane.showMessageDialog(Calculator.this, "Stack is empty.");
				return;
			}
			
			screenLabel.setText(memoryStack.pop().toString());
		});
		panel.add(popButton, "4,7");
	}
	
	/**
	 * Method which resets calculator.
	 */
	private void resetCalculator() {
		currentBinaryOperator = null;
		newNumberExpected = false;
		secondOperandSet = false;
		operands = new ArrayList<>();
		memoryStack = new Stack<>();
		screenLabel.setText("0");
		equalsWasLast = false;
		invCheckBox.setSelected(false);
	}
}
