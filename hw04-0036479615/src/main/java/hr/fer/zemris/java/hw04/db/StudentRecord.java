package hr.fer.zemris.java.hw04.db;

import java.util.Objects;

/**
 * Class representing a single student record.
 * It contains student name, last name, JMBAG and grade.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class StudentRecord {
	
	/** Students JMBAG. */
	private String jmbag;
	/** Students last name. */
	private String lastName;
	/** Students first name */
	private String firstName;
	/** Students grade. */
	private int grade;
	
	/** Indicates next field in a row. */
	private static final String DELIMITER = "\t";
	
	/**
	 * Constructor of student record.
	 * It accepts all of students attributes and creates
	 * a new record.
	 * 
	 * @param jmbag students JMBAG
	 * @param lastName students last name
	 * @param firstName students first name
	 * @param grade students grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int grade) {
		if (jmbag == null || lastName == null || firstName == null) {
			throw new IllegalArgumentException("Student fields can not be null.");
		}
		
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.grade = grade;
	}
	
	/**
	 * Returns students JMBAG.
	 * 
	 * @return students JMBAG
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * Returns students last name.
	 * 
	 * @return students last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Returns students first name.
	 * 
	 * @return students first name.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Returns students grade.
	 * 
	 * @return students grade
	 */
	public int getGrade() {
		return grade;
	}
	
	/**
	 * Returns true if students have equal JMBAG.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof StudentRecord)) {
			return false;
		}
		
		StudentRecord record = (StudentRecord) obj;
		
		return Objects.equals(this.jmbag, record.getJmbag());
	}
	
	/**
	 * Calculates hash code only considering JMBAG.
	 */
	@Override
	public int hashCode() {
		return ((jmbag == null) ? 0 : jmbag.hashCode());
	}
	
	/**
	 * Creates student record using String row.
	 * 
	 * @param row String representing a record
	 * @return new student record
	 */
	public static StudentRecord makeStudentRecord(String row) {
		if (row == null) {
			throw new IllegalArgumentException("String used for making record can not be null.");
		}
		
		String[] args = row.split(DELIMITER);
		
		if (args.length != 4) {
			StringBuilder sb = new StringBuilder("Wrong number of elements in a database row.\nWere: ");
			
			for (String string : args) {
				sb.append(string + ", ");
			}
			
			throw new IllegalArgumentException(sb.toString());
		}
		
		String jmbag = args[0].trim();
		String lastName = args[1].trim();
		String firstName = args[2].trim();
		int grade = 0;
		try {
			grade = Integer.parseInt(args[3].trim());
		} catch (Exception e) {
			throw new IllegalArgumentException("Can not assign non integer as grade; was" + args[3].trim());
		}
		
		return new StudentRecord(jmbag, lastName, firstName, grade);
	}
}
