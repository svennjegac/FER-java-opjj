package hr.fer.zemris.java.hw13.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
 * Class creates image with default values of OS usage.
 * It outputs image bytes to user.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="reportimage", urlPatterns={"/reportImage"})
public class ReportImageServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("Programs", 22);
		data.setValue("Processor work", 17);
		data.setValue("Unused", 45);
		data.setValue("Internet", 16);
		
		JFreeChart chart = ChartFactory.createPieChart("OS pie chart", data);
		
		BufferedImage bim = chart.createBufferedImage(550, 550);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bim, "png", bos);
		
		resp.setContentType("image/png");
		resp.getOutputStream().write(bos.toByteArray());
	}
}
