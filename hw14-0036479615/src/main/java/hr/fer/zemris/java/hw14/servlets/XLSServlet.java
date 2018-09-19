package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet accepts user request for XLS output of voting results and sends him response.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="glasanjexls", urlPatterns={"/servleti/glasanje-xls"})
public class XLSServlet extends HttpServlet {
	
	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pollIDParam = req.getParameter("pollID");
		if (pollIDParam == null) {
			return;
		}
		
		int pollID = 0;
		try {
			pollID = Integer.parseInt(pollIDParam);
		} catch (NumberFormatException e) {
			return;
		}
		
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptionsSortedByVotes(pollID);
		
		createExcell(pollOptions, req, resp);
	}
	
	/**
	 * Method creates XLS table of voting results and outputs it to user..
	 * 
	 * @param list voting results
	 * @param req {@link HttpServletRequest}
	 * @param resp {@link HttpServletResponse}
	 */
	private void createExcell(List<PollOption> list, HttpServletRequest req, HttpServletResponse resp) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		createSheet(hwb, list);
		
		try (OutputStream os = resp.getOutputStream()) {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition", "attachment; filename=rezultati.xls");
			hwb.write(os);
		} catch (IOException e) {}
	}

	/**
	 * Method creates XLS sheet with voting results.
	 * 
	 * @param hwb {@link HSSFWorkbook}
	 * @param list list of results
	 */
	private void createSheet(HSSFWorkbook hwb, List<PollOption> list) {
		HSSFSheet sheet = hwb.createSheet();
		
		for (int i = 0, size = list.size(); i < size; i++) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(list.get(i).getName());
			row.createCell(1).setCellValue(list.get(i).getVotes());
		}
	}
}
