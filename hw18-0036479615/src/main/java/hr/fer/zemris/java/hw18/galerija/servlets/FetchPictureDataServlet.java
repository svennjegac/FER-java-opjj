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
 * Servlet accepts tag and index of image for that tag images.
 * It responds with all data about image: name, description and all associated tags.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(urlPatterns={"/fetchpicturedata"})
public class FetchPictureDataServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tag = req.getParameter("tagName");
		int index = Integer.parseInt(req.getParameter("index"));
		
		List<Image> images = Database.getInstance().getTagImages(tag);
		Image image = images.get(index);
		
		List<String> imageTags = Database.getInstance().getImageTags(image);
		
		ImageData imageData = new ImageData(image.getImageName(), image.getImageDescription(), imageTags);
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(imageData);
		
		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}
	
	/**
	 * Class representing image data.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	public static class ImageData {
		
		/** Image name. */
		@SuppressWarnings("unused")
		private String name;
		/** Image description. */
		@SuppressWarnings("unused")
		private String description;
		/** List of tags associated with image. */
		@SuppressWarnings("unused")
		private List<String> tags;
		
		/**
		 * Constructor accepting all parameters.
		 * 
		 * @param name image name
		 * @param description image description
		 * @param tags list of image tags
		 */
		public ImageData(String name, String description, List<String> tags) {
			this.name = name;
			this.description = description;
			this.tags = tags;
		}
	}
}
