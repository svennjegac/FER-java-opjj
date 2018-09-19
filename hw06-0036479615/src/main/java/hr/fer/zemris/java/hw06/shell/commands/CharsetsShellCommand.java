package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.UtilShell;

/**
 * This command accepts zero arguments and prints all
 * available charsets to user.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class CharsetsShellCommand implements ShellCommand {

	/** Command name. */
	private static final String COMMAND_NAME = "charsets";
	/** Command description. */
	private static List<String> description;
	
	/**
	 * Default constructor.
	 */
	public CharsetsShellCommand() {
		description = new ArrayList<>();
		description.add("This command outputs all available charsets to user.");
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
		
		SortedMap<String, Charset> charsetsMap = Charset.availableCharsets();
		
		charsetsMap.forEach((k, v) -> { env.writeln(k); });
		
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
