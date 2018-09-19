package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet accepts request for voting on a defined poll.
 * It gets data for that poll from data layer and prepares it for rendering on jsp.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="votingservler", urlPatterns={"/servleti/glasanje"})
public class SinglePollServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pollIDParam = req.getParameter("pollID");
		
		if (pollIDParam == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
			return;
		}
		
		int pollID = 0;
		try {
			pollID = Integer.parseInt(pollIDParam);
		} catch (NumberFormatException e) {
			resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
			return;
		}
		
		DAO dao = DAOProvider.getDao();
		
		Poll poll = dao.getPoll(pollID);
		if (poll == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
			return;
		}
		
		List<PollOption> pollOptions = dao.getPollOptions(pollID);
		
		req.setAttribute("poll", poll);
		req.setAttribute("pollOptions", pollOptions);
		req.getRequestDispatcher("/WEB-INF/pages/poll.jsp").forward(req, resp);
	}
}
