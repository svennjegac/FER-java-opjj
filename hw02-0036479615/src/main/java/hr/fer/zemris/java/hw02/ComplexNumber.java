package hr.fer.zemris.java.hw02;

/**
 * Class offers support for working with complex numbers.
 * ComplexNumber is unmodifiable and it offers numerous methods to
 * interact with him such as addition, subtraction, division...
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ComplexNumber {

	/** Real part of complex number. */
	private double real;
	/** Imaginary part of complex number. */
	private double imaginary;

	/**
	 * Constructor constructs complex number from real and imaginary part.
	 * 
	 * @param real real part of complex number
	 * @param imaginary imaginary part of complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Method makes complex number from real part.
	 * 
	 * @param real real part of complex number.
	 * @return complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Method makes complex number from imaginary part.
	 * 
	 * 
	 * @param imaginary imaginary part of complex number
	 * @return complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Method makes complex number from magnitude and angle.
	 * 
	 * @param magnitude magnitude of complex number
	 * @param angle angle of complex number
	 * @return complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Method makes complex number from string.
	 * It parses string.
	 * 
	 * @param s string which will be evaluated as complex number
	 * @return complex number
	 */
	public static ComplexNumber parse(String s) {
		if (s == null) {
			throw new IllegalArgumentException("String can not be null.");
		}
		
		ComplexNumberParser parser = new ComplexNumberParser();
		double array[] = parser.parse(s);
		return new ComplexNumber(array[0], array[1]);
	}

	/**
	 * Gets the real part of the number.
	 * 
	 * @return real part of complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Gets the imaginary part of complex number.
	 * 
	 * @return imaginary part of complex number
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Gets magnitude of complex number.
	 * 
	 * @return magnitude of complex number
	 */
	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Gets angle of complex number.
	 * 
	 * @return angle of complex number
	 */
	public double getAngle() {
		return Math.atan(imaginary / real);
	}
	
	/**
	 * Method checks if complex number is not null.
	 * 
	 * @param c complex number
	 */
	private void validate(ComplexNumber c) {
		if (c == null) {
			throw new IllegalArgumentException("Complex number can not be null.");
		}
	}

	/**
	 * Method adds complex number with provided complex number
	 * and returns new complex number.
	 * 
	 * @param c complex number which will be added
	 * @return complex number - result of addition
	 */
	public ComplexNumber add(ComplexNumber c) {
		validate(c);
		return new ComplexNumber(real + c.getReal(), imaginary + c.getImaginary());
	}

	/**
	 * Method subtracts complex number from complex number
	 * and returns new complex number.
	 * NEW = this - c
	 * 
	 * @param c complex number - subtrahend of operation
	 * @return complex number - difference
	 */
	public ComplexNumber sub(ComplexNumber c) {
		validate(c);
		return new ComplexNumber(real - c.getReal(), imaginary - c.getImaginary());
	}

	/**
	 * Method multiplies complex number with another complex number
	 * and returns new complex number.
	 * 
	 * @param c complex number - multiplier
	 * @return complex number - product
	 */
	public ComplexNumber mul(ComplexNumber c) {
		validate(c);
		double realPart = real * c.getReal() - imaginary * c.getImaginary();
		double imaginaryPart = real * c.getImaginary() + imaginary * c.getReal();
		return new ComplexNumber(realPart, imaginaryPart);
	}

	/**
	 * Method divides complex number by provided complex number
	 * returning the result.
	 * 
	 * @param c complex number - divisor
	 * @return complex number - quotient
	 */
	public ComplexNumber div(ComplexNumber c) {
		validate(c);
		if (c.getReal() < 1E-3 && c.getImaginary() < 1E-3) {
			throw new IllegalArgumentException("Can not divide by zero.");
		}

		ComplexNumber conjugated = new ComplexNumber(c.getReal(), -c.getImaginary());
		ComplexNumber numerator = mul(conjugated);
		ComplexNumber denominator = c.mul(conjugated);

		return new ComplexNumber(numerator.getReal() / denominator.getReal(),
				numerator.getImaginary() / denominator.getReal());
	}

	/**
	 * Method calculates power of complex number and returns new
	 * complex number.
	 * 
	 * @param n exponent
	 * @return complex number - result
	 */
	public ComplexNumber power(int n) {
		if (!(n >= 0)) {
			throw new IllegalArgumentException("Illegal power argument.");
		}

		double magnitude = Math.pow(getMagnitude(), n);
		double angle = getAngle() * n % (Math.PI * 2);

		return ComplexNumber.fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Method calculates root of complex number and
	 * returns new complex number.
	 * 
	 * @param n root
	 * @return complex number - result
	 */
	public ComplexNumber[] root(int n) {
		if (!(n > 0)) {
			throw new IllegalArgumentException("Illegal root argument.");
		}

		ComplexNumber[] array = new ComplexNumber[n];
		double magnitude = root(getMagnitude(), n);
		double angle = getAngle();

		for (int i = 0; i < n; i++) {
			double realPart = magnitude * Math.cos((angle + 2 * i * Math.PI) / n);
			double imaginaryPart = magnitude * Math.sin((angle + 2 * i * Math.PI) / n);

			array[i] = (new ComplexNumber(realPart, imaginaryPart));
		}

		return array;
	}

	/**
	 * Method used for root calculation.
	 * Number should be positive and root should be
	 * higher than 0.
	 * 
	 * @param number number for which root is calculated
	 * @param root root
	 * @return root of number
	 */
	private double root(double number, int root) {
		if (number <= 0 || root < 1) {
			throw new IllegalArgumentException("Illegal arguments for root calculation.");
		}

		return Math.pow(Math.E, Math.log(number) / root);
	}

	/**
	 * Method outputs pretty format String of complex number.
	 * 
	 * @return String - pretty format complex number representation
	 */
	@Override
	public String toString() {
		String output = "";

		output += String.format("%.2f", real);

		if (imaginary >= 0) {
			output += "+";
		}

		output += String.format("%.2fi", imaginary);
		return output;
	}

	/**
	 * Helper class for parsing complex numbers from string input.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class ComplexNumberParser {

		/** State of parser which determines how parser is working */
		private String state = "";
		/** Flag indicates if parser is consuming first token. */
		private boolean firstIteration = true;
		/** Flag indicates if real part is already set. */
		private boolean realSet = false;
		/** Real part of complex number. */
		private double real = -1;
		/** Flag indicates if imaginary part of complex number is set. */
		private boolean imaginarySet = false;
		/** Imaginary part of complex number. */
		private double imaginary = -1;

		/**
		 * Method parses string and returns an array.
		 * Array consists of [real, imaginary] values.
		 * 
		 * @param s string to be parsed
		 * @return array of results [real, imaginary]
		 */
		private double[] parse(String s) {
			String first = consume(s);
			validateState();
			
			assignStringAsComplexPart(first);
			s = prepareStringForNextToken(s, first);

			String second = consume(s);
			validateState();

			assignStringAsComplexPart(second);
			s = prepareStringForNextToken(s, second);

			if (s.length() != 0) {
				throw new IllegalArgumentException("Non valid expression. Too much tokens.");
			}

			real = realSet ? real : 0;
			imaginary = imaginarySet ? imaginary : 0;

			double array[] = { real, imaginary };
			return array;
		}

		/**
		 * Method takes an input string and returns first token.
		 * Token is representation of one number(real or imaginary).
		 * 
		 * @param s string
		 * @return string token representing number
		 */
		private String consume(String s) {
			String output = "";
			state = "";

			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);

				if (!allowedChar(c)) {
					state = "error";
					break;
				}
				
				//second number in parsing(!firstIteration)
				//first char of second number must be + or -
				//simply because two numbers must be of type a + bi
				if (!firstIteration && i == 0) {
					if (c != '-' && c != '+') {
						state = "error";
						break;
					}
				}

				
				if (c == 'i') {
					//if there was no digits before(state is not real)
					//add number 1 because i is read as 1*i
					//else just say that it is imaginary
					if (!state.equals("real")) {
						output += "1";
						state = "imaginaryOnlyI";
					} else {
						state = "imaginary";
					}

					break;
				}

				if (!Character.isDigit(c) && c != '.' && state.equals("real")) {
					break;
				}

				if (Character.isDigit(c)) {
					state = "real";
				}

				output += s.charAt(i);
			}

			firstIteration = false;
			return output;
		}
		
		/**
		 * Method checks if char is in allowed chars for parsing.
		 * 
		 * @param c char under test
		 * @return true if char is allowed, otherwise false
		 */
		private boolean allowedChar(char c) {
			if (Character.isDigit(c)) {
				return true;
			}
			
			char array[] = { 'i', '+', '-', '.' };
			
			for (int i = 0; i < array.length; i++) {
				if (array[i] == c) {
					return true;
				}
			}
			
			return false;
		}

		/**
		 * Method strips first token from string and
		 * returns string.
		 * 
		 * @param s input string
		 * @param first first token
		 * @return stripped string
		 */
		private String prepareStringForNextToken(String s, String first) {
			if (first.length() >= s.length()) {
				return "";
			}

			s = s.substring(first.length(), s.length());

			if (state.equals("imaginary")) {
				s = s.substring(1, s.length());
			}

			return s;
		}

		/**
		 * Method takes string token and assigns it
		 * to real or imaginary part.
		 * 
		 * @param s string token to be assigned as number
		 */
		private void assignStringAsComplexPart(String s) {
			double number = 0;

			if (state.equals("") && s.length() != 0) {
				throw new IllegalArgumentException("Not valid complex number expression.");
			}
			
			if (!state.equals("")) {
				number = Double.parseDouble(s);
			}

			if (state.equals("real")) {
				if (realSet) {
					throw new IllegalArgumentException("Too much real arguments.");
				}

				realSet = true;
				real = number;
			}

			if (state.equals("imaginary") || state.equals("imaginaryOnlyI")) {
				if (imaginarySet) {
					throw new IllegalArgumentException("Too much imaginary arguments.");
				}

				imaginarySet = true;
				imaginary = number;
			}
		}

		/**
		 * Validates state of parser.
		 */
		private void validateState() {
			if (state.equals("error")) {
				throw new IllegalArgumentException("Not valid complex number expression.");
			}
		}
	}
}
