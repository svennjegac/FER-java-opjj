package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class IFilterTest {

	public StudentDatabase init() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("src/main/resources/database.txt"), StandardCharsets.UTF_8 );
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("hm");
		}
		
		return new StudentDatabase(lines);
	}
	
	@Test
	public void filter() {
		StudentDatabase db = init();
		
		List<StudentRecord> l = db.filter(record -> record.getFirstName().equals("Ivan"));
		
		int i = 0;
		for (StudentRecord studentRecord : l) {
			if (!studentRecord.getFirstName().equals("Ivan")) {
				fail();
			}
			i++;
		}
		
		assertEquals(5, i);
	}
	
	@Test
	public void filterAll() {
		StudentDatabase db = init();
		
		List<StudentRecord> l = db.filter(record -> true);
		
		assertEquals(63, l.size());
	}
	
	@Test
	public void filterZero() {
		StudentDatabase db = init();
		
		List<StudentRecord> l = db.filter(record -> false);
		
		assertEquals(0, l.size());
	}

}
