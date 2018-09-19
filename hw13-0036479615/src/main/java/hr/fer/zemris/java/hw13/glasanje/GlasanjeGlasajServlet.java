package hr.fer.zemris.java.hw13.glasanje;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is used to process user HTTP request for voting.
 * It updates band votes based on sent ID as URL parameter.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="glasajservlet", urlPatterns={"/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {
	
	/** UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		try {
			updateVotingResults(req);
		} catch (IllegalArgumentException e) {}
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

	/**
	 * Method parses users URL parameters and delegates voting to another method.
	 * 
	 * @param req {@link HttpServletRequest}
	 */
	private void updateVotingResults(HttpServletRequest req) {
		int id;
		
		try {
			id = Integer.parseInt(req.getParameter("id"));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Can not parse voted id; was: " + req.getParameter("id"));
		}

		update(id, req);
	}
	
	/**
	 * Method fetches current voting statistics from file on disk, then
	 * updates statistics of voted band and writes them to disk.
	 * 
	 * @param id band ID
	 * @param req {@link HttpServletRequest}
	 */
	private void update(int id, HttpServletRequest req) {
		Map<Integer, VoteNominee> nominees = Util.mergeNomineesWithResults(req);
		
		VoteNominee nominee = nominees.get(id);
		if (nominee != null) {
			nominee.setVotes(nominee.getVotes() + 1);
		}
		
		Util.writeToResultsFile(req, nominees);
	}
}
