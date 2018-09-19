package hr.fer.zemris.java.fractals;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Implementation of Newton-Raphson iteration and fractal drawing.
 * This implementation is multiple threaded and it is pretty fast.
 * 
 * Program has implemented interaction with user.
 * User is asked to enter roots of complex polynomial in a
 * shape of a + ib. User must enter at least 2 roots.
 * Program interaction ends when {@link #DONE} is entered.
 * After few seconds fractal drawing will appear.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Newton {

	/** Sign for end of user interaction. */
	private static final String DONE = "done";
	
	/**
	 * Main method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in), StandardCharsets.UTF_8));
		
		int index = 1;
		List<Complex> roots = new ArrayList<>();
		
		while (true) {
			System.out.print("Root " + index + "> ");
			
			String input;
			
			try {
				input = reader.readLine().trim();
			} catch (IOException e) {
				System.out.println("Error while readnig");
				return;
			}
			
			if (input.equals(DONE)) {
				break;
			}
			
			Complex root = Util.parseUserInput(input);
			
			if (root == null) {
				System.out.println("Your input was invalid.");
				continue;
			}
			
			roots.add(root);
			index++;
		}
		
		if (index < 3) {
			System.out.println("You didnt entered at least two roots. Goodbye.");
			return;
		}
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		FractalViewer.show(new NewtonProducer(roots));
	}
	
	/**
	 * Producer of data for drawing fractals.
	 * Its method produce accepts several parameters and populates data and then
	 * calls observer to draw an image.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class NewtonProducer implements IFractalProducer {

		/** Thread pool. */
		ExecutorService pool;
		/** Roots of polynomial. */
		List<Complex> roots;
		
		/** Number of jobs for threads. */
		private static final int NUMBER_OF_JOBS = 8 * Runtime.getRuntime().availableProcessors();
		
		/**
		 * Constructor of fractal data producer.
		 * It accepts roots and using that roots constructs polynomial
		 * and then draws its fractals.
		 * 
		 * @param roots roots of polynomial
		 */
		public NewtonProducer(List<Complex> roots) {
			this.roots = roots;
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new DaemonicThreadFactory());
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {
			
			short[] data = new short[height * width];
			
			ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(roots.toArray(new Complex[roots.size()]));
			ComplexPolynomial polynomial = rootedPolynomial.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();
			
			List<Future<Void>> results = new ArrayList<>();
			
			for (int i = 0; i < NUMBER_OF_JOBS; i++) {
				
				int yStart = height / NUMBER_OF_JOBS * i;
				int yStop = height / NUMBER_OF_JOBS * (i + 1);
				
				if (i == NUMBER_OF_JOBS - 1) {
					yStop = height - 1;
				}
				
				results.add(pool.submit(
						new PratialImageCalculation(
								reMin, reMax, imMin, imMax, width, height,
								yStart, yStop,
								data, rootedPolynomial, polynomial, derived
				)));
			}
			
			for (Future<Void> future : results) {
				try {
					future.get();
				} catch (Exception ignorable) {
				}
			}
			
			observer.acceptResult(data, (short) (rootedPolynomial.toComplexPolynom().order() + 1), requestNo);
		}
	}
	
	/**
	 * Thread factory which makes threads with
	 * daemon flag set to true.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class DaemonicThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;
		}
	}
	
	/**
	 * This class utilizes job for one thread which job is to populate part
	 * of {@link #data} so that drawing can be done.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class PratialImageCalculation implements Callable<Void> {
		
		/** Minimum for real part. */
		double reMin;
		/** Maximum for real part. */
		double reMax;
		/** Minimum for imaginary part. */
		double imMin;
		/** Maximum for imaginary part. */
		double imMax;
		/** Screen width. */
		int width;
		/** Screen height. */
		int height;
		/** Point of height start. */
		int yStart;
		/** Point of height stop. */
		int yStop;
		/** Complex polynomial in rooted shape. */
		ComplexRootedPolynomial rootedPolynomial;
		/** Complex polynomial in standard shape. */
		ComplexPolynomial polynomial;
		/** Derived complex polynomial. */
		ComplexPolynomial derived;
		/** Data which needs to be populated. */
		short[] data;
		
		/**
		 * Constructor for job of one thread.
		 * 
		 * @param reMin minimum for real part
		 * @param reMax maximum for real part
		 * @param imMin minimum for imaginary part
		 * @param imMax maximum for imaginary part
		 * @param width screen width
		 * @param height screen height
		 * @param yStart start point of height
		 * @param yStop stop point of height
		 * @param data data to be populated
		 * @param rootedPolynomial polynomial in rooted shape
		 * @param polynomial polynomial in standard shape
		 * @param derived derived polynomial
		 */
		public PratialImageCalculation(double reMin, double reMax, double imMin, double imMax, int width, int height,
				int yStart, int yStop, short[] data, ComplexRootedPolynomial rootedPolynomial, ComplexPolynomial polynomial, ComplexPolynomial derived) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yStart = yStart;
			this.yStop = yStop;
			this.data = data;
			this.rootedPolynomial = rootedPolynomial;
			this.polynomial = polynomial;
			this.derived = derived;
		}

		@Override
		public Void call() throws Exception {
			Util.newton(reMin, reMax, imMin, imMax, width, height, yStart, yStop, data, rootedPolynomial, polynomial, derived);
			return null;
		}
	}
}

