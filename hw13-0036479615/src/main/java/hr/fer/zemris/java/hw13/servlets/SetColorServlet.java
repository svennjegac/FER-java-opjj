package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class accepts user request for background color changing.
 * If color is not recognized, it will be set to default white.
 * Also, user can ask for random {@link #RANDOM} color.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="setcolor", urlPatterns={"/setcolor"})
public class SetColorServlet extends HttpServlet {
	
	/** UID. */
	private static final long serialVersionUID = 1L;
	
	/** White color. */
	public static final String WHITE = "white";
	/** Red color. */
	public static final String RED = "red";
	/** Green color. */
	public static final String GREEN = "green";
	/** Cyan color. */
	public static final String CYAN = "cyan";
	/** Random color. */
	public static final String RANDOM = "random";
	
	/** Color parameter. */
	public static final String COLOR_PARAM = "color";
	/** Color request attribute. */
	public static final String BG_COLOR = "pickedBgCol";
	/** Array of colors. */
	public static final String[] COLORS = {WHITE, RED, GREEN, CYAN};

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		setColor(req);
		req.getRequestDispatcher("/colors.jsp").forward(req, resp);
	}
	
	/**
	 * Getter for color parameter.
	 * 
	 * @return color parameter
	 */
	public static String getColorParam() {
		return COLOR_PARAM;
	}
	
	/**
	 * Setter for color.
	 * 
	 * @param req {@link HttpServletRequest}
	 */
	public static void setColor(HttpServletRequest req) {
		setColor(req, req.getParameter(COLOR_PARAM));
	}
	
	/**
	 * Setter for color.
	 * 
	 * @param req {@link HttpServletRequest}
	 * @param color color
	 */
	public static void setColor(HttpServletRequest req, String color) {
		if (color == null) {
			color = WHITE;
		}
		
		switch (color) {
		case WHITE:
			req.getSession().setAttribute(BG_COLOR, WHITE);
			break;
		case RED:
			req.getSession().setAttribute(BG_COLOR, RED);
			break;
		case GREEN:
			req.getSession().setAttribute(BG_COLOR, GREEN);
			break;
		case CYAN:
			req.getSession().setAttribute(BG_COLOR, CYAN);
			break;
		case RANDOM:
			Random r = new Random();
			req.getSession().setAttribute(BG_COLOR, COLORS[r.nextInt(COLORS.length)]);
			break;
		default:
			req.getSession().setAttribute(BG_COLOR, WHITE);
			break;
		}
	}
}
