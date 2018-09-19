package hr.fer.zemris.bf.lexer;

/**
 * Class representing all possible token types.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public enum TokenType {

	/** End of file token type. */
	EOF,
	/** Variable token type. */
	VARIABLE,
	/** Constant token type. */
	CONSTANT,
	/** Operator token type. */
	OPERATOR,
	/** Open bracket token type. */
	OPEN_BRACKET,
	/** Closed bracket token type. */
	CLOSED_BRACKET
}
