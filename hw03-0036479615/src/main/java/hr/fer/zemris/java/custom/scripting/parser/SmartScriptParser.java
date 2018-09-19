package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.constants.ParserDelimiters;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * SmartScriptParser parses sequence of tokens provided by SmartScriptLexer.
 * It will construct a document tree of received tokens
 * which can later be switched back to original text(it will have same semantic meaning).
 * 
 * If parsers input is invalid and can not be parsed, SmartScriptParserException will be thrown.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SmartScriptParser {

	/** Lexer used for generating tokens. */
	private SmartScriptLexer lexer;
	/** Stack used for building document tree. */
	private ObjectStack stack;
	
	/**
	 * Constructor which accepts String, initializes lexer with that string,
	 * creates stack with root document node and
	 * delegates parsing to its private method parse();
	 * 
	 * If any possible problem occurs, from trivial to problems with memory
	 * allocation for instantiating new objects, SmartScriptParserException
	 * will be thrown.
	 * 
	 * @param text string input which will be parsed
	 */
	public SmartScriptParser(String text) {
		try {
			if (text == null) {
				throw new SmartScriptParserException("Text used for parser initialization can not be null.");
			}
			
			lexer = new SmartScriptLexer(text);
			stack = new ObjectStack();
			stack.push(new DocumentNode());
			parse();
		} catch (Exception ex) {
			throw new SmartScriptParserException(ex);
		}
	}
	
	/**
	 * Method retrieves tokens from lexer until end of file occurs
	 * and builds a document tree.
	 */
	private void parse() {
		while (true) {
			Token token = getNextToken();
			
			if (token.getType() == TokenType.EOF_TOKEN_TYPE) {
				break;
			}
			
			if (token.getType() == TokenType.TEXT_SEQUENCE_TOKEN_TYPE) {
				addTextTokenAsChildElement(token);
				continue;
			}
			
			if (token.getType() == TokenType.OPEN_TAG_TOKEN_TYPE) {
				processOpenTag();
				continue;
			}
		}
		
		if (stack == null || stack.size() != 1) {
			throw new SmartScriptParserException("Stack does not have valid number of arguments on the end.");
		}
	}
	
	/**
	 * Method returns a document node of parsed input.
	 * 
	 * @return document node
	 */
	public DocumentNode getDocumentNode() {
		if (stack == null) {
			throw new SmartScriptParserException("Stack is set to null.");
		}
		
		if (stack.size() != 1) {
			throw new SmartScriptParserException("Wrong number of arguments on stack.");
		}
		
		return (DocumentNode) stack.peek();
	}
	
	/**
	 * Method returns next token received by lexer.
	 * 
	 * @return next token
	 */
	private Token getNextToken() {
		try {
			Token token = lexer.nextToken();
			
			if (token == null) {
				throw new SmartScriptParserException("Retrieved token was null.");
			}
			
			return token;
		} catch (SmartScriptLexerException ex) {
			throw new SmartScriptParserException(ex);
		}
	}
	
	/**
	 * Method which is called when open tag is received.
	 * It will delegate job to other methods depending on the tag name
	 * which follows after tag name.
	 */
	private void processOpenTag() {
		lexer.setState(SmartScriptLexerState.TAG_NAME_IS_NEXT_STATE);
		Token tagNameToken = getNextToken();
		
		if (tagNameToken.getType() == TokenType.TAG_NAME_TOKEN_TYPE) {
			String tagName = tagNameToken.getValue().toString().toLowerCase();
			lexer.setState(SmartScriptLexerState.TAG_STATE);
			
			if (tagName.equals(ParserDelimiters.FOR_LOOP_DELIMITER.getValue())) {
				processForLoop();
				return;
			}
			
			if (tagName.equals(ParserDelimiters.ECHO_TAG_DELIMITER.getValue())) {
				processEchoTag();
				return;
			}
			
			if (tagName.equals(ParserDelimiters.END_LOOP_DELIMITER.getValue())) {
				processEndLoop();
				return;
			}
			
			throw new SmartScriptParserException("None of the expecting tag names were not provided.");
			
		} else {
			throw new SmartScriptParserException("After open tag, tag name was not provided.");
		}
	}
	
	/**
	 * Method called when FOR_LOOP_DELIMITER is read.
	 * It will try to read parameters of for loop, set them,
	 * and it will put that for loop node on the top of the stack
	 * so every next node until END_LOOP_DELIMITER will be its child.
	 */
	private void processForLoop() {
		Token token = getNextToken();
		
		//first token must be a variable
		if (token.getType() != TokenType.VARIABLE_TOKEN_TYPE) {
			throw new SmartScriptParserException("For loop should start with varible token;"
					+ "was '" + token.getType() + "'");
		}
		
		//set variable
		checkString(token.getValue());
		ElementVariable variable = new ElementVariable((String) token.getValue());
		
		//read next three tokens and check if they are valid
		Token first = getNextToken();
		Element startExpression = getForLoopElement(first);
		
		Token second = getNextToken();
		Element endExpression = getForLoopElement(second);
		
		Token third = getNextToken();
		Element stepExpression;
		
		//third token does not have to be a for loop parameter so check if it is a close tag
		if (third.getType() == TokenType.CLOSE_TAG_TOKEN_TYPE) {
			stepExpression = null;
		} else {
			//if it was not a close tag, get that element
			stepExpression = getForLoopElement(third);
			
			//check that after that element directly follows a close tag
			Token closeTagToken = getNextToken();
			if (closeTagToken.getType() != TokenType.CLOSE_TAG_TOKEN_TYPE) {
				throw new SmartScriptParserException("Too many arguments in for loop.");
			}
		}
		
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		Node forLoopNode = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
		addNodeAsChildElement(forLoopNode);
		stack.push(forLoopNode);
	}
	
	/**
	 * Method called when ECHO_TAG_DELIMITER is read.
	 * It will add all elements to that tag until CLOSE_TAG is reached.
	 */
	private void processEchoTag() {
		int count = 0;
		Element[] elements = new Element[0];
		
		while (true) {
			Token token = getNextToken();
			
			if (token.getType() == TokenType.CLOSE_TAG_TOKEN_TYPE) {
				break;
			}
			
			//copy old elements to new array and
			//add last element to the end of an array
			Element element = getAnyOfElements(token);
			Element[] newElements = new Element[count + 1];
			
			for (int i = 0; i < count; i++) {
				newElements[i] = elements[i];
			}
			
			count++;
			newElements[count - 1] = element;
			elements = newElements;
		}
		
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		EchoNode echoNode = new EchoNode(elements);
		addNodeAsChildElement(echoNode);
	}
	
	/**
	 * Method called when END_LOOP_DLIMITER is reached.
	 * It will try to remove last node from stack.
	 * If there is less than two elements on stack it will
	 * throw SmartScriptParserException.
	 */
	private void processEndLoop() {
		Token token = getNextToken();
		
		if (token.getType() != TokenType.CLOSE_TAG_TOKEN_TYPE) {
			throw new SmartScriptParserException("After end loop tag name, close tag expected; was '" + token.getType() + "'");
		}
		
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
		removeLastNodeFromStack();
	}
	
	/**
	 * It will remove one element from stack if
	 * there is at least two elements on stack.
	 * Otherwise it will throw SmartScriptParserExcception.
	 */
	private void removeLastNodeFromStack() {
		if (stack == null) {
			throw new SmartScriptParserException("Stack is set to null.");
		}
		
		if (stack.size() <= 1) {
			throw new SmartScriptParserException("Can not remove from stack. Stack will be empty. Too much end tags occured.");
		}
		
		stack.pop();
	}
	
	/**
	 * Method accepts token and checks if its kind is allowed to
	 * be in for tag. If it is, it will return appropriate type of Element.
	 * If it is not, it will throw SmartScriptParserException.
	 * 
	 * @param token token which will be processed
	 * @return appropriate element to represent token
	 */
	private Element getForLoopElement(Token token) {
		if (token == null) {
			throw new SmartScriptParserException("Token can not be null.");
		}
		
		if (token.getType() == TokenType.VARIABLE_TOKEN_TYPE) {
			checkString(token.getValue());
			return new ElementVariable((String) token.getValue());
		}
		
		if (token.getType() == TokenType.STRING_TOKEN_TYPE) {
			checkString(token.getValue());
			return new ElementString((String) token.getValue());
		}
		
		if (token.getType() == TokenType.NUMBER_INTEGER_TOKEN_TYPE) {
			checkInteger(token.getValue());
			return new ElementConstantInteger((Integer) token.getValue());
		}
		
		if (token.getType() == TokenType.NUMBER_DOUBLE_TOKEN_TYPE) {
			checkDouble(token.getValue());
			return new ElementConstantDouble((Double) token.getValue());
		}
		
		throw new SmartScriptParserException("Invalid for loop argument; was '" + token.getType() + "'");
	}
	
	/**
	 * Method accepts token and checks if its kind is allowed to
	 * be in echo tag. If it is, it will return appropriate type of Element.
	 * If it is not, it will throw SmartScriptParserException.
	 * 
	 * @param token token which will be processed
	 * @return appropriate element to represent token
	 */
	private Element getAnyOfElements(Token token) {
		if (token == null) {
			throw new SmartScriptParserException("Token can not be null.");
		}
		
		if (token.getType() == TokenType.OPERATOR_TOKEN_TYPE) {
			checkString(token.getValue());
			return new ElementOperator((String) token.getValue());
		}
		
		if (token.getType() == TokenType.FUNCTION_TOKEN_TYPE) {
			checkString(token.getValue());
			return new ElementFunction((String) token.getValue());
		}
		
		return getForLoopElement(token);
	}
	
	/**
	 * Method accepts token of TEXT_SEQUENCE_TOKEN_TYPE and
	 * adds it as child element to tree structure.
	 * 
	 * @param token text token
	 */
	private void addTextTokenAsChildElement(Token token) {
		if (token == null || token.getType() != TokenType.TEXT_SEQUENCE_TOKEN_TYPE) {
			throw new SmartScriptParserException("Can not add token to stack.");
		}
		
		checkString(token.getValue());
		Node child = new TextNode((String) token.getValue());
		addNodeAsChildElement(child);
	}
	
	/**
	 * Method accepts node and adds it as child node to
	 * node which is currently on the top of the stack.
	 * 
	 * @param childNode node which will be added as child node
	 */
	private void addNodeAsChildElement(Node childNode) {
		if (childNode == null) {
			throw new SmartScriptParserException("Provided child node was null.");
		}
		
		if (stack == null) {
			throw new SmartScriptParserException("Can not add to stack. Stack was null.");
		}
		
		if (stack.isEmpty()) {
			throw new SmartScriptParserException("Can not add token as child element. Stack was empty."
					+ "Node was '" + childNode + "'");
		}
		
		Node parentNode = (Node) stack.peek();
		parentNode.addChildNode(childNode);
	}
	
	/**
	 * Method checks if object is instance of String class.
	 * @param value value to be checked
	 */
	private void checkString(Object value) {
		if (!(value instanceof String)) {
			throw new SmartScriptParserException("Object was not type of string.");
		}
	}
	
	/**
	 * Method checks if object is instance of Integer class.
	 * @param value value to be checked
	 */
	private void checkInteger(Object value) {
		if (!(value instanceof Integer)) {
			throw new SmartScriptParserException("Object was not type of integer.");
		}
	}
	
	/**
	 * Method checks if object is instance of Double class.
	 * @param value value to be checked
	 */
	private void checkDouble(Object value) {
		if (!(value instanceof Double)) {
			throw new SmartScriptParserException("Object was not type of double.");
		}
	}
}
