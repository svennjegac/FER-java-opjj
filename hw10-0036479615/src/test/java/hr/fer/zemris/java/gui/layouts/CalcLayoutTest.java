package hr.fer.zemris.java.gui.layouts;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.junit.Test;

public class CalcLayoutTest {

	@Test
	public void valid() {
		JFrame f = new JFrame("Test");
		
		f.setTitle("Example");
		
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		
		Container cp = f.getContentPane();
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		JLabel label = new JLabel("s");
		label.setOpaque(true);
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLUE);
		label.setPreferredSize(new Dimension(50, 50));
		label.setMinimumSize(new Dimension(10, 10));
		label.setMaximumSize(new Dimension(60, 60));
		
		JLabel label7 = new JLabel("v");
		label7.setOpaque(true);
		label7.setForeground(Color.WHITE);
		label7.setBackground(Color.BLUE);
		label7.setPreferredSize(new Dimension(50, 50));
		label7.setMinimumSize(new Dimension(10, 10));
		label7.setMaximumSize(new Dimension(60, 60));
		
		p.add(label, new RCPosition(5, 7));
		p.add(label7, new RCPosition(2, 5));
	
		cp.add(p, null);
	}
	
	@Test
	public void validStrings() {
		JFrame f = new JFrame("Test");
		
		f.setTitle("Example");
		
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		
		Container cp = f.getContentPane();
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		JLabel label = new JLabel("s");
		label.setOpaque(true);
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLUE);
		label.setPreferredSize(new Dimension(50, 50));
		label.setMinimumSize(new Dimension(10, 10));
		label.setMaximumSize(new Dimension(60, 60));
		
		JLabel label7 = new JLabel("v");
		label7.setOpaque(true);
		label7.setForeground(Color.WHITE);
		label7.setBackground(Color.BLUE);
		label7.setPreferredSize(new Dimension(50, 50));
		label7.setMinimumSize(new Dimension(10, 10));
		label7.setMaximumSize(new Dimension(60, 60));
		
		p.add(label, "1,1");
		p.add(label7, "1,6");
	
		cp.add(p, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void wrongRCPosition() {
		JFrame f = new JFrame("Test");
		
		f.setTitle("Example");
		
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		
		Container cp = f.getContentPane();
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		JLabel label = new JLabel("s");
		label.setOpaque(true);
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLUE);
		label.setPreferredSize(new Dimension(50, 50));
		label.setMinimumSize(new Dimension(10, 10));
		label.setMaximumSize(new Dimension(60, 60));
		
		JLabel label7 = new JLabel("v");
		label7.setOpaque(true);
		label7.setForeground(Color.WHITE);
		label7.setBackground(Color.BLUE);
		label7.setPreferredSize(new Dimension(50, 50));
		label7.setMinimumSize(new Dimension(10, 10));
		label7.setMaximumSize(new Dimension(60, 60));
		
		p.add(label, new RCPosition(0, 7));
		p.add(label7, new RCPosition(2, 5));
	
		cp.add(p, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void wrongRCPosition2() {
		JFrame f = new JFrame("Test");
		
		f.setTitle("Example");
		
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		
		Container cp = f.getContentPane();
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		JLabel label = new JLabel("s");
		label.setOpaque(true);
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLUE);
		label.setPreferredSize(new Dimension(50, 50));
		label.setMinimumSize(new Dimension(10, 10));
		label.setMaximumSize(new Dimension(60, 60));
		
		JLabel label7 = new JLabel("v");
		label7.setOpaque(true);
		label7.setForeground(Color.WHITE);
		label7.setBackground(Color.BLUE);
		label7.setPreferredSize(new Dimension(50, 50));
		label7.setMinimumSize(new Dimension(10, 10));
		label7.setMaximumSize(new Dimension(60, 60));
		
		p.add(label, new RCPosition(1, 2));
		p.add(label7, new RCPosition(2, 5));
	
		cp.add(p, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void wrongRCPosition3() {
		JFrame f = new JFrame("Test");
		
		f.setTitle("Example");
		
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		
		Container cp = f.getContentPane();
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		JLabel label = new JLabel("s");
		label.setOpaque(true);
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLUE);
		label.setPreferredSize(new Dimension(50, 50));
		label.setMinimumSize(new Dimension(10, 10));
		label.setMaximumSize(new Dimension(60, 60));
		
		JLabel label7 = new JLabel("v");
		label7.setOpaque(true);
		label7.setForeground(Color.WHITE);
		label7.setBackground(Color.BLUE);
		label7.setPreferredSize(new Dimension(50, 50));
		label7.setMinimumSize(new Dimension(10, 10));
		label7.setMaximumSize(new Dimension(60, 60));
		
		p.add(label, new RCPosition(1, 4));
		p.add(label7, new RCPosition(2, 5));
	
		cp.add(p, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void wrongRCPosition4() {
		JFrame f = new JFrame("Test");
		
		f.setTitle("Example");
		
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		
		Container cp = f.getContentPane();
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		JLabel label = new JLabel("s");
		label.setOpaque(true);
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLUE);
		label.setPreferredSize(new Dimension(50, 50));
		label.setMinimumSize(new Dimension(10, 10));
		label.setMaximumSize(new Dimension(60, 60));
		
		JLabel label7 = new JLabel("v");
		label7.setOpaque(true);
		label7.setForeground(Color.WHITE);
		label7.setBackground(Color.BLUE);
		label7.setPreferredSize(new Dimension(50, 50));
		label7.setMinimumSize(new Dimension(10, 10));
		label7.setMaximumSize(new Dimension(60, 60));
		
		p.add(label, new RCPosition(1, 5));
		p.add(label7, new RCPosition(2, 5));
	
		cp.add(p, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void wrongRCPosition5() {
		JFrame f = new JFrame("Test");
		
		f.setTitle("Example");
		
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		
		Container cp = f.getContentPane();
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		JLabel label = new JLabel("s");
		label.setOpaque(true);
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLUE);
		label.setPreferredSize(new Dimension(50, 50));
		label.setMinimumSize(new Dimension(10, 10));
		label.setMaximumSize(new Dimension(60, 60));
		
		JLabel label7 = new JLabel("v");
		label7.setOpaque(true);
		label7.setForeground(Color.WHITE);
		label7.setBackground(Color.BLUE);
		label7.setPreferredSize(new Dimension(50, 50));
		label7.setMinimumSize(new Dimension(10, 10));
		label7.setMaximumSize(new Dimension(60, 60));
		
		p.add(label, new RCPosition(2, 8));
		p.add(label7, new RCPosition(2, 5));
	
		cp.add(p, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void wrongRCPosition6() {
		JFrame f = new JFrame("Test");
		
		f.setTitle("Example");
		
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		
		Container cp = f.getContentPane();
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		JLabel label = new JLabel("s");
		label.setOpaque(true);
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLUE);
		label.setPreferredSize(new Dimension(50, 50));
		label.setMinimumSize(new Dimension(10, 10));
		label.setMaximumSize(new Dimension(60, 60));
		
		JLabel label7 = new JLabel("v");
		label7.setOpaque(true);
		label7.setForeground(Color.WHITE);
		label7.setBackground(Color.BLUE);
		label7.setPreferredSize(new Dimension(50, 50));
		label7.setMinimumSize(new Dimension(10, 10));
		label7.setMaximumSize(new Dimension(60, 60));
		
		p.add(label, new RCPosition(6, 2));
		p.add(label7, new RCPosition(2, 5));
	
		cp.add(p, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void duplicatePosition() {
		JFrame f = new JFrame("Test");
		
		f.setTitle("Example");
		
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		
		Container cp = f.getContentPane();
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		JLabel label = new JLabel("s");
		label.setOpaque(true);
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLUE);
		label.setPreferredSize(new Dimension(50, 50));
		label.setMinimumSize(new Dimension(10, 10));
		label.setMaximumSize(new Dimension(60, 60));
		
		JLabel label7 = new JLabel("v");
		label7.setOpaque(true);
		label7.setForeground(Color.WHITE);
		label7.setBackground(Color.BLUE);
		label7.setPreferredSize(new Dimension(50, 50));
		label7.setMinimumSize(new Dimension(10, 10));
		label7.setMaximumSize(new Dimension(60, 60));
		
		p.add(label, new RCPosition(5, 2));
		p.add(label7, new RCPosition(5, 2));
	
		cp.add(p, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void duplicatePosition2() {
		JFrame f = new JFrame("Test");
		
		f.setTitle("Example");
		
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		
		Container cp = f.getContentPane();
		
		JPanel p = new JPanel(new CalcLayout(3));
		
		JLabel label = new JLabel("s");
		label.setOpaque(true);
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLUE);
		label.setPreferredSize(new Dimension(50, 50));
		label.setMinimumSize(new Dimension(10, 10));
		label.setMaximumSize(new Dimension(60, 60));
		
		JLabel label7 = new JLabel("v");
		label7.setOpaque(true);
		label7.setForeground(Color.WHITE);
		label7.setBackground(Color.BLUE);
		label7.setPreferredSize(new Dimension(50, 50));
		label7.setMinimumSize(new Dimension(10, 10));
		label7.setMaximumSize(new Dimension(60, 60));
		
		p.add(label, "1,1");
		p.add(label7, "1,1");
	
		cp.add(p, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void maximumSizeNull() {
		JPanel p = new JPanel(new CalcLayout(3));
		
		((CalcLayout) p.getLayout()).maximumLayoutSize(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void minimumSizeNull() {
		JPanel p = new JPanel(new CalcLayout(3));
		
		((CalcLayout) p.getLayout()).minimumLayoutSize(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void preferredSizeNull() {
		JPanel p = new JPanel(new CalcLayout(3));
		
		((CalcLayout) p.getLayout()).preferredLayoutSize(null);
	}
}
