package hr.fer.zemris.java.hw14;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.dao.sql.SQLConnectionProvider;

/**
 * Listener whose methods are invoked on application startup and shutdown.
 * Its startup method initializes data structure and DB connections.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebListener
public class ApplicationInitialization implements ServletContextListener {
	
	/** Host address. */
	private String host;
	/** Port. */
	private int port;
	/** DB name. */
	private String name;
	/** DB user. */
	private String user;
	/** DB password. */
	private String password;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		PathResolver.init(sce.getServletContext());
		
		readDBSettings();
		
		String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + name;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
			cpds.setJdbcUrl(connectionURL);
			cpds.setUser(user);
			cpds.setPassword(password);
			
			sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
			
			initializeDBTables(cpds);
		} catch (Exception e) {
			if (cpds != null) {
				try { DataSources.destroy(cpds); } catch (SQLException ignorable) {}
			}
			
			throw new RuntimeException("Error while initializing DB connection pool and DB.");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method reads settings from configuration file.
	 */
	private void readDBSettings() {
		Path dbSettingsAbsoulutePath = PathResolver.resolve("/WEB-INF/configuration/dbsettings.properties");
		
		if (!Files.exists(dbSettingsAbsoulutePath) || !Files.isRegularFile(dbSettingsAbsoulutePath) || !Files.isReadable(dbSettingsAbsoulutePath)) {
			throw new RuntimeException("DB settings file not valid.");
		}
		
		Properties dbProperties = new Properties();
		
		try (InputStream is = new BufferedInputStream(Files.newInputStream(dbSettingsAbsoulutePath))) {
			dbProperties.load(is);
		} catch (IOException e) {
			throw new RuntimeException("Error while reading DB settings.");
		}
		
		assignDBProperties(dbProperties);
	}

	/**
	 * Method assigns settings to class properties.
	 * 
	 * @param dbProperties database properties
	 */
	private void assignDBProperties(Properties dbProperties) {
		host = dbProperties.getProperty("host");
		
		try {
			port = Integer.parseInt(dbProperties.getProperty("port"));
		} catch (NumberFormatException e) {
			throw new RuntimeException("Can not parse port from DB settings.");
		}
		
		name = dbProperties.getProperty("name");
		user = dbProperties.getProperty("user");
		password = dbProperties.getProperty("password");
		
		if (host == null || name == null || user == null || password == null) {
			throw new RuntimeException("Missing arguments in properties file.");
		}
	}
	
	/**
	 * Method initializes database tables.
	 * 
	 * @param cpds {@link ComboPooledDataSource}
	 */
	private void initializeDBTables(ComboPooledDataSource cpds) {
		Connection con = null;
		
		try {
			con = cpds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("Could not establish DB connection.");
		}
		
		SQLConnectionProvider.setConnection(con);
		
		DAO dao = DAOProvider.getDao();
		try {
			dao.initializeDataLayerStructure();
		} catch (DAOException e) {
			throw new RuntimeException("Table initialization failed.");
		} finally {
			SQLConnectionProvider.setConnection(null);
			try { con.close(); } catch (SQLException ignorable) {}
		}
	}
}