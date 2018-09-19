package hr.fer.zemris.java.hw13.glasanje;

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

/**
 * This class outputs bytes representing pie chart image to response output.
 * pie chart is based on users votes on favorite bands.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="grafikaservlet", urlPatterns={"/glasanje-grafika"})
public class GlasanjeGrafikaServlet extends HttpServlet {

	/** UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<VoteNominee> nominees = null;
		
		try {
			nominees = Util.nomineesMapToSortedList(Util.mergeNomineesWithResults(req), null);
		} catch (IllegalArgumentException e) {
			return;
		}
		
		outputPieChart(nominees, resp);
	}

	/**
	 * Method outputs bytes representing pie chart of voting results
	 * to invoker.
	 * 
	 * @param nominees list of bands
	 * @param resp {@link HttpServletResponse}
	 * @throws IOException if writing to invoker fails
	 */
	private void outputPieChart(List<VoteNominee> nominees, HttpServletResponse resp) throws IOException {
		DefaultPieDataset data = new DefaultPieDataset();
		nominees.forEach(nominee -> {
			if (nominee.getVotes() > 0) {
				data.setValue(nominee.getBandName(), nominee.getVotes());
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
