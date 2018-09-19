package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class IFieldValueGetterTest {

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
	public void firstName() {
		IFieldValueGetter f = FieldValueGetters.FIRST_NAME;
		
		StudentDatabase db = init();
		StudentRecord r = db.forJMBAG("0000000034");
		
		assertEquals(r.getFirstName(), f.get(r));
	}

	@Test
	public void lastName() {
		IFieldValueGetter f = FieldValueGetters.LAST_NAME;
		
		StudentDatabase db = init();
		StudentRecord r = db.forJMBAG("0000000034");
		
		assertEquals(r.getLastName(), f.get(r));
	}
	
	@Test
	public void jmbag() {
		IFieldValueGetter f = FieldValueGetters.JMBAG;
		
		StudentDatabase db = init();
		StudentRecord r = db.forJMBAG("0000000034");
		
		assertEquals(r.getJmbag(), f.get(r));
	}	
}
