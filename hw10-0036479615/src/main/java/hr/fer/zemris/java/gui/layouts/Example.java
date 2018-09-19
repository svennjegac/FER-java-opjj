package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Example of layout usage.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Example extends JFrame {

	/** Serial version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public Example() {
		setTitle("Example");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		pack();
		//setSize(getPreferredSize());
	}
	
	/**
	 * Initialization of frame.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		
		JPanel p = new JPanel(new CalcLayout(5));
		
		JLabel label = new JLabel("s");
		label.setOpaque(true);
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLUE);
		label.setPreferredSize(new Dimension(50, 50));
		label.setMinimumSize(new Dimension(10, 10));
		label.setMaximumSize(new Dimension(60, 60));
		
		JLabel label7 = new JLabel("v");
		label7.setOpaque(true);
		label7.setVerticalAlignment(SwingConstants.CENTER);
		label7.setHorizontalAlignment(SwingConstants.CENTER);
		label7.setForeground(Color.WHITE);
		label7.setBackground(Color.BLUE);
		label7.setPreferredSize(new Dimension(50, 50));
		label7.setMinimumSize(new Dimension(10, 10));
		label7.setMaximumSize(new Dimension(60, 60));
		
		JLabel label1 = new JLabel("x");
		label1.setOpaque(true);
		label1.setForeground(Color.WHITE);
		label1.setBackground(Color.BLUE);
		label1.setPreferredSize(new Dimension(50, 50));
		label1.setMinimumSize(new Dimension(10, 10));
		label1.setMaximumSize(new Dimension(60, 60));
		
		JLabel label2 = new JLabel("y");
		label2.setOpaque(true);
		label2.setForeground(Color.WHITE);
		label2.setBackground(Color.BLUE);
		label2.setPreferredSize(new Dimension(50, 50));
		label2.setMinimumSize(new Dimension(10, 10));
		label2.setMaximumSize(new Dimension(60, 60));
		
		JLabel label3 = new JLabel("z");
		label3.setOpaque(true);
		label3.setForeground(Color.WHITE);
		label3.setBackground(Color.BLUE);
		label3.setPreferredSize(new Dimension(50, 50));
		label3.setMinimumSize(new Dimension(10, 10));
		label3.setMaximumSize(new Dimension(60, 60));
		
		JLabel label4 = new JLabel("w");
		label4.setOpaque(true);
		label4.setForeground(Color.WHITE);
		label4.setBackground(Color.BLUE);
		label4.setPreferredSize(new Dimension(90, 70));
		label4.setMinimumSize(new Dimension(10, 10));
		label4.setMaximumSize(new Dimension(60, 60));
		
		JLabel label5 = new JLabel("a");
		label5.setOpaque(true);
		label5.setForeground(Color.WHITE);
		label5.setBackground(Color.BLUE);
		label5.setPreferredSize(new Dimension(50, 50));
		label5.setMinimumSize(new Dimension(10, 10));
		label5.setMaximumSize(new Dimension(60, 60));
		
		JLabel label6 = new JLabel("b");
		label6.setOpaque(true);
		label6.setForeground(Color.WHITE);
		label6.setBackground(Color.BLUE);
		label6.setPreferredSize(new Dimension(50, 50));
		label6.setMinimumSize(new Dimension(10, 10));
		label6.setMaximumSize(new Dimension(60, 60));
		
		
		
		p.add(label, new RCPosition(5, 7));
		p.add(label7, new RCPosition(2, 5));
		p.add(label1, new RCPosition(1,1));
		p.add(label2, new RCPosition(2,3));
		p.add(label3, new RCPosition(2,7));
		p.add(label4, new RCPosition(4,2));
		p.add(label5, new RCPosition(4,5));
		p.add(label6,new RCPosition(4,7));
		p.remove(label);
		p.remove(label);
		p.add(label, new RCPosition(5, 7));
		p.add(label, new RCPosition(5, 7));
		
		cp.add(p, null);
	}
	
	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Example().setVisible(true);
		});
	}

}
