package hr.fer.zemris.bf.qmc;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import hr.fer.zemris.bf.constants.Constants;
import hr.fer.zemris.bf.constants.Operators;
import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.OperatorNodeFactory;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Minimizer of boolean function.
 * It offers constructor which accepts parameters and does the minimalization.
 * Minimal forms can then be fetched by two different getters.
 * One fetches minimal forms as a strings and other as a node expressions.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Minimizer {

	/** Logger. */
	private static final Logger LOG = Logger.getLogger("hr.fer.zemris.bf.qmc");
	
	/** Set of minterms. */
	private Set<Integer> mintermSet;
	/** Set of do not cares. */
	private Set<Integer> dontCareSet;
	/** List of variables. */
	private List<String> variables;
	
	/** All minimal forms. */
	List<Set<Mask>> minimalForms;
	
	/**
	 * Minimizer constructor. It constructs minimizer and does minimization.
	 * 
	 * @param mintermSet set of minterms
	 * @param dontCareSet set of do not cares
	 * @param variables list of variables
	 */
	public Minimizer(Set<Integer> mintermSet, Set<Integer> dontCareSet, List<String> variables) {
		if (mintermSet == null || dontCareSet == null || variables == null) {
			throw new IllegalArgumentException("Parameters can not be null.");
		}
		
		checkParametersCompatibility(mintermSet, dontCareSet, variables);
		
		this.mintermSet = mintermSet.stream().sorted().collect(Collectors.toSet());
		this.dontCareSet = dontCareSet.stream().sorted().collect(Collectors.toSet());
		this.variables = variables;
		
		Set<Mask> primCover = findPrimaryImplicants();
		minimalForms = chooseMinimalCover(primCover);
		logMinimalForms(Level.FINE);
	}
	
	/**
	 * Gets minimal forms.
	 * 
	 * @return minimal forms
	 */
	public List<Set<Mask>> getMinimalForms() {
		return minimalForms;
	}
	
	/**
	 * Gets minimal forms as strings.
	 * 
	 * @return minimal forms as strings
	 */
	public List<String> getMinimalFormsAsString() {
		List<String> stringExpressions = new ArrayList<>();
		
		minimalForms.forEach(minimalForm -> {
			stringExpressions.add(constructStringExpressionFromMinimalForm(minimalForm));
		});
		
		return stringExpressions;
	}
	
	/**
	 * Constructs single string from one minimal form.
	 * 
	 * @param minimalForm one minimal form
	 * @return string representation
	 */
	private String constructStringExpressionFromMinimalForm(Set<Mask> minimalForm) {
		List<String> products = new ArrayList<>();
		
		minimalForm.forEach(mask -> {
			products.add(constructStringExpressionFromSingleMask(mask));
		});
		
		if (products.isEmpty()) {
			return Constants.FALSE.getName();
		}
		
		//concatenates with OR operator all elements
		while (products.size() > 1) {
			String first = products.get(0);
			String second = products.get(1);
			
			products.remove(0);
			products.remove(0);
			products.add(0, first + " " + Operators.OR_OPERATOR.getName() + " " + second);
		}
		
		return products.get(0);
	}
	
	/**
	 * Constructs one expression in minimal form expression.
	 * It constructs one expression which will be concatenated with other expressions
	 * by OR operator.
	 * This expression can have multiple variables separated with AND operator.
	 * 
	 * @param mask single mask
	 * @return expression
	 */
	private String constructStringExpressionFromSingleMask(Mask mask) {
		List<String> stringExpressionVariables = new ArrayList<>();
		byte[] maskValues = getMaskValues(mask);
		
		for (int i = 0; i < maskValues.length; i++) {
			if (maskValues[i] == 1) {
				stringExpressionVariables.add(variables.get(i));
			} else if (maskValues[i] == 0) {
				stringExpressionVariables.add(Operators.NOT_OPERATOR.getName() + " " + variables.get(i));
			}
		}
		
		if (stringExpressionVariables.isEmpty()) {
			return Constants.TRUE.getName();
		}
		
		//concatenate expressions with AND operator
		while (stringExpressionVariables.size() > 1) {
			String first = stringExpressionVariables.get(0);
			String second = stringExpressionVariables.get(1);
			
			stringExpressionVariables.remove(0);
			stringExpressionVariables.remove(0);
			stringExpressionVariables.add(0, first + " " + Operators.AND_OPERATOR.getName() + " " + second);
		}
		
		return stringExpressionVariables.get(0);
	}
	
	/**
	 * Gets minimal forms ad Nodes.
	 * 
	 * @return minimal forms as nodes
	 */
	public List<Node> getMinimalFormsAsExpressions() {
		List<Node> expressions = new ArrayList<>();
		
		minimalForms.forEach(minimalForm -> {
			expressions.add(constructExpressionFromMinimalForm(minimalForm));
		});
		
		return expressions;
	}
	
	/**
	 * Constructs single minimal form node expression.
	 * 
	 * @param minimalForm single minimal form
	 * @return one node expression
	 */
	private Node constructExpressionFromMinimalForm(Set<Mask> minimalForm) {
		List<Node> nodes = new ArrayList<>();
		
		minimalForm.forEach(mask -> {
			nodes.add(constructExpressionFromSingleMask(mask));
		});
		
		if (nodes.size() == 0) {
			return new ConstantNode(Constants.FALSE.getValue());
		}
		
		//concatenate all nodes with OR operator
		while (nodes.size() > 1) {
			Node first = nodes.get(0);
			Node second = nodes.get(1);
			
			BinaryOperatorNode operator = null;
			
			if (first instanceof BinaryOperatorNode) {
				operator = (BinaryOperatorNode) first;
			}
			
			if (operator != null && operator.getName().equals(Operators.OR_OPERATOR)) {
				operator.getChildren().add(second);
			} else {
				List<Node> children = new ArrayList<>();
				children.add(first);
				children.add(second);
				
				operator = OperatorNodeFactory.makeOROperatorNode(children);
			}
			
			nodes.remove(0);
			nodes.remove(0);
			nodes.add(0, operator);
		}
		
		return nodes.get(0);
	}
	
	/**
	 * Constructs one expression which can have multiple variables separated by
	 * AND operator. This expressions can be separated with OR operator.
	 * 
	 * @param mask single mask
	 * @return one expression Node
	 */
	private Node constructExpressionFromSingleMask(Mask mask) {
		byte[] maskValues = getMaskValues(mask);
		
		List<Node> variablesList = new ArrayList<>();
		
		for (int i = 0; i < maskValues.length; i++) {
			if (maskValues[i] == 1) {
				variablesList.add(new VariableNode(variables.get(i)));
			} else if (maskValues[i] == 0) {
				variablesList.add(OperatorNodeFactory.makeNOTOperatorNode(new VariableNode(variables.get(i))));
			}
		}
		
		if (variablesList.isEmpty()) {
			return new ConstantNode(Constants.TRUE.getValue());
		}
		
		//concatenate all expressions with AND operator
		while (variablesList.size() > 1) {
			Node first = variablesList.get(0);
			Node second = variablesList.get(1);
			
			BinaryOperatorNode operator;
			
			if (first instanceof BinaryOperatorNode) {
				operator = (BinaryOperatorNode) first;
				
				operator.getChildren().add(second);
			} else {
				List<Node> children = new ArrayList<>();
				children.add(first);
				children.add(second);
				
				operator = OperatorNodeFactory.makeANDOperatorNode(children);
			}
			
			variablesList.remove(0);
			variablesList.remove(0);
			variablesList.add(0, operator);
		}
		
		return variablesList.get(0);
	}
	
	/**
	 * Gets all mask values as a single array.
	 * 
	 * @param mask one mask
	 * @return all mask values as a single array
	 */
	private byte[] getMaskValues(Mask mask) {
		byte[] maskValues = new byte[mask.size()];
		
		for (int i = 0, length = mask.size(); i < length; i++) {
			maskValues[i] = mask.getValueAt(i);
		}
		
		return maskValues;
	}
	
	/**
	 * Method finds primary implicants.
	 * 
	 * @return primary implicants
	 */
	private Set<Mask> findPrimaryImplicants() {
		Set<Mask> primaryImplicants = new LinkedHashSet<>();
		
		List<Set<Mask>> firstColumn = createFirstColumn();
		
		while (true) {
			List<Set<Mask>> secondColumn = new ArrayList<>();
			
			int i = 0;
			while (i < firstColumn.size() - 1) {
				Set<Mask> combinedSet = combineColumns(firstColumn, i, i + 1);
				
				if (!combinedSet.isEmpty()) {
					secondColumn.add(combinedSet);
				}
				
				i++;
			}
			
			logColumn(firstColumn, Level.FINER);
			
			Set<Mask> iterationPrimaryImplicants = new LinkedHashSet<>();
			
			//gets primary implicants found in iteration
			firstColumn.forEach(set -> {
				set.forEach(mask -> {
					if (!mask.isCombined() && !mask.isDontCare()) {
						iterationPrimaryImplicants.add(mask);
					}
				});
			});
			
			logIterationPrimaryImplicants(iterationPrimaryImplicants, Level.FINEST);
			
			primaryImplicants.addAll(iterationPrimaryImplicants);
			
			if (secondColumn.isEmpty()) {
				break;
			}
			
			firstColumn = secondColumn;
		}
		
		logPrimaryImplicants(primaryImplicants, Level.FINE);
		
		return primaryImplicants;
	}
	
	/**
	 * Method creates first column in Quine-McCluskey minimization method.
	 * First column has many rows, each having masks with same number of ones.
	 * 
	 * @return first column
	 */
	private List<Set<Mask>> createFirstColumn() {
		List<Set<Mask>> firstColumn = new ArrayList<>();
		
		firstColumn.addAll(makeMapOfCountOnesAndSets().values());
		
		//sorts sets of masks by number of ones in mask in defined set
		return firstColumn
				.stream()
				.sorted(new Comparator<Set<Mask>>() {
						@Override
						public int compare(Set<Mask> o1, Set<Mask> o2) {
							Mask mask1 = o1.iterator().next();
							Mask mask2 = o2.iterator().next();
							
							if (mask1.countOfOnes() < mask2.countOfOnes()) {
								return -1;
							} else if (mask1.countOfOnes() > mask2.countOfOnes()) {
								return 1;
							} else {
								return 0;
							}
						}
					})
				.collect(Collectors.toList());
	}
	
	/**
	 * Makes and returns map which consists of keys(integer number of ones in mask) and 
	 * values, sets which consists of masks which has defined number of ones.
	 * 
	 * @return populated map
	 */
	private Map<Integer, Set<Mask>> makeMapOfCountOnesAndSets() {
		Map<Integer, Set<Mask>> map = new LinkedHashMap<>();
		
		populateMapWithSet(mintermSet, map, false);
		populateMapWithSet(dontCareSet, map, true);
		
		return map;
	}
	
	/**
	 * Populates map which has key integer(number of ones in mask) and its value is set of masks
	 * which have that number of ones.
	 * It populates that map with values from sourceSet, and its values are representing indexes of funtion.
	 * 
	 * @param sourceSet source set of indexes of minterm/do not care
	 * @param map map of sets
	 * @param dontCare new mask flag
	 */
	private void populateMapWithSet(Set<Integer> sourceSet, Map<Integer, Set<Mask>> map, boolean dontCare) {
		sourceSet.forEach(index -> {
			Mask mask = new Mask(index, variables.size(), dontCare);
			
			Set<Mask> destinationSet = map.get(mask.countOfOnes());
			
			if (destinationSet == null) {
				destinationSet = new LinkedHashSet<>();
			}
			
			destinationSet.add(mask);
			map.put(mask.countOfOnes(), destinationSet);
		});
	}
	
	/**
	 * Method used in iterations when masks need to be combined between groups of
	 * masks having number of ones different by one.
	 * 
	 * @param column column consisting sets of masks
	 * @param first index of first set
	 * @param second index of second set
	 * @return combined set
	 */
	private Set<Mask> combineColumns(List<Set<Mask>> column, int first, int second) {
		Set<Mask> combinedSet = new LinkedHashSet<>();
		
		Set<Mask> firstSet = column.get(first);
		Set<Mask> secondSet = column.get(second);
		
		firstSet.forEach(mask1 -> {
			secondSet.forEach(mask2 -> {
				Optional<Mask> combined = mask1.combineWith(mask2);
				
				if (combined.isPresent()) {
					mask1.setCombined(true);
					mask2.setCombined(true);
					
					combinedSet.add(combined.get());
				}
			});
		});
		
		return combinedSet;
	}
	
	/**
	 * Method accepts primary implicants and returns minimal forms.
	 * 
	 * @param primCover primary implicants
	 * @return minimal forms
	 */
	private List<Set<Mask>> chooseMinimalCover(Set<Mask> primCover) {
		Mask[] implicants = primCover.toArray(new Mask[primCover.size()]);
		Integer[] minterms = mintermSet.toArray(new Integer[mintermSet.size()]);
		
		Map<Integer, Integer> mintermToColumnMap = new HashMap<>();
		for (int i = 0; i < minterms.length; i++) {
			Integer index = minterms[i];
			mintermToColumnMap.put(index, i);
		}
		
		boolean[][] table = buildCoverTable(implicants, minterms, mintermToColumnMap);
		
		boolean[] coveredMinterms = new boolean[minterms.length];
		
		Set<Mask> importantSet = selectImportantPrimaryImplicants(implicants, mintermToColumnMap, table, coveredMinterms);
		
		logImportantImplicants(importantSet, Level.FINE);
	
		List<Set<BitSet>> pFunction = buildPFunction(table, coveredMinterms);
		logPFunction(pFunction, Level.FINER);
		
		Set<BitSet> minset = findMinimalSet(pFunction);
		
		List<Set<Mask>> minimalForms = new ArrayList<>();
		
		if (minset.isEmpty()) {
			minimalForms.add(importantSet);
			return minimalForms;
		}
		
		for (BitSet bs : minset) {
			Set<Mask> set = new LinkedHashSet<>(importantSet);
			bs.stream().forEach(i -> set.add(implicants[i]));
			minimalForms.add(set);
		}
		
		return minimalForms;
	}
	
	/**
	 * Builds table which indicates which implicant covers which minterm.
	 * 
	 * @param implicants primary implicants
	 * @param minterms minterms
	 * @param mintermToColumnMap mapped minterms to column indexes
	 * @return table which indicates which implicant covers which minterm
	 */
	private boolean[][] buildCoverTable(Mask[] implicants, Integer[] minterms, Map<Integer, Integer> mintermToColumnMap) {
		boolean[][] table = new boolean[implicants.length][minterms.length];
		
		mintermToColumnMap.forEach((minterm, position) -> {
			for (int i = 0; i < implicants.length; i++) {
				if (implicants[i].getIndexes().contains(minterm)) {
					table[i][position] = true;
				}
			}
		});
		
		return table;
	}
	
	/**
	 * Method returns important primary implicants and sets fields in coveresMinterms
	 * table which indicates which minterms are covered by important implicants.
	 * 
	 * @param implicants primary implicants
	 * @param mintermToColumnMap mapped minterms to column positions
	 * @param table cover table
	 * @param coveredMinterms table which will indicate which minterms are covered by important primary implicants
	 * @return important primary implicants
	 */
	private Set<Mask> selectImportantPrimaryImplicants(
			Mask[] implicants, Map<Integer, Integer> mintermToColumnMap, boolean[][] table, boolean[] coveredMinterms) {
		Set<Mask> primaryImplicants = new HashSet<>();
		
		mintermToColumnMap.forEach((minterm, position) -> {
			Set<Mask> possiblePrimaries = new HashSet<>();
			for (int i = 0; i < table.length; i++) {
				if (table[i][position]) {
					possiblePrimaries.add(implicants[i]);
				}
			}
			
			if (possiblePrimaries.size() > 1) {
				return;
			}
			
			primaryImplicants.addAll(possiblePrimaries);
			
			possiblePrimaries.forEach(mask -> {
				mask.getIndexes().forEach(index -> {
					if (mintermToColumnMap.containsKey(index)) {
						coveredMinterms[mintermToColumnMap.get(index)] = true;
					}
				});
			});
		});
		
		return primaryImplicants;
	}
	
	/**
	 * Method builds function which covers all minterms that are not covered
	 * by important primary implicants.
	 * 
	 * @param table cover table
	 * @param coveredMinterms minterms covered in cover table
	 * @return function which covers all minterms that are not covered
	 * 			by important primary implicants
	 */
	private List<Set<BitSet>> buildPFunction(boolean[][] table, boolean[] coveredMinterms) {
		List<Set<BitSet>> pFunction = new ArrayList<>();
		
		for (int i = 0; i < coveredMinterms.length; i++) {
			if (coveredMinterms[i] == true) {
				continue;
			}
			
			Set<BitSet> oneParentheses = new LinkedHashSet<>();
			for (int j = 0; j < table.length; j++) {
				if (table[j][i] == true) {
					BitSet implicant = new BitSet();
					implicant.set(j);
					oneParentheses.add(implicant);
				}
			}
			
			pFunction.add(oneParentheses);
		}
		
		return pFunction;
	}
	
	/**
	 * Finds set which consists of multiple bit sets.
	 * Each bit set must be included in one minimal form to
	 * build a valid minimal function.
	 * 
	 * @param pFunction p function
	 * @return set consisting of bit sets which represents implicants which need to
	 * 			be included to single minimal form
	 */
	private Set<BitSet> findMinimalSet(List<Set<BitSet>> pFunction) {
		Set<BitSet> minimalSet = new LinkedHashSet<>();
		
		if (pFunction.isEmpty()) {
			return minimalSet;
		}
		
		//multiply all parentheses of p function
		while (pFunction.size() > 1) {
			Set<BitSet> first = pFunction.get(0);
			Set<BitSet> second = pFunction.get(1);
			
			Set<BitSet> productSet = new LinkedHashSet<>();
			
			first.forEach(bitSet1 -> {
				second.forEach(bitSet2 -> {
					BitSet product = new BitSet();
					product.or(bitSet1);
					product.or(bitSet2);
					productSet.add(product);
				});
			});
			
			pFunction.remove(0);
			pFunction.remove(0);
			pFunction.add(0, productSet);
		}
		
		logPFunctionAfterTransformation(pFunction, Level.FINER);
		
		//find minimal cardinality of bit sets representing implicants
		int minimalCardinality = pFunction.get(0)
				.stream()
				.map(BitSet::cardinality)
				.collect(Collectors.minBy(Comparator.naturalOrder()))
				.get();
		
		//add bit sets with minimal cardinality to minimal sets
		pFunction.get(0).forEach(bitSet -> {
			if (bitSet.cardinality() == minimalCardinality) {
				minimalSet.add(bitSet);
			}
		});
		
		logMinimalSet(minimalSet, Level.FINER);
		
		return minimalSet;
	}
	
	/**
	 * Logs all minimal forms.
	 * 
	 * @param level log level
	 */
	private void logMinimalForms(Level level) {
		if (!LOG.isLoggable(level)) {
			return;
		}
		
		LOG.log(level, "");
		LOG.log(level, "Minimalni oblici funkcije su:");
		
		for (int i = 0; i < minimalForms.size(); i ++) {
			LOG.log(level, "" + (i + 1) + ". " + minimalForms.get(i));
		}
	}
	
	/**
	 * Logs p function.
	 * 
	 * @param pFunction p function
	 * @param level log level
	 */
	private void logPFunction(List<Set<BitSet>> pFunction, Level level) {
		if (!LOG.isLoggable(level) || pFunction.isEmpty()) {
			return;
		}
		
		LOG.log(level, "");
		LOG.log(level, "p funkcija je:");
		LOG.log(level, pFunction.toString());
		LOG.log(level, "");
	}
	
	/**
	 * Logs p function after transformation.
	 * 
	 * @param pFunction p function after transformation
	 * @param level log level
	 */
	private void logPFunctionAfterTransformation(List<Set<BitSet>> pFunction, Level level) {
		if (!LOG.isLoggable(level) || pFunction.isEmpty()) {
			return;
		}
		
		LOG.log(level, "Nakon pretvorbe p funkcije u sumu produkata:");
		LOG.log(level, pFunction.toString());
		LOG.log(level, "");
	}
	
	/**
	 * Logs minimal set.
	 * 
	 * @param minimalSet minimal set
	 * @param level log level
	 */
	private void logMinimalSet(Set<BitSet> minimalSet, Level level) {
		if (!LOG.isLoggable(level) || minimalSet.isEmpty()) {
			return;
		}
		
		LOG.log(level, "Minimalna pokrivanja još trebaju:");
		LOG.log(level, minimalSet.toString());
	}
	
	/**
	 * Logs important implicants.
	 * 
	 * @param importantSet important implicants
	 * @param level log level
	 */
	private void logImportantImplicants(Set<Mask> importantSet, Level level) {
		if (!LOG.isLoggable(level) || importantSet.isEmpty()) {
			return;
		}
		
		LOG.log(level, "");
		LOG.log(level, "Bitni primarni implikanti su:");
		
		importantSet.forEach(mask -> {
			LOG.log(level, mask.toString());
		});
	}
	
	/**
	 * Logs primary implicants.
	 * 
	 * @param primaryImplicants primary implicants
	 * @param level log level
	 */
	private void logPrimaryImplicants(Set<Mask> primaryImplicants, Level level) {
		if (!LOG.isLoggable(level) || primaryImplicants.isEmpty()) {
			return;
		}
		
		LOG.log(level, "");
		LOG.log(level, "Svi primarni implikanti:");
		
		primaryImplicants.forEach(implicant -> {
			LOG.log(level, implicant.toString());
		});
	}
	
	/**
	 * Logs primary implicants found in iteration.
	 * 
	 * @param iterationPrimaryImplicants primary implicants found in iteration
	 * @param level log level
	 */
	private void logIterationPrimaryImplicants(Set<Mask> iterationPrimaryImplicants, Level level) {
		if (!LOG.isLoggable(level) || iterationPrimaryImplicants.isEmpty()) {
			return;
		}
		
		iterationPrimaryImplicants.forEach(implicant -> {
			LOG.log(level, "Pronašao primarni implikant: " + implicant.toString());
		});
		
		LOG.log(level, "");
	}
	
	/**
	 * Logs iteration column in QuineMcCluskey method.
	 * 
	 * @param column iteration column in QuineMcCluskey method
	 * @param level log level
	 */
	private void logColumn(List<Set<Mask>> column, Level level) {
		if (!LOG.isLoggable(level)) {
			return;
		}
		
		LOG.log(level, "Stupac tablice:");
		LOG.log(level, "=================================");
		
		Flag flag = new Flag(true);
		column.forEach(set -> {
			if (!flag.isFirst()) {
				LOG.log(level, "------------------------------");
			}
			
			flag.setFirst(false);
			
			set.forEach(mask -> {
				LOG.log(level, mask.toString());
			});
		});
		
		LOG.log(level, "");
	}
	
	/**
	 * Class representing a wrapper for boolean value.
	 * This allows to use boolean values in lambda statements.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class Flag {
		/** Value. */
		private boolean first;
		
		/**
		 * Simple constructor.
		 * 
		 * @param first default value
		 */
		public Flag(boolean first) {
			this.first = first;
		}
		
		/**
		 * Gets value.
		 * 
		 * @return value
		 */
		public boolean isFirst() {
			return first;
		}
		
		/**
		 * Sets value.
		 * 
		 * @param first new value
		 */
		public void setFirst(boolean first) {
			this.first = first;
		}
	}
	
	/**
	 * Checks if parameters given in constructor are valid.
	 * 
	 * @param mintermSet set of minterms
	 * @param dontCareSet set of do not cares
	 * @param variables list of variables
	 */
	private void checkParametersCompatibility(Set<Integer> mintermSet,
			Set<Integer> dontCareSet, List<String> variables) {

		mintermSet.forEach(index -> {
			if (dontCareSet.contains(index)) {
				throw new IllegalArgumentException("Duplicate element in minterm and dontcare sets; was: " + index);
			}
		});
		
		Optional<Integer> maxMinterm = mintermSet.stream().collect(Collectors.maxBy(Comparator.naturalOrder()));
		Optional<Integer> maxDontCare = dontCareSet.stream().collect(Collectors.maxBy(Comparator.naturalOrder()));
		
		int max1 = maxMinterm.isPresent() ? maxMinterm.get() : -1;
		int max2 = maxDontCare.isPresent() ? maxDontCare.get() : -1;
		int max = max1 > max2 ? max1 : max2;
		
		if (max >= Math.pow(2, variables.size())) {
			throw new IllegalArgumentException("Maximum minterm/dontcare is unacceptable for number of variables; "
					+ "max: " + max + ", variables: " + variables.size());
		}
	}
}
