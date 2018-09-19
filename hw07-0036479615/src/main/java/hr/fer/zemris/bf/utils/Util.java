package hr.fer.zemris.bf.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import hr.fer.zemris.bf.model.Node;

/**
 * Utility class which offers a set of methods that are used for
 * different operations with boolean expressions.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Util {
	
	/**
	 * Makes representation of integer as a binary number and
	 * returns it as array.
	 * 
	 * @param x number
	 * @param n number of positions in binary number
	 * @return byte array representing binary number
	 */
	public static byte[] indexToByteArray(int x, int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Second argument must be 0 or higher; was: " + n);
		}
		
		long value = x;
		
		boolean negative = value < 0 ? true : false;
		long absolute = Math.abs(value);
		
		byte[] result = new byte[n];
		
		convertNumberToBits(absolute, result);
		
		if (negative) {
			doubleComplement(result);
		}
		
		return result;
	}
	
	/**
	 * Stores number in array as binary representation.
	 * 
	 * @param x number
	 * @param result array representing binary number
	 */
	private static void convertNumberToBits(long x, byte[] result) {
		Arrays.fill(result, (byte) 0);
		
		int potention = twosPotentionLessOrEqual(x);
		
		while (potention >= 0) {
			if (x >= Math.pow(2, potention)) {
				x -= Math.pow(2, potention);
				
				if (result.length > potention) {
					result[potention] = 1;
				}
			}
			
			if (x == 0) {
				break;
			}
			
			potention--;
		}
		
		reverseArray(result);
	}
	
	/**
	 * Reverses array.
	 * 
	 * @param array array to be reversed
	 */
	private static void reverseArray(byte[] array) {
		for(int i = 0; i < array.length / 2; i++) {
		    byte tmp = array[i];
		    array[i] = array[array.length - i - 1];
		    array[array.length - i - 1] = tmp;
		}
	}
	
	/**
	 * Double complements byte array representing binary number.
	 * 
	 * @param array byte array
	 */
	private static void doubleComplement(byte[] array) {
		oneComplement(array);
		
		for (int i = array.length - 1; i >= 0; i--) {
			if (array[i] == (byte) 1) {
				array[i] = (byte) 0;
				continue;
			}
			
			array[i] = (byte) 1;
			break;
		}
	}
	
	/**
	 * One complements byte array representing binary number.
	 * 
	 * @param array byte array
	 */
	private static void oneComplement(byte[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = array[i] == (byte) 0 ? (byte) 1 : (byte) 0;
		}
	}
	
	/**
	 * For number, method fines index of number two exponent which gives
	 * first smaller number than given or same number.
	 * 
	 * @param x number
	 * @return exponent
	 */
	private static int twosPotentionLessOrEqual(long x) {
		if (x == 0) {
			return -1;
		}
		
		int potention = 0;
		
		while (Math.pow(2, potention) < x) {
			potention++;
		}
		
		potention = Math.pow(2, potention) == x ? potention : potention - 1;
		
		return potention;
	}

	/**
	 * Method calculates sum of minterms of expression.
	 * 
	 * @param variables variables used in expression
	 * @param expression expression for calculation
	 * @return set of minterms
	 */
	public static Set<Integer> toSumOfMinterms(List<String> variables, Node expression) {
		return getMatchingRows(variables, expression, true);
	}

	/**
	 * Method calculates product of maxterms of expression.
	 * 
	 * @param variables variables used in expression
	 * @param expression expression for calculation
	 * @return set of maxterms
	 */
	public static Set<Integer> toProductOfMaxterms(List<String> variables, Node expression) {
		return getMatchingRows(variables, expression, false);
	}
	
	/**
	 * Method calculates which rows of expression evaluate to same value
	 * as provided value.
	 * 
	 * @param variables variables used in expression
	 * @param expression expression for calculation
	 * @param value matching value
	 * @return set of integers representing rows
	 */
	private static Set<Integer> getMatchingRows(List<String> variables, Node expression, boolean value) {
		return filterAssignments(variables, expression, value)
					.stream()
					.map(row -> Util.booleanArrayToInt(row))
					.collect(Collectors.toSet());
	}
	
	/**
	 * Method calculates integer representation for boolean array.
	 * 
	 * @param values boolean array
	 * @return integer representation of boolean array
	 */
	public static int booleanArrayToInt(boolean[] values) {
		if (values == null) {
			throw new IllegalArgumentException("Values can not be null.");
		}
		
		int sum = 0;
		
		for (int i = values.length - 1; i >= 0; i--) {
			sum += (values[i] == true) ? (int) Math.pow(2, values.length - 1 - i) : 0;
		}
		
		return sum;
	}
	
	/**
	 * Method returns set of boolean arrays which evaluates to provided
	 * expression value.
	 * 
	 * @param variables variables used in expression
	 * @param expression boolean expression
	 * @param expressionValue matching value
	 * @return set of boolean arrays which match expressionValue
	 */
	public static Set<boolean[]> filterAssignments(List<String> variables, Node expression, boolean expressionValue) {
		if (variables == null || expression == null) {
			throw new IllegalArgumentException("Variables or expression can not be null;"
					+ " variables: " + variables + "; expression: " + expression);
		}
		
		Set<boolean[]> resultSet = new LinkedHashSet<>();
		ExpressionEvaluator evaluator = new ExpressionEvaluator(variables);
		
		forEach(variables, truthPermutation -> {
			evaluator.setValues(truthPermutation);
			expression.accept(evaluator);
			
			if (evaluator.getResult() == expressionValue) {
				resultSet.add(truthPermutation);
			}
		});
		
		return resultSet;
	}
	
	/**
	 * Method accepts list of variables and constructs a truth table with number of
	 * rows equal to 2**(number of variables).
	 * Then for each row consumer is called.
	 * 
	 * @param variables list of variables
	 * @param consumer defined consumer
	 */
	public static void forEach(List<String> variables, Consumer<boolean[]> consumer) {
		if (variables == null || consumer == null) {
			throw new IllegalArgumentException("Variables or consumer can not be null.");
		}
		
		if (variables.isEmpty()) {
			return;
		}
		
		List<boolean[]> truthTable = new ArrayList<>();
		
		constructTruthTable(truthTable, variables.size());
		
		truthTable.forEach(consumer);
	}
	
	/**
	 * Method constructs a truth table based on number of variables.
	 * 
	 * @param truthTable truthTable which will be filled
	 * @param numberOfVariables number of variables
	 */
	private static void constructTruthTable(List<boolean[]> truthTable, int numberOfVariables) {
		boolean[] truthPermutation = new boolean[numberOfVariables];
		Arrays.fill(truthPermutation, false);
		
		for (int i = 0, size = (int) Math.pow(2, numberOfVariables); i < size; i++) {
			truthTable.add(Arrays.copyOf(truthPermutation, truthPermutation.length));
			
			boolean changeNext = true;
			for (int j = truthPermutation.length - 1; j >= 0; j--) {
				if (changeNext) {
					truthPermutation[j] = truthPermutation[j] ^ true;
					
					if (truthPermutation[j] == true) {
						break;
					}
				}
			}
		}
	}
}
