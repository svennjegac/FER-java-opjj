package hr.fer.zemris.java.hw13.glasanje;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet responds to user request which wants to open main page for voting.
 * It fetches bands from definition file and prepares them as request attributes for rendering.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="glasanje", urlPatterns={"/glasanje"})
public class GlasanjeServlet extends HttpServlet {
	
	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<VoteNominee> list = null;
		
		try {
			list = Util.nomineesMapToSortedList(Util.readDefinitionFile(req), null);
		} catch (IllegalArgumentException e) {
			req.setAttribute(Util.ERROR, e.getMessage());
		}
		
		req.setAttribute("list", list);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
