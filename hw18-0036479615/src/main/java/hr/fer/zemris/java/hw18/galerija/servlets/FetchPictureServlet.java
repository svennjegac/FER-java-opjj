package hr.fer.zemris.java.hw18.galerija.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw18.galerija.db.Database;
import hr.fer.zemris.java.hw18.galerija.db.Image;

/**
 * Servlet accepts request for image identified by tag and index of image for all tag images.
 * It responds to user with image data.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(urlPatterns={"/fetchpicture"})
public class FetchPictureServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tag = req.getParameter("tagName");
		int index = Integer.parseInt(req.getParameter("index"));
		
		List<Image> images = Database.getInstance().getTagImages(tag);
		Image image = images.get(index);
		
		try (InputStream is = Files.newInputStream(image.getImagePath())) {
			BufferedImage bim = ImageIO.read(is);
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(bim, "jpg", bos);
			
			resp.setContentType("image/jpg");
			resp.getOutputStream().write(bos.toByteArray());
		}
	}
}
