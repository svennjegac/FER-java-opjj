package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Implementation of Newton-Raphson iteration and fractal drawing.
 * It is an example of slow, one threaded implementation.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class NewtonSlow {

	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		FractalViewer.show(new MyProducer());
	}
	
	/**
	 * Class which produces data for fractal drawing.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private static class MyProducer implements IFractalProducer {

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {
		
			short[] data = new short[height * width];
			
			ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
			ComplexPolynomial polynomial = rootedPolynomial.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();
			
			Util.newton(reMin, reMax, imMin, imMax, width, height, 0, height, data, rootedPolynomial, polynomial, derived);
			
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}
	}
}
