package hr.fer.zemris.java.tecaj_13.dao.jpa;

/**
 * Utility class which is used for conversion between hex and bytes.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Util {

	/** Letters which are accepted as hex chars. */
	private static final char[] HEX_CHARS = {'a', 'b', 'c', 'd', 'e', 'f'};
	
	/**
	 * Conversion from bytes to hex.
	 * 
	 * @param byteArray bytes
	 * @return hex representation
	 */
	public static String bytetohex(byte[] byteArray) {
		String output = "";
		
		for (byte b : byteArray) {
			output += convertToHex(b);
		}
		
		return output;
	}
	
	/**
	 * Conversion from hex to bytes.
	 * 
	 * @param keyText hex representation
	 * @return bytes
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText == null) {
			throw new IllegalArgumentException("Text can not be null.");
		}
		
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("Text must have even number of input chars.");
		}
		
		byte[] output = new byte[keyText.length() / 2];
		
		for (int i = 0, length = keyText.length(); i < length; i += 2) {
			String hex = keyText.substring(i, i + 2);
			byte resultByte = convertToByte(hex);
			output[i / 2]  = resultByte;
		}
		
		return output;
	}
	
	/**
	 * Helper method which converts single byte to hex.
	 * 
	 * @param b byte
	 * @return hex representation of byte
	 */
	private static String convertToHex(byte b) {
		String output = "";
		
		int significantBits = b & 0xF0;
		significantBits = significantBits >> 4;
		
		output += fourBitNumberToHexString(significantBits);
		
		int lessSignificantBits = b & 0x0F;
		
		output += fourBitNumberToHexString(lessSignificantBits);
		
		return output;
	}
	
	/**
	 * Helper method converts last four bits of number to hex.
	 * 
	 * @param number number
	 * @return hex representation
	 */
	private static String fourBitNumberToHexString(int number) {
		if (number <= 9) {
			return "" + number;
		} else {
			return "" + (char) (number + 87);
		}
	}
	
	/**
	 * Helper method which converts hex to one byte.
	 * 
	 * @param hex hex which will be converted
	 * @return result byte
	 */
	private static byte convertToByte(String hex) {
		hex = hex.toLowerCase();
		
		if (!charIsValidHexChar(hex.charAt(0)) || !charIsValidHexChar(hex.charAt(1))) {
			throw new IllegalArgumentException("Hex consisted of invalid chars; was: " + hex);
		}
		
		int result = hexCharToNumber(hex.charAt(0));
		
		result = result << 4;
		
		result += hexCharToNumber(hex.charAt(1));
		
		return (byte) result;
	}
	
	/**
	 * Converts one char to its bit representation for byte.
	 * 
	 * @param c char representing hex number
	 * @return number as bit result of that hex
	 */
	private static int hexCharToNumber(char c) {
		if (Character.isDigit(c)) {
			return c - 48;
		}
		
		return c - 87;
	}
	
	/**
	 * Method checks if char is valid to be used in hex number.
	 * 
	 * @param c tested char
	 * @return <code>true</code> if char is valid in hex numbers, <code>false</code> otherwise
	 */
	private static boolean charIsValidHexChar(char c) {
		if (Character.isDigit(c)) {
			return true;
		}
		
		for (int i = 0; i < HEX_CHARS.length; i++) {
			if (HEX_CHARS[i] == c) {
				return true;
			}
		}
		
		return false;
	}
}
