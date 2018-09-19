package hr.fer.zemris.java.hw05.observer2;

/**
 * Example of Observer pattern usage.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class MyExample {

	/**
	 * Method called when program is run.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		IntegerStorage s = new IntegerStorage(-5);
		
		IntegerStorageObserver counter = new ChangeCounter();
		
		s.addObserver(counter);
		
		s.setValue(3); //1
		
		s.addObserver(counter);
		s.addObserver(counter);
		
		s.setValue(4); //2
		
		s.addObserver(counter);
		s.removeObserver(counter);
		
		s.setValue(4);
		s.setValue(4);
		
		SquareValue sq = new SquareValue();
		
		s.addObserver(sq);
		s.addObserver(sq);
		
		s.setValue(4); //nis - isto ko prije
		s.setValue(4); //nis

		s.addObserver(sq);
		s.setValue(3); //9
		
		s.addObserver(sq);
		s.removeObserver(sq);
		s.removeObserver(sq);
		
		s.setValue(7); //nis
		
		s.addObserver(sq);
		s.removeObserver(sq);
		
		s.setValue(8); //nis
		
		s.addObserver(sq);
		s.removeObserver(sq);
		s.addObserver(sq);
		
		s.setValue(9); //81
		
		s.removeObserver(sq);
		s.setValue(1);
		
		IntegerStorageObserver d = new DoubleValue(3);
		
		s.addObserver(d);
		s.setValue(9); //18
		s.removeObserver(d);
		s.setValue(5);
		s.removeObserver(d);
		
		IntegerStorageObserver d2 = new DoubleValue(3);
		
		s.setValue(4);
		s.addObserver(d2);
		s.setValue(1); //2
		s.setValue(4); //8
		s.setValue(5); //10
		s.setValue(6);
		s.setValue(7);
		
		s.removeObserver(d2);
		s.removeObserver(d);
		
		s.setValue(1);
		
		IntegerStorageObserver d3 = new DoubleValue(3);
		IntegerStorageObserver d4 = new DoubleValue(3);
		
		s.addObserver(d3);
		s.addObserver(d4);
		
		s.setValue(2); // 4 4
		s.setValue(3); // 6 6
		s.setValue(4); // 8 8
		s.setValue(5);
		s.setValue(6);
		
		s.addObserver(new ChangeCounter());
		s.clearObservers();
		s.setValue(3);
		
		IntegerStorageObserver ch = new ChangeCounter();
		
		s.addObserver(ch);
		s.removeObserver(ch);
		s.clearObservers();
		s.setValue(10);
	
		IntegerStorageObserver ch2 = new ChangeCounter();
		s.addObserver(ch2);
		s.setValue(19); //1
		s.setValue(20); //2
		s.clearObservers();
		s.setValue(1);
	
		IntegerStorageObserver ch3 = new ChangeCounter();
		s.removeObserver(ch3);
		s.clearObservers();
		s.addObserver(ch3);
		s.setValue(4); //1
	}
}
