package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw04.collections.SimpleHashtable;

/**
 * Class representing a database of student records.
 * It accepts a list of String rows and makes database.
 * It stores student records in database in two types of collections.
 * Students are stored in a list and in a hash table which gives user
 * opportunity to fetch a single record with complexity of O(1);
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class StudentDatabase {

	/** List of student records. */
	private List<StudentRecord> studentList;
	/** Hash table of student records. */
	private SimpleHashtable<String, StudentRecord> studentHashtable;
	
	/**
	 * Student database constructor.
	 * It accepts list of Strings and makes database.
	 * 
	 * @param list list of String rows, each representing a single student record
	 */
	public StudentDatabase(List<String> list) {
		if (list == null) {
			throw new IllegalArgumentException("Can not instantiate DB with null list.");
		}
		
		studentList = new ArrayList<>();
		studentHashtable = new SimpleHashtable<>();
		
		retrieveStudents(list);
	}
	
	/**
	 * Method fetches record by JMBAG with
	 * complexity O(1).
	 * 
	 * @param jmbag searched JMBAG
	 * @return Student record having provided JMBAG or null
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return studentHashtable.get(jmbag);
	}
	
	/**
	 * Method accepts implementation of IFilter interface,
	 * iterates through every record in database and returns a
	 * list of records which accomplished IFilters condition.
	 * 
	 * @param filter interface having a condition which determines
	 * 					whether record will be added to list or not
	 * @return list of records who accomplished a IFilters condition
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temporaryList = new ArrayList<>();
		
		for (StudentRecord r : studentList) {
			if (filter.accepts(r)) {
				temporaryList.add(r);
			}
		}
		
		return temporaryList;
	}
	
	/**
	 * Method fills database list and hash table.
	 * 
	 * @param list list of String rows representing records
	 */
	private void retrieveStudents(List<String> list) {
		for (String row : list) {
			putRowInDB(row);
		}
	}
	
	/**
	 * Method puts single String row record in database.
	 * 
	 * @param row String representing a record
	 */
	private void putRowInDB(String row) {
		if (row == null) {
			throw new IllegalArgumentException("Database row can not be null.");
		}
		
		StudentRecord studentRecord = StudentRecord.makeStudentRecord(row);
		
		if (studentHashtable.containsKey(studentRecord.getJmbag())) {
			throw new IllegalArgumentException("JMBAG duplicate occured.");
		}
		
		studentList.add(studentRecord);
		studentHashtable.put(studentRecord.getJmbag(), studentRecord);		
	}
}
