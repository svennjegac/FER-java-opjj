package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


import hr.fer.zemris.java.custom.scripting.constants.EscapableArrays;
import hr.fer.zemris.java.custom.scripting.constants.ParserDelimiters;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class which tests working of a SmartScriptParser.
 * It accepts one command line argument filepath to some .txt file.
 * Then it reads .txt content and tries to parse it.
 * If parsing was successful it will reconstruct original String
 * form document body.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SmartScriptTester {
	
	/**
	 * Main method called when program is run.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Broj argumenata naredbenog retka mora biti 1.");
			System.exit(-1);
		}
		
		String filepath = args[0];
		String docBody = "";
		
		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(-1);
		}
		
		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBodySecond = createOriginalDocumentBody(document2);
		
		if (originalDocumentBody.equals(originalDocumentBodySecond)) {
			System.out.println("Identični");
		}
	}

	/**
	 * Method accepts document node and reconstructs original
	 * String of a parser input with same semantical meaning.
	 * 
	 * @param parentNode document node
	 * @return String of an input to parser
	 */
	private static String createOriginalDocumentBody(Node parentNode) {
		String text = "";
		for (int i = 0, number = parentNode.numberOfChildren(); i < number; i++) {
			Node node = parentNode.getChild(i);
			
			text += printNode(node);
			text += createOriginalDocumentBody(node);
			
			if (node instanceof ForLoopNode) {
				text +=ParserDelimiters.OPEN_TAG_DELIMITER.getValue()
						+ ParserDelimiters.END_LOOP_DELIMITER.getValue()
						+ ParserDelimiters.CLOSE_TAG_DELIMITER.getValue();
			}
		}
		
		return text;
	}
	
	/**
	 * Method returns String representation of a given node.
	 * 
	 * @param node node which String representation will be returned
	 * @return String representation of a node
	 */
	private static String printNode(Node node) {
		if (node instanceof TextNode) {
			TextNode textNode = (TextNode) node;
			return addEscapes(
					textNode.getText(),
					EscapableArrays.textEscapable(),
					ParserDelimiters.ESCAPE_DELIMITER.getValue()
			);
		}
		
		if (node instanceof ForLoopNode) {
			return printForLoopNode((ForLoopNode) node);
		}
		
		if (node instanceof EchoNode) {
			return printEchoNode((EchoNode) node);
		}
		
		return "";
	}
	
	/**
	 * Method returns String representation of a for loop node.
	 * 
	 * @param forLoopNode for loop node which representation will be returned
	 * @return String representation of a for loop node
	 */
	private static String printForLoopNode(ForLoopNode forLoopNode) {
		String forLoopTag = ParserDelimiters.OPEN_TAG_DELIMITER.getValue()
							+ ParserDelimiters.FOR_LOOP_DELIMITER.getValue()
							+ " ";
		
		forLoopTag += printInTagElement(forLoopNode.getVariable()) + " ";
		forLoopTag += printInTagElement(forLoopNode.getStartExpression()) + " ";
		forLoopTag += printInTagElement(forLoopNode.getEndExpression()) + " ";
		forLoopTag += printInTagElement(forLoopNode.getStepExpression()) + " ";
		
		forLoopTag += ParserDelimiters.CLOSE_TAG_DELIMITER.getValue();
		return forLoopTag;
	}
	
	/**
	 * Method returns String representation of an echo node.
	 * 
	 * @param echoNode echo node whose String representation will be returned
	 * @return String representation of an echo node
	 */
	private static String printEchoNode(EchoNode echoNode) {
		String echoTag = ParserDelimiters.OPEN_TAG_DELIMITER.getValue()
							+ ParserDelimiters.ECHO_TAG_DELIMITER.getValue()
							+ " ";
		
		for (int i = 0, number = echoNode.getElements().length; i < number; i++) {
			echoTag += printInTagElement(echoNode.getElements()[i]) + " ";
		}
		
		echoTag += ParserDelimiters.CLOSE_TAG_DELIMITER.getValue();
		return echoTag;
	}
	
	/**
	 * Returns String representation of an element containing a value.
	 * 
	 * @param element element which String representation will be returned
	 * @return String representation of an element containing a value
	 */
	private static String printInTagElement(Element element) {
		if (element == null) {
			return "";
		}
		
		if (element instanceof ElementFunction) {
			return ParserDelimiters.FUNCTION_DELIMITER.getValue() + element.asText();
		}
		
		if (element instanceof ElementString) {
			return ParserDelimiters.STRING_DELIMITER.getValue()
					+ addEscapes(
							element.asText(),
							EscapableArrays.stringEscpable(),
							ParserDelimiters.ESCAPE_DELIMITER.getValue()
						)
					+ ParserDelimiters.STRING_DELIMITER.getValue();
		}
		
		return element.asText();
	}
	
	/**
	 * Method accepts three parameters.
	 * For every character in text which is contained in an escapedChars array,
	 * it will add before that character String escapeDelimiter.
	 * Method returns re escaped String.
	 * 
	 * @param text text which will be re escaped
	 * @param escapedChars array of chars before which escapeDelimiter will be added
	 * @param escapeDelimiter delimiter which will be added before escapedChars
	 * @return re escaped String
	 */
	private static String addEscapes(String text, char[] escapedChars, String escapeDelimiter) {
		for (int i = 0; i < text.length(); i++) {
			if (inArray(text.charAt(i), escapedChars)) {
				text = text.substring(0, i) + escapeDelimiter + text.charAt(i) + text.substring(i + 1, text.length());
				i += escapeDelimiter.length();
			}
		}
		
		return text;
	}
	
	/**
	 * Method tests if char c is in array.
	 * 
	 * @param c tested char
	 * @param array array in which char will be looked up
	 * @return true if char c is found, otherwise false
	 */
	private static boolean inArray(char c, char[] array) {
		for (int i = 0; i < array.length; i++) {
			if (c == array[i]) {
				return true;
			}
		}
		
		return false;
	}
}
