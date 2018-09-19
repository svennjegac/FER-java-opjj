package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValueWrapperTest {

	@Test
	public void cupicExample1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());     // v1 now stores Integer(0); v2 still stores null.
		
		assertEquals(0, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertNull(v2.getValue());
	}
	
	@Test
	public void cupicExample2() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());     // v3 now stores Double(13); v4 still stores Integer(1).
		
		assertEquals(13d, v3.getValue());
		assertTrue(v3.getValue() instanceof Double);
		assertEquals(1, v4.getValue());
		assertTrue(v4.getValue() instanceof Integer);
	}
	
	@Test
	public void cupicExample3() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue());     // v5 now stores Integer(13); v6 still stores Integer(1).
		
		assertEquals(13, v5.getValue());
		assertTrue(v5.getValue() instanceof Integer);
		assertEquals(1, v6.getValue());
		assertTrue(v6.getValue() instanceof Integer);
	}
	
	@Test(expected=RuntimeException.class)
	public void cupicExample4() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		v7.add(v8.getValue());     // throws RuntimeException
	}
	
	@Test
	public void nullNullAdd() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.add(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertNull(v2.getValue());
	}
	
	@Test
	public void nullIntAdd() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(5);
		
		v1.add(v2.getValue());
		
		assertEquals(5, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals(5, v2.getValue());
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void intNullAdd() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.add(v2.getValue());
		
		assertEquals(3, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertNull(v2.getValue());
	}
	
	@Test
	public void doubleNullAdd() {
		ValueWrapper v1 = new ValueWrapper(3.4);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.add(v2.getValue());
		
		assertEquals(3.4, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertNull(v2.getValue());
	}
	
	@Test
	public void nullDoubleAdd() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(-3.5);
		
		v1.add(v2.getValue());
		
		assertEquals(-3.5, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(-3.5, v2.getValue());
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void intIntAdd() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(4);
		
		v1.add(v2.getValue());
		
		assertEquals(7, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals(4, v2.getValue());
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void doubleDoubleAdd() {
		ValueWrapper v1 = new ValueWrapper(3.5);
		ValueWrapper v2 = new ValueWrapper(4.1);
		
		v1.add(v2.getValue());
		
		assertEquals(7.6, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(4.1, v2.getValue());
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void intDoubleAdd() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(4.1);
		
		v1.add(v2.getValue());
		
		assertEquals(7.1, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(4.1, v2.getValue());
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void doubleIntAdd() {
		ValueWrapper v1 = new ValueWrapper(3.8);
		ValueWrapper v2 = new ValueWrapper(4);
		
		v1.add(v2.getValue());
		
		assertEquals(7.8, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(4, v2.getValue());
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void stringIntStringIntAdd() {
		ValueWrapper v1 = new ValueWrapper("3");
		ValueWrapper v2 = new ValueWrapper("-9");
		
		v1.add(v2.getValue());
		
		assertEquals(-6, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals("-9", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringDoubleStringIntAdd() {
		ValueWrapper v1 = new ValueWrapper("3.");
		ValueWrapper v2 = new ValueWrapper("-9");
		
		v1.add(v2.getValue());
		
		assertEquals(-6.0, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("-9", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringIntStringDoubleAdd() {
		ValueWrapper v1 = new ValueWrapper("3");
		ValueWrapper v2 = new ValueWrapper("1.0E2");
		
		v1.add(v2.getValue());
		
		assertEquals(103.0, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("1.0E2", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringDoubleStringDoubleAdd() {
		ValueWrapper v1 = new ValueWrapper("-2.1E3");
		ValueWrapper v2 = new ValueWrapper("1.0E2");
		
		v1.add(v2.getValue());
		
		assertEquals(-2000d, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("1.0E2", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringIntNullAdd() {
		ValueWrapper v1 = new ValueWrapper("-6");
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.add(v2.getValue());
		
		assertEquals(-6, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertNull(v2.getValue());
	}
	
	@Test
	public void nullStringIntAdd() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper("-211");
		
		v1.add(v2.getValue());
		
		assertEquals(-211, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals("-211", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void nullStringDoubleAdd() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper("-211.9");
		
		v1.add(v2.getValue());
		
		assertEquals(-211.9, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("-211.9", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringDoubleNullAdd() {
		ValueWrapper v1 = new ValueWrapper("-999.11");
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.add(v2.getValue());
		
		assertEquals(-999.11, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertNull(v2.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void invalidInput() {
		ValueWrapper v1 = new ValueWrapper("-999.11.");
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.add(v2.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void invalidInput2() {
		ValueWrapper v1 = new ValueWrapper("-999.11E1.0");
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.add(v2.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void invalidInput3() {
		ValueWrapper v1 = new ValueWrapper("-999.11E1E");
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.add(v2.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void invalidInput4() {
		ValueWrapper v1 = new ValueWrapper("E1");
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.add(v2.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void invalidInput5() {
		ValueWrapper v1 = new ValueWrapper("1,2");
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.add(v2.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void invalidInput7() {
		ValueWrapper v1 = new ValueWrapper("1,2");
		
		v1.add(new Object());
	}
	
	@Test(expected=RuntimeException.class)
	public void invalidInput9() {
		ValueWrapper v1 = new ValueWrapper("1,2");
		
		v1.add(new String("haha"));
	}
	
	@Test
	public void nullNullSub() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.subtract(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertNull(v2.getValue());
	}
	
	@Test
	public void nullIntSub() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(5);
		
		v1.subtract(v2.getValue());
		
		assertEquals(-5, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals(5, v2.getValue());
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void intNullSub() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.subtract(v2.getValue());
		
		assertEquals(3, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertNull(v2.getValue());
	}
	
	@Test
	public void doubleNullSub() {
		ValueWrapper v1 = new ValueWrapper(3.4);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.subtract(v2.getValue());
		
		assertEquals(3.4, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertNull(v2.getValue());
	}
	
	@Test
	public void nullDoubleSub() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(-3.5);
		
		v1.subtract(v2.getValue());
		
		assertEquals(3.5, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(-3.5, v2.getValue());
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void intIntSub() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(4);
		
		v1.subtract(v2.getValue());
		
		assertEquals(-1, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals(4, v2.getValue());
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void doubleDoubleSub() {
		ValueWrapper v1 = new ValueWrapper(3.5);
		ValueWrapper v2 = new ValueWrapper(4.1);
		
		v1.subtract(v2.getValue());
		
		assertEquals(-.6, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(4.1, v2.getValue());
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void intDoubleSub() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(4.1);
		
		v1.subtract(v2.getValue());
		
		assertEquals(-1.1, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(4.1, v2.getValue());
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void doubleIntSub() {
		ValueWrapper v1 = new ValueWrapper(3.8);
		ValueWrapper v2 = new ValueWrapper(4);
		
		v1.subtract(v2.getValue());
		
		assertEquals(-.2, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(4, v2.getValue());
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void stringIntStringIntSub() {
		ValueWrapper v1 = new ValueWrapper("3");
		ValueWrapper v2 = new ValueWrapper("-9");
		
		v1.subtract(v2.getValue());
		
		assertEquals(12, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals("-9", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringDoubleStringIntSub() {
		ValueWrapper v1 = new ValueWrapper("3.");
		ValueWrapper v2 = new ValueWrapper("-9");
		
		v1.subtract(v2.getValue());
		
		assertEquals(12.0, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("-9", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringIntStringDoubleSub() {
		ValueWrapper v1 = new ValueWrapper("3");
		ValueWrapper v2 = new ValueWrapper("1.0E2");
		
		v1.subtract(v2.getValue());
		
		assertEquals(-97.0, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("1.0E2", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringDoubleStringDoubleSub() {
		ValueWrapper v1 = new ValueWrapper("-2.1E3");
		ValueWrapper v2 = new ValueWrapper("1.0E2");
		
		v1.subtract(v2.getValue());
		
		assertEquals(-2200d, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("1.0E2", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringIntNullSub() {
		ValueWrapper v1 = new ValueWrapper("-6");
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.subtract(v2.getValue());
		
		assertEquals(-6, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertNull(v2.getValue());
	}
	
	@Test
	public void nullStringIntSub() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper("-211");
		
		v1.subtract(v2.getValue());
		
		assertEquals(211, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals("-211", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void nullStringDoubleSub() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper("-211.9");
		
		v1.subtract(v2.getValue());
		
		assertEquals(211.9, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("-211.9", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringDoubleNullSub() {
		ValueWrapper v1 = new ValueWrapper("-999.11");
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.subtract(v2.getValue());
		
		assertEquals(-999.11, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertNull(v2.getValue());
	}
	
	@Test
	public void nullNullMul() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.multiply(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertNull(v2.getValue());
	}
	
	@Test
	public void nullIntMul() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(5);
		
		v1.multiply(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals(5, v2.getValue());
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void intNullMul() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.multiply(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertNull(v2.getValue());
	}
	
	@Test
	public void doubleNullMul() {
		ValueWrapper v1 = new ValueWrapper(3.4);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.multiply(v2.getValue());
		
		assertEquals(0.0, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertNull(v2.getValue());
	}
	
	@Test
	public void nullDoubleMul() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(-3.5);
		
		v1.multiply(v2.getValue());
		
		assertEquals(0.0, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(-3.5, v2.getValue());
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void intIntMul() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(4);
		
		v1.multiply(v2.getValue());
		
		assertEquals(12, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals(4, v2.getValue());
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void doubleDoubleMul() {
		ValueWrapper v1 = new ValueWrapper(3.5);
		ValueWrapper v2 = new ValueWrapper(4.1);
		
		v1.multiply(v2.getValue());
		
		assertEquals(14.35, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(4.1, v2.getValue());
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void intDoubleMul() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(4.1);
		
		v1.multiply(v2.getValue());
		
		assertEquals(12.3, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(4.1, v2.getValue());
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void doubleIntMul() {
		ValueWrapper v1 = new ValueWrapper(3.8);
		ValueWrapper v2 = new ValueWrapper(4);
		
		v1.multiply(v2.getValue());
		
		assertEquals(15.2, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(4, v2.getValue());
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void stringIntStringIntMul() {
		ValueWrapper v1 = new ValueWrapper("3");
		ValueWrapper v2 = new ValueWrapper("-9");
		
		v1.multiply(v2.getValue());
		
		assertEquals(-27, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals("-9", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringDoubleStringIntMul() {
		ValueWrapper v1 = new ValueWrapper("3.");
		ValueWrapper v2 = new ValueWrapper("-9");
		
		v1.multiply(v2.getValue());
		
		assertEquals(-27.0, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("-9", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringIntStringDoubleMul() {
		ValueWrapper v1 = new ValueWrapper("3");
		ValueWrapper v2 = new ValueWrapper("1.0E2");
		
		v1.multiply(v2.getValue());
		
		assertEquals(300.0, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("1.0E2", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringDoubleStringDoubleMul() {
		ValueWrapper v1 = new ValueWrapper("-2.1E3");
		ValueWrapper v2 = new ValueWrapper("1.0E2");
		
		v1.multiply(v2.getValue());
		
		assertEquals(-210000.0, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("1.0E2", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringIntNullMul() {
		ValueWrapper v1 = new ValueWrapper("-6");
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.multiply(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertNull(v2.getValue());
	}
	
	@Test
	public void nullStringIntMul() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper("-211");
		
		v1.multiply(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals("-211", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void nullStringDoubleMul() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper("-211.9");
		
		v1.multiply(v2.getValue());
		
		assertEquals(0.0, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("-211.9", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringDoubleNullMul() {
		ValueWrapper v1 = new ValueWrapper("-999.11");
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.multiply(v2.getValue());
		
		assertEquals(0.0, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertNull(v2.getValue());
	}
	
	@Test(expected=ArithmeticException.class)
	public void nullNullDiv() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.divide(v2.getValue());
	}
	
	@Test
	public void nullIntDiv() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(5);
		
		v1.divide(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals(5, v2.getValue());
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test(expected=ArithmeticException.class)
	public void intNullDiv() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.divide(v2.getValue());
		
	}
	
	@Test
	public void doubleNullDiv() {
		ValueWrapper v1 = new ValueWrapper(3.4);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.divide(v2.getValue());
		
		assertEquals(Double.POSITIVE_INFINITY, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertNull(v2.getValue());
	}
	
	@Test
	public void doubleNullDiv2() {
		ValueWrapper v1 = new ValueWrapper(-0.01);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.divide(v2.getValue());
		
		assertEquals(Double.NEGATIVE_INFINITY, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertNull(v2.getValue());
	}
	
	@Test
	public void nullDoubleDiv() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(-3.5);
		
		v1.divide(v2.getValue());
		
		assertEquals(0.0, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(-3.5, v2.getValue());
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void intIntDiv() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(4);
		
		v1.divide(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals(4, v2.getValue());
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void doubleDoubleDiv() {
		ValueWrapper v1 = new ValueWrapper(3.5);
		ValueWrapper v2 = new ValueWrapper(4.1);
		
		v1.divide(v2.getValue());
		
		assertEquals(0.85366, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(4.1, v2.getValue());
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void intDoubleDiv() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(4.1);
		
		v1.divide(v2.getValue());
		
		assertEquals(0.7317, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(4.1, v2.getValue());
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void doubleIntDiv() {
		ValueWrapper v1 = new ValueWrapper(3.8);
		ValueWrapper v2 = new ValueWrapper(4);
		
		v1.divide(v2.getValue());
		
		assertEquals(0.95, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(4, v2.getValue());
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void stringIntStringIntDiv() {
		ValueWrapper v1 = new ValueWrapper("3");
		ValueWrapper v2 = new ValueWrapper("-9");
		
		v1.divide(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals("-9", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringDoubleStringIntDiv() {
		ValueWrapper v1 = new ValueWrapper("3.");
		ValueWrapper v2 = new ValueWrapper("-9");
		
		v1.divide(v2.getValue());
		
		assertEquals(-0.3333, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("-9", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringIntStringDoubleDiv() {
		ValueWrapper v1 = new ValueWrapper("3");
		ValueWrapper v2 = new ValueWrapper("1.0E2");
		
		v1.divide(v2.getValue());
		
		assertEquals(0.03, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("1.0E2", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringDoubleStringDoubleDiv() {
		ValueWrapper v1 = new ValueWrapper("-2.1E3");
		ValueWrapper v2 = new ValueWrapper("1.0E2");
		
		v1.divide(v2.getValue());
		
		assertEquals(-21.0, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("1.0E2", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test(expected=ArithmeticException.class)
	public void stringIntNullDiv() {
		ValueWrapper v1 = new ValueWrapper("-6");
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.divide(v2.getValue());
	}
	
	@Test
	public void nullStringIntDiv() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper("-211");
		
		v1.divide(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals("-211", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void nullStringDoubleDiv() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper("-211.9");
		
		v1.divide(v2.getValue());
		
		assertEquals(0.0, (double) v1.getValue(), 1E-3);
		assertTrue(v1.getValue() instanceof Double);
		assertEquals("-211.9", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringDoubleNullDiv() {
		ValueWrapper v1 = new ValueWrapper("-999.11");
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.divide(v2.getValue());
		assertEquals(Double.NEGATIVE_INFINITY, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertNull(v2.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void wrongType() {
		ValueWrapper v1 = new ValueWrapper("-999.11");
		ValueWrapper v2 = new ValueWrapper(new ValueWrapper(1));
		
		v1.divide(v2.getValue());
	}

	@Test
	public void nullNullCmp() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		assertEquals(0, v1.numCompare(v2.getValue()));
		assertNull(v1.getValue());
		assertNull(v2.getValue());
	}
	
	@Test
	public void nullIntCmp() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(0);
		
		assertEquals(0, v1.numCompare(v2.getValue()));
		assertNull(v1.getValue());
		assertEquals(0, v2.getValue());
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void intNullCmp() {
		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(null);
		
		assertEquals(1, v1.numCompare(v2.getValue()));
		assertEquals(1, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertNull(v2.getValue());
	}
	
	@Test
	public void doubleNullCmp() {
		ValueWrapper v1 = new ValueWrapper(0.00000000001);
		ValueWrapper v2 = new ValueWrapper(null);
		
		
		assertEquals(1, v1.numCompare(v2.getValue()));
		assertEquals(0.00000000001, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertNull(v2.getValue());
	}
	
	@Test
	public void nullDoubleCmp() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(-0.00000001);
		
		assertEquals(1, v1.numCompare(v2.getValue()));
		assertNull(v1.getValue());
		assertEquals(-0.00000001, v2.getValue());
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void intIntCmp() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(4);
		
		assertEquals(-1, v1.numCompare(v2.getValue()));
		assertEquals(3, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals(4, v2.getValue());
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void doubleDoubleCmp() {
		ValueWrapper v1 = new ValueWrapper(3.00000005);
		ValueWrapper v2 = new ValueWrapper(3.00000006);
		
		assertEquals(-1, v1.numCompare(v2.getValue()));
		assertEquals(3.00000005, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(3.00000006, v2.getValue());
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void intDoubleCmp() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(3.000001);
		
		assertEquals(-1, v1.numCompare(v2.getValue()));
		assertEquals(3, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
		assertEquals(3.000001, v2.getValue());
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void doubleIntCmp() {
		ValueWrapper v1 = new ValueWrapper(3.9999);
		ValueWrapper v2 = new ValueWrapper(4);
		
		assertEquals(-1, v1.numCompare(v2.getValue()));
		assertEquals(3.9999, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
		assertEquals(4, v2.getValue());
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void stringIntStringIntCmp() {
		ValueWrapper v1 = new ValueWrapper("3");
		ValueWrapper v2 = new ValueWrapper("-9");
		
		assertEquals(1, v1.numCompare(v2.getValue()));
		assertEquals("3", v1.getValue());
		assertTrue(v1.getValue() instanceof String);
		assertEquals("-9", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringDoubleStringIntCmp() {
		ValueWrapper v1 = new ValueWrapper("-8.9999");
		ValueWrapper v2 = new ValueWrapper("-9");
		
		
		assertEquals(1, v1.numCompare(v2.getValue()));
		assertEquals("-8.9999", v1.getValue());
		assertTrue(v1.getValue() instanceof String);
		assertEquals("-9", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringIntStringDoubleCmp() {
		ValueWrapper v1 = new ValueWrapper("100");
		ValueWrapper v2 = new ValueWrapper("1.0E2");
		
		assertEquals(0, v1.numCompare(v2.getValue()));
		assertEquals("100", v1.getValue());
		assertTrue(v1.getValue() instanceof String);
		assertEquals("1.0E2", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringDoubleStringDoubleCmp() {
		ValueWrapper v1 = new ValueWrapper("-2.1E3");
		ValueWrapper v2 = new ValueWrapper("-2100.000");
		
		assertEquals(0, v1.numCompare(v2.getValue()));
		assertEquals("-2.1E3", v1.getValue());
		assertTrue(v1.getValue() instanceof String);
		assertEquals("-2100.000", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringIntNullCmp() {
		ValueWrapper v1 = new ValueWrapper("0.0");
		ValueWrapper v2 = new ValueWrapper(null);
		
		assertEquals(0, v1.numCompare(v2.getValue()));
		assertEquals("0.0", v1.getValue());
		assertTrue(v1.getValue() instanceof String);
		assertNull(v2.getValue());
	}
	
	@Test
	public void nullStringDoubleCmp() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper("+00.00001");
		
		assertEquals(-1, v1.numCompare(v2.getValue()));
		assertNull(v1.getValue());
		assertEquals("+00.00001", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void nullStringIntCmp() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper("-0");
		
		assertEquals(0, v1.numCompare(v2.getValue()));
		
		assertNull(v1.getValue());
		assertEquals("-0", v2.getValue());
		assertTrue(v2.getValue() instanceof String);
	}
	
	@Test
	public void stringDoubleNullCmp() {
		ValueWrapper v1 = new ValueWrapper("-999.11");
		ValueWrapper v2 = new ValueWrapper(null);
		
		assertEquals(-1, v1.numCompare(v2.getValue()));
		assertEquals("-999.11", v1.getValue());
		assertTrue(v1.getValue() instanceof String);
		assertNull(v2.getValue());
	}
}
