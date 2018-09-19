package hr.fer.zemris.java.webserver;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

public class RequestContextTest {

	@Test
	public void constructor() throws FileNotFoundException {
		RequestContext rc = new RequestContext(new FileOutputStream("file"), null, null, null);
		
		assertEquals(0, rc.getParameterNames().size());
		assertEquals(0, rc.getPersistentParameterNames().size());
		assertEquals(0, rc.getTemporaryParameterNames().size());
	}
	
	@Test
	public void removeEmpty() throws FileNotFoundException {
		RequestContext rc = new RequestContext(new FileOutputStream("file"), null, null, null);
		
		rc.removeTemporaryParameter(null);
	}
	
	@Test
	public void addRemove() throws FileNotFoundException {
		RequestContext rc = new RequestContext(new FileOutputStream("file"), null, null, null);
		
		rc.setPersistentParameter("hej", "a");
		rc.setPersistentParameter("hej", "b");
		rc.setPersistentParameter("oj", "a");
	
		assertEquals(2, rc.getPersistentParameterNames().size());
		assertTrue(rc.getPersistentParameter("hej").equals("b"));
		assertTrue(rc.getPersistentParameter("oj").equals("a"));
		
		rc.removePersistentParameter("hej");
		assertNull(rc.getPersistentParameter("hej"));
		rc.removePersistentParameter("hej");
		assertNull(rc.getPersistentParameter("hej"));
		assertEquals(1, rc.getPersistentParameterNames().size());
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void unmodifiableParameter() throws FileNotFoundException {
		RequestContext rc = new RequestContext(new FileOutputStream("file"), null, null, null);
		
		rc.getParameterNames().add("aaa");
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void unmodifiablePersistentParameter() throws FileNotFoundException {
		RequestContext rc = new RequestContext(new FileOutputStream("file"), null, null, null);
		
		rc.getPersistentParameterNames().add("aaa");
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void unmodifiableTemporaryParameter() throws FileNotFoundException {
		RequestContext rc = new RequestContext(new FileOutputStream("file"), null, null, null);
		
		rc.getTemporaryParameterNames().add("aaa");
	}

}
