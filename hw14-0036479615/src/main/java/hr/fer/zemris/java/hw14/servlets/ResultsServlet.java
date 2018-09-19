package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
 * Servlet accepts user request for results.
 * It processes request and prepares data for rendering on jsp.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="resultsservlet", urlPatterns={"/servleti/glasanje-rezultati"})
public class ResultsServlet extends HttpServlet {

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
		List<PollOption> pollOptions = dao.getPollOptionsSortedByVotes(pollID);
		List<PollOption> winners = calculateWinners(pollOptions);
	
		req.setAttribute("poll", poll);
		req.setAttribute("pollOptions", pollOptions);
		req.setAttribute("winners", winners);
		req.getRequestDispatcher("/WEB-INF/pages/results.jsp").forward(req, resp);
	}

	/**
	 * Method calculates winners of voting.
	 * 
	 * @param pollOptions all options for voting
	 * @return list of winners
	 */
	private List<PollOption> calculateWinners(List<PollOption> pollOptions) {
		Optional<PollOption> maxVotes = pollOptions
												.stream()
												.max(PollOption.VOTES_COMPARATOR.reversed());
		
		if (!maxVotes.isPresent()) {
			return new ArrayList<>();
		}
		
		return pollOptions
						.stream()
						.filter(pollOption -> pollOption.getVotes() == maxVotes.get().getVotes())
						.collect(Collectors.toList());
	}
}
