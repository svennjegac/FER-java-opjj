package hr.fer.zemris.java.hw04.db.lexer;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.hw04.db.lexer.QueryLexer;
import hr.fer.zemris.java.hw04.db.lexer.QueryLexerState;
import hr.fer.zemris.java.hw04.db.lexer.QueryLexerToken;
import hr.fer.zemris.java.hw04.db.lexer.QueryLexerTokenType;

public class QueryLexerTest {

	public QueryLexer init(String text) {
		return new QueryLexer(text);
	}
	
	public void checkToken(QueryLexerToken token, QueryLexerTokenType type, String val) {
		assertEquals(token.getType(), type);
		if (val != null) {
			assertEquals(token.getValue(), val);
			return;
		}
		assertNull(val);
	}
	
	@Test
	public void simple() {
		QueryLexer l = init("jmbag=\"0000000021\"");
		
		checkToken(l.nextToken(), QueryLexerTokenType.FILED_NAME_TOKEN_TYPE, "jmbag");
		l.setState(QueryLexerState.OPERATOR_STATE);
		checkToken(l.nextToken(), QueryLexerTokenType.OPERATOR_TOKEN_TYPE, "=");
		l.setState(QueryLexerState.LITERAL_STATE);
		checkToken(l.nextToken(), QueryLexerTokenType.LITERAL_TOKEN_TYPE, "0000000021");
		l.setState(QueryLexerState.SEPARATOR_STATE);
		checkToken(l.nextToken(), QueryLexerTokenType.EOF_TOKEN_TYPE, null);
	}
	
	@Test
	public void simple2() {
		QueryLexer l = init("   jmbag		=\"0000000021\"          ");
		
		checkToken(l.nextToken(), QueryLexerTokenType.FILED_NAME_TOKEN_TYPE, "jmbag");
		l.setState(QueryLexerState.OPERATOR_STATE);
		checkToken(l.nextToken(), QueryLexerTokenType.OPERATOR_TOKEN_TYPE, "=");
		l.setState(QueryLexerState.LITERAL_STATE);
		checkToken(l.nextToken(), QueryLexerTokenType.LITERAL_TOKEN_TYPE, "0000000021");
		l.setState(QueryLexerState.SEPARATOR_STATE);
		checkToken(l.nextToken(), QueryLexerTokenType.EOF_TOKEN_TYPE, null);
	}
	
	@Test
	public void twoArgs() {
		QueryLexer l = init("  lastname		>=\"nig!ff\"  aNd   firstname LIKE \"a*a\"        ");
		
		checkToken(l.nextToken(), QueryLexerTokenType.FILED_NAME_TOKEN_TYPE, "lastname");
		l.setState(QueryLexerState.OPERATOR_STATE);
		checkToken(l.nextToken(), QueryLexerTokenType.OPERATOR_TOKEN_TYPE, ">=");
		l.setState(QueryLexerState.LITERAL_STATE);
		checkToken(l.nextToken(), QueryLexerTokenType.LITERAL_TOKEN_TYPE, "nig!ff");
		l.setState(QueryLexerState.SEPARATOR_STATE);
		checkToken(l.nextToken(), QueryLexerTokenType.SEPARATOR_TOKEN_TYPE, "and");
		l.setState(QueryLexerState.FIELD_NAME_STATE);
		checkToken(l.nextToken(), QueryLexerTokenType.FILED_NAME_TOKEN_TYPE, "firstname");
		l.setState(QueryLexerState.OPERATOR_STATE);
		checkToken(l.nextToken(), QueryLexerTokenType.OPERATOR_TOKEN_TYPE, "LIKE");
		l.setState(QueryLexerState.WHITESPACE_STATE);
		checkToken(l.nextToken(), QueryLexerTokenType.WHITESPACE_TOKEN_TYPE, " ");
		l.setState(QueryLexerState.LITERAL_STATE);
		checkToken(l.nextToken(), QueryLexerTokenType.LITERAL_TOKEN_TYPE, "a*a");
		l.setState(QueryLexerState.SEPARATOR_STATE);
		checkToken(l.nextToken(), QueryLexerTokenType.EOF_TOKEN_TYPE, null);
	}
}
