package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class represents possible states of lexer.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public enum SmartScriptLexerState {
	/** Lexer is in "normal" text reading. */
	TEXT_STATE,
	/** Lexer must read opening tag next. */
	OPEN_TAG_IS_NEXT_STATE,
	/** Lexer has just read opening tag. */
	TAG_NAME_IS_NEXT_STATE,
	/** Lexer is in "tag" reading state. */
	TAG_STATE
}
