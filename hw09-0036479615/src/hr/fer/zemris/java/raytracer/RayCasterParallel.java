package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program draws 3D scene by method of ray-tracing.
 * Implementation of this program is multiple threaded so this
 * program is pretty fast.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class RayCasterParallel {

	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(new RayTracerParallelProducer(),
				new Point3D(10, 5, 5),
				new Point3D(0, 0, 0),
				new Point3D(0, 0, 10),
				20, 20);
	}

	/**
	 * Producer for object which duty is to make data which represents light intensity
	 * on scene. It delegates work to many threads so it is fast.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	public static class RayTracerParallelProducer implements IRayTracerProducer {

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
			
			ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
			pool.invoke(new RayTracerJob(i, j, screenCorner, eye, scene, horizontal, vertical, width, height, red, green, blue, 0, height));
			pool.shutdown();
			
			System.out.println("Izračuni gotovi...");
			observer.acceptResult(red, green, blue, requestNo);
		}
	}
	
	/**
	 * Job for each thread.
	 * Each threads duty is to paint a part of image
	 * (to populate part of data representing light intensity).
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class RayTracerJob extends RecursiveAction {

		/** Serial version UID. */
		private static final long serialVersionUID = -4142885107422758958L;

		/** Threshold which determines should this thread do the job or it should be divided. */
		private static final int THRESHOLD = 100;
		
		/** Vector i. */
		Point3D i;
		/** Vector j. */
		Point3D j;
		/** Position of screen corner. */
		Point3D screenCorner;
		/** Eye position. */
		Point3D eye;
		/** Scene. */
		Scene scene;
		
		/** Horizontal length of scene. */
		double horizontal;
		/** Vertical length of scene. */
		double vertical;
		/** Screen width. */
		int width;
		/** Screen height. */
		int height;
		
		/** Red component. */
		short[] red;
		/** Green component. */
		short[] green;
		/** Blue component. */
		short[] blue;
		
		/** Point of height start for calculation. */
		int yStart;
		/** Point of height stop for calculation. */
		int yStop;
		
		

		/**
		 * Constructor for thread job.
		 * 
		 * @param i vector i
		 * @param j vector j
		 * @param screenCorner screen corner
		 * @param eye eye position
		 * @param scene scene
		 * @param horizontal horizontal length of scene
		 * @param vertical vertical length of scene
		 * @param width screen width
		 * @param height screen height
		 * @param red red component
		 * @param green green component
		 * @param blue blue component
		 * @param yStart point of height start for calculation
		 * @param yStop point of height sop for calculation
		 */
		public RayTracerJob(Point3D i, Point3D j, Point3D screenCorner, Point3D eye, Scene scene, double horizontal,
				double vertical, int width, int height, short[] red, short[] green, short[] blue, int yStart,
				int yStop) {
			super();
			this.i = i;
			this.j = j;
			this.screenCorner = screenCorner;
			this.eye = eye;
			this.scene = scene;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.yStart = yStart;
			this.yStop = yStop;
		}

		@Override
		protected void compute() {
			if (yStop - yStart < THRESHOLD) {
				computeDirect();
				return;
			}

			invokeAll(
					new RayTracerJob(i, j, screenCorner, eye, scene, horizontal, vertical, width, height, red, green, blue, yStart, yStart + (yStop - yStart) / 2),
					new RayTracerJob(i, j, screenCorner, eye, scene, horizontal, vertical, width, height, red, green, blue, yStart + (yStop - yStart) / 2, yStop)
			);
		}
		
		/**
		 * Method computes data for given part of image.
		 */
		private void computeDirect() {
			short[] rgb = new short[3];
			
			for (int y = yStart; y < yStop; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(i.scalarMultiply(((double) x) / (width - 1) * horizontal)).sub(j.scalarMultiply((((double) y) / (height - 1) * vertical)));
					
					Ray ray = Ray.fromPoints(eye.copy(), screenPoint.copy());
					
					Util.tracer(scene, ray, rgb);
					
					int position = y * width + x;
					red[position] = rgb[0] > 255 ? 255 : rgb[0];
					green[position] = rgb[1] > 255 ? 255 : rgb[1];
					blue[position] = rgb[2] > 255 ? 255 : rgb[2];
				}
			}
		}
	}
}
