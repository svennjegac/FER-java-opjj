package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet accepts request for updating a number of votes for some poll option.
 * It updates votes and process to results of voting.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="updatevotes", urlPatterns={"/servleti/glasanje-glasaj"})
public class UpdateVotesServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pollOptionIDParam = req.getParameter("pollOptionID");
		
		if (pollOptionIDParam == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
			return;
		}

		int pollOptionID = 0;
		
		try {
			pollOptionID = Integer.parseInt(pollOptionIDParam);
		} catch (NumberFormatException e) {
			resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
			return;
		}

		DAO dao = DAOProvider.getDao();

		PollOption pollOption = dao.getPollOption(pollOptionID);
		if (pollOption == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
			return;
		}
		
		dao.updatePollOption(pollOption.getId(), pollOption.getVotes() + 1);
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollOption.getPollID());
	}
}
