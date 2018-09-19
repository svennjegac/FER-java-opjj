package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;

/**
 * Class offers storage of DB connections into map. Each connection is stored for a single thread.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SQLConnectionProvider {

	/** Map of thread ID -> connection. */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * MEthod sets new connection for thread, or removes thread connection if null provided.
	 * 
	 * @param con DB connection
	 */
	public static void setConnection(Connection con) {
		if(con==null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * Getter for thread current connection.
	 * 
	 * @return DB connection
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}