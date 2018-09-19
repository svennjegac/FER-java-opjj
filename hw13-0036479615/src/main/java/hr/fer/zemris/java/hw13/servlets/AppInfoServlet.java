package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which sets request attribute to current application time usage.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="appinfo", urlPatterns={"/appinfo"})
public class AppInfoServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("info", new AppInfo());
		req.getRequestDispatcher("/appinfo.jsp").forward(req, resp);
	}
	
	/**
	 * Class which represents informations about current application time
	 * usage.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	public static class AppInfo {
		
		/** Days. */
		private int days;
		/** Hours. */
		private int hours;
		/** Minutes. */
		private int minutes;
		/** Seconds. */
		private int seconds;
		/** Milliseconds. */
		private int millis;
		
		/**
		 * Class constructor which initializes values.
		 */
		public AppInfo() {
			setAttributes();
		}
		
		/**
		 * Getter for days of usage.
		 * 
		 * @return days of usage
		 */
		public int getDays() {
			return days;
		}
		
		/**
		 * Getter for hours of usage.
		 * 
		 * @return hours of usage
		 */
		public int getHours() {
			return hours;
		}
		
		/**
		 * Getter for minutes of usage.
		 * 
		 * @return minutes of usage
		 */
		public int getMinutes() {
			return minutes;
		}
		
		/**
		 * Getter for seconds of usage.
		 * 
		 * @return seconds of usage
		 */
		public int getSeconds() {
			return seconds;
		}
		
		/**
		 * Getter for milliseconds of usage.
		 * 
		 * @return millisecond of usage
		 */
		public int getMillis() {
			return millis;
		}
		
		/**
		 * Methods sets usage attributes.
		 */
		private void setAttributes() {
			long timeElapsed = System.currentTimeMillis() - InitializationServletListener.getTime();
			
			days = (int) (timeElapsed / 1000 / 60 / 60 / 24);
			timeElapsed -= days * 24 * 60 * 60 * 1000;
			
			hours = (int) (timeElapsed / 1000 / 60 / 60);
			timeElapsed -= hours * 60 * 60 * 1000;
			
			minutes = (int) (timeElapsed / 1000 / 60);
			timeElapsed -= minutes * 60 * 1000;
			
			seconds = (int) (timeElapsed / 1000);
			timeElapsed -= seconds * 1000;
			
			millis = (int) timeElapsed;
		}
	}
}
