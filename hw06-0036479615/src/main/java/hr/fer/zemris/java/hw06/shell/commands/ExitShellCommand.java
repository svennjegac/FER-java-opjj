package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.UtilShell;

/**
 * Command accepts no arguments and exits from shell.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ExitShellCommand implements ShellCommand {

	/** Command name. */
	private static final String COMMAND_NAME = "exit";
	/** Command description. */
	private static List<String> description;
	
	/**
	 * Default constructor.
	 */
	public ExitShellCommand() {
		description = new ArrayList<>();
		description.add("Command accepts no arguments and exits shell.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilShell.fetchArguments(env, arguments);
		
		if (args == null) {
			return ShellStatus.CONTINUE;
		}
		
		if (!args.isEmpty()) {
			env.writeln("Not valid number of arguments for " + getCommandName() + " command; was: " + args.size());
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.TERMINATE;
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
