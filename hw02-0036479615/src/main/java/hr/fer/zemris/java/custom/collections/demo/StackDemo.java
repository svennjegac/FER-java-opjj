package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * StackDemo program demonstrates usage of ObjectStack. Program accepts one
 * argument from command line which must be in "". Argument should represent an
 * expression in postfix notation separated by spaces. Program returns result of
 * expression or appropriate message.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class StackDemo {

	/**
	 * Method which is run when program starts.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Pogrešan broj argumenata naredbenog retka.");
			System.exit(1);
		}

		ObjectStack stack = new ObjectStack();
		String[] array = args[0].split(" ");

		for (int i = 0; i < array.length; i++) {
			try {
				stack.push(Integer.parseInt(array[i]));
				continue;
			} catch (NumberFormatException ex) {
			}

			try {
				String input = array[i];
				int b = (int) stack.pop();
				int a = (int) stack.pop();
				int c;

				switch (input) {
				case "+":
					c = a + b;
					break;
				case "-":
					c = a - b;
					break;
				case "*":
					c = a * b;
					break;

				case "/":
					if (b == 0) {
						throw new IllegalArgumentException("Division by zero./");
					}
					c = a / b;
					break;

				case "%":
					if (b == 0) {
						throw new IllegalArgumentException("Division by zero.%");
					}
					c = a % b;
					break;

				default:
					throw new IllegalArgumentException("Unknown sign.");
				}

				stack.push(c);
			} catch (IllegalArgumentException ex) {
				if (ex.getMessage().equals("Division by zero.%")) {
					System.out.println("Pokušaj dijeljena s nulom koristeći znak '%'.");
				}

				if (ex.getMessage().equals("Division by zero./")) {
					System.out.println("Pokušaj dijeljena s nulom koristeći znak '/'.");
				}

				if (ex.getMessage().equals("Unknown sign.")) {
					System.out.println("Izraz sadrži nedozvoljene znakove.");
				}

				System.exit(1);
			} catch (EmptyStackException ex) {
				if (ex.getMessage().equals("Empty stack.")) {
					System.out.println("Izraz nije dobro definiran. Nedovoljan broj argumenata na stogu.");
				}

				System.exit(1);
			}
		}

		if (stack.size() != 1) {
			System.out.println("Broj argumenata na stogu različit od 1.");
			System.exit(1);
		}

		System.out.println("Expression evaluates to " + stack.pop() + ".");
	}
}
