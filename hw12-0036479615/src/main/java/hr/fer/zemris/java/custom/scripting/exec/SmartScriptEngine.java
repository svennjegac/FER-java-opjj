package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.collections.EmptyStackException;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class is an engine which accepts two arguments,
 * {@link DocumentNode} and {@link RequestContext}.
 * Class processes script which is parsed in node and outputs
 * script result to {@link OutputStream} defined in request context.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class SmartScriptEngine {
	
	/** Name of stack which has for loop variables names. */
	private static final String FOR_LOOP_NAMES = "forLoopnNames";
	/** Name of stack which has for loop variables values. */
	private static final String FOR_LOOP_VALUES = "forLoopValues";
	
	/** Sin function. */
	private static final String SINUS_FUNCTION = "sin";
	/** Decimal format function. */
	private static final String DECIMAL_FORMAT_FUNCTION = "decfmt";
	/** Duplicate function. */
	private static final String DUPLICATE_FUNCTION = "dup";
	/** Swap function. */
	private static final String SWAP_FUNCTION = "swap";
	/** Set MIME type function. */
	private static final String SET_MIME_TYPE_FUNCTION = "setMimeType";
	/** Get parameter function. */
	private static final String PARAM_GET_FUNCTION = "paramGet";
	/** Get persistent parameter function. */
	private static final String PERS_PARAM_GET_FUNCTION = "pparamGet";
	/** Set persistent parameter function. */
	private static final String PERS_PARAM_SET_FUNCTION = "pparamSet";
	/** Delete persistent parameter function. */
	private static final String PERS_PARAM_DEL_FUNCTION = "pparamDel";
	/** Get temporary parameter function. */
	private static final String TEMP_PARAM_GET_FUNCTION = "tparamGet";
	/** Set temporary parameter function. */
	private static final String TEMP_PARAM_SET_FUNCTION = "tparamSet";
	/** Delete temporary parameter function. */
	private static final String TEMP_PARAM_DEL_FUNCTION = "tparamDel";
	
	/** Document node. */
	private DocumentNode documentNode;
	/** Request context. */
	private RequestContext requestContext;
	/** Object multistack. */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * Interface which defines method which gets parameter.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private interface ParameterGetter {
		/**
		 * Getter for parameter.
		 * 
		 * @param name parameter key
		 * @return parameter value
		 */
		public String getParam(String name);
	}
	
	/**
	 * Interface which defines method which sets parameter.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private interface ParameterSetter {
		
		/**
		 * Setter for parameter.
		 * 
		 * @param name parameter key
		 * @param value parameter value
		 */
		public void setParam(String name, String value);
	}
	
	/**
	 * Interface which defines method which deletes parameter.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private interface ParameterDeleter {
		
		/**
		 * Method removes parameter.
		 * 
		 * @param name parameter key
		 */
		public void delParam(String name);
	}
	
	/**
	 * Implementation of {@link INodeVisitor}.
	 * It goes through document structure and processes document as a script.
	 * When scripts defines output, it is sent to output stream of request context.
	 */
	private INodeVisitor visitor = new INodeVisitor() {
		
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException ignorable) {}
		}
		
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ValueWrapper end = new ValueWrapper(node.getEndExpression().asText());
			ValueWrapper step = new ValueWrapper(node.getStepExpression() == null ? Integer.valueOf(1) : node.getStepExpression().asText());
			
			multistack.push(FOR_LOOP_NAMES, new ValueWrapper(node.getVariable().asText()));
			multistack.push(FOR_LOOP_VALUES, new ValueWrapper(node.getStartExpression().asText()));
			
			while (true) {
				if (multistack.peek(FOR_LOOP_VALUES).numCompare(end.getValue()) == 1) {
					break;
				}
				
				iterateOverChildren(node);
				
				multistack.peek(FOR_LOOP_VALUES).add(step.getValue());
			}
			
			multistack.pop(FOR_LOOP_NAMES);
			multistack.pop(FOR_LOOP_VALUES);
		}
		
		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<ValueWrapper> temporaryStack = new Stack<>();
			
			Element[] echoElements = node.getElements();
			
			for (int i = 0; i < echoElements.length; i++) {
				processEchoElement(echoElements[i], temporaryStack);
			}
			
			List<ValueWrapper> stackElements = new ArrayList<>();
			
			while (!temporaryStack.isEmpty()) {
				stackElements.add(temporaryStack.pop());
			}
			
			for (int i = stackElements.size() - 1; i >= 0; i--) {
				try {
					requestContext.write(stackElements.get(i).getValue().toString());
				} catch (IOException ignorable) {}
			}
		}
		
		@Override
		public void visitDocumentNode(DocumentNode node) {
			iterateOverChildren(node);
		}
		
		/**
		 * Method accepts element and temporary stack.
		 * It determines type of element and delegates further processing to other methods.
		 * 
		 * @param element processed element
		 * @param temporaryStack temporary stack of echo node
		 */
		private void processEchoElement(Element element, Stack<ValueWrapper> temporaryStack) {
			if (element instanceof ElementConstantDouble || element instanceof ElementConstantInteger || element instanceof ElementString) {
				temporaryStack.push(new ValueWrapper(element.asText()));
				return;
			}
			
			if (element instanceof ElementVariable) {
				processEchoVariable((ElementVariable) element, temporaryStack);
				return;
			}
			
			if (element instanceof ElementOperator) {
				processEchoOperator((ElementOperator) element, temporaryStack);
				return;
			}
			
			if (element instanceof ElementFunction) {
				processEchoFunction((ElementFunction) element, temporaryStack);
				return;
			}
		}
		
		/**
		 * Method accepts variable and pushes its value on temporary stack.
		 * 
		 * @param element variable element
		 * @param temporaryStack echo node temporary stack
		 */
		private void processEchoVariable(ElementVariable element, Stack<ValueWrapper> temporaryStack) {
			temporaryStack.push(getVariableValue(element.asText()));
		}

		/**
		 * Method determines value for given variable.
		 * 
		 * @param variableName variable name
		 * @return value of variable
		 */
		private ValueWrapper getVariableValue(String variableName) {
			List<ValueWrapper> rememberNames = new ArrayList<>();
			List<ValueWrapper> rememberValues = new ArrayList<>();
			ValueWrapper valueWrapper;
			
			//find variable value on for loop stack
			//remember variables which are poped
			while (true) {
				ValueWrapper forLoopName = multistack.pop(FOR_LOOP_NAMES);
				ValueWrapper forLoopValue = multistack.pop(FOR_LOOP_VALUES);
				rememberNames.add(forLoopName);
				rememberValues.add(forLoopValue);
				
				if (forLoopName.getValue().equals(variableName)) {
					valueWrapper = new ValueWrapper(Integer.valueOf(0));
					valueWrapper.add(forLoopValue.getValue());
					break;
				}
			}
			
			//get for loop stack in initial state
			for (int i = rememberNames.size() - 1; i >= 0; i--) {
				multistack.push(FOR_LOOP_NAMES, rememberNames.get(i));
				multistack.push(FOR_LOOP_VALUES, rememberValues.get(i));
			}
			
			return valueWrapper;
		}
		
		/**
		 * Method accepts echo operator and makes operation on two last parameters in echo tag.
		 * Result is then pushed on stack.
		 * 
		 * @param element operator element
		 * @param temporaryStack echo node temporary stack
		 */
		private void processEchoOperator(ElementOperator element, Stack<ValueWrapper> temporaryStack) {
			ValueWrapper num2 = temporaryStack.pop();
			ValueWrapper num1 = temporaryStack.pop();
			
			temporaryStack.push(makeOperation(num1, num2, element));
		}

		/**
		 * Method makes operation on two elements.
		 * 
		 * @param num1 first element
		 * @param num2 second element
		 * @param element operator
		 * @return result of operation
		 */
		private ValueWrapper makeOperation(ValueWrapper num1, ValueWrapper num2, ElementOperator element) {
			switch (element.asText()) {
			case "+":
				num1.add(num2.getValue());
				break;
			case "-":
				num1.subtract(num2.getValue());
				break;
			case "*":
				num1.multiply(num2.getValue());
				break;
			case "/":
				num1.divide(num2.getValue());
				break;
			default:
				throw new SmartScriptEngineException("Unrecognizable operator.");
			}
			
			return num1;
		}

		/**
		 * Method accepts function name and echo node temporary stack.
		 * It delegates further processing to dedicated function.
		 * 
		 * @param element function element
		 * @param temporaryStack echo node temporary stack
		 */
		private void processEchoFunction(ElementFunction element, Stack<ValueWrapper> temporaryStack) {
			switch (element.asText()) {
			case SINUS_FUNCTION:
				sinusFunction(temporaryStack);
				break;
			case DECIMAL_FORMAT_FUNCTION:
				decimalFormatFunction(temporaryStack);
				break;
			case DUPLICATE_FUNCTION:
				duplicateFunction(temporaryStack);
				break;
			case SWAP_FUNCTION:
				swapFunction(temporaryStack);
				break;
			case SET_MIME_TYPE_FUNCTION:
				setMimeTypeFunction(temporaryStack);
				break;
			case PARAM_GET_FUNCTION:
				getMapValueFunction(temporaryStack, requestContext::getParameter);
				break;
			case PERS_PARAM_GET_FUNCTION:
				getMapValueFunction(temporaryStack, requestContext::getPersistentParameter);
				break;
			case PERS_PARAM_SET_FUNCTION:
				setMapValueFunction(temporaryStack, requestContext::setPersistentParameter);
				break;
			case PERS_PARAM_DEL_FUNCTION:
				delMapValueFunction(temporaryStack, requestContext::removePersistentParameter);
				break;
			case TEMP_PARAM_GET_FUNCTION:
				getMapValueFunction(temporaryStack, requestContext::getTemporaryParameter);
				break;
			case TEMP_PARAM_SET_FUNCTION:
				setMapValueFunction(temporaryStack, requestContext::setTemporaryParameter);
				break;
			case TEMP_PARAM_DEL_FUNCTION:
				delMapValueFunction(temporaryStack, requestContext::removeTemporaryParameter);
				break;
			default:
				throw new SmartScriptEngineException("Unrecognizable function: " + element.asText());
			}
		}

		/**
		 * Method calculates sin of last element on stack.
		 * 
		 * @param temporaryStack echo node temporary stack
		 */
		private void sinusFunction(Stack<ValueWrapper> temporaryStack) {
			ValueWrapper num = temporaryStack.pop();
			//make sure that num has double value stored
			num.add(Double.valueOf(0.0));
			temporaryStack.push(new ValueWrapper(Math.sin(degreesToRadians((Double) num.getValue()))));
		}
		
		/**
		 * Method converts degrees to radians.
		 * 
		 * @param value degrees
		 * @return radians
		 */
		private double degreesToRadians(double value) {
			return (value * Math.PI * 2.0) / 360.0;
		}
		
		/**
		 * Method formats last value on stack by a provided format (also on stack).
		 * 
		 * @param temporaryStack echo node temporary stack
		 */
		private void decimalFormatFunction(Stack<ValueWrapper> temporaryStack) {
			String format = temporaryStack.pop().getValue().toString();
			ValueWrapper num = temporaryStack.pop();
			//make sure that num has double value stored
			num.add(Double.valueOf(0.0));
			DecimalFormat decFormat = new DecimalFormat(format);
			temporaryStack.push(new ValueWrapper(decFormat.format((Double) num.getValue())));
		}
		
		/**
		 * Method duplicates last element on stack.
		 * 
		 * @param temporaryStack echo node temporary stack
		 */
		private void duplicateFunction(Stack<ValueWrapper> temporaryStack) {
			ValueWrapper value = temporaryStack.pop();
			temporaryStack.push(new ValueWrapper(value.getValue()));
			temporaryStack.push(new ValueWrapper(value.getValue()));
		}
		
		/**
		 * Method swaps last two elements on stack.
		 * 
		 * @param temporaryStack echo node temporary stack
		 */
		private void swapFunction(Stack<ValueWrapper> temporaryStack) {
			ValueWrapper v1 = temporaryStack.pop();
			ValueWrapper v2 = temporaryStack.pop();
			temporaryStack.push(v1);
			temporaryStack.push(v2);
		}
		
		/**
		 * Method sets MIME type of {@link RequestContext} to one provided
		 * on stack.
		 * 
		 * @param temporaryStack echo node temporary stack
		 */
		private void setMimeTypeFunction(Stack<ValueWrapper> temporaryStack) {
			requestContext.setMimeType(temporaryStack.pop().getValue().toString());
		}
		
		/**
		 * Method gets parameter value and pushes it on stack.
		 * 
		 * @param temporaryStack echo node temporary stack
		 * @param getter getter for parameter {@link ParameterGetter}
		 */
		private void getMapValueFunction(Stack<ValueWrapper> temporaryStack, ParameterGetter getter) {
			String defValue = temporaryStack.pop().getValue().toString();
			String name = temporaryStack.pop().getValue().toString();
			String mapValue = getter.getParam(name);
			
			temporaryStack.push(new ValueWrapper((mapValue == null ? defValue : mapValue)));
		}
		
		/**
		 * Method sets parameter with value provided on stack.
		 * 
		 * @param temporaryStack echo node temporary stack
		 * @param setter setter for parameter {@link ParameterSetter}
		 */
		private void setMapValueFunction(Stack<ValueWrapper> temporaryStack, ParameterSetter setter) {
			String name = temporaryStack.pop().getValue().toString();
			String value = temporaryStack.pop().getValue().toString();
			setter.setParam(name, value);
		}
		
		/**
		 * Method deletes entry from map.
		 * 
		 * @param temporaryStack echo node temporary stack
		 * @param deleter deleter for parameter {@link ParameterDeleter}
		 */
		private void delMapValueFunction(Stack<ValueWrapper> temporaryStack, ParameterDeleter deleter) {
			String name = temporaryStack.pop().getValue().toString();
			deleter.delParam(name);
		}

		/**
		 * Method iterates over every node child and calls visitor.
		 * 
		 * @param node node whose children will be visited by visitor
		 */
		private void iterateOverChildren(Node node) {
			for (int i = 0, size = node.numberOfChildren(); i < size; i++) {
				visitNode(node.getChild(i));
			}
		}
		
		/**
		 * Method determines node type and calls appropriate
		 * method for visitor.
		 * 
		 * @param node node on which visitor will be accepted
		 */
		private void visitNode(Node node) {
			if (node instanceof TextNode) {
				TextNode textNode = (TextNode) node;
				textNode.accept(this);
				return;
			}
			
			if (node instanceof EchoNode) {
				EchoNode echoNode = (EchoNode) node;
				echoNode.accept(this);
				return;
			}
			
			if (node instanceof ForLoopNode) {
				ForLoopNode forLoopNode = (ForLoopNode) node;
				forLoopNode.accept(this);
				return;
			}
			
			if (node instanceof DocumentNode) {
				DocumentNode documentNode = (DocumentNode) node;
				documentNode.accept(this);
				return;
			}
		}
	};
	
	/**
	 * Constructor of engine which accepts document node and request context.
	 * 
	 * @param documentNode document node which has parsed structure of element
	 * @param requestContext request context which has parameters which will be used in calculations and
	 * 							which has output stream on which result will be sent
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	
	/**
	 * Method executes script.
	 */
	public void execute() {
		try {
			documentNode.accept(visitor);
		} catch (EmptyStackException |
				IllegalArgumentException |
				java.util.EmptyStackException |
				ClassCastException e) {
			
			throw new SmartScriptEngineException(e);
		} catch (Exception e) {
			throw new SmartScriptEngineException(e);
		}
	}
}
