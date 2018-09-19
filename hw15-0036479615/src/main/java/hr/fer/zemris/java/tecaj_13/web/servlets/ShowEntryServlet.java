package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet prepares blog entry for showing on page.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="show-entry", urlPatterns={"/author/entry"})
public class ShowEntryServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUser author = (BlogUser) req.getAttribute("author");
		Long id = null;
		try {
			id = Long.parseLong((String) req.getAttribute("entryID"));
		} catch (Exception ignorable) {}
		
		if (author == null || id == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntryByAuthorAndID(author, id);
		if (entry == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		
		req.setAttribute("entry", entry);
		req.setAttribute("comments", DAOProvider.getDAO().getComments(entry));
		req.getRequestDispatcher("/WEB-INF/pages/show-entry.jsp").forward(req, resp);
	}
	
}
