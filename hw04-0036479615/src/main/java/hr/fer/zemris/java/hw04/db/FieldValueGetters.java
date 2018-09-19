package hr.fer.zemris.java.hw04.db;

/**
 * Class having three different implementations of
 * IFieldValueGetter interface.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class FieldValueGetters {
	
	/** Getter for first name. */
	public static final IFieldValueGetter FIRST_NAME = record -> record.getFirstName();
	
	/** Getter for last name. */
	public static final IFieldValueGetter LAST_NAME = record -> record.getLastName();
	
	/** Getter for JMBAG. */
	public static final IFieldValueGetter JMBAG = record -> record.getJmbag();
	
	/** Getter for grade. */
	public static final IFieldValueGetter GRADE = record -> Integer.toString(record.getGrade());
}
