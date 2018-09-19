package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.*;

import org.junit.Test;

public class PrimListModelTest {

	@Test
	public void simpleTest() {
		PrimListModel model = new PrimListModel();
		
		assertEquals(1, model.getSize());
	}
	
	@Test
	public void simpleTest2() {
		PrimListModel model = new PrimListModel();
		
		assertEquals(1, model.getSize());
		model.next();
		assertEquals(2, model.getSize());
	}
	
	@Test
	public void simpleTest3() {
		PrimListModel model = new PrimListModel();
		
		model.next();
		model.next();
		
		int prim = model.getElementAt(1);
		
		assertEquals(2, prim);
	}
	
	@Test
	public void simpleTest4() {
		PrimListModel model = new PrimListModel();
		
		model.next();
		model.next();
		
		int prim = model.getElementAt(2);
		
		assertEquals(3, prim);
	}
	
	@Test
	public void simpleTest5() {
		PrimListModel model = new PrimListModel();
		
		model.next();
		model.next();
		model.next();
		
		int prim = model.getElementAt(3);
		
		assertEquals(5, prim);
	}
}
