package hr.fer.zemris.bf.qmc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import demo.QMC;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.parser.Parser;
import hr.fer.zemris.bf.utils.Util;

/**
 * Parser used in user interaction program {@link QMC} for parsing user input.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class MinimizerParser {

	/** Message if something bad occurs. */
	private static final String MESSAGE = "Pogreška: funkcija nije ispravno zadana.";
	/** List of variables. */
	private static List<String> variables = null;
	/** List of sets of integers which represents minterms and do not cares. */
	private static List<Set<Integer>> indexes = null;
	
	/**
	 * Gets variables.
	 * 
	 * @return variables
	 */
	public static List<String> getVariables() {
		return variables;
	}
	
	/**
	 * Gets indexes.
	 * 
	 * @return indexes
	 */
	public static List<Set<Integer>> getIndexes() {
		return indexes;
	}
	
	/**
	 * Method which parses user input and sets class properties.
	 * 
	 * @param input user input
	 */
	public static void parseUserInput(String input) {
		if (input.indexOf("=") != input.lastIndexOf("=")) {
			throw new IllegalArgumentException(MESSAGE);
		}
		
		String[] arguments = input.split("=");
		
		if (arguments.length != 2) {
			throw new IllegalArgumentException(MESSAGE);
		}
		
		variables = MinimizerParser.parseFunctionDefinition(arguments[0]);
		
		indexes = MinimizerParser.parseFunctionExpressions(arguments[1], variables);
	}
	
	/**
	 * Method parses left side of equals sign in function and returns a list of variables.
	 * 
	 * @param definition left side of equals sign in function
	 * @return list of variables
	 */
	public static List<String> parseFunctionDefinition(String definition) {
		String[] fields = definition.split("\\(");
		
		if (fields.length != 2) {
			throw new IllegalArgumentException(MESSAGE);
		}
		
		parseFunctionName(fields[0]);
		
		return parseFunctionParentheses(fields[1]);
	}
	
	/**
	 * Validates function name.
	 * 
	 * @param name function name
	 */
	private static void parseFunctionName(String name) {
		if (!name.trim().matches("[a-zA-Z][\\w]*")) {
			throw new IllegalArgumentException(MESSAGE);
		}
	}
	
	/**
	 * Parses function parentheses and returns list of variables.
	 * 
	 * @param parentheses parentheses part of function
	 * @return list of variables
	 */
	private static List<String> parseFunctionParentheses(String parentheses) {
		parentheses = parentheses.trim();
		
		if (!parentheses.matches("[^\\)]*\\)")) {
			throw new IllegalArgumentException(MESSAGE);
		}
		
		parentheses = parentheses.substring(0, parentheses.length() - 1);
		
		List<String> variables = new ArrayList<>();
		
		String var = "([A-Z])([A-Z0-9_])*";
		if (!parentheses.matches("(\\s*" + var + "\\s*)(\\s*,\\s*" + var + "\\s*)*")) {
			throw new IllegalArgumentException(MESSAGE);
		}
		
		String[] arguments = parentheses.split(",");
		
		int i = 0;
		while (i < arguments.length) {
			variables.add(arguments[i].trim());
			i++;
			continue;
		}

		return variables;
	}
	
	/**
	 * Parses right part of function equals sign and returns a list of sets of integers
	 * which represents minterms and do not cares.
	 * 
	 * @param function right part of function
	 * @param variables function variables
	 * @return list of sets of integers representing minterms and do not cares
	 */
	public static List<Set<Integer>> parseFunctionExpressions(String function, List<String> variables) {
		if ((function.indexOf("|") != function.lastIndexOf("|")) || function.indexOf("|") == function.length() - 1) {
			throw new IllegalArgumentException(MESSAGE);
		}
		
		String[] expressions = function.split("\\|");
		
		List<Set<Integer>> results = new ArrayList<>();
		
		for (String expression : expressions) {
			results.add(parseSingleExpression(expression, variables));
		}
		
		if (results.size() == 1) {
			results.add(new HashSet<>());
		}
		
		return results;
	}
	
	/**
	 * Parses single expression.
	 * 
	 * @param expression single expression
	 * @param variables function variables
	 * @return set of minterms or do not cares
	 */
	private static Set<Integer> parseSingleExpression(String expression, List<String> variables) {
		if (expression.trim().startsWith("[")) {
			return parseExpressionIndexes(expression.trim());
		}
		
		Node parserExpression = new Parser(expression).getExpression();
		
		return Util.toSumOfMinterms(variables, parserExpression);
	}
	
	/**
	 * Parses expression given as minter or do not care indexes.
	 * 
	 * @param expression single expression
	 * @return set of minterms or do not cares
	 */
	private static Set<Integer> parseExpressionIndexes(String expression) {
		if (!expression.matches("(\\[\\s*)([0-9]+(\\s*,\\s*[0-9]+\\s*)*)?(\\s*\\])")) {
			throw new IllegalArgumentException(MESSAGE);
		}
		
		expression = expression.substring(1, expression.length() - 1);
		
		Set<Integer> resultSet = new HashSet<>();
		String[] indexes = expression.split(",");
		
		for (String string : indexes) {
			String index = string.trim();
			
			if (index.length() == 0) {
				continue;
			}
			
			resultSet.add(Integer.parseInt(index));
		}
		
		return resultSet;
	}
}
