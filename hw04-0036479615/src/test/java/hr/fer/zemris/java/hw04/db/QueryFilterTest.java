package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class QueryFilterTest {

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
	public void test() {
		StudentDatabase db = init();
		
		QueryParser p = new QueryParser("firstName LIKE \"M*\" AnD lastName LIKE \"*ić\"");
		
		int i = 0;
		for (StudentRecord r : db.filter(new QueryFilter(p.getQuery()))) {
			i++;
		}
		
		assertEquals(5, i);
	}
	
	@Test
	public void test2() {
		StudentDatabase db = init();
		
		QueryParser p = new QueryParser("firstName LIKE \"M*\" AnD jmbag > \"0000000017\"");
		
		int i = 0;
		for (StudentRecord r : db.filter(new QueryFilter(p.getQuery()))) {
			i++;
		}
		
		assertEquals(2, i);
	}
	
	@Test
	public void test3() {
		StudentDatabase db = init();
		
		QueryParser p = new QueryParser(" jmbag > \"0000000017\"");
		
		int i = 0;
		for (StudentRecord r : db.filter(new QueryFilter(p.getQuery()))) {
			i++;
		}
		
		assertEquals(46, i);
	}
	
	@Test
	public void test4() {
		StudentDatabase db = init();
		
		QueryParser p = new QueryParser(" jmbag > \"0000000017\" and jmbag <= \"0000000022\"");
		
		int i = 0;
		for (StudentRecord r : db.filter(new QueryFilter(p.getQuery()))) {
			i++;
		}
		
		assertEquals(5, i);
	}

}
