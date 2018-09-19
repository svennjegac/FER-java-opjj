package hr.fer.zemris.java.hw03.prob1;

/**
 * This class accepts String argument in constructor and
 * is able to reproduce sequence of tokens until EOF is reached.
 * If problem occurs, SmartScriptLexer exception will be thrown.
 * 
 * Lexer has two states, BASIC and EXTENDED.
 * 
 * In BASIC state, lexer is able to generate WORD, NUMBER,
 * SYMBOL and EOF(if end of file is reached) types of tokens.
 * 
 * In EXTENDED state, lexer will generate everything as WORD token.
 * Only if TOGGLE_STATE(#) occurrs, SYMBOL will be generated, or
 * if end of file is reached, EOF token type will be generated.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Lexer {

	/** Array of chars representing an input. */
	private char[] data;
	/** Pointer on first unprocessed char. */
	private int currentIndex;
	/** Last token generated. */
	private Token token;
	/** Current state of lexer. */
	private LexerState state;
	/** Char which is used to toggle states. */
	private static final char TOGGLE_STATE = '#';
	
	/**
	 * Constructor accepts String and initializes lexer data,
	 * current index and sets lexer to BASIC state.
	 * 
	 * @param text text which will be processed
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Text which is going to be parsed can not be null.");
		}
		
		data = text.toCharArray();
		currentIndex = 0;
		token = null;
		state = LexerState.BASIC;
	}
	
	/**
	 * Method generates next token.
	 * 
	 * @return next token
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("End of file was already reached. Calling nextToken() method is forbbiden.");
		}
		
		skipBlanks();
		
		if (currentIndex >= data.length) {
			return token = new Token(TokenType.EOF, null);
		}
		
		if (state == LexerState.BASIC) {
			return token = extractNextTokenBasicState();
		}
		
		if (state == LexerState.EXTENDED) {
			return token = extractNextTokenExtendedState();
		}
		
		throw new LexerException("Something went wrong.");
	}
	
	/**
	 * Returns last generated token.
	 * 
	 * @return last generated token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Sets lexer state.
	 * 
	 * @param state new lexer state
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("LexerState can not be set to null.");
		}
		
		this.state = state;
	}
	
	/**
	 * Extracts next token in basic state.
	 * 
	 * @return next token
	 */
	private Token extractNextTokenBasicState() {
		char c = data[currentIndex];
		
		if (Character.isLetter(c) || c == '\\') {
			return extractNextWordBasicState();
		}
		
		if (Character.isDigit(c)) {
			return extractNextNumber();
		}
		
		return extractNextSymbol();
	}
	
	/**
	 * Extracts next token in EXTENDED state.
	 * Everything will be extracted as WORD, except if
	 * TOGGLE_STATE char occurs, then SYMBOL will be generated.
	 * 
	 * @return next token
	 */
	private Token extractNextTokenExtendedState() {
		char c = data[currentIndex];
		
		//only if TOGGLE_STATE char occurs, SYMBOL will be generated
		if (c == TOGGLE_STATE) {
			return extractNextSymbol();
		}
		
		//everything other than TOGGLE_STATE char will be generated as WORD
		return extractNextWordExtendedState();
	}
	
	/**
	 * Extracts next word in basic state.
	 * In words, numbers and '\' can be escaped.
	 * 
	 * @return next word in basic state
	 */
	private Token extractNextWordBasicState() {
		int startIndex = currentIndex;
		
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			
			if (Character.isLetter(c)) {
				currentIndex++;
				continue;
			}
			
			/*
			 * If slash occurs, there must be something after slash(something must be escaped)
			 * So check if data[] is long enough to have one more char
			 */
			if (c == '\\') {
				if (currentIndex >= data.length - 1) {
					throw new LexerException("Escaping char can not be at the end of an input.");
				}
					
				char afterC = data[currentIndex + 1];
				
				/*
				 * if char after slash is valid(digit or another slash)
				 * make a new array, just remove escaping slash and leave character after slash as it was
				 * current index after this operation will point to character which was 2 positions after slash
				 */
				if (Character.isDigit(afterC) || afterC == '\\') {
					char[] newData = new char[data.length - 1];
					
					for (int i = 0; i < currentIndex; i++) {
						newData[i] = data[i];
					}
					
					for (int i = currentIndex + 1; i < data.length; i++) {
						newData[i - 1] = data[i];
					}
					
					data = newData;
					currentIndex++;
					continue;
				}
				
				//if character after slash was not valid, throw an exception 
				throw new LexerException("After slash can be only another slash or digit; was " + afterC + ".");
			}
			
			break;
		}
		
		String word = String.copyValueOf(data, startIndex, currentIndex - startIndex);
		return new Token(TokenType.WORD, word);
	}
	
	/**
	 * Extracts next word in extended sate.
	 * Everything until blank space or TOGGLE_STATE
	 * char is extracted as single WORD.
	 * 
	 * @return next word
	 */
	private Token extractNextWordExtendedState() {
		int startIndex = currentIndex;
		
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			
			if (c == TOGGLE_STATE || Character.isWhitespace(c)) {
				break;
			}
			
			currentIndex++;
		}
		
		String word = String.copyValueOf(data, startIndex, currentIndex - startIndex);
		return new Token(TokenType.WORD, word);
	}
	
	/**
	 * Extracts next number.
	 * Valid numbers are those who can be represented
	 * as long types.
	 * 
	 * @return number token
	 */
	private Token extractNextNumber() {
		int startIndex = currentIndex;
		
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			
			if (Character.isDigit(c)) {
				currentIndex++;
				continue;
			}
			
			break;
		}
		
		String numberString = String.copyValueOf(data, startIndex, currentIndex - startIndex);
		try {
			long number = Long.parseUnsignedLong(numberString);
			return new Token(TokenType.NUMBER, number);
		} catch (NumberFormatException ex) {
			throw new LexerException("Number is to big to be stored as long; was " + numberString);
		}
	}
	
	/**
	 * Extracts symbol as a symbol token.
	 * 
	 * @return symbol token
	 */
	private Token extractNextSymbol() {
		char c = data[currentIndex];
		currentIndex++;
		
		return new Token(TokenType.SYMBOL, c);
	}
	
	/**
	 * Skips all blank spaces until first char or eof.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			if (!Character.isWhitespace(data[currentIndex])) {
				break;
			}
			
			currentIndex++;
		}
	}
}
