package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.UtilShell;

/**
 * Command accepts two arguments and copies first file to second.
 * First is source file and second is destination file.
 * If second file is directory, method will make file with same name as first file in given directory.
 * If destination file already exists, method will ask user if
 * overwriting is allowed.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class CopyShellCommand implements ShellCommand {

	/** Command name. */
	private static final String COMMAND_NAME = "copy";
	/** Command description. */
	private static List<String> description;
	
	/**
	 * Default constructor.
	 */
	public CopyShellCommand() {
		description = new ArrayList<>();
		
		description.add("This command copies source file to destination.");
		description.add("It accepts two arguments - source and destination file.");
		description.add("If destination already exists, user is asked if overwriteing is allowed.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = UtilShell.fetchArguments(env, arguments);
		
		if (args == null) {
			return ShellStatus.CONTINUE;
		}
		
		if (args.size() != 2) {
			env.writeln("Not valid number of arguments for " + getCommandName() + " command; was: " + args.size());
			return ShellStatus.CONTINUE;
		}
		
		Path sourceFile = UtilShell.getPath(env, args.get(0));
		Path destinationFile = UtilShell.getPath(env, args.get(1));
		
		if (sourceFile == null || destinationFile == null) {
			return ShellStatus.CONTINUE;
		}
		
		if (sourceFile.toString().equals(destinationFile.toString())) {
			return ShellStatus.CONTINUE;
		}
		
		if (!Files.isRegularFile(sourceFile)) {
			env.writeln("Source is not a regular file.");
			return ShellStatus.CONTINUE;
		}
		
		//if second argument is directory, make path that will make file in that directory with name
		//same as first file
		if (Files.isDirectory(destinationFile)) {
			destinationFile = Paths.get(destinationFile.toString() + File.separator + sourceFile.getFileName().toString());
		}
		
		//is overwriting needed?
		if (Files.exists(destinationFile) && Files.isRegularFile(destinationFile)) {
			boolean canOverWrite = askUserToOverWrite(env);
			
			if (!canOverWrite) {
				return ShellStatus.CONTINUE;
			}
		}
		
		copyFiles(env, sourceFile, destinationFile);
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Method copies one file to another.
	 * 
	 * @param env environment reference
	 * @param sourceFile source file
	 * @param destinationFile destination file
	 */
	private void copyFiles(Environment env, Path sourceFile, Path destinationFile) {
		try (BufferedInputStream is = new BufferedInputStream(Files.newInputStream(sourceFile));
				BufferedOutputStream os = new BufferedOutputStream(Files.newOutputStream(destinationFile));
				) {
			byte[] bytes = new byte[1024];
			
			while (true) {
				int number = is.read(bytes);
				
				if (number != -1) {
					os.write(bytes, 0, number);
					continue;
				}
				
				break;
			}
		} catch (IOException e) {
			env.writeln("Could not copy files.");
		}
	}
	
	/**
	 * Method interacts with user and determines whether
	 * it overwriting of file is allowed or not.
	 * 
	 * @param env environment reference
	 * @return <code>true</code> if overwriting is allowed, <code>false</code> otherwise
	 */
	private boolean askUserToOverWrite(Environment env) {
		while (true) {
			env.writeln("Do you want to overwrite existing file?");
			env.writeln("Y(yes) / N(no)");
			env.write(env.getPromptSymbol() + " ");
			String answer = env.readLine();
			
			if (answer.equals("Y")) {
				return true;
			} else if (answer.equals("N")) {
				return false;
			}
			
			env.writeln("Your answer was invalid; was: " + answer);
			env.writeln("");
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
