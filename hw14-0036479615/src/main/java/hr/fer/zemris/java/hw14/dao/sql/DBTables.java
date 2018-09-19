package hr.fer.zemris.java.hw14.dao.sql;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class which encapsulates tables and their definition.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class DBTables {
	
	/** Polls table. */
	public static final String POLLS = "Polls";
	/** Poll options table. */
	public static final String POLL_OPTIONS = "PollOptions";
	/** Map of table name -> table definition. */
	public static final Map<String, String> TABLE_DEFINITIONS;
	
	static {
		TABLE_DEFINITIONS = new LinkedHashMap<>();
		TABLE_DEFINITIONS.put(POLLS, "CREATE TABLE " + POLLS
					+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
					+ " title VARCHAR(150) NOT NULL,"
					+ " message CLOB(2048) NOT NULL)");
		TABLE_DEFINITIONS.put(POLL_OPTIONS, "CREATE TABLE " + POLL_OPTIONS
					+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
					+ " optionTitle VARCHAR(100) NOT NULL,"
					+ " optionLink VARCHAR(150) NOT NULL,"
					+ " pollID BIGINT,"
					+ " votesCount BIGINT,"
					+ " FOREIGN KEY (pollID) REFERENCES " + POLLS + "(id))");
	}
}
