package hr.fer.zemris.java.hw14.dao.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw14.PathResolver;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Utility class which offers reading default txt defintion of polls.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Util {
	
	/**
	 * Method reads first line from poll definition file which defines poll.
	 * 
	 * @param relativePathString relative string to file
	 * @return array representing poll
	 */
	public static String[] readFirstLine(String relativePathString) {
		try (BufferedReader r = new BufferedReader(new InputStreamReader(Files.newInputStream(PathResolver.resolve(relativePathString)), StandardCharsets.UTF_8))) {
			String line = r.readLine();
			
			String[] args = line.split("\t");
			if (args.length != 2) {
				throw new IllegalArgumentException("Wrong number of parameters of first line; was: " + args.length);
			}
			
			return args;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * Method reads nominees from poll and returns them as a list.
	 * 
	 * @param relativePathString relative path to polls file
	 * @return list of poll options
	 */
	public static List<PollOption> readNomineesFromFile(String relativePathString) {
		try (BufferedReader r = new BufferedReader(new InputStreamReader(Files.newInputStream(PathResolver.resolve(relativePathString)), StandardCharsets.UTF_8))) {
			List<PollOption> list = new ArrayList<>();
			
			//skip first line which defines poll
			r.readLine();
			
			String line;
			while ((line = r.readLine()) != null) {
				String[] args = line.split("\t");
				PollOption nominee = new PollOption(args);
				list.add(nominee);
			}
			
			return list;
		} catch (IOException e) {
			throw new IllegalArgumentException("Can not read from definition file.");
		}
	}
}
