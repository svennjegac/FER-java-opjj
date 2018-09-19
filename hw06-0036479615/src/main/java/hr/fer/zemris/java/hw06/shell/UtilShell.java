package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for easier implementation of shell.
 * It offers static methods for user.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class UtilShell {

	/**
	 * Method fetches arguments as list using arguments as a in line arguments.
	 * 
	 * @param env environment reference
	 * @param arguments in line arguments
	 * @return list of arguments
	 */
	public static List<String> fetchArguments(Environment env, String arguments) {
		try {
			return extractArguments(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage().toString());
			return null;
		}
	}
	
	/**
	 * Gets path from string representation.
	 * 
	 * @param env environment reference
	 * @param path string representation of path
	 * @return path
	 */
	public static Path getPath(Environment env, String path) {
		try {
			return Paths.get(path);
		} catch (Exception e) {
			env.writeln(e.getMessage().toString());
			return null;
		}
	}
	
	/**
	 * Extracts arguments as list from in line arguments.
	 * 
	 * @param argument in line arguments
	 * @return list of arguments
	 */
	public static List<String> extractArguments(String argument) {
		List<String> list = new ArrayList<>();
		argument = argument.trim();
		
		while (argument.length() > 0) {
			//if argument is in quotes
			if (argument.charAt(0) == '\"') {
				//separates quoted argument from rest of arguments
				String[] parts = convertToPathString(argument);
				
				list.add(parts[0]);
				argument = parts[1];
				
				//there is no more arguments
				if (argument.length() == 0) {
					break;
				}
				
				//there is more arguments
				//after quoted argument first char must be whitespace
				if (!Character.isWhitespace(argument.charAt(0))) {
					throw new IllegalArgumentException("After quotes parameter, whitespace must follow.");
				}
				
				argument = argument.trim();
				continue;
			} else {
				//argument is not quoted argument
				//read all to the first whitespace or end
				int index = 0;
				
				while (index < argument.length()) {
					if (Character.isWhitespace(argument.charAt(index))) {
						break;
					}
					
					index++;
				}
				
				String start = argument.substring(0, index);
				list.add(start);
				argument = argument.substring(start.length()).trim();
				continue;
			}
		}
		
		return list;
	}
	
	/**
	 * Method processes in line arguments.
	 * It is known that first argument is quoted argument and that
	 * first char is quotes.
	 * It returns array of two parameters.
	 * First parameter is what is extracted from quoted argument and second parameter is
	 * rest of in line arguments.
	 * 
	 * @param path in line arguments
	 * @return array consisting first argument and rest of in line arguments
	 */
	private static String[] convertToPathString(String path) {
		StringBuilder sb = new StringBuilder(path);
	
		int index = 1;
		boolean endOfPathReached = false;
		
		while (index < sb.length()) {
			if (sb.charAt(index) == '\"') {
				endOfPathReached = true;
				break;
			}
			
			if (sb.charAt(index) == '\\') {
				if (index < sb.length() - 1) {
					char afterC = sb.charAt(index + 1);
					
					if (afterC == '\\' || afterC == '\"') {
						sb.deleteCharAt(index);
					}
				}
			}
			
			index++;
		}
		
		if (endOfPathReached) {
			return new String[] {sb.substring(1, index), sb.substring(index + 1, sb.length())};
		}
		
		throw new IllegalArgumentException("Path not properly provided.");
	}
}
