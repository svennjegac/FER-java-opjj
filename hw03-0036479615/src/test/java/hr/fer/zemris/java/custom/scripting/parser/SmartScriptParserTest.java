package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {

	private void exec(String text) {
		SmartScriptParser parser = new SmartScriptParser(text);
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void crash() {
		SmartScriptParser parser = new SmartScriptParser("{$for a 1 1");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void crash2() {
		SmartScriptParser parser = new SmartScriptParser("{$for a 1 1 1");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void crash3() {
		SmartScriptParser parser = new SmartScriptParser("{$for a 1 1 1 1");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void crash4() {
		SmartScriptParser parser = new SmartScriptParser("{$for a 1 $}{$end$}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void crash5() {
		SmartScriptParser parser = new SmartScriptParser("{$for a 1 1 1 1$}{$end$}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void crash6() {
		SmartScriptParser parser = new SmartScriptParser("{$c a 1 1$}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void crash7() {
		SmartScriptParser parser = new SmartScriptParser("{$= @_e$}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void crash8() {
		SmartScriptParser parser = new SmartScriptParser("{$= @1a 1 1$}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void crash9() {
		SmartScriptParser parser = new SmartScriptParser("{$= \\ a 1 1$}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void crash10() {
		SmartScriptParser parser = new SmartScriptParser("{$= a 1 1 \"\\{\" $}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void test() {
		SmartScriptParser parser = new SmartScriptParser(null);
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExamplex() {
		exec("{$= @");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExamplex1() {
		exec("{$= @ a");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExamplex2() {
		exec("{$= 1111111111111111111111111111111111111111111111");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExamplex3() {
		exec("{$= @1");
	}
	
	@Test
	public void testExample0() {
		exec("");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExampleEdge() {
		exec("{$}");
	}
	
	@Test
	public void testExample1() {
		exec(" ");
	}
	
	@Test
	public void testExample2() {
		exec("!");
	}

	@Test(expected=SmartScriptParserException.class)
	public void testExample3() {
		exec("{$");
	}
	
	@Test
	public void testExample4() {
		exec("$}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExample5() {
		exec("{$!$}");
	}
	
	@Test
	public void testExample12() {
		exec("{$= \"\\\"\\\\ \\\\  \\\"\" $}");
	}
	
	@Test
	public void testExample21() {
		exec("{$=$}");
	}
	
	@Test
	public void testExample22() {
		exec("\\\\\\{$\\{{$=$}");
	}
	
	@Test
	public void testExample23() {
		exec("{$= \"\\\\ \\\" \\\\ \\\\\"$}");
	}
	
	@Test
	public void testExample24() {
		exec("{$= \"\\\"\\\\ \\\" \\\\ \\\\\\\"\"$}");
	}
	
	@Test
	public void testExample25() {
		exec("{$= -@d_2+*xx@t $}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExample32() {
		exec("{$= \" $}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExample33() {
		exec("{$= \" \\ \" $}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExample34() {
		exec("{$= \" \\{ \" $}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExample35() {
		exec("{$= \" \\n \" $}");
	}
	
	@Test
	public void testExample36() {
		exec("{$= \" \n \t \" $}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExample37() {
		exec("\\{$= \" \\n \" $}");
	}
	
	@Test
	public void testExample38() {
		exec("\\{$= \" \n \" $}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExample39() {
		exec("\\{$= \" \\\" \" $}");
	}
	
	//FUNC NAME
	@Test(expected=SmartScriptParserException.class)
	public void testExample26() {
		exec("{$= @_ $}");
	}
	
	//func name
	@Test(expected=SmartScriptParserException.class)
	public void testExample27() {
		exec("{$= @3 $}");
	}
	
	//func name
	@Test(expected=SmartScriptParserException.class)
	public void testExample28() {
		exec("{$= @# $}");
	}
	
	@Test
	public void testExample29() {
		exec("{$= -@G $}");
	}
	
	@Test
	public void testExample30() {
		exec("{$= -4 $}");
	}
	
	@Test
	public void testExample13() {
		exec("{$= x_2 32x_1@r_ ****2/^ $}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExample14() {
		exec("{$c= x_2 32_1@r_ ****2/^ $}");
	}
	
	@Test
	public void testExample15() {
		exec("aaaaaaaaaa\\\\\\{{{}$\\\\{\\{\\{$\\{{$= x_2 32x_1@r_ ****2/^ $}\\{$\\{$\\{\\{{$=$}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExample6() {
		exec("{$for$}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExample8() {
		exec("{$for @3 $}");
	}
	
	//too many args
	@Test(expected=SmartScriptParserException.class)
	public void testExample11() {
		exec("{$for w_re32_ \"\\\\ \\\\  \\\" \" 2 2.2 2  $}");
	}
	
	//too few args
	@Test(expected=SmartScriptParserException.class)
	public void testExample16() {
		exec("{$for w_re32_ \"\\\\ \\\\  \\\" \" $}");
	}
	
	//stack too many args
	@Test(expected=SmartScriptParserException.class)
	public void testExample17() {
		exec("{$for w_re32_ \"\\\\ \\\\  \\\" \"  3$}");
	}
	
	@Test
	public void testExample18() {
		exec("{$    for w_re32_ \"\\\\ \\\\  \\\" \"  3$}{$  eNd   $}");
	}
	
	@Test
	public void testExample19() {
		exec("{$For w_re32_ \"\\\\ \\\\  \\\" \"  3$}{$  END$}");
	}
	
	@Test
	public void testExample20() {
		exec("{$For w_re32_ \"\\\\ \\\\  \\\" \"  3$}"
				+ "{$    foR w_re32_ \"\\\\ \\\\  \\\" \"  3$}"
				+ "{$  For w_re32_ \"\\\\ \\\\  \\\" \"  3$}"
				+ "{$ for w_re32_ \"\\\\ \\\\  \\\" \"  3$}"
				+ "\\{$\\{{$= d$}"
				+ "{$  EnD$}" + "\\{$\\{{$= d$}"
				+ "\\\\\\{$  END  $}" + "\\{$\\{{$= d$}"
				+ "{$  END$}" + "\\{$For w_re32_  3$}{$  EnD$}"
				+ "{$eND $}");
	}
	
	@Test
	public void testExample31() {
		exec("{$   =  \" \n \t \r \"   $}");
	}
	
	@Test
	public void testExample7() {
		exec("\\{$\\{{$= d$}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExample9() {
		exec("{$end # $}");
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void testExample10() {
		exec("{$end \"\\\\\" $}");
	}
	
	@Test
	public void testExampleminus() {
		SmartScriptParser p = new SmartScriptParser("{$=- 2 -2- 2$}");
		DocumentNode doc = p.getDocumentNode();
		
		assertEquals(1, doc.numberOfChildren());
		if (!(doc.getChild(0) instanceof EchoNode)) {
			fail();
		}
		
		EchoNode e = (EchoNode) doc.getChild(0);
		
		assertEquals("-", e.getElements()[0].asText());
		assertEquals("2", e.getElements()[1].asText());
		assertEquals("-2", e.getElements()[2].asText());
		assertEquals("-", e.getElements()[3].asText());
		assertEquals("2", e.getElements()[4].asText());
		assertEquals(5, e.getElements().length);
	}
	
	@Test
	public void structure0() {
		SmartScriptParser p = new SmartScriptParser("aaaa{$=r$}");
		DocumentNode doc = p.getDocumentNode();
		
		assertEquals(2, doc.numberOfChildren());
		assertEquals(0, doc.getChild(0).numberOfChildren());
		assertEquals(0, doc.getChild(1).numberOfChildren());
	}
	
	@Test
	public void structure1() {
		SmartScriptParser p = new SmartScriptParser("{$for a 1 2 3 $}aaaa{$=$}xx{$END$}{$=$}");
		DocumentNode doc = p.getDocumentNode();
		
		assertEquals(2, doc.numberOfChildren());
		assertEquals(3, doc.getChild(0).numberOfChildren());
		assertEquals(0, doc.getChild(1).numberOfChildren());
	}
	
	@Test
	public void structure2() {
		SmartScriptParser p = new SmartScriptParser(
				"{$for a 1 2 3 $}"
						+ "aaaa{$=$}xx"
						+ "{$for x 2 \" \r \"$}"
							+ "{$=$}xxx!x$}%  \n \t  \\{$f \\\\  x{$= \"\\\"\"  \"\\\\\" $}xx"
						+ "{$   EnD  $}"
				+ "{$END$}"
				+ "{$=$}wwwwwwwwwww4343D3\"21"
		);
		DocumentNode doc = p.getDocumentNode();
		
		assertEquals(3, doc.numberOfChildren());
		assertEquals(4, doc.getChild(0).numberOfChildren());
		assertEquals(4, doc.getChild(0).getChild(3).numberOfChildren());
		assertEquals(0, doc.getChild(1).numberOfChildren());
		assertEquals(0, doc.getChild(2).numberOfChildren());
	}
	
	@Test
	public void structure3() {
		SmartScriptParser p = new SmartScriptParser(loader("document1.txt"));
		DocumentNode doc = p.getDocumentNode();
		
		if (!(doc.getChild(0) instanceof ForLoopNode)) {
			throw new SmartScriptParserException();
		}
		
		if (!(doc.getChild(1) instanceof TextNode)) {
			throw new SmartScriptParserException();
		}
		
		if (!(doc.getChild(2) instanceof EchoNode)) {
			throw new SmartScriptParserException();
		}
		
		if (!(doc.getChild(3) instanceof TextNode)) {
			throw new SmartScriptParserException();
		}
		
		assertEquals(4, doc.numberOfChildren());
		assertEquals(5, doc.getChild(0).numberOfChildren());
		
		
		assertEquals(5, doc.getChild(0).getChild(3).numberOfChildren());
		assertEquals(0, doc.getChild(0).getChild(0).numberOfChildren());
		assertEquals(0, doc.getChild(0).getChild(1).numberOfChildren());
		assertEquals(0, doc.getChild(0).getChild(2).numberOfChildren());
		assertEquals(0, doc.getChild(0).getChild(4).numberOfChildren());
		
		if (!(doc.getChild(0).getChild(0) instanceof TextNode)) {
			throw new SmartScriptParserException();
		}
		
		if (!(doc.getChild(0).getChild(1) instanceof EchoNode)) {
			throw new SmartScriptParserException();
		}
		
		if (!(doc.getChild(0).getChild(2) instanceof TextNode)) {
			throw new SmartScriptParserException();
		}
		
		if (!(doc.getChild(0).getChild(3) instanceof ForLoopNode)) {
			throw new SmartScriptParserException();
		}
		
		
		assertEquals(0, doc.getChild(0).getChild(3).getChild(0).numberOfChildren());
		
		if (!(doc.getChild(0).getChild(3).getChild(0) instanceof TextNode)) {
			throw new SmartScriptParserException();
		}
		
		if (!(doc.getChild(0).getChild(3).getChild(1) instanceof EchoNode)) {
			throw new SmartScriptParserException();
		}
		
		if (!(doc.getChild(0).getChild(3).getChild(2) instanceof TextNode)) {
			throw new SmartScriptParserException();
		}
		
		if (!(doc.getChild(0).getChild(3).getChild(3) instanceof EchoNode)) {
			throw new SmartScriptParserException();
		}
		
		if (!(doc.getChild(0).getChild(3).getChild(4) instanceof TextNode)) {
			throw new SmartScriptParserException();
		}
		
		
		assertEquals(0, doc.getChild(0).getChild(3).getChild(1).numberOfChildren());
		assertEquals(0, doc.getChild(0).getChild(3).getChild(2).numberOfChildren());
		assertEquals(0, doc.getChild(0).getChild(3).getChild(3).numberOfChildren());
		assertEquals(0, doc.getChild(0).getChild(3).getChild(4).numberOfChildren());
		
		assertEquals(0, doc.getChild(1).numberOfChildren());
		assertEquals(0, doc.getChild(2).numberOfChildren());
		assertEquals(0, doc.getChild(3).numberOfChildren());
	}
	
	@Test
	public void structure4() {
		SmartScriptParser p = new SmartScriptParser(loader("document2.txt"));
		
		DocumentNode doc = p.getDocumentNode();
		
		assertEquals(3, doc.numberOfChildren());
		
		assertEquals(0, doc.getChild(0).numberOfChildren());
		assertEquals(0, doc.getChild(1).numberOfChildren());
		assertEquals(0, doc.getChild(2).numberOfChildren());
		
		if (!(doc.getChild(0) instanceof TextNode)) {
			throw new SmartScriptParserException();
		}
		
		if (!(doc.getChild(1) instanceof EchoNode)) {
			throw new SmartScriptParserException();
		}
		
		if (!(doc.getChild(2) instanceof TextNode)) {
			throw new SmartScriptParserException();
		}
		
	}
	
	@Test
	public void structure5() {
		SmartScriptParser p = new SmartScriptParser(loader("document3.txt"));
		
		DocumentNode doc = p.getDocumentNode();
		
		assertEquals(1, doc.numberOfChildren());
		
		if (!(doc.getChild(0) instanceof TextNode)) {
			throw new SmartScriptParserException();
		}
	}
	
	@Test
	public void structure6() {
		SmartScriptParser p = new SmartScriptParser(loader("document4.txt"));
		
		DocumentNode doc = p.getDocumentNode();
		
		assertEquals(1, doc.numberOfChildren());
		
		if (!(doc.getChild(0) instanceof EchoNode)) {
			throw new SmartScriptParserException();
		}
	}
	
	@Test
	public void structure7() {
		SmartScriptParser p = new SmartScriptParser(loader("document5.txt"));
		
		DocumentNode doc = p.getDocumentNode();
		
		//docs children
		assertEquals(4, doc.numberOfChildren());
		
		if (!(doc.getChild(0) instanceof TextNode)) {
			throw new SmartScriptParserException();
		}
		ForLoopNode fl = (ForLoopNode) doc.getChild(1);
		assertEquals("a_2a", fl.getVariable().asText());
		assertEquals("\"\\", fl.getStartExpression().asText());
		assertEquals("21", fl.getEndExpression().asText());
		assertEquals("-2.1", fl.getStepExpression().asText());
		
		assertEquals(5, doc.getChild(1).numberOfChildren());
		assertEquals(0, doc.getChild(1).getChild(0).numberOfChildren());
		if (!(doc.getChild(1).getChild(0) instanceof TextNode)) {
			throw new SmartScriptParserException();
		}
		TextNode t = (TextNode) doc.getChild(1).getChild(0);
		assertEquals("\"\"\"\"\"\\{34#$%%T$T$GGGG$G$\t \r\n", t.getText());
		
		
		assertEquals(0, doc.getChild(1).getChild(1).numberOfChildren());
		if (!(doc.getChild(1).getChild(1) instanceof EchoNode)) {
			fail();
		}
		EchoNode e = (EchoNode) doc.getChild(1).getChild(1);
		assertEquals(e.getElements().length, 6);
		assertEquals("r_", e.getElements()[0].asText());
		assertEquals("*", e.getElements()[1].asText());
		assertEquals("+", e.getElements()[2].asText());
		assertEquals("-2.1", e.getElements()[3].asText());
		assertEquals("xx_2", e.getElements()[4].asText());
		assertEquals("/", e.getElements()[5].asText());
		
		
		
		assertEquals(0, doc.getChild(1).getChild(2).numberOfChildren());
		
		if (!(doc.getChild(1) instanceof ForLoopNode)) {
			throw new SmartScriptParserException();
		}
		if (!(doc.getChild(2) instanceof ForLoopNode)) {
			throw new SmartScriptParserException();
		}
		if (!(doc.getChild(3) instanceof TextNode)) {
			throw new SmartScriptParserException();
		}
	}
	
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			
			while(true) {
				int read = is.read(buffer);
				if(read<1) break;
				bos.write(buffer, 0, read);
			}
			
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch(IOException ex) {
			return null;
		}
	}

}
