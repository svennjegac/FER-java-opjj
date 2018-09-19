package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.UtilShell;

/**
 * Command helps user to use shell.
 * With no arguments it outputs list of all commands.
 * If one argument is given(it must be command name argument) it will
 * output help for that command.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class HelpShellCommand implements ShellCommand {

	/** Command name. */
	private static final String COMMAND_NAME = "help";
	/** Command description. */
	private static List<String> description;
	
	/**
	 * Default constructor.
	 */
	public HelpShellCommand() {
		description = new ArrayList<>();
		
		description.add("Command outputs all command names to user.");
		description.add("If one argument is given which must be command name, it outputs help for that command.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilShell.fetchArguments(env, arguments);
		
		if (args == null) {
			return ShellStatus.CONTINUE;
		}
		
		if (args.size() > 1) {
			env.writeln("Not valid number of arguments for " + getCommandName() + " command; was: " + args.size());
			return ShellStatus.CONTINUE;
		}
		
		SortedMap<String, ShellCommand> map = env.commands();
		
		if (args.size() == 0) {
			map.forEach((s, c) -> { env.writeln("-> " + s); });
		}
		
		if (args.size() == 1) {
			ShellCommand command = map.get(args.get(0));
			
			if (command != null) {
				env.writeln(command.getCommandName());
				env.writeln("");
				command.getCommandDescription().forEach(System.out::println);
			} else {
				env.writeln("Command name is invalid; was: " + args.get(0));
			}
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
