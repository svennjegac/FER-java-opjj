package hr.fer.zemris.java.hw06.shell;

import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;

/**
 * Class which offers to user an extraordinary experience of using shell.
 * Shell has many commands.
 * Command line arguments are not used.
 * Shell terminates when exit is entered.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class MyShell {

	/**
	 * Main method which starts when program is run.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		Environment environment = new EnvironmentConcrete();
		ShellStatus status = null;
		
		System.out.println("Welcome to MyShell v 1.0");
		do {
			String input = getUsersCompleteInput(environment);
			String commandName = extractCommandName(input);
			String arguments = input.substring(commandName.length()).trim();
			ShellCommand command = environment.commands().get(commandName);
			
			if (command == null) {
				environment.writeln("Unrecognizable command; was: " + commandName);
				continue;
			}
			
			try {
				status = command.executeCommand(environment, arguments);
			} catch (ShellIOException e) {
				return;
			}
			
		} while (status != ShellStatus.TERMINATE);
	}
	
	/**
	 * Extracts command from users input.
	 * 
	 * @param input users input
	 * @return command
	 */
	private static String extractCommandName(String input) {
		String[] args = input.split("\\s+");
		 return args.length == 0 ? "" : args[0];
	}
	
	/**
	 * Method accepts users input until input is done.
	 * Input is done when users enters ENTER, and last symbol is not morelines symbol.
	 * Method returns formatted input which means that all is one string without morelines symbols.
	 * 
	 * @param environment environment reference
	 * @return users input
	 */
	private static String getUsersCompleteInput(Environment environment) {
		environment.write(environment.getPromptSymbol() + " ");
		
		StringBuilder userInput = new StringBuilder();
		while (true) {
			String line = environment.readLine();
			
			if (line.length() >= 1) {
				if (line.charAt(line.length() - 1) == environment.getMorelinesSymbol()) {
					userInput.append(line.substring(0, line.length() - 1));
					environment.write(environment.getMultilineSymbol() + " ");
					continue;
				}
			}
			
			userInput.append(line);
			break;
		}
		
		return userInput.toString().trim();
	}

}
