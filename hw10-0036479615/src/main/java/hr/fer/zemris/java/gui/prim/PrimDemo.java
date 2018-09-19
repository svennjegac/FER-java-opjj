package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demo program for generating prime numbers.
 * It has two lists of numbers and one button.
 * Every click on button generates next prime number on both lists.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class PrimDemo extends JFrame {

	/** Serial version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public PrimDemo() {
		setSize(new Dimension(500, 300));
		setLocation(new Point(400, 100));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}
	
	/**
	 * GUI initialization.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel primModel = new PrimListModel();
		JList<Integer> leftList = new JList<>(primModel);
		JList<Integer> rightList = new JList<>(primModel);
	
		JButton next = new JButton("Slijedeći");
		next.addActionListener(e -> {
			primModel.next();
		});
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		panel.add(new JScrollPane(leftList));
		panel.add(new JScrollPane(rightList));
		
		cp.add(panel, BorderLayout.CENTER);
		cp.add(next, BorderLayout.PAGE_END);
	}
	
	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}
}
