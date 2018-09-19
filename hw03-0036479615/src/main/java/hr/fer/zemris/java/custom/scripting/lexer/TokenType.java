package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class with all possible token types
 * used for parsing.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public enum TokenType {
	/** End of file is reached. */
	EOF_TOKEN_TYPE,
	/** Token with "normal" text. */
	TEXT_SEQUENCE_TOKEN_TYPE,
	/** Token with tag opener. */
	OPEN_TAG_TOKEN_TYPE,
	/** Token with closing tag. */
	CLOSE_TAG_TOKEN_TYPE,
	/** Token with tag name. */
	TAG_NAME_TOKEN_TYPE,
	/** Token with variable name */
	VARIABLE_TOKEN_TYPE,
	/** Token with double number. */
	NUMBER_DOUBLE_TOKEN_TYPE,
	/** Token with integer number. */
	NUMBER_INTEGER_TOKEN_TYPE,
	/** Token with string. */
	STRING_TOKEN_TYPE,
	/** Token with function name. */
	FUNCTION_TOKEN_TYPE,
	/** Token with arithmetic operator. */
	OPERATOR_TOKEN_TYPE
}
