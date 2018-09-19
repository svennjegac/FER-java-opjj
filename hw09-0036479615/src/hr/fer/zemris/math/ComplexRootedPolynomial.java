package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a polynomial which is defined by its roots.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ComplexRootedPolynomial {

	/** Roots of a polynomial. */
	private Complex[] roots;
	
	/**
	 * Constructor which accepts roots and constructs a polynomial.
	 * 
	 * @param roots roots of polynomial
	 */
	public ComplexRootedPolynomial(Complex ...roots) {
		if (roots == null) {
			throw new IllegalArgumentException("Roots can not be null.");
		}
		
		if (roots.length == 0) {
			throw new IllegalArgumentException("Roots length must not be 0.");
		}
		
		this.roots = roots;
	}
	
	/**
	 * Method calculates value of polynomial in dot z.
	 * 
	 * @param z dot in complex plain
	 * @return value of polynomial in that dot
	 */
	public Complex apply(Complex z) {
		return this.toComplexPolynom().apply(z);
	}
	
	/**
	 * Method returns {@link ComplexPolynomial} which represents same
	 * function as this polynomial.
	 * 
	 * @return {@link ComplexPolynomial} of a same function
	 */
	public ComplexPolynomial toComplexPolynom() {
		List<ComplexPolynomial> polynomList = new ArrayList<>();
		
		for (int i = 0; i < roots.length; i++) {
			polynomList.add(new ComplexPolynomial(roots[i].negate(), Complex.ONE));
		}
		
		while (polynomList.size() > 1) {
			ComplexPolynomial polynom = polynomList.get(0).multiply(polynomList.get(1));
			
			polynomList.remove(0);
			polynomList.remove(0);
			polynomList.add(0, polynom);
		}
		
		return polynomList.get(0);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (Complex complex : roots) {
			sb.append("[z - (" + complex.toString() + ")]");
		}
		
		return sb.toString();
	}
	
	/**
	 * Method returns index of closest root(to z) of this class stored in
	 * {@link #roots}, but its difference must be within threshold.
	 * If there is no roots satisfying this condition, -1 is returned.
	 * 
	 * @param z reference point
	 * @param threshold threshold
	 * @return index of closest root to threshold
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		if (z == null) {
			throw new IllegalArgumentException("Parameter can not be null.");
		}
		
		Complex[] differences = new Complex[roots.length];
		
		for (int i = 0; i < roots.length; i++) {
			differences[i] = z.sub(roots[i]);
		}
		
		int index = -1;
		
		for (int i = 0; i < differences.length; i++) {
			double diff = differences[i].module();
			
			if (diff > threshold) {
				continue;
			}
			
			if (index == -1) {
				index = i;
				continue;
			}
			
			if (diff < differences[index].module()) {
				index = i;
			}
		}
		
		return index;
	}
}
