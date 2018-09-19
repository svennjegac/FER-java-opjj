package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;

/**
 * Environment interface.
 * It has ability to read from input stream and write to input stream.
 * It can retrieve commands, set shell symbols and get them.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public interface Environment {

	/**
	 * Method reads line from input stream.
	 * 
	 * @return String that was read
	 * @throws ShellIOException if reading fails
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Method writes to output stream.
	 * 
	 * @param text text which should be written
	 * @throws ShellIOException if writing fails
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Method writes line to output stream.
	 * 
	 * @param text text which should be written
	 * @throws ShellIOException if writing fails
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Method gets map of commands.
	 * 
	 * @return map of commands
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Method gets multiline symbol.
	 * 
	 * @return multiline symbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Method sets multiline symbol.
	 * 
	 * @param symbol multiline symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Method gets prompt symbol.
	 * 
	 * @return prompt symbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Method sets prompt symbol.
	 * 
	 * @param symbol prompt symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * MEthod gets morelines symol.
	 * 
	 * @return morelines symol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Method sets morelines symbol.
	 * 
	 * @param symbol morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
