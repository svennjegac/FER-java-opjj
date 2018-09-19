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
 * Servlet catches request for /servleti/author and processes it to another designated servlet.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="dispatcher-servlet", urlPatterns={"/servleti/author/*"})
public class AuthorDispatcherServlet extends HttpServlet {
	
	/** UID. */
	private static final long serialVersionUID = 1L;
	/** Path for new entry. */
	private static final String NEW = "/new";
	/** Path for editing entry. */
	private static final String EDIT = "/edit";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestedDestination = req.getPathInfo().substring(1);
		
		System.out.println("Disapctha");
		
		if (requestedDestination.endsWith(NEW)) {
			BlogUser user = DAOProvider.getDAO().getBlogUserByNick(requestedDestination.substring(0, requestedDestination.length() - NEW.length()));
			req.setAttribute("author", user);
			req.getRequestDispatcher("/author/new").forward(req, resp);
			return;
		}
		
		if (requestedDestination.endsWith(EDIT)) {
			String idParam = req.getParameter("id");
			try {
				BlogUser user = DAOProvider.getDAO().getBlogUserByNick(requestedDestination.substring(0, requestedDestination.length() - EDIT.length()));
				req.setAttribute("author", user);
				
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(idParam));
				req.setAttribute("entry", entry);
				req.getRequestDispatcher("/author/edit").forward(req, resp);
				return;
			} catch (Exception e) {
				System.out.println("EXC");
				e.printStackTrace();
				resp.sendRedirect(req.getContextPath() + "/servleti/main");
				return;
			}
		}
		
		if (requestedDestination.substring(requestedDestination.lastIndexOf("/") + 1).matches("[0-9]+")) {
			BlogUser user = DAOProvider.getDAO().getBlogUserByNick(requestedDestination.substring(0, requestedDestination.lastIndexOf("/")));
			req.setAttribute("author", user);
			req.setAttribute("entryID", requestedDestination.substring(requestedDestination.lastIndexOf("/") + 1));
			req.getRequestDispatcher("/author/entry").forward(req, resp);
			return;
		}

		req.setAttribute("nick", requestedDestination);
		req.getRequestDispatcher("/author/info").forward(req, resp);
	}
}
