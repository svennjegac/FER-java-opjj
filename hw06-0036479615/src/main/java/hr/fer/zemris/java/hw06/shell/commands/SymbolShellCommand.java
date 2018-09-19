package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.UtilShell;

/**
 * Command prints symbol for dedicated meaning if one argument is given.
 * If two arguments are given it changes symbol for given meaning.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SymbolShellCommand implements ShellCommand {

	/** Command name. */
	private static final String COMMAND_NAME = "symbol";
	/** Command description. */
	private static List<String> description;
	
	/** Prompt. */
	private static final String PROMPT = "PROMPT";
	/** Morelines. */
	private static final String MORELINES = "MORELINES";
	/** Multiline. */
	private static final String MULTILINE = "MULTILINE";
	
	/**
	 * Default constructor.
	 */
	public SymbolShellCommand() {
		description = new ArrayList<>();
		
		description.add("If command take one argument it prints symbol for provided meaning.");
		description.add("If command gets two arguments it sets a new symbol for provided meaning.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilShell.fetchArguments(env, arguments);
		
		if (args == null) {
			return ShellStatus.CONTINUE;
		}
		
		if (args.size() < 1 || args.size() > 2) {
			env.writeln("Not valid number of arguments for " + getCommandName() + " command; was: " + args.size());
			return ShellStatus.CONTINUE;
		}
		
		if (args.size() == 1) {
			processOneArgument(env, args.get(0));
		}
		
		if (args.size() == 2) {
			processTwoArguments(env, args.get(0), args.get(1));
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Interface for one environment task.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private interface EnvironmentTask {
		/**
		 * Method which defines environment task.
		 * 
		 * @param env environment reference
		 * @param symbol symbol
		 */
		void doTask(Environment env, char symbol);
	}
	
	/**
	 * Method processes statement based on argument which defines which keyword must be processed
	 * and based on flag change which determines if symbol should change or not.
	 * 
	 * @param env environment reference
	 * @param argument keyword for symbol command
	 * @param task environment task
	 * @param change flag if symbol should change
	 * @param newSym new symbol
	 */
	private void statement(Environment env, String argument, EnvironmentTask task, boolean change, char newSym) {
		char symbol;
		
		switch (argument) {
			case PROMPT:
				symbol = env.getPromptSymbol();
				if (change) {
					env.setPromptSymbol(newSym);
				}
				break;
			case MORELINES:
				symbol= env.getMorelinesSymbol();
				if (change) {
					env.setMorelinesSymbol(newSym);
				}
				break;
			case MULTILINE:
				symbol = env.getMultilineSymbol();
				if (change) {
					env.setMultilineSymbol(newSym);
				}
				break;
			default:
				env.writeln("Not recognizable argument; was: " + argument);
				return;
		}
		
		task.doTask(env, symbol);
	}
	
	/**
	 * Method processes one argument
	 * 
	 * @param env environment reference
	 * @param argument single argument
	 */
	private void processOneArgument(Environment env, String argument) {
		statement(env, argument, (en, sym) -> { en.writeln("Symbol for " + argument + " is '" + sym + "'"); }, false, 'x');
	}
	
	/**
	 * Method which processes two arguments.
	 * 
	 * @param env environment reference
	 * @param keyWord keyword for symbol command
	 * @param newChar new character for keyword
	 */
	private void processTwoArguments(Environment env, String keyWord, String newChar) {
		if (newChar.length() != 1) {
			env.writeln("New delimiter for " + keyWord + " must be one char; was: " + newChar);
			return;
		}
		
		statement(env, keyWord, (en, sym) -> {
			env.writeln("Symbol for " + keyWord + " changed from '" + sym + "' to '" + newChar + "'");
		}, true, newChar.charAt(0));
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
