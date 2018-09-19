package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class gives user ability to check checksum of some file, to encrypt file
 * an to decrypt file.
 * If checksum will be calculated one command line argument is needed(file name).
 * If encryption or decryption will be made, two arguments are needed(file names).
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Crypto {

	/** Checksum key word. */
	private static final String CHECK_SHA = "checksha";
	/** Encryption key word. */
	private static final String ENCRYPT = "encrypt";
	/** Decryption key word. */
	private static final String DECRYPT = "decrypt";
	
	/** Checksum algorithm. */
	private static final String SHA_256 = "SHA-256";
	/** Key spec. */
	private static final String KEY_SPEC_ALGORITHM = "AES";
	/** Cipher algorithm spec. */
	private static final String CIPHER_ALGORITHM_SPEC = "AES/CBC/PKCS5Padding";
	
	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Please provide command line arguments.");
			return;
		}
		
		Scanner sc = new Scanner(System.in);
		
		switch (args[0]) {
		case CHECK_SHA:	
			try {
				processChecksum(args, sc);
			} catch (NoSuchAlgorithmException | IOException | IllegalArgumentException ex) {
				System.out.println("Exception: " + ex.getClass() + "; Message: " + ex.getMessage());
			}
			break;
		case ENCRYPT:
			try {
				processCryptography(true, args, sc, KEY_SPEC_ALGORITHM, CIPHER_ALGORITHM_SPEC);
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
					| IOException | IllegalArgumentException ex) {
				System.out.println("Exception: " + ex.getClass() + "; Message: " + ex.getMessage());
			}
			break;
		case DECRYPT:
			try {
				processCryptography(false, args, sc, KEY_SPEC_ALGORITHM, CIPHER_ALGORITHM_SPEC);
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
					| IOException | IllegalArgumentException ex) {
				System.out.println("Exception: " + ex.getClass() + "; Message: " + ex.getMessage());
			}
			break;
		default:
			System.out.println("Wrong keyword specified; was: " + args[0]);
			break;
		}
		
		sc.close();
		
	}
	
	/**
	 * Method processes checksum operation.
	 * 
	 * @param args command line arguments
	 * @param sc scanner
	 * @throws IOException when reading fails
	 * @throws NoSuchAlgorithmException if algorithm does not exist
	 */
	private static void processChecksum(String[] args, Scanner sc) throws IOException, NoSuchAlgorithmException {
		if (!checkCommandLineArguments(2, args)) return;
		
		String fileName = args[1];
		
		System.out.println("Please provide expected " + SHA_256 + " digest for " + fileName + ":");
		String expectedDigest = getUserInput(sc);
		
		InputStream inputStream = new BufferedInputStream(Files.newInputStream(Paths.get(fileName), StandardOpenOption.READ));
		String calculatedDigest = checkSum(inputStream, SHA_256);
		inputStream.close();
		
		if (calculatedDigest.equalsIgnoreCase(expectedDigest)) {
			System.out.println("Digesting completed. Digest of " + fileName + " matches expected digest. ");
		} else {
			System.out.println("Digesting completed. Digest of "
					+ fileName + " does not match the expected digest. "
					+ "Digest was: " + calculatedDigest);
		}
	}
	
	/**
	 * Method calculates digest from input stream.
	 * 
	 * @param inputStream input stream
	 * @param algorythm calculating algorithm
	 * @return digest of stream
	 * @throws NoSuchAlgorithmException if algorithm is invalid
	 * @throws IOException if reading from stream fails
	 */
	private static String checkSum(InputStream inputStream, String algorythm) throws NoSuchAlgorithmException, IOException {
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

	/**
	 * Method processes cryptography.
	 * If encrypt is true encryption will be made, otherwise decryption will be made.
	 * 
	 * @param encrypt flag for encryption or decryption
	 * @param args command line arguments
	 * @param sc scanner
	 * @param keySpecAlgorithm key spec
	 * @param cipherAlgorithmSpec cipher spec
	 * @throws NoSuchAlgorithmException if algorithm is invalid
	 * @throws NoSuchPaddingException bad padding
	 * @throws InvalidKeyException if key is invalid
	 * @throws InvalidAlgorithmParameterException if algorithm parameters are invalid
	 * @throws IOException if stream reading or writing fails
	 * @throws IllegalBlockSizeException if block is illegal
	 * @throws BadPaddingException if padding fails
	 */
	private static void processCryptography(boolean encrypt, String[] args, Scanner sc,
			String keySpecAlgorithm, String cipherAlgorithmSpec)
					throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, BadPaddingException
	{
		if (!checkCommandLineArguments(3, args)) return;
		String inputFile = args[1];
		String outputFile = args[2];
		
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits): ");
		String keyText = getUserInput(sc);
		
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits): ");
		String ivText = getUserInput(sc);
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), keySpecAlgorithm);
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = Cipher.getInstance(cipherAlgorithmSpec);
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		
		InputStream inputStream = new BufferedInputStream(Files.newInputStream(Paths.get(inputFile), StandardOpenOption.READ));
		OutputStream outputStream = new BufferedOutputStream(
				Files.newOutputStream(Paths.get(outputFile), StandardOpenOption.CREATE, StandardOpenOption.WRITE)
		);
		
		cryptography(inputStream, outputStream, cipher);

		inputStream.close();
		outputStream.close();
		
		if (encrypt) {
			System.out.println("Encryption completed. Generated file " + outputFile + " based on file " + inputFile);
		} else {
			System.out.println("Decryption completed. Generated file " + outputFile + " based on file " + inputFile);
		}
	}
	
	/**
	 * Method takes initialized cipher object and makes dedicated cryptography.
	 * 
	 * @param inputStream input stream
	 * @param outputStream output stream
	 * @param cipher cipher object
	 * @throws IOException if reading or writing to stream fails
	 * @throws IllegalBlockSizeException if block is illegal size
	 * @throws BadPaddingException if padding is bad
	 */
	private static void cryptography(InputStream inputStream, OutputStream outputStream, Cipher cipher) throws IOException, IllegalBlockSizeException, BadPaddingException {
		byte[] inputBytes = new byte[1024];
		
		while (true) {
			int number = inputStream.read(inputBytes);
			
			if (number == 1024) {
				byte[] result = cipher.update(inputBytes, 0, number);
				outputStream.write(result, 0, result.length);
				continue;
			}
			
			if (number != -1) {
				byte[] result = cipher.doFinal(inputBytes, 0, number);
				outputStream.write(result, 0, result.length);
				outputStream.flush();
			}
			
			break;
		}
	}
	
	/**
	 * Method for interaction with user.
	 * 
	 * @param sc scanner
	 * @return user input
	 */
	private static String getUserInput(Scanner sc) {
		System.out.print("> ");
		
		String input = sc.next();
		
		return input;
	}
	
	/**
	 * Method checks if number of arguments is as expected.
	 * 
	 * @param expected expected number of arguments
	 * @param args arguments
	 * @return <code>true</code> if number of arguments is as expected, <code>false</code> otherwise
	 */
	private static boolean checkCommandLineArguments(int expected, String[] args) {
		if (args.length != expected) {
			System.out.println("Wrong number of command line arguments; expected: " + expected + "; was: " + args.length);
			return false;
		}
		
		return true;
	}
}
