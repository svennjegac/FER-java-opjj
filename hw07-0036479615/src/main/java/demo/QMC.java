package demo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import java.util.List;

import java.util.Set;

import hr.fer.zemris.bf.parser.ParserException;
import hr.fer.zemris.bf.qmc.Minimizer;
import hr.fer.zemris.bf.qmc.MinimizerParser;


/**
 * Class offers user to input a boolean function which can be defined as:
 * f(A,B) = {expression} | {expression}
 * Expression can be given as function defined with variables and operators or as
 * a set of minterms/do not cares -> for example - [0, 1, 2].
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class QMC {

	/** Quit keyword. */
	private static final String QUIT = "quit";
	/** Message if something bad occurs. */
	private static final String MESSAGE = "Pogreška: funkcija nije ispravno zadana.";
	
	/**
	 * Method which is run when program starts.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in), StandardCharsets.UTF_8));
		
		while (true) {
			try {
				
				System.out.print("> ");
				String input = reader.readLine().trim();
				
				if (input.equals(QUIT)) {
					return;
				}
				
				MinimizerParser.parseUserInput(input);
				
				List<String> variables = MinimizerParser.getVariables();
				List<Set<Integer>> indexes = MinimizerParser.getIndexes();
				
				if (indexes.size() != 2) {
					throw new IllegalArgumentException(MESSAGE);
				}
				
				Minimizer minimizer = new Minimizer(indexes.get(0), indexes.get(1), variables);
				
				List<String> minimalForms = minimizer.getMinimalFormsAsString();
				
				for (int i = 0, size = minimalForms.size(); i < size; i++) {
					System.out.println(i + 1 + ". " + minimalForms.get(i));
				}
				
			} catch (IOException e) {
				System.out.println("Greška pri čitanju sa konzole.");
			} catch (NumberFormatException e) {
				System.out.println("Pogrešno zadan broj.");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (ParserException e) {
				System.out.println(e.getMessage());
			} catch (IllegalStateException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
