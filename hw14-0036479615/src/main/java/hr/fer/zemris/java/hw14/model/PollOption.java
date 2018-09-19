package hr.fer.zemris.java.hw14.model;

import java.util.Comparator;

/**
 * Class representing a single poll option.
 * It has few properties and can be used as a java bean for easier
 * constructing of HTML page.
 * Class natural order will sort objects of this class based on their ID.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class PollOption implements Comparable<PollOption> {
		
	/** Poll option ID. */
	private int id;
	/** Poll option name. */
	private String name;
	/** Poll option link. */
	private String link;
	/** Number of votes for poll option. */
	private int votes;
	/** ID of poll which is owner of this poll option. */
	private int pollID;
	
	/**
	 * Comparator used for comparing poll options by number of votes.
	 */
	public static final Comparator<PollOption> VOTES_COMPARATOR = new Comparator<PollOption>() {
		public int compare(PollOption o1, PollOption o2) {
			return Integer.compare(o2.votes, o1.votes);
		};
	};
	
	/**
	 * Constructor which accepts several parameters.
	 * 
	 * @param id poll option ID
	 * @param name poll option name
	 * @param link poll option link
	 * @param votes poll option number of votes
	 * @param pollID owner of this poll option
	 */
	public PollOption(int id, String name, String link, int votes, int pollID) {
		this.id = id;
		this.name = name;
		this.link = link;
		this.votes = votes;
		this.pollID = pollID;
	}

	/**
	 * Constructor which accepts array of parameters.
	 * 
	 * @param args array of parameters
	 */
	public PollOption(String[] args) {
		initializeNominee(args);
	}
	
	/**
	 * Getter for ID.
	 * 
	 * @return ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter for name.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for link.
	 * 
	 * @return link
	 */
	public String getLink() {
		return link;
	}
	
	/**
	 * Getter for number of votes.
	 * 
	 * @return number of votes
	 */
	public int getVotes() {
		return votes;
	}
	
	/**
	 * Getter for poll ID.
	 * 
	 * @return poll ID
	 */
	public int getPollID() {
		return pollID;
	}
	
	/**
	 * Setter for number of votes.
	 * 
	 * @param votes number of votes
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}

	/**
	 * Method initializes poll option properties based on
	 * array of arguments.
	 * 
	 * @param args array of arguments
	 */
	private void initializeNominee(String[] args) {
		if (args.length != 2) {
			throw new IllegalArgumentException("Expected 2 arguments; were: " + args.length);
		}
		
		id = 0;
		name = args[0];
		link = args[1];
		votes = 0;
		pollID = 0;
	}
	
	@Override
	public String toString() {
		return "id:" + id + ", name:" + name + ", link:" + link + ", votes:" + votes + ", pollid:" + pollID;
	}

	@Override
	public int compareTo(PollOption o) {
		return Integer.compare(id, o.id);
	}
}