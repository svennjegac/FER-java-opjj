package hr.fer.zemris.java.hw05.demo2;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class PrimesCollectionTest {

	@Test(expected=IllegalArgumentException.class)
	public void tooSmall() {
		PrimesCollection primes = new PrimesCollection(-5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void tooSmall2() {
		PrimesCollection primes = new PrimesCollection(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void tooBig() {
		PrimesCollection primes = new PrimesCollection(2_105_097_566);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void tooBig2() {
		PrimesCollection primes = new PrimesCollection(105_097_566);
	}
	
	@Test
	public void good() {
		PrimesCollection primes = new PrimesCollection(1);
		
		Iterator<Integer> iter = primes.iterator();
		
		assertEquals(2, (int) iter.next());
	}
	
	@Test(expected=NoSuchElementException.class)
	public void noSuchElemEx() {
		PrimesCollection primes = new PrimesCollection(1);
		
		Iterator<Integer> iter = primes.iterator();
		
		iter.next();
		iter.next();
	}
	
	@Test
	public void good2() {
		PrimesCollection primes = new PrimesCollection(11);
		
		Iterator<Integer> iter = primes.iterator();
		
		assertEquals(2, (int) iter.next());
		assertEquals(3, (int) iter.next());
		assertEquals(5, (int) iter.next());
		assertEquals(7, (int) iter.next());
		assertEquals(11, (int) iter.next());
		assertEquals(13, (int) iter.next());
		assertEquals(17, (int) iter.next());
		assertEquals(19, (int) iter.next());
		assertEquals(23, (int) iter.next());
		assertEquals(29, (int) iter.next());
		assertEquals(31, (int) iter.next());
		assertFalse(iter.hasNext());
	}

}
