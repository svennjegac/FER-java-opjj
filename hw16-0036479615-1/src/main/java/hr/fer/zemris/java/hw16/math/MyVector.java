package hr.fer.zemris.java.hw16.math;

/**
 * Class representing a single vector of variable number of components.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class MyVector {
	
	/** Vector components. */
	private double[] data;
	
	/**
	 * Constructor accepting data.
	 * 
	 * @param data representation of vector components
	 */
	public MyVector(double[] data) {
		this.data = data;
	}
	
	/**
	 * Gets vector component at position i.
	 * 
	 * @param i position
	 * @return vector component on position i
	 */
	public double getComponentValue(int i) {
		return data[i];
	}
	
	/**
	 * Calculates vectors norm.
	 * 
	 * @return vectors norm.
	 */
	public double norm() {
		double norm = 0;
		
		for (int i = 0; i < data.length; i++) {
			norm += Math.pow(data[i], 2);
		}
		
		return Math.sqrt(norm);
	}

	/**
	 * Calculates vectors dot product.
	 * 
	 * @param other other vector
	 * @return dot product of this and parameter vector
	 */
	public double dot(MyVector other) {
		double dot = 0;
		
		for (int i = 0; i < data.length; i++) {
			dot += data[i] * other.data[i];
		}
		
		return dot;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < data.length; i++) {
			sb.append(data[i] + ", ");
		}
		
		return sb.toString();
	}
}
