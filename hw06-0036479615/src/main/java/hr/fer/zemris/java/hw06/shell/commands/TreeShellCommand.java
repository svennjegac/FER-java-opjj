package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.UtilShell;

/**
 * Command accepts one argument and prints directory structure from
 * given directory to its leafs.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class TreeShellCommand implements ShellCommand {

	/** Command name. */
	private static final String COMMAND_NAME = "tree";
	/** Command description. */
	private static List<String> description;
	
	/**
	 * Default constructor.
	 */
	public TreeShellCommand() {
		description = new ArrayList<>();
		description.add("Command outputs tree structure from given directory to its leafs.");
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
		
		try {
			Files.walkFileTree(filePath, new Visitor(env));
		} catch (IOException e) {
			env.writeln("Tree structure printing failed.");
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Visitor of every directory/file.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class Visitor implements FileVisitor<Path> {

		/** Environment reference. */
		Environment env;
		/** Indentation of output. */
		int indent;
		
		/**
		 * Constructor accepts reference to environment.
		 * 
		 * @param env environment reference
		 */
		public Visitor(Environment env) {
			this.env = env;
			indent = 1;
		}
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			env.writeln(String.format("%" + indent + "s%s", " ", dir.getFileName().toString()));
			indent += 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln(String.format("%" + indent + "s%s", " ", file.getFileName().toString()));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			indent -= 2;
			return FileVisitResult.CONTINUE;
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
