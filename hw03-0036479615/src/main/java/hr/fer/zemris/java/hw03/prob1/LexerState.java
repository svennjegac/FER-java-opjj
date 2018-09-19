package hr.fer.zemris.java.hw03.prob1;

/**
 * Enum with all possible states of lexer.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public enum LexerState {
	/** Basic state of lexer. Generating word, symbols or numbers. */
	BASIC,
	/** Extended lexer state. Generating words until toggle sign occurs. */
	EXTENDED
}
