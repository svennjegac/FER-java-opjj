package hr.fer.zemris.bf.qmc;

import static org.junit.Assert.*;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class MaskTest {

	@Test
	public void firstConstructor() {
		Mask m = new Mask(3, 5, false);
		
		assertEquals(m.isCombined(), false);
		assertEquals(m.isDontCare(), false);
		assertArrayEquals(new Integer[]{3}, m.getIndexes().toArray(new Integer[m.getIndexes().size()]));
	}
	
	@Test
	public void secondConstructor() {
		Set<Integer> set = new TreeSet<>();
		set.add(4);
		set.add(5);
		byte[] b = {0, 1, 1, 0};
		
		
		Mask m = new Mask(b, set, true);
		
		assertEquals(m.isCombined(), false);
		assertEquals(m.isDontCare(), true);
		assertArrayEquals(new Integer[]{4, 5}, m.getIndexes().toArray(new Integer[m.getIndexes().size()]));
		set.add(6);
		assertArrayEquals(new Integer[]{4, 5}, m.getIndexes().toArray(new Integer[m.getIndexes().size()]));
		
		Optional<Mask> opt = m.combineWith(new Mask(7, 4, true));
		assertTrue(opt.isPresent());
		b[1] = (byte) 0;
		opt = m.combineWith(new Mask(7, 4, true));
		assertTrue(opt.isPresent());
		assertArrayEquals(new Integer[]{4, 5}, m.getIndexes().toArray(new Integer[m.getIndexes().size()]));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalConstructor() {
		new Mask(-1, 2, true);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalConstructor2() {
		new Mask(4, 0, true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void illegalConstructor3() {
		Set<Integer> set = new TreeSet<>();
		set.add(4);
		set.add(5);
		new Mask(null, set, false);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalConstructor4() {
		new Mask(new byte[]{2, 4}, null, false);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalConstructor5() {
		Set<Integer> set = new TreeSet<>();
		new Mask(null, set, false);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void unmodifiableIndexes() {
		Mask m = new Mask(3, 5, false);
		
		m.getIndexes().add(4);
	}
	
	@Test
	public void testCombinedDontCare() {
		Mask m = new Mask(3, 5, false);
		
		assertFalse(m.isCombined());
		m.setCombined(true);
		assertTrue(m.isCombined());
		m.setCombined(false);
		assertFalse(m.isCombined());
		
		assertFalse(m.isDontCare());
	}
	
	@Test
	public void testCombined2() {
		Set<Integer> set = new TreeSet<>();
		set.add(4);
		set.add(5);
		byte[] b = {0, 1, 1, 0};
		
		
		Mask m = new Mask(b, set, true);
		assertFalse(m.isCombined());
		m.setCombined(true);
		assertTrue(m.isCombined());
		m.setCombined(false);
		assertFalse(m.isCombined());
		assertTrue(m.isDontCare());
	}
	
	@Test
	public void countOfOnes() {
		Mask m = new Mask(3, 5, false);
		assertEquals(2, m.countOfOnes());
		
		m = new Mask(2, 5, false);
		assertEquals(1, m.countOfOnes());
		
		m = new Mask(0, 5, false);
		assertEquals(0, m.countOfOnes());
		
		m = new Mask(3, 2, false);
		assertEquals(2, m.countOfOnes());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void combineWithNull() {
		Set<Integer> set = new TreeSet<>();
		set.add(6);
		byte[] b = {0, 1, 1, 0};
		Mask m = new Mask(b, set, true);
		m.combineWith(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void combineWithIncompatibleMasks() {
		Set<Integer> set = new TreeSet<>();
		set.add(6);
		byte[] b = {0, 1, 1, 0};
		Mask m = new Mask(b, set, true);
		
		Set<Integer> set2 = new TreeSet<>();
		set2.add(7);
		byte[] b2 = {0, 0, 1, 1, 1};
		Mask m2 = new Mask(b2, set2, true);
		
		m.combineWith(m2);
	}
	
	@Test
	public void combineWith() {
		Set<Integer> set = new TreeSet<>();
		set.add(6);
		byte[] b = {0, 1, 1, 0};
		Mask m = new Mask(b, set, true);
		
		Set<Integer> set2 = new TreeSet<>();
		set2.add(7);
		byte[] b2 = {0, 1, 1, 1};
		Mask m2 = new Mask(b2, set2, true);
		
		Optional<Mask> opt = m.combineWith(m2);
		assertFalse(m.isCombined());
		assertFalse(m2.isCombined());
		assertTrue(opt.isPresent());
		assertArrayEquals(new Integer[]{6, 7}, opt.get().getIndexes().toArray(new Integer[opt.get().getIndexes().size()]));
	}
	
	@Test
	public void combineWith2() {
		Set<Integer> set = new TreeSet<>();
		set.add(6);
		byte[] b = {0, 1, 1, 0};
		Mask m = new Mask(b, set, true);
		
		Set<Integer> set2 = new TreeSet<>();
		set2.add(5);
		byte[] b2 = {0, 1, 0, 1};
		Mask m2 = new Mask(b2, set2, true);
		
		Optional<Mask> opt = m.combineWith(m2);
		assertFalse(m.isCombined());
		assertFalse(m2.isCombined());
		assertTrue(!opt.isPresent());
	}
	
	@Test
	public void combineWith3() {
		Set<Integer> set = new TreeSet<>();
		set.add(6);
		set.add(7);
		byte[] b = {0, 1, 1, 2};
		Mask m = new Mask(b, set, true);
		
		Set<Integer> set2 = new TreeSet<>();
		set2.add(5);
		set2.add(4);
		byte[] b2 = {0, 1, 0, 2};
		Mask m2 = new Mask(b2, set2, true);
		
		Optional<Mask> opt = m.combineWith(m2);
		assertFalse(m.isCombined());
		assertFalse(m2.isCombined());
		assertTrue(opt.isPresent());
		assertArrayEquals(new Integer[]{4, 5, 6, 7}, opt.get().getIndexes().toArray(new Integer[opt.get().getIndexes().size()]));
	}
	
	@Test
	public void combineWith4() {
		Set<Integer> set = new TreeSet<>();
		set.add(15);
		set.add(14);
		byte[] b = {1, 1, 1, 2};
		Mask m = new Mask(b, set, true);
		
		Set<Integer> set2 = new TreeSet<>();
		set2.add(4);
		set2.add(5);
		byte[] b2 = {0, 1, 0, 2};
		Mask m2 = new Mask(b2, set2, true);
		
		Optional<Mask> opt = m.combineWith(m2);
		assertFalse(m.isCombined());
		assertFalse(m2.isCombined());
		
		assertTrue(!opt.isPresent());
	}
	
	@Test
	public void combineWith5() {
		Set<Integer> set = new TreeSet<>();
		set.add(15);
		set.add(14);
		byte[] b = {1, 1, 1, 2};
		Mask m = new Mask(b, set, true);
		
		Set<Integer> set2 = new TreeSet<>();
		set2.add(15);
		set2.add(14);
		set2.add(13);
		set2.add(12);
		byte[] b2 = {1, 1, 2, 2};
		Mask m2 = new Mask(b2, set2, true);
		
		Optional<Mask> opt = m.combineWith(m2);
		assertFalse(m.isCombined());
		assertFalse(m2.isCombined());
		
		assertTrue(!opt.isPresent());
	}
	
	@Test
	public void combineWith6() {
		Set<Integer> set = new TreeSet<>();
		set.add(15);
		set.add(14);
		byte[] b = {1, 1, 1, 2};
		Mask m = new Mask(b, set, true);
		
		Set<Integer> set2 = new TreeSet<>();
		set2.add(13);
		set2.add(12);
		byte[] b2 = {1, 1, 0, 2};
		Mask m2 = new Mask(b2, set2, true);
		
		Optional<Mask> opt = m.combineWith(m2);
		assertFalse(m.isCombined());
		assertFalse(m2.isCombined());
		
		assertTrue(opt.isPresent());
		assertTrue(opt.get().isDontCare());
		assertTrue(!opt.get().isCombined());
	
		assertArrayEquals(new Integer[]{12, 13, 14, 15}, opt.get().getIndexes().toArray(new Integer[opt.get().getIndexes().size()]));
	}
	
	@Test
	public void combineWith7() {
		Set<Integer> set = new TreeSet<>();
		set.add(15);
		set.add(14);
		byte[] b = {1, 1, 1, 2};
		Mask m = new Mask(b, set, false);
		
		Set<Integer> set2 = new TreeSet<>();
		set2.add(13);
		set2.add(12);
		byte[] b2 = {1, 1, 0, 2};
		Mask m2 = new Mask(b2, set2, true);
		
		Optional<Mask> opt = m.combineWith(m2);
		assertFalse(m.isCombined());
		assertFalse(m2.isCombined());
		
		assertTrue(opt.isPresent());
		assertTrue(!opt.get().isDontCare());
		assertTrue(!opt.get().isCombined());
		
		assertArrayEquals(new Integer[]{12, 13, 14, 15}, opt.get().getIndexes().toArray(new Integer[opt.get().getIndexes().size()]));
	}
	
	@Test
	public void combineWith8() {
		Set<Integer> set = new TreeSet<>();
		set.add(15);
		set.add(14);
		byte[] b = {1, 1, 1, 2};
		Mask m = new Mask(b, set, true);
		
		Set<Integer> set2 = new TreeSet<>();
		set2.add(13);
		set2.add(12);
		byte[] b2 = {1, 1, 0, 2};
		Mask m2 = new Mask(b2, set2, false);
		
		Optional<Mask> opt = m.combineWith(m2);
		assertFalse(m.isCombined());
		assertFalse(m2.isCombined());
		
		assertTrue(opt.isPresent());
		assertTrue(!opt.get().isDontCare());
		assertTrue(!opt.get().isCombined());
		
		assertArrayEquals(new Integer[]{12, 13, 14, 15}, opt.get().getIndexes().toArray(new Integer[opt.get().getIndexes().size()]));
	}
	
	@Test
	public void combineWith9() {
		Set<Integer> set = new TreeSet<>();
		set.add(15);
		set.add(14);
		byte[] b = {1, 1, 1, 2};
		Mask m = new Mask(b, set, false);
		
		Set<Integer> set2 = new TreeSet<>();
		set2.add(13);
		set2.add(12);
		byte[] b2 = {1, 1, 0, 2};
		Mask m2 = new Mask(b2, set2, false);
		
		Optional<Mask> opt = m.combineWith(m2);
		assertFalse(m.isCombined());
		assertFalse(m2.isCombined());
		
		assertTrue(opt.isPresent());
		assertTrue(!opt.get().isDontCare());
		assertTrue(!opt.get().isCombined());
		
		assertArrayEquals(new Integer[]{12, 13, 14, 15}, opt.get().getIndexes().toArray(new Integer[opt.get().getIndexes().size()]));
	}
	
}
