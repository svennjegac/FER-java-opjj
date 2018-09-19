package hr.fer.zemris.bf.qmc;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class MinimizerTest {

	@Test(expected=IllegalArgumentException.class)
	public void constructorNull() {
		Set<Integer> dontCares = new TreeSet<>();
		List<String> vars = new ArrayList<>();
		
		new Minimizer(null, dontCares, vars);
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructorNull2() {
		Set<Integer> dontCares = new TreeSet<>();
		List<String> vars = new ArrayList<>();
		
		new Minimizer(dontCares, null, vars);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorNull3() {
		Set<Integer> minterms = new TreeSet<>();
		Set<Integer> dontCares = new TreeSet<>();
		
		List<String> vars = new ArrayList<>();
		
		new Minimizer(minterms, dontCares, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorOverlap() {
		Set<Integer> minterms = new TreeSet<>();
		minterms.add(0);
		minterms.add(1);
		
		Set<Integer> dontCares = new TreeSet<>();
		dontCares.add(1);
		dontCares.add(2);
		
		List<String> vars = new ArrayList<>();
		vars.add("A");
		vars.add("B");
		vars.add("C");
		
		new Minimizer(minterms, dontCares, vars);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorOverlap2() {
		Set<Integer> minterms = new TreeSet<>();
		minterms.add(0);
		
		Set<Integer> dontCares = new TreeSet<>();
		dontCares.add(0);
		
		List<String> vars = new ArrayList<>();
		vars.add("A");
		vars.add("B");
		vars.add("C");
		
		new Minimizer(minterms, dontCares, vars);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorIndexesOutOfRange() {
		Set<Integer> minterms = new TreeSet<>();
		minterms.add(0);
		minterms.add(10);
		
		Set<Integer> dontCares = new TreeSet<>();
		dontCares.add(1);
		dontCares.add(2);
		
		List<String> vars = new ArrayList<>();
		vars.add("A");
		vars.add("B");
		vars.add("C");
		
		new Minimizer(minterms, dontCares, vars);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorIndexesOutOfRange2() {
		Set<Integer> minterms = new TreeSet<>();
		minterms.add(0);
		minterms.add(8);
		
		Set<Integer> dontCares = new TreeSet<>();
		dontCares.add(1);
		dontCares.add(2);
		
		List<String> vars = new ArrayList<>();
		vars.add("A");
		vars.add("B");
		vars.add("C");
		
		new Minimizer(minterms, dontCares, vars);
	}
	
	@Test
	public void constructor() {
		Set<Integer> minterms = new TreeSet<>();
		minterms.add(0);
		minterms.add(1);
		minterms.add(7);
		
		Set<Integer> dontCares = new TreeSet<>();
		dontCares.add(3);
		dontCares.add(2);
		dontCares.add(4);
		
		List<String> vars = new ArrayList<>();
		vars.add("A");
		vars.add("B");
		vars.add("C");
		
		new Minimizer(minterms, dontCares, vars);
	}
}
