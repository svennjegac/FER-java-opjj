package hr.fer.zemris.bf.parser;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.bf.constants.Operators;
import hr.fer.zemris.bf.lexer.Lexer;
import hr.fer.zemris.bf.lexer.LexerException;
import hr.fer.zemris.bf.lexer.Token;
import hr.fer.zemris.bf.lexer.TokenType;
import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.OperatorNodeFactory;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Parser for boolean expressions.
 * Parser accepts String input representing a boolean expression
 * and tries to parse it.
 * If parsing is successful it will store an expression as a tree
 * in a root attribute.
 * If parsing fails it will throw a {@link ParserException}.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Parser {

	/** Lexer used to generate tokens. */
	private Lexer lexer;
	/** Attribute which stores parsed expression. */
	private Node root;
	/** Last generated token. */
	private Token lastToken;
	/** Counter which indicates if there is equal number of open and closed brackets. */
	private int bracketsCounter;
	
	/**
	 * Constructor of parser.
	 * 
	 * @param expression String input which will be parsed
	 */
	public Parser(String expression) {
		if (expression == null) {
			throw new ParserException("Expression can not be null.");
		}
		
		bracketsCounter = 0;
		lexer = new Lexer(expression);
		root = parse();
		
		if (bracketsCounter != 0) {
			throw new ParserException("Number of open brackets does not match number of closed brackets.");
		}
	}
	
	/**
	 * Getter for tree-like parsed boolean expression.
	 * 
	 * @return tree-like boolean expression
	 */
	public Node getExpression() {
		return root;
	}
	
	/**
	 * Method generates next token.
	 * Besides, that method counts number of open and closed brackets.
	 * 
	 * @return next token
	 * @throws ParserException if lexer exception occurs while generating token
	 */
	private Token nextToken() {
		try {
			lastToken = lexer.nextToken();
			
			if (lastToken.getTokenType().equals(TokenType.OPEN_BRACKET)) {
				bracketsCounter++;
			} else if (lastToken.getTokenType().equals(TokenType.CLOSED_BRACKET)) {
				bracketsCounter--;
			}
			
			return lastToken;
		} catch (LexerException e) {
			throw new ParserException(e);
		}
	}
	
	/**
	 * Method parses provided expression.
	 * 
	 * @return node representing tree-like expression.
	 */
	private Node parse() {
		return processORStatement();
	}
	
	/**
	 * Method processes all expressions connected with OR operator and merges them.
	 * 
	 * @return tree-like node structure
	 */
	private Node processORStatement() {
		return constructMultipleExpressionsWithOperator(new OROperatorStrategy());
	}
	
	/**
	 * Method processes local (separated by OR operators) expressions
	 * connected with XOR operator and merges them.
	 * 
	 * @return tree-like node structure
	 */
	private Node processXORStatement() {
		return constructMultipleExpressionsWithOperator(new XOROperatorStrategy());
	}
	
	/**
	 * Method processes local (separated by XOR or OR operators) expressions
	 * connected with AND operator and merges them.
	 * 
	 * @return tree-like node structure
	 */
	private Node processANDStatement() {
		return constructMultipleExpressionsWithOperator(new ANDOperatorStrategy());
	}
	
	/**
	 * Method parses one atomic expression.
	 * 
	 * @return Node representing an atomic expression
	 */
	private Node parseAtomicExpression() {
		Token token = nextToken();
		
		if (token.getTokenType().equals(TokenType.VARIABLE)) {
			lastToken = nextToken();
			return new VariableNode((String) token.getTokenValue());
		}
		
		if (token.getTokenType().equals(TokenType.CONSTANT)) {
			lastToken = nextToken();
			return new ConstantNode((Boolean) token.getTokenValue());
		}
		
		if (token.getTokenType().equals(TokenType.OPERATOR) && token.getTokenValue().equals(Operators.NOT_OPERATOR.getName())) {
			return OperatorNodeFactory.makeNOTOperatorNode(parseAtomicExpression());
		}
		
		if (token.getTokenType().equals(TokenType.OPEN_BRACKET)) {
			Node result = parse();
			
			if (!lastToken.getTokenType().equals(TokenType.CLOSED_BRACKET)) {
				throw new ParserException("Closed bracket missing.");
			}
			
			lastToken = nextToken();
			
			return result;
		}
		
		throw new ParserException("Unable to construct expression; was: " + token.getTokenType());
	}
	
	/**
	 * Abstract class representing a strategy.
	 * This strategy represents a single binary operator in
	 * a multiple level recursive calls of binary operators.
	 * 
	 * Each implemented concrete strategy must define its operator
	 * and must define successor operator which will be called to utilize
	 * top-down recursive calls to create expressions.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private abstract class OperatorStrategy {
		/**
		 * Node representing a currently constructed expression.
		 * To expression can be added more expressions which will be
		 * all separated by operator defined in strategy.
		 */
		Node expression;
		
		/** Strategy operator. */
		BinaryOperatorNode operatorNode;
		
		/**
		 * Default constructor of strategy.
		 */
		public OperatorStrategy() {
			this.expression = callSuccessor();
		}

		/**
		 * Getter for currently constructed expression.
		 * 
		 * @return expression
		 */
		public Node getExpression() {
			return expression;
		}
		
		/**
		 * Setter for expression.
		 * 
		 * @param expression new expression
		 */
		public void setExpression(Node expression) {
			this.expression = expression;
		}
		
		/**
		 * Getter for operator strategy operator.
		 * 
		 * @return strategy operator
		 */
		public BinaryOperatorNode getOperatorNode() {
			return operatorNode;
		}
		
		/**
		 * Method which calls next operator in multiple level
		 * operator recursion. It will return a lower level expression
		 * constructed by recursion.
		 * 
		 * @return tree-like expression
		 */
		public abstract Node callSuccessor();
	}
	
	/**
	 * Concrete OR operator strategy.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private class OROperatorStrategy extends OperatorStrategy {
		
		/**
		 * Default constructor which assigns operator.
		 */
		public OROperatorStrategy() {
			operatorNode = OperatorNodeFactory.makeOROperatorNode(new ArrayList<>());
		}
		
		@Override
		public Node callSuccessor() {
			return processXORStatement();
		}
	}
	
	/**
	 * Concrete XOR operator strategy.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private class XOROperatorStrategy extends OperatorStrategy {

		/**
		 * Default constructor which assigns operator.
		 */
		public XOROperatorStrategy() {
			operatorNode = OperatorNodeFactory.makeXOROperatorNode(new ArrayList<>());
		}
		
		@Override
		public Node callSuccessor() {
			return processANDStatement();
		}
	}
	
	/**
	 * Concrete AND operator strategy.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private class ANDOperatorStrategy extends OperatorStrategy {
		
		/**
		 * Default constructor which assigns operator.
		 */
		public ANDOperatorStrategy() {
			operatorNode = OperatorNodeFactory.makeANDOperatorNode(new ArrayList<>());
		}
		
		@Override
		public Node callSuccessor() {
			return parseAtomicExpression();
		}
	}
	
	/**
	 * Method which accepts strategy and constructs one expression
	 * which is constructed of lower level expressions separated by
	 * operator defined in strategy.
	 * 
	 * @param operatorStrategy operator strategy
	 * @return tree-like expression
	 */
	private Node constructMultipleExpressionsWithOperator(OperatorStrategy operatorStrategy) {
		while (true) {
			//if last token is operator construction of expression should be continued
			if (lastToken.getTokenType().equals(TokenType.OPERATOR)) {
				
				//if last token is operator with value of this level of strategy
				//this operator strategy should add more children to its operator
				//if operator is different operator type, it can only be in higher calls so we
				//will skip this step and go to return block of code
				if (lastToken.getTokenValue().equals(operatorStrategy.getOperatorNode().getName())) {
					Node lowerLevelExpression = operatorStrategy.callSuccessor();
					
					if (mergeWithExistingOperator(operatorStrategy, lowerLevelExpression)) {
						continue;
					}
					
					makeNewOperator(operatorStrategy, lowerLevelExpression);
					
					continue;
				}
			}
			
			//return block of code
			//returning is allowed if last token is EOF, CLOSED_BRACKET or OPERATOR
			//specially it is not allowed that returning is made if operator
			//is type of NOT operator because that indicates that invalid expression is given
			if ((lastToken.getTokenType().equals(TokenType.OPERATOR)
					|| lastToken.getTokenType().equals(TokenType.EOF)
					|| lastToken.getTokenType().equals(TokenType.CLOSED_BRACKET))
					&& !Operators.NOT_OPERATOR.getName().equals(lastToken.getTokenValue())) {
				return operatorStrategy.getExpression();
			}
			
			throw new ParserException("Unexpected token; was: " + lastToken.getTokenType());
		}
	}
	
	/**
	 * Method assumes that strategy already had two expressions so in strategy in
	 * expression attribute is not an lower level expression. It expects that in
	 * expression attribute of operator strategy is binary operator which represents that
	 * strategy.
	 * Based on above assumption method will check if that is true and it will try to
	 * add lowerLevelExpression to already constructed operator stored in expression.
	 * 
	 * @param operatorStrategy operator strategy
	 * @param lowerLevelExpression expression constructed of lower level recursions
	 * @return <code>true</code> if method merged lower level expression to expression operator,
	 * 			<code>false</code> if method could not merge lowerLevelExpression
	 */
	private boolean mergeWithExistingOperator(OperatorStrategy operatorStrategy, Node lowerLevelExpression) {
		//check if current expression is binary operator or it is a simple expression
		if (operatorStrategy.getExpression() instanceof BinaryOperatorNode) {
			BinaryOperatorNode operator = (BinaryOperatorNode) operatorStrategy.getExpression();
			
			//if current expression is binary operator check that it is constructed in current strategy
			//if it is binary operator but constructed with operators in lower levels of recursion
			//we do not want to merge with that operator
			if (operator.getName().equals(operatorStrategy.getOperatorNode().getName())) {
				operator.getChildren().add(lowerLevelExpression);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Method is called if expression in operatorStarategy is not type of binary
	 * operator or if it is type of binary operator but operator constructed in
	 * lower level in recursion calls.
	 * Method initializes operatorStrategy operator with current expression attribute
	 * and with newly constructed lowerLevelExpression.
	 * After all method sets current expression of strategy to its operator node, because
	 * operator node represents a current expression and should be returned at the end.
	 * 
	 * @param operatorStrategy operator strategy
	 * @param lowerLevelExpression lowerLevelExpression from recursion
	 */
	private void makeNewOperator(OperatorStrategy operatorStrategy, Node lowerLevelExpression) {
		List<Node> args = new ArrayList<>();
		args.add(operatorStrategy.getExpression());
		args.add(lowerLevelExpression);
		
		operatorStrategy.getOperatorNode().getChildren().addAll(args);
		
		operatorStrategy.setExpression(operatorStrategy.getOperatorNode());
	}
}
