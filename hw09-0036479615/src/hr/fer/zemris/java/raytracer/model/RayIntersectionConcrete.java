package hr.fer.zemris.java.raytracer.model;

/**
 * Concrete implementation of ray intersection.
 * For details you can see {@link RayIntersection}.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class RayIntersectionConcrete extends RayIntersection {

	/** Diffuse red light component. */
	double kdr;
	/** Diffuse green light component. */
	double kdg;
	/** Diffuse blue light component. */
	double kdb;
	/** Reflective red light component. */
	double krr;
	/** Reflective green light component. */
	double krg;
	/** Reflective blue light component. */
	double krb;
	/** Reflective n parameter. */
	double krn;
	/** Normal vector on object in dot of intersection. */
	Point3D normal;
	
	/**
	 * 
	 * @param point point of intersection
	 * @param distance distance to ray origin
	 * @param outer is intersection inner or outer - flag
	 * @param kdr diffuse red light component
	 * @param kdg diffuse green light component
	 * @param kdb diffuse blue light component
	 * @param krr reflective red light component
	 * @param krg reflective green light component
	 * @param krb reflective blue light component
	 * @param krn reflective n parameter
	 * @param normal normal to object in point of intersection
	 */
	public RayIntersectionConcrete(Point3D point, double distance, boolean outer, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn, Point3D normal) {
		super(point, distance, outer);
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
		this.normal = normal;
	}

	@Override
	public Point3D getNormal() {
		return normal;
	}

	@Override
	public double getKdr() {
		return kdr;
	}

	@Override
	public double getKdg() {
		return kdg;
	}

	@Override
	public double getKdb() {
		return kdb;
	}

	@Override
	public double getKrr() {
		return krr;
	}

	@Override
	public double getKrg() {
		return krg;
	}

	@Override
	public double getKrb() {
		return krb;
	}

	@Override
	public double getKrn() {
		return krn;
	}	
}
