package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program draws 3D scene by method of ray-tracing.
 * Program is one threaded and pretty slow.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class RayCaster {

	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
				new Point3D(10, 0, 0),
				new Point3D(0, 0, 0),
				new Point3D(0, 0, 10),
				20, 20);
	}
	
	/**
	 * Getter for {@link IRayTracerProducer}.
	 * It returns an object which is able to populate data which can be then
	 * used for drawing a scene.
	 * 
	 * @return new {@link IRayTracerProducer}
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width,
					int height, long requestNo, IRayTracerResultObserver observer) {
				
				System.out.println("Započinjem izračune...");
				
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				
				Point3D OGNormalized = eye.difference(view, eye).normalize();
				Point3D VUVNormalized = viewUp.normalize();
				
				Point3D j_ = VUVNormalized.sub(OGNormalized.scalarMultiply(OGNormalized.scalarProduct(VUVNormalized)));
				Point3D j = j_.normalize();
				
				Point3D i_ = OGNormalized.vectorProduct(j);
				Point3D i = i_.normalize();
				
				Point3D screenCorner = view.sub(i.scalarMultiply(horizontal / 2)).add(j.scalarMultiply(vertical / 2));
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				int offset = 0;
				
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(i.scalarMultiply(((double) x) / (width - 1) * horizontal)).sub(j.scalarMultiply((((double) y) / (height - 1) * vertical)));
						
						Ray ray = Ray.fromPoints(eye.copy(), screenPoint.copy());
						
						Util.tracer(scene, ray, rgb);
						
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						
						offset++;
					}
				}
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
			}
		};
	}
}
