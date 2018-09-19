package hr.fer.zemris.java.hw05.observer1;

/**
 * Example of Observer design pattern usage.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ObserverExample {
	
	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		IntegerStorage istorage = new IntegerStorage(20);
		
		IntegerStorageObserver observer = new SquareValue();
		
		istorage.addObserver(observer);
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		istorage.removeObserver(observer);
		
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}