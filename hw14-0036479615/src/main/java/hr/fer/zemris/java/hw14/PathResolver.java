package hr.fer.zemris.java.hw14;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

/**
 * Class which can be used to resolve paths to the context
 * of servlets.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class PathResolver {
	
	/** Servlet context. */
	private static ServletContext servletContext;
	
	/**
	 * Constructor.
	 * 
	 * @param sc {@link ServletContext}
	 */
	public static void init(ServletContext sc) {
		servletContext = sc;
	}
	
	/**
	 * MEthod resolves relative path in order of servlet context.
	 * 
	 * @param relativePath relative path to file
	 * @return absolute path to file
	 */
	public static Path resolve(String relativePath) {
		return Paths.get(servletContext.getRealPath(relativePath));
	}
}