package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayIndexedCollectionTest {

	@Test(expected = IllegalArgumentException.class)
	public void constructorCollectionnull0() {
		ArrayIndexedCollection a = new ArrayIndexedCollection(null, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void constructorCollection0() {
		ArrayIndexedCollection a = new ArrayIndexedCollection(0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void constructorCollectionOtherNull() {
		ArrayIndexedCollection a = new ArrayIndexedCollection(null);
	}
	
	@Test
	public void constructorCollection() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add(1);
		
		ArrayIndexedCollection b = new ArrayIndexedCollection(a);
		
		assertEquals(1, b.get(0));
	}
	
	@Test
	public void constructorCollectionCapacity() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add(1);
		a.add(2);
		a.add(3);
		
		ArrayIndexedCollection b = new ArrayIndexedCollection(a, 5);
		assertEquals(3, b.size());
	}
	
	@Test
	public void isEmpty0() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertTrue(array.isEmpty());
	}
	
	@Test
	public void isEmpty1() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add("Pas");
		assertFalse(array.isEmpty());
	}
	
	@Test
	public void isEmpty3() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add("Pas");
		array.add(2);
		array.add(3.8);
		assertFalse(array.isEmpty());
	}
	
	@Test
	public void isEmptyAddRemove() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertTrue(array.isEmpty());
		array.add("Pas");
		array.add(2);
		array.add(3.8);
		assertFalse(array.isEmpty());
		array.remove(2);
		array.remove(0);
		array.remove(0);
		assertTrue(array.isEmpty());
	}
	
	@Test
	public void size0() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertEquals(0, array.size());
	}
	
	@Test
	public void size1() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(3.14);
		assertEquals(1, array.size());
	}
	
	@Test
	public void size5() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(3.1);
		array.add(3);
		array.add("Pas");
		array.add("Lav");
		array.add("Kas");
		assertEquals(5, array.size());
	}
	
	@Test
	public void sizeAddRemove() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(1);
		assertEquals(0, array.size());
		array.add(3.1);
		assertEquals(1, array.size());
		array.add(3);
		assertEquals(2, array.size());
		array.add("Pas");
		array.add("Lav");
		array.add("Kas");
		assertEquals(5, array.size());
		array.remove(3);
		array.remove(0);
		assertEquals(3, array.size());
		Integer i = new Integer(5);
		array.add(i);
		array.add(i);
		assertEquals(5, array.size());
		array.remove(i);
		assertEquals(4, array.size());
		array.remove(i);
		assertEquals(3, array.size());	
	}
	
	@Test
	public void addReallocate() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(1);
		double d = 3.14;
		array.add(d);
		array.add(d);
		array.add(d);
		array.add(d);
		d = 2.88;
		array.add(d);
		assertEquals(5, array.size());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addException() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(1);
		array.add(null);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void insertExIndex() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(1);
		array.insert(5, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void insertExArgument() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(1);
		array.insert(null, 0);
	}
	
	@Test
	public void insert1() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(1);
		array.insert(4, 0);
		assertEquals(1, array.size());
	}
	
	@Test
	public void insert2() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(1);
		array.insert(4, 0);
		array.insert(1, 0);
		assertEquals(2, array.size());
		assertEquals(1, array.get(0));
		assertEquals(4, array.get(1));
	}
	
	@Test
	public void insertEnd() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(1);
		array.insert(5, 0);
		array.insert(6, 1);
		assertEquals(5, array.get(0));
		assertEquals(6, array.get(1));
		array.insert(4, 1);
		assertEquals(4, array.get(1));
		assertEquals(5, array.get(0));
		assertEquals(6, array.get(2));
	}
	
	@Test
	public void removeBooleanNull() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertFalse(array.remove(null));
	}
	
	@Test
	public void removeBoolean0() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertFalse(array.remove(new Integer(5)));
	}
	
	@Test
	public void removeBoolean1() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(new Integer(5));
		assertEquals(1, array.size());
		assertTrue(array.remove(new Integer(5)));
		assertEquals(0, array.size());
		assertFalse(array.remove(new Integer(5)));
	}
	
	@Test
	public void removeBooleanMiddle() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(new Integer(5));
		array.add(new Integer(6));
		array.add(new Integer(6));
		array.add(new Integer(6));
		array.add(new Integer(7));
		
		array.remove(new Integer(5));
		assertEquals(new Integer(6), array.get(0));
		assertEquals(new Integer(6), array.get(1));
		assertEquals(new Integer(6), array.get(2));
		assertEquals(new Integer(7), array.get(3));
	
		array.insert(new Integer(11), 2);
		array.remove(new Integer(7));
		assertEquals(new Integer(11), array.get(2));
		assertEquals(new Integer(6), array.get(3));
		
		array.remove(new Integer(6));
		array.remove(new Integer(6));
		assertEquals(new Integer(11), array.get(0));
		
		array.insert(new Integer(6), 1);
		assertEquals(new Integer(11), array.get(0));
		
		array.remove(1);
		assertEquals(new Integer(11), array.get(0));
		assertEquals(new Integer(6), array.get(1));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void removeVoid0() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(1);
		array.remove(0);
	}
	
	@Test
	public void removeVoid1() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.insert(5, 0);
		array.remove(0);
		assertEquals(0, array.size());
	}
	
	@Test
	public void removeVoidMiddle() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.insert(5, 0);
		array.insert(4, 0);
		array.insert(3, 0);
		array.insert(2, 0);
		array.insert(1, 0);
		array.remove(2);
		assertEquals(4, array.get(2));
	}
	
	@Test
	public void clear0() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertEquals(0, array.size());
		array.clear();
		assertEquals(0, array.size());
	}
	
	@Test
	public void clear1() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		assertEquals(1, array.size());
		array.clear();
		assertEquals(0, array.size());
	}
	
	@Test
	public void clear3() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(1);
		array.add(1);
		array.add(1);
		assertEquals(3, array.size());
		array.clear();
		assertEquals(0, array.size());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void getExIndex0() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.get(0);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void getExIndex10() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.get(10);
	}
	
	@Test
	public void get0() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add(1);
		assertEquals(1, a.get(0));
	}
	
	@Test
	public void get2() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add(1);
		a.add(2);
		a.add(3);
		assertEquals(3, a.get(2));
		assertEquals(2, a.get(1));
	}
	
	@Test
	public void indexOfNull() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		assertEquals(-1, a.indexOf(null));
	}
	
	@Test
	public void indexOf() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		assertEquals(-1, a.indexOf(5));
		a.add(5);
		assertEquals(0, a.indexOf(5));
		a.add(6);
		a.add(7);
		a.add(7);
		assertEquals(1, a.indexOf(6));
		assertEquals(2, a.indexOf(7));
		a.remove(1);
		assertEquals(1, a.indexOf(7));
		a.remove(1);
		assertEquals(1, a.indexOf(7));
	}
	
	@Test
	public void containsNull() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		assertFalse(a.contains(null));
	}
	
	@Test
	public void contains() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		assertFalse(a.contains(null));
		assertFalse(a.contains(5));
		a.add(5);
		assertTrue(a.contains(5));
		assertFalse(a.contains(6));
		a.remove(0);
		assertFalse(a.contains(5));
	}
	
	@Test
	public void toArray0() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		Object[] b = a.toArray();
		assertEquals(0, b.length);
	}
	
	@Test
	public void toArray1() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add(1);
		Object[] b = a.toArray();
		assertEquals(1, b.length);
		assertEquals(1, b[0]);
	}
	
	@Test
	public void toArray3() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add(1);
		a.add(2);
		Object[] b = a.toArray();
		assertEquals(2, b.length);
		assertEquals(1, b[0]);
		assertEquals(2, b[1]);
	}

	@Test
	public void forEach() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		class LocalProcessor extends Processor {
			@Override
			public void process(Object value) {
				assertEquals(5, value);
			}
		};
		Processor processor = new LocalProcessor();
		
		a.add(5);
		a.add(5);
		a.forEach(processor);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addAllNull() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		ArrayIndexedCollection b = new ArrayIndexedCollection();
		
		b.addAll(null);
	}
	
	@Test
	public void addAll00() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		ArrayIndexedCollection b = new ArrayIndexedCollection();
		
		a.addAll(b);
		assertEquals(0, a.size());
	}
	
	@Test
	public void addAll01() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		ArrayIndexedCollection b = new ArrayIndexedCollection();
		
		b.add(5);
		
		a.addAll(b);
		assertEquals(1, a.size());
		assertEquals(5, a.get(0));
	}
	
	@Test
	public void addAll03() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		ArrayIndexedCollection b = new ArrayIndexedCollection();
		
		b.add(5);
		b.add(6);
		b.add(7);
		
		a.addAll(b);
		assertEquals(3, a.size());
		assertEquals(5, a.get(0));
		assertEquals(6, a.get(1));
		assertEquals(7, a.get(2));
	}
	
	@Test
	public void addAll10() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		ArrayIndexedCollection b = new ArrayIndexedCollection();
		
		a.add(5);
		
		a.addAll(b);
		assertEquals(1, a.size());
		assertEquals(5, a.get(0));
	}
	
	@Test
	public void addAll30() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		ArrayIndexedCollection b = new ArrayIndexedCollection();
		
		a.add(5);
		a.add(6);
		a.add(7);
		
		a.addAll(b);
		assertEquals(3, a.size());
		assertEquals(5, a.get(0));
		assertEquals(6, a.get(1));
		assertEquals(7, a.get(2));
	}
	
	@Test
	public void addAll32() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		ArrayIndexedCollection b = new ArrayIndexedCollection();
		
		a.add(5);
		a.add(6);
		a.add(7);
		
		b.add(8);
		b.add(9);
		
		a.addAll(b);
		assertEquals(5, a.size());
		assertEquals(5, a.get(0));
		assertEquals(6, a.get(1));
		assertEquals(7, a.get(2));
		assertEquals(8, a.get(3));
		assertEquals(9, a.get(4));
	}
	
	@Test
	public void addAll13() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		ArrayIndexedCollection b = new ArrayIndexedCollection();
		
		a.add(5);
		
		b.add(6);
		b.add(7);
		b.add(8);
		
		a.addAll(b);
		assertEquals(4, a.size());
		assertEquals(5, a.get(0));
		assertEquals(6, a.get(1));
		assertEquals(7, a.get(2));
		assertEquals(8, a.get(3));
	}
}
