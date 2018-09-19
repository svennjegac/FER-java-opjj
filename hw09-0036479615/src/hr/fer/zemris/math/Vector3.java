package hr.fer.zemris.math;

/**
 * Class representing a single 3D vector and offers various
 * number of operation on vectors such as: scalar product, cross product,
 * addition, subtraction...
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Vector3 {
	
	/** Vectors x component. */
	private double x;
	/** Vectors y component. */
	private double y;
	/** Vectors z component. */
	private double z;
	
	/**
	 * Constructs vector initialized with x, y and z components.
	 * 
	 * @param x x component
	 * @param y y component
	 * @param z z component
	 */
	public Vector3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Calculates vectors norm.
	 * 
	 * @return vectors norm.
	 */
	public double norm() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
	
	/**
	 * Returns new normalized vector.
	 * 
	 * @return new normalized vector
	 */
	public Vector3 normalized() {
		double norm = norm();
		
		if (norm == 0.0) {
			throw new IllegalArgumentException("Can not normalize vector with norm 0; was: " + norm);
		}
		
		return new Vector3(x / norm, y / norm, z / norm);
	}
	
	/**
	 * Returns new vector v = this + other.
	 * 
	 * @param other parameter in addition
	 * @return new vector
	 */
	public Vector3 add(Vector3 other) {
		validate(other);
		
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}
	
	/**
	 * Subtracts this vector and other and returns result as a
	 * new vector.
	 * 
	 * @param other parameter in subtraction
	 * @return new vector
	 */
	public Vector3 sub(Vector3 other) {
		validate(other);
		
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}
	
	/**
	 * Calculates dot product of this and other vector.
	 * 
	 * @param other parameter in dot product
	 * @return dot product
	 */
	public double dot(Vector3 other) {
		validate(other);
		
		return x * other.x + y * other.y + z * other.z;
	}
	
	/**
	 * Calculates cross product of this and other vector.
	 * 
	 * @param other parameter in cross product
	 * @return cross product
	 */
	public Vector3 cross(Vector3 other) {
		validate(other);
		
		double i = y * other.z - z * other.y;
		double j = z * other.x - x * other.z;
		double k = x * other.y - y * other.x;
		
		return new Vector3(i, j, k);
	}
	
	/**
	 * Multiplies every component of vector with scale.
	 * 
	 * @param s scale
	 * @return new vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(s * x, s * y, s * z);
	}
	
	/**
	 * Calculates cosine angle of two vectors.
	 * 
	 * @param other parameter vector
	 * @return cosine angle between two vectors
	 */
	public double cosAngle(Vector3 other) {
		validate(other);
		
		if (this.norm() * other.norm() == 0.0) {
			throw new IllegalArgumentException("Can not calculate angle with vecotrs of norm 0; were: "
					+ this.norm() + ", " + other.norm());
		}
		
		return this.dot(other) / (this.norm() * other.norm());
	}

	/**
	 * Getter for x component.
	 * 
	 * @return x component
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Getter for y component.
	 * 
	 * @return y component
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Getter for z component.
	 * 
	 * @return z component
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Returns array of vectors components.
	 * 
	 * @return array of vector components
	 */
	public double[] toArray() {
		return new double[]{x, y, z};
	}
	
	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}
	
	/**
	 * Checks if parameter is null and throws exception if
	 * it is.
	 * 
	 * @param other vector to be checked
	 */
	private void validate(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Other vector can not be null.");
		}
	}
}
