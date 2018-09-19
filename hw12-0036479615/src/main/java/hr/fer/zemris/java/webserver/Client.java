package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * My class for testing.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Client {

	/** Request. */
	private static String req = "GET /scripts/brojPoziva.smscr HTTP/1.1\r\n\r\n";
	
	/**
	 * Main method.
	 * 
	 * @param args command line args
	 * @throws Exception exception
	 */
	public static void main(String[] args) throws Exception {
		
		Socket soc = new Socket("127.0.0.1", 5721);
		InputStream is = soc.getInputStream();
		OutputStream os = soc.getOutputStream();
	
		os.write(req.getBytes(StandardCharsets.ISO_8859_1));
		os.flush();
		
		String response = getResponse(is);
		soc.close();
		
		String sid = response.substring(response.indexOf("sid") + 5, response.indexOf("sid") + 25);
		
		
		ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		List<Future<?>> tasks = new ArrayList<>();
		
		for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
			tasks.add(pool.submit(new ServerClient(sid)));
		}
		
		for (Future<?> future : tasks) {
			future.get();
		}

		pool.shutdown();
	}
	
	/**
	 * Server client.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class ServerClient extends Thread {
		
		/** SID. */
		private String sid;
		
		/**
		 * Constructor.
		 * 
		 * @param sid SID
		 */
		public ServerClient(String sid) {
			this.sid = sid;
		}
		
		@Override
		public void run() {
			try {
				int counter = 0;
				while (true) {
					Socket soc = new Socket("127.0.0.1", 5721);
					InputStream is = soc.getInputStream();
					OutputStream os = soc.getOutputStream();
					
					String req2 = "GET /scripts/brojPoziva.smscr HTTP/1.1\r\nCookie: sid=\"" + sid + "\"\r\n\r\n";
					os.write(req2.getBytes(StandardCharsets.ISO_8859_1));
					os.flush();
					
					counter++;
					if (counter == 1000) {
						break;
					}
					
					System.out.println(getResponse(is));
					
					soc.close();
				}
			} catch (Exception e) {}
		}
	}
	
	/**
	 * Response getter.
	 * 
	 * @param is input stream
	 * @return response
	 * @throws Exception exception
	 */
	private static String getResponse(InputStream is) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] read = new byte[1024];
		
		while (true) {
			int r  = is.read(read);
			
			if (r == -1) {
				break;
			}
			
			bos.write(read, 0, r);
		}
		
		return new String(bos.toByteArray(), StandardCharsets.UTF_8);
	}	
}
