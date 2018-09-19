package hr.fer.zemris.java.hw05.demo2;

/**
 * Demonstration of PrimesCollection.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class PrimesDemo2 {

	/**
	 * Method which starts when program is run.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		PrimesCollection primesCollection = new PrimesCollection(2);
		
		for(Integer prime : primesCollection) {
			for(Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
