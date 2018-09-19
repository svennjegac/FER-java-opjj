package hr.fer.zemris.java.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Program simulates usage of database.
 * It gives user option to write queries from command line
 * and executes them.
 * 
 * If query is direct it will be executed in O(1) complexity.
 * 
 * Program ends when user enters 'exit'.
 * 
 * Command line arguments are not used in this program.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class StudentDB {

	/** Command delimiter. */
	private static String COMMAND = "query";
	
	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("src/main/resources/database.txt"), StandardCharsets.UTF_8 );
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			System.exit(-1);
		}
		
		StudentDatabase db = null;
		
		try {
			db = new StudentDatabase(lines);
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
			System.exit(-1);
		} catch (Exception ex) {
			System.out.println("Something went wrong.");
			System.exit(-1);
		}
		
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.print("> ");
			
			String query = sc.nextLine().trim();
			
			if (query.equals("exit")) {
				System.out.println("Goodbye!");
				sc.close();
				System.exit(0);
			}
			
			if (!checkCommand(query)) {
				System.out.println("Command not recognized");
				continue;
			}
			
			query = query.substring(COMMAND.length(), query.length());
			
			QueryParser parser;
			
			try {
				parser = new QueryParser(query);
			} catch (QueryParserException ex) {
				System.out.println(ex.getMessage().toString());
				continue;
			} catch (Exception ex) {
				System.out.println("Something went wrong.");
				continue;
			}
			
			List<StudentRecord> records = fetchFromDB(parser, db);
			
			if (parser.isDirectQuery()) {
				System.out.println("Using index for record retrieval.");
			}
			
			printRecords(records);
		}
	}
	
	/**
	 * Prints fetched records from database.
	 * 
	 * @param records fetched records
	 */
	private static void printRecords(List<StudentRecord> records) {
		String output = buildOutput(records);
		
		if (output.length() > 0) {
			System.out.print(output);
		}
		
		System.out.println("Records selected: " + records.size() + "\n");
	}
	
	/**
	 * Builds pretty formatted output of database records.
	 * 
	 * @param list list of records
	 * @return pretty output
	 */
	private static String buildOutput(List<StudentRecord> list) {
		if (list.isEmpty()) {
			return "";
		}
		
		List<Integer> longestEntries = new ArrayList<>();
		longestEntries.add(getLongestEntry(list, FieldValueGetters.JMBAG));
		longestEntries.add(getLongestEntry(list, FieldValueGetters.LAST_NAME));
		longestEntries.add(getLongestEntry(list, FieldValueGetters.FIRST_NAME));
		longestEntries.add(getLongestEntry(list, FieldValueGetters.GRADE));
		
		List<IFieldValueGetter> listGetters = new ArrayList<>();
		listGetters.add(FieldValueGetters.JMBAG);
		listGetters.add(FieldValueGetters.LAST_NAME);
		listGetters.add(FieldValueGetters.FIRST_NAME);
		listGetters.add(FieldValueGetters.GRADE);
		
		String border = buildBorder(longestEntries);
		
		StringBuilder output = new StringBuilder(border);
		
		for (StudentRecord studentRecord : list) {
			output.append(buildSingleRecord(studentRecord, longestEntries, listGetters));
		}
		
		output.append(border);
		
		return output.toString();
	}
	
	/**
	 * Method builds pretty formatted output for single record.
	 * 
	 * @param record StudentRecord
	 * @param longestEntries list of longest entries for each parameter
	 * @param fieldGetters list of field getters
	 * @return pretty output for single student record
	 */
	private static String buildSingleRecord(StudentRecord record, List<Integer> longestEntries,
			List<IFieldValueGetter> fieldGetters) {
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < fieldGetters.size(); i++) {
			int max = longestEntries.get(i);
			sb.append("| ");
			String field = fieldGetters.get(i).get(record);
			
			for (int j = 0; j < max + 1; j++) {
				if (field.length() > j) {
					sb.append(field.charAt(j));
				} else {
					sb.append(" ");
				}
			}
		}
		
		sb.append("|\n");
		
		return sb.toString();
	}
	
	/**
	 * Builds border depending on number of entries and length of
	 * provided entries.
	 * 
	 * @param longestEntries list of longest entries for each field
	 * @return pretty border output
	 */
	private static String buildBorder(List<Integer> longestEntries) {
		if (longestEntries == null || longestEntries.isEmpty()) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (Integer integer : longestEntries) {
			sb.append("+");
			for (int i = 0;  i < integer + 2; i++) {
				sb.append("=");
			}
		}
		
		sb.append("+\n");
		
		return sb.toString();
	}
	
	/**
	 * For list of students determines length of longest attribute from
	 * all records. Attribute is defined by IFieldValueGetter.
	 * 
	 * @param list list of student records
	 * @param fieldGetter field getter
	 * @return longest field of all students
	 */
	private static int getLongestEntry(List<StudentRecord> list, IFieldValueGetter fieldGetter) {
		if (list == null || list.isEmpty()) {
			throw new IllegalArgumentException("List must not be null and must have at least one entry.");
		}
		
		int max = fieldGetter.get(list.get(0)).length();
		
		for (StudentRecord studentRecord : list) {
			if (fieldGetter.get(studentRecord).length() > max) {
				max = fieldGetter.get(studentRecord).length();
			}
		}
		
		return max;
	}
	
	/**
	 * Fetches records form database. Direct queries are
	 * executed in complexity of O(1).
	 * 
	 * @param parser QueryParser
	 * @param db StudentDatabase
	 * @return list of fetched records
	 */
	private static List<StudentRecord> fetchFromDB(QueryParser parser, StudentDatabase db) {
		List<StudentRecord> records = new ArrayList<>();
		
		if (parser.isDirectQuery()) {
			StudentRecord record = db.forJMBAG(parser.getQueriedJMBAG());
			
			if (record != null) {
				records.add(record);
			}
			
		} else {
			for (StudentRecord record : db.filter(new QueryFilter(parser.getQuery()))) {
				records.add(record);
			}
		}
		
		return records;
	}
	
	/**
	 * Method checks if query starts with valid command.
	 * 
	 * @param query query to be checked
	 * @return <code>true</code> if query starts with valid command,
	 * 			<code>false</code> otherwise
	 */
	private static boolean checkCommand(String query) {
		if (query.length() <= COMMAND.length()) {
			return false;
		}
		
		if (!query.startsWith(COMMAND)) {
			return false;
		}
		
		query = query.substring(COMMAND.length(), query.length());
		
		if (!Character.isWhitespace(query.charAt(0))) {
			return false;
		}
		
		return true;
	}
}
