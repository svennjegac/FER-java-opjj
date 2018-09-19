package hr.fer.zemris.bf.lexer;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.bf.constants.Constants;
import hr.fer.zemris.bf.constants.Operators;

/**
 * Lexer is class which accepts String text input and produces
 * numbers of tokens.
 * 
 * This lexer produces set of tokens which can be used to construct
 * boolean expressions. Tokens which are created by this lexer are
 * defines in {@link TokenType}.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Lexer {
	
	/** Pointer on first unprocessed char. */
	private int currentIndex;
	/** Array of chars representing String input. */
	private char[] data;
	
	/** Last generated token. */
	private Token currentToken;
	
	/** Map connecting operator signs with operator names. */
	private static final Map<String, String> OPERATORS;
	/** Map connecting constant names with constant values. */
	private static final Map<String, Boolean> CONSTANTS;
	/** Map connecting bracket signs with dedicated token types. */
	private static final Map<String, TokenType> BRACKETS;
	
	static {
		Operators[] operatorsArray = Operators.values();
		OPERATORS = new HashMap<>();
		
		for (Operators operator : operatorsArray) {
			OPERATORS.put(operator.getSign(), operator.getName());
		}
	}
	
	static {
		Constants[] constantsArray = Constants.values();
		CONSTANTS = new HashMap<>();
		
		for (Constants constant : constantsArray) {
			CONSTANTS.put(constant.getName(), constant.getValue());
		}
	}
	
	static {
		BRACKETS = new HashMap<>();
		BRACKETS.put("(", TokenType.OPEN_BRACKET);
		BRACKETS.put(")", TokenType.CLOSED_BRACKET);
	}

	/**
	 * Lexer constructor which accepts String and
	 * initialize data array with dedicated chars.
	 * 
	 * @param expression input text
	 */
	public Lexer(String expression) {
		if (expression == null) {
			throw new LexerException("String provided for lexer can not be null.");
		}
		
		currentIndex = 0;
		data = expression.toCharArray();
		currentToken = null;
	}
	
	/**
	 * Method generates next token.
	 * If last EOF token was reached and this method is called
	 * it will throw an exception.
	 * 
	 * @return next token
	 * @throws LexerException if method is called after EOF was reached
	 */
	public Token nextToken() {
		if (currentToken != null && currentToken.getTokenType().equals(TokenType.EOF)) {
			throw new LexerException("End of file was already reached. Ilegal call of nextToken method.");
		}
		
		skipBlanks();
		
		if (currentIndex >= data.length) {
			return currentToken = new Token(TokenType.EOF, null);
		}
		
		if (Character.isLetter(data[currentIndex])) {
			return currentToken = extractIdentifier();
		}
		
		if (Character.isDigit(data[currentIndex])) {
			return currentToken = extractNumber();
		}
		
		if (BRACKETS.containsKey(String.valueOf(data[currentIndex]))) {
			currentToken = new Token(BRACKETS.get(String.valueOf(data[currentIndex])), data[currentIndex]);
			currentIndex++;
			return currentToken;
		}
		
		return currentToken = extractSignOperator();
	}
	
	/**
	 * Method extracts new identifier.
	 * 
	 * @return next identifier token
	 */
	private Token extractIdentifier() {
		int startIndex = currentIndex;
		
		while (currentIndex < data.length) {
			if (validIdentifierChar(data[currentIndex])) {
				currentIndex++;
				continue;
			}
			
			break;
		}
		
		String string = String.copyValueOf(data, startIndex, currentIndex - startIndex);
		return createIdentifierToken(string);
	}
	
	/**
	 * Method extracts new number.
	 * 
	 * @return next number token
	 */
	private Token extractNumber() {
		int startIndex = currentIndex;
		
		while (currentIndex < data.length) {
			if (Character.isDigit(data[currentIndex])) {
				currentIndex++;
				continue;
			}
			
			break;
		}
		
		String string = String.copyValueOf(data, startIndex, currentIndex - startIndex);
		return createNumberToken(string);
	}
	
	/**
	 * Method creates new operator.
	 * 
	 * @return next operator token
	 */
	private Token extractSignOperator() {
		int startIndex = currentIndex;
		
		while (currentIndex < data.length) {
			currentIndex++;
			
			String string = String.copyValueOf(data, startIndex, currentIndex - startIndex);
			
			if (OPERATORS.containsKey(string)) {
				return new Token(TokenType.OPERATOR, OPERATORS.get(string));
			}
		}
		
		throw new LexerException("Unable to tokenize requested sequence of chars, starting at: " + startIndex);
	}
	
	/**
	 * Method creates identifier token.
	 * Identifier token can be operator or constant if
	 * key name is same as one of operators or constants
	 * key names.
	 * Otherwise it will create variable token.
	 * 
	 * @param string input identifier
	 * @return next identifier token
	 */
	private Token createIdentifierToken(String string) {
		if (OPERATORS.containsValue(string.toLowerCase())) {
			return new Token(TokenType.OPERATOR, string.toLowerCase());
		}
		
		if (CONSTANTS.containsKey(string.toLowerCase())) {
			return new Token(TokenType.CONSTANT, CONSTANTS.get(string.toLowerCase()));
		}
		
		return new Token(TokenType.VARIABLE, string.toUpperCase());
	}
	
	/**
	 * Method creates new token based on number provided
	 * as a String.
	 * 
	 * @param string input number as String
	 * @return next number token
	 */
	private Token createNumberToken(String string) {
		if (CONSTANTS.containsKey(string.toLowerCase())) {
			return new Token(TokenType.CONSTANT, CONSTANTS.get(string.toLowerCase()));
		}
		
		throw new LexerException("Undefined number; was: " + string);
	}
	
	/**
	 * Method checks if char is valid to be in
	 * identifier.
	 * 
	 * @param c tested char
	 * @return <code>true</code> if char can be in identifier,
	 * 			<code>false</code> otherwise
	 */
	private boolean validIdentifierChar(char c) {
		if (Character.isLetterOrDigit(c) || c == '_') {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Method skips all blank characters.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			if (Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
				continue;
			}
			
			break;
		}
	}
}
