package hr.fer.zemris.java.raytracer.model;

/**
 * This class represents a sphere object.
 * It has its location, radius and surface parameters for
 * light output.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Sphere extends GraphicalObject {

	/** Sphere location. */
	Point3D center;
	/** Radius of sphere. */
	double radius;
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
	
	/**
	 * 
	 * @param center sphere location
	 * @param radius sphere radius
	 * @param kdr diffuse red light component
	 * @param kdg diffuse green light component
	 * @param kdb diffuse blue light component
	 * @param krr reflective red light component
	 * @param krg reflective green light component
	 * @param krb reflective blue light component
	 * @param krn reflective n parameter
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {	
		double a = 1.0;
		double b = 2.0 * ray.direction.scalarProduct(ray.start.sub(center));
		double c = Math.pow(ray.start.sub(center).norm(), 2) - Math.pow(radius, 2);
		
		double discriminant = b * b - 4.0 * a * c;
		
		//ray does not intersect with sphere
		if (discriminant < 0) {
			return null;
		}
		
		//ray is touching sphere
		if (discriminant < 0.00000001) {
			double t = -b / (2.0 * a);
			
			Point3D touch = ray.start.add(ray.direction.scalarMultiply(t));
			return new RayIntersectionConcrete(touch, touch.sub(ray.start).norm(), true, kdr, kdg, kdb, krr, krg, krb, krn, touch.sub(center).normalize());
		}
		
		//ray have 2 intersections
		double t0 = (-b + Math.sqrt(discriminant)) / (2.0 * a);
		double t1 = (-b - Math.sqrt(discriminant)) / (2.0 * a);
		
		//sphere is behind ray source
		if (t0 < 0 && t1 < 0) {
			return null;
		}
		
		//ray source is in sphere
		if (t0 < 0 || t1 < 0) {
			Point3D innerIntersection = ray.start.add(ray.direction.scalarMultiply(t0 > 0 ? t0 : t1));
			
			return new RayIntersectionConcrete(innerIntersection, innerIntersection.sub(ray.start).norm(), false, kdr, kdg, kdb, krr, krg, krb, krn, innerIntersection.sub(center).normalize());
		}
		
		//sphere is in front of ray source
		Point3D outerIntersection = ray.start.add(ray.direction.scalarMultiply(t0 < t1 ? t0 : t1));
		return new RayIntersectionConcrete(outerIntersection, outerIntersection.sub(ray.start).norm(), true, kdr, kdg, kdb, krr, krg, krb, krn, outerIntersection.sub(center).normalize());
		
	}

}
