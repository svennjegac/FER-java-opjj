package hr.fer.zemris.java.hw05.demo4;

/**
 * Class representing a single student record.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class StudentRecord {

	/** Students JMBAG. */
	private String jmbag;
	/** Students last name. */
	private String lastName;
	/** Students first name. */
	private String firstName;
	/** Students points on MI. */
	private double miPoints;
	/** Students points on ZI. */
	private double ziPoints;
	/** Students points on laboratory. */
	private double labPoints;
	/** Students final grade. */
	private int grade;
	
	/** Delimiter which separates student parameters in a String line. */
	private static final String DELIMITER = "\t";
	
	/**
	 * Constructor which accepts students parameters and instantiates
	 * new StudentRecord.
	 * 
	 * @param jmbag students JMBAG
	 * @param lastName students last name
	 * @param firstName students first name
	 * @param miPoints students points on MI
	 * @param ziPoints students points on ZI
	 * @param labPoints students points on laboratory
	 * @param grade students final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double miPoints, double ziPoints,
			double labPoints, int grade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.miPoints = miPoints;
		this.ziPoints = ziPoints;
		this.labPoints = labPoints;
		this.grade = grade;
	}
	
	/**
	 * Gets students JMBAG.
	 * 
	 * @return students JMBAG
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * Gets students final grade.
	 * 
	 * @return students final grade
	 */
	public int getGrade() {
		return grade;
	}
	
	/**
	 * Gets students total points.
	 * Sum of MI, ZI and laboratory points.
	 * 
	 * @return students total points
	 */
	public double totalPoints() {
		return miPoints + ziPoints + labPoints;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s %s %f %f %f %d", jmbag, lastName, firstName, miPoints, ziPoints, labPoints, grade);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	/**
	 * Makes student record from String line.
	 * Parameters in line must be separated by DELIMITER.
	 * 
	 * @param row String row representing record
	 * @return new StudentRecord
	 */
	public static StudentRecord makeStudentRecord(String row) {
		if (row == null) {
			throw new IllegalArgumentException("Row can not be null.");
		}
		
		String[] args = row.split(DELIMITER);
		
		if (args.length != 7) {
			throw new IllegalArgumentException("Too much parameters in single line; was: " + row);
		}
		
		try {
			return new StudentRecord(
					args[0].trim(),
					args[1].trim(),
					args[2].trim(),
					Double.parseDouble(args[3]),
					Double.parseDouble(args[4]),
					Double.parseDouble(args[5]),
					Integer.parseInt(args[6])
			);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Invalid numbers provided; were: " + row);
		}
	}
}
