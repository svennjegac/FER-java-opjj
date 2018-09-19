package hr.fer.zemris.java.hw13.glasanje;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class used to set request fields to appropriate values which will
 * be then rendered on view which represents results page.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="glasanjerezultati", urlPatterns={"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {

	/** UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<VoteNominee> list = null;
		List<VoteNominee> winners = null;
		
		try {
			list = Util.nomineesMapToSortedList(Util.mergeNomineesWithResults(req), VoteNominee.VOTES_COMPARATOR);
		} catch (IllegalArgumentException e) {
			req.setAttribute(Util.ERROR, e.getMessage());
		}
		
		if (list != null && list.size() > 0) {
			int maxVotes = list.get(0).getVotes();
			winners = list
						.stream()
						.filter(nominee -> {
							return nominee.getVotes() == maxVotes;
						})
						.collect(Collectors.toList());
		}
		
		req.setAttribute("winners", winners);
		req.setAttribute("list", list);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
