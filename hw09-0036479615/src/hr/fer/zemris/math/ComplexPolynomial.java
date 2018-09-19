package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a polynomial of complex numbers.
 * It supports multiplication, deriving and applying in of
 * function in specified dot.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ComplexPolynomial {

	/** Factors with polynomial members. */
	private Complex[] factors;
	
	/**
	 * Constructor accepts factors and constructs polynomial.
	 * 
	 * @param factors polynomial factors
	 */
	public ComplexPolynomial(Complex ...factors) {
		if (factors == null) {
			throw new IllegalArgumentException("Factors can not be null.");
		}
		
		int i = factors.length - 1;
		for (; i >= 0; i--) {
			if (factors[i].getRe() == 0.0 && factors[i].getIm() == 0.0) {
				continue;
			}
			
			break;
		}
		
		this.factors = Arrays.copyOfRange(factors, 0, i + 1);

		if (this.factors.length == 0) {
			this.factors = new Complex[]{Complex.ZERO};
		}
	}
	
	/**
	 * Returns polynomial order.
	 * 
	 * @return polynomial order
	 */
	public short order() {
		return (short) (factors.length - 1);
	}
	
	/**
	 * Multiplies this polynomial with one in parameter and returns new
	 * polynomial which contains result.
	 * 
	 * @param p parameter polynomial
	 * @return new polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		if (p == null) {
			throw new IllegalArgumentException("Parameter must not be null.");
		}
		
		Map<Integer, List<Complex>> potentionListOfCoeficientsMap = multiplyEveryMemberWithEveryMember(p);
		Map<Integer, Complex> potentionSingleCoeficientMap = mergeCoeficientsWithSamePotention(potentionListOfCoeficientsMap);
		
		Complex[] resultFactors = new Complex[order() + p.order() + 1];
		
		potentionSingleCoeficientMap.forEach((potention, coeficient) -> {
			resultFactors[potention] = coeficient;
		});
		
		return new ComplexPolynomial(resultFactors);
	}
	
	/**
	 * Method multiplies every factor of this polynomial with every factor of parameter
	 * polynomial.
	 * Every result of multiplication is put in map which consists of integer(which potention) and
	 * list of coeficients.
	 * At the end there is map with integers pointing to lists, each integer represents a potention and its list
	 * contains that potention factors.
	 * 
	 * @param p parameter polynomial
	 * @return result of every with every member multiplication
	 */
	private Map<Integer, List<Complex>> multiplyEveryMemberWithEveryMember(ComplexPolynomial p) {
		//map containing coeficients standing to factor with same potention
		Map<Integer, List<Complex>> potentionListOfCoeficientsMap = new HashMap<>();
		
		for (int i = 0; i <= order(); i++) {
			for (int j = 0; j <= p.order(); j++) {
				//calculate new coeficient
				Complex coeficient = factors[i].multiply(p.factors[j]);
				
				//put coeficient in list of coeficients standing to same potention of factor
				int potention = i + j;
				List<Complex> listOfSamePotentions = potentionListOfCoeficientsMap.get(potention);
				
				if (listOfSamePotentions == null) {
					listOfSamePotentions = new ArrayList<>();
				}
				
				listOfSamePotentions.add(coeficient);
				
				potentionListOfCoeficientsMap.put(potention, listOfSamePotentions);
			}
		}
		
		return potentionListOfCoeficientsMap;
	}
	
	/**
	 * Method receives result of every with every polynomial member multiplication,
	 * {@link #multiplyEveryMemberWithEveryMember(ComplexPolynomial)} and calculates result
	 * coeficient for every potention.
	 * 
	 * @param potentionListOfCoeficientsMap result of {@link #multiplyEveryMemberWithEveryMember(ComplexPolynomial)}
	 * @return result coeficient for every potention
	 */
	private Map<Integer, Complex> mergeCoeficientsWithSamePotention(Map<Integer, List<Complex>> potentionListOfCoeficientsMap) {
		Map<Integer, Complex> potentionSingleCoeficientMap = new HashMap<>();
		
		potentionListOfCoeficientsMap.forEach((potention, coeficientsList) -> {
			Complex finalCoeficient = Complex.ZERO;
			
			for (int i = 0; i < coeficientsList.size(); i++) {
				finalCoeficient = finalCoeficient.add(coeficientsList.get(i));
			}
			
			potentionSingleCoeficientMap.put(potention, finalCoeficient);
		});
		
		return potentionSingleCoeficientMap;
	}
	
	/**
	 * Method derives this polynomial and returns result.
	 * 
	 * @return new polynomial
	 */
	public ComplexPolynomial derive() {
		if (factors.length <= 1) {
			return new ComplexPolynomial(Complex.ZERO);
		}
		
		Complex[] resultFactors = new Complex[order()];
		
		for (int i = order(); i > 0; i--) {
			resultFactors[i - 1] = factors[i].multiply(new Complex(i, 0));
		}
		
		return new ComplexPolynomial(resultFactors);
	}
	
	/**
	 * Method calculates value of polynomial in dot z.
	 * 
	 * @param z dot in complex plain
	 * @return value of polynomial in that dot
	 */
	public Complex apply(Complex z) {
		if (z == null) {
			throw new IllegalArgumentException("Parameter must not be null.");
		}
		
		Complex result = Complex.ZERO;
		
		for (int i = 0; i <= order(); i++) {
			result = result.add(z.power(i).multiply(factors[i]));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		
		for (int i = factors.length - 1; i >= 0; i--) {
			if (!first) {
				sb.append(" + ");
			}
			
			sb.append("(" + factors[i].toString() + ")*z**" + i);
			first = false;
		}
		
		return sb.toString();
	}
}
