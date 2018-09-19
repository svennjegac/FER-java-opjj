package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which accepts request for output of trigonometric values.
 * It prepares data and sends it to JSP page for rendering.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="trigonometric", urlPatterns={"/trigonometric"})
public class TrigonometricServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String paramA = req.getParameter("a");
		String paramB = req.getParameter("b");
		
		int a = 0;
		int b = 360;
		
		try {
			if (paramA != null) {
				a = Integer.parseInt(paramA);
			}
		} catch (NumberFormatException ignorable) {}
		
		try {
			if (paramB != null) {
				b = Integer.parseInt(paramB);
			}
		} catch (NumberFormatException ignorable) {}
		
		if (a > b) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		
		if (b > a + 720) {
			b = a + 720;
		}
		
		List<TrigonometricValue> list = new ArrayList<>();
		
		for (int i = a; i <= b; i++) {
			list.add(new TrigonometricValue(Integer.valueOf(i).doubleValue()));
		}
		
		req.setAttribute("trigonometricResults", list);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
	
	/**
	 * Class representing a trigonometric value.
	 * It has properties value, sin, cos.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	public static class TrigonometricValue {
		
		/** Value. */
		double value;

		/**
		 * Constructor which accepts value.
		 * 
		 * @param value value
		 */
		public TrigonometricValue(double value) {
			this.value = value;
		}
		
		/**
		 * Getter for value.
		 * 
		 * @return value
		 */
		public double getValue() {
			return value;
		}
		
		/**
		 * Getter for sin.
		 * 
		 * @return sin
		 */
		public Double getSin() {
			return Math.sin(degreesToRadians(value));
		}
		
		/**
		 * Getter for cos.
		 * 
		 * @return cos
		 */
		public double getCos() {
			return Math.cos(degreesToRadians(value));
		}
		
		/**
		 * Method converts degrees to radians.
		 * 
		 * @param d degrees
		 * @return radians
		 */
		private double degreesToRadians(double d) {
			return d * 2 * Math.PI / 360;
		}
	}
}
