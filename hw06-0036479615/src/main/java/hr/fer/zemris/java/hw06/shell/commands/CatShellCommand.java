package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.UtilShell;

/**
 * This command prints file content to user.
 * It must accept one argument which represents a path to file
 * which will be printed, and second argument is optional - charset to use.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class CatShellCommand implements ShellCommand {

	/** Command name. */
	private static final String COMMAND_NAME = "cat";
	/** Command description. */
	private static List<String> description;
	
	/**
	 * Default constructor.
	 */
	public CatShellCommand() {
		description = new ArrayList<>();
		
		description.add("This command outputs file content to user.");
		description.add("Command must accept one or two arguments.");
		description.add("If one argument is given. It must be file path.");
		description.add("If two arguments are given, it must be file path and charset name.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilShell.fetchArguments(env, arguments);
		
		if (args == null) {
			return ShellStatus.CONTINUE;
		}
		
		if (args.isEmpty() || args.size() > 2) {
			env.writeln("Not valid number of arguments for " + getCommandName() + " command; was: " + args.size());
			return ShellStatus.CONTINUE;
		}
		
		Path filePath = UtilShell.getPath(env, args.get(0));
		Charset cs = setCharset(env, args);
		
		if (filePath == null || cs == null) {
			return ShellStatus.CONTINUE;
		}
		
		printFile(env, filePath, cs);
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Method prints file to environment using given charset.
	 * 
	 * @param env environment reference
	 * @param filePath path to file
	 * @param cs charset to use
	 */
	private void printFile(Environment env, Path filePath, Charset cs) {
		try (BufferedInputStream is = new BufferedInputStream(Files.newInputStream(filePath))) {
			byte[] bytes = new byte[1024];
			
			while (true) {
				int number = is.read(bytes);
				
				if (number != -1) {
					env.write(new String(bytes, 0, number, cs));
					continue;
				}
				
				break;
			}
			env.writeln("");
			
		} catch (IOException e) {
			env.writeln("Could not write file to user.");
		}
	}
	
	/**
	 * Method sets charset to default if no charset is given
	 * or to given charset.
	 * 
	 * @param env environment reference
	 * @param args command arguments
	 * @return charset to use
	 */
	private Charset setCharset(Environment env, List<String> args) {
		if (args.size() == 1) {
			return Charset.defaultCharset();
		}
		
		try {
			return Charset.forName(args.get(1));
		} catch (Exception e) {
			env.writeln("Not supported charset; was: " + args.get(1));
			return null;
		}
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
