package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet invoked when any of stories is requested.
 * It sets color to random and processes to story.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="stories", urlPatterns={"/stories/*"})
public class StoriesServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SetColorServlet.setColor(req, SetColorServlet.RANDOM);
		req.getRequestDispatcher("/funny-stories" + req.getRequestURI().replaceAll("webapp2/stories/", "")).forward(req, resp);
	}
}
