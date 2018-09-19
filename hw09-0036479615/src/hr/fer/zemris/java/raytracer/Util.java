package hr.fer.zemris.java.raytracer;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * Utility class which offers tracing method {@link #tracer(Scene, Ray, short[])} for
 * each ray.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Util {
	
	/**
	 * Method takes ray of light and scene. Taking scene in calculations, it calculates
	 * intensity of red, green and blue light which observer sees for that ray.
	 * Its values are returned in rgb[] array.
	 * 
	 * @param scene scene
	 * @param ray ray
	 * @param rgb rgb which stores result
	 */
	public static void tracer(Scene scene, Ray ray, short[] rgb) {
		RayIntersection firstIntersection = Util.findFirstIntersection(scene, ray);
		
		if (firstIntersection == null || !firstIntersection.isOuter()) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
			
			return;
		}
		
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;
		
		List<LightSource> lightSources = scene.getLights();
		
		for (LightSource lightSource : lightSources) {
			Ray rayFromLightSourceToIntersection = Ray.fromPoints(lightSource.getPoint(), firstIntersection.getPoint());
		
			RayIntersection firstIntersectionFromLightToObject = Util.findFirstIntersection(scene, rayFromLightSourceToIntersection);
			
			if (firstIntersectionFromLightToObject == null || !firstIntersectionFromLightToObject.isOuter()) {
				continue;
			}
			
			if (firstIntersection.getPoint().sub(firstIntersectionFromLightToObject.getPoint()).norm() > 0.1) {
				continue;
			}
			
			addDiffuseLightComponent(lightSource, firstIntersection, rgb);
			addReflectiveLightComponent(lightSource, firstIntersection, rgb, ray);
		}
	}
	
	/**
	 * Method finds and returns first intersection of provided ray and objects in
	 * scene.
	 * If there is no intersections, null will be returned.
	 * 
	 * @param scene scene
	 * @param ray ray
	 * @return first intersection
	 */
	private static RayIntersection findFirstIntersection(Scene scene, Ray ray) {
		List<GraphicalObject> sceneGraphicalObjects = scene.getObjects();
		List<RayIntersection> intersections = new ArrayList<>();
		
		//find intersections with all objects
		sceneGraphicalObjects.forEach(object -> {
			RayIntersection intersection = object.findClosestRayIntersection(ray);
			
			if (intersection != null) {
				intersections.add(intersection);
			}
		});
		
		//null if there is no intersections
		if (intersections.isEmpty()) {
			return null;
		}
		
		//find first intersection
		double minDistance = intersections.get(0).getDistance();
		RayIntersection firstIntersection = intersections.get(0);
		
		for (RayIntersection rayIntersection : intersections) {
			if (rayIntersection.getDistance() < minDistance) {
				minDistance = rayIntersection.getDistance();
				firstIntersection = rayIntersection;
			}
		}
		
		return firstIntersection;
	}
	
	/**
	 * Method adds to rgb[] diffuse values from light taking in mind parameters of
	 * surface of intersection.
	 * 
	 * @param lightSource light source
	 * @param rayIntersection intersection
	 * @param rgb stored result
	 */
	private static void addDiffuseLightComponent(LightSource lightSource, RayIntersection rayIntersection, short[] rgb) {
		Point3D intersectionToLightSource = lightSource.getPoint().sub(rayIntersection.getPoint()).normalize();
		double angleCoeficient = intersectionToLightSource.scalarProduct(rayIntersection.getNormal());
		
		rgb[0] += lightSource.getR() * rayIntersection.getKdr() * angleCoeficient;
		rgb[1] += lightSource.getG() * rayIntersection.getKdg() * angleCoeficient;
		rgb[2] += lightSource.getB() * rayIntersection.getKdb() * angleCoeficient;
	}
	
	/**
	 * Method adds to rgb[] reflective values from light taking in mind parameters of
	 * surface of intersection.
	 * 
	 * @param lightSource light source
	 * @param rayIntersection intersection
	 * @param rgb stored result
	 * @param ray ray
	 */
	private static void addReflectiveLightComponent(LightSource lightSource, RayIntersection rayIntersection, short[] rgb, Ray ray) {
		Point3D intersectionToLightSource = lightSource.getPoint().sub(rayIntersection.getPoint());
		Point3D intersectionToLightSourceProjectionOnNormal = rayIntersection.getNormal().scalarMultiply(intersectionToLightSource.scalarProduct(rayIntersection.getNormal()));
		Point3D reflectedLightNormalized = intersectionToLightSourceProjectionOnNormal.add(intersectionToLightSourceProjectionOnNormal).sub(intersectionToLightSource).normalize();
		
		Point3D vectorToEye = ray.direction.negate();
		
		double reflectiveCoeficient = Math.pow(reflectedLightNormalized.scalarProduct(vectorToEye), rayIntersection.getKrn());
		
		rgb[0] += lightSource.getR() * rayIntersection.getKrr() * reflectiveCoeficient;
		rgb[1] += lightSource.getG() * rayIntersection.getKrg() * reflectiveCoeficient;
		rgb[2] += lightSource.getB() * rayIntersection.getKrb() * reflectiveCoeficient;
	}
}
