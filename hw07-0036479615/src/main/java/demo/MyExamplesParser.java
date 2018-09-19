package demo;

import hr.fer.zemris.bf.parser.Parser;
import hr.fer.zemris.bf.parser.ParserException;
import hr.fer.zemris.bf.utils.ExpressionTreePrinter;

public class MyExamplesParser {

	public static void main(String[] args) {
		String[] expressions = new String[] {
				"a and b or c",
				"((!(!a) and ((!b) or g)) and c or !d xor (e *!d))",
				"a and b and !",
				"a and !",
				"b and or",
				"a and (b or c) xor d",
				"(((a)) and ((b or c) xor d))"
		};
			
		for(String expr : expressions) {
			System.out.println("==================================");
			System.out.println("Izraz: " + expr);
			System.out.println("==================================");
			
			try {
				System.out.println("Stablo:");
				Parser parser = new Parser(expr);
				parser.getExpression().accept(new ExpressionTreePrinter());
			} catch(ParserException ex) {
				System.out.println("Iznimka: " + ex.getClass()+" - " + ex.getMessage());
			}
			System.out.println();
		}
	}
}
