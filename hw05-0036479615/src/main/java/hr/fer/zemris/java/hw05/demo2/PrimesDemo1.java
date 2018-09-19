package hr.fer.zemris.java.hw05.demo2;

/**
 * Demonstration of PrimesCollection usage.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class PrimesDemo1 {

	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		
		for(Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}
}
