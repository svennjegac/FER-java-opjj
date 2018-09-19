package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw16.util.Util;

/**
 * Class representing a vocabular of documents given by their location.
 * Vocabular removes stop words defined in {@link Vocabular#STOP_WORDS_LOCATION} file.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Vocabular {

	/** Set of vocabular words. */
	private Set<String> vocabularWords;
	/** Set of stop words. */
	private Set<String> stopWords;
	/** Stop words location. */
	private static final String STOP_WORDS_LOCATION = "src/main/resources/hrvatski_stoprijeci.txt";
	
	/**
	 * Constructor accepting documents location.
	 * 
	 * @param documentsLocation documents location
	 */
	public Vocabular(String documentsLocation) {
		vocabularWords = new LinkedHashSet<>();
		stopWords = new LinkedHashSet<>();
		
		initStopWords();
		initVocabular(documentsLocation);
	}
	
	/**
	 * Getter for vocabular words.
	 * 
	 * @return vocabular words
	 */
	public Set<String> getVocabularWords() {
		return vocabularWords;
	}
	
	/**
	 * Method filters words which are in vocabular.
	 * 
	 * @param words parameter words
	 * @return only words consisted in vocabular
	 */
	public List<String> filterValidWords(List<String> words) {
		return words.stream()
					.filter(vocabularWords::contains)
					.collect(Collectors.toList());
	}

	/**
	 * Initialization of stop words.
	 */
	private void initStopWords() {
		Path stopWordsPath = Paths.get(STOP_WORDS_LOCATION);
		
		if (!Files.exists(stopWordsPath) || !Files.isRegularFile(stopWordsPath)
			|| !Files.isReadable(stopWordsPath)) {	
			
			throw new IllegalArgumentException("Provide valid file for stop words.");
		}
		
		try {
			Util.readAllLines(stopWordsPath).forEach(line -> {
				stopWords.add(line);
			});
		} catch (Exception e) {
			throw new IllegalArgumentException("Stop words reading failed.");
		}
	}
	
	/**
	 * Initialization of vocabular words.
	 * 
	 * @param documentsLocation documents location
	 */
	private void initVocabular(String documentsLocation) {
		Path documentsPath = Paths.get(documentsLocation);
		
		try {
			Files.walkFileTree(documentsPath, new FileVisitorAdapter() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Util.readAllLines(file).forEach(line -> {
						fillVocabularWithNewWords(line);
					});
					
					return FileVisitResult.CONTINUE;
				}
				
				private void fillVocabularWithNewWords(String line) {
					Util.getWordsFromLine(line).forEach(word -> {
						if (!stopWords.contains(word)) {
							vocabularWords.add(word);
						}
					});
				}
			});
		} catch (IOException e) {
			throw new IllegalArgumentException("Can not visit documents for vacabulary initialization.");
		}
	}
}
