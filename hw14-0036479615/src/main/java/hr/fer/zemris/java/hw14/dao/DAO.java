package hr.fer.zemris.java.hw14.dao;

import java.util.List;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;


/**
 * Interface to the layer of data persistency.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface DAO {

	/**
	 * Method gets poll with provided ID.
	 * 
	 * @param id poll id
	 * @return poll with that ID or <code>null</code> if poll does not exist
	 */
	public Poll getPoll(int id);
	
	/**
	 * Method returns list of polls.
	 * 
	 * @return list of polls
	 */
	public List<Poll> getPolls();
	
	/**
	 * Method returns poll option with provided ID.
	 * 
	 * @param id poll option ID
	 * @return poll option with provided ID or <code>null</code> if poll option does not exist
	 */
	public PollOption getPollOption(int id);
	
	/**
	 * Method returns list of poll options which are poll options
	 * for poll with provided poll ID.
	 * 
	 * @param pollID poll ID
	 * @return list of poll options which are options for poll with provided poll ID
	 */
	public List<PollOption> getPollOptions(int pollID);
	
	/**
	 * Method returns list of poll options which are poll options
	 * for poll with provided poll ID.
	 * Poll options are sorted by number of votes descending.
	 * 
	 * @param pollID poll ID
	 * @return list of poll options which are options for poll with provided poll ID, sorted by votes, descending
	 */
	public List<PollOption> getPollOptionsSortedByVotes(int pollID);
	
	/**
	 * Method updates poll option with provided id and sets votes property
	 * to new number of votes.
	 * 
	 * @param id poll option ID
	 * @param votes new number of votes
	 */
	public void updatePollOption(int id, int votes);
	
	/**
	 * Method initializes structure of data layer.
	 */
	public void initializeDataLayerStructure();
	
}