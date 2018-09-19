package demo2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Example class for logging learning.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Logiranje {

	/** Logger. */
	private static final Logger LOG = Logger.getLogger("demo2");
	
	/**
	 * Method which is run when program starts.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		
		Level[] levels = new Level[] {
			Level.SEVERE,
			Level.WARNING,
			Level.INFO,
			Level.CONFIG,
			Level.FINE,
			Level.FINER,
			Level.FINEST
		};
		
		for (Level level : levels) {
			LOG.log(level, "Ovo je poruka " + level + " razine.");
		}
	}
}
