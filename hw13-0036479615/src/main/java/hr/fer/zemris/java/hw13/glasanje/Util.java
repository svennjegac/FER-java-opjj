package hr.fer.zemris.java.hw13.glasanje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility class which supports various number of operations for fetching nominees for voting
 * from definition file, results file, writing to results file, sorting...
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Util {
	
	/** Definition file location. */
	private static final String DEFINITION_FILE = "/WEB-INF/glasanje-definicija.txt";
	/** Results file location. */
	private static final String RESULTS = "/WEB-INF/glasanje-rezultati.txt";
	/** Error constant. */
	public static final String ERROR = "error";

	/**
	 * Method reads nominees from definition file and outputs them as a map.
	 * Map consists of (key, value) pair -> (id, nominee).
	 * 
	 * @param req {@link HttpServletRequest}
	 * @return map of nominees
	 */
	protected static Map<Integer, VoteNominee> readDefinitionFile(HttpServletRequest req) {
		String absoluteString = req.getServletContext().getRealPath(DEFINITION_FILE);;
		
		try (BufferedReader r = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(absoluteString)), StandardCharsets.UTF_8))) {
			Map<Integer, VoteNominee> map = new HashMap<>();
			
			String line;
			while ((line = r.readLine()) != null) {
				String[] args = line.split("\t");
				VoteNominee nominee = new VoteNominee(args);
				map.put(nominee.getId(), nominee);
			}
			
			return map;
		} catch (IOException e) {
			throw new IllegalArgumentException("Can not read from definition file.");
		}
	}
	
	/**
	 * Method accepts map of (key, value) -> (ID, nominee), converts it to list and
	 * sorts that list with provided comparator.
	 * 
	 * @param nominees list of nominees
	 * @param comparator comparator which can compare {@link VoteNominee}
	 * @return sorted list of nominees
	 */
	protected static List<VoteNominee> nomineesMapToSortedList(Map<Integer, VoteNominee> nominees, Comparator<VoteNominee> comparator) {
		List<VoteNominee> list = nominees.entrySet()
											.stream()
											.map(entry -> {
												return entry.getValue();
											})
											.collect(Collectors.toList());
		list.sort(comparator);
		return list;
	}
	
	/**
	 * Method reads content from results file and outputs it as a list which consists of
	 * elements {ID, votes}.
	 * If results file is not created yet, method will create it with all votes set to 0.
	 * 
	 * @param req {@link HttpServletRequest}
	 * @return list of voting results
	 */
	protected static List<int[]> readResultsFile(HttpServletRequest req) {
		String absoluteString = req.getServletContext().getRealPath(RESULTS);
		Path absolutePath = Paths.get(absoluteString);
		
		if (!Files.exists(absolutePath)) {
			createResultsFile(req);
		}
		
		try (BufferedReader r = new BufferedReader(new InputStreamReader(Files.newInputStream(absolutePath), StandardCharsets.UTF_8))) {
			List<int[]> results = new ArrayList<>();
			
			String line;
			while ((line = r.readLine()) != null) {
				String[] args = line.split("\t");
				if (args.length != 2) {
					throw new IllegalArgumentException("Wrong number of parameters in result file line, expected 2; were: " + args.length);
				}
				
				int id;
				int votes;
				
				try {
					id = Integer.parseInt(args[0]);
					votes = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("ID, votes pair can not be parsed; was: id='" + args[0] + "', votes: '" + args[1] + "'.");
				}
				
				results.add(new int[]{id, votes});
			}
			
			return results;
		} catch (IOException e) {
			throw new IllegalArgumentException("Error while accesing results file");
		}
	}
	
	/**
	 * Method creates default results file with all votes set to 0.
	 * 
	 * @param req {@link HttpServletRequest}
	 */
	private static void createResultsFile(HttpServletRequest req) {
		Map<Integer, VoteNominee> nominees = Util.readDefinitionFile(req);
		writeToResultsFile(req, nominees);
	}
	
	/**
	 * Method writes map of nominees to results file.
	 * 
	 * @param req {@link HttpServletRequest}
	 * @param nominees map of nominees
	 */
	protected static void writeToResultsFile(HttpServletRequest req, Map<Integer, VoteNominee> nominees) {
		try (OutputStream os = Files.newOutputStream(Paths.get(req.getServletContext().getRealPath(RESULTS)))) {
			nominees.entrySet().forEach(entry -> {
				String line = "" + entry.getValue().getId() + "\t" + entry.getValue().getVotes() + "\r\n";
				try {
					os.write(line.getBytes(StandardCharsets.UTF_8));
				} catch (IOException e) {
					throw new IllegalArgumentException("Error while accesing results file");
				}
			});
			
		} catch (IOException e) {
			throw new IllegalArgumentException("Error while accesing results file");
		}
	}

	/**
	 * Method reads results of voting, reads bands from definition file and
	 * merges them together and outputs map (ID, nominee)
	 * 
	 * @param req {@link HttpServletRequest}
	 * @return map of pairs (ID, nominee)
	 */
	public static Map<Integer, VoteNominee> mergeNomineesWithResults(HttpServletRequest req) {
		List<int[]> results = readResultsFile(req);
		Map<Integer, VoteNominee> nominees = readDefinitionFile(req);
		
		results.forEach(pair -> {
			int id = pair[0];
			int votes = pair[1];
			
			nominees.get(id).setVotes(votes);
		});
		
		return nominees;
	}
}
