package hr.fer.zemris.java.hw16.trazilica;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import hr.fer.zemris.java.hw16.util.Util;

/**
 * Class which is a simple searcher of the most similar document based
 * on user query.
 * User must provide 1 command line argument -> path to folder with documents.
 * User can enter commands
 * query ... -> output is most similar documents
 * results -> output results by last query command
 * type X -> X is number -> output is file on the X position of similarity
 * exit -> program ends
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Konzola {
	
	/** Vocabular. */
	private static Vocabular vocabular;
	/** Documents and TFIDF vectors. */
	private static DocumentsTFIDFVectors docsTFIDFVectors;

	/**
	 * Method run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		if (!initConsole(args)) {
			return;
		}
		
		System.out.println("Veličina riječnika je: " + vocabular.getVocabularWords().size());
		
		Scanner sc = new Scanner(System.in);
		Map<Double, String> results = null;
		while (true) {
			System.out.print("Enter command > ");
			String input = sc.nextLine().trim().toLowerCase();
			String command = input.split("\\s+")[0];
			boolean exit = false;
			
			switch (command) {
			case "query":
				results = processQuery(input.replaceFirst(command, ""));
				break;
			case "results":
				if (results != null) {
					printResults(results);
				} else {
					System.out.println("Prvo dohvatite rezultate.");
				}
				break;
			case "type":
				if (results == null) {
					System.out.println("Prvo dohvatite rezultate.");
					break;
				} else if (results.size() == 0) {
					System.out.println("Dohvaćeni rezultati su prazni.");
					break;
				}
				processType(input.replaceFirst(command, ""), results);
				break;
			case "exit":
				exit = true;
				break;
			default:
				System.out.println("Nepoznata naredba.");
			}
			
			if (exit) {
				System.out.println("Doviđenja.");
				break;
			}
		}
		sc.close();
	}

	/**
	 * Method processes query command.
	 * 
	 * @param query query string representation
	 * @return results of query
	 */
	private static Map<Double, String> processQuery(String query) {
		List<String> allQueryWords = Util.getWordsFromLine(query);
		List<String> validQueryWords = vocabular.filterValidWords(allQueryWords);
		
		System.out.print("Query is: [");
		for (int i = 0, size = validQueryWords.size(); i < size; i++) {
			if (i == 0) {
				System.out.print(validQueryWords.get(i));
			} else {
				System.out.print(", " + validQueryWords.get(i));
			}
		}
		System.out.println("]");
		
		Map<Double, String> results = docsTFIDFVectors.searchDocuments(validQueryWords);
		System.out.println("Najboljih deset rezultata:");
		printResults(results);
		return results;
	}

	/**
	 * Method prints results to console.
	 * 
	 * @param results query results
	 */
	private static void printResults(Map<Double, String> results) {
		int i = 0;
		for (Entry<Double, String> entry : results.entrySet()) {
			System.out.println("[" + i + "] " + "(" + entry.getKey() + ") " + entry.getValue());
			i++;
		}
	}
	
	/**
	 * Method processes type command.
	 * It outputs document on index given in type command to user.
	 * 
	 * @param input type command
	 * @param results results of last query command
	 */
	private static void processType(String input, Map<Double, String> results) {
		try {
			int index = Integer.parseInt(input.trim());
			if (index >= results.size() || index < 0) {
				System.out.println("Vaš indeks je: " + index + 
						", za dohvaćene rezultate validni indeksi su od " + 0 + " do " + (results.size() - 1) + ".");
			}
			
			int i = 0;
			String file = null;
			for (Entry<Double, String> entry : results.entrySet()) {
				if (i == index) {
					file = entry.getValue();
				}
				i++;
			}
			
			Util.readAllLines(Paths.get(file)).forEach(System.out::println);
		} catch (Exception e) {
			System.out.println("'type' command failed.");
		}
	}

	/**
	 * Method initializes console application.
	 * 
	 * @param args command line arguments
	 * @return <code>true</code> if initialization was successful, <code>false</code> otherwise
	 */
	private static boolean initConsole(String[] args) {
		if (args.length != 1) {
			System.out.println("Please provide single path to documents.");
			return false;
		}
		
		try {
			vocabular = new Vocabular(args[0]);
		} catch (IllegalArgumentException e) {
			return false;
		}
		
		IDFValues idfValues = null;
		try {
			idfValues = new IDFValues(vocabular.getVocabularWords(), args[0]);
		} catch (IllegalArgumentException e) {
			return false;
		}
		
		try {
			docsTFIDFVectors = new DocumentsTFIDFVectors(
					new ArrayList<>(vocabular.getVocabularWords()), idfValues, args[0]);
		} catch (IllegalArgumentException e) {
			return false;
		}
		
		return true;
	}
}
