package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class representing a wrapper of object stored in
 * ObjectMultistack class.
 * ValueWrapper offers several methods which can be made
 * on object with other objects, such as: add, subtract, multiply, divide
 * and numCompare.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ValueWrapper {
	
	/** Addition signature. */
	private static final String ADD = "+";
	/** Subtraction signature. */
	private static final String SUB = "-";
	/** Multiplication signature. */
	private static final String MUL = "*";
	/** Division signature. */
	private static final String DIV = "/";
	
	/** Value stored in wrapper. */
	private Object value;

	/**
	 * Constructor accepting a single value.
	 * 
	 * @param value value which will be stored in wrapper
	 */
	public ValueWrapper(Object value) {
		super();
		this.value = value;
	}
	
	/**
	 * Sets value stored in wrapper.
	 * 
	 * @param value new value stored in wrapper
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * Returns value stored in wrapper.
	 * 
	 * @return value stored in wrapper
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Adds parameter value to current value
	 * stored in wrapper.
	 * 
	 * @param incValue value which is going to be added
	 */
	public void add(Object incValue) {
		value = operate(value, incValue, ADD);
	}
	
	/**
	 * Subtracts parameter value from current value
	 * stored in wrapper.
	 * 
	 * @param decValue value which is going to be subtracted
	 */
	public void subtract(Object decValue) {
		value = operate(value, decValue, SUB);
	}
	
	/**
	 * Multiplies current value with parameter value.
	 * 
	 * @param mulValue multiplier
	 */
	public void multiply(Object mulValue) {
		value = operate(value, mulValue, MUL);
	}
	
	/**
	 * Divides current value with divValue.
	 * 
	 * @param divValue value which divides current value
	 */
	public void divide(Object divValue) {
		value = operate(value, divValue, DIV);
	}
	
	/**
	 * Compares current value with provided value.
	 * 
	 * @param withValue value with which current value is going to be compared
	 * @return 1 if current value is higher, -1 if current value is lower, 0 if values are equal
	 */
	public int numCompare(Object withValue) {
		Number myValue = assignDoubleOrIntegerValue(value);
		Number argValue = assignDoubleOrIntegerValue(withValue);
	
		if (myValue instanceof Double || argValue instanceof Double) {
			return Double.compare(myValue.doubleValue(), argValue.doubleValue());
		}
		
		return Integer.compare(myValue.intValue(), argValue.intValue());
	}
	
	/**
	 * Method accepts two objects, converts them to Numbers, and
	 * returns result of defined operation on two values.
	 * 
	 * If values are Integers, Integer will be returned.
	 * If at least one value is Double, Double will be returned.
	 * 
	 * @param value current value of wrapper
	 * @param argument value provided in argument
	 * @param operation definition of operation that should be made
	 * @return Integer or Double result of operation
	 */
	private Number operate(Object value, Object argument, String operation) {
		Number myValue = assignDoubleOrIntegerValue(value);
		Number argValue = assignDoubleOrIntegerValue(argument);
		
		if (myValue instanceof Double || argValue instanceof Double) {
			return operationWithDoubles(myValue.doubleValue(), argValue.doubleValue(), operation);
		}
		
		return operationWithIntegers(myValue.intValue(), argValue.intValue(), operation);
	}
	
	/**
	 * Method performs operation on double values.
	 * 
	 * @param value1 first double value
	 * @param value2 second double value
	 * @param operation operation which is going to be made
	 * @return double result
	 */
	private Double operationWithDoubles(Double value1, Double value2, String operation) {
		switch (operation) {
			case ADD:	return value1 + value2;
			case SUB:	return value1 - value2;
			case MUL:	return value1 * value2;
			case DIV:	return value1 / value2;
			default:	throw new RuntimeException("Unsupported operation; was: " + operation);
		}
	}
	
	/**
	 * Method performs operation on integer values.
	 * 
	 * @param value1 first integer value
	 * @param value2 second integer value
	 * @param operation operation which is going to be made
	 * @return integer result
	 */
	private Integer operationWithIntegers(Integer value1, Integer value2, String operation) {
		switch (operation) {
			case ADD:	return value1 + value2;
			case SUB:	return value1 - value2;
			case MUL:	return value1 * value2;
			case DIV:	return value1 / value2;
			default:	throw new RuntimeException("Unsupported operation; was: " + operation);
		}
	}
	
	/**
	 * Method accepts Object and returns its value
	 * as Number.
	 * 
	 * If Object can not be interpreted as Number,
	 * exception will be thrown.
	 * 
	 * @param value processed value
	 * @return Number representation
	 */
	private Number assignDoubleOrIntegerValue(Object value) {
		if (value instanceof String) {
			return processString(value);
		}
		
		if (value instanceof Double || value instanceof Integer) {
			return (Number) value;
		}
		
		if (value == null) {
			return new Integer(0);
		}
		
		throw new RuntimeException("Current object value is not of valid type; was: " + value.getClass());
	}
	
	/**
	 * Method accepts String and returns its value
	 * as a Number.
	 * If String can not be parsed as a Number,
	 * exception is thrown.
	 * 
	 * @param value String which will be parsed
	 * @return Number value of String
	 */
	private Number processString(Object value) {
		String str = (String) value;
		
		if (str.indexOf(".") != -1 || str.indexOf("E") != -1) {
			return Double.parseDouble(str);
		}
		
		return Integer.parseInt(str);
	}
}
