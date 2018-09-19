package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class representing a context which is made for every request to server.
 * When request comes to server this context is initialized with output stream
 * which points to address which sent us request.
 * So, this context is used to write response to every request made to server.
 * 
 * This context also have references to parameters which user sent in URL {@link #parameters},
 * to temporary parameters which can be used in internal calculations {@link #temporaryParameters}
 * and to parameters which are in possession of users session (cookies) {@link #persistentParameters}.
 * 
 * Also, context has its encoding, answer status code, status text and mime type.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class RequestContext {
	
	/** Default encoding. */
	private static final String DEFAULT_ENCODING = "UTF-8";
	/** Default status code. */
	private static final int DEFAULT_STATUS_CODE = 200;
	/** Default status text. */
	private static final String DEFAULT_STATUS_TEXT = "OK";
	/** Default mime type. */
	private static final String DEFAULT_MIME_TYPE = "text/html";
	
	/** Default HTTP version. */
	private static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";
	/** Headers separator. */
	private static final String HEADERS_SEPARATOR = "\r\n";
	
	/** Output stream with which server sends response to user. */
	private OutputStream outputStream;
	/** Charset used for response. */
	private Charset charset;
	
	/** Response encoding. */
	private String encoding;
	/** Response status code. */
	private int statusCode;
	/** Response status text. */
	private String statusText;
	/** Response mime type. */
	private String mimeType;
	
	/** Users URL parameters. */
	private Map<String, String> parameters;
	/** Parameters used for internal calculations. */
	private Map<String, String> temporaryParameters;
	/** Users session parameters (cookies). */
	public Map<String, String> persistentParameters;
	/** List of cookies sent to user. */
	private List<RCCookie> outputCookies;
	
	/**
	 * Reference to dispatcher of user request.
	 * Dispatcher is server thread which is serving this request context.
	 * With reference to dispatcher it is possible to ask serving thread to
	 * process another resource on server (defined with URL path).
	 */
	private IDispatcher dispatcher;
	
	/** 
	 * Flag indicating if headers are generated. Headers are generated only at the
	 * start of a response.
	 */
	private boolean headerGenerated;
	
	/**
	 * Constructor of a request context.
	 * 
	 * @param outputStream stream used to write response to user
	 * @param parameters users URL parameters
	 * @param persistentParameters users cookie parameters
	 * @param outputCookies cookies which will be sent to user
	 * @param temporaryParameters parameters for internal calculations
	 * @param dispatcher reference to server dispatcher thread
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		
		if (temporaryParameters != null) {
			this.temporaryParameters = temporaryParameters;
		}
		
		this.dispatcher = dispatcher;
	}
	
	/**
	 * Constructor of a request context.
	 * 
	 * @param outputStream stream used to write response to user
	 * @param parameters users URL parameters
	 * @param persistentParameters users cookie parameters
	 * @param outputCookies cookies which will be sent to user
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		if (outputStream == null) {
			throw new IllegalArgumentException("Output stream can not be null.");
		}
		
		if (parameters == null) {
			parameters = new HashMap<>();
		}
		
		if (persistentParameters == null) {
			persistentParameters = new ConcurrentHashMap<>();
		}
		
		if (outputCookies == null) {
			outputCookies = new ArrayList<>();
		}
		
		this.outputStream = outputStream;
		this.parameters = Collections.unmodifiableMap(parameters);
		this.persistentParameters = persistentParameters;
		this.outputCookies = outputCookies;
		this.temporaryParameters = new HashMap<>();
		
		encoding = DEFAULT_ENCODING;
		statusCode = DEFAULT_STATUS_CODE;
		statusText = DEFAULT_STATUS_TEXT;
		mimeType = DEFAULT_MIME_TYPE;
		
		headerGenerated = false;
	}
	
	/**
	 * Getter for dispatcher of a request context.
	 * 
	 * @return dispatcher for request context
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
	
	/**
	 * Method writes array of byte data to user.
	 * If headers were not generated, they will be generated and then
	 * the response will be written.
	 * 
	 * @param data data for user
	 * @return reference to this object
	 * @throws IOException if writing to user fails
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			writeHeaders();
		}
		
		outputStream.write(data);
		outputStream.flush();
		
		return this;
	}
	
	/**
	 * Method writes string text to user.
	 * If headers were not generated, they will be generated and then
	 * the response will be written.
	 * Method writes data to user using currently set charset {@link #charset}.
	 * 
	 * @param text text for user
	 * @return reference to this object
	 * @throws IOException if writing to user fails
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			writeHeaders();
		}
		
		return write(text.getBytes(charset));
	}
	
	/**
	 * Method writes headers of a HTTP response to user.
	 * 
	 * @throws IOException if writing to user fails
	 */
	private void writeHeaders() throws IOException {
		headerGenerated = true;
		
		try {
			charset = Charset.forName(encoding);
		} catch (Exception e) {
			charset = StandardCharsets.UTF_8;
		}
		
		byte[] header = constructHeader();
		outputStream.write(header);
		outputStream.flush();
	}

	/**
	 * Method constructs header for user response taking in mind parameters set for
	 * response such as {@link #statusCode}, {@link #statusText}, {@link #mimeType}, {@link #encoding}.
	 * It also writes cookies.
	 * It returns data using standard encoding for HTTP headers - {@link StandardCharsets#ISO_8859_1}.
	 * 
	 * @return byte array of data representing headers for user
	 */
	private byte[] constructHeader() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(DEFAULT_HTTP_VERSION + " " + statusCode + " " + statusText + HEADERS_SEPARATOR);
		sb.append("Content-Type: " + mimeType + (mimeType.startsWith("text/") ? "; charset=" + encoding : "") + HEADERS_SEPARATOR);
		sb.append(constructCookieHeaderLines());
		sb.append(HEADERS_SEPARATOR);
		
		return sb.toString().getBytes(StandardCharsets.ISO_8859_1);
	}

	/**
	 * Method constructs string which represents lines of header cookies.
	 * 
	 * @return header cookies as string
	 */
	private String constructCookieHeaderLines() {
		StringBuilder sb = new StringBuilder();
		
		outputCookies.forEach(cookie -> {
			sb.append(constructSingleCookieLine(cookie));
		});
		
		return sb.toString();
	}

	/**
	 * Method constructs single header line for one cookie.
	 * 
	 * @param cookie cookie which will be put in header
	 * @return string representing a single cookie header
	 */
	private String constructSingleCookieLine(RCCookie cookie) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Set-Cookie: " + cookie.name + "=\"" + cookie.value + "\"");
		
		if (cookie.domain != null) {
			sb.append("; Domain=" + cookie.domain);
		}
		
		if (cookie.path != null) {
			sb.append("; Path=" + cookie.path);
		}
		
		if (cookie.maxAge != null) {
			sb.append("; Max-Age=" + cookie.maxAge);
		}
		
		sb.append("; HttpOnly");
		
		sb.append(HEADERS_SEPARATOR);
		
		return sb.toString();
	}

	/**
	 * Setter for encoding.
	 * 
	 * @param encoding new encoding
	 */
	public void setEncoding(String encoding) {
		isAllowedOperation();
		this.encoding = encoding;
	}
	
	/**
	 * Setter for status code.
	 * 
	 * @param statusCode new status code
	 */
	public void setStatusCode(int statusCode) {
		isAllowedOperation();
		this.statusCode = statusCode;
	}
	
	/**
	 * Setter for status text.
	 * 
	 * @param statusText new status text
	 */
	public void setStatusText(String statusText) {
		isAllowedOperation();
		this.statusText = statusText;
	}
	
	/**
	 * Setter for mime type.
	 * 
	 * @param mimeType new mime type
	 */
	public void setMimeType(String mimeType) {
		isAllowedOperation();
		this.mimeType = mimeType;
	}
	
	/**
	 * Getter for parameter value.
	 * 
	 * @param name parameter key
	 * @return parameter value
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * Getter for set of parameter names.
	 * 
	 * @return set of parameter names
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}
	
	/**
	 * Getter for persistent parameter value.
	 * 
	 * @param name persistent parameter key
	 * @return persistent parameter value
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * Getter for set of persistent parameter names.
	 * 
	 * @return set of persistent parameter names
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * Setter for persistent parameter entry.
	 * 
	 * @param name entry key
	 * @param value entry value
	 */
	public void setPersistentParameter(String name, String value) {
		if (name == null || value == null) {
			throw new IllegalArgumentException("Persistent parameter map key or value can not be null.");
		}
		
		persistentParameters.put(name, value);
	}
	
	/**
	 * Method removes persistent parameter entry.
	 * 
	 * @param name key of persistent parameter entry
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Getter for temporary parameter value.
	 * 
	 * @param name temporary parameter key
	 * @return temporary parameter value
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * Getter for set of temporary parameter names.
	 * 
	 * @return set of temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	/**
	 * Setter for temporary parameter entry.
	 * 
	 * @param name temporary parameter key
	 * @param value temporary parameter value
	 */
	public void setTemporaryParameter(String name, String value) {
		if (name == null || value == null) {
			throw new IllegalArgumentException("Temporary parameter map key or value can not be null.");
		}
		
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Method removes entry from temporary parameters.
	 * 
	 * @param name key of entry
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	/**
	 * Method called in operations which can not be executed if
	 * headers are already generated.
	 */
	private void isAllowedOperation() {
		if (headerGenerated) {
			throw new RuntimeException("Headers already generated. Can not modify headers values.");
		}
	}
	
	/**
	 * Method adds a cookie to list of cookies.
	 * 
	 * @param rcCookie new cookie
	 */
	public void addRCCookie(RCCookie rcCookie) {
		isAllowedOperation();
		outputCookies.add(rcCookie);
	}

	/**
	 * Class representing a single cookie for user.
	 * Cookie is defined by pair of name and value.
	 * 
	 * It can have parameters such as:
	 * -{@link #maxAge} which defines cookie expiration time
	 * -{@link #domain} which defines for which domains is cookie valid
	 * -{@link #path} which defines for which path is cookie valid
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	public static class RCCookie {
		
		/** Cookie name. */
		private String name;
		/** Cookie value. */
		private String value;
		/** Cookie expiration time. */
		private Integer maxAge;
		/** Domain for which is cookie valid. */
		private String domain;
		/** Path for which is cookie valid. */
		private String path;
		
		/**
		 * Constructor of a cookie.
		 * 
		 * @param name cookie name
		 * @param value cookie value
		 * @param maxAge cookie expiration time
		 * @param domain domain for which is cookie valid
		 * @param path path for which is cookie valid
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			super();
			this.name = name;
			this.value = value;
			this.maxAge = maxAge;
			this.domain = domain;
			this.path = path;
		}

		/**
		 * Getter for cookie name.
		 * 
		 * @return cookie name
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Getter for cookie value.
		 * 
		 * @return cookie value
		 */
		public String getValue() {
			return value;
		}
		
		/**
		 * Getter for cookie expiration time.
		 * 
		 * @return cookie expiration time
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
		
		/**
		 * Getter for domain for which cookie is valid.
		 * 
		 * @return domain for which is cookie valid
		 */
		public String getDomain() {
			return domain;
		}
		
		/**
		 * Getter for path for which is cookie valid.
		 * 
		 * @return path for which is cookie valid
		 */
		public String getPath() {
			return path;
		}
	}
}
