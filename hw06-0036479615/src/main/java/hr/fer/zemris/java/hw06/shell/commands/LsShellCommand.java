package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.UtilShell;

/**
 * Command writes a directory listing to user.
 * It writes if object is directory, is readable, is writable, is executable.
 * It writes date and time of its creation, its size and name.
 * It accepts one argument - a directory.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class LsShellCommand implements ShellCommand {

	/** Command name. */
	private static final String COMMAND_NAME = "ls";
	/** Command description. */
	private static List<String> description;
	
	/**
	 * Default constructor.
	 */
	public LsShellCommand() {
		description = new ArrayList<>();
		
		description.add("This command writes directory listing.");
		description.add("It accepts one argument.");
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
		
		if (Files.isDirectory(filePath)) {
			try {
				List<Path> list = Files.list(filePath).collect(Collectors.toList());
				
				for (Path path : list) {
					processSinglePath(env, path);
				}
			
			} catch (IOException e) {
				env.writeln("Error while listing files.");;
			}
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Method processes single path and outputs it.
	 * 
	 * @param env environment reference
	 * @param path path to file
	 */
	private void processSinglePath(Environment env, Path path) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(Files.isDirectory(path)	? "d" : "-");
		sb.append(Files.isReadable(path)	? "r" : "-");
		sb.append(Files.isWritable(path)	? "w" : "-");
		sb.append(Files.isExecutable(path)	? "x" : "-");
		
		try {
			sb.append(String.format("%10d ", Files.size(path)));
		} catch (IOException e) {
			env.writeln("Error reading size.");
			return;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(
				path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
		);
		BasicFileAttributes attributes;
		
		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			env.writeln("Error reading file attributes.");
			return;
		}
		
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis())); 
		
		sb.append(formattedDateTime + " ");
		sb.append(path.getFileName());
		
		env.writeln(sb.toString());
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
