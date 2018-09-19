package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Test;

public class ObjectMultistackTest {

	@Test(expected=EmptyStackException.class)
	public void stackPeekExc() {
		ObjectMultistack s = new ObjectMultistack();
		
		s.peek("a");
	}
	
	@Test(expected=EmptyStackException.class)
	public void stackPopExc() {
		ObjectMultistack s = new ObjectMultistack();
		
		s.pop("a");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void stackPeekNull() {
		ObjectMultistack s = new ObjectMultistack();
		
		s.peek(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void stackPopNull() {
		ObjectMultistack s = new ObjectMultistack();
		
		s.pop(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void stackIsEmpty() {
		ObjectMultistack s = new ObjectMultistack();
		
		s.isEmpty(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void pushNull() {
		ObjectMultistack s = new ObjectMultistack();
		
		s.push("xx", null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void pushNull2() {
		ObjectMultistack s = new ObjectMultistack();
		
		s.push(null, new ValueWrapper(4));
	}
	
	@Test
	public void combined() {
		ObjectMultistack s = new ObjectMultistack();
		
		assertTrue(s.isEmpty("year"));
		
		s.push("year", new ValueWrapper(2000));
		
		assertFalse(s.isEmpty("year"));
		assertTrue(s.isEmpty("price"));
		
		assertEquals(2000, s.peek("year").getValue());
		
		Integer y = (int) s.pop("year").getValue();
		
		assertTrue(s.isEmpty("year"));
	}
	
	@Test
	public void multiPart() {
		ObjectMultistack s = new ObjectMultistack();
		
		assertTrue(s.isEmpty("year"));
		
		s.push("year", new ValueWrapper(2000));
		
		assertEquals(2000, s.peek("year").getValue());
		
		Integer y = (int) s.pop("year").getValue();
		
		assertTrue(s.isEmpty("year"));
		
		s.push("price", new ValueWrapper(100));
		s.push("price", new ValueWrapper(200));
		
		assertEquals(200, s.peek("price").getValue());
		
		s.push("price", new ValueWrapper(300));
		assertEquals(300, s.pop("price").getValue());
		assertEquals(200, s.pop("price").getValue());
		
		s.push("price", new ValueWrapper(500));
		assertEquals(500, s.pop("price").getValue());
		assertEquals(100, s.pop("price").getValue());
		assertTrue(s.isEmpty("price"));
	}
	
	@Test(expected=EmptyStackException.class)
	public void peekEmpty() {
		ObjectMultistack s = new ObjectMultistack();
		
		assertTrue(s.isEmpty("year"));
		
		s.push("year", new ValueWrapper(2000));
		
		assertEquals(2000, s.peek("year").getValue());
		
		Integer y = (int) s.pop("year").getValue();
		
		assertTrue(s.isEmpty("year"));
		
		s.push("price", new ValueWrapper(100));
		s.push("price", new ValueWrapper(200));
		
		assertEquals(200, s.peek("price").getValue());
		
		s.push("price", new ValueWrapper(300));
		assertEquals(300, s.pop("price").getValue());
		assertEquals(200, s.pop("price").getValue());
		
		s.push("price", new ValueWrapper(500));
		assertEquals(500, s.pop("price").getValue());
		assertEquals(100, s.pop("price").getValue());
		assertTrue(s.isEmpty("price"));
		s.peek("price");
	}
	
	@Test(expected=EmptyStackException.class)
	public void popEmpty() {
		ObjectMultistack s = new ObjectMultistack();
		
		assertTrue(s.isEmpty("year"));
		
		s.push("year", new ValueWrapper(2000));
		
		assertEquals(2000, s.peek("year").getValue());
		
		Integer y = (int) s.pop("year").getValue();
		
		assertTrue(s.isEmpty("year"));
		
		s.push("price", new ValueWrapper(100));
		s.push("price", new ValueWrapper(200));
		
		assertEquals(200, s.peek("price").getValue());
		
		s.push("price", new ValueWrapper(300));
		assertEquals(300, s.pop("price").getValue());
		assertEquals(200, s.pop("price").getValue());
		
		s.push("price", new ValueWrapper(500));
		assertEquals(500, s.pop("price").getValue());
		assertEquals(100, s.pop("price").getValue());
		assertTrue(s.isEmpty("price"));
		s.pop("price");
	}
}
