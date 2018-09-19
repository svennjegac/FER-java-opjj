package hr.fer.zemris.java.hw04.collections;

/**
 * Example of usage of SimpleHashtable.
 * Command line arguments are not used.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Main {

	/**
	 * Method run when porgram is started.
	 * 
	 * @param args command line args
	 */
	public static void main(String[] args) {
		
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		
		// query collection:
		Integer kristinaGrade = examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
		
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
		
		System.out.println(examMarks);
	}
}
