package hr.fer.zemris.java.hw16.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class with some helping methods.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Util {

	/**
	 * Method reads all lines from file.
	 * 
	 * @param filePath file path
	 * @return all lines as list
	 * @throws IOException if reading fails
	 */
	public static List<String> readAllLines(Path filePath) throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(Files.newInputStream(filePath), StandardCharsets.UTF_8));
	
		List<String> lines = new ArrayList<>();
		while (true) {
			String line = reader.readLine();
			
			if (line == null) {
				break;
			}
			
			lines.add(line.toLowerCase());
		}
		reader.close();
		
		return lines;
	}
	
	/**
	 * Method accepts string and extracts words from it.
	 * 
	 * @param line line
	 * @return list of words in line
	 */
	public static List<String> getWordsFromLine(String line) {
		List<String> words = new ArrayList<>();
		char[] data = line.toCharArray();
		
		int i = 0;
		int startOfWord = 0;
		while (i < data.length) {
			char c = data[i];
			
			if (!Character.isLetter(c)) {
				if (startOfWord < i) {
					String word = String.copyValueOf(data, startOfWord, i - startOfWord);
					words.add(word);
				}
				
				i++;
				startOfWord = i;
				continue;
			}
			
			i++;
		}
		
		if (startOfWord < i) {
			String word = String.copyValueOf(data, startOfWord, i - startOfWord);
			words.add(word);
		}
		
		return words;
	}
	
	/**
	 * Method calculates word repetition in lines.
	 * 
	 * @param word word
	 * @param lines lines
	 * @return number of times word occured in lines
	 */
	public static int calculateWordOccurencesInLines(String word, List<String> lines) {
		Counter counter = new Counter();
		
		lines.forEach(line -> {
			Util.getWordsFromLine(line).forEach(docWord -> {
				if (docWord.equals(word)) {
					counter.increment();
				}
			});
		});
		
		return counter.getCounter();
	}
}
