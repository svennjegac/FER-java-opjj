package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class ConditionalExpressionTest {

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
	public void firstNameLike() {
		StudentDatabase db = init();
		
		List<StudentRecord> l = db.filter(record -> {
			ConditionalExpression expr = new ConditionalExpression(
					FieldValueGetters.FIRST_NAME,
					"*na",
					ComparisonOperators.LIKE
			);
			
			return expr.getComparisonOperator().satisfied(
					expr.getFieldGetter().get(record), 
					expr.getStringLiteral()
			);
		});
		
		assertEquals(4, l.size());
	}
	
	@Test
	public void lastNameLike() {
		StudentDatabase db = init();
		
		List<StudentRecord> l = db.filter(record -> {
			ConditionalExpression expr = new ConditionalExpression(
					FieldValueGetters.LAST_NAME,
					"*se",
					ComparisonOperators.LIKE
			);
			
			return expr.getComparisonOperator().satisfied(
					expr.getFieldGetter().get(record), 
					expr.getStringLiteral()
			);
		});
		
		assertEquals(0, l.size());
	}
	
	@Test
	public void lastNameLike2() {
		StudentDatabase db = init();
		
		List<StudentRecord> l = db.filter(record -> {
			ConditionalExpression expr = new ConditionalExpression(
					FieldValueGetters.LAST_NAME,
					"M*",
					ComparisonOperators.LIKE
			);
			
			return expr.getComparisonOperator().satisfied(
					expr.getFieldGetter().get(record), 
					expr.getStringLiteral()
			);
		});
		
		assertEquals(8, l.size());
	}
	
	@Test
	public void lastNameLessEq2() {
		StudentDatabase db = init();
		
		List<StudentRecord> l = db.filter(record -> {
			ConditionalExpression expr = new ConditionalExpression(
					FieldValueGetters.LAST_NAME,
					"Cvrlje",
					ComparisonOperators.LESS_OR_EQUALS
			);
			
			return expr.getComparisonOperator().satisfied(
					expr.getFieldGetter().get(record), 
					expr.getStringLiteral()
			);
		});
		
		assertEquals(6, l.size());
	}
	
	@Test
	public void lastNameLess() {
		StudentDatabase db = init();
		
		List<StudentRecord> l = db.filter(record -> {
			ConditionalExpression expr = new ConditionalExpression(
					FieldValueGetters.JMBAG,
					"0000000012",
					ComparisonOperators.LESS
			);
			
			return expr.getComparisonOperator().satisfied(
					expr.getFieldGetter().get(record), 
					expr.getStringLiteral()
			);
		});
		
		assertEquals(11, l.size());
	}
	
	@Test
	public void jmbagNameGr() {
		StudentDatabase db = init();
		
		List<StudentRecord> l = db.filter(record -> {
			ConditionalExpression expr = new ConditionalExpression(
					FieldValueGetters.JMBAG,
					"0000000012",
					ComparisonOperators.GREATER_OR_EQUALS
			);
			
			return expr.getComparisonOperator().satisfied(
					expr.getFieldGetter().get(record), 
					expr.getStringLiteral()
			);
		});
		
		assertEquals(52, l.size());
	}
	
	@Test
	public void jmbagNameGrEq2() {
		StudentDatabase db = init();
		
		List<StudentRecord> l = db.filter(record -> {
			ConditionalExpression expr = new ConditionalExpression(
					FieldValueGetters.FIRST_NAME,
					"Miiixa",
					ComparisonOperators.GREATER
			);
			
			return expr.getComparisonOperator().satisfied(
					expr.getFieldGetter().get(record), 
					expr.getStringLiteral()
			);
		});
		
		assertEquals(20, l.size());
	}
	
	@Test
	public void jmbagNameNEq2() {
		StudentDatabase db = init();
		
		List<StudentRecord> l = db.filter(record -> {
			ConditionalExpression expr = new ConditionalExpression(
					FieldValueGetters.FIRST_NAME,
					"",
					ComparisonOperators.NOT_EQUALS
			);
			
			return expr.getComparisonOperator().satisfied(
					expr.getFieldGetter().get(record), 
					expr.getStringLiteral()
			);
		});
		
		assertEquals(63, l.size());
	}
	
	@Test
	public void jmbagNameNEq3() {
		StudentDatabase db = init();
		
		List<StudentRecord> l = db.filter(record -> {
			ConditionalExpression expr = new ConditionalExpression(
					FieldValueGetters.FIRST_NAME,
					"Ivan",
					ComparisonOperators.NOT_EQUALS
			);
			
			return expr.getComparisonOperator().satisfied(
					expr.getFieldGetter().get(record), 
					expr.getStringLiteral()
			);
		});
		
		assertEquals(58, l.size());
	}
	
	@Test
	public void jmbagNameEq() {
		StudentDatabase db = init();
		
		List<StudentRecord> l = db.filter(record -> {
			ConditionalExpression expr = new ConditionalExpression(
					FieldValueGetters.FIRST_NAME,
					"Maja",
					ComparisonOperators.EQUALS
			);
			
			return expr.getComparisonOperator().satisfied(
					expr.getFieldGetter().get(record), 
					expr.getStringLiteral()
			);
		});
		
		assertEquals(0, l.size());
	}
	
	@Test
	public void jmbagNameEq2() {
		StudentDatabase db = init();
		
		List<StudentRecord> l = db.filter(record -> {
			ConditionalExpression expr = new ConditionalExpression(
					FieldValueGetters.FIRST_NAME,
					"Ante",
					ComparisonOperators.EQUALS
			);
			
			return expr.getComparisonOperator().satisfied(
					expr.getFieldGetter().get(record), 
					expr.getStringLiteral()
			);
		});
		
		assertEquals(1, l.size());
	}

}
