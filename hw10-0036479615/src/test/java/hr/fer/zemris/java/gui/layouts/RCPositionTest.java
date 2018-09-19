package hr.fer.zemris.java.gui.layouts;

import static org.junit.Assert.*;

import org.junit.Test;

public class RCPositionTest {

	@Test
	public void constructor() {
		new RCPosition(1, 1);
		new RCPosition(1, 6);
		new RCPosition(1, 7);
		new RCPosition(3, 3);
		new RCPosition(5, 1);
		new RCPosition(5, 7);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidConstructor() {
		new RCPosition(0, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidConstructor2() {
		new RCPosition(1, -1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidConstructor3() {
		new RCPosition(1, 2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidConstructor4() {
		new RCPosition(1, 4);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidConstructor5() {
		new RCPosition(1, 5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidConstructor6() {
		new RCPosition(6, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidConstructor7() {
		new RCPosition(5, 8);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidConstructor8() {
		new RCPosition(1, -1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidConstructor9() {
		new RCPosition(3, 0);
	}
	
	@Test
	public void equals() {
		assertTrue(new RCPosition(1, 1).equals(new RCPosition(1, 1)));
	}
	
	@Test
	public void equals2() {
		assertTrue(new RCPosition(2, 6).equals(new RCPosition(2, 6)));
	}
	
	@Test
	public void equals3() {
		assertTrue(!(new RCPosition(2, 6).equals(new RCPosition(2, 3))));
	}
	
	@Test
	public void makeFromString() {
		assertTrue(new RCPosition(2, 6).equals(RCPosition.makeFromString("2, 6")));
	}
	
	@Test
	public void makeFromString2() {
		assertTrue(new RCPosition(2, 6).equals(RCPosition.makeFromString("  2    ,    6   ")));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void makeFromStringError() {
		RCPosition.makeFromString("  2    ,,    6   ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void makeFromStringError2() {
		RCPosition.makeFromString(",  2,    6   ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void makeFromStringError3() {
		RCPosition.makeFromString("2,6,");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void makeFromStringError4() {
		RCPosition.makeFromString("2111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111,6,");
	}
}
