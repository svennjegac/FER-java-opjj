package hr.fer.zemris.java.fractals;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Utility class which offers parsing of users input to its complex
 * value and calculating of Newton-Raphson fractals.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Util {

	/**
	 * Method takes an input of type a + ib and parses it to its
	 * complex value.
	 * If input can not be parsed, null is returned.
	 * 
	 * @param input user input
	 * @return complex value
	 */
	public static Complex parseUserInput(String input) {
		input = input.replaceAll("\\s*", "");
		
		String numberRegex = "(([+-])?([0-9]+(.[0-9]+)?))";
		String cpxNumberRegex = "((i[0-9]*(.[0-9]+)?))";
		String numberExists = "(" + numberRegex + "([+-] " + cpxNumberRegex +")?" + ")";
		String cpxNumberExists = "(" + "(" + numberRegex + "[+-])?" + cpxNumberRegex + ")";
		
		boolean validExpression = false;
		String rightPart = null;
		String leftPart = null;
		
		if (input.matches(numberExists)) {
			rightPart = input.replaceFirst(numberRegex, "");
			leftPart = input.substring(0, input.length() - rightPart.length());
			
			if (rightPart.length() > 0) {
				rightPart = rightPart.replaceAll("i", "");
				
				if (rightPart.length() == 0 || rightPart.equals("-") || rightPart.equals("+")) {
					rightPart += "1";
				}
				
			} else {
				rightPart = "0";
			}
			
			validExpression = true;
		}
		
		if (input.matches(cpxNumberExists) || input.matches("[+-]" + cpxNumberRegex)) {
			leftPart = input.replaceFirst("[+-]?" + cpxNumberRegex, "");
			rightPart = input.substring(leftPart.length(), input.length());
			
			rightPart = rightPart.replaceAll("i", "");
			if (rightPart.length() == 0 || rightPart.equals("-") || rightPart.equals("+")) {
				rightPart += "1";
			}
			
			if (leftPart.length() < 1) {
				leftPart = "0";
			}
			
			validExpression = true;
		}
		
		if (validExpression) {
			try {
				return new Complex(Double.parseDouble(leftPart), Double.parseDouble(rightPart));
			} catch (NumberFormatException e) {
				return null;
			}
		}
		
		return null;
	}
	
	/**
	 * Method calculates Newton-Raphson fractal values and populates data array which represents
	 * picture. Method calculates values for just a part of picture in height range
	 * from yStart to yStop.
	 * 
	 * @param reMin minimum for real part
	 * @param reMax maximum for real part
	 * @param imMin minimum for imaginary part
	 * @param imMax maximum for imaginary part
	 * @param width screen width
	 * @param height screen height
	 * @param yStart height start point
	 * @param yStop height stop point
	 * @param data picture data
	 * @param rootedPolynomial polynomial in rooted shape
	 * @param polynomial polynomial
	 * @param derived derived polynomial
	 */
	public static void newton(double reMin, double reMax, double imMin, double imMax, int width, int height,
			int yStart, int yStop, short[] data, ComplexRootedPolynomial rootedPolynomial, ComplexPolynomial polynomial, ComplexPolynomial derived) {
		for (int y = yStart; y < yStop; y++) {
			for(int x = 0; x < width; x++) {
				Complex c = mapToComplexPlain(x, y, 0, width, 0, height, reMin, reMax, imMin, imMax);
				
				Complex zn = c;
				Complex zn1;
				
				int iter = 0;
				double module;
				double convergenceTreshold = 0.00001;
				
				do {
					iter++;
					
					Complex numerator = polynomial.apply(zn);
					Complex denominator = derived.apply(zn);
					
					Complex fraction = numerator.divide(denominator);
					
					zn1 = zn.sub(fraction);
					
					module = zn1.sub(zn).module();
					
					zn = zn1;
					
				} while(module > convergenceTreshold && iter < 100_000);
				
				int index = rootedPolynomial.indexOfClosestRootFor(zn1, 0.002);
				
				if(index == -1) {
					data[y * width + x] = (short) 0;
				} else {
					data[y * width + x] = (short) (index + 1);
				}
			}
		}
	}
	
	/**
	 * Method scales x, y coordinates to its allowed real and imaginary values.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param xMin minimum for x
	 * @param xMax maximum for x
	 * @param yMin minimum for y
	 * @param yMax maximum for y
	 * @param reMin minimum for real part
	 * @param reMax maximum for real part
	 * @param imMin minimum for imaginary part
	 * @param imMax maximum for imaginary part
	 * @return scaled complex number
	 */
	private static Complex mapToComplexPlain(int x, int y, int xMin, int xMax, int yMin, int yMax, double reMin,
			double reMax, double imMin, double imMax) {
		
		double xScale = (reMax - reMin) / (xMax - xMin);
		double yScale = (imMax - imMin) / (yMax - yMin);
		
		double xScaled = x * xScale + reMin;
		double yScaled = -(y * yScale + imMin);
	
		return new Complex(xScaled, yScaled);
	}
}
