package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.UtilShell;

/**
 * Command takes one argument - path and makes a
 * provided directory structure.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class MkDirShellCommand implements ShellCommand {

	/** Command name. */
	private static final String COMMAND_NAME = "mkdir";
	/** Command description. */
	private static List<String> description;
	
	/**
	 * Default constructor.
	 */
	public MkDirShellCommand() {
		description = new ArrayList<>();
		description.add("Command takes one argument - path and makes directory structure.");
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
		
		Path directoryPath = UtilShell.getPath(env, args.get(0));
		if (directoryPath == null) {
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.createDirectories(directoryPath);
		} catch (IOException e) {
			env.writeln("Could not create directory structure.");
		}
		
		return ShellStatus.CONTINUE;
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
