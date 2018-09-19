package hr.fer.zemris.bf.qmc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Professor example.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Example {

	/**
	 * Method which is run when program starts.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		
		Set<Integer> minterms = new HashSet<>(Arrays.asList(0,1,3,10,11,14,15));
		Set<Integer> dontcares = new HashSet<>(Arrays.asList(4,6));
		@SuppressWarnings("unused")
		Minimizer m = new Minimizer(minterms, dontcares, Arrays.asList("A","B","C","D"));

	}

}
