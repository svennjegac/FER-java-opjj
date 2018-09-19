package hr.fer.zemris.java.hw06.shell;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class UtilShellTest {

	@Test
	public void empty() {
		List<String> list = UtilShell.extractArguments("");
		
		assertEquals(0, list.size());
		//assertEquals(list.get(0), "haha");
	}
	
	@Test
	public void empty2() {
		List<String> list = UtilShell.extractArguments(" ");
		
		assertEquals(0, list.size());
		//assertEquals(list.get(0), "haha");
	}
	
	@Test
	public void simple() {
		List<String> list = UtilShell.extractArguments("haha");
		
		assertEquals(1, list.size());
		assertEquals(list.get(0), "haha");
	}
	
	@Test
	public void simple2() {
		List<String> list = UtilShell.extractArguments("haha.txt xx");
		
		assertEquals(2, list.size());
		assertEquals(list.get(0), "haha.txt");
		assertEquals(list.get(1), "xx");
	}
	
	@Test
	public void simple3() {
		List<String> list = UtilShell.extractArguments("haha.txt xx zz.yy\\");
		
		assertEquals(3, list.size());
		assertEquals(list.get(0), "haha.txt");
		assertEquals(list.get(1), "xx");
		assertEquals(list.get(2), "zz.yy\\");
	}
	
	@Test
	public void simple4() {
		List<String> list = UtilShell.extractArguments("ha\"\\\\\"\"ha.txt\" xx zz.yy\\ \\\\\"");
		
		assertEquals(4, list.size());
		assertEquals(list.get(0), "ha\"\\\\\"\"ha.txt\"");
		assertEquals(list.get(1), "xx");
		assertEquals(list.get(2), "zz.yy\\");
		assertEquals(list.get(3), "\\\\\"");
	}
	
	@Test
	public void quotes1() {
		List<String> list = UtilShell.extractArguments("\"file.txt\"");
		
		assertEquals(1, list.size());
		assertEquals(list.get(0), "file.txt");
	}
	
	@Test
	public void quotes2() {
		List<String> list = UtilShell.extractArguments("\"fi\\le.txt\"");
		
		assertEquals(1, list.size());
		assertEquals(list.get(0), "fi\\le.txt");
	}
	
	@Test
	public void quotes3() {
		List<String> list = UtilShell.extractArguments("\"fi\\\\le.txt\"");
		
		assertEquals(1, list.size());
		assertEquals(list.get(0), "fi\\le.txt");
	}
	
	@Test
	public void quotes4() {
		List<String> list = UtilShell.extractArguments("\"fi\\\\\\\\le.txt\"");
		
		assertEquals(1, list.size());
		assertEquals(list.get(0), "fi\\\\le.txt");
	}
	
	@Test
	public void quotes5() {
		List<String> list = UtilShell.extractArguments("\"files\\docs\\dir\\\\\\\"Docs and settings\\\"\"");
		
		assertEquals(1, list.size());
		assertEquals(list.get(0), "files\\docs\\dir\\\"Docs and settings\"");
	}
	
	@Test
	public void quotes6() {
		List<String> list = UtilShell.extractArguments("\"br.t\\\\xt\" \"dr.t\\\"xt\\\\\"");
		
		assertEquals(2, list.size());
		assertEquals(list.get(0), "br.t\\xt");
		assertEquals(list.get(1), "dr.t\"xt\\");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void quotes7() {
		List<String> list = UtilShell.extractArguments("\"br.t\\\\xt\"c \"dr.t\\\"xt\\\\\"");
	}
	
	@Test
	public void quotes8() {
		List<String> list = UtilShell.extractArguments("   \"br.t\\\\xt\"  	 ");
		
		assertEquals(1, list.size());
		assertEquals(list.get(0), "br.t\\xt");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void quotes9() {
		List<String> list = UtilShell.extractArguments("   \"br.t\\\\xt  	 ");
	}
	
	@Test
	public void quotes10() {
		List<String> list = UtilShell.extractArguments("   q\"br.t\\\\xt\"  \"\\\\\"	 ");
		
		assertEquals(2, list.size());
		assertEquals(list.get(0), "q\"br.t\\\\xt\"");
		assertEquals(list.get(1), "\\");
	}

	@Test(expected=IllegalArgumentException.class)
	public void quotes11() {
		List<String> list = UtilShell.extractArguments("   \"\\\"  	 ");
	}
}
