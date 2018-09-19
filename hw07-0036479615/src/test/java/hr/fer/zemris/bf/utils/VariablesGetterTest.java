package hr.fer.zemris.bf.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.bf.parser.Parser;

public class VariablesGetterTest {

	@Test
	public void getterTestEmpty() {
		Parser p = new Parser("1 or true ANd false");
		VariablesGetter getter = new VariablesGetter();
		p.getExpression().accept(getter);
		
		assertEquals(0, getter.getVariables().size());
	}
	
	@Test
	public void getterTestOne() {
		Parser p = new Parser("a");
		VariablesGetter getter = new VariablesGetter();
		p.getExpression().accept(getter);
		
		assertArrayEquals(new String[]{"A"}, getter.getVariables().toArray(new String[getter.getVariables().size()]));
	}
	
	@Test
	public void getterTestComplex() {
		Parser p = new Parser("a or c and (đ) * ž :+:(erT and !mre or b)");
		VariablesGetter getter = new VariablesGetter();
		p.getExpression().accept(getter);
		
		assertArrayEquals(new String[]{"A", "B", "C", "ERT", "MRE", "Đ", "Ž"},
				getter.getVariables().toArray(new String[getter.getVariables().size()]));
	}
}
