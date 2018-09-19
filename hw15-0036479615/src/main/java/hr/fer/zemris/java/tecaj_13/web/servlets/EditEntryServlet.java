package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogEntryForm;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet processes request for editing entry.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="edit-entry", urlPatterns={"/author/edit"})
public class EditEntryServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUser author = (BlogUser) req.getAttribute("author");
		BlogEntry entry = (BlogEntry) req.getAttribute("entry");
		
		System.out.println("edit servle");
		System.out.println(author);
		System.out.println(entry);
		
		if (author == null
				|| req.getSession().getAttribute("current_user_id") == null
				|| author.getId() != (Long) req.getSession().getAttribute("current_user_id")
				|| entry.getCreator().getId() != author.getId())
		{
			System.out.println("Ode ća");
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		
		System.out.println("Osta");
		req.setAttribute("entryForm", new BlogEntryForm(entry.getId().toString(), entry.getTitle(), entry.getText()));
		System.out.println("disp");
		req.getRequestDispatcher("/WEB-INF/pages/edit-update-entry.jsp").forward(req, resp);
	}
}
