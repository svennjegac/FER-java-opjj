package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.UtilShell;

/**
 * Commands accepts one argument - file path and dumps its hex
 * representation to user.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class HexDumpShellCommand implements ShellCommand {

	/** Command name. */
	private static final String COMMAND_NAME = "hexdump";
	/** Command description. */
	private static List<String> description;
	
	/**
	 * Default constructor.
	 */
	public HexDumpShellCommand() {
		description = new ArrayList<>();
		description.add("Command accepts one argument - file path and dumps its hex representation to user.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilShell.fetchArguments(env, arguments);
		
		if (args == null) {
			return ShellStatus.CONTINUE;
		}
		
		if (args.size() != 1) {
			env.writeln("Not valid number of arguments for " + getCommandName() + " command; was: " + args.size());
			return ShellStatus.CONTINUE;
		}
		
		Path filePath = UtilShell.getPath(env, args.get(0));
		if (filePath == null) {
			return ShellStatus.CONTINUE;
		}
		
		if (!Files.isRegularFile(filePath)) {
			env.writeln("Regular file was not provided.");
			return ShellStatus.CONTINUE;
		}
		
		hexPrint(env, filePath);
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Method makes a hexprint of file to user.
	 * 
	 * @param env environment reference
	 * @param filePath path to file
	 */
	private void hexPrint(Environment env, Path filePath) {
		try (BufferedInputStream is = new BufferedInputStream(Files.newInputStream(filePath))) {
			
			byte[] bytes = new byte[16];
			int row = 0;
			
			while (true) {
				int number = is.read(bytes);
				
				if (number == -1) {
					break;
				}
				
				env.writeln(getRowOutput(row, bytes, number));
				row += 16;
			}
			
		} catch (Exception e) {
			env.writeln("Could not hex dump file.");
		}
	}
	
	/**
	 * Method makes single row representation for hexprint.
	 * 
	 * @param row row number
	 * @param bytes bytes to be printed
	 * @param number number of bytes
	 * @return row representation
	 */
	private String getRowOutput(int row, byte[] bytes, int number) {
		StringBuilder sb = new StringBuilder();
		
		//counter on start of row
		sb.append(String.format("%08x:", row));
		
		getHexPartRepresentation(bytes, number, sb);
		
		sb.append(" | ");
		sb = new StringBuilder(sb.toString().toUpperCase());
		
		getSymbolPartRepresentation(bytes, number, sb);
		
		return sb.toString();
	}
	
	/**
	 * Method makes representation of hex part.
	 * 
	 * @param bytes bytes to be printed
	 * @param number number of bytes
	 * @param sb string builder reference
	 */
	private void getHexPartRepresentation(byte[] bytes, int number, StringBuilder sb) {
		for (int i = 0; i < bytes.length; i++) {
			if (i < number) {
				sb.append(printByte(bytes[i], i != bytes.length / 2));
				continue;
			}
			
			if (i == bytes.length / 2) {
				sb.append("|  ");
			} else {
				sb.append("   ");
			}
		}
	}
	
	/**
	 * Method makes representation of symbol part.
	 * 
	 * @param bytes bytes to be printed
	 * @param number number of bytes
	 * @param sb string builder reference
	 */
	private void getSymbolPartRepresentation(byte[] bytes, int number, StringBuilder sb) {
		for (int i = 0; i < number; i++) {
			if (bytes[i] < 32 || bytes[i] > 127) {
				sb.append(".");
				continue;
			}
			
			sb.append(new String(bytes, i, 1));
		}
	}
	
	/**
	 * Method makes representation of single byte.
	 * 
	 * @param b byte
	 * @param condition condition if it is in the middle or not
	 * @return byte representation
	 */
	private String printByte(byte b, boolean condition) {
		if (condition) {
			return String.format(" %02x", b);
		}
		
		return String.format("|%02x", b);
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
