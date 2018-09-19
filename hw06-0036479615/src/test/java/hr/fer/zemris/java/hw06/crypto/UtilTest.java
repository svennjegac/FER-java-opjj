package hr.fer.zemris.java.hw06.crypto;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilTest {

	@Test
	public void empty() {
		byte[] result = Util.hextobyte("");
		
		assertEquals(0, result.length);
		assertTrue(Util.bytetohex(result).equals(""));
	}
	
	@Test
	public void zero() {
		byte[] result = Util.hextobyte("00");
		
		assertEquals(1, result.length);
		assertEquals(0, result[0]);
		assertTrue(Util.bytetohex(result).equals("00"));
	}
	
	@Test
	public void one() {
		byte[] result = Util.hextobyte("01");
		
		assertEquals(1, result.length);
		assertEquals(1, result[0]);
		assertTrue(Util.bytetohex(result).equals("01"));
	}
	
	@Test
	public void eight() {
		byte[] result = Util.hextobyte("08");
		
		assertEquals(1, result.length);
		assertEquals(8, result[0]);
		assertTrue(Util.bytetohex(result).equals("08"));
	}
	
	@Test
	public void thirteen() {
		byte[] result = Util.hextobyte("0D");
		
		assertEquals(1, result.length);
		assertEquals(13, result[0]);
		assertTrue(Util.bytetohex(result).equals("0d"));
	}
	
	@Test
	public void fifthteen() {
		byte[] result = Util.hextobyte("0F");
		
		assertEquals(1, result.length);
		assertEquals(15, result[0]);
		assertTrue(Util.bytetohex(result).equals("0f"));
	}
	
	@Test
	public void sixteen() {
		byte[] result = Util.hextobyte("10");
		
		assertEquals(1, result.length);
		assertEquals(16, result[0]);
		assertTrue(Util.bytetohex(result).equals("10"));
	}
	
	@Test
	public void twenty() {
		byte[] result = Util.hextobyte("14");
		
		assertEquals(1, result.length);
		assertEquals(20, result[0]);
		assertTrue(Util.bytetohex(result).equals("14"));
	}
	
	@Test
	public void big() {
		byte[] result = Util.hextobyte("1E33");
		
		assertEquals(2, result.length);
		assertEquals(30, result[0]);
		assertEquals(51, result[1]);
		assertTrue(Util.bytetohex(result).equals("1e33"));
	}
	
	@Test
	public void negative() {
		byte[] result = Util.hextobyte("FFA1c407");
		
		assertEquals(4, result.length);
		assertEquals(-1, result[0]);
		assertEquals(-95, result[1]);
		assertEquals(-60, result[2]);
		assertEquals(7, result[3]);
		assertTrue(Util.bytetohex(result).equals("ffa1c407"));
	}
	
	@Test
	public void cupicEx() {
		byte[] result = Util.hextobyte("01aE22");
		
		assertEquals(3, result.length);
		assertEquals(1, result[0]);
		assertEquals(-82, result[1]);
		assertEquals(34, result[2]);
		assertTrue(Util.bytetohex(result).equals("01ae22"));
	}
	
	@Test
	public void cupicEx2() {
		String result = Util.bytetohex((new byte[] {1, -82, 34}));
		
		assertTrue(result.equals("01ae22"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void odd() {
		byte[] result = Util.hextobyte("FFA1c4073");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void invalid() {
		byte[] result = Util.hextobyte("FFA1cg07");
	}

}
