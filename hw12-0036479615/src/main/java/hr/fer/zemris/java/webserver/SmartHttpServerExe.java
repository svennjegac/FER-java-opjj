package hr.fer.zemris.java.webserver;

import java.util.Scanner;

/**
 * Class which starts and stops {@link SmartHttpServer}.
 * It accepts one argument - path to server.properties which are used for
 * server configuration.
 * Server terminates when user inputs "exit".
 * This works very good on mozilla. Google chrome sometimes opens
 * connections which are never closed, so read request block in them.
 * Solution which catches exception if input stream is closed while reading does not solve this.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SmartHttpServerExe {

	/** Command to exit program. */
	private static final String EXIT = "exit";
	
	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		try {
			if (args.length != 1) {
				System.out.println("Provide 1 path argument...");
				return;
			}
			
			SmartHttpServer server = new SmartHttpServer(args[0]);
			
			//thread which runs server
			Thread t = new Thread() {
				@Override
				public void run() {
					server.start();
				}
			};
			t.start();
			
			Scanner sc = new Scanner(System.in);
			
			while (true) {
				String input = sc.next();
				
				if (input.trim().equals(EXIT)) {
					server.stop();
					break;
				}
			}
			
			sc.close();
			
		} catch (SmartHttpServerException e) {
			System.out.println(e.getMessage());
		}
	}
}
