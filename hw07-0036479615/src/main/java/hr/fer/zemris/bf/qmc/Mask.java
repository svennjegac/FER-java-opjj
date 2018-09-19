package hr.fer.zemris.bf.qmc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.bf.utils.Util;

/**
 * Class representing a single mask in Quine-McCluskey method of minimization
 * of boolean functions.
 * It represents values of variables of function giving us information
 * about their state (true, false or do not care).
 * It also stores set of minterms which are covered by mask and flags if
 * mask is do not care or combined.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Mask {

	/** Value of do not care. */
	private static final byte DONT_CARE = (byte) 2;
	
	/** Mask representing variables and their boolean value. */
	private byte[] mask;
	/** Minterms covered by mask. */
	private Set<Integer> indexes;
	/** Signal if mask is do not care. */
	private boolean dontCare;
	/** Signal if mask is combined with other mask or not. */
	private boolean combined;
	
	/** Mask hash code. */
	private int hashCode;
	
	/**
	 * Constructor which accepts index of minterm, number of variables and
	 * do not care flag and creates mask.
	 * 
	 * @param index index of minterm
	 * @param numberOfVariables number of variables of function
	 * @param dontCare do not care flag
	 */
	public Mask(int index, int numberOfVariables, boolean dontCare) {
		if (index < 0 || numberOfVariables < 1 || index >= Math.pow(2, numberOfVariables)) {
			throw new IllegalArgumentException("Forbidden combination of index and number of variables; "
					+ "were: " + index + ", " + numberOfVariables);
		}
		
		
		
		mask = Util.indexToByteArray(index, numberOfVariables);
		hashCode = Arrays.hashCode(mask);
		
		indexes = new TreeSet<>();
		indexes.add(index);
		indexes = Collections.unmodifiableSet(indexes);
		
		this.dontCare = dontCare;
		combined = false;
	}
	
	/**
	 * Constructor which constructs mask from mask values, set of minterms covered by mask values
	 * and do not care flag.
	 * 
	 * @param values mask values
	 * @param indexes minterms covered by mask
	 * @param dontCare do not care flag
	 */
	public Mask(byte[] values, Set<Integer> indexes, boolean dontCare) {
		if (values == null || indexes == null) {
			throw new IllegalArgumentException("Values and indexes can not be null.");
		}
		
		if (indexes.isEmpty()) {
			throw new IllegalArgumentException("Indexes can not be empty.");
		}
		
		mask = Arrays.copyOf(values, values.length);
		hashCode = Arrays.hashCode(mask);
		
		this.indexes = new TreeSet<>();
		copySet(this.indexes, indexes);
		
		this.indexes = Collections.unmodifiableSet(this.indexes);
		
		this.dontCare = dontCare;
	}
	
	/**
	 * Method combines mask with other mask and returns new mask if they can be
	 * combined or nothing.
	 * 
	 * @param other other mask
	 * @return new mask or nothing
	 */
	public Optional<Mask> combineWith(Mask other) {
		if (other == null || other.mask.length != this.mask.length) {
			throw new IllegalArgumentException("Other mask can not be compared to this mask.");
		}
		
		if (!isCombineable(this.mask, other.mask)) {
			return Optional.empty();
		}
		
		return Optional.of(combine(other));
	}
	
	/**
	 * Method combines two masks.
	 * 
	 * @param other other mask
	 * @return new mask
	 */
	private Mask combine(Mask other) {
		byte[] newMask = new byte[mask.length];
		
		for (int i = 0; i < mask.length; i++) {
			if (mask[i] == other.mask[i]) {
				newMask[i] = mask[i];
				continue;
			}
			
			newMask[i] = DONT_CARE;
		}
		
		Set<Integer> newIndexes = new TreeSet<>();
		copySet(newIndexes, indexes);
		copySet(newIndexes, other.indexes);
		
		boolean newDontCare = (dontCare == true && other.dontCare == true) ? true : false;
		
		return new Mask(newMask, newIndexes, newDontCare);
	}
	
	/**
	 * Method checks if two masks can be combined.
	 * 
	 * @param array1 first mask values
	 * @param array2 second mask values
	 * @return <code>true</code> if masks can be combined, <code>false</code> otherwise
	 */
	private boolean isCombineable(byte[] array1, byte[] array2) {
		int differences = 0;
		
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != array2[i]) {
				if (array1[i] == DONT_CARE || array2[i] == DONT_CARE) {
					return false;
				}
				
				differences++;
			}
		}
		
		return differences <= 1;
	}
	
	/**
	 * Method counts number of '1' in mask values.
	 * 
	 * @return number of '1' in mask values
	 */
	public int countOfOnes() {
		int count = 0;
		
		for (int i = 0; i < mask.length; i++) {
			if (mask[i] == (byte) 1) {
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * Sets combined value.
	 * 
	 * @param combined new combined value
	 */
	public void setCombined(boolean combined) {
		this.combined = combined;
	}
	
	/**
	 * Gets combined value.
	 * 
	 * @return combined value
	 */
	public boolean isCombined() {
		return combined;
	}
	
	/**
	 * Gets do not care value.
	 * 
	 * @return do not care value
	 */
	public boolean isDontCare() {
		return dontCare;
	}
	
	/**
	 * Gets minterm indexes.
	 * 
	 * @return minterm indexes
	 */
	public Set<Integer> getIndexes() {
		return indexes;
	}
	
	/**
	 * Gets mask length.
	 * 
	 * @return mask length
	 */
	public int size() {
		return mask.length;
	}
	
	/**
	 * Gets value of mask at given position.
	 * 
	 * @param position position of value in mask
	 * @return value of mask on given position
	 */
	public byte getValueAt(int position) {
		if (position < 0 || position > mask.length - 1) {
			throw new IndexOutOfBoundsException("Position out of bounds; was: " + position);
		}
		
		return mask[position];
	}
	
	/**
	 * Copies integer set elements to new set elements.
	 * 
	 * @param destination destination set
	 * @param source source set
	 */
	private void copySet(Set<Integer> destination, Set<Integer> source) {
		source.forEach(index -> {
			destination.add(new Integer(index.intValue()));
		});
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Mask)) {
			return false;
		}
		
		Mask that = (Mask) obj;
		
		if (that.hashCode() != this.hashCode) {
			return false;
		}
		
		if (!Arrays.equals(that.mask, this.mask)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return hashCode;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < mask.length; i++) {
			sb.append(mask[i] == DONT_CARE ? "-" : mask[i]);
		}
		
		sb.append(" " + (isDontCare() ? "D" : ".") + " ");
		sb.append((isCombined() ? "*" : " ") + " ");
		
		sb.append("[");
		
		boolean first = true;
		for (Integer integer : indexes) {
			if (first) {
				first = false;
				sb.append(integer);
				continue;
			}
			sb.append(", " + integer);
		}
		
		sb.append("]");
		
		return sb.toString();
	}
}
