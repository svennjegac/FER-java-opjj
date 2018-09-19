package hr.fer.zemris.java.hw18.galerija.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw18.galerija.db.Database;

/**
 * Servlet counts how many images are associated with some tag.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(urlPatterns={"/thumbnailcount"})
public class CountTagThumbnailsServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tag = req.getParameter("tagName");
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(new Number(Database.getInstance().getTagImages(tag).size()));
		
		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}
	
	/**
	 * Class representing a single number.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class Number {
		
		/** Value. */
		@SuppressWarnings("unused")
		private int number;

		/**
		 * Constructor.
		 * 
		 * @param number value
		 */
		public Number(int number) {
			this.number = number;
		}
	}
}
