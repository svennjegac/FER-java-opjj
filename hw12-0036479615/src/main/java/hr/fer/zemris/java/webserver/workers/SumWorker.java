package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker which calculates sum of parameters "a" and "b" which are sent by user and
 * outputs result of calculation.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		int a;
		int b;
		
		try {
			a = Integer.parseInt(context.getParameter("a"));
		} catch (NumberFormatException e) {
			a = 1;
		}
		
		try {
			b = Integer.parseInt(context.getParameter("b"));
		} catch (NumberFormatException e) {
			b = 2;
		}
		
		String zbroj = Integer.valueOf(a + b).toString();
		
		context.setTemporaryParameter("zbroj", zbroj);
		context.setTemporaryParameter("a", Integer.valueOf(a).toString());
		context.setTemporaryParameter("b", Integer.valueOf(b).toString());
		
		try {
			context.getDispatcher().dispatchRequest("/private/calc.smscr");
		} catch (Exception ignorable) {}
	}
}
