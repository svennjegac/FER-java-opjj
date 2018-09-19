package hr.fer.zemris.java.custom.scripting.demo;

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
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Simple class. It has an inner static class which implements
 * interface {@link INodeVisitor} and has ability to write parsed document
 * structure to console.
 * This class has main method which accepts one argument - path to file which has
 * a smart script.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class TreeWriter {

	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Provide 1 argument");
			return;
		}
		
		try {
			String docBody = new String(Files.readAllBytes(Paths.get(args[0])));
			SmartScriptParser p = new SmartScriptParser(docBody);
			WriterVisitor visitor = new WriterVisitor();
			p.getDocumentNode().accept(visitor);
		} catch (Exception e) {
			System.out.println("Can not process given document.");
		}
	}
	
	/**
	 * Implementation of {@link INodeVisitor}.
	 * It goes through whole document structure and prints its
	 * content to console.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			String text = addEscapes(
								node.getText(),
								EscapableArrays.textEscapable(),
								ParserDelimiters.ESCAPE_DELIMITER.getValue()
			);
			
			System.out.print(text);
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String forLoopTag = ParserDelimiters.OPEN_TAG_DELIMITER.getValue()
					+ ParserDelimiters.FOR_LOOP_DELIMITER.getValue()
					+ " ";

			forLoopTag += printInTagElement(node.getVariable()) + " ";
			forLoopTag += printInTagElement(node.getStartExpression()) + " ";
			forLoopTag += printInTagElement(node.getEndExpression()) + " ";
			forLoopTag += printInTagElement(node.getStepExpression()) + " ";
			
			forLoopTag += ParserDelimiters.CLOSE_TAG_DELIMITER.getValue();
			System.out.print(forLoopTag);
			
			for (int i = 0, size = node.numberOfChildren(); i < size; i++) {
				visitNode(node.getChild(i));
			}
			
			String closeTag = ParserDelimiters.OPEN_TAG_DELIMITER.getValue()
					+ ParserDelimiters.END_LOOP_DELIMITER.getValue()
					+ ParserDelimiters.CLOSE_TAG_DELIMITER.getValue();
			
			System.out.print(closeTag);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			String echoTag = ParserDelimiters.OPEN_TAG_DELIMITER.getValue()
					+ ParserDelimiters.ECHO_TAG_DELIMITER.getValue()
					+ " ";

			for (int i = 0, number = node.getElements().length; i < number; i++) {
				echoTag += printInTagElement(node.getElements()[i]) + " ";
			}

			echoTag += ParserDelimiters.CLOSE_TAG_DELIMITER.getValue();
			
			System.out.print(echoTag);
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, size = node.numberOfChildren(); i < size; i++) {
				visitNode(node.getChild(i));
			}
		}
		
		/**
		 * Method which determines type of node, and
		 * calls appropriate visitor method.
		 * 
		 * @param node node on which visitor must be called
		 */
		private void visitNode(Node node) {
			if (node instanceof TextNode) {
				TextNode textNode = (TextNode) node;
				textNode.accept(this);
			}
			
			if (node instanceof EchoNode) {
				EchoNode echoNode = (EchoNode) node;
				echoNode.accept(this);
			}
			
			if (node instanceof ForLoopNode) {
				ForLoopNode forLoopNode = (ForLoopNode) node;
				forLoopNode.accept(this);
			}
			
			if (node instanceof DocumentNode) {
				DocumentNode documentNode = (DocumentNode) node;
				documentNode.accept(this);
			}
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
								EscapableArrays.stringReverseEscapable(),
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
}
