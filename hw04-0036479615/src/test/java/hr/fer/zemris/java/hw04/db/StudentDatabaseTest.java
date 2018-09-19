package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudentDatabaseTest {

	public StudentDatabase init() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("src/main/resources/database.txt"), StandardCharsets.UTF_8 );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new StudentDatabase(lines);
	}
	
	@Test
	public void forJmbag() {
		StudentDatabase db = init();
		
		assertEquals("0000000021", db.forJMBAG("0000000021").getJmbag());
		assertEquals("Antonija", db.forJMBAG("0000000021").getFirstName());
	}
	
	@Test
	public void forJmbag2() {
		StudentDatabase db = init();
		
		assertNull(db.forJMBAG("33333333333333333333333333333333333333333333333333333333333333333333333333333"));
	}
	
	@Test
	public void forJmbag3() {
		StudentDatabase db = init();
		
		assertNull(db.forJMBAG(""));
	}
}
