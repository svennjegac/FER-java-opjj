package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet processes request for new entry.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="add-entry-servlet", urlPatterns={"/author/new"})
public class NewEntryServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUser user = (BlogUser) req.getAttribute("author");
		if (user == null
				|| req.getSession().getAttribute("current_user_id") == null
				|| user.getId() != (Long) req.getSession().getAttribute("current_user_id"))
		{	
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/edit-update-entry.jsp").forward(req, resp);
	}
}
