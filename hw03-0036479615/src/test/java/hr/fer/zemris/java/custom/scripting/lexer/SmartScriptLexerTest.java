package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.*;

import org.junit.Test;

public class SmartScriptLexerTest {
	
	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}

	@Test(expected=SmartScriptLexerException.class)
	public void testNullInput() {
		// must throw!
		new SmartScriptLexer(null);
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals("Empty input must generate only EOF token.", TokenType.EOF_TOKEN_TYPE, lexer.nextToken().getType());
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}

	@Test(expected=SmartScriptLexerException.class)
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}
	
	@Test
	public void testNoActualContent() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");
		
		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "   \r\n\t    "),
			new Token(TokenType.EOF_TOKEN_TYPE, null)
		};

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testTwoWords() {
		// Lets check for several words...
		SmartScriptLexer lexer = new SmartScriptLexer("  Štefanija\r\n\t Automobil   ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "  Štefanija\r\n\t Automobil   "),
			new Token(TokenType.EOF_TOKEN_TYPE, null)
		};

		checkTokenStream(lexer, correctData);
	}

	@Test(expected=SmartScriptLexerException.class)
	public void testWordStartingWithEscape() {
		SmartScriptLexer lexer = new SmartScriptLexer("  \\1st  \r\n\t   ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "  \\1st  \r\n\t   "),
			new Token(TokenType.EOF_TOKEN_TYPE, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testWordStartingWithEscape2() {
		SmartScriptLexer lexer = new SmartScriptLexer("  \\\\1st  \r\n\t   ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "  \\1st  \r\n\t   "),
			new Token(TokenType.EOF_TOKEN_TYPE, null)
		};

		checkTokenStream(lexer, correctData);
	}

	@Test(expected=SmartScriptLexerException.class)
	public void testInvalidEscapeEnding() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \\");  // this is three spaces and a single backslash -- 4 letters string

		// will throw!
		lexer.nextToken();
	}
	
	@Test
	public void testInvalidEscapeEnding2() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \\\\");  // this is three spaces and a single backslash -- 4 letters string

		Token correctData[] = {
			new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "   \\"),
			new Token(TokenType.EOF_TOKEN_TYPE, null)
		};

		checkTokenStream(lexer, correctData);
	}

	@Test(expected=SmartScriptLexerException.class)
	public void testInvalidEscape() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \\a    ");

		// will throw!
		lexer.nextToken();
	}
	
	@Test
	public void testInvalidEscape2() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \\\\a    ");

		Token correctData[] = {
			new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "   \\a    "),
			new Token(TokenType.EOF_TOKEN_TYPE, null)
		};

		checkTokenStream(lexer, correctData);
	}

	@Test(expected=SmartScriptLexerException.class)
	public void testSingleEscapedDigit() {
		SmartScriptLexer lexer = new SmartScriptLexer("  \\1  ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "1"),
			new Token(TokenType.EOF_TOKEN_TYPE, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testSingleEscapedDigit2() {
		SmartScriptLexer lexer = new SmartScriptLexer("  \\\\1  ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "  \\1  "),
			new Token(TokenType.EOF_TOKEN_TYPE, null)
		};

		checkTokenStream(lexer, correctData);
	}

	@Test(expected=SmartScriptLexerException.class)
	public void testWordWithManyEscapes() {
		// Lets check for several words...
		SmartScriptLexer lexer = new SmartScriptLexer("  ab\\1\\2cd\\3 ab\\2\\1cd\\4\\\\ \r\n\t   ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "ab12cd3"),
			new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "ab21cd4\\"), // this is 8-letter long, not nine! Only single backslash!
			new Token(TokenType.EOF_TOKEN_TYPE, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testWordWithManyEscapes2() {
		// Lets check for several words...
		SmartScriptLexer lexer = new SmartScriptLexer("  ab\\\\1\\\\2cd\\\\3 ab\\\\2\\\\1cd\\\\4\\\\\\\\ \r\n\t   ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "  ab\\1\\2cd\\3 ab\\2\\1cd\\4\\\\ \r\n\t   "),
			new Token(TokenType.EOF_TOKEN_TYPE, null)
		};

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testTwoNumbers() {
		// Lets check for several numbers...
		SmartScriptLexer lexer = new SmartScriptLexer("  1234\r\n\t 5678   ");

		Token correctData[] = {
			new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE,"  1234\r\n\t 5678   "),
			new Token(TokenType.EOF_TOKEN_TYPE, null)
		};

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testWordWithManyEscapesAndNumbers() {
		// Lets check for several words...
		SmartScriptLexer lexer = new SmartScriptLexer("  ab\\\\123cd ab\\\\2\\\\1cd\\\\4\\\\\\\\ \r\n\t   ");

		// We expect following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE,"  ab\\123cd ab\\2\\1cd\\4\\\\ \r\n\t   "),
			new Token(TokenType.EOF_TOKEN_TYPE, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testSomeSymbols() {
		// Lets check for several symbols...
		SmartScriptLexer lexer = new SmartScriptLexer("  -.? \r\n\t ##   ");

		Token correctData[] = {
			new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "  -.? \r\n\t ##   "),
			new Token(TokenType.EOF_TOKEN_TYPE, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testCombinedInput() {
		// Lets check for several symbols...
		SmartScriptLexer lexer = new SmartScriptLexer("Janko 3! Jasmina 5; -24");

		Token correctData[] = {
			new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "Janko 3! Jasmina 5; -24"),
			new Token(TokenType.EOF_TOKEN_TYPE, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(SmartScriptLexer lexer, Token[] correctData) {
		int counter = 0;
		for(Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
	}
	
	// ----------------------------------------------------------------------------------------------------------
	// --------------------- Second part of tests; uncomment when everything above works ------------------------
	// ----------------------------------------------------------------------------------------------------------
	
	@Test
	public void testMultipartInput() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("Janko 3# {$FOR i 1 10 2 $}");

		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "Janko 3# "));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "for"));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_TOKEN_TYPE, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, 1));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, 10));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, 2));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.EOF_TOKEN_TYPE, null));
	}
	
	@Test
	public void testEscapeTag() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("Janko 3# \\{$FOR i 1 10 2 $}");

		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "Janko 3# {$FOR i 1 10 2 $}"));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF_TOKEN_TYPE, null));
	}
	
	@Test
	public void testEscapeTag2() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("Janko 3# \\{$FOR i\\\\{{$   fOr   i 1 10.2 -1.4     $}  ");

		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "Janko 3# {$FOR i\\{"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "for"));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_TOKEN_TYPE, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, 1));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_DOUBLE_TOKEN_TYPE, 10.2));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_DOUBLE_TOKEN_TYPE, -1.4));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "  "));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF_TOKEN_TYPE, null));
	}
	
	@Test
	public void testEscapeTag3() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("Janko 3# \\{$FOR i\\\\{{$   spor   i 1 10.2+0.4 "
				+ " \" Ovo je string\" \"Some \\\\ test X\"     $}  ");

		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "Janko 3# {$FOR i\\{"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "spor"));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_TOKEN_TYPE, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, 1));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_DOUBLE_TOKEN_TYPE, 10.2));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR_TOKEN_TYPE, "+"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_DOUBLE_TOKEN_TYPE, 0.4));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, " Ovo je string"));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, "Some \\ test X"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "  "));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF_TOKEN_TYPE, null));
	}
	
	@Test
	public void testEscapeTag4() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$ =   i_xxx2 @r_s3  \"Joe \\\"Long\\\" Smith\"  $}");

		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "="));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_TOKEN_TYPE, "i_xxx2"));
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION_TOKEN_TYPE, "r_s3"));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, "Joe \"Long\" Smith"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		//checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE, "  "));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF_TOKEN_TYPE, null));
	}
	
	@Test
	public void testEscapeTag5() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("a{$ =-00.3230   i_xxx2@r_s3\"Joe \\\"Long\\\" Smith\"$}");

		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "a"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "="));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_DOUBLE_TOKEN_TYPE, -0.323));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_TOKEN_TYPE, "i_xxx2"));
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION_TOKEN_TYPE, "r_s3"));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, "Joe \"Long\" Smith"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		//checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE, "  "));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF_TOKEN_TYPE, null));
	}
	
	@Test
	public void testEscapeTag6() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("a{$ =*1.323-1.323-*+-2/^3   i_xxx2@r_s3\"Joe \\\"Long\\\" Smith\"$}");

		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "a"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "="));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR_TOKEN_TYPE, "*"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_DOUBLE_TOKEN_TYPE, 1.323));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_DOUBLE_TOKEN_TYPE, -1.323));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR_TOKEN_TYPE, "-"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR_TOKEN_TYPE, "*"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR_TOKEN_TYPE, "+"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, -2));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR_TOKEN_TYPE, "/"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR_TOKEN_TYPE, "^"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, 3));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_TOKEN_TYPE, "i_xxx2"));
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION_TOKEN_TYPE, "r_s3"));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, "Joe \"Long\" Smith"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		//checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE, "  "));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF_TOKEN_TYPE, null));
	}
	
	@Test
	public void testEscapeTag7() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("a{$miš \"\"$}");

		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "a"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "miš"));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, ""));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		//checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE, "  "));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF_TOKEN_TYPE, null));
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testWillFail() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$?$}");

		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testWillFail1() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$3$}");

		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testWillFail2() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$fix #$}");

		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testWillFail3() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$fix \n $}");

		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testWillFail4() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$fix \" $}");

		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testWillFail5() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$fix @3a $}");

		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testWillFail6() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$@ @a $}");

		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testWillFail7() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$= _a32 $}");

		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testWillFail8() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$= a32 \"\\a\" $}");

		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test(expected=SmartScriptLexerException.class)
	public void testWillFail9() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$= a32 \\ \"a\" $}");

		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test
	public void testInputs() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$= 22a_32 $}");

		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "="));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, 22));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_TOKEN_TYPE, "a_32"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.EOF_TOKEN_TYPE, null));
	}
	
	@Test
	public void testInputs2() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$= 22a_32\"\"\" \"   \"a\" \"\n\r\" \"\\\\\" $}");

		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "="));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, 22));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_TOKEN_TYPE, "a_32"));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, ""));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, " "));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, "a"));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, "\n\r"));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, "\\"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.EOF_TOKEN_TYPE, null));
	}
	
	@Test
	public void testInputs3() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("\\{$as!**$}\\{{$c22a_32\"\"\" \"   \"a\" \"\n\r\" \"\\\\\" $}");

		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "{$as!**$}{"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "c22a_32"));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, ""));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, " "));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, "a"));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, "\n\r"));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, "\\"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.EOF_TOKEN_TYPE, null));
	}
	
	@Test
	public void testInputs4() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("{$FOR x     $}\\{${$   EnD$}");

		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "for"));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_TOKEN_TYPE, "x"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "{$"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "end"));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.EOF_TOKEN_TYPE, null));
	}
	
	@Test
	public void testInputs5() {
		// Test input which has parts which are tokenized by different rules...
		SmartScriptLexer lexer = new SmartScriptLexer("This is sample text.{$ FOR i 1 10 1 $}"
				+ "This is {$= i $}-th time this message is generated."
				+ "{$END$}"
				+ "{$FOR i 0 10 2 $} "
				+ "sin({$=i$}^2) = {$= i i * @sin  \"0.000\" @decfmt $}"
				+ "{$END$}"
		);

		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "This is sample text."));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "for"));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_TOKEN_TYPE, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, 1));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, 10));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, 1));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "This is "));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "="));
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_TOKEN_TYPE, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "-th time this message is generated."));
		
		
		
		
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "end"));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "for"));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_TOKEN_TYPE, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, 0));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, 10));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, 2));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, " sin("));
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "="));
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_TOKEN_TYPE, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, "^2) = "));
		
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "="));
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_TOKEN_TYPE, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE_TOKEN_TYPE, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR_TOKEN_TYPE, "*"));
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION_TOKEN_TYPE, "sin"));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING_TOKEN_TYPE, "0.000"));
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION_TOKEN_TYPE, "decfmt"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_TAG_TOKEN_TYPE, "{$"));
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_NAME_TOKEN_TYPE, "end"));
		
		lexer.setState(SmartScriptLexerState.TAG_STATE);
		
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSE_TAG_TOKEN_TYPE, "$}"));
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.EOF_TOKEN_TYPE, null));
	}
	
	private void checkToken(Token actual, Token expected) {
		String msg = "Token are not equal.";
		assertEquals(msg, expected.getType(), actual.getType());
		assertEquals(msg, expected.getValue(), actual.getValue());
	}
}
