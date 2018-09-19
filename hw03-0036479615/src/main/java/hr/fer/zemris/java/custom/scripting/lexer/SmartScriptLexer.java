package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.constants.EscapableArrays;
import hr.fer.zemris.java.custom.scripting.constants.ParserDelimiters;

/**
 * This class accepts String argument in constructor and
 * is able to reproduce sequence of tokens until EOF is reached.
 * If problem occurs, SmartScriptLexer exception will be thrown.
 * Lexer will generate different tokens if it is in
 * TEXT_STATE or TAG_STATE.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SmartScriptLexer {
	
	/** Array of chars representing string argument of constructor. */
	private char[] data;
	/** Pointer on first char of data array which is not yet processed as token. */
	private int currentIndex;
	/** Last reproduced token. */
	private Token token;
	/** Current state of lexer. */
	private SmartScriptLexerState lexerState;
	
	/** Operators.(Only in tags) */
	private static final char[] OPERATORS = { '-', '+', '*', '/', '^'};
	
	/**
	 * Constructor accepts string as an argument,
	 * converts it in char array and sets lexer to TEXT reading state.
	 * 
	 * @param text string which will be processed as tokens
	 */
	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new SmartScriptLexerException("Text can not be null.");
		}
		
		data = text.toCharArray();
		currentIndex = 0;
		token = null;
		lexerState = SmartScriptLexerState.TEXT_STATE;
	}
	
	/**
	 * Method returns last reproduced token.
	 * 
	 * @return last token reproduced
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Method which client uses to set lexer state.
	 * 
	 * @param lexerState new lexer state
	 */
	public void setState(SmartScriptLexerState lexerState) {
		if (lexerState == null) {
			throw new SmartScriptLexerException("Lexer state can not be null.");
		}
		
		this.lexerState = lexerState;
	}
	
	/**
	 * Method generates and returns new token.
	 * If EOF is already reached it will throw an exception.
	 * 
	 * @return next token
	 */
	public Token nextToken() {
		//if token is set, and its type is eof
		//you can not read more tokens, end of file was reached
		if (token != null && token.getType() == TokenType.EOF_TOKEN_TYPE) {
			throw new SmartScriptLexerException("End of file was already reached. Calling nextToken() method is forbbiden.");
		}
		
		//if current index is higher or equal to data length, generate eof
		if (currentIndex >= data.length) {
			return token = new Token(TokenType.EOF_TOKEN_TYPE, null);
		}
		
		//read text sequence
		if (lexerState == SmartScriptLexerState.TEXT_STATE) {
			return token = extractNextTokenTextState();
		}
		
		//if lexer is in state which indicates that opening tag is next
		if (lexerState == SmartScriptLexerState.OPEN_TAG_IS_NEXT_STATE) {
			return token = extractSequenceReturnToken(
					ParserDelimiters.OPEN_TAG_DELIMITER.getValue(),
					TokenType.OPEN_TAG_TOKEN_TYPE
			);
		}
		
		//if opening tag is read, read tag name
		if (lexerState == SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE) {
			return token = extractTagName();
		}
		
		//in tag reading
		if (lexerState == SmartScriptLexerState.TAG_STATE) {
			return token = extractNextTokenTagState();
		}
		
		//this part of code should never execute
		throw new SmartScriptLexerException("Invalid state was reached.");
	}
	
	/**
	 * Method extracts next token in "normal" reading state.
	 * It will output all chars as TEXT_SEQUENCE_TOKEN_TYPE until OPEN_TAG_DELIMITER shows up.
	 * If OPEN_TAG_DELIMITER is first, it will output OPEN_TAG_TOKEN_TYPE.
	 * In text reading mode, escaping is allowed for chars defined in {@link EscapableArrays}.
	 * 
	 * @return next token (TEXT_SEQUENCE_TOKEN_TYPE or OPEN_TAG_TOKEN_TYPE)
	 */
	private Token extractNextTokenTextState() {
		int startIndex = currentIndex;
		
		while (currentIndex < data.length) {	
			//if escape delimiter occurs, escape next char if it is valid to escape it in text reading mode
			if (isNextSequenceInData(ParserDelimiters.ESCAPE_DELIMITER.getValue())) {
				manageEscapingChar(EscapableArrays.textEscapable());
				continue;
			}
			
			//while reading text input, check if opening tag delimiter is next
			//and stop with processing
			if (isNextSequenceInData(ParserDelimiters.OPEN_TAG_DELIMITER.getValue())) {
				lexerState = SmartScriptLexerState.OPEN_TAG_IS_NEXT_STATE;
				break;
			}
			
			currentIndex++;
		}
		
		//this means that opening tag was on first position when lexer tried to read text data
		//in that case, nothing must be returned as text, we will return opening tag token
		if (startIndex == currentIndex) {
			return extractSequenceReturnToken(
					ParserDelimiters.OPEN_TAG_DELIMITER.getValue(),
					TokenType.OPEN_TAG_TOKEN_TYPE
			);
		}
		
		String text = String.copyValueOf(data, startIndex, currentIndex - startIndex);
		return new Token(TokenType.TEXT_SEQUENCE_TOKEN_TYPE, text);
	}
	
	/**
	 * Method extracts next token in "tag" reading state.
	 * In "tag" reading state, lexer returns various types of
	 * tokens defined in {@link TokenType}.
	 * 
	 * @return next token
	 */
	private Token extractNextTokenTagState() {
		//in tags every blank space is skipped
		skipBlanks();
		validateCurrentIndex();
		
		//if close tag is next, return it
		if (isNextSequenceInData(ParserDelimiters.CLOSE_TAG_DELIMITER.getValue())) {
			return extractSequenceReturnToken(
					ParserDelimiters.CLOSE_TAG_DELIMITER.getValue(),
					TokenType.CLOSE_TAG_TOKEN_TYPE
			);
		}
		
		//this char is start of a token in tag state
		//determine what kind of char is it
		//and depending on that, decide which type of token is next
		char c = data[currentIndex];
		
		if (Character.isLetter(c)) {
			return extractVariable();
		}
		
		if (isNextSequenceInData(ParserDelimiters.FUNCTION_DELIMITER.getValue())) {
			return extractFunction();
		}
		
		if (isInArray(c, OPERATORS)) {
			return extractOperator();
		}
		
		if (Character.isDigit(c)) {
			return extractNumber();
		}
		
		if (isNextSequenceInData(ParserDelimiters.STRING_DELIMITER.getValue())) {
			return extractString();
		}
		
		//if c was none of above chars
		//c is invalid char which can not be used in tag
		//throw an exception
		throw new SmartScriptLexerException("Invalid char occured, can not extract as anything; was '" + c + "'");
	}
	
	/**
	 * Method extracts sequence which is provided by user and
	 * method returns that sequence in token of given token type.
	 * Method is case insensitive and always returns lowercase sequence.
	 * 
	 * If that sequence is not next in data array, exception will be thrown.
	 * 
	 * @param sequence this will be extracted
	 * @param type token type to return after extraction
	 * @return next token
	 */
	private Token extractSequenceReturnToken(String sequence, TokenType type) {
		if (sequence == null || type == null) {
			throw new SmartScriptLexerException("Can not extract tag. Null parameters provided.");
		}
		
		shiftOverSequence(sequence);
		
		return new Token(type, sequence.toLowerCase());
	}
	
	/**
	 * Method shifts over sequence of chars in data[]
	 * if chars are equal as provided sequence(case insensitive).
	 * Otherwise it will throw SmartScriptLexerException.
	 * 
	 * @param sequence sequence which will be shifted
	 */
	private void shiftOverSequence(String sequence) {
		if (sequence == null) {
			throw new SmartScriptLexerException("Sequence can not be null.");
		}
		
		if (currentIndex < data.length - sequence.length() + 1) {
			//extract same number of chars from data and check if they are equal to sequence
			String dataSequence = String.copyValueOf(data, currentIndex, sequence.length());
			dataSequence.toLowerCase();
			currentIndex += dataSequence.length();
			
			if (dataSequence.equals(sequence.toLowerCase())) {
				return;
			}
		}
		
		throw new SmartScriptLexerException("Tag not valid");
	}
	
	/**
	 * Method extracts tag name.
	 * First it will try to extract special tag name -> ECHO_TAG_DELIMITER('=').
	 * After that it will try to extract a variable because every
	 * variable is a valid tag name.
	 * 
	 * @return next token (TAG_NAME_TOKEN_TYPE)
	 */
	private Token extractTagName() {
		skipBlanks();
		validateCurrentIndex();
		
		//if tag name starts with ECHO_TAG_OPENER... '='
		//set it to tag name
		if (isNextSequenceInData(ParserDelimiters.ECHO_TAG_DELIMITER.getValue())) {
			return extractSequenceReturnToken(
					ParserDelimiters.ECHO_TAG_DELIMITER.getValue(),
					TokenType.TAG_NAME_TOKEN_TYPE
			);
		}
		
		//otherwise tag name must be built by same rules as variable
		Token tagName = extractVariable();
		return new Token(TokenType.TAG_NAME_TOKEN_TYPE, tagName.getValue().toString().toLowerCase());
	}
	
	/**
	 * Method extracts new variable.
	 * Variable must start with letter.
	 * After letter, there can be any of allowed variable chars.
	 * 
	 * @return next token (VARIABLE_TOKEN_TYPE)
	 */
	private Token extractVariable() {
		validateCurrentIndex();
		
		int startIndex = currentIndex;
		char c = data[currentIndex];
		
		//variable must start with letter
		if (!Character.isLetter(c)) {
			throw new SmartScriptLexerException("Start of variable expected; was '" + Character.toString(c) + "'");
		}
		
		//after letter add as many allowed variable chars as you can
		while (currentIndex < data.length) {
			char newChar = data[currentIndex];
			
			if (allowedVariableChar(newChar)) {
				currentIndex++;
				continue;
			}
			
			break;
		}
		
		String variable = String.copyValueOf(data, startIndex, currentIndex - startIndex);
		return new Token(TokenType.VARIABLE_TOKEN_TYPE, variable);
	}
	
	/**
	 * Method extracts string.
	 * String must start with STRING_DELIMITER and it must end with same sign.
	 * In string there is allowed to escape chars defined in {@link EscapableArrays}.
	 * 
	 * @return next token (STRING_TOKEN_TYPE)
	 */
	private Token extractString() {
		validateCurrentIndex();
		
		//string must start with STRING_DELIMITER
		if (!isNextSequenceInData(ParserDelimiters.STRING_DELIMITER.getValue())) {
			throw new SmartScriptLexerException("Wrong start of string.");
		}
		
		currentIndex += ParserDelimiters.STRING_DELIMITER.getValue().length();
		
		int startIndex = currentIndex;
		boolean endOfStringReached = false;
		
		//add as many chars to string as you can
		while (currentIndex < data.length) {
			//end of string
			if (isNextSequenceInData(ParserDelimiters.STRING_DELIMITER.getValue())) {
				endOfStringReached = true;
				break;
			}
			
			//escape valid chars of string escaping
			if (isNextSequenceInData(ParserDelimiters.ESCAPE_DELIMITER.getValue())) {
				manageEscapingChar(EscapableArrays.stringEscpable());
				continue;
			}
			
			currentIndex++;
		}
		
		if (endOfStringReached) {
			String str = String.copyValueOf(data, startIndex, currentIndex - startIndex);
			
			currentIndex += ParserDelimiters.STRING_DELIMITER.getValue().length();
			return new Token(TokenType.STRING_TOKEN_TYPE, str);
		}
		
		//string was not closed properly
		throw new SmartScriptLexerException("String was not properly closed.");
	}
	
	/**
	 * Method extracts new operator.
	 * It can switch to digit extracting if after '-' follows a digit.
	 * 
	 * @return next token (OPERATOR_TOKEN_TYPE or NUMBER_TOKEN_TYPE)
	 */
	private Token extractOperator() {
		validateCurrentIndex();
		
		char c = data[currentIndex];
		
		if (isInArray(c, OPERATORS)) {
			//if operator is - and after it digit follows
			//extract number
			if (c == '-') {
				if (currentIndex < data.length - 1 &&
						(Character.isDigit(data[currentIndex + 1]))) {
					return extractNumber();
				}
			}
			
			currentIndex++;
			return new Token(TokenType.OPERATOR_TOKEN_TYPE, Character.toString(c));
		}
		
		throw new SmartScriptLexerException("Operator expected; was '" + c + "'");
	}
	
	/**
	 * Method extracts number, integer or double.
	 * Number can start with '-' if it is a negative number.
	 * 
	 * @return next token (NUMBER_TOKEN_TYPE)
	 */
	private Token extractNumber() {
		validateCurrentIndex();
		
		int startIndex = currentIndex;
		char first = data[currentIndex];
		
		//after starting with minus, next must be digit
		if (first == '-') {
			currentIndex++;
			validateCurrentIndex();
			char nextChar = data[currentIndex];
			
			if (first == '-' && !Character.isDigit(nextChar)) {
				throw new SmartScriptLexerException("After - sign, digit was expected, was '" + nextChar + "'");
			}
		}
		
		boolean dotOccured = false;
		
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			
			if (!Character.isDigit(c) && c != '.') {
				break;
			}
			
			if (c == '.') {
				//if second dot appeared, it can not be part of the same number
				if (dotOccured) {
					break;
				}
				dotOccured = true;
			}
			
			currentIndex++;
		}
		
		String number = String.copyValueOf(data, startIndex, currentIndex - startIndex);
		
		try {
			if (dotOccured) {
				return new Token(TokenType.NUMBER_DOUBLE_TOKEN_TYPE, Double.parseDouble(number));
			} else {
				return new Token(TokenType.NUMBER_INTEGER_TOKEN_TYPE, Integer.parseInt(number));
			}
		} catch (NumberFormatException e) {
			throw new SmartScriptLexerException("Number format is invalid; was '" + number + "'");
		}
	}
	
	/**
	 * Method extracts function name.
	 * Function must start with FUNTION_DELIMITER.
	 * After FUNCTION_DELIMITER function name must accomplish same rules as variable.
	 * 
	 * @return next token (FUNCTION_TOKEN_TYPE)
	 */
	private Token extractFunction() {
		validateCurrentIndex();
		
		if (!isNextSequenceInData(ParserDelimiters.FUNCTION_DELIMITER.getValue())) {
			throw new SmartScriptLexerException("Wrong start of function.");
		}
		
		currentIndex += ParserDelimiters.FUNCTION_DELIMITER.getValue().length();
		Token functionName = extractVariable();
		return new Token(TokenType.FUNCTION_TOKEN_TYPE, functionName.getValue());
	}
	
	/**
	 * Method accepts array of valid chars to be escaped.
	 * If char after ESCAPE_DELIMITER is in that array, ESCAPE_DELIMITER will be erased
	 * and char which was after ESCAPE_DELIMITER will be skipped.
	 * currentIndex will point on first char after char which was escaped.
	 * 
	 * @param array array of chars which can be escaped
	 */
	private void manageEscapingChar(char[] array) {
		if (array == null) {
			throw new SmartScriptLexerException("Escapable array can not be null.");
		}
		
		//escaping char can not be on the end of input
		if (currentIndex >= data.length - ParserDelimiters.ESCAPE_DELIMITER.getValue().length()) {
			throw new SmartScriptLexerException("Escaping character can not be at the end of input.");
		}
		
		char afterC = data[currentIndex + ParserDelimiters.ESCAPE_DELIMITER.getValue().length()];
		
		//char which is being escaped is not valid for escaping
		if (!isInArray(afterC, array)) {
			throw new SmartScriptLexerException("Not valid char after escaping character; was " + afterC);
		}
		
		escapeChars();
		currentIndex += 1;
	}
	
	/**
	 * Method erases ESCAPE_DELIMITER and shifts all data backwards for one position.
	 * It resizes data array to have size - ESCAPE_DELIMITER size.
	 */
	private void escapeChars() {
		if (currentIndex >= data.length - ParserDelimiters.ESCAPE_DELIMITER.getValue().length()) {
			throw new SmartScriptLexerException("Escaping character can not be at the end of input.");
		}
		
		//copy all form start to ESCAPE_DELIMITER
		String toEscapedChar = String.copyValueOf(data, 0, currentIndex);
		
		//add all from ESCAPE_DELIMITER to end 
		String escapedData = toEscapedChar
				+ String.copyValueOf(
						data,
						currentIndex + ParserDelimiters.ESCAPE_DELIMITER.getValue().length(),
						data.length - currentIndex - ParserDelimiters.ESCAPE_DELIMITER.getValue().length()
				);
		
		data = escapedData.toCharArray();
	}
	
	/**
	 * Method checks if char c is in given array.
	 * 
	 * @param c searched char
	 * @param array array in which char is searched
	 * @return <code>true</code> if char is found in an array, otherwise <code>false</code>
	 */
	private boolean isInArray(char c, char[] array) {
		for (int i = 0; i < array.length; i++) {
			if (c == array[i]) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Method checks if next chars in data array are equal as provided sequence.
	 * Method is case insensitive.
	 * 
	 * @param sequence tested sequence
	 * @return <code>true</code> if exact sequence follows, <code>false</code> otherwise
	 */
	private boolean isNextSequenceInData(String sequence) {
		if (sequence == null) {
			throw new SmartScriptLexerException("Sequence can not be null.");
		}
		
		if (currentIndex < data.length - sequence.length() + 1) {
			String dataSequence = String.copyValueOf(data, currentIndex, sequence.length()).toLowerCase();
			
			return dataSequence.equals(sequence.toLowerCase());
		}
		
		return false;
	}
	
	/**
	 * Method tests if char c is allowed to be in a variable name.
	 * 
	 * @param c tested char
	 * @return <code>true</code> if c can be in variable name, <code>false</code> otherwise
	 */
	private boolean allowedVariableChar(char c) {
		return (Character.isLetter(c) || Character.isDigit(c) || c == '_');
	}
	
	/**
	 * Method throws an exception if currentIndex is out of data bounds.
	 */
	private void validateCurrentIndex() {
		if (currentIndex >= data.length) {
			throw new SmartScriptLexerException("Current index out of range.");
		}
	}
	
	/**
	 * Method skip every blank space until some other char.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			
			if (!Character.isWhitespace(c)) {
				break;
			}
			
			currentIndex++;
		}
	}
}
