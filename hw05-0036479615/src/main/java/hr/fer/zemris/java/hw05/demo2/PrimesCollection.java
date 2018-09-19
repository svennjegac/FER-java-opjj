package hr.fer.zemris.java.hw05.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class representing collection of prime numbers.
 * Class accepts number in constructor which determines
 * how many prime numbers will be represented by collection.
 * Only usage of class is to use it in combination with iterator.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class PrimesCollection implements Iterable<Integer> {
	
	/** How many prime numbers this collection represents. */
	private int counter;

	/**
	 * Constructor accepts number which determines how many
	 * prime numbers instance of class represents.
	 * 
	 * @param counter number of prime numbers
	 */
	public PrimesCollection(int counter) {
		if (counter < 1 || counter > 105_097_565) {
			throw new IllegalArgumentException("Number of primes can not be less than 1; was: " + counter);
		}
		
		this.counter = counter;
	}
	
	/**
	 * Method checks if number is prime number.
	 * 
	 * @param number tested number
	 * @return <code>true</code> if number is prime, <code>false</code>
	 * 			otherwise
	 */
	private boolean isPrimeNumber(int number) {
		if (number < 2) {
			return false;
		}
		
		if (number == 2) {
			return true;
		}
		
		if (number % 2 == 0) {
			return false;
		}
		
		for (int i = 3, bound = number / 2; i <= bound; i += 2) {
			if (number % i == 0) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimeIterator();
	}
	
	/**
	 * Iterator over PrimesCollection class.
	 * In every iteration it returns next prime number.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private class PrimeIterator implements Iterator<Integer> {
		
		/** Counts how many primes can it output. */
		private int iteratorCounter;
		/** Current prime number. */
		private int current;
		
		/**
		 * Default constructor.
		 */
		public PrimeIterator() {
			iteratorCounter = counter;
			current = 1;
		}

		/**
		 * Checks if there is more prime numbers.
		 */
		@Override
		public boolean hasNext() {
			return iteratorCounter > 0;
		}

		/**
		 * Returns next prime number, if it is available.
		 */
		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException("There is no more elements.");
			}
			
			iteratorCounter--;
			
			return getNextPrime();
		}
		
		/**
		 * Calculates next prime number.
		 * 
		 * @return next prime number
		 */
		private int getNextPrime() {
			while (!isPrimeNumber(++current));
			return current;
		}
	}
}
