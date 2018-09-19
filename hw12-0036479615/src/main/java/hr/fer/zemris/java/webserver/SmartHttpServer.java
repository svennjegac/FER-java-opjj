package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngineException;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class representing an implementation of a simple HTTP server.
 * It can accept HTTP GET request, process it and return an HTTP response.
 * It can output html files, pictures or process server scripts.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SmartHttpServer {

	/** Server address. */
	private static final String SERVER_ADDRESS = "server.addres";
	/** Port on which server listens. */
	private static final String SERVER_PORT = "server.port";
	/** Number of server threads. */
	private static final String SERVER_WORKER_THREADS = "server.workerThreads";
	/** Server root folder. */
	private static final String SERVER_DOCUMENT_ROOT = "server.documentRoot";
	/** Server mime configuration location. */
	private static final String SERVER_MIME_CONFIG = "server.mimeConfig";
	/** Server session timeout. */
	private static final String SERVER_SESSION_TIMEOUT = "session.timeout";
	/** Path to server workers file. */
	private static final String SERVER_WORKERS = "server.workers";
	/** Path for workers convention package. */
	private static final String SERVER_WORKERS_CONVENTION_PACKAGE = "workers.package";
	/** URL for calling workers by convention. */
	private static final String SERVER_WORKERS_CONVENTION_URL = "workers.conventionUrl";
	
	/** Private folder name. */
	private static final String PRIVATE_FOLDER_NAME = "private";
	
	/** Time which will session cleaner wait until next cleaning. */
	private static final long SESSION_CLEANER_SLEEP = 1000 * 60 * 5;
	/** Conversion for seconds to milliseconds. */
	private static final int MILLIS_MULTIPLIER = 1000;
	/** Length of SID. */
	private static final int SID_LENGTH = 20;
	
	/** Server address. */
	private String address;
	/** Port on which server listens. */
	private int port;
	/** Root of server. */
	private Path documentRoot;
	/** Number of worker threads. */
	private int workerThreads;
	/** Timeout of single session. */
	private int sessionTimeout;
	/** Package in which workers are stored by convention. */
	private String workersConventionPackage;
	/** URL for conventional calling of workers. */
	private String workersConventionUrl;
	/** Map of extension - mime type. */
	private Map<String, String> mimeTypes = new HashMap<>();
	/** Server thread. */
	private ServerThread serverThread;
	/** Pool of threads for client serving. */
	private ExecutorService threadPool;
	/** List of tasks on processing. */
	private List<Future<?>> tasks = new ArrayList<>();
	/** Map of path - worker. */
	private Map<String, IWebWorker> workersMap = new HashMap<>();
	
	/** Map of user sessions. */
	private Map<String, SessionMapEntry> sessions = new HashMap<>();
	/** Used for generating SID. */
	private Random sessionRandom = new Random();
	/** Lock for session map. */
	private Object sessionMapLock = new Object();
	/** Lock for server starting. */
	private Object serverStartLock = new Object();
	
	/**
	 * Constructor. It accepts path to server.properties file.
	 * 
	 * @param configFileName path for server.properties file
	 */
	public SmartHttpServer(String configFileName) {
		initServerAttributes(configFileName);
	}
	
	/**
	 * Method initializes server attributes using configuration file.
	 * 
	 * @param configFileName configuration file
	 */
	private void initServerAttributes(String configFileName) {
		Path configFilePath = Paths.get(configFileName);
		
		if (!Files.exists(configFilePath) || !Files.isRegularFile(configFilePath) || !Files.isReadable(configFilePath)) {
			throw new SmartHttpServerException("Invalid configuration file");
		}
		
		Properties properties = new Properties();
		try {
			properties.load(Files.newInputStream(configFilePath));
		} catch (IOException e) {
			throw new SmartHttpServerException("Error while reading configuration file.");
		}
		
		address = properties.getProperty(SERVER_ADDRESS);
		port = Integer.parseInt(properties.getProperty(SERVER_PORT));
		workerThreads = Integer.parseInt(properties.getProperty(SERVER_WORKER_THREADS));
		sessionTimeout = Integer.parseInt(properties.getProperty(SERVER_SESSION_TIMEOUT));
		documentRoot = Paths.get(properties.getProperty(SERVER_DOCUMENT_ROOT));
		workersConventionPackage = properties.getProperty(SERVER_WORKERS_CONVENTION_PACKAGE);
		workersConventionUrl = properties.getProperty(SERVER_WORKERS_CONVENTION_URL);
		
		Path mimeConfigFilePath = Paths.get(properties.getProperty(SERVER_MIME_CONFIG));
		initMimeTypes(mimeConfigFilePath);
		
		Path workersConfigFilePath = Paths.get(properties.getProperty(SERVER_WORKERS));
		initServerWorkers(workersConfigFilePath);
	}

	/**
	 * Method initializes map of extension - mime type using mime configuraton file.
	 * 
	 * @param mimeConfigFilePath mime configuration file
	 */
	private void initMimeTypes(Path mimeConfigFilePath) {
		if (!Files.exists(mimeConfigFilePath) || !Files.isRegularFile(mimeConfigFilePath) || !Files.isReadable(mimeConfigFilePath)) {
			throw new SmartHttpServerException("Invalid mime configuration file");
		}
		
		Properties mimeProperties = new Properties();
		try {
			mimeProperties.load(Files.newInputStream(mimeConfigFilePath));
		} catch (IOException e) {
			throw new SmartHttpServerException("Error while reading mime configuration file.");
		}
		
		mimeProperties.forEach((key, propertie) -> {
			mimeTypes.put((String) key, (String) propertie);
		});
	}

	/**
	 * Method initializes map of path - worker using workers configuration file.
	 * 
	 * @param workersConfigFilePath workers configuration file
	 */
	private void initServerWorkers(Path workersConfigFilePath) {
		if (!Files.exists(workersConfigFilePath) || !Files.isRegularFile(workersConfigFilePath) || !Files.isReadable(workersConfigFilePath)) {
			throw new SmartHttpServerException("Invalid server workers configuration file.");
		}
		
		Properties workersProperties = new Properties();
		try {
			workersProperties.load(Files.newInputStream(workersConfigFilePath));
		} catch (IOException e) {
			throw new SmartHttpServerException("Error while reading workers configuration file.");
		}
		
		workersProperties.forEach((key, propertie) -> {
			String path = (String) key;
			String fqcn = (String) propertie;
			
			if (workersMap.containsKey(path)) {
				throw new SmartHttpServerException("There is more than one worker with same path; path: " + path);
			} 
			
			workersMap.put(path, createIWebWorkerFromFQCN(fqcn));
		});
	}
	
	/**
	 * Method creates worker using its FQCN.
	 * 
	 * @param fqcn workers FQCN
	 * @return {@link IWebWorker}
	 */
	private IWebWorker createIWebWorkerFromFQCN(String fqcn) {
		try {
			Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			Object newObject = referenceToClass.newInstance();
			return (IWebWorker) newObject;
		} catch (InstantiationException | IllegalArgumentException |
				ClassNotFoundException | IllegalAccessException | ClassCastException e) {
			throw new SmartHttpServerException("Can not load class; fqcn: " + fqcn);
		}
	}

	/**
	 * Method starts server.
	 */
	protected void start() {
		synchronized (serverStartLock) {
			threadPool = Executors.newFixedThreadPool(workerThreads);
			new SessionCleaner().start();
			serverThread = new ServerThread();
			serverThread.run();
		}
	}
	
	/**
	 * Method stops server.
	 */
	protected synchronized void stop() {
		serverThread.terminate();
		threadPool.shutdown();
	}
	
	/**
	 * Thread which is run when server starts. It listens for client requests
	 * and for each request it provides one serving thread.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	protected class ServerThread extends Thread {
		
		/** Flag for stopping this thread. */
		private volatile boolean stop = false;
		
		/**
		 * MEthod stops this thread.
		 */
		private void terminate() {
			stop = true;
		}
		
		@Override
		public void run() {
			try (ServerSocket serverSocekt = new ServerSocket()) {
				serverSocekt.bind(new InetSocketAddress(InetAddress.getByName(address), port));
				serverSocekt.setSoTimeout(1000 * 5);
				
				//listen for requests
				while (true) {
					Socket client = null;
					
					//try to open client socket
					//if timeout reaches, do nothing
					try {
						client = serverSocekt.accept();
					} catch (SocketTimeoutException ignorable) {}
					
					//termination invoked
					if (stop) {
						//close socket if opened
						if (!(client == null)) {
							client.close();
						}
						
						break;
					}
					
					//check if socket was opened
					//it can be null due to timeout
					if (!(client == null)) {
						ClientWorker cw = new ClientWorker(client);
						tasks.add(threadPool.submit(cw));
					}
				}
				
				//wait for all tasks
				for (Future<?> future : tasks) {
					try {
						future.get();
					} catch (Exception e) {}
				}
			} catch (IOException e) {
				throw new SmartHttpServerException("Can not open server socket.");
			}
		}
	}
	
	/**
	 * Thread which is periodically runs and removes all
	 * sessions which are not valid anymore.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private class SessionCleaner extends Thread {
		
		/**
		 * Constructor. It sets this thread to daemon so when server
		 * stops working, whole program can end.
		 */
		public SessionCleaner() {
			setDaemon(true);
		}
		
		@Override
		public void run() {
			while (true) {
				synchronized (sessionMapLock) {
					List<String> willBeRemoved = new ArrayList<>();
					
					sessions.forEach((sid, session) -> {
						if (sessionTimeout(session)) {
							willBeRemoved.add(sid);
						}
					});
					
					willBeRemoved.forEach(sid -> {
						sessions.remove(sid);
					});
				}
				
				try {
					sleep(SESSION_CLEANER_SLEEP);
				} catch (InterruptedException ignorable) {}
			}
		}
	}
	
	/**
	 * Method gets current session for user and updates its maximum age.
	 * If session is expired or do not exist, it will create new session.
	 * 
	 * @param sid Session ID
	 * @return user session
	 */
	private SessionMapEntry getSession(String sid) {
		synchronized (sessionMapLock) {
			SessionMapEntry candidate = sessions.get(sid);
			
			if (candidate == null || sessionTimeout(candidate)) {
				sid = createSid();
				candidate = createNewSession(sid);
				sessions.put(sid, candidate);
				return candidate;
			}
			
			candidate.updateTime(currentTime() + sessionTimeout * MILLIS_MULTIPLIER);
			
			return candidate;
		}
	}
	
	/**
	 * Method determines if session expired.
	 * 
	 * @param candidate session
	 * @return <code>true</code> if session expired, <code>false</code> otherwise
	 */
	private boolean sessionTimeout(SessionMapEntry candidate) {
		long currentTime = currentTime();
		
		if (currentTime - candidate.validUntil > 0) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Getter for current time.
	 * 
	 * @return current time
	 */
	private long currentTime() {
		return System.currentTimeMillis();
	}

	/**
	 * Method creates new session.
	 * 
	 * @param sid session ID
	 * @return new session
	 */
	private SessionMapEntry createNewSession(String sid) {
		return new SessionMapEntry(sid, currentTime() + sessionTimeout * MILLIS_MULTIPLIER);
	}

	/**
	 * Method creates session ID.
	 * 
	 * @return SID
	 */
	private String createSid() {
		String sid = "";
		
		for (int i = 0; i < SID_LENGTH; i++) {
			sid += getSidCharacter();
		}
		
		return sid;
	}
	
	/**
	 * Method creates single SID character.
	 * 
	 * @return single SID character
	 */
	private String getSidCharacter() {
		char c = (char) (sessionRandom.nextInt('Z' - 'A') + 'A');
		return String.valueOf(c);
	}

	/**
	 * Class implementing a runnable interface.
	 * It is used as a task for server serving thread.
	 * When client makes request on server, one instance of this class will
	 * serve its request.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		/** Client socket. */
		private Socket csocket;
		/** Client input stream. */
		private PushbackInputStream istream;
		/** Client output stream. */
		private OutputStream ostream;
		/** HTTP version. */
		private String version;
		/** HTTP method. */
		private String method;
		/** Path resolved in relation to document root. */
		private Path resolvedPath;
		/** Client request context. */
		private RequestContext requestContext;
		/** Client URL parameters. */
		private Map<String, String> params = new HashMap<>();
		/** Client temporary parameters. */
		private Map<String, String> tempParams = new HashMap<>();
		/** Client cookies. */
		private Map<String, String> permParams = new HashMap<>();
		/** Client HTTP cookies. */
		private List<RCCookie> outputCookies = new ArrayList<>();
		/** Client session ID. */
		private String SID;
		
		/** Supported request method. */
		private static final String SUPPORTED_METHOD = "GET";
		/** Supported HTTP version. */
		private static final String SUPPORTED_HTTP_V1 = "HTTP/1.1";
		/** Supported HTTP version. */
		private static final String SUPPORTED_HTTP_V2 = "HTTP/1.0";
		
		/**
		 * Constructor which accepts socket to client.
		 * 
		 * @param csocket socket to client
		 */
		public ClientWorker(Socket csocket) {
			this.csocket = csocket;
		}

		@Override
		public void run() {			
			try {
				if (!initializeStreams()) {
					return;
				}
				
				List<String> headers = getHeaders();
				if (headers == null) {
					return;
				}
			
				String requestedPath = parseHeadersFirstLine(headers);
				if (requestedPath == null) {
					return;
				}
				
				fetchSession(headers);
				
				try {
					internalDispatchRequest(requestedPath, true);
				} catch (Exception e) {
					sendError(598, "Error on server.");
				}
			} finally {
				try {
					csocket.close();
				} catch (IOException ignorable) {}
			}
		}
		
		/**
		 * Method initializes streams for reading from client and
		 * writing to client.
		 * 
		 * @return <code>true</code> if streams are initialized, otherwise <code>false</code>
		 */
		private boolean initializeStreams() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
			} catch (IOException e) {
				return false;
			}
			
			return true;
		}
		
		/**
		 * Method gets headers from user request.
		 * 
		 * @return request headers
		 */
		private List<String> getHeaders() {
			byte[] request = readRequest(istream);
			if (request == null) {
				sendError(400, "Bad request");
				return null;
			}
			
			String requestStr = new String(
				request, 
				StandardCharsets.ISO_8859_1
			);
			
			List<String> headers = extractHeaders(requestStr);
			if (headers.isEmpty()) {
				sendError(400, "Bad request");
				return null;
			}
			
			return headers;
		}
		
		/**
		 * Method reads header of user request.
		 * 
		 * @param is input stream
		 * @return headers of request
		 */
		private byte[] readRequest(InputStream is) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				int state = 0;
				l:	while(true) {
					int b = is.read();
					if(b==-1) return null;
					if(b!=13) {
						bos.write(b);
					}
					switch(state) {
					case 0: 
						if(b==13) { state=1; } else if(b==10) state=4;
						break;
					case 1: 
						if(b==10) { state=2; } else state=0;
						break;
					case 2: 
						if(b==13) { state=3; } else state=0;
						break;
					case 3: 
						if(b==10) { break l; } else state=0;
						break;
					case 4: 
						if(b==10) { break l; } else state=0;
						break;
					}
				}
				return bos.toByteArray();
			} catch (Exception e) {
				if (bos.size() != 0) {
					System.out.println("Error while reading client request header.");
				}
				
				return null;
			}	
		}
		
		/**
		 * Method extracts list of headers from headers string.
		 * 
		 * @param requestHeader string representing headers
		 * @return headers as list
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for(String s : requestHeader.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}
		
		/**
		 * Method parses first line of request.
		 * 
		 * @param headers request headers
		 * @return path requested by user
		 */
		private String parseHeadersFirstLine(List<String> headers) {
			String[] firstLine = headers.get(0).split(" ");
			if(firstLine.length != 3) {
				sendError(400, "Bad request");
				return null;
			}
			
			method = firstLine[0].toUpperCase();
			if(!method.equals(SUPPORTED_METHOD)) {
				sendError(400, "Bad request");
				return null;
			}

			version = firstLine[2].toUpperCase();
			if(!version.equals(SUPPORTED_HTTP_V1) && !version.equals(SUPPORTED_HTTP_V2)) {
				sendError(400, "Bad request");
				return null;
			}
		
			String requestResource = firstLine[1];
			if (requestResource.indexOf("?") != requestResource.lastIndexOf("?")) {
				sendError(400, "Bad request.");
				return null;
			}
			
			String requestedPathString;
			String paramString;
			if (requestResource.indexOf("?") != -1) {
				requestedPathString = requestResource.substring(0, requestResource.indexOf("?"));
				paramString = requestResource.substring(requestResource.indexOf("?") + 1);
			} else {
				requestedPathString = requestResource;
				paramString = null;
			}
			
			parseParameters(paramString);
			
			return requestedPathString;
		}
		
		/**
		 * Method parses user URL parameters.
		 * 
		 * @param paramString string containing parameters
		 */
		private void parseParameters(String paramString) {
			if (paramString == null) {
				return;
			}
			
			String[] pairs = paramString.split("[&]");
			
			for (String pair : pairs) {
				String[] buff = pair.split("[=]");
				
				if (buff.length != 2) {
					continue;
				}
				
				params.put(buff[0], buff[1]);
			}
		}
		
		/**
		 * Method fetches session for this request.
		 * If there is a valid session, it will fetch it.
		 * If there is not valid session for this request it will create
		 * a new session.
		 * 
		 * @param headers request headers
		 */
		private void fetchSession(List<String> headers) {
			String sidCandidate = findSidCandidate(headers);
			SessionMapEntry session = getSession(sidCandidate);
			permParams = session.map;
			SID = session.sid;
			outputCookies.add(new RCCookie("sid", SID, null, getDomain(headers), "/"));
		}
		
		/**
		 * Method tries to read SID cookie from headers.
		 * 
		 * @param headers headers
		 * @return SID cookie value
		 */
		private String findSidCandidate(List<String> headers) {
			String sidPrefix = "sid=\"";
			
			for (String header : headers) {
				//header line must be: "Cookie:<anything>sid="<anything>"<anything>"
				if (!header.matches("Cookie:[\\d\\D]*sid=\"[\\d\\D]*\"[\\d\\D]*")) {
					continue;
				}
				
				int startIndex = header.indexOf(sidPrefix);
				int endIndex = header.indexOf("\"", startIndex + sidPrefix.length()) + 1;
				
				String sidPair = header.substring(startIndex, endIndex);
				
				return sidPair.split("[=]")[1].replaceAll("\"", "");
			}
			
			return null;
		}

		/**
		 * Method gets the domain from user request headers.
		 * 
		 * @param headers request headers
		 * @return server domain
		 */
		private String getDomain(List<String> headers) {
			String hostPrefix = "Host:";
			String portPostfix = ":[0-9]+";
			
			for (String header : headers) {
				if (!header.startsWith(hostPrefix)) {
					continue;
				}
				
				String host = header.substring(hostPrefix.length()).trim();
				host = host.replaceAll(portPostfix, "");
				
				return host;
			}
			
			return address;
		}
		
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
		
		/**
		 * Method which accepts path to resource on server and processes request.
		 * 
		 * @param urlPath path to resource
		 * @param directCall flag if call to path is direct, or it is made internally by server workers
		 * @throws Exception if something goes wrong
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if (directCall && (urlPath.startsWith("/" + PRIVATE_FOLDER_NAME + "/") || urlPath.equals("/" + PRIVATE_FOLDER_NAME))) {
				sendError(404, "Not Found");
				return;
			}
			
			if (urlPath.startsWith(workersConventionUrl)) {
				createDefaultRequestContext();
				processWorkersByConvention(urlPath, requestContext);
				return;
			}
			
			if (workersMap.containsKey(urlPath)) {
				createDefaultRequestContext();
				workersMap.get(urlPath).processRequest(requestContext);
				return;
			}
			
			if (!resolveAndValidateRequestedPath(urlPath)) {
				return;
			}
			
			String extension = getExtension(urlPath);
			
			if ("smscr".equals(extension)) {
				createDefaultRequestContext();
				processSmartScript(requestContext, resolvedPath);	
			} else {
				createMimeSpecificRequestContext();
				sendResponse(requestContext, resolvedPath);
			}
		}
		
		/**
		 * Creates request context with mime type dedicated to resource extension.
		 */
		private void createMimeSpecificRequestContext() {
			String extension = getExtension(resolvedPath.toString());
			
			String mime = mimeTypes.get(extension);
			if (mime == null) {
				mime = "application/octet-stream";
			}
			
			requestContext = new RequestContext(ostream, params, permParams, outputCookies);
			requestContext.setMimeType(mime);
			requestContext.setStatusCode(200);
			requestContext.setStatusText("OK");
		}
		
		/**
		 * Creates default request context.
		 */
		private void createDefaultRequestContext() {
			requestContext = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this);
		}
		
		/**
		 * Processes workers invoked by convention path.
		 * 
		 * @param urlPath convention path
		 * @param requestContext request context
		 */
		private void processWorkersByConvention(String urlPath, RequestContext requestContext) {
			String workerName = urlPath.substring(workersConventionUrl.length());
			String fqcn = workersConventionPackage + "." + workerName;
			
			IWebWorker iww = null;
			try {
				iww = createIWebWorkerFromFQCN(fqcn);
			} catch (SmartHttpServerException e) {
				sendError(499, "Can not create worker from fqcn; fqcn: '" + fqcn + "'");
				return;
			}
			
			iww.processRequest(requestContext);
		}
		
		/**
		 * Resolves path to server document root.
		 * 
		 * @param requestedPathString requested path of resource
		 * @return <code>true</code> if path is sucessfully resolved, <code>false</code> otherwise
		 */
		private boolean resolveAndValidateRequestedPath(String requestedPathString) {
			resolvedPath = documentRoot;
			resolvedPath = resolvedPath.resolve(requestedPathString.substring(1));
			
			if (resolvedPath.compareTo(documentRoot) < 0) {
				sendError(403, "Forbidden");
				return false;
			}
			
			if (!Files.exists(resolvedPath) || !Files.isRegularFile(resolvedPath) || !Files.isReadable(resolvedPath)) {
				sendError(404, "Not Found");
				return false;
			}
			
			return true;
		}
		
		/**
		 * Gets extension from user path.
		 * 
		 * @param path path
		 * @return extension
		 */
		private String getExtension(String path) {
			int lastDot = path.lastIndexOf(".");
			String extension = null;
			
			if (lastDot == path.length() - 1) {
				return null;
			}
			
			if (lastDot != -1) {
				extension = path.substring(lastDot + 1);
			}
			
			return extension;
		}

		/**
		 * Processes smart script on server.
		 * 
		 * @param requestContext request context
		 * @param path path to smart script
		 */
		private void processSmartScript(RequestContext requestContext, Path path) {
			String script = new String(loadFile(path), StandardCharsets.UTF_8);
			DocumentNode documentNode = null;
			
			try {
				documentNode = new SmartScriptParser(script).getDocumentNode();
			} catch (SmartScriptParserException e) {
				sendError(598, "Script can not be parsed.");
				return;
			}
			
			try {
				new SmartScriptEngine(documentNode, requestContext).execute();
			} catch (SmartScriptEngineException e) {
				sendError(599, "An error occured while executing script. Information about error: " + e.getMessage());
			}
		}
		
		/**
		 * Loads file from server and returns it as a byte array.
		 * 
		 * @param urlPath path to file
		 * @return bytes representing a file
		 */
		private byte[] loadFile(Path urlPath) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(urlPath))) {
				byte[] buffer = new byte[1024];
				
				while (true) {
					int r = bis.read(buffer);
					
					if (r == -1) {
						break;
					}
					
					bos.write(buffer, 0, r);
				}
			} catch (IOException e) {}
			
			return bos.toByteArray();
		}
		
		/**
		 * Sends response to user.
		 * 
		 * @param rc request context
		 * @param path path of resource
		 */
		private void sendResponse(RequestContext rc, Path path) {
			try {
				rc.write(loadFile(path));
			} catch (IOException e) {}
		}
		
		/**
		 * Sends response to user.
		 * 
		 * @param rc request context
		 * @param text text which will be sent
		 */
		private void sendResponse(RequestContext rc, String text) {
			try {
				rc.write(text);
			} catch (IOException e) {}
		}
		
		/**
		 * Sends error message for user.
		 * 
		 * @param statusCode error code
		 * @param statusText error text
		 */
		private void sendError(int statusCode, String statusText) {

			RequestContext errorContext = new RequestContext(ostream, null, null, null);
			errorContext.setMimeType("txt");
			errorContext.setStatusCode(statusCode);
			errorContext.setStatusText(statusText);
			
			sendResponse(errorContext, statusText);
		}
	}
	
	/**
	 * Class representing a single session map entry for one user.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class SessionMapEntry {
		/** Session ID: */
		String sid;
		/** Time stamp for session expiration. */
		long validUntil;
		/** Map of cookies. */
		Map<String, String> map;
		
		/**
		 * Constructor.
		 * 
		 * @param sid session ID
		 * @param validUntil time stamp for validation
		 */
		public SessionMapEntry(String sid, long validUntil) {
			this.sid = sid;
			this.validUntil = validUntil;
			map = new ConcurrentHashMap<>();
		}
		
		/**
		 * Method updates time stamp.
		 * 
		 * @param validUntil time stamp
		 */
		private void updateTime(long validUntil) {
			this.validUntil = validUntil;
		}
	}
}
