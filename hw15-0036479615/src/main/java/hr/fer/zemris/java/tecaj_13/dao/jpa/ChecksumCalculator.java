package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class used to calculate checksums for DB passwords.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ChecksumCalculator {
	
	/** Default algorithm. */
	private static final String DEFAULT_ALGORITHM = "SHA-1";

	/**
	 * Calculates checksum for string.
	 * 
	 * @param input input
	 * @return checksum
	 */
	public static String checkSum(String input) {
		try (ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes())) {
			return checkSum(bis, DEFAULT_ALGORITHM);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Calculates chekcksum.
	 * 
	 * @param inputStream input stream of data
	 * @param algorythm algorithm
	 * @return chekcksum
	 * @throws NoSuchAlgorithmException if algorithm does not exist
	 * @throws IOException if stream operation fails
	 */
	public static String checkSum(InputStream inputStream, String algorythm) throws NoSuchAlgorithmException, IOException {
		MessageDigest digestFunction = MessageDigest.getInstance(algorythm);
		
		byte[] inputBytes = new byte[1024];
		
		while (true) {
			int number = inputStream.read(inputBytes);
			
			if (number == -1) {
				break;
			}
			
			digestFunction.update(inputBytes, 0, number);
		}
		
		return Util.bytetohex(digestFunction.digest());
	}
}
