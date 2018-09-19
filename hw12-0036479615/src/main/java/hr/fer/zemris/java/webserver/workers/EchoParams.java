package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of a web worker which echoes a table of URL
 * parameters sent by user to user.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");
		
		StringBuilder sb = new StringBuilder();
		sb.append("<html>"
					+ "<head>"
						+ "<style>"
							+ "table, th, td { border: 1px solid black; }"
						+ "</style>"
					+ "</head>"
					+ "<body>");
		
		if (context.getParameterNames().size() == 0) {
			sb.append("No parameters sent...");
		} else {
			constructTable(context, sb);
		}
		
		sb.append("</body></html>");
		
		try {
			context.write(sb.toString());
		} catch (IOException e) {}
	}

	/**
	 * Method constructs a html table of URL parameters sent by user.
	 * 
	 * @param context {@link RequestContext}
	 * @param sb string builder in which table is written
	 */
	private void constructTable(RequestContext context, StringBuilder sb) {
		sb.append("<table>");
		
		context.getParameterNames().forEach(name -> {
			sb.append("<tr><td>");
			sb.append(name);
			sb.append("</td><td>");
			sb.append(context.getParameter(name));
			sb.append("</td></tr>");
		});
		
		sb.append("</table>");
	}
}
