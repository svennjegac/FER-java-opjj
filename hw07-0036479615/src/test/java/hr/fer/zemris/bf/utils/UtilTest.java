package hr.fer.zemris.bf.utils;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import hr.fer.zemris.bf.parser.Parser;

public class UtilTest {

	@Test
	public void booleanArrayToIntValidValues() {
		List<boolean[]> listOfBooleanArrays = Arrays.asList(
				new boolean[]{},
				new boolean[]{false},
				new boolean[]{true},
				new boolean[]{false, false},
				new boolean[]{false, true},
				new boolean[]{true, false},
				new boolean[]{true, true},
				new boolean[]{false, false, true, true},
				new boolean[]{true, false, true, true},
				new boolean[]{true, true, true, true}
				
		);
		
		List<Integer> values = new ArrayList<>();
		
		for (boolean[] bs : listOfBooleanArrays) {
			values.add(Util.booleanArrayToInt(bs));
		}
		
		assertArrayEquals(values.toArray(new Integer[listOfBooleanArrays.size()]), 
				new Integer[]{0, 0, 1, 0, 1, 2, 3, 3, 11, 15}
		);
	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void booleanArrayToIntNullValue() {
		Util.booleanArrayToInt(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void forEachNull() {
		Util.forEach(null, a -> {});
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void forEachNull2() {
		Util.forEach(Arrays.asList("a"), null);
	}
	
	@Test
	public void forEach() {
		Util.forEach(Arrays.asList(), a -> {System.out.println("This should not print");});
	}
	
	@Test
	public void forEach2() {
		boolean[] arr = new boolean[]{true, true, true};
		List<boolean[]> l = new ArrayList<>();
		
		Util.forEach(Arrays.asList("a", "b", "c"), a -> {
			if (Arrays.equals(arr, a)) {
				l.add(a);
			}
		});
		
		assertArrayEquals(arr, l.get(0));
	}
	
	@Test
	public void forEach3() {
		boolean[] arr = new boolean[]{true};
		List<boolean[]> l = new ArrayList<>();
		
		Util.forEach(Arrays.asList("a"), a -> {
			if (Arrays.equals(arr, a)) {
				l.add(a);
			}
		});
		
		assertArrayEquals(arr, l.get(0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void filterAssignmentsNull() {
		Parser p = new Parser("a");
		
		Util.filterAssignments(null, p.getExpression(), true);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void filterAssignmentsNull2() {
		Parser p = new Parser("a");
		
		Util.filterAssignments(Arrays.asList(), null, true);
	}
	
	@Test
	public void filterAssignments() {
		Parser p = new Parser("a");
		
		assertEquals(1, Util.filterAssignments(Arrays.asList("A"), p.getExpression(), true).size());
	}
	
	@Test
	public void filterAssignments2() {
		Parser p = new Parser("a");
		
		assertEquals(0, Util.filterAssignments(Arrays.asList(), p.getExpression(), true).size());
	}
	
	@Test(expected=IllegalStateException.class)
	public void filterAssignmentsUndeclared() {
		Parser p = new Parser("a or b");
		
		assertEquals(0, Util.filterAssignments(Arrays.asList("B"), p.getExpression(), true).size());
	}
	
	@Test(expected=IllegalStateException.class)
	public void filterAssignmentsUndeclared2() {
		Parser p = new Parser("a");
		
		assertEquals(0, Util.filterAssignments(Arrays.asList("B"), p.getExpression(), true).size());
	}
	
	@Test
	public void filterAssignments3() {
		Parser p = new Parser("a and b and c");
		
		assertEquals(1, Util.filterAssignments(Arrays.asList("A", "B", "C"), p.getExpression(), true).size());
	}
	
	@Test
	public void filterAssignments4() {
		Parser p = new Parser("a and b or c");
		
		assertEquals(5, Util.filterAssignments(Arrays.asList("A", "B", "C"), p.getExpression(), true).size());
	}
	
	@Test
	public void filterAssignments5() {
		Parser p = new Parser("a and b or c or 1");
		
		assertEquals(8, Util.filterAssignments(Arrays.asList("A", "B", "C"), p.getExpression(), true).size());
	}
	
	@Test
	public void toSumOfMinterms() {
		Parser p = new Parser("a");
		
		VariablesGetter g = new VariablesGetter();
		
		p.getExpression().accept(g);
		
		List<String> vars = g.getVariables();
		
		Set<Integer> resultSet = Util.toSumOfMinterms(vars, p.getExpression());
		
		assertArrayEquals(new Integer[]{1}
				, resultSet.toArray(new Integer[resultSet.size()]));
	}
	
	@Test
	public void toProductOfMaxterms() {
		Parser p = new Parser("a");
		
		VariablesGetter g = new VariablesGetter();
		
		p.getExpression().accept(g);
		
		List<String> vars = g.getVariables();
		
		Set<Integer> resultSet = Util.toProductOfMaxterms(vars, p.getExpression());
		
		assertArrayEquals(new Integer[]{0}
				, resultSet.toArray(new Integer[resultSet.size()]));
	}
	
	@Test
	public void toSumOfMinterms2() {
		Parser p = new Parser("a and b or c");
		
		VariablesGetter g = new VariablesGetter();
		
		p.getExpression().accept(g);
		
		List<String> vars = g.getVariables();
		
		Set<Integer> resultSet = Util.toSumOfMinterms(vars, p.getExpression());
		
		assertArrayEquals(new Integer[]{1, 3, 5, 6, 7}
				, resultSet.toArray(new Integer[resultSet.size()]));
	}
	
	@Test
	public void toProductOfMaxterms2() {
		Parser p = new Parser("a and b or c");
		
		VariablesGetter g = new VariablesGetter();
		
		p.getExpression().accept(g);
		
		List<String> vars = g.getVariables();
		
		Set<Integer> resultSet = Util.toProductOfMaxterms(vars, p.getExpression());
		
		assertArrayEquals(new Integer[]{0, 2, 4}
				, resultSet.toArray(new Integer[resultSet.size()]));
	}
	
	@Test
	public void toSumOfMinterms3() {
		Parser p = new Parser("a and b or c or 1");
		
		VariablesGetter g = new VariablesGetter();
		
		p.getExpression().accept(g);
		
		List<String> vars = g.getVariables();
		
		Set<Integer> resultSet = Util.toSumOfMinterms(vars, p.getExpression());
		
		assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7}
				, resultSet.toArray(new Integer[resultSet.size()]));
	}
	
	@Test
	public void toProductOfMaxterms3() {
		Parser p = new Parser("a and b or c or true");
		
		VariablesGetter g = new VariablesGetter();
		
		p.getExpression().accept(g);
		
		List<String> vars = g.getVariables();
		
		Set<Integer> resultSet = Util.toProductOfMaxterms(vars, p.getExpression());
		
		assertArrayEquals(new Integer[]{}
				, resultSet.toArray(new Integer[resultSet.size()]));
	}
	
	@Test
	public void toSumOfMinterms4() {
		Parser p = new Parser("a xor b");
		
		VariablesGetter g = new VariablesGetter();
		
		p.getExpression().accept(g);
		
		List<String> vars = g.getVariables();
		
		Set<Integer> resultSet = Util.toSumOfMinterms(vars, p.getExpression());
		
		assertArrayEquals(new Integer[]{1, 2}
				, resultSet.toArray(new Integer[resultSet.size()]));
	}
	
	@Test
	public void toProductOfMaxterms4() {
		Parser p = new Parser("a xor b");
		
		VariablesGetter g = new VariablesGetter();
		
		p.getExpression().accept(g);
		
		List<String> vars = g.getVariables();
		
		Set<Integer> resultSet = Util.toProductOfMaxterms(vars, p.getExpression());
		
		assertArrayEquals(new Integer[]{0, 3}
				, resultSet.toArray(new Integer[resultSet.size()]));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void toProductOfMaxtermsNull() {
		Parser p = new Parser("a xor b");
		
		VariablesGetter g = new VariablesGetter();
		
		p.getExpression().accept(g);
		
		List<String> vars = g.getVariables();
		
		Set<Integer> resultSet = Util.toProductOfMaxterms(null, p.getExpression());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void toProductOfMaxtermsNull2() {
		Parser p = new Parser("a xor b");
		
		VariablesGetter g = new VariablesGetter();
		
		p.getExpression().accept(g);
		
		List<String> vars = g.getVariables();
		
		Set<Integer> resultSet = Util.toProductOfMaxterms(vars, null);
	}
	
	@Test
	public void indexToByteArraySimple() {
		byte[] result = Util.indexToByteArray(3, 3);
		
		assertArrayEquals(result, new byte[]{0, 1, 1});
	}
	
	@Test
	public void indexToByteArraySimple2() {
		byte[] result = Util.indexToByteArray(3, 5);
		
		assertArrayEquals(result, new byte[]{0, 0, 0, 1, 1});
	}
	
	@Test
	public void indexToByteArraySimple3() {
		byte[] result = Util.indexToByteArray(3, 2);
		
		assertArrayEquals(result, new byte[]{1, 1});
	}
	
	@Test
	public void indexToByteArrayLessSpaces() {
		byte[] result = Util.indexToByteArray(3, 1);
		
		assertArrayEquals(result, new byte[]{1});
	}
	
	@Test
	public void indexToByteArraySimple4() {
		byte[] result = Util.indexToByteArray(2, 2);
		
		assertArrayEquals(result, new byte[]{1, 0});
	}
	
	@Test
	public void indexToByteArrayLessSpaces2() {
		byte[] result = Util.indexToByteArray(2, 1);
		
		assertArrayEquals(result, new byte[]{0});
	}
	
	@Test
	public void indexToByteArrayLessSpaces3() {
		byte[] result = Util.indexToByteArray(3, 0);
		
		assertArrayEquals(result, new byte[]{});
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void indexToByteArrayNegativeSpaces() {
		Util.indexToByteArray(3, -1);
	}
	
	@Test
	public void indexToByteArrayLessSpaces4() {
		byte[] result = Util.indexToByteArray(0, 0);
		
		assertArrayEquals(result, new byte[]{});
	}
	
	@Test
	public void indexToByteArraySimple5() {
		byte[] result = Util.indexToByteArray(0, 2);
		
		assertArrayEquals(result, new byte[]{0, 0});
	}
	
	@Test
	public void indexToByteArraySimple6() {
		byte[] result = Util.indexToByteArray(0, 1);
		
		assertArrayEquals(result, new byte[]{0});
	}
	
	@Test
	public void indexToByteArrayNegative() {
		byte[] result = Util.indexToByteArray(-2, 4);
		
		assertArrayEquals(result, new byte[]{1, 1, 1, 0});
	}
	
	@Test
	public void indexToByteArrayNegative2() {
		byte[] result = Util.indexToByteArray(-4, 4);
		
		assertArrayEquals(result, new byte[]{1, 1, 0, 0});
	}
	
	@Test
	public void indexToByteArrayNegative3() {
		byte[] result = Util.indexToByteArray(-0, 4);
		
		assertArrayEquals(result, new byte[]{0, 0, 0, 0});
	}
	
	@Test
	public void indexToByteArrayNegative4() {
		byte[] result = Util.indexToByteArray(-2, 1);
		
		assertArrayEquals(result, new byte[]{0});
	}
	
	@Test
	public void indexToByteArrayNegative5() {
		byte[] result = Util.indexToByteArray(-2, 32);
		
		assertArrayEquals(result, new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0});
	}
	
	@Test
	public void indexToByteArrayNegative6() {
		byte[] result = Util.indexToByteArray(-2, 16);
		
		assertArrayEquals(result, new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0});
	}
	
	@Test
	public void indexToByteArrayNegative7() {
		byte[] result = Util.indexToByteArray(-2, 0);
		
		assertArrayEquals(result, new byte[]{});
	}
	
	@Test
	public void indexToByteArraySimple7() {
		byte[] result = Util.indexToByteArray(19, 4);
		
		assertArrayEquals(result, new byte[]{0, 0, 1, 1});
	}
	
	@Test
	public void indexToByteArrayMax() {
		byte[] result = Util.indexToByteArray(Integer.MAX_VALUE, 32);
		
		assertArrayEquals(result, new byte[]{0, 1, 1, 1, 1 , 1, 1, 1, 1 , 1, 1,
				1, 1 , 1, 1, 1, 1 , 1, 1, 1, 1 , 1, 1, 1, 1 , 1, 1, 1, 1 , 1, 1, 1});
	}
	
	@Test
	public void indexToByteArrayMin() {
		byte[] result = Util.indexToByteArray(Integer.MIN_VALUE, 32);
		
		assertArrayEquals(result, new byte[]{1, 0, 0, 0, 0 , 0, 0, 0, 0 , 0, 0, 0, 0
				, 0, 0, 0, 0 , 0, 0, 0, 0 , 0, 0, 0, 0 , 0, 0, 0, 0 , 0, 0, 0});
	}
}
