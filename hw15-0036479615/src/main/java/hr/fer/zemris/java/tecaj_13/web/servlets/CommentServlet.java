package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * Servlet adds comment to database.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="comment", urlPatterns={"/servleti/comment"})
public class CommentServlet extends HttpServlet {
	
	/** UID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = Long.parseLong(req.getParameter("id"));
		String text = req.getParameter("comment");
		String email = null;
		
		String authorNick = (String) req.getSession().getAttribute("current_user_nick");
		if (authorNick != null) {
			email = DAOProvider.getDAO().getBlogUserByNick(authorNick).getEmail();
		} else {
			email = "unregisterd";
		}
		
		BlogEntry en = DAOProvider.getDAO().getBlogEntry(id);
		BlogComment comment = new BlogComment();
		comment.setBlogEntry(en);
		comment.setMessage(text);
		comment.setPostedOn(new Date());
		comment.setEmail(email);
		
		DAOProvider.getDAO().comment(comment);
		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + en.getCreator().getNick() + "/" + en.getId());
	}
}
