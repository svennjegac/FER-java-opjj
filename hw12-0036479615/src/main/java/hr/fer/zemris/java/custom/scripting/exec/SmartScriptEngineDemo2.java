package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Demo program which accepts single path to script argument and
 * outputs result to console.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SmartScriptEngineDemo2 {

	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 * @throws IOException if something goes wrong
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Provide 1 path.");
			return;
		}
		
		
		String documentBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		
		// create engine and execute it
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();
	}
}
