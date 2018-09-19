package hr.fer.zemris.bf.utils;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import hr.fer.zemris.bf.parser.Parser;

public class ExpressionEvaluatorTest {

	@Test
	public void evaluateOne() {
		Parser p = new Parser("a");
		
		ExpressionEvaluator eval = new ExpressionEvaluator(Arrays.asList("A"));
		
		p.getExpression().accept(eval);
		
		assertEquals(false, eval.getResult());
	}
	
	@Test
	public void evaluateOneTrue() {
		Parser p = new Parser("a");
		
		ExpressionEvaluator eval = new ExpressionEvaluator(Arrays.asList("A"));
		eval.setValues(new boolean[]{true});
		
		p.getExpression().accept(eval);
		
		assertEquals(true, eval.getResult());
	}
	
	@Test(expected=IllegalStateException.class)
	public void emptyStack() {
		Parser p = new Parser("a");
		
		ExpressionEvaluator eval = new ExpressionEvaluator(Arrays.asList("A"));
		eval.setValues(new boolean[]{true});
		eval.getResult();
	}
	
	@Test(expected=IllegalStateException.class)
	public void lowercaseVar() {
		Parser p = new Parser("a");
		
		ExpressionEvaluator eval = new ExpressionEvaluator(Arrays.asList("a"));
		eval.setValues(new boolean[]{true});
		
		p.getExpression().accept(eval);
	}
	
	@Test(expected=IllegalStateException.class)
	public void undeclared() {
		Parser p = new Parser("a or b");
		
		ExpressionEvaluator eval = new ExpressionEvaluator(Arrays.asList("A"));
		eval.setValues(new boolean[]{true});
		
		p.getExpression().accept(eval);
		
		assertEquals(true, eval.getResult());
	}
	
	@Test
	public void moreDeclared() {
		Parser p = new Parser("a or b");
		
		ExpressionEvaluator eval = new ExpressionEvaluator(Arrays.asList("A", "B", "C"));
		eval.setValues(new boolean[]{true, false, false});
		
		p.getExpression().accept(eval);
		
		assertEquals(true, eval.getResult());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void differentNumberOfVariablesAndValues() {
		Parser p = new Parser("a or b");
		
		ExpressionEvaluator eval = new ExpressionEvaluator(Arrays.asList("A", "B", "C"));
		eval.setValues(new boolean[]{true, false});
		
		p.getExpression().accept(eval);
		
		assertEquals(true, eval.getResult());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void differentNumberOfVariablesAndValues2() {
		Parser p = new Parser("a or b");
		
		ExpressionEvaluator eval = new ExpressionEvaluator(Arrays.asList());
		eval.setValues(new boolean[]{true, false});
		
		p.getExpression().accept(eval);
		
		assertEquals(true, eval.getResult());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void differentNumberOfVariablesAndValues3() {
		Parser p = new Parser("a or b");
		
		ExpressionEvaluator eval = new ExpressionEvaluator(Arrays.asList("A"));
		eval.setValues(new boolean[]{});
		
		p.getExpression().accept(eval);
		
		assertEquals(true, eval.getResult());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void differentNumberOfVariablesAndValues4() {
		Parser p = new Parser("a or b");
		
		ExpressionEvaluator eval = new ExpressionEvaluator(Arrays.asList("A"));
		eval.setValues(null);
		
		p.getExpression().accept(eval);
		
		assertEquals(true, eval.getResult());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void evaluatorNull() {
		new ExpressionEvaluator(null);
	}
}
