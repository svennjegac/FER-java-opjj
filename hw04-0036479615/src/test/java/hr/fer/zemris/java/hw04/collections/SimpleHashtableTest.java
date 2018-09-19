package hr.fer.zemris.java.hw04.collections;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import hr.fer.zemris.java.hw04.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTest {

	@Test
	public void getNull() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>();
		
		assertNull(sh.get(null));
		assertNull(sh.get("kosd"));
		assertEquals(0, sh.size());
		assertTrue(sh.isEmpty());
	}
	
	@Test
	public void get0() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>();
		
		assertNull(sh.get("kosd"));
		assertEquals(0, sh.size());
		assertTrue(sh.isEmpty());
	}
	
	@Test
	public void getPut0() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>();
		
		sh.put("bok", 2);
		assertNull(sh.get("kosd"));
		assertEquals((int) 2, (int) sh.get("bok"));
		assertFalse(sh.isEmpty());
		assertEquals(1, sh.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void putNull() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>();
		sh.put(null, 2);
	}
	
	@Test
	public void getPut1() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>();
		
		sh.put("bok", 2);
		sh.put("bok", null);
		assertNull(sh.get("kosd"));
		assertNull(sh.get("bok"));
		assertEquals(1, sh.size());
		sh.put("hi", 2);
		assertEquals(2, sh.size());
		assertEquals((int) 2, (int) sh.get("hi"));
	}
	
	@Test
	public void clear() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>();
		
		assertEquals(0, sh.size());
		sh.clear();
		assertEquals(0, sh.size());
		
		sh.put("a", 2);
		assertEquals(1, sh.size());
		assertEquals((int) 2, (int) sh.get("a"));
		sh.clear();
		assertEquals(0, sh.size());
		assertFalse(sh.containsValue(2));
		
		sh.put("a", 3);
		sh.put("aj", 38);
		sh.put("ah", 37);
		sh.put("ah", 36);
		sh.put("ae", 35);
		assertEquals(4, sh.size());
		
		sh.clear();
		assertEquals(0, sh.size());
		assertFalse(sh.containsValue(3));
		assertFalse(sh.containsValue(38));
		assertFalse(sh.containsValue(37));
		assertFalse(sh.containsValue(36));
		assertFalse(sh.containsValue(35));
	}
	
	@Test
	public void containsKey() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>();
		
		assertFalse(sh.containsKey(null));
		sh.put("a", null);
		assertFalse(sh.containsKey(null));
		assertTrue(sh.containsKey("a"));
		sh.remove("a");
		assertFalse(sh.containsKey("a"));
	}
	
	@Test
	public void containsValue() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>();
		
		assertFalse(sh.containsValue(null));
		assertFalse(sh.containsValue(2));
		
		sh.put("a", 4);
		sh.put("b", null);
		
		assertTrue(sh.containsValue(null));
		assertTrue(sh.containsValue(4));
		
		sh.remove("b");
		assertFalse(sh.containsValue(null));
		assertTrue(sh.containsValue(4));
		assertEquals(1, sh.size());
		assertFalse(sh.isEmpty());
	}
	
	@Test
	public void removePutContainsKey() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>();
		
		assertFalse(sh.containsKey("s"));
		assertEquals(0, sh.size());
		sh.remove("s");
		assertFalse(sh.containsKey("s"));
		assertEquals(0, sh.size());
		sh.put("s", 1);
		assertTrue(sh.containsKey("s"));
		assertEquals(1, sh.size());
		sh.remove("s");
		assertFalse(sh.containsKey("s"));
		assertEquals(0, sh.size());
		assertNull(sh.get("s"));
		
		sh.put("s", 1);
		sh.put("w", 5);
		sh.put("t", 2);
		assertEquals(3, sh.size());
		assertEquals((int) 1, (int) sh.get("s"));
		assertEquals((int) 2, (int) sh.get("t"));
		assertEquals((int) 5, (int) sh.get("w"));
		assertTrue(sh.containsKey("s"));
		assertTrue(sh.containsKey("w"));
		assertTrue(sh.containsKey("t"));
		
		
		sh.remove("t");
		assertEquals(2, sh.size());
		assertEquals((int) 1, (int) sh.get("s"));
		assertNull(sh.get("t"));
		assertEquals((int) 5, (int) sh.get("w"));
		assertTrue(sh.containsKey("s"));
		assertTrue(sh.containsKey("w"));
		assertFalse(sh.containsKey("t"));
		
		sh.remove("s");
		sh.remove("t");
		sh.remove("w");
		
		assertEquals(0, sh.size());
		assertTrue(sh.isEmpty());
	}
	
	@Test
	public void removePutContainsKey2() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(3);
		
		sh.put("x", 10);
		sh.put("s", 1);
		sh.put("w", 5);
		sh.put("t", 2);
		assertEquals(4, sh.size());
		assertEquals((int) 1, (int) sh.get("s"));
		assertEquals((int) 2, (int) sh.get("t"));
		assertEquals((int) 5, (int) sh.get("w"));
		assertTrue(sh.containsKey("s"));
		assertTrue(sh.containsKey("w"));
		assertTrue(sh.containsKey("x"));
		assertTrue(sh.containsKey("t"));
		
		sh.remove("x");
		assertEquals(3, sh.size());
		assertEquals((int) 1, (int) sh.get("s"));
		assertEquals((int) 2, (int) sh.get("t"));
		assertEquals((int) 5, (int) sh.get("w"));
		assertNull(sh.get("x"));
		assertTrue(sh.containsKey("s"));
		assertTrue(sh.containsKey("w"));
		assertFalse(sh.containsKey("x"));
		assertTrue(sh.containsKey("t"));
		
		
		sh.remove("w");
		assertEquals(2, sh.size());
		assertEquals((int) 1, (int) sh.get("s"));
		assertEquals((int) 2, (int) sh.get("t"));
		assertNull(sh.get("w"));
		assertTrue(sh.containsKey("s"));
		assertFalse(sh.containsKey("w"));
		assertFalse(sh.containsKey("x"));
		assertTrue(sh.containsKey("t"));
		
		sh.put("a", 22);
		sh.put("b", 333);
		sh.put("c", 444);
		assertEquals(5, sh.size());
		assertEquals((int) 1, (int) sh.get("s"));
		assertEquals((int) 2, (int) sh.get("t"));
		assertEquals((int) 22, (int) sh.get("a"));
		assertEquals((int) 333, (int) sh.get("b"));
		assertEquals((int) 444, (int) sh.get("c"));
		assertNull(sh.get("w"));
		assertTrue(sh.containsKey("s"));
		assertFalse(sh.containsKey("w"));
		assertFalse(sh.containsKey("x"));
		assertTrue(sh.containsKey("t"));
		assertTrue(sh.containsKey("a"));
		assertTrue(sh.containsKey("b"));
		assertTrue(sh.containsKey("c"));
		
		sh.remove("a");
		assertEquals(4, sh.size());
		assertEquals((int) 1, (int) sh.get("s"));
		assertEquals((int) 2, (int) sh.get("t"));
		assertEquals((int) 333, (int) sh.get("b"));
		assertEquals((int) 444, (int) sh.get("c"));
		assertNull(sh.get("w"));
		assertNull(sh.get("a"));
		assertTrue(sh.containsKey("s"));
		assertFalse(sh.containsKey("w"));
		assertFalse(sh.containsKey("x"));
		assertTrue(sh.containsKey("t"));
		assertFalse(sh.containsKey("a"));
		assertTrue(sh.containsKey("b"));
		assertTrue(sh.containsKey("c"));
		
		sh.put("s", 3);
		sh.put("t", 4);
		sh.put("b", 11);
		sh.put("c", 0);
		assertEquals(4, sh.size());
		assertEquals((int) 3, (int) sh.get("s"));
		assertEquals((int) 4, (int) sh.get("t"));
		assertEquals((int) 11, (int) sh.get("b"));
		assertEquals((int) 0, (int) sh.get("c"));
		
		sh.remove("s");
		sh.remove("b");
		sh.remove("c");
		sh.remove("t");
		
		assertTrue(sh.isEmpty());
		assertEquals(0, sh.size());
	}
	
	@Test
	public void removePutContainsValue() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(6);
		
		sh.put("x", 10);
		sh.put("s", 1);
		sh.put("w", 5);
		sh.put("t", 2);
		assertTrue(sh.containsValue(10));
		assertTrue(sh.containsValue(1));
		assertTrue(sh.containsValue(5));
		assertTrue(sh.containsValue(2));
		
		
		sh.remove("x");
		assertFalse(sh.containsValue(10));
		assertTrue(sh.containsValue(1));
		assertTrue(sh.containsValue(5));
		assertTrue(sh.containsValue(2));
		
		
		sh.remove("w");
		assertFalse(sh.containsValue(10));
		assertTrue(sh.containsValue(1));
		assertFalse(sh.containsValue(5));
		assertTrue(sh.containsValue(2));
		
		sh.put("a", 22);
		sh.put("b", 333);
		sh.put("c", 444);
		assertFalse(sh.containsValue(10));
		assertTrue(sh.containsValue(1));
		assertFalse(sh.containsValue(5));
		assertTrue(sh.containsValue(2));
		assertTrue(sh.containsValue(22));
		assertTrue(sh.containsValue(333));
		assertTrue(sh.containsValue(444));
		
		sh.remove("c");
		assertFalse(sh.containsValue(10));
		assertTrue(sh.containsValue(1));
		assertFalse(sh.containsValue(5));
		assertTrue(sh.containsValue(2));
		assertTrue(sh.containsValue(22));
		assertTrue(sh.containsValue(333));
		assertFalse(sh.containsValue(444));
		
		sh.put("s", 3);
		sh.put("t", 4);
		sh.put("b", 11);
		sh.put("c", 0);
		assertFalse(sh.containsValue(10));
		assertFalse(sh.containsValue(1));
		assertFalse(sh.containsValue(5));
		assertFalse(sh.containsValue(2));
		assertTrue(sh.containsValue(22));
		assertFalse(sh.containsValue(333));
		assertFalse(sh.containsValue(444));
		assertTrue(sh.containsValue(3));
		assertTrue(sh.containsValue(4));
		assertTrue(sh.containsValue(11));
		assertTrue(sh.containsValue(0));
	}
	
	@Test(expected=NoSuchElementException.class)
	public void iteratorNext() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>();
		
		Iterator<TableEntry<String, Integer>> iter = sh.iterator();
		
		iter.next();
	}
	
	@Test
	public void iterator1() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>();
		
		Iterator<TableEntry<String, Integer>> iter = sh.iterator();
		
		int i = 0;
		while (iter.hasNext()) {
			iter.next();
			i++;
		}
		assertEquals(0, i);
		
		sh.put("", 3);
		
		iter = sh.iterator();
		i = 0;
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			if (e.getKey().equals("")) {
				i++;
			}
		}
		assertEquals(1, i);
	}
	
	@Test
	public void iterator2() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(1);
		
		Iterator<TableEntry<String, Integer>> iter = sh.iterator();
		
		int i = 0;
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			i++;
		}
		assertEquals(0, i);
		
		sh.put("a", 1);
		sh.put("b", 2);
		sh.put("c", 3);
		sh.put("d", 4);
		
		iter = sh.iterator();
		i = 0;
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			i++;
		}
		assertEquals(4, i);
		
		iter = sh.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			
			if (e.getKey().equals("a")) {
				iter.remove();
			} else if (e.getKey().equals("b")) {
				iter.remove();
			}
		}
		
		i = 0;
		iter = sh.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			if (e.getKey().equals("c") || e.getKey().equals("d")) {
				i++;
			}
			
		}
		assertEquals(2, i);
	}
	
	@Test
	public void iterator3() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(1);
		
		Iterator<TableEntry<String, Integer>> iter = sh.iterator();
		
		int i = 0;
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			i++;
		}
		assertEquals(0, i);
		
		sh.put("a", 1);
		sh.put("b", 2);
		sh.put("c", 3);
		sh.put("d", 4);
		
		iter = sh.iterator();
		i = 0;
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			i++;
		}
		assertEquals(4, i);
		
		iter = sh.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			
			if (e.getKey().equals("b")) {
				iter.remove();
			} else if (e.getKey().equals("c")) {
				iter.remove();
			}
		}
		
		i = 0;
		iter = sh.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			if (e.getKey().equals("a") || e.getKey().equals("d")) {
				i++;
			}
		}
		assertEquals(2, i);
	}
	
	@Test
	public void iterator4() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(1);
		
		Iterator<TableEntry<String, Integer>> iter = sh.iterator();
		
		int i = 0;
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			i++;
		}
		assertEquals(0, i);
		
		sh.put("a", 1);
		sh.put("b", 2);
		sh.put("c", 3);
		sh.put("d", 4);
		
		iter = sh.iterator();
		i = 0;
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			i++;
		}
		assertEquals(4, i);
		
		iter = sh.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			
			if (e.getKey().equals("c")) {
				iter.remove();
			} else if (e.getKey().equals("d")) {
				iter.remove();
			}
		}
		
		i = 0;
		iter = sh.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			if (e.getKey().equals("a") || e.getKey().equals("b")) {
				i++;
			}
		}
		assertEquals(2, i);
	}
	
	@Test
	public void iterator5() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(4);
		
		Iterator<TableEntry<String, Integer>> iter = sh.iterator();
		
		int i = 0;
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			i++;
		}
		assertEquals(0, i);
		
		sh.put("a", 1);
		sh.put("e", 6);
		sh.put("d", 4);
		
		iter = sh.iterator();
		i = 0;
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			i++;
		}
		assertEquals(3, i);
		
		iter = sh.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			
			if (e.getKey().equals("e")) {
				iter.remove();
			} else if (e.getKey().equals("")) {
				iter.remove();
			}
		}
		
		i = 0;
		iter = sh.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			if (e.getKey().equals("a") || e.getKey().equals("d")) {
				i++;
			}
		}
		assertEquals(2, i);
	}
	
	
	@Test
	public void iterator6() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(4);
		
		Iterator<TableEntry<String, Integer>> iter = sh.iterator();
		
		int i = 0;
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			i++;
		}
		assertEquals(0, i);
		
		sh.put("a", 1);
		sh.put("e", 6);
		sh.put("d", 4);
		
		iter = sh.iterator();
		i = 0;
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			i++;
		}
		assertEquals(3, i);
		
		iter = sh.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			
			if (e.getKey().equals("a")) {
				iter.remove();
			} else if (e.getKey().equals("")) {
				iter.remove();
			}
		}
		
		i = 0;
		iter = sh.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			if (e.getKey().equals("e") || e.getKey().equals("d")) {
				i++;
			}
		}
		assertEquals(2, i);
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void iteratorRemove() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(3);
		
		sh.put("ab", 1);
		sh.put("xx", 2);
		sh.put("fr", 3);
		
		Iterator<TableEntry<String, Integer>> iter = sh.iterator();
		
		iter = sh.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			
			if (e.getKey().equals("xx")) {
				iter.remove();
			} else if (e.getKey().equals("fr")) {
				iter.remove();
			}
			
			sh.remove("ab");
		}
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void iteratorPut() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(3);
		
		sh.put("ab", 1);
		sh.put("xx", 2);
		sh.put("fr", 3);
		
		Iterator<TableEntry<String, Integer>> iter = sh.iterator();
		
		iter = sh.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			
			sh.put("c", 3);
			
			if (e.getKey().equals("xx")) {
				iter.remove();
			} else if (e.getKey().equals("fr")) {
				iter.remove();
			}
		}
	}
	
	public void iteratorPutOk() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(3);
		
		sh.put("ab", 1);
		sh.put("xx", 2);
		sh.put("fr", 3);
		
		Iterator<TableEntry<String, Integer>> iter = sh.iterator();
		
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			System.out.println(e.getKey() + "...." + e.getValue());
		}
		
		iter = sh.iterator();
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			
			sh.put("ab", 3);
			
			if (e.getKey().equals("xx")) {
				iter.remove();
			} else if (e.getKey().equals("fr")) {
				iter.remove();
			}
		}
		
		assertEquals(3, (int) sh.get("ab"));
		assertEquals(3, sh.size());
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void iteratorClear() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(3);
		
		sh.put("a", 1);
		
		Iterator<TableEntry<String, Integer>> iter = sh.iterator();
		
		sh.clear();
		
		while (iter.hasNext()) {
			TableEntry<String, Integer> e = iter.next();
			
			sh.put("ab", 3);
			
			if (e.getKey().equals("xx")) {
				iter.remove();
			} else if (e.getKey().equals("fr")) {
				iter.remove();
			}
		}
		
		assertEquals(3, (int) sh.get("ab"));
		assertEquals(3, sh.size());
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void twoIterators() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(3);
		
		sh.put("ab", 1);
		sh.put("xx", 2);
		sh.put("fr", 3);
		
		Iterator<TableEntry<String, Integer>> iter = sh.iterator();
		Iterator<TableEntry<String, Integer>> iter2 = sh.iterator();
		
		while (iter.hasNext()) {
			iter2.next();
			iter2.remove();
		}
	}
	
	@Test(expected=IllegalStateException.class)
	public void fakeRemove() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(3);
		
		sh.put("a", 1);
		
		Iterator<TableEntry<String, Integer>> iter = sh.iterator();
		iter.remove();
	}
	
	@Test(expected=IllegalStateException.class)
	public void twoRemove() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(3);
		
		sh.put("a", 1);
		
		Iterator<TableEntry<String, Integer>> iter = sh.iterator();
		iter.next();
		iter.remove();
		iter.remove();
	}
	
	@Test
	public void emptyColl() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<>(3);
		
		sh.put("a", 1);
		sh.put("b", 2);
		
		Iterator<TableEntry<String, Integer>> iter = sh.iterator();

		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}
		
		assertEquals(0, sh.size());
	}
}
