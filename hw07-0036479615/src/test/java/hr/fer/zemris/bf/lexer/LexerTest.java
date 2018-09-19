package hr.fer.zemris.bf.lexer;

import static org.junit.Assert.*;

import org.junit.Test;

public class LexerTest {

	@Test
	public void simple() {
		Lexer l = new Lexer("!a and b");
		
		checkToken(l.nextToken(), TokenType.OPERATOR, "not");
		checkToken(l.nextToken(), TokenType.VARIABLE, "A");
		checkToken(l.nextToken(), TokenType.OPERATOR, "and");
		checkToken(l.nextToken(), TokenType.VARIABLE, "B");
		checkToken(l.nextToken(), TokenType.EOF, null);
	}
	
	@Test
	public void simple2() {
		Lexer l = new Lexer("*a AND CE");
		
		checkToken(l.nextToken(), TokenType.OPERATOR, "and");
		checkToken(l.nextToken(), TokenType.VARIABLE, "A");
		checkToken(l.nextToken(), TokenType.OPERATOR, "and");
		checkToken(l.nextToken(), TokenType.VARIABLE, "CE");
		checkToken(l.nextToken(), TokenType.EOF, null);
	}
	
	@Test
	public void simple3() {
		Lexer l = new Lexer("+anda :+:and+ CE");
		
		checkToken(l.nextToken(), TokenType.OPERATOR, "or");
		checkToken(l.nextToken(), TokenType.VARIABLE, "ANDA");
		checkToken(l.nextToken(), TokenType.OPERATOR, "xor");
		checkToken(l.nextToken(), TokenType.OPERATOR, "and");
		checkToken(l.nextToken(), TokenType.OPERATOR, "or");
		checkToken(l.nextToken(), TokenType.VARIABLE, "CE");
		checkToken(l.nextToken(), TokenType.EOF, null);
	}
	
	@Test(expected=LexerException.class)
	public void nullConstructor() {
		new Lexer(null);
	}
	
	@Test(expected=LexerException.class)
	public void unexpectedChar() {
		Lexer l = new Lexer("+anda % :+:and+ CE");
		
		l.nextToken();
		l.nextToken();
		l.nextToken();
	}
	
	@Test(expected=LexerException.class)
	public void failedOperator() {
		Lexer l = new Lexer("+anda :+and+ CE");
		
		l.nextToken();
		l.nextToken();
		l.nextToken();
	}
	
	@Test(expected=LexerException.class)
	public void wrongNumber1() {
		Lexer l = new Lexer("+anda 2 :+:and+ CE");
		
		l.nextToken();
		l.nextToken();
		l.nextToken();
	}
	
	@Test(expected=LexerException.class)
	public void wrongNumber2() {
		Lexer l = new Lexer("+anda 01 :+:and+ CE");
		
		l.nextToken();
		l.nextToken();
		l.nextToken();
	}
	
	@Test(expected=LexerException.class)
	public void wrongNumber3() {
		Lexer l = new Lexer("+anda 10 :+:and+ CE");
		
		l.nextToken();
		l.nextToken();
		l.nextToken();
	}
	
	@Test
	public void complex() {
		Lexer l = new Lexer("+*:+:+and*anda*trUe*1:+:fALse+falsetrue!false_ a1_+a0_1())!false1 0 xor:+:or*not!false01*)");
		
		checkToken(l.nextToken(), TokenType.OPERATOR, "or");
		checkToken(l.nextToken(), TokenType.OPERATOR, "and");
		checkToken(l.nextToken(), TokenType.OPERATOR, "xor");
		checkToken(l.nextToken(), TokenType.OPERATOR, "or");
		checkToken(l.nextToken(), TokenType.OPERATOR, "and");
		checkToken(l.nextToken(), TokenType.OPERATOR, "and");
		checkToken(l.nextToken(), TokenType.VARIABLE, "ANDA");
		checkToken(l.nextToken(), TokenType.OPERATOR, "and");
		checkToken(l.nextToken(), TokenType.CONSTANT, true);
		checkToken(l.nextToken(), TokenType.OPERATOR, "and");
		checkToken(l.nextToken(), TokenType.CONSTANT, true);
		checkToken(l.nextToken(), TokenType.OPERATOR, "xor");
		checkToken(l.nextToken(), TokenType.CONSTANT, false);
		checkToken(l.nextToken(), TokenType.OPERATOR, "or");
		checkToken(l.nextToken(), TokenType.VARIABLE, "FALSETRUE");
		checkToken(l.nextToken(), TokenType.OPERATOR, "not");
		checkToken(l.nextToken(), TokenType.VARIABLE, "FALSE_");
		checkToken(l.nextToken(), TokenType.VARIABLE, "A1_");
		checkToken(l.nextToken(), TokenType.OPERATOR, "or");
		checkToken(l.nextToken(), TokenType.VARIABLE, "A0_1");
		checkToken(l.nextToken(), TokenType.OPEN_BRACKET, '(');
		checkToken(l.nextToken(), TokenType.CLOSED_BRACKET, ')');
		checkToken(l.nextToken(), TokenType.CLOSED_BRACKET, ')');
		checkToken(l.nextToken(), TokenType.OPERATOR, "not");
		checkToken(l.nextToken(), TokenType.VARIABLE, "FALSE1");
		checkToken(l.nextToken(), TokenType.CONSTANT, false);
		checkToken(l.nextToken(), TokenType.OPERATOR, "xor");
		checkToken(l.nextToken(), TokenType.OPERATOR, "xor");
		checkToken(l.nextToken(), TokenType.OPERATOR, "or");
		checkToken(l.nextToken(), TokenType.OPERATOR, "and");
		checkToken(l.nextToken(), TokenType.OPERATOR, "not");
		checkToken(l.nextToken(), TokenType.OPERATOR, "not");
		checkToken(l.nextToken(), TokenType.VARIABLE, "FALSE01");
		checkToken(l.nextToken(), TokenType.OPERATOR, "and");
		checkToken(l.nextToken(), TokenType.CLOSED_BRACKET, ')');
		
		checkToken(l.nextToken(), TokenType.EOF, null);
	}
	
	private void checkToken(Token token, TokenType type, Object value) {
		assertEquals(token.getTokenType(), type);
		
		if (value == null) {
			assertNull(token.getTokenValue());
		} else {
			assertEquals(token.getTokenValue(), value);
		}
	}

}
