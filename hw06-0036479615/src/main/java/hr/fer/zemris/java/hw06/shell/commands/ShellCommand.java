package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Interface representing a single shell command.
 * Shell command must realize three methods:
 * -execution of command
 * -getter for command name
 * -getter for command description
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface ShellCommand {

	/**
	 * Method receives environment reference and arguments in one string line.
	 * Method utilizes commands functionality.
	 * 
	 * @param env environment reference
	 * @param arguments one line String arguments
	 * @return ShellStatus - whether shell should continue wit executing or terminate
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Getter for command name.
	 * 
	 * @return command name
	 */
	String getCommandName();
	
	/**
	 * Getter for command description.
	 * 
	 * @return command description
	 */
	List<String> getCommandDescription();
}
