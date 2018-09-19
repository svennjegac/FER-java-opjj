package hr.fer.zemris.java.hw06.shell;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexDumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkDirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * Concrete implementation of environment.
 * It uses input stream as system.in and output stream as system.out.
 * It has number of available commands:
 * symbol, charsets, cat, ls, tree, copy, mkdir, hexdump, exit, help.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class EnvironmentConcrete implements Environment {

	/** Map of commands. */
	SortedMap<String, ShellCommand> commands;
	
	/** Input stream. */
	private BufferedReader is;
	/** Output stream */
	private BufferedWriter os;
	
	/** Multiline symbol. */
	private static char MULTILINESYMBOL = '|';
	/** Morelines symbol. */
	private static char MORELINESSYMBOL = '\\';
	/** Prompt symbol. */
	private static char PROMPTSYMBOL = '>';
	
	/**
	 * Default constructor which registers all commands.
	 */
	public EnvironmentConcrete() {
		is = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)));
		os = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(System.out)));
		
		commands = new TreeMap<>();
		commands.put("symbol", new SymbolShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkDirShellCommand());
		commands.put("hexdump", new HexDumpShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
	}
	
	@Override
	public String readLine() throws ShellIOException {
		try {
			return is.readLine();
		} catch (IOException e) {
			throw new ShellIOException(e.getCause());
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			os.write(text);
			os.flush();
		} catch (IOException e) {
			throw new ShellIOException(e.getCause());
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		write(String.format("%s%n", text));
		
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return MULTILINESYMBOL;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINESYMBOL = symbol;
		
	}

	@Override
	public Character getPromptSymbol() {
		return PROMPTSYMBOL;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPTSYMBOL = symbol;
		
	}

	@Override
	public Character getMorelinesSymbol() {
		return MORELINESSYMBOL;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINESSYMBOL = symbol;
		
	}

}
