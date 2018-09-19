package hr.fer.zemris.java.hw04.db.lexer;

/**
 * Enumeration representing types of tokens.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public enum QueryLexerTokenType {
	
	/** End of file token type. */
	EOF_TOKEN_TYPE,
	
	/** Field name token type. */
	FILED_NAME_TOKEN_TYPE,
	
	/** Operator token type. */
	OPERATOR_TOKEN_TYPE,
	
	/** Separator token type. */
	SEPARATOR_TOKEN_TYPE,
	
	/** Literal token type. */
	LITERAL_TOKEN_TYPE,
	
	/** Whitespace token type. */
	WHITESPACE_TOKEN_TYPE
}
