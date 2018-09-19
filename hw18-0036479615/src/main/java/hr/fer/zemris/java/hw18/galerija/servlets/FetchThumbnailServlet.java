package hr.fer.zemris.java.hw18.galerija.servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
 * Servlet accepts request for thumb nail for image which is identified by
 * tag and index of image for all tag images.
 * It responds to user with thumb nail.
 * If thumb nail for that image does not already exist it will create it
 * and store it in folder thumb nails and then respond with thumb nail.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(urlPatterns={"/fetchthumbnail"})
public class FetchThumbnailServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tag = req.getParameter("tagName");
		int index = Integer.parseInt(req.getParameter("index"));
		
		List<Image> images = Database.getInstance().getTagImages(tag);
		Image image = images.get(index);
		
		Path thumbPath = image.getThumbPath();
		BufferedImage thumb;
		
		if (!Files.exists(thumbPath)) {
			try (InputStream is = Files.newInputStream(images.get(index).getImagePath());
					OutputStream os = Files.newOutputStream(thumbPath)) {
				BufferedImage bim = ImageIO.read(is);
				
				thumb = new BufferedImage(Image.THUMB_WIDTH, Image.THUMB_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D g2d = thumb.createGraphics();
				g2d.drawImage(bim, 0, 0, Image.THUMB_WIDTH, Image.THUMB_HEIGHT, null);
				g2d.dispose();
				
				ImageIO.write(thumb, "jpg", os);
			}
		} else {
			try (InputStream is = Files.newInputStream(thumbPath)) {
				thumb = ImageIO.read(is);
			}
		}
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(thumb, "jpg", bos);
		
		resp.setContentType("image/jpg");
		resp.getOutputStream().write(bos.toByteArray());
	}
}
