package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This class accepts users request and parses parameters(a, b, n).
 * If parameters can not be parsed it outputs message.
 * Else it will generate XLS file of powers for user.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="powers", urlPatterns={"/powers"})
public class PowersServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String paramA = req.getParameter("a");
		String paramB = req.getParameter("b");
		String paramN = req.getParameter("n");
		
		int a = 0;
		int b = 0;
		int n = 0;
		
		try {
			a = Integer.parseInt(paramA);
			b = Integer.parseInt(paramB);
			n = Integer.parseInt(paramN);
		} catch (NumberFormatException e) {
			req.setAttribute("error", "error");
		}
		
		if (req.getAttribute("error") != null || a < -100  || a > 100 || b < -100 || b > 100 || n < 1 || n > 5) {
			req.setAttribute("error", "error");
			req.getRequestDispatcher("/WEB-INF/pages/powers.jsp").forward(req, resp);
			return;
		}
		
		if (a > b) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		
		createExcell(a, b, n, resp);
	}

	/**
	 * Method outputs XLS file of powers to user.
	 * 
	 * @param a bottom powers bound
	 * @param b upper powers bound
	 * @param n number of powers
	 * @param resp {@link HttpServletResponse}
	 */
	private void createExcell(int a, int b, int n, HttpServletResponse resp) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		for (int i = 1; i <= n; i++) {
			createSheet(hwb, a, b, i);
		}
		
		try (OutputStream os = resp.getOutputStream()) {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition", "attachment; filename=powers.xls");
			hwb.write(os);
		} catch (IOException e) {}
	}

	/**
	 * Method creates single sheet of powers.
	 * 
	 * @param hwb {@link HSSFWorkbook}
	 * @param a bottom powers bound
	 * @param b upper powers bound
	 * @param i power
	 */
	private void createSheet(HSSFWorkbook hwb, int a, int b, int i) {
		HSSFSheet sheet = hwb.createSheet(Integer.valueOf(i).toString());
		
		for (int j = a; j <= b; j++) {
			HSSFRow row = sheet.createRow(j - a);
			row.createCell(0).setCellValue(Integer.valueOf(j).toString());
			row.createCell(1).setCellValue(Math.pow(Integer.valueOf(j).doubleValue(), Integer.valueOf(i).doubleValue()));
		}
	}
}
