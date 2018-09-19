package hr.fer.zemris.java.hw18.galerija.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw18.galerija.db.Database;

/**
 * Servlet responds to user with json data which consists of all
 * tags in database.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(urlPatterns={"/tags"})
public class TagsServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> tagNames = Database.getInstance().getTags();
		List<Tag> tags = tagNames
								.stream()
								.sorted()
								.map(tagName -> new Tag(tagName))
								.collect(Collectors.toList());
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(tags.toArray(new Tag[tags.size()]));
		
		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}
	
	/**
	 * Helper class representing a single tag.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class Tag {
		
		/** Tag name. */
		@SuppressWarnings("unused")
		private String name;
		
		/**
		 * Constructor which accepts tag name.
		 * 
		 * @param name tag name
		 */
		public Tag(String name) {
			this.name = name;
		}
	}
}
