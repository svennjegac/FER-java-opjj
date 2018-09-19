package hr.fer.zemris.java.hw04.db.lexer;

/**
 * Enumeration representing lexer states.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public enum QueryLexerState {
	
	/** Lexer in field name reading state. */
	FIELD_NAME_STATE,
	
	/** Lexer in operator reading state. */
	OPERATOR_STATE,
	
	/** Lexer in literal reading state. */
	LITERAL_STATE,
	
	/** Lexer in separator reading state. */
	SEPARATOR_STATE,
	
	/** Lexer in whitespace reading state. */
	WHITESPACE_STATE
}
