package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class QueryParserTest {

	@Test
	public void testSimpleEq() {
		QueryParser p = new QueryParser("jmbag = \"0000000012\"");
		
		List<ConditionalExpression> l = p.getQuery();
		
		checkExp(l.get(0), FieldValueGetters.JMBAG, ComparisonOperators.EQUALS, "0000000012");
		
		assertTrue(p.isDirectQuery());
		assertEquals("0000000012", p.getQueriedJMBAG());
		
	}
	
	@Test
	public void testTwoLess() {
		QueryParser p = new QueryParser("jmbag = \"0000000012\" aND lastName < \"pppaaa\"");
		
		List<ConditionalExpression> l = p.getQuery();
		
		checkExp(l.get(0), FieldValueGetters.JMBAG, ComparisonOperators.EQUALS, "0000000012");
		checkExp(l.get(1), FieldValueGetters.LAST_NAME, ComparisonOperators.LESS, "pppaaa");
		
		assertFalse(p.isDirectQuery());
	}
	
	@Test
	public void testTwoLike() {
		QueryParser p = new QueryParser("   firstName>=    \"as?\" 	aND lastName 	LIKE \"*\"");
		
		List<ConditionalExpression> l = p.getQuery();
		
		checkExp(l.get(0), FieldValueGetters.FIRST_NAME, ComparisonOperators.GREATER_OR_EQUALS, "as?");
		checkExp(l.get(1), FieldValueGetters.LAST_NAME, ComparisonOperators.LIKE, "*");
		
		assertFalse(p.isDirectQuery());
	}
	
	@Test
	public void testTwoNeq() {
		QueryParser p = new QueryParser("   jmbag <=\"\" 	aND		 firstName!=\"\\\"");
		
		List<ConditionalExpression> l = p.getQuery();
		
		checkExp(l.get(0), FieldValueGetters.JMBAG, ComparisonOperators.LESS_OR_EQUALS, "");
		checkExp(l.get(1), FieldValueGetters.FIRST_NAME, ComparisonOperators.NOT_EQUALS, "\\");
		
		assertFalse(p.isDirectQuery());
	}
	
	@Test
	public void testLikeZeroAsx() {
		QueryParser p = new QueryParser("   firstName LIKE    \"aa\" ");
		
		List<ConditionalExpression> l = p.getQuery();
		
		checkExp(l.get(0), FieldValueGetters.FIRST_NAME, ComparisonOperators.LIKE, "aa");
		
		assertFalse(p.isDirectQuery());
	}
	
	@Test
	public void testMultiple() {
		QueryParser p = new QueryParser("   jmbag >  \"#x\" 	aND jmbag!=\"\" AND    lastName = \"'\" AnD "
				+ "jmbag LIKE \"aa*\"");
		
		List<ConditionalExpression> l = p.getQuery();
		
		checkExp(l.get(0), FieldValueGetters.JMBAG, ComparisonOperators.GREATER, "#x");
		checkExp(l.get(1), FieldValueGetters.JMBAG, ComparisonOperators.NOT_EQUALS, "");
		checkExp(l.get(2), FieldValueGetters.LAST_NAME, ComparisonOperators.EQUALS, "'");
		checkExp(l.get(3), FieldValueGetters.JMBAG, ComparisonOperators.LIKE, "aa*");
		
		assertFalse(p.isDirectQuery());
	}
	
	@Test(expected=QueryParserException.class)
	public void testCrash3() {
		QueryParser p = new QueryParser("   firstName>=    \"as?\"aND lastName 	LIKE \"*\"");
	}
	
	@Test(expected=QueryParserException.class)
	public void testCrash4() {
		QueryParser p = new QueryParser("   firstName>=    \"as?\" aND lastNameLIKE \"*\"");	
	}
	
	@Test(expected=QueryParserException.class)
	public void testCrash5() {
		QueryParser p = new QueryParser("   firstName>=    \"as?\" aND lastName LIKE\"*\"");	
	}
	
	@Test(expected=QueryParserException.class)
	public void testCrash6() {
		QueryParser p = new QueryParser("   firstName>=    \"as?\" aNDlastName LIKE \"*\"");	
	}
	
	@Test(expected=QueryParserException.class)
	public void testCrash7() {
		QueryParser p = new QueryParser("   firstName>=    \"as?\" aND");	
	}
	
	@Test(expected=QueryParserException.class)
	public void testCrash8() {
		QueryParser p = new QueryParser("   firstName and    \"as?\" ");	
	}
	
	@Test(expected=QueryParserException.class)
	public void testCrash9() {
		QueryParser p = new QueryParser("   firstname !=    \"as?\" ");	
	}
	
	@Test(expected=QueryParserException.class)
	public void testCrash10() {
		QueryParser p = new QueryParser("   firstName !==    \"as?\" ");	
	}
	
	@Test(expected=QueryParserException.class)
	public void testCrash11() {
		QueryParser p = new QueryParser("   firstName ==    \"as?\" ");	
	}
	
	@Test(expected=QueryParserException.class)
	public void testCrash12() {
		QueryParser p = new QueryParser("   firstName LIKE    \"a*a*\" ");	
	}
	
	@Test(expected=QueryParserException.class)
	public void testCrash13() {
		QueryParser p = new QueryParser("   firstName LIKE    a*a*\" ");	
	}
	
	@Test(expected=QueryParserException.class)
	public void testCrash14() {
		QueryParser p = new QueryParser("   firstName LIKE    \"a*a* ");	
	}
	
	@Test(expected=QueryParserException.class)
	public void testCrash15() {
		QueryParser p = new QueryParser("");	
	}
	
	@Test(expected=QueryParserException.class)
	public void testCrash16() {
		QueryParser p = new QueryParser("))soifobf\"");	
	}
	
	@Test(expected=QueryParserException.class)
	public void testCrash17() {
		QueryParser p = new QueryParser("jmbag =");	
	}
	
	public void checkExp(ConditionalExpression ex, IFieldValueGetter f, IComparisonOperator op, String literal) {
		assertEquals(ex.getFieldGetter(), f);
		assertEquals(ex.getComparisonOperator(), op);
		assertEquals(ex.getStringLiteral(), literal);
	}

}
