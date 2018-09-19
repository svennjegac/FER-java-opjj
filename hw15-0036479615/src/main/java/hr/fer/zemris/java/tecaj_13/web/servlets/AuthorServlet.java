package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet prepares info about author and its posts.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="author-info-servlet", urlPatterns="/author/info")
public class AuthorServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = (String) req.getAttribute("nick");
		
		BlogUser user = DAOProvider.getDAO().getBlogUserByNick(nick);
		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		
		req.setAttribute("entries", DAOProvider.getDAO().getBlogEntries(user));
		req.setAttribute("author", user);
		req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
	}
}
