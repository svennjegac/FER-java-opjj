package hr.fer.zemris.java.hw18.galerija.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw18.galerija.db.Database;
import hr.fer.zemris.java.hw18.galerija.db.Image;

/**
 * Method which accepts initial request for page and renders main page.
 * It also initializes needed paths.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(urlPatterns={"/index.html", "/"})
public class PageRequestServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Database.setDESCRIPTOR(req.getServletContext().getRealPath("/WEB-INF/opisnik.txt"));
		Image.setIMAGES_LOCATION(req.getServletContext().getRealPath("/WEB-INF/slike") + "/");
		Image.setTHUMBS_LOCATION(req.getServletContext().getRealPath("/WEB-INF/thumbnails") + "/");
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}
}
