package hr.fer.zemris.java.hw05.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class demonstrating usage of streams in Java.
 * Class reads a database of students from text file
 * and then performs different queries using streams.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class StudentDemo {

	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("src/main/resources/studenti.txt"), StandardCharsets.UTF_8 );
		} catch (IOException e) {
			System.out.println("Database text file could not be read;");
			System.exit(-1);
		}
		
		List<StudentRecord> records = null;
		try {
			records = convert(lines);
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
			System.exit(-2);
		}
		
		long broj = vratiBodovaViseOd25(records);
		System.out.println("Više od 25: " + broj);
		
		long broj5 = vratiBrojOdlikasa(records);
		System.out.println("Broj odlikaša: " + broj5);
		
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		System.out.println("Odlikaši:");
		for (StudentRecord studentRecord : odlikasi) {
			System.out.println(studentRecord);
		}
		
		List<StudentRecord> odlikasiSrotirano = vratiSortiranuListuOdlikasa(records);
		System.out.println("Odlikaši sortirani po br bod:");
		for (StudentRecord studentRecord : odlikasiSrotirano) {
			System.out.println(studentRecord);
		}
		
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		System.out.println("Nepoloženi JMBAG-ovi:");
		for (String string : nepolozeniJMBAGovi) {
			System.out.println(string);
		}
		
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		System.out.println("Razvrstaj studente po ocjenama:");
		for (int i = 1; i <= 5; i++) {
			System.out.println("Ocjena: " + i);
			for (StudentRecord r : mapaPoOcjenama.get(i)) {
				System.out.println(r);
			}
		}
		
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		System.out.println("Broj studenata po ocjenama:");
		for (int i = 1; i <= 5; i++) {
			System.out.println(mapaPoOcjenama2.get(i) + " studenata s ocjenom " + i + ".");
		}
		
		
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		System.out.println("Prolaz:");
		for (StudentRecord r : prolazNeprolaz.get(true)) {
			System.out.println(r);
		}
		
		System.out.println("Neprolaz:");
		for (StudentRecord r : prolazNeprolaz.get(false)) {
			System.out.println(r);
		}
	}
	
	/**
	 * Method returns number of students who had more
	 * than 25 points.
	 * 
	 * @param list list of student records
	 * @return number of students who had more than 25 points
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> list) {
		return list
				.stream()
				.filter(record -> record.totalPoints() > 25)
				.collect(Collectors.counting());
	}
	
	/**
	 * Method returns number of students who had excellent grade.
	 * 
	 * @param list list of student records
	 * @return number of students who had excellent grade
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> list) {
		return list
				.stream()
				.filter(record -> record.getGrade() == 5)
				.collect(Collectors.counting());
	}
	
	/**
	 * Method returns list of students who had excellent grade.
	 * 
	 * @param list list of student records
	 * @return list of students who had excellent grade
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> list) {
		return list
				.stream()
				.filter(record -> record.getGrade() == 5)
				.collect(Collectors.toList());
	}
	
	/**
	 * Method returns sorted list of students who had excellent grade.
	 * 
	 * @param list list of student records
	 * @return sorted list of students who had excellent grade
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> list) {
		return list
				.stream()
				.filter(record -> record.getGrade() == 5)
				.sorted((o1, o2) -> {
					if (o1.totalPoints() > o2.totalPoints()) return -1;
					if (o1.totalPoints() < o2.totalPoints()) return 1;
					return 0;
				})
				.collect(Collectors.toList());
	}
	
	/**
	 * Method returns sorted list of JMBAGs who failed subject.
	 * 
	 * @param list list of student records
	 * @return sorted list of JMBAGs who failed subject
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> list) {
		return list
				.stream()
				.filter(record -> record.getGrade() == 1)
				.map(StudentRecord::getJmbag)
				.sorted()
				.collect(Collectors.toList());
	}
	
	/**
	 * Method returns map of key(grade), value(list of students who had dedicated grade).
	 * 
	 * @param list list of student records
	 * @return map of key(grade), value(list of students who had dedicated grade)
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> list) {
		return list
				.stream()
				.collect(Collectors.groupingBy(StudentRecord::getGrade));
	}
	
	/**
	 * Method returns map of key(grade), value(number of students who had dedicated grade).
	 * 
	 * @param list list of student records
	 * @return map of key(grade), value(number of students who had dedicated grade)
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> list) {
		return list
				.stream()
				.collect(Collectors.toMap(
							StudentRecord::getGrade,
							record -> 1,
							(x, y) -> x + 1
				));
	}
	
	/**
	 * Method returns map of key(true/false - passed/failed),
	 * value(list of students who passed/failed).
	 * 
	 * @param list list of student records
	 * @return map of key(true/false - passed/failed), value(list of students who passed/failed)
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> list) {
		return list
				.stream()
				.collect(Collectors.partitioningBy(record -> record.getGrade() > 1));
	}
	
	/**
	 * Converts list of Strings to list of StudentRecords.
	 * 
	 * @param lines list of Strings
	 * @return list of StudentRecords
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		if (lines == null) {
			throw new IllegalArgumentException("Lines could not be null.");
		}
		
		List<StudentRecord> outputList = new ArrayList<>();
		
		for (String row : lines) {
			putInList(row, outputList);
		}
		
		return outputList;
	}
	
	/**
	 * Converts single String row to StudentRecord
	 * and puts it to record list.
	 * 
	 * @param row String row
	 * @param outputList list of StudentRecords
	 */
	private static void putInList(String row, List<StudentRecord> outputList) {
		if (row.length() == 0) {
			return;
		}
		
		StudentRecord record = StudentRecord.makeStudentRecord(row);
		
		if (outputList.contains(record)) {
			throw new IllegalArgumentException("Record with same jmbag already exists; was: " + record.getJmbag());
		}
		
		outputList.add(record);
	}
}
