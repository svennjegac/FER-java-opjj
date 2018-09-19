package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class IComparisonOperatorTest {

	@Test
	public void testLess() {
		IComparisonOperator less = ComparisonOperators.LESS;
		
		assertTrue(less.satisfied("ivor", "sven"));
		assertTrue(less.satisfied("ivoreufeuwof", "sven"));
		assertTrue(less.satisfied("sven", "svena"));
		assertFalse(less.satisfied("sven", "sven"));
		assertFalse(less.satisfied("v", "sven"));
		assertFalse(less.satisfied("", ""));
		assertTrue(less.satisfied("", "a"));
	}
	
	@Test
	public void testLessEq() {
		IComparisonOperator lessEq = ComparisonOperators.LESS_OR_EQUALS;
		
		assertTrue(lessEq.satisfied("ivor", "sven"));
		assertTrue(lessEq.satisfied("ivoreufeuwof", "sven"));
		assertTrue(lessEq.satisfied("sven", "svena"));
		assertTrue(lessEq.satisfied("sven", "sven"));
		assertFalse(lessEq.satisfied("v", "sven"));
		assertTrue(lessEq.satisfied("", ""));
	}
	
	@Test
	public void testGreater() {
		IComparisonOperator gr = ComparisonOperators.GREATER;
		
		assertFalse(gr.satisfied("ivor", "sven"));
		assertFalse(gr.satisfied("ivoreufeuwof", "sven"));
		assertFalse(gr.satisfied("sven", "svena"));
		assertFalse(gr.satisfied("sven", "sven"));
		assertTrue(gr.satisfied("v", "sven"));
		assertFalse(gr.satisfied("", ""));
		assertFalse(gr.satisfied("", "a"));
	}
	
	@Test
	public void testGreaterEq() {
		IComparisonOperator grEq = ComparisonOperators.GREATER_OR_EQUALS;
		
		assertFalse(grEq.satisfied("ivor", "sven"));
		assertFalse(grEq.satisfied("ivoreufeuwof", "sven"));
		assertFalse(grEq.satisfied("sven", "svena"));
		assertTrue(grEq.satisfied("sven", "sven"));
		assertTrue(grEq.satisfied("v", "sven"));
		assertTrue(grEq.satisfied("", ""));
		assertFalse(grEq.satisfied("", "a"));
	}
	
	@Test
	public void testEq() {
		IComparisonOperator eq = ComparisonOperators.EQUALS;
		
		assertFalse(eq.satisfied("ivor", "sven"));
		assertFalse(eq.satisfied("ivoreufeuwof", "sven"));
		assertFalse(eq.satisfied("sven", "svena"));
		assertTrue(eq.satisfied("sven", "sven"));
		assertFalse(eq.satisfied("v", "sven"));
		assertTrue(eq.satisfied("", ""));
		assertFalse(eq.satisfied("", "a"));
	}
	
	@Test
	public void testNotEq() {
		IComparisonOperator neq = ComparisonOperators.NOT_EQUALS;
		
		assertTrue(neq.satisfied("ivor", "sven"));
		assertTrue(neq.satisfied("ivoreufeuwof", "sven"));
		assertTrue(neq.satisfied("sven", "svena"));
		assertFalse(neq.satisfied("sven", "sven"));
		assertTrue(neq.satisfied("v", "sven"));
		assertFalse(neq.satisfied("", ""));
		assertTrue(neq.satisfied("", "a"));
	}
	
	@Test
	public void testLike() {
		IComparisonOperator l = ComparisonOperators.LIKE;
		
		assertTrue(l.satisfied("sven", "sv*en"));
		assertTrue(l.satisfied("sveuobdovbwoen", "sv*en"));
		assertTrue(l.satisfied("AAAA", "AA*AA"));
		assertFalse(l.satisfied("AAA", "AA*AA"));
		assertTrue(l.satisfied("AAAA", "*AAAA"));
		assertTrue(l.satisfied("AAAA", "AAAA*"));
		assertTrue(l.satisfied("AAAA", "A*AAA"));
		assertTrue(l.satisfied("AobfebfAAA", "A*"));
		assertTrue(l.satisfied("AApihfi23f32ibfAA", "*"));
		assertTrue(l.satisfied("AAA80hff0283f23A", "*A"));
		assertTrue(l.satisfied("AA*AA", "AA*AA"));
		assertFalse(l.satisfied("A*A", "AA*AA"));
		assertTrue(l.satisfied("asa*dhddhd", "asa*d"));
		assertFalse(l.satisfied("AA", "AAaaaa*aaaaaAA"));
		assertFalse(l.satisfied("AAAA", "AA*AAaaaaaaaaaaaaaa"));
		assertTrue(l.satisfied("aaa", "aaa"));
		assertFalse(l.satisfied("aaa", ""));
		assertTrue(l.satisfied("", ""));
		assertTrue(l.satisfied("", "*"));
	}
}
