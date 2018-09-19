package hr.fer.zemris.java.webserver;

/**
 * Dispatcher interface.
 * Dispatcher is a class (thread) which can process HTTP request for
 * resource defined by URL path.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface IDispatcher {
	
	/**
	 * When this method is invoked, dispatcher will
	 * process request for resource on server.
	 * 
	 * @param urlPath path of a resource
	 * @throws Exception if error occurs while processing
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
