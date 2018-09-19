package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a single complex number with real and
 * imaginary part.
 * Class also defines various number of methods which are executable
 * on complex numbers, such as: addition, subtraction, multiplication, division...
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Complex {

	/** Complex number representing 0. */
	public static final Complex ZERO = new Complex(0, 0);
	/** Complex number representing 1. */
	public static final Complex ONE = new Complex(1, 0);
	/** Complex number representing -1. */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/** Complex number representing i. */
	public static final Complex IM = new Complex(0, 1);
	/** Complex number representing -i. */
	public static final Complex IM_NEG = new Complex(0, -1);
	
	/** Real part of complex number. */
	private double re;
	/** Imaginary part of complex number. */
	private double im;
	
	/**
	 * Default constructor. Constructs 0.
	 */
	public Complex() {
		re = 0;
		im = 0;
	}

	/**
	 * Constructor with provided real and imaginary part.
	 * 
	 * @param re real part
	 * @param im imaginary part
	 */
	public Complex(double re, double im) {
		super();
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Getter for real part.
	 * 
	 * @return real part
	 */
	public double getRe() {
		return re;
	}
	
	/**
	 * Getter for imaginary part.
	 * 
	 * @return imaginary part
	 */
	public double getIm() {
		return im;
	}
	
	/**
	 * Calculates magnitude of complex number.
	 * 
	 * @return magnitude of complex number
	 */
	public double module() {
		return Math.sqrt(Math.pow(re, 2) + Math.pow(im, 2));
	}
	
	/**
	 * Method multiplies this complex number with one in
	 * parameter and returns new complex number.
	 * 
	 * @param c multiplier
	 * @return new complex number
	 */
	public Complex multiply(Complex c) {
		validate(c);
		
		double real = re * c.re - im * c.im;
		double imaginary = re * c.im + im * c.re;
		
		return new Complex(real, imaginary);
	}
	
	/**
	 * Method divides this complex number with one given in parameter.
	 * 
	 * @param c divisor
	 * @return new complex number
	 */
	public Complex divide(Complex c) {
		validate(c);
		
		if (Math.abs(c.re) == 0.0 && Math.abs(c.im) == 0.0) {
			throw new IllegalArgumentException("Can not divide by zero.");
		}

		Complex conjugated = new Complex(c.re, -c.im);
		Complex numerator = multiply(conjugated);
		Complex denominator = c.multiply(conjugated);

		return new Complex(numerator.re / denominator.re,
				numerator.im / denominator.re);
	}
	
	/**
	 * Method adds complex number to this complex number and
	 * returns new complex number containing result.
	 * 
	 * @param c number to be added
	 * @return new complex number
	 */
	public Complex add(Complex c) {
		validate(c);
		
		return new Complex(re + c.re, im + c.im);
	}
	
	/**
	 * Method subtracts this complex number with one in parameter and
	 * returns new complex number containing result.
	 * 
	 * @param c number to be subtracted
	 * @return  new complex number
	 */
	public Complex sub(Complex c) {
		validate(c);
		
		return new Complex(re - c.re, im - c.im);
	}
	
	/**
	 * Method returns new complex number which is result of
	 * negation of this complex number.
	 * 
	 * @return new complex number
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	/**
	 * Method calculates power of this complex number and returns result
	 * as a new complex number.
	 * Exponents less than 0 are illegal.
	 * 
	 * @param n exponent
	 * @return new complex number
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Exponent must be 0 or higher.");
		}

		double magnitude = Math.pow(module(), n);
		double angle = angle() * n;
		
		return new Complex(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	/**
	 * Method calculates root of complex number and returns list
	 * of results.
	 * Root must be number higher than 0.
	 * 
	 * @param n root
	 * @return list of complex number roots
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Root must be positive number.");
		}

		double magnitude = root(module(), n);
		double angle = angle();

		List<Complex> roots = new ArrayList<>();
		
		for (int i = 0; i < n; i++) {
			double realPart = magnitude * Math.cos((angle + 2 * i * Math.PI) / n);
			double imaginaryPart = magnitude * Math.sin((angle + 2 * i * Math.PI) / n);

			roots.add(new Complex(realPart, imaginaryPart));
		}

		return roots;
	}
	
	@Override
	public String toString() {
		String sign;
		
		if (im < 0) {
			sign = "-";
		} else {
			sign = "+";
		}
		
		return String.format("%.5f " + sign + " %.5fi", re, Math.abs(im));
	}
	
	/**
	 * Method calculates angle of complex number.
	 * 
	 * @return angle of complex number
	 */
	private double angle() {
		return Math.atan2(im, re);
	}
	
	/**
	 * Method calculates root of number.
	 * Number must be positive, and root must be higher than 0.
	 * 
	 * @param number number which root is searched
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
	 * Method checks if complex number is null and throws
	 * exception if it is.
	 * 
	 * @param c complex number to be checked
	 */
	private void validate(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException("C can not be null.");
		}
	}
}
