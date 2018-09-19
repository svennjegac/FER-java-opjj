package hr.fer.zemris.java.hw18.galerija.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw18.galerija.db.Database;
import hr.fer.zemris.java.hw18.galerija.db.Image;

/**
 * Servlet accepts request with provided tag and returns all images associated with that tag.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(urlPatterns={"/imagenames"})
public class ImageNamesServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tag = req.getParameter("tagName");
		
		List<Image> images = Database.getInstance().getTagImages(tag);
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(images);
		
		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}
}
