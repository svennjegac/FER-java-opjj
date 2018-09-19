package hr.fer.zemris.java.hw13.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Class registers start of application usage.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebListener
public class InitializationServletListener implements ServletContextListener {

	/**
	 * Time when application started.
	 */
	private static long time;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		time = System.currentTimeMillis();
	}
	
	/**
	 * Getter for application start time.
	 * 
	 * @return application start time
	 */
	public static long getTime() {
		return time;
	}
}
