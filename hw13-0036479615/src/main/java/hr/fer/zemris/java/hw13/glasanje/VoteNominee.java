package hr.fer.zemris.java.hw13.glasanje;

import java.util.Comparator;

/**
 * Class representing a single vote nominee.
 * It has few properties and can be used as a java bean for easier
 * constructing of HTML page.
 * Class natural order will sort objects of this class based on thier ID.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class VoteNominee implements Comparable<VoteNominee> {
		
	/** Nominee ID. */
	private int id;
	/** Band name. */
	private String bandName;
	/** Song link. */
	private String songLink;
	/** Number of votes for nominee. */
	private int votes;
	
	/**
	 * Comparator used for comparing nominees by number of votes.
	 */
	protected static final Comparator<VoteNominee> VOTES_COMPARATOR = new Comparator<VoteNominee>() {
		public int compare(VoteNominee o1, VoteNominee o2) {
			return Integer.compare(o2.votes, o1.votes);
		};
	};
	
	/**
	 * Constructor which accepts array of parameters.
	 * 
	 * @param args array of parameters
	 */
	public VoteNominee(String[] args) {
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
	 * Getter for band name.
	 * 
	 * @return band name
	 */
	public String getBandName() {
		return bandName;
	}
	
	/**
	 * Getter for song link.
	 * 
	 * @return song link
	 */
	public String getSongLink() {
		return songLink;
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
	 * Setter for number of votes.
	 * 
	 * @param votes number of votes
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}

	/**
	 * Method initializes nominees properties based on
	 * array of arguments.
	 * 
	 * @param args array of arguments
	 */
	private void initializeNominee(String[] args) {
		if (args.length != 3) {
			throw new IllegalArgumentException("Expected 3 arguments; were: " + args.length);
		}
		
		try {
			id = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Band id can not be parsed; was: " + args[0]);
		}
		
		bandName = args[1];
		songLink = args[2];
		votes = 0;
	}

	@Override
	public int compareTo(VoteNominee o) {
		return Integer.compare(id, o.id);
	}
}