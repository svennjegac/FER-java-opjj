package hr.fer.zemris.bf.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParserTest {
	
	@Test(expected=ParserException.class)
	public void crash1() {
		new Parser("a and b!");
	}
	
	@Test(expected=ParserException.class)
	public void crash2() {
		new Parser("a and b)");
	}
	
	@Test(expected=ParserException.class)
	public void crash3() {
		new Parser("a and b(");
	}
	
	@Test(expected=ParserException.class)
	public void crash4() {
		new Parser("a(");
	}
	
	@Test(expected=ParserException.class)
	public void crash5() {
		new Parser("a)");
	}
	
	@Test(expected=ParserException.class)
	public void crash6() {
		new Parser("(a and b");
	}
	
	@Test(expected=ParserException.class)
	public void crash7() {
		new Parser(")a and b!");
	}
	
	@Test(expected=ParserException.class)
	public void crash8() {
		new Parser("a and (b");
	}
	
	@Test(expected=ParserException.class)
	public void crash9() {
		new Parser("a and )b");
	}
	
	@Test(expected=ParserException.class)
	public void crash10() {
		new Parser("");
	}
	
	@Test(expected=ParserException.class)
	public void crash11() {
		new Parser("()");
	}
	
	@Test(expected=ParserException.class)
	public void crash12() {
		new Parser("(");
	}
	
	@Test(expected=ParserException.class)
	public void crash13() {
		new Parser(")");
	}
	
	@Test(expected=ParserException.class)
	public void crash14() {
		new Parser("!");
	}
	
	@Test(expected=ParserException.class)
	public void crash15() {
		new Parser("or a");
	}
	
	@Test(expected=ParserException.class)
	public void crash16() {
		new Parser("a!");
	}
	
	@Test(expected=ParserException.class)
	public void crash17() {
		new Parser("or");
	}
}
