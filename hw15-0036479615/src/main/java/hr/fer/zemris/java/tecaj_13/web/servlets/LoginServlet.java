package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.BlogUserForm;

/**
 * Servlet processes login request.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="login-servlet", urlPatterns={"/servleti/login"})
public class LoginServlet extends HttpServlet {
	
	/** UID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");
		
		if (nick == null || password == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		
		BlogUser user = DAOProvider.getDAO().getBlogUserByNickAndPassword(nick, password);
		if (user == null) {
			BlogUserForm userForm = new BlogUserForm();
			userForm.setNick(nick);
			req.setAttribute("userForm", userForm);
			req.setAttribute("userNotFound", "User with provided nick and password does not exist.");
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
			return;
		}
		
		login(user, req);
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}
	
	/**
	 * Method initializes logged in user.
	 * 
	 * @param user user
	 * @param req request
	 */
	public static void login(BlogUser user, HttpServletRequest req) {
		req.getSession().setAttribute("current_user_id", user.getId());
		req.getSession().setAttribute("current_user_fn", user.getFirstName());
		req.getSession().setAttribute("current_user_ln", user.getLastName());
		req.getSession().setAttribute("current_user_nick", user.getNick());
	}
}
