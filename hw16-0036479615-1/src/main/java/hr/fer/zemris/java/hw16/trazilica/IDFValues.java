package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.java.hw16.util.Counter;
import hr.fer.zemris.java.hw16.util.Util;

/**
 * Class representing a collection of IDF values for vocabular words.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class IDFValues {
	
	/** Words and their IDF values. */
	private Map<String, Double> IDFValues;

	/**
	 * Constructor.
	 * 
	 * @param vocabularWords vocabular words
	 * @param documentsLocation location of documents
	 */
	public IDFValues(Set<String> vocabularWords, String documentsLocation) {
		int numberOfDocuments = countDocuments(documentsLocation);
		Map<String, Set<String>> vocabularWordsInDocuments = matchWordsWithDocuments(vocabularWords, documentsLocation);
		IDFValues = createIDFValues(vocabularWordsInDocuments, numberOfDocuments);
	}
	
	/**
	 * Gets IDF value for asked word.
	 * 
	 * @param word word
	 * @return IDF value
	 */
	public Double getIDFValue(String word) {
		return IDFValues.get(word.toLowerCase());
	}

	/**
	 * Method count number of documents.
	 * 
	 * @param documentsLocation location of documents
	 * @return number of documents
	 */
	private int countDocuments(String documentsLocation) {
		Counter documentCounter = new Counter();
		
		try {
			Files.walkFileTree(Paths.get(documentsLocation), new FileVisitorAdapter() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					documentCounter.increment();
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			throw new IllegalArgumentException("Error while counting documents.");
		}
		
		return documentCounter.getCounter();
	}
	
	/**
	 * Method counts in how much different files every vocabular word occurs.
	 * 
	 * @param vocabularWords vocabular words
	 * @param documentsLocation lacation of documents
	 * @return map of word and set consisting documents in which that word occurs
	 */
	private Map<String, Set<String>> matchWordsWithDocuments(Set<String> vocabularWords, String documentsLocation) {
		Map<String, Set<String>> vocabularWordsInDocuments = new HashMap<>();
		
		try {
			Files.walkFileTree(Paths.get(documentsLocation), new FileVisitorAdapter() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Util.readAllLines(file).forEach(line -> {
						List<String> words = Util.getWordsFromLine(line);
						
						for (String word : words) {
							if (!vocabularWords.contains(word)) {
								continue;
							}
							
							if (!vocabularWordsInDocuments.containsKey(word)) {
								vocabularWordsInDocuments.put(word, new HashSet<>());
							}
							
							Set<String> wordDocuments = vocabularWordsInDocuments.get(word);
							if (!wordDocuments.contains(file.toAbsolutePath().toString())) {
								wordDocuments.add(file.toAbsolutePath().toString());
							}
						}
					});
					
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			throw new IllegalArgumentException("Error while reading from documents.");
		}
		
		return vocabularWordsInDocuments;
	}
	
	/**
	 * Method calculates IDF values for every word.
	 * 
	 * @param vocabularWordsInDocuments map of word and set consisting documents in which that word occurs
	 * @param numberOfDocuments number of documents
	 * @return IDF values map word -> value
	 */
	private Map<String, Double> createIDFValues(Map<String, Set<String>> vocabularWordsInDocuments, int numberOfDocuments) {
		Map<String, Double> IDFValues = new HashMap<>();
		
		vocabularWordsInDocuments.forEach((word, set) -> {
			IDFValues.put(word, Math.log((double) numberOfDocuments / (double) set.size()));
		});
		
		return IDFValues;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		IDFValues.forEach((w, v) -> {
			sb.append(w + ", " + v + "\n");
		});
		return sb.toString();
	}
}
