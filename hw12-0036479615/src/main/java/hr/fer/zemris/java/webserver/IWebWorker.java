package hr.fer.zemris.java.webserver;

/**
 * Interface which must be implemented by a web worker.
 * It accepts {@link RequestContext} and processes its work on it.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface IWebWorker {

	/**
	 * Method which implementtion processes user request.
	 * 
	 * @param context request context
	 */
	public void processRequest(RequestContext context);
}
