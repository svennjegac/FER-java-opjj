package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw16.math.MyVector;
import hr.fer.zemris.java.hw16.util.Util;

/**
 * Class represents a collection which has document paths as keys
 * and their vector TFIDF vector representations as values.
 * Class offers searching which document is the most similar to some other document.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class DocumentsTFIDFVectors {

	/** List of vocabular words. */
	private List<String> vocabularWords;
	/** Reference to IDF Values class. */
	private IDFValues idfValues;
	/** Map consisted of key value pairs -> documents and TFIDF vectors. */
	private Map<String, MyVector> docsAndVectors = new HashMap<>();
	
	/**
	 * Class constructor.
	 * 
	 * @param vocabularWords vocabular words
	 * @param idfValues IDF values
	 * @param documentsLocation location of documents which will be stored in collection
	 */
	public DocumentsTFIDFVectors(List<String> vocabularWords, IDFValues idfValues, String documentsLocation) {
		this.vocabularWords = vocabularWords;
		this.idfValues = idfValues;
		
		initializeDocumentsVectors(documentsLocation);
	}
	
	/**
	 * Method accepts list of strings representing words.
	 * Method returns map of key value pairs consisting of
	 * similarity, document path values.
	 * Results are ordered descending.
	 * 
	 * @param words list of words
	 * @return 10 or less most similar documents
	 */
	public Map<Double, String> searchDocuments(List<String> words) {
		double[] queryData = new double[vocabularWords.size()];
		
		for (String word : words) {
			int wordOccurences = Util.calculateWordOccurencesInLines(word, words);
			queryData[vocabularWords.indexOf(word)] = wordOccurences * idfValues.getIDFValue(word);
		}
		
		MyVector queryVector = new MyVector(queryData);
		Map<String, MyVector> docsAndVectorsCopy = new HashMap<>(docsAndVectors);
		Map<Double, String> results = new LinkedHashMap<>();
		
		for (int i = 0; i < 10; i++) {
			Wrapper result = findBestMatchingDocument(queryVector, docsAndVectorsCopy);
			
			if (result.getValue() == Wrapper.INITIAL_VALUE) {
				break;
			}
			
			results.put(result.getValue(), result.getPath());
			docsAndVectorsCopy.remove(result.getPath());
		}
		
		return results;
	}

	/**
	 * Method finds best matching document for provided query vector.
	 * 
	 * @param queryVector query vector of TFIDF values
	 * @param docsAndVectorsCopy map of documents and vectors
	 * @return wrapper consisting a path to document and similarity to vector
	 */
	private Wrapper findBestMatchingDocument(MyVector queryVector, Map<String, MyVector> docsAndVectorsCopy) {
		Wrapper wrapper = new Wrapper();
		
		docsAndVectorsCopy.keySet().forEach(key -> {
			MyVector docVector = docsAndVectorsCopy.get(key);
			double coef = similarity(queryVector, docVector);
			
			if (coef > wrapper.getValue()) {
				wrapper.setValue(coef);
				wrapper.setPath(key);
			}
		});
		
		return wrapper;
	}
	
	/**
	 * Method calculates similarity of 2 vectors.
	 * 
	 * @param first first vector
	 * @param second second vector
	 * @return similarity
	 */
	private double similarity(MyVector first, MyVector second) {
		return first.dot(second) / (first.norm() * second.norm());
	}

	/**
	 * Method initializes collection of documents and their TFIDF vectors.
	 * 
	 * @param documentsLocation location of documents
	 */
	private void initializeDocumentsVectors(String documentsLocation) {
		try {
			Files.walkFileTree(Paths.get(documentsLocation), new FileVisitorAdapter() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					double[] vectorData = new double[vocabularWords.size()];
					List<String> lines = Util.readAllLines(file);
					
					for (String line : lines) {
						List<String> words = Util.getWordsFromLine(line);
						
						for (String word : words) {
							if (!vocabularWords.contains(word)) {
								continue;
							}
							
							int wordOccurences = Util.calculateWordOccurencesInLines(word, lines);
							vectorData[vocabularWords.indexOf(word)] = wordOccurences * idfValues.getIDFValue(word);
						}
					}
					
					docsAndVectors.put(file.toAbsolutePath().toString(), new MyVector(vectorData));
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			throw new IllegalArgumentException("Error while initializing document vectors.");
		}
	}
	
	/**
	 * Class having two properties, double value
	 * and string which in our usage represents a path.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class Wrapper {
		
		/** Default value. */
		private static final Double INITIAL_VALUE = 0.0;
		/** Value. */
		private double value = INITIAL_VALUE;
		/** Path. */
		private String path;
		
		/**
		 * Setter for value.
		 * 
		 * @param value value
		 */
		public void setValue(double value) {
			this.value = value;
		}
		
		/**
		 * Getter for value.
		 * 
		 * @return value
		 */
		public double getValue() {
			return value;
		}
		
		/**
		 * Setter for path.
		 * 
		 * @param path path
		 */
		public void setPath(String path) {
			this.path = path;
		}
		
		/**
		 * Getter for path.
		 * 
		 * @return path
		 */
		public String getPath() {
			return path;
		}
	}
}
