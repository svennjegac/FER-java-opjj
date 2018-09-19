package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.dao.jpa.ChecksumCalculator;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.BlogUserForm;

/**
 * Servlet processes registration request.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="register-servlet", urlPatterns={"/servleti/register"})
public class RegisterServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUserForm userForm = prepareFormFromParameters(req);

		if (!userForm.validate()) {
			req.setAttribute("userForm", userForm);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}
		
		if (DAOProvider.getDAO().getBlogUserByNick(userForm.getNick()) != null) {
			userForm.addError("nick", "Provided nick already exist.");
			req.setAttribute("userForm", userForm);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);;
			return;
		}
		
		BlogUser user = DAOProvider.getDAO().addUser(userForm.createBlogUser());
		LoginServlet.login(user, req);
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}

	/**
	 * Prepares form from parameters.
	 * 
	 * @param req request
	 * @return user form
	 */
	private BlogUserForm prepareFormFromParameters(HttpServletRequest req) {
		return new BlogUserForm(
				readParameter(req, "firstName"),
				readParameter(req, "lastName"),
				readParameter(req, "nick"),
				readParameter(req, "email"),
				ChecksumCalculator.checkSum(readParameter(req, "password"))
		);
	}

	/**
	 * Reads parameter and sets value to parameter or nothing if parameter
	 * is not present
	 * 
	 * @param req request
	 * @param parameter parameter
	 * @return parameter value
	 */
	private String readParameter(HttpServletRequest req, String parameter) {
		String value = req.getParameter(parameter);
		return value == null ? "" : value;
	}
}
