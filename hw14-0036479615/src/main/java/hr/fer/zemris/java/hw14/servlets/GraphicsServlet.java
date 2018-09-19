package hr.fer.zemris.java.hw14.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * This class outputs bytes representing pie chart image to response output.
 * pie chart is based on users votes on favorite options.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="graphicsservlet", urlPatterns={"/servleti/glasanje-grafika"})
public class GraphicsServlet extends HttpServlet {

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
		
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID);
		outputPieChart(pollOptions, resp);
	}

	/**
	 * Method outputs bytes representing pie chart of voting results
	 * to invoker.
	 * 
	 * @param pollOptions list of options
	 * @param resp {@link HttpServletResponse}
	 * @throws IOException if writing to invoker fails
	 */
	private void outputPieChart(List<PollOption> pollOptions, HttpServletResponse resp) throws IOException {
		DefaultPieDataset data = new DefaultPieDataset();
		pollOptions.forEach(pollOption -> {
			if (pollOption.getVotes() > 0) {
				data.setValue(pollOption.getName(), pollOption.getVotes());
			}
		});
		
		if (data.getItemCount() == 0) {
			return;
		}
		
		JFreeChart chart = ChartFactory.createPieChart("Grafički glasovi", data);		
		BufferedImage bim = chart.createBufferedImage(500, 500);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bim, "png", bos);
		
		resp.setContentType("image/png");
		resp.getOutputStream().write(bos.toByteArray());
	}
}
