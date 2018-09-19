package hr.fer.zemris.java.hw14.dao.sql;

import hr.fer.zemris.java.hw14.PathResolver;
import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class is an implementation of {@link DAO} interface.
 * Implementation is based on relational databases and SQL.
 * It expects to have {@link Connection} set for usage in {@link SQLConnectionProvider}.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SQLDAO implements DAO {
	
	@Override
	public Poll getPoll(int id) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("SELECT * FROM " + DBTables.POLLS + " WHERE id=?");
			pst.setInt(1, id);
			ResultSet rs = null;
			
			try {
				rs = pst.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				return new Poll(rs.getInt(1), rs.getString(2), rs.getString(3));
			} finally {
				try { rs.close(); } catch (Exception ignorable) {}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			try { pst.close(); } catch (Exception ignorable) {}
		}
	}

	@Override
	public List<Poll> getPolls() {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("SELECT * FROM " + DBTables.POLLS);
			ResultSet rs = null;
			
			try {
				List<Poll> polls = new ArrayList<>();
				rs = pst.executeQuery();
				
				while (rs.next()) {
					polls.add(new Poll(rs.getInt(1), rs.getString(2), rs.getString(3)));
				}
				
				return polls;
			} finally {
				try { rs.close(); } catch (Exception ignorable) {}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			try { pst.close(); } catch (Exception ignorable) {}
		}
	}
	
	@Override
	public PollOption getPollOption(int id) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("SELECT * FROM " + DBTables.POLL_OPTIONS + " WHERE id=?");
			pst.setInt(1, id);
			ResultSet rs = null;
			
			try {
				rs = pst.executeQuery();
				
				if (!rs.next()) {
					return null;
				}
				
				return new PollOption(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(5), rs.getInt(4));
			} finally {
				try { rs.close(); } catch (Exception ignorable) {}
			}
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			try { pst.close(); } catch (Exception ignorable) {}
		}
	}
	
	@Override
	public List<PollOption> getPollOptions(int pollID) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("SELECT * FROM " + DBTables.POLL_OPTIONS + " WHERE pollID=?");
			pst.setInt(1, pollID);
			ResultSet rs = null;
			
			try {
				List<PollOption> pollOptions = new ArrayList<>();
				rs = pst.executeQuery();
				
				while (rs.next()) {
					pollOptions.add(new PollOption(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(5), rs.getInt(4)));
				}
				
				return pollOptions;
			} finally {
				try { rs.close(); } catch (Exception ignorable) {}
			}
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			try { pst.close(); } catch (Exception ignorable) {}
		}
	}
	
	@Override
	public List<PollOption> getPollOptionsSortedByVotes(int pollID) {
		List<PollOption> pollOptions = getPollOptions(pollID);
		pollOptions.sort(PollOption.VOTES_COMPARATOR);
		return pollOptions;
	}
	
	@Override
	public void updatePollOption(int id, int votes) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("UPDATE " + DBTables.POLL_OPTIONS + " SET votesCount=? WHERE id=?");
			pst.setInt(1, votes);
			pst.setInt(2, id);
			pst.executeUpdate();
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			try { pst.close(); } catch (Exception ignorable) {}
		}
	}
	
	@Override
	public void initializeDataLayerStructure() {
		DBTables.TABLE_DEFINITIONS.forEach((tableName, definition) -> {
			createTableIfDontExist(tableName);
		});
		
		DBTables.TABLE_DEFINITIONS.forEach((tableName, definition) -> {
			populateTableIfEmpty(tableName);
		});
	}

	/**
	 * Method creates table if table do not exists in DB.
	 * 
	 * @param tableName table name
	 */
	private void createTableIfDontExist(String tableName) {
		if (tableExists(tableName)) {
			return;
		}
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement(DBTables.TABLE_DEFINITIONS.get(tableName));
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Could not create table.");
		} finally {
			try { pst.close(); } catch (Exception ignorable) {}
		}
	}
	
	/**
	 * Method checks if table exists.
	 * 
	 * @param tableName table name
	 * @return <code>true</code> if table exists, otherwise <code>false</code>
	 */
	private boolean tableExists(String tableName) {
		Connection con = SQLConnectionProvider.getConnection();
		DatabaseMetaData dbmd;
		
		try {
			dbmd = con.getMetaData();
			ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(), null);

			try {
				if (!rs.next()) {
					return false;
				}
				
				return true;
			} finally {
				try { rs.close(); } catch (Exception ignorable) {}
			}
		} catch (SQLException e) {
			throw new DAOException("Table existance checking failed for: " + tableName);
		}
	}
	
	/**
	 * If table is {@link DBTables#POLLS} and table is empty it will populate it and it will also
	 * populate {@link DBTables#POLL_OPTIONS} with options.
	 * 
	 * @param tableName table name
	 */
	private void populateTableIfEmpty(String tableName) {
		if (!tableEmpty(tableName)) {
			return;
		}
		
		if (tableName.equals(DBTables.POLLS)) {
			populateTable(tableName);
		}
	}
	
	/**
	 * Method checks if table is empty.
	 * 
	 * @param tableName table name
	 * @return <code>true</code> if table is empty, otherwise <code>false</code>
	 */
	private boolean tableEmpty(String tableName) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("SELECT * FROM " + tableName.toUpperCase());
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (!rs.next()) {
						return true;
					}
					
					return false;
				} finally {
					try { rs.close(); } catch (Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch (Exception ignorable) {}
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	/**
	 * Method populate tables.
	 * 
	 * @param tableName table name
	 */
	private void populateTable(String tableName) {
		Properties polls = new Properties();
		
		try {
			polls.load(Files.newInputStream(PathResolver.resolve("/WEB-INF/configuration/defaultpolls.properties")));
		} catch (IOException e) {
			throw new DAOException("Could not read default polls.");
		}
		
		polls.forEach((key, value) -> {
			createPoll((String) value);
		});
	}

	/**
	 * Method creates poll. It reads poll and populates
	 * polls and poll options tables.
	 * 
	 * @param relativePathString relative path to poll definition
	 */
	private void createPoll(String relativePathString) {
		String[] firstLine = null;
		List<PollOption> nominees = null;
		
		try {
			firstLine = Util.readFirstLine(relativePathString);
			nominees = Util.readNomineesFromFile(relativePathString);
		} catch (IllegalArgumentException e) {
			throw new DAOException(e);
		}
		
		long pollID = insertToPolls(firstLine);
		insertToPollOptions(pollID, nominees);
	}
	
	/**
	 * Method inserts poll entity to polls table.
	 * 
	 * @param firstLine array of string representing poll
	 * @return poll ID
	 */
	private long insertToPolls(String[] firstLine) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("INSERT INTO " + DBTables.POLLS + "(title, message) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, firstLine[0]);
			pst.setString(2, firstLine[1]);
			
			ResultSet rs = null;
			try {
				pst.executeUpdate();
				rs = pst.getGeneratedKeys();
				
				if (rs != null && rs.next()) {
					return rs.getLong(1);
				}
			} finally {
				try { rs.close(); } catch (Exception ignorable) {}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			try { pst.close(); } catch (Exception ignorable) {}
		}
		
		throw new DAOException("Could not insert to polls.");
	}

	/**
	 * Method inserts poll options to poll options table.
	 * 
	 * @param pollID poll ID
	 * @param nominees poll options
	 */
	private void insertToPollOptions(long pollID, List<PollOption> nominees) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("INSERT INTO " + DBTables.POLL_OPTIONS + "(optionTitle, optionLink, pollID, votesCount) VALUES(?, ?, ?, ?)");
			
			for (PollOption voteNominee : nominees) {
				pst.setString(1, voteNominee.getName());
				pst.setString(2, voteNominee.getLink());
				pst.setLong(3, pollID);
				pst.setLong(4, voteNominee.getVotes());
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			try { pst.close(); } catch (Exception ignorable) {}
		}
	}
}